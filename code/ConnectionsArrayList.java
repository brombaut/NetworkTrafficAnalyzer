import java.util.ArrayList;

public class ConnectionsArrayList{

  ArrayList<Connection> connections;

  public ConnectionsArrayList() {
    connections = new ArrayList<Connection>();
  }

  public ArrayList<Connection> getConnectionsList() {
    return connections;
  }

  public void addConnection(Connection c) {
    connections.add(c);
  }

  public void setSynAck(Connection c) {
    for(int i=0; i<connections.size(); i++){
      if (checkIfRespondingConnection(c, connections.get(i))) {
        connections.get(i).setFlagStatus("S.");
      }
    }
  }

  public void handleAckFlag(Connection c) {
    Connection cTemp;
    for(int i=0; i<connections.size(); i++){

      if (checkIfSameConnection(c, connections.get(i))) {
        cTemp = connections.get(i);
        switch (cTemp.getFlagStatus()) {
          case "S.":
            connections.get(i).setFlagStatus("O");
            break;
          case ("F."):
            connections.get(i).setFlagStatus("F");
            break;
          default:
        }
      }

      if (checkIfRespondingConnection(c, connections.get(i))) {
        cTemp = connections.get(i);
        switch (cTemp.getFlagStatus()) {
          case ("F."):
            connections.get(i).setFlagStatus("F");
            break;
          default:
        }
      }

    }
  }

  public void setFinishSynAck(Connection c) {
    Connection cTemp;
    for(int i=0; i<connections.size(); i++){
      if (checkIfSameConnection(c, connections.get(i))) {
        cTemp = connections.get(i);
        switch (cTemp.getFlagStatus()) {
          case ("O"):
            connections.get(i).setFlagStatus("F.");
            break;
          default:
        }
      }
      if (checkIfRespondingConnection(c, connections.get(i))) {
        cTemp = connections.get(i);
        switch (cTemp.getFlagStatus()) {
          case ("O"):
            connections.get(i).setFlagStatus("F.");
            break;
          default:
        }
      }

    }
  }

  public void setFinishAck(Connection c) {

  }

  public void handleOtherFlags(Connection c) {

  }
  
  public boolean checkIfRespondingConnection(Connection c1, Connection c2){
    return c1.getSourceIp().equals(c2.getDestIp())
      && c1.getSourcePort().equals(c2.getDestPort())
      && c1.getDestIp().equals(c2.getSourceIp())
      && c1.getDestPort().equals(c2.getSourcePort());
  }

  public boolean checkIfSameConnection(Connection c1, Connection c2){
    return c1.getSourceIp().equals(c2.getSourceIp())
      && c1.getSourcePort().equals(c2.getSourcePort())
      && c1.getDestIp().equals(c2.getDestIp())
      && c1.getDestPort().equals(c2.getDestPort());
  }

  public ArrayList<Connection> getConnectionsListBySubProtocol(String subProtocolIn) {
    ArrayList<Connection> subProcConns = new ArrayList<Connection>();
    for(int i=0; i<connections.size(); i++){
      if (connections.get(i).getProtocolSub().equals(subProtocolIn)) {
        subProcConns.add(connections.get(i));
      }
    }
    return subProcConns;
  }

  public void printAllConnections() {
    for(int i=0; i<connections.size(); i++){
      connections.get(i).printConnectionInformation();
    }
  }

  public void printTcpConnection() {
    for (int i=0; i<connections.size(); i++) {
      if (connections.get(i).getProtocolSub().equals("TCP")) {
        connections.get(i).printConnectionInformation();
      }
    }
  }

  public void removeOldConnections() {
    long currTime = System.currentTimeMillis() / 1000;
    connections.removeIf(c -> currTime - c.getTimeSecondsInt() > 60);
  }
}
