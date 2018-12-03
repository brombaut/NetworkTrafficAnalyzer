import java.util.ArrayList;

public class ConnectionsArrayList{

  ArrayList<Connection> connections;

  public ConnectionsArrayList() {
    connections = new ArrayList<Connection>();
  }

  public ArrayList<Connection> getConnectionsList() {
    return connections;
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

  public ArrayList<Connection> getConnectionsListByProtocolInPastSeconds(String protocolIn, int seconds) {
    ArrayList<Connection> retArr = new ArrayList<Connection>();
    ArrayList<Connection> subProcCons= getConnectionsListBySubProtocol(protocolIn);
    for (int i=0; i<subProcCons.size(); i++) {
      long currTime = System.currentTimeMillis() / 1000;
      int connTime = subProcCons.get(i).getTimeSecondsInt();
      if(currTime - seconds - connTime < 0) {
        retArr.add(subProcCons.get(i));
      }
    }
    return retArr;
  }

  public void addConnection(Connection c) {
    connections.add(c);
  }

  public void setSynAck(Connection c) {
    for(int i=0; i<connections.size(); i++){
      if (checkIfRespondingConnection(c, connections.get(i))) {
        connections.get(i).setFlagStatus("S.");
        connections.get(i).setTime(c.getTime());
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
            connections.get(i).setTime(c.getTime());
            break;
          case ("F."):
            connections.get(i).setFlagStatus("F");
            connections.get(i).setTime(c.getTime());
            break;
        }
      }
      if (checkIfRespondingConnection(c, connections.get(i))) {
        cTemp = connections.get(i);
        switch (cTemp.getFlagStatus()) {
          case ("F."):
            connections.get(i).setFlagStatus("F");
            connections.get(i).setTime(c.getTime());
            break;
        }
      }
    }
  }

  public void handleResetFlag(Connection c) {
    Connection cTemp;
    for(int i=0; i<connections.size(); i++){
      if (checkIfRespondingConnection(c, connections.get(i))) {
        cTemp = connections.get(i);
        switch (cTemp.getFlagStatus()) {
          case ("S"):
            connections.get(i).setFlagStatus("R");
            connections.get(i).setTime(c.getTime());
            break;
          case ("F"):
            connections.get(i).setFlagStatus("F");
            connections.get(i).setTime(c.getTime());
            break;
          case ("F."):
            connections.get(i).setFlagStatus("F");
            connections.get(i).setTime(c.getTime());
            break;
          default:
            connections.get(i).setFlagStatus("R");
            connections.get(i).setTime(c.getTime());
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
            connections.get(i).setTime(c.getTime());
            break;
        }
      }
      if (checkIfRespondingConnection(c, connections.get(i))) {
        cTemp = connections.get(i);
        switch (cTemp.getFlagStatus()) {
          case ("O"):
            connections.get(i).setFlagStatus("F.");
            connections.get(i).setTime(c.getTime());
            break;
        }
      }
    }
  }

  public void handleOtherFlags(Connection c) {

  }

  public boolean checkIfRespondingConnection(Connection c1, Connection c2){
    return c1.getSourceIp().equals(c2.getDestIp())
      && c1.getSourcePort().equals(c2.getDestPort())
      && c1.getDestIp().equals(c2.getSourceIp())
      && c1.getDestPort().equals(c2.getSourcePort())
      && !(c2.getFlagStatus().equals("F") || c2.getFlagStatus().equals("R"));
  }

  public boolean checkIfSameConnection(Connection c1, Connection c2){
    return c1.getSourceIp().equals(c2.getSourceIp())
      && c1.getSourcePort().equals(c2.getSourcePort())
      && c1.getDestIp().equals(c2.getDestIp())
      && c1.getDestPort().equals(c2.getDestPort())
      && !(c2.getFlagStatus().equals("F") || c2.getFlagStatus().equals("R"));
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
