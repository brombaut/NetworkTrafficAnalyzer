import java.util.ArrayList;

public class ConnectionIPObject{
  int hand, open, close, fail;
  String source, dest, time;
  ArrayList<String> handSourcePorts, connectedSourcePorts, closedSourcePorts, failedSourcePorts;
  public ConnectionIPObject(String sourceIn, String destIn, String timeIn) {
    hand = 0;
    open = 0;
    close = 0;
    fail = 0;
    source = sourceIn;
    dest = destIn;
    handSourcePorts = new ArrayList<String>();
    connectedSourcePorts = new ArrayList<String>();
    closedSourcePorts = new ArrayList<String>();
    failedSourcePorts = new ArrayList<String>();
    time = timeIn;
  }

  public void addHand() {
    hand++;
  }

  public void addOpen() {
    open++;
  }

  public void addClose() {
    close++;
  }

  public void addFail() {
    fail++;
  }

  public int getHand() {
    return hand;
  }

  public int getOpen() {
    return open;
  }

  public int getClose() {
    return close;
  }

  public int getFail() {
    return fail;
  }

  public String getSource() {
    return source;
  }

  public String getDest() {
    return dest;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String timeIn) {
    time = timeIn;
  }

  public void addHandSourcePort(String port) {
    handSourcePorts.add(port);
  }

  public ArrayList<String> getHandSourcePorts() {
    return handSourcePorts;
  }

  public void addConnectedSourcePort(String port) {
    connectedSourcePorts.add(port);
  }

  public ArrayList<String> getConnectedSourcePorts() {
    return connectedSourcePorts;
  }

  public void addClosedSourcePort(String port) {
    closedSourcePorts.add(port);
  }

  public ArrayList<String> getClosedSourcePorts() {
    return closedSourcePorts;
  }

  public void addFailedSourcePort(String port) {
    failedSourcePorts.add(port);
  }

  public ArrayList<String> getFailedSourcePorts() {
    return failedSourcePorts;
  }
}
