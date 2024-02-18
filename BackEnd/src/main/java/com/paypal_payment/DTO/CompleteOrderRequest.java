package com.paypal_payment.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompleteOrderRequest {
    String intent;
    String order_id;
}
