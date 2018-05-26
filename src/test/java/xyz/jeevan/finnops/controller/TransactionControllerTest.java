package xyz.jeevan.finnops.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.concurrent.CompletableFuture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import xyz.jeevan.finnops.domain.Statistics;
import xyz.jeevan.finnops.domain.Transaction;
import xyz.jeevan.finnops.exception.ApplicationException;
import xyz.jeevan.finnops.exception.ErrorResponseEnum;
import xyz.jeevan.finnops.service.TransactionService;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class TransactionControllerTest {

  @InjectMocks
  private TransactionController cut;

  @Mock
  private TransactionService mockTransactionService;

  @Test
  public void create_successTransaction() {
    Transaction transaction = dummyTransaction(220);
    ResponseEntity<Object> response = new ResponseEntity<Object>(null, HttpStatus.CREATED);

    doNothing().when(mockTransactionService).createNew(transaction);
    ResponseEntity<Object> actualResponse = cut.create(transaction);
    assertEquals(response.getStatusCode().value(), actualResponse.getStatusCode().value(), 0);
    verify(mockTransactionService).createNew(transaction);
    verifyNoMoreInteractions(mockTransactionService);
  }

  @Test(expected = ApplicationException.class)
  public void create_failureOldTransaction() {
    Transaction transaction = dummyTransaction(220);
    ResponseEntity<Object> response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);

    doThrow(new ApplicationException(ErrorResponseEnum.OLD_TRANSACTION_ERROR))
        .when(mockTransactionService).createNew(transaction);
    ResponseEntity<Object> actualResponse = cut.create(transaction);
    assertEquals(response.getStatusCode().value(), actualResponse.getStatusCode().value(), 0);
    verify(mockTransactionService).createNew(transaction);
    verifyNoMoreInteractions(mockTransactionService);
  }

  @Test
  public void statistics() {
    Statistics statistics = new Statistics(120, 60, 90, 30, 2);
    when(mockTransactionService.getLastMinuteTransactionStats())
        .thenReturn(CompletableFuture.completedFuture(statistics));
    ResponseEntity<Statistics> response = new ResponseEntity<Statistics>(statistics, HttpStatus.OK);

    ResponseEntity<Statistics> actualResponse = cut.statistics();
    assertEquals(response.getStatusCode().value(), actualResponse.getStatusCode().value(), 0);

    assertEquals(response.getBody().getAvg(), actualResponse.getBody().getAvg(), 0);
    assertEquals(response.getBody().getCount(), actualResponse.getBody().getCount(), 0);
    verify(mockTransactionService).getLastMinuteTransactionStats();
    verifyNoMoreInteractions(mockTransactionService);
  }

  private Transaction dummyTransaction(double amount) {
    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setTimestamp(System.currentTimeMillis());
    return transaction;
  }
}
