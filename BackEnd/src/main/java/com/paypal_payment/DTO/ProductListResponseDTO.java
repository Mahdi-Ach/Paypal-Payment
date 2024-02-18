package com.paypal_payment.DTO;

import com.paypal_payment.BO.Products;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ProductListResponseDTO {
    List<Products> Content;
    int totalpage;
    long totalelements;
    int current_page;
}
