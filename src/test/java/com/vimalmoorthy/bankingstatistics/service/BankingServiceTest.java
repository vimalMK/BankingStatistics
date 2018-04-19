package com.vimalmoorthy.bankingstatistics.service;

import com.vimalmoorthy.bankingstatistics.BankingStatisticsApplicationTests;
import com.vimalmoorthy.bankingstatistics.model.Statistics;
import com.vimalmoorthy.bankingstatistics.model.Transaction;
import org.junit.Test;
import org.mockito.InjectMocks;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Vimal on 04/17/2018.
 */
public class BankingServiceTest extends BankingStatisticsApplicationTests {

    @InjectMocks
    private BankingService service;

    Statistics stats=new Statistics();
    private static final Date VALID_DATE =new Date();
    private static final Date INVALID_DATE = new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1));;
    ;

    @Test
    public void returnTrue_onValidTimestampEntry() throws Exception {
        final Transaction transaction = new Transaction(12.3,VALID_DATE);
        final Boolean checked = service.IntervalCheck(transaction);
        assertThat(checked, is(true));
    }

    @Test
    public void returnTrue_onInValidTimestampEntry() throws Exception {
        final Transaction transaction = new Transaction(12.3, INVALID_DATE);
        final Boolean checked = service.IntervalCheck(transaction);
        assertThat(checked, is(false));
    }

    @Test
    public void returnTrue_OnSuccessfullTransaction() throws Exception {
        final Transaction transaction = new Transaction(10.5, VALID_DATE);
        final Boolean transactionComplete = service.addTransaction(transaction);
        assertThat(transactionComplete, is(true));
    }

    @Test
    public void returnFalse_OnUnSuccessfullTransaction() throws Exception {
        final Transaction transaction = new Transaction(10.5, INVALID_DATE);
        final Boolean transactionComplete = service.addTransaction(transaction);
        assertThat(transactionComplete, is(false));
    }

    @Test
    public void SuccessfulTrasactionEntry_toLikedList() throws Exception {
        final Transaction transaction = new Transaction(11.5, VALID_DATE);
        final Transaction transactionSecondary = new Transaction(10.5, VALID_DATE);

        service.addTransaction(transaction);
        service.addTransaction(transactionSecondary);
        assertThat(service.getStatistics(), is(notNullValue()));
        assertThat(service.getStatistics().getMin(), is(10.5));
        assertThat(service.getStatistics().getMax(), is(11.5));
        assertThat(service.getStatistics().getSum(), is(22.0));
        assertThat(service.getStatistics().getAvg(), is(11.0));
    }

    @Test
    public void checkIftheStatistics_UpdateonTransactionRemoval() throws Exception {
        final Transaction transaction = new Transaction(100.5, VALID_DATE);
        final Transaction transactionSecondary = new Transaction(10.5, INVALID_DATE);

        service.addTransaction(transaction);
        service.addTransaction(transactionSecondary);
        service.removeExpiredTransaction();
        assertThat(service.getStatistics().getSum(), is(100.5));
        assertThat(service.getStatistics().getAvg(), is(100.5));
    }
    @Test
    public void checkforLatestMaxTransaction() throws Exception {
        final Transaction transaction = new Transaction(12.5, VALID_DATE);
        final Transaction transactionSecondary = new Transaction(1.5, VALID_DATE);

        service.addTransaction(transaction);
        service.addTransaction(transactionSecondary);
        final double resultmax=service.getMaxTransaction();
        assertThat(resultmax, is(12.5));

    }

    @Test
    public void checkforLatestMinTransaction() throws Exception {
        final Transaction transaction = new Transaction(12.5, VALID_DATE);
        final Transaction transactionSecondary = new Transaction(1.5, VALID_DATE);

        service.addTransaction(transaction);
        service.addTransaction(transactionSecondary);
        final double result=service.getMinTransaction();
        assertThat(result, is(1.5));
    }
}
