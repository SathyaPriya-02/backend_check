package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SimCard;
import com.example.demo.entity.User;

public interface SimCardRepo extends JpaRepository<SimCard, String> {

	SimCard findByPhonenumber(String phonenumber);

}
