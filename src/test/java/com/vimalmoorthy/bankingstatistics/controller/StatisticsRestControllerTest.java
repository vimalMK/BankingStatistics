package com.vimalmoorthy.bankingstatistics.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.vimalmoorthy.bankingstatistics.BankingStatisticsApplicationTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Vimal on 04/17/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsRestControllerTest extends BankingStatisticsApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;


    @InjectMocks
    private StatisticsRestController Service;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void TransactionControllerTest_forBaseResult_withoutinput() throws Exception {

        mockMvc.perform(get("/statistics"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value("0")).andExpect(jsonPath("$.sum").value("0.0"))
                .andExpect(jsonPath("$.avg").value("0.0")).andExpect(jsonPath("$.max").value(0))
                .andExpect(jsonPath("$.min").value(0));

    }

}