package com.vimalmoorthy.bankingstatistics.service;

import com.vimalmoorthy.bankingstatistics.model.Statistics;
import com.vimalmoorthy.bankingstatistics.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.LinkedList;

/**
 * ********  Created by Vimal on 04/17/2018 *********
 * Contains all the services for Transactions and Statistics
 * The transactions are stored in a List. The trade off for choosing LinkedList to store the transactions is because
 * insert and remove operations give good performance (O(1)) in LinkedList. All the transaction are posted through the
 * POST ("/transaction") endpoint.which is also the only endpoint to enter values. Each values are analyzed with the timestamp
 * json field attached with the transactions.All the transaction which happend in last 60 seconds are stored in List and respective
 * sum , max, min and average are calculated(Statistics values)
 *
 * A schedule task is allocated with the service called scheduleTasktoRemoveExpired(). This task runs in a whole new thread
 * the functionality of this task is to remove all the expired transactions from the list according to the timestamp value.
 * the task is called every .5 seconds. This makes the Statistics up to date.This is done by using
 * @EnableScheduing. This also have the fature to control the start time delay of the scheduling.
 *
 * Everytime the transactions expired the statistics changes calculating new sum,average,count,max and min.
 *
 */
@Component
@EnableScheduling
public class BankingService implements removeExpired {
    public static long intervalForStats = 60000;
    private final Logger log = LoggerFactory.getLogger(BankingService.class);
    private static LinkedList < Transaction > requiredTransaction = new LinkedList < > ();
    private Statistics statsCall = new Statistics();

    BankingService() { scheduleTasktoRemoveExpired(); }

    /**
     * Task scheduled for every 0.5 seconds to flush the expired transactions.
     */
    @Scheduled(fixedRate = 500, initialDelay = 500)
    public void scheduleTasktoRemoveExpired() {
        removeExpiredTransaction();
    }

    /**
     * Every new transactions is checked for valid timestamp in the range of 60 seconds.
     * IntervalCheck() does the checking for timestamp.
     * If valid the trasaction is entered into the statistics with the help of a LikedList.
     * @param transaction
     * @return boolean
     */
    public boolean addTransaction(Transaction transaction) {
        if (IntervalCheck(transaction)) {
            resigterTransaction(transaction);
            return true;
        }
        return false;
    }

    /**
     * Checks for the expired transactions while entering into the list.
     * @param registeredTransaction
     * @return boolean
     */
    public boolean IntervalCheck(Transaction registeredTransaction) {
        long currentTime = System.currentTimeMillis();
        long validTime = currentTime - registeredTransaction.getTimestamp().getTime();
        return (validTime <= intervalForStats) ? true : false;
    }

    /**
     * Valid transactions are added to the linkedList and the statistics are updated.
     * support parallel transaction
     * @param transaction
     */
    private void resigterTransaction(Transaction transaction) {
        synchronized(BankingService.class) {
            double amount = transaction.getAmount();
            requiredTransaction.addFirst(transaction);
            statsCall.addNewAmount(amount);
        }
    }

    /**
     * Expired timestamp transactions are removed from the LinkedList and
     * new statistics are updated.
     */
    public void removeExpiredTransaction() {

        for (Transaction data: requiredTransaction) {
            if (IntervalCheck(data) == false) {
                synchronized(BankingService.class) {
                    requiredTransaction.remove(data);
                    updateValidTransaction(data);
                }
            }
        }
    }

    /**
     * Updating the statistics after removing the unwanted transactions
     * All the stat values are set to 0 if the count is 0 or less than 0
     * @param transaction
     */
    public void updateValidTransaction(Transaction transaction) {
        statsCall.setCount(statsCall.getCount() - 1);

        if (statsCall.getCount() > 0) {
            statsCall.setSum(statsCall.getSum() - transaction.getAmount());
            statsCall.setAvg(statsCall.getSum() / statsCall.getCount());
            if (transaction.getAmount() == statsCall.getMax()) {
                statsCall.setMax(getMaxTransaction());
            }
            if (transaction.getAmount() == statsCall.getMin()) {
                statsCall.setMin(getMinTransaction());
            }
        } else {
            statsCall.setSum(0);
            statsCall.setAvg(0);
            statsCall.setMin(0);
            statsCall.setMax(0);
        }
    }

    /**
     * The GET("/statistics") endpoint calls this function to get all the statistic value for past 60 seconds.
     * @return Statistics
     */
    public Statistics getStatistics() {
        return new Statistics(statsCall);
    }

    /**
     * Calculate the latest maximum transaction amount after deletion of expired transactions
     * @return maximum Transaction Amount
     */
    public double getMaxTransaction() {
        double maxTransactionAmount = 0;
        for (Transaction data: requiredTransaction) {
            if (data.getAmount() > maxTransactionAmount) {
                maxTransactionAmount = data.getAmount();
            }
        }
        return maxTransactionAmount;
    }

    /**
     * Calculate the latest minimum transaction amount after deletion of expired transactions
     * @return minimum transaction Amount
     */
    public double getMinTransaction() {
        double minTransactionAmount = Double.MAX_VALUE;
        for (Transaction data: requiredTransaction) {
            if (data.getAmount() < minTransactionAmount) {
                minTransactionAmount = data.getAmount();
            }
        }
        return minTransactionAmount;
    }

}