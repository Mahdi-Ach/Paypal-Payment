package com.paypal_payment.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal_payment.BO.*;
import com.paypal_payment.BO.Users;
import com.paypal_payment.DTO.CompleteOrderRequest;
import com.paypal_payment.DTO.ItemsRequest;
import com.paypal_payment.Exceptions.Exception.ReachLimitQuantite;
import com.paypal_payment.Repositories.OrdersRepo;
import com.paypal_payment.Services_Implemnt.*;
import com.paypal_payment.Utils.PaypalToken;
import com.paypal_payment.enums.Expenses;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@Service
@Transactional
@RequiredArgsConstructor
public class OrdersService implements OrdersInt {
    private final UsersInt usersInt;
    private final BreakDownInt breakDownInt;
    private final ExpenseOrderInt expenseOrderInt;
    private final PaypalToken paypalToken;
    private final OrdersRepo ordersRepo;
    private final ProductsInt productsInt;
    @Value("${Order}")
    private String Order;
    @Override
    public Orders save(Orders orders) {
        return ordersRepo.save(orders);
    }
    @Override
    public Orders findByOrderId(String orderId){
        return ordersRepo.findByOrderId(orderId);
    }
    public JSONObject getOrderID(List<ItemsRequest> itemsRequest) throws IOException,ReachLimitQuantite {
        int total = itemsRequest.stream().mapToInt(ItemsRequest::getUnit_amount).sum();
        URL url = new URL(Order);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestProperty("Authorization", "Bearer "+paypalToken.getAccessToken());
        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("{\"intent\":\"CAPTURE\",\"purchase_units\":[{\"amount\":{\"currency_code\":\"USD\",\"value\":\""+total+"\"}}]}");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();
        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response1 = s.hasNext() ? s.next() : "";
        org.json.JSONObject response = new JSONObject(response1);
        Orders orders = new Orders();
        orders.setOrderId((String) response.get("id"));
        orders.setStatus((String) response.get("status"));
        ordersRepo.save(orders);
        productsInt.update_Quantite(itemsRequest);
        return response;
    }
    @Override
    public String ConfirmCompleteOrder(CompleteOrderRequest completeOrderRequest) throws IOException {
        URL url = new URL(Order+"/"+completeOrderRequest.getOrder_id()+"/capture");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestProperty("Authorization","Bearer "+paypalToken.getAccessToken());
        conn.setDoOutput(true);
        InputStream responseStream = conn.getResponseCode() / 100 == 2
                ? conn.getInputStream()
                : conn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response1 = s.hasNext() ? s.next() : "";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode response = objectMapper.readTree(response1);
        //Create User
        com.paypal_payment.BO.Users payer = new Users();
        payer.setEmail(response.path("payer").path("email_address").asText());
        payer.setAccount_status(response.path("payment_source").path("paypal").path("account_status").asText());
        payer.setAccount_id(response.path("payer").path("payer_id").asText());
        payer.setSurname(response.path("payer").path("name").path("surname").asText());
        payer.setGiven_name(response.path("payer").path("name").path("given_name").asText());
        usersInt.save(payer);
        //Create Order
        Orders orders = findByOrderId(response.path("id").asText());
        orders.setCreate_time(response.path("purchase_units").path(0).path("payments").path("captures").path(0).path("create_time").asText());
        orders.setCreate_time(response.path("purchase_units").path(0).path("payments").path("captures").path(0).path("update_time").asText());
        orders.setStatus(response.path("status").asText());
        orders.setAmount(response.path("purchase_units").path(0).path("payments").path("captures").path(0).path("amount").path("value").asText());
        orders.setUsers(payer);
        JsonNode breakdownpayment = response.path("purchase_units").path(0).path("payments").path("captures").path(0).path("seller_receivable_breakdown");
        //Create BreakDown
        BreakDown breakDown = new BreakDown();
        breakDown.setValue(breakdownpayment.path("gross_amount").path("value").asText());
        breakDown.setCurrency_code(breakdownpayment.path("gross_amount").path("currency_code").asText());
        ExpensesOrder expensesOrder = expenseOrderInt.findbyExpenses(Expenses.gross_amount);
        expensesOrder.getBreakDowns().add(breakDown);
        breakDownInt.save(breakDown);
        orders.getBreakDowns().add(breakDown);
        expenseOrderInt.save(expensesOrder);
        //Create BreakDown
        breakDown = new BreakDown();
        breakDown.setValue(breakdownpayment.path("paypal_fee").path("value").asText());
        breakDown.setCurrency_code(breakdownpayment.path("paypal_fee").path("currency_code").asText());
        expensesOrder = expenseOrderInt.findbyExpenses(Expenses.paypal_fee);
        expensesOrder.getBreakDowns().add(breakDown);
        breakDownInt.save(breakDown);
        orders.getBreakDowns().add(breakDown);
        expenseOrderInt.save(expensesOrder);
        //Create BreakDown
        breakDown = new BreakDown();
        breakDown.setValue(breakdownpayment.path("net_amount").path("value").asText());
        breakDown.setCurrency_code(breakdownpayment.path("net_amount").path("currency_code").asText());
        expensesOrder = expenseOrderInt.findbyExpenses(Expenses.net_amount);
        expensesOrder.getBreakDowns().add(breakDown);
        breakDownInt.save(breakDown);
        orders.getBreakDowns().add(breakDown);
        expenseOrderInt.save(expensesOrder);
        ordersRepo.save(orders);
        return response.path("status").asText();
    }
}
