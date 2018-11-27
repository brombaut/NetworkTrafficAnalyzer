public class ConnectionListManager{

  ConnectionsArrayList cal;

  public ConnectionListManager() {
    cal = new ConnectionsArrayList();
  }

  public void handleNewConnectionLine(Connection c) {
    int connIndex = checkIfOpenConnectionExists(c);
    if (connIndex > 0) {
      cal.setConnectionIndexResponded(connIndex);
    } else {
      cal.addConnection(c);
    }
  }

  public int checkIfOpenConnectionExists(Connection c) {
    for(int i=0; i<cal.getConnectionsList().size(); i++){
      Connection cTemp = cal.getConnectionsList().get(i);
      if (!cTemp.getHasBeenResponded() && checkIfResponse(c, cTemp)) {
        // System.out.print("FOUND: ");
        // c.printConnectionInformation();
        return i;
      }
    }
    return -1;
  }

  public boolean checkIfResponse(Connection c1, Connection c2) {
    return c1.getSourceIp().equals(c2.getDestIp())
      && c1.getSourcePort().equals(c2.getDestPort())
      && c1.getDestIp().equals(c2.getSourceIp())
      && c1.getDestPort().equals(c2.getSourcePort());
  }

  public void printConnectionsForProtocol(String protocolIn) {
    switch (protocolIn) {
      case ("TCP"): {
        cal.printTcpConnection();
        break;
      }
      default:
        cal.printAllConnections();
    }
  }


}
