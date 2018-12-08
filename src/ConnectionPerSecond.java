public class ConnectionPerSecond{
  int openPerSecond, failPerSecond;

  public ConnectionPerSecond() {
    openPerSecond = 0;
    failPerSecond = 0;
  }

  public int getOpenPerSecond() {
    return openPerSecond;
  }

  public int getFailPerSecond() {
    return failPerSecond;
  }

  public void incrementOpenPerSecond() {
    openPerSecond++;
  }

  public void incrementFailPerSecond() {
    failPerSecond++;
  }
}
