import java.util.ArrayList;

public class ConnectionsArrayList{

  ArrayList<Connection> connections;

  public ConnectionsArrayList() {
    connections = new ArrayList<Connection>();
  }

  public void addConnection(Connection c) {
    connections.add(c);
  }

  public void printAllConnections() {
    for(int i=0; i<connections.size(); i++){
      connections.get(i).printConnectionInformation();
    }
  }
}
