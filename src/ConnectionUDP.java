public class ConnectionUDP extends Connection {

  public ConnectionUDP(
    String timeIn,
    String protocolMainIn,
    String protocolSubIn,
    String sourceIPIn,
    String destIPIn,
    String sourcePortIn,
    String destPortIn
  ) {
    super(timeIn, protocolMainIn, protocolSubIn, sourceIPIn, destIPIn, sourcePortIn, destPortIn);
  }

  public void printConnectionInformation() {
    System.out.println(getTime() + " " +
      "\t" + getProtocolMain() + " " +
      "\t" + getProtocolSub() + " " +
      "\t" + getSourceIp() + "\t:" + getSourcePort() + " " +
      "\t" + getDestIp() + "\t:" + getDestPort());
  }
}
