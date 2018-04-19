package com.vimalmoorthy.bankingstatistics.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.vimalmoorthy.bankingstatistics.BankingStatisticsApplicationTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * Created by Vimal on 04/17/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionRestControllerTest extends BankingStatisticsApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;


    @InjectMocks
    private TransactionRestController sampleService;

    private MockMvc mockMvc;
    private static final long VALID_TIMESTAMP = System.currentTimeMillis();
    private static final long INVALID_TIMESTAMP = System.currentTimeMillis()-60000;



    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void postTransaction_forValidTimestamp() throws Exception {
        String input="{\"timestamp\": "+VALID_TIMESTAMP+", \"amount\": 12.1}";
        mockMvc.perform(post("/transactions")
                .content(input)
                .contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isCreated());

    }
    @Test
    public void postTransaction_forInValidTimestamp() throws Exception {
        String input="{\"timestamp\": "+INVALID_TIMESTAMP+", \"amount\": 12.1}";
        mockMvc.perform(post("/transactions")
                .content(input)
                .contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isNoContent());

    }
}