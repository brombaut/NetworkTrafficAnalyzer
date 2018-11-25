public class Connection {
  String time;
  String protocol;
  String sourceIP;
  String destIP;
  String sourcePort;
  String destPort;
  boolean hasBeenResponded;

  public Connection(String timeIn, String protocolIn, String sourceIPIn, String destIPIn, String sourcePortIn, String destPortIn) {
    time = timeIn;
    protocol = protocolIn;
    sourceIP = sourceIPIn;
    destIP = destIPIn;
    sourcePort = sourcePortIn;
    destPort = destPortIn;
    hasBeenResponded = false;
  }

  public void printConnectionInformation() {
    String respString = hasBeenResponded ? "Responded" : "Not Responded";
    System.out.println("Time:" + time + "\tProtocol:" + protocol + "\t Source:" + sourceIP + ":" + sourcePort + " \tDest:" + destIP + ":" + destPort + " " + respString);
  }

  public String getTime() {
    return time;
  }

  public String getProtocol() {
    return protocol;
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

}
