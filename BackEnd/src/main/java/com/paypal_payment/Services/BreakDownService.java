package com.paypal_payment.Services;

import com.paypal_payment.BO.BreakDown;
import com.paypal_payment.Repositories.BreakDownRepo;
import com.paypal_payment.Services_Implemnt.BreakDownInt;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BreakDownService implements BreakDownInt {
    private final BreakDownRepo breakDownRepo;
    @Override
    public BreakDown save(BreakDown breakDown) {
        return breakDownRepo.save(breakDown);
    }
}
