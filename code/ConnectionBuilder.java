public class ConnectionBuilder{

  public ConnectionBuilder() { }

  public Connection buildConnection(String tcpLine) {
    String time, protocolMain, sourceIpPort, sourceIp, sourcePort, destIpPort, destIp, destPort;
    String[] splitTcpDumpLine = tcpLine.split(" ");
    if(splitTcpDumpLine.length < 2) {
      return null;
    }
    time = splitTcpDumpLine[0];
    protocolMain = splitTcpDumpLine[1];
    if (protocolMain.equals("IP")) {
      sourceIpPort = splitTcpDumpLine[2];
      sourceIp = sourceIpPort.substring(0, sourceIpPort.lastIndexOf("."));
      sourcePort = sourceIpPort.substring(sourceIpPort.lastIndexOf(".")+1);
      destIpPort = splitTcpDumpLine[4];
      destIp = destIpPort.substring(0, destIpPort.lastIndexOf("."));
      destPort = destIpPort.substring(destIpPort.lastIndexOf(".")+1);
      destPort = destPort.substring(0, destPort.length()-1);
      boolean isUDP = false;
      boolean isTCP = false;
      for (String  splitTcpDumpLinValue: splitTcpDumpLine) {
        isUDP = splitTcpDumpLinValue.contains("UDP");
        isTCP = splitTcpDumpLinValue.contains("Flags");
        if (isUDP || isTCP) {
          break;
        }
      }
      if (isUDP) {
        //return new ConnectionUDP(time, protocolMain, "UDP", sourceIp, destIp, sourcePort, destPort);
      }
      if (isTCP) {
        String rawFlagStatus = splitTcpDumpLine[6];
        String flagStatus = rawFlagStatus.replace("[", "").replace("]", "").replace(",", "");
        return new ConnectionTCP(time, protocolMain, "TCP", sourceIp, destIp, sourcePort, destPort, flagStatus);
      }
    }
    return null;
  }
}
