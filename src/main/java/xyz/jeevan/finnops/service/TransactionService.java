package xyz.jeevan.finnops.service;

import java.util.concurrent.CompletableFuture;
import xyz.jeevan.finnops.domain.Statistics;
import xyz.jeevan.finnops.domain.Transaction;

/**
 * Transaction service.
 * 
 * @author jeevan
 *
 */
public interface TransactionService {

  /**
   * This method creates new transaction.
   * 
   * @param transaction Transaction information.
   */
  void createNew(Transaction transaction);

  CompletableFuture<Statistics> getLastMinuteTransactionStats();

}
