import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConnectionListManager{

  ConnectionsArrayList cal;
  ConnectionHashMapIP chm;

  public ConnectionListManager() {
    cal = new ConnectionsArrayList();
    cleanListRunnable();
    createOutputRunnable();
    chm = new ConnectionHashMapIP();
  }

  public void createOutputRunnable() {
    Runnable printRunnable = new Runnable() {
      public void run() {
        printFullUpdate();
      }
    };

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    executor.scheduleAtFixedRate(printRunnable, 1, 1, TimeUnit.SECONDS);
  }

  public void cleanListRunnable() {
    Runnable cleanRunnable = new Runnable() {
      public void run() {
        cal.removeOldConnections();
      }
    };
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    executor.scheduleAtFixedRate(cleanRunnable, 180, 180, TimeUnit.SECONDS);
  }

  public void handleNewConnectionLine(Connection c) {
    String flagString = c.getFlagStatus();
    if (flagString.equals("S")) {
      cal.addConnection(c);
    } else if(flagString.equals("S.")) {
      cal.setSynAck(c);
    } else if(flagString.contains("F")) {
      cal.setFinishSynAck(c);
    } else if(flagString.contains("R")) {
      cal.handleResetFlag(c);
    } else if(flagString.contains(".")) {
      cal.handleAckFlag(c);
    } else {
      cal.handleOtherFlags(c);
    }
  }

  public void printFullUpdate() {
    System.out.print("\033[H\033[2J");
    chm.resetHM();
    chm.createHM(cal.getConnectionsListByProtocolInPastSeconds("TCP", 60));
    chm.printHMConnectionData();
  }

}
