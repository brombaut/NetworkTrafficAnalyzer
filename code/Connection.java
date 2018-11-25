public class Connection {
  String time;
  String protocol;
  String sourceIP;
  String destIP;
  String sourcePort;
  String destPort;

  public Connection(String timeIn, String protocolIn, String sourceIPIn, String destIPIn, String sourcePortIn, String destPortIn) {
    time = timeIn;
    protocol = protocolIn;
    sourceIP = sourceIPIn;
    destIP = destIPIn;
    sourcePort = sourcePortIn;
    destPort = destPortIn;
  }

  public void printConnectionInformation() {
    System.out.println("Time:" + time + "\tProtocol:" + protocol + "\t Source:" + sourceIP + ":" + sourcePort + " \tDest:" + destIP + ":" + destPort);
  }

}
