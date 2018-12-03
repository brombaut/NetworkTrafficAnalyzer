import java.util.HashMap;
import java.util.ArrayList;

public class ConnectionHashMapIP{

  HashMap<String, ConnectionIPObject> mHashMap;

  public ConnectionHashMapIP() {
    mHashMap = new HashMap<String, ConnectionIPObject>();
  }

  public void createHM(ArrayList<Connection> connectionsAL) {
    for(int i=0; i<connectionsAL.size(); i++){
      Connection cTemp = connectionsAL.get(i);
      String key = createHMKey(cTemp);
      if (mHashMap.get(key) == null) {
        String sourceIP = cTemp.getSourceIp();
        String destIP = cTemp.getDestIp();
        ConnectionIPObject cipo = new ConnectionIPObject(sourceIP, destIP);
        mHashMap.put(key, cipo);
      }

      if (cTemp.getFlagStatus().contains("S") || cTemp.getFlagStatus().contains("O")) {
        mHashMap.get(key).addOpen();
        mHashMap.get(key).addConnectedSourcePort(cTemp.getSourcePort());
      }

      if (cTemp.getFlagStatus().contains("F")) {
        mHashMap.get(key).addClose();
        mHashMap.get(key).addClosedSourcePort(cTemp.getSourcePort());
      }

      if (cTemp.getFlagStatus().contains("R")) {
        mHashMap.get(key).addFail();
        mHashMap.get(key).addFailedSourcePort(cTemp.getSourcePort());
      }

    }
  }

  public String createHMKey(Connection c) {
    return c.getSourceIp() + "-" + c.getDestIp();
  }

  public void resetHM() {
    mHashMap.clear();
  }

  public void printHMConnectionData() {
    String restoreColorCode = "\033[31;44;0m";
    String addColorCode = "\033[31;44;1m";
    System.out.println();
    System.out.printf("%18s -> %18s %8s %8s %8s %30s %30s %30s\n",
      "Client",
      "Server",
      "OPEN",
      "CLOSED",
      "FAILED",
      "CONNECTED PORTS",
      "CLOSED PORTS",
      "FAILED PORTS"
    );
    mHashMap.forEach((key, connIPObj) -> {
      if(connIPObj.getOpen() > 10 || connIPObj.getFail() > 10) {
        System.out.printf(addColorCode + "%18s -> %18s %8s %8s %8s %30s %30s %30s\n",
          connIPObj.getSource(),
          connIPObj.getDest(),
          connIPObj.getOpen(),
          connIPObj.getClose(),
          connIPObj.getFail(),
          connIPObj.getConnectedSourcePorts(),
          connIPObj.getClosedSourcePorts(),
          connIPObj.getFailedSourcePorts()
        );
      } else {
        System.out.printf(restoreColorCode + "%18s -> %18s %8s %8s %8s %30s %30s %30s\n",
          connIPObj.getSource(),
          connIPObj.getDest(),
          connIPObj.getOpen(),
          connIPObj.getClose(),
          connIPObj.getFail(),
          connIPObj.getConnectedSourcePorts(),
          connIPObj.getClosedSourcePorts(),
          connIPObj.getFailedSourcePorts()
        );
      }
    });
    System.out.printf(restoreColorCode);
  }

}