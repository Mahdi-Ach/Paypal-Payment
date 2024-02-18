package com.paypal_payment.Services;

import com.paypal_payment.BO.ExpensesOrder;
import com.paypal_payment.Repositories.ExpensesOrderRepo;
import com.paypal_payment.Services_Implemnt.ExpenseOrderInt;
import com.paypal_payment.enums.Expenses;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseOrder implements ExpenseOrderInt {
    private final ExpensesOrderRepo expensesOrderRepo;
    @Override
    public ExpensesOrder save(ExpensesOrder expensesOrder) {
        return expensesOrderRepo.save(expensesOrder);
    }
    public ExpensesOrder findbyExpenses(Expenses expenses){
        return expensesOrderRepo.findByExpenses(expenses);
    }
}
