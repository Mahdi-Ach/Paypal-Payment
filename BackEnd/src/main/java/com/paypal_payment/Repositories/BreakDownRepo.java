package com.paypal_payment.Repositories;

import com.paypal_payment.BO.BreakDown;
import org.checkerframework.common.util.report.qual.ReportCreation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakDownRepo extends JpaRepository<BreakDown,Long> {
}
