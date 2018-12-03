public class ConnectionTCP extends Connection {

  String flagStatus;

  public ConnectionTCP(
    String timeIn,
    String protocolMainIn,
    String protocolSubIn,
    String sourceIPIn,
    String destIPIn,
    String sourcePortIn,
    String destPortIn,
    String flagStatusIn
  ) {
    super(timeIn, protocolMainIn, protocolSubIn, sourceIPIn, destIPIn, sourcePortIn, destPortIn);
    flagStatus = flagStatusIn;
  }

  public String getFlagStatus() {
    return flagStatus;
  }

  public void setFlagStatus(String flagStatusIn) {
    flagStatus = flagStatusIn;
  }

  public void printConnectionInformation() {
    System.out.println(getTime() + " " +
      " " + getProtocolMain() + " " +
      " " + getProtocolSub() + " " +
      "\t" + getSourceIp() + "\t:" + getSourcePort() + " " +
      "\t" + getDestIp() + "\t:" + getDestPort() + " " +
      "\t" + flagStatus);
  }
}
