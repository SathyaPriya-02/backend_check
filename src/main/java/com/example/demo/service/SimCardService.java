package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.SimCard;
import com.example.demo.entity.User;
import com.example.demo.repository.SimCardRepo;


@Service
public class SimCardService {
    @Autowired
    private SimCardRepo simCardRepository;

    public SimCard createSimCard(SimCard simCard) {
        return simCardRepository.save(simCard);
    }
}   