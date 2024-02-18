package com.paypal_payment.Repositories;

import com.paypal_payment.BO.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepo extends JpaRepository<Orders,Long> {
    Orders findByOrderId(String orderId);
}
