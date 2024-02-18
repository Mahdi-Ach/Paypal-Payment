package com.paypal_payment.Services_Implemnt;

import com.paypal_payment.BO.Orders;
import com.paypal_payment.DTO.CompleteOrderRequest;
import com.paypal_payment.DTO.ItemsRequest;
import com.paypal_payment.Exceptions.Exception.ReachLimitQuantite;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public interface OrdersInt {
    Orders save(Orders orders);
    Orders findByOrderId(String orderId);
    JSONObject getOrderID(List<ItemsRequest> itemsRequest) throws IOException, ReachLimitQuantite;
    String ConfirmCompleteOrder(CompleteOrderRequest completeOrderRequest) throws IOException;
}
