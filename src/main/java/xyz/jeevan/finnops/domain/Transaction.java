package xyz.jeevan.finnops.domain;

import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Transaction {

  private String id;

  @NotNull
  @Min(value = 1, message = "Transfer amount should be greater than zero.")
  private Double amount;

  @NotNull
  private Long timestamp;

  public Transaction() {
    this.id = UUID.randomUUID().toString();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * Method to check if transaction is older than give time.
   * 
   * @param timeToCompareWith Time to compare with.
   * @return true if old, false otherwise.
   */
  public boolean isOlderThan(long timeToCompareWith) {
    return (this.timestamp < timeToCompareWith);
  }

}
