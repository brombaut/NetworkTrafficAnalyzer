import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConnectionListManager{

  ConnectionsArrayList cal;
  ConnectionHashMapIP chm;

  public ConnectionListManager() {
    cal = new ConnectionsArrayList();
    // cleanListRunnable();
    // createOutputRunnable();
    chm = new ConnectionHashMapIP();
  }

  public void createOutputRunnable() {
    Runnable printRunnable = new Runnable() {
      public void run() {
        // clm.printNumberOfOpenConnectionsForProtocolInPastSeconds("TCP", 30);
        printConnectionsForProtocol("TCP");
        System.out.println("\n");
      }
    };

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    executor.scheduleAtFixedRate(printRunnable, 5, 5, TimeUnit.SECONDS);
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
    // System.out.print("NEW: ");
    // c.printConnectionInformation();
    String flagString = c.getFlagStatus();
    if (flagString.equals("S")) {
      cal.addConnection(c);
      // System.out.print("ADDING: ");
      // c.printConnectionInformation();
    } else if(flagString.equals("S.")) {
      cal.setSynAck(c);
      // System.out.print("SYNACK: ");
      // c.printConnectionInformation();
    } else if(flagString.contains("F") && flagString.contains(".")) {
      cal.setFinishSynAck(c);
      // System.out.print("FINACK: ");
      // c.printConnectionInformation();
    } else if(flagString.contains(".")) {
      cal.handleAckFlag(c);
      // System.out.print("ACK: ");
      // c.printConnectionInformation();
    } else {
      cal.handleOtherFlags(c);
    }
    // System.out.print("\033[H\033[2J");
    // System.out.println("\n");
    // System.out.print("CONN: ");
    // c.printConnectionInformation();
    // printConnectionsForProtocol("TCP");
    printFullUpdate();

  }

  public void printFullUpdate() {
    System.out.print("\033[H\033[2J");
    chm.resetHM();
    chm.createHM(cal.getConnectionsListBySubProtocol("TCP"));
    chm.printHMConnectionData();
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
