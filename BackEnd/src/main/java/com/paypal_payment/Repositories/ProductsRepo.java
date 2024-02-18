package com.paypal_payment.Repositories;

import com.paypal_payment.BO.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepo extends JpaRepository<Products,Long> {
    Products findByProdid(Long Id);
}
