N26 API---STATISTICS FOR 60 SECONDS TRANSACTIONS
------------------------------------------------

2 ENPOINT

("/transactions") - only endpoint to enter trasaction in json format.

("/statistics")- endpoint to get the satistics for last 60 seconds.

##############################################################################################################

API STRUCTURE:
----------------------

- CONTROLLER
- MODEL
- SERVICE

CONTROLLER:
-------------

There are two separate controller for two endpoint:

TransactionRestController - to represent the post endpoint for entering all the transaction in the form of JSON

StatisticsRestController  - to get the statistics such as count,sum,avg,max and min out of all trasactioin for past 60 seconds.


MODEL:
------------

Transactions :   represents the model of json entry data. it has two differnt part , one with be the amount and other will be the transaction timestamp.

Statistics  :    represents the  computation of sum,avg,count, max and min . here no data are stored but the datas are managed and handled as per the trasactions.


SERVICE:
--------------

 Contains all the services for Transactions and Statistics
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

 ###############################################################################################################

STORAGE:
-----------------
Datas are stores in  memory LinkedList.The reason for using likedlist is because it has good insert and delete operation runtime complexity of O(1). Basically the remove operation in likedList is very effective in this kind of API where are is frequent deletion of data.  

####################################################################################################################

SCHEDULED TASK EXECUTOR:
-----------------------------

checks for the invalid trasaction every 0.5 seconds. A transaction is considered to be invalid once it exceed 60 seconds frame. The Scheduled task executor looks for such transaction in the list and removes it . Once the transaction is removed new statistics are calculated as per the current status of the list.


##########################################################################################################################

TEST CASES:
-------------

There are 17 test cases

- INTEGRATION TESTING for controllers
- SANITY TESTING for the https layer
- SERVICE TESTING for service class