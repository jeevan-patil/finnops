package xyz.jeevan.finnops.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import xyz.jeevan.finnops.domain.Transaction;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

  private static final Logger log = LoggerFactory.getLogger(TransactionRepositoryImpl.class);

  /**
   * Collection which stores all the transactions. Key -> Transaction id, value -> transaction
   * object.
   */
  private final Map<String, Transaction> transactions = new ConcurrentHashMap<>();

  @Override
  @Async
  public void create(Transaction transaction) {
    log.info("Insert new transaction in memory.");
    transactions.put(transaction.getId(), transaction);
  }

  @Override
  @Async
  public CompletableFuture<List<Transaction>> findTransactionsInLast() {
    log.info("Fetch transaction within last 60 seconds");
    long sixtySecondsAgo = System.currentTimeMillis() - 60 * 1000;
    List<Transaction> transactionsWithinMinute =
        transactions.entrySet().parallelStream().map(tranx -> tranx.getValue())
            .filter(tranx -> !tranx.isOlderThan(sixtySecondsAgo)).collect(Collectors.toList());
    return CompletableFuture.completedFuture(transactionsWithinMinute);
  }

}
