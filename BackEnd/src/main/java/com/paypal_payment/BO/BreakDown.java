package com.paypal_payment.BO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class BreakDown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long breakdownid;
    String currency_code;
    String value;
    @ManyToOne
    @JoinColumn(name = "order_id")  // Specify the foreign key column
    private Orders order;

}
