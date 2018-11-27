import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConnectionListManager{

  ConnectionsArrayList cal;

  public ConnectionListManager() {
    cal = new ConnectionsArrayList();
    cleanListRunnable();
  }

  public void cleanListRunnable() {
    Runnable cleanRunnable = new Runnable() {
      public void run() {
        cal.removeOldConnections();
      }
    };
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    executor.scheduleAtFixedRate(cleanRunnable, 30, 30, TimeUnit.SECONDS);
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

  public void printNumberOfOpenConnectionsForProtocolInPastSeconds(String protocolIn, int seconds) {
    ArrayList<Connection> subProcCons= cal.getConnectionsListBySubProtocol(protocolIn);
    int count = 0;
    for (int i=0; i<subProcCons.size(); i++) {
      if (!subProcCons.get(i).getHasBeenResponded()) {
        long currTime = System.currentTimeMillis() / 1000;
        int connTime = subProcCons.get(i).getTimeSecondsInt();
        if(currTime - seconds - connTime < 0) {
          count++;
        }
      }
    }
    System.out.println("Number of Open " + protocolIn + " Connections in Past " + seconds + " Seconds: " + count);
  }


}
