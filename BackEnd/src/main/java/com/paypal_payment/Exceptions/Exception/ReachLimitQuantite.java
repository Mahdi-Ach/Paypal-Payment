package com.paypal_payment.Exceptions.Exception;

public class ReachLimitQuantite extends RuntimeException{
    public ReachLimitQuantite(String message){
        super(message);
    }
}
