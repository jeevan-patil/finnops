package xyz.jeevan.finnops.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import xyz.jeevan.finnops.domain.Transaction;

/**
 * This repository handles all the database related stuff for transactions.
 * 
 * @author jeevan
 *
 */
public interface TransactionRepository {

  /**
   * This method creates new transaction.
   * 
   * @param transaction Transaction object containing amount and time.
   */
  void create(Transaction transaction);

  /**
   * Method to return transactions carried out in last 60 seconds.
   * 
   * @return {@code Future<List<Transaction>>} List of transactions carried out in the last 60
   *         seconds.
   */
  CompletableFuture<List<Transaction>> findTransactionsInLast();

}
