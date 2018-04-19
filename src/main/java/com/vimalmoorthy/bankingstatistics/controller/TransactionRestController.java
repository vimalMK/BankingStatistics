package com.vimalmoorthy.bankingstatistics.controller;

import com.vimalmoorthy.bankingstatistics.model.Transaction;
import com.vimalmoorthy.bankingstatistics.service.BankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Vimal on 04/17/2018.
 * Controller for transaction endpoint
 */
@RestController
@RequiredArgsConstructor
public class TransactionRestController {

    @Autowired
    private BankingService transactionService;

    /**
     * Method called registerTransaction gets the JSON transaction values from body
     * and returns 201 on successful entry else 204 if unsuccessful
     */
    @PostMapping( path = "/transactions", produces = "application/json")
    public ResponseEntity<String> registerTransaction(@RequestBody Transaction transaction) {
        boolean registerStatus = transactionService.addTransaction(transaction);

        if (registerStatus) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
