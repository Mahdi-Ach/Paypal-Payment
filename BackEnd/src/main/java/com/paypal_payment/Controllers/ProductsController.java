package com.paypal_payment.Controllers;

import com.paypal_payment.DTO.ProductListResponseDTO;
import com.paypal_payment.Services_Implemnt.ProductsInt;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductsController {
    private final ProductsInt productsInt;
    private final OrdersContoller ordersContoller;
    @GetMapping("/list-Product")
    public ProductListResponseDTO GetListProduct(@RequestParam int page, @RequestParam int size){
        return productsInt.listProduct(page,size);
    }

}
