package com.paypal_payment.Repositories;

import com.paypal_payment.BO.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users,Long> {
}
