package com.paypal_payment;

import com.paypal_payment.BO.ExpensesOrder;
import com.paypal_payment.Repositories.ExpensesOrderRepo;
import com.paypal_payment.Services_Implemnt.ExpenseOrderInt;
import com.paypal_payment.enums.Expenses;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackEndApplication {

    public static void main(String[] args) {

        SpringApplication.run(BackEndApplication.class, args);
    }
    @Bean
    CommandLineRunner run (ExpenseOrderInt expenseOrderInt)
    {return  args ->
    {
        expenseOrderInt.save(new ExpensesOrder(Expenses.gross_amount));
        expenseOrderInt.save(new ExpensesOrder(Expenses.paypal_fee));
        expenseOrderInt.save(new ExpensesOrder(Expenses.net_amount));


    };}
}
