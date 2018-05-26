package xyz.jeevan.finnops.domain;

public class Statistics {
  private double sum;
  private double avg;
  private double max;
  private double min;
  private long count;

  public Statistics(double sum, double avg, double max, double min, long count) {
    super();
    this.sum = sum;
    this.avg = avg;
    this.max = max;
    this.min = min;
    this.count = count;
  }

  public double getSum() {
    return sum;
  }

  public double getAvg() {
    return avg;
  }

  public double getMax() {
    return max;
  }

  public double getMin() {
    return min;
  }


  public long getCount() {
    return count;
  }

  @Override
  public String toString() {
    return "Statistics [sum=" + sum + ", avg=" + avg + ", max=" + max + ", min=" + min + ", count="
        + count + "]";
  }
  
  

}
