package xyz.jeevan.finnops.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;

public class TransactionTest {

  @Test
  public void testIdGeneration() {
    Transaction transaction = new Transaction();
    assertNotNull(transaction.getId());
  }

  @Test
  public void isOlderThan_success() {
    Transaction transaction = new Transaction();
    transaction.setTime(System.currentTimeMillis());
    long sixtySecondsAgo = System.currentTimeMillis() - 60 * 1000;
    assertFalse(transaction.isOlderThan(sixtySecondsAgo));
  }

  @Test
  public void isOlderFailure() {
    Transaction transaction = new Transaction();
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MINUTE, -2);
    long twoMinutesAgo = calendar.getTimeInMillis();

    transaction.setTime(twoMinutesAgo);
    long sixtySecondsAgo = System.currentTimeMillis() - 60 * 1000;
    assertTrue(transaction.isOlderThan(sixtySecondsAgo));
  }
}
