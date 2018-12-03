import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.text.DecimalFormat;

public class ConnectionHashMapIP{

  HashMap<String, ConnectionIPObject> mHashMap;
  HashMap<Integer, ConnectionPerSecond> avgConnPerSec;

  public ConnectionHashMapIP() {
    mHashMap = new HashMap<String, ConnectionIPObject>();
    avgConnPerSec = new HashMap<Integer, ConnectionPerSecond>();
  }

  public void createHM(ArrayList<Connection> connectionsAL) {
    for(int i=0; i<connectionsAL.size(); i++){
      Connection cTemp = connectionsAL.get(i);
      String key = createHMKey(cTemp);
      if (mHashMap.get(key) == null) {
        String sourceIP = cTemp.getSourceIp();
        String destIP = cTemp.getDestIp();
        String time = cTemp.getTime();
        ConnectionIPObject cipo = new ConnectionIPObject(sourceIP, destIP, time);
        mHashMap.put(key, cipo);
      }

      if (cTemp.getFlagStatus().contains("S")) {
        mHashMap.get(key).addHand();
        mHashMap.get(key).addHandSourcePort(cTemp.getSourcePort());

        Integer avgConnPerSecKey = cTemp.getTimeSecondsInt();
        if (avgConnPerSec.get(avgConnPerSecKey) == null) {
          avgConnPerSec.put(avgConnPerSecKey, new ConnectionPerSecond());
        }
        avgConnPerSec.get(avgConnPerSecKey).incrementOpenPerSecond();
      }

      if (cTemp.getFlagStatus().contains("O")) {
        mHashMap.get(key).addOpen();
        mHashMap.get(key).addConnectedSourcePort(cTemp.getSourcePort());

        Integer avgConnPerSecKey = cTemp.getTimeSecondsInt();
        if (avgConnPerSec.get(avgConnPerSecKey) == null) {
          avgConnPerSec.put(avgConnPerSecKey, new ConnectionPerSecond());
        }
        avgConnPerSec.get(avgConnPerSecKey).incrementOpenPerSecond();
      }

      if (cTemp.getFlagStatus().contains("F")) {
        mHashMap.get(key).addClose();
        mHashMap.get(key).addClosedSourcePort(cTemp.getSourcePort());
      }

      if (cTemp.getFlagStatus().contains("R")) {
        mHashMap.get(key).addFail();
        mHashMap.get(key).addFailedSourcePort(cTemp.getSourcePort());

        Integer avgConnPerSecKey = cTemp.getTimeSecondsInt();
        if (avgConnPerSec.get(avgConnPerSecKey) == null) {
          avgConnPerSec.put(avgConnPerSecKey, new ConnectionPerSecond());
        }
        avgConnPerSec.get(avgConnPerSecKey).incrementFailPerSecond();
      }

      if (mHashMap.get(key).getTime().compareTo(cTemp.getTime()) > 0) {
      }
    }
  }

  public String createHMKey(Connection c) {
    return c.getSourceIp() + "-" + c.getDestIp();
  }

  public void resetHM() {
    mHashMap.clear();
    avgConnPerSec.clear();
  }

