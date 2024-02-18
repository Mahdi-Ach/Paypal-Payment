package com.paypal_payment.BO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordID;
    private String orderId;
    private String description;
    private String create_time;
    private String status;
    private String amount;
    private String intent;
    private String update_time;
    @OneToMany
    @JoinColumn(name = "order_id")
    private List<BreakDown> breakDowns = new ArrayList<>();
    @ManyToOne
    private Users users;
}
