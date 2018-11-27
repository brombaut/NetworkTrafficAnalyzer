public abstract class Connection {
  private String time;
  private String protocolMain;
  private String protocolSub;
  private String sourceIP;
  private String destIP;
  private String sourcePort;
  private String destPort;
  private boolean hasBeenResponded;

  public Connection(
    String timeIn,
    String protocolMainIn,
    String protocolSubIn,
    String sourceIPIn,
    String destIPIn,
    String sourcePortIn,
    String destPortIn
  ) {
    time = timeIn;
    protocolMain = protocolMainIn;
    protocolSub = protocolSubIn;
    sourceIP = sourceIPIn;
    destIP = destIPIn;
    sourcePort = sourcePortIn;
    destPort = destPortIn;
    hasBeenResponded = false;
  }

  public String getTime() {
    return time;
  }

  public String getProtocolMain() {
    return protocolMain;
  }

  public String getProtocolSub() {
    return protocolSub;
  }

  public String getSourceIp() {
    return sourceIP;
  }

  public String getSourcePort() {
    return sourcePort;
  }

  public String getDestIp() {
    return destIP;
  }

  public String getDestPort() {
    return destPort;
  }

  public boolean getHasBeenResponded() {
    return hasBeenResponded;
  }

  public void setHasBeenResponded(boolean val) {
    hasBeenResponded = val;
  }
  public abstract void printConnectionInformation();

}
