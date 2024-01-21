package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.RechargePayment;


public interface RechargePaymentRepo extends JpaRepository<RechargePayment, Integer> {
	
	 RechargePayment findByPhonenumber(String phonenumber);
	 
	 RechargePayment findByTransactionid(String tid);

}
