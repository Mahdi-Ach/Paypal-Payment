package com.paypal_payment.Services;

import com.paypal_payment.Repositories.UsersRepo;
import com.paypal_payment.Services_Implemnt.UsersInt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Users implements UsersInt {
    private final UsersRepo usersRepo;
    @Override
    public com.paypal_payment.BO.Users save(com.paypal_payment.BO.Users users) {
        return usersRepo.save(users);
    }
}
