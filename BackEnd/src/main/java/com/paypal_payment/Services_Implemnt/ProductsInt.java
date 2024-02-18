package com.paypal_payment.Services_Implemnt;

import com.paypal_payment.BO.Products;
import com.paypal_payment.DTO.ItemsRequest;
import com.paypal_payment.DTO.ProductListResponseDTO;

import java.util.List;

public interface ProductsInt {
    Products save(Products products);
    ProductListResponseDTO listProduct(int page, int size);
    Products findproductbyid(Long id);
    Boolean update_Quantite(List<ItemsRequest> itemsRequest);
}
