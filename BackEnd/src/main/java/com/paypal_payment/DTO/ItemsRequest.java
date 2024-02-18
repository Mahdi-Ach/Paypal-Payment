package com.paypal_payment.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemsRequest {
    private Long id;
    private Long quantite;
    private int unit_amount;
}
