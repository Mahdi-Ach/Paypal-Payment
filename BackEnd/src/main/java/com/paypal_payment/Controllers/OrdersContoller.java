package com.paypal_payment.Controllers;

import com.paypal_payment.DTO.CompleteOrderRequest;
import com.paypal_payment.DTO.ItemsRequest;
import com.paypal_payment.Exceptions.Exception.ReachLimitQuantite;
import com.paypal_payment.Services_Implemnt.OrdersInt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrdersContoller {
    private final OrdersInt ordersInt;

    @PostMapping("/create-order")
    public HashMap<String,String> createOrder(@RequestBody List<ItemsRequest> itemsRequest) throws IOException, ReachLimitQuantite {
        HashMap<String,String> orderid = new HashMap<>();
        orderid.put("orderID", (String) ordersInt.getOrderID(itemsRequest).get("id"));
        return orderid;
    }
    @PostMapping("/complete-order")
    public ResponseEntity<HashMap<String,String>> completeOrder(@RequestBody CompleteOrderRequest completeOrderRequest) throws IOException {
        String response = ordersInt.ConfirmCompleteOrder(completeOrderRequest);
        HashMap<String,String> status = new HashMap<>();
        status.put("status",response);
        return ResponseEntity.ok(status);
    }
}
