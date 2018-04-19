package com.vimalmoorthy.bankingstatistics.controller;

import com.vimalmoorthy.bankingstatistics.model.Statistics;
import com.vimalmoorthy.bankingstatistics.service.BankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Vimal on 04/17/2018.
 * Controller for statistics endpoint
 */
@RestController
@RequiredArgsConstructor
public class StatisticsRestController {

    @Autowired
    private BankingService requestService;

    /**
     *  Method showStats get the statistics for the last 60 seconds.
     */
    @GetMapping(path = "/statistics")
    Statistics showStats(){
        return requestService.getStatistics();
    }

}
