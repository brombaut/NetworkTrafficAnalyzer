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

  public int checkIfOpenConnectionExists(Connection c) {
    for(int i=0; i<connections.size(); i++){
      Connection cTemp = connections.get(i);
      if (!cTemp.getHasBeenResponded() && checkIfResponse(c, cTemp)) {
        System.out.print("FOUND: ");
        c.printConnectionInformation();
        return i;
      }
    }
    return -1;
  }

  public void setConnectionIndexResponded(int i) {
    connections.get(i).setHasBeenResponded(true);
  }

  public boolean checkIfResponse(Connection c1, Connection c2) {
    return c1.getSourceIp().equals(c2.getDestIp())
      && c1.getSourcePort().equals(c2.getDestPort())
      && c1.getDestIp().equals(c2.getSourceIp())
      && c1.getDestPort().equals(c2.getSourcePort());
  }
}
