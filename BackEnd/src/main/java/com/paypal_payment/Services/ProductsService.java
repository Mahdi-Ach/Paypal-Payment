package com.paypal_payment.Services;

import com.paypal_payment.BO.Products;
import com.paypal_payment.DTO.ItemsRequest;
import com.paypal_payment.DTO.ProductListResponseDTO;
import com.paypal_payment.Repositories.ProductsRepo;
import com.paypal_payment.Services_Implemnt.ProductsInt;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsService implements ProductsInt {
    private final ProductsRepo productsRepo;

    @Override
    public Products save(Products products) {
        return productsRepo.save(products);
    }

    @Override
    public ProductListResponseDTO listProduct(int page, int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("prodid").descending());
        Page<Products> products = productsRepo.findAll(paging);
        ProductListResponseDTO prod = new ProductListResponseDTO();
        prod.setContent(products.getContent());
        prod.setTotalelements(products.getTotalElements());
        prod.setTotalpage(products.getTotalPages());
        prod.setCurrent_page(products.getNumber());
        return prod;
    }
    @Override
    public Products findproductbyid(Long id){
        return productsRepo.findByProdid(id);
    }
    @Override
    public Boolean update_Quantite(List<ItemsRequest> itemsRequest){
        itemsRequest.stream().forEach(itemsRequest1 -> {
            Products products = findproductbyid(itemsRequest1.getId());
            products.setQuantite(products.getQuantite()-itemsRequest1.getQuantite());
        });
        return true;
    }
}
