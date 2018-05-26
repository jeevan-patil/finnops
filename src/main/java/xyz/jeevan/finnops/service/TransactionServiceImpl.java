package xyz.jeevan.finnops.service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.jeevan.finnops.domain.Statistics;
import xyz.jeevan.finnops.domain.Transaction;
import xyz.jeevan.finnops.exception.ApplicationException;
import xyz.jeevan.finnops.exception.ErrorResponseEnum;
import xyz.jeevan.finnops.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

  private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

  @Autowired
  private TransactionRepository transactionRepo;

  @Override
  @Async
  public void createNew(Transaction transaction) {
    log.info("Create new transaction");
    validateTransaction(transaction);
    transactionRepo.create(transaction);
  }

  private void validateTransaction(final Transaction transaction) {
    long sixtySecondsAgo = System.currentTimeMillis() - 60 * 1000;
    if (transaction.isOlderThan(sixtySecondsAgo)) {
      log.error("Transaction was older than 60 seconds. Could not process.");
      throw new ApplicationException(ErrorResponseEnum.OLD_TRANSACTION_ERROR);
    }
  }

  @Override
  @Async
  public CompletableFuture<Statistics> getLastMinuteTransactionStats() {
    log.info("Build statistics of transactions within last minute");
    List<Transaction> transactions = transactionRepo.findTransactionsInLast().join();
    List<Double> amounts = mapToAmounts(transactions).join();
    DoubleSummaryStatistics stats = getStats(amounts).join();
    return CompletableFuture.completedFuture(new Statistics(stats.getSum(), stats.getAverage(),
        stats.getMax(), stats.getMin(), stats.getCount()));
  }

  @Async
  public CompletableFuture<List<Double>> mapToAmounts(List<Transaction> transactions) {
    List<Double> amounts =
        transactions.stream().map(d -> d.getAmount()).collect(Collectors.toList());
    return CompletableFuture.completedFuture(amounts);
  }

  @Async
  public CompletableFuture<DoubleSummaryStatistics> getStats(List<Double> amounts) {
    DoubleSummaryStatistics stats = amounts.stream().mapToDouble((d) -> d).summaryStatistics();
    return CompletableFuture.completedFuture(stats);
  }

}
