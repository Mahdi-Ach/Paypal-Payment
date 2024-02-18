package com.paypal_payment.Repositories;

import com.paypal_payment.BO.ExpensesOrder;
import com.paypal_payment.enums.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesOrderRepo extends JpaRepository<ExpensesOrder,Long> {
    ExpensesOrder findByExpenses(Expenses expenses);
}
