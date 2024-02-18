package com.paypal_payment.Services_Implemnt;

import com.paypal_payment.BO.ExpensesOrder;
import com.paypal_payment.enums.Expenses;

public interface ExpenseOrderInt {
    ExpensesOrder save(ExpensesOrder expensesOrder);
    ExpensesOrder findbyExpenses(Expenses expenses);
}
