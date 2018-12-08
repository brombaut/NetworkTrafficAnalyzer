public abstract class Connection {
  private String time;
  private String protocolMain;
  private String protocolSub;
  private String sourceIP;
  private String destIP;
  private String sourcePort;
  private String destPort;

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
  }

  public String getTime() {
    return time;
  }

  public void setTime(String timeIn) {
    time = timeIn;
  }

  public int getTimeSecondsInt() {
    String secString = time.substring(0, time.indexOf("."));
    return Integer.parseInt(secString);
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

  public abstract void printConnectionInformation();

  public String getFlagStatus() { return null; }

  public void setFlagStatus(String flagStatusIn) { }

}
