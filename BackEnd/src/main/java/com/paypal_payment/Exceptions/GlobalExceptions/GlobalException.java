package com.paypal_payment.Exceptions.GlobalExceptions;

import com.paypal_payment.Exceptions.Exception.ReachLimitQuantite;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { ReachLimitQuantite.class})
    protected ResponseEntity<HashMap<String,String>> ReachQuantite(ReachLimitQuantite ex) {
        HashMap<String,String> error = new HashMap<>();
        error.put("no-quantite",ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}