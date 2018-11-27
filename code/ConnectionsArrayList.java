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

  public void setConnectionIndexResponded(int i) {
    connections.get(i).setHasBeenResponded(true);
  }
}
