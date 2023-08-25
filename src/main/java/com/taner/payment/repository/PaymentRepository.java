package com.taner.payment.repository;

import com.taner.payment.repository.entity.PaymentEntity;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {
}
