import java.util.ArrayList;

public class ConnectionIPObject{
  int open, close, fail;
  String source, dest;
  ArrayList<String> connectedSourcePorts, closedSourcePorts, failedSourcePorts;
  public ConnectionIPObject(String sourceIn, String destIn) {
    open = 0;
    close = 0;
    fail = 0;
    source = sourceIn;
    dest = destIn;
    connectedSourcePorts = new ArrayList<String>();
    closedSourcePorts = new ArrayList<String>();
    failedSourcePorts = new ArrayList<String>();
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
