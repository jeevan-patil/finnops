package xyz.jeevan.finnops.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import xyz.jeevan.finnops.domain.Transaction;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class TransactionRepositoryImplTest {

  @InjectMocks
  @Spy
  private TransactionRepositoryImpl cut;

  @Test
  public void create() {
    // prepare mock data
    Transaction transaction = dummyTransaction(221d);
    cut.create(transaction);
  }

  private Transaction dummyTransaction(double amount) {
    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setTime(System.currentTimeMillis());
    return transaction;
  }

}
