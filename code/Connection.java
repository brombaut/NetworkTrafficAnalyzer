public class Connection {
  String time;
  String sourceIP;
  String destIP;
  String sourcePort;
  String destPort;
  String protocol;

  public Connection(String timeIn, String sourceIPIn, String destIPIn, String sourcePortIn, String destPortIn, String protocolIn) {
    time = timeIn;
    sourceIP = sourceIPIn;
    destIP = destIPIn;
    sourcePort = sourcePortIn;
    destPort = destPortIn;
    protocol = protocolIn;
  }

  public void printConnectionInformation() {
    System.out.println("Time:" + time + "\tProtocol:" + protocol + "\t Source:" + sourceIP + ":" + sourcePort + " \tDest:" + destIP + ":" + destPort);
  }

}