  public void printHMConnectionData() {
    String restoreColorCode = "\033[31;44;0m";
    String addColorCode = "\033[31;44;1m";
    System.out.println("NETWORK TRAFFIC FROM PAST 60 SECONDS");
    System.out.printf("%18s %15s -> %15s %4s %4s %5s %4s %25s %25s %25s %25s\n",
      "Time",
      "Client",
      "Server",
      "HAND",
      "OPEN",
      "CLOSE",
      "FAIL",
      "HAND PORTS",
      "CONNECTED PORTS",
      "CLOSED PORTS",
      "FAILED PORTS"
    );

    mHashMap.forEach((key, connIPObj) -> {
      String handPorts = "[";
      for (int i=0; i<connIPObj.getHandSourcePorts().size(); i++) {
        if (i > 3) {
          handPorts += "..";
          break;
        }
        handPorts += connIPObj.getHandSourcePorts().get(i);
        if(i != connIPObj.getHandSourcePorts().size()-1) {
          handPorts += ",";
        }
      }
      handPorts += "]";

      String connectedPorts = "[";
      for (int i=0; i<connIPObj.getConnectedSourcePorts().size(); i++) {
        if (i > 3) {
          connectedPorts += "..";
          break;
        }
        connectedPorts += connIPObj.getConnectedSourcePorts().get(i);
        if(i != connIPObj.getConnectedSourcePorts().size()-1) {
          connectedPorts += ",";
        }
      }
      connectedPorts += "]";

      String closedPorts = "[";
      for (int i=0; i<connIPObj.getClosedSourcePorts().size(); i++) {
        if (i > 3) {
          closedPorts += "..";
          break;
        }
        closedPorts += connIPObj.getClosedSourcePorts().get(i);
        if(i != connIPObj.getClosedSourcePorts().size()-1) {
          closedPorts += ",";
        }
      }
      closedPorts += "]";

      String failedPorts = "[";
      for (int i=0; i<connIPObj.getFailedSourcePorts().size(); i++) {
        if (i > 3) {
          failedPorts += "..";
          break;
        }
        failedPorts += connIPObj.getFailedSourcePorts().get(i);
        if(i != connIPObj.getFailedSourcePorts().size()-1) {
          failedPorts += ",";
        }
      }
      failedPorts += "]";

      String secMilString = connIPObj.getTime();
      String formattedDate = secMilString;

      if(connIPObj.getOpen() > 10 || connIPObj.getFail() > 10) {
        System.out.printf(addColorCode + "%18s %15s -> %15s %4s %4s %5s %4s %25s %25s %25s %25s\n",
          formattedDate,
          connIPObj.getSource(),
          connIPObj.getDest(),
          connIPObj.getHand(),
          connIPObj.getOpen(),
          connIPObj.getClose(),
          connIPObj.getFail(),
          handPorts,
          connectedPorts,
          closedPorts,
          failedPorts
        );
      } else {
        System.out.printf(restoreColorCode + "%18s %15s -> %15s %4s %4s %5s %4s %25s %25s %25s %25s\n",
          formattedDate,
          connIPObj.getSource(),
          connIPObj.getDest(),
          connIPObj.getHand(),
          connIPObj.getOpen(),
          connIPObj.getClose(),
          connIPObj.getFail(),
          handPorts,
          connectedPorts,
          closedPorts,
          failedPorts
        );
      }
    });
    System.out.printf(restoreColorCode);

    int totalOpen = 0;
    int totalFail = 0;
    int oldestTime = (int)(System.currentTimeMillis() / 1000);

    for (Map.Entry<Integer, ConnectionPerSecond> entry : avgConnPerSec.entrySet()) {
        if(oldestTime > entry.getKey()) {
          oldestTime = entry.getKey();
        }
        totalOpen += entry.getValue().getOpenPerSecond();
        totalFail += entry.getValue().getFailPerSecond();
		}
    int totalSec = ((int)(System.currentTimeMillis() / 1000)) - oldestTime;
    if(totalSec == 0) {
      totalSec = 1;
    }
    double avgOpen = (double)totalOpen/(double)totalSec;
    double avgFail = (double)totalFail/(double)totalSec;
    DecimalFormat df = new DecimalFormat("#.00");

    System.out.printf("\n%33s  | %33s\n",
     "AVG OPEN CONNECTIONS PER SECOND",
     "AVG FAIL CONNECTIONS PER SECOND"
    );
    if (avgOpen > 10) {
      System.out.printf(addColorCode + "%33s",
        df.format(avgOpen)
      );
    } else {
      System.out.printf("%33s",
        df.format(avgOpen)
      );
    }
    System.out.printf(restoreColorCode);

    if (avgFail > 10) {
      System.out.printf(addColorCode + "%33s",
        df.format(avgFail)
      );
    } else {
      System.out.printf("%33s",
        df.format(avgFail)
      );
    }
    System.out.printf(restoreColorCode);
  }
}
