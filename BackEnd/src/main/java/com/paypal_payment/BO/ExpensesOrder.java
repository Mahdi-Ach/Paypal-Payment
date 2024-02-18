package com.paypal_payment.BO;

import com.paypal_payment.enums.Expenses;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ExpensesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expensesid;
    @Enumerated(EnumType.STRING)
    private Expenses expenses;

    @OneToMany
    @JoinColumn(name = "expense_id")
    private List<BreakDown> breakDowns = new ArrayList<>();

    public ExpensesOrder(Expenses expenses) {
        this.expenses = expenses;
    }

    public ExpensesOrder() {

    }
}
