package xyz.jeevan.finnops.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import xyz.jeevan.finnops.domain.Statistics;
import xyz.jeevan.finnops.domain.Transaction;
import xyz.jeevan.finnops.exception.ApplicationException;
import xyz.jeevan.finnops.exception.ErrorResponseEnum;
import xyz.jeevan.finnops.repository.TransactionRepositoryImpl;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class TransactionServiceImplTest {

  @InjectMocks
  @Spy
  private TransactionServiceImpl cut;

  @Mock
  private TransactionRepositoryImpl mockTransactionRepo;

  @Before
  public void setup() {}

  @Test
  public void createNew_success() {
    Transaction transaction = dummyTransaction(234d);

    doNothing().when(cut).validateTransaction(transaction);
    doNothing().when(mockTransactionRepo).create(transaction);

    cut.createNew(transaction);
    verify(mockTransactionRepo).create(transaction);
    verifyNoMoreInteractions(mockTransactionRepo);
  }

  @Test(expected = ApplicationException.class)
  public void createNew_testOldTransaction() {
    Transaction transaction = dummyTransaction(120d);

    doThrow(new ApplicationException(ErrorResponseEnum.OLD_TRANSACTION_ERROR)).when(cut)
        .validateTransaction(transaction);

    cut.createNew(transaction);
  }

  @Test
  public void getLastMinuteTransactionStats() {
    List<Transaction> transactions = Arrays.asList(dummyTransaction(100d), dummyTransaction(120d));

    when(mockTransactionRepo.findTransactionsInLast())
        .thenReturn(CompletableFuture.completedFuture(transactions));

    CompletableFuture<Statistics> stats = cut.getLastMinuteTransactionStats();
    Statistics statistics = stats.join();
    assertEquals(220d, statistics.getSum(), 0);
    assertEquals(2d, statistics.getCount(), 0);
    verify(mockTransactionRepo).findTransactionsInLast();
    verifyNoMoreInteractions(mockTransactionRepo);
  }

  private Transaction dummyTransaction(double amount) {
    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setTimestamp(1527344354287l);
    return transaction;
  }
}
