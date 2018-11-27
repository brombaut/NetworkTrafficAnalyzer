import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NetworkTrafficAnalyzer {
  ConnectionsArrayList cal = new ConnectionsArrayList();

  int count = 0;
  public static void main(String[] args) {
    NetworkTrafficAnalyzer nta = new NetworkTrafficAnalyzer();
    nta.run(nta.getShellCommand());
  }

  public void run(String[] command) {
    Process proc = null;
    try {
      System.out.println("Starting...\n");
      proc = new ProcessBuilder(command).start();
      BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      String line;
      while((line = in.readLine()) != null){
        Connection c = null;
        String time, protocolMain, sourceIpPort, sourceIp, sourcePort, destIpPort, destIp, destPort;
        String[] splitTcpDumpLine = line.split(" ");
        time = splitTcpDumpLine[0];
        protocolMain = splitTcpDumpLine[1];
        if (protocolMain.equals("IP")) {
          sourceIpPort = splitTcpDumpLine[2];
          sourceIp = sourceIpPort.substring(0, sourceIpPort.lastIndexOf("."));
          sourcePort = sourceIpPort.substring(sourceIpPort.lastIndexOf(".")+1);
          destIpPort = splitTcpDumpLine[4];
          destIp = destIpPort.substring(0, destIpPort.lastIndexOf("."));
          destPort = destIpPort.substring(destIpPort.lastIndexOf(".")+1);
          destPort = destPort.substring(0, destPort.length()-1);
          boolean isUDP = false;
          boolean isTCP = false;
          for (String  splitTcpDumpLinValue: splitTcpDumpLine) {
            isUDP = splitTcpDumpLinValue.contains("UDP");
            isTCP = splitTcpDumpLinValue.contains("Flags");
            if (isUDP || isTCP) {
              break;
            }
          }
          if (isUDP) {
            c = new ConnectionUDP(time, protocolMain, "UDP", sourceIp, destIp, sourcePort, destPort);
          }
          if (isTCP) {
            c = new ConnectionTCP(time, protocolMain, "TCP", sourceIp, destIp, sourcePort, destPort);
          }
        }
        if (c != null) {
          int connIndex = cal.checkIfOpenConnectionExists(c);
          if (connIndex > 0) {
            cal.setConnectionIndexResponded(connIndex);
          } else {
            cal.addConnection(c);
          }
          count++;
          if(count > 200) {
            cal.printTcpConnection();
            System.exit(0);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String[] getShellCommand() {
    // tcpdump -s 0 == Capture all bytes of data within the packet
    // tcpdump -s 500 == Capture 500 bytes of data for each packet rather than the default of 68 bytes
    // tcpdump -n == Display IP addresses and port numbers instead of domain and service names when capturing packets (note: on some systems you need to specify -nn to display port numbers)
    // tcpdump -B 524288
    // tcpdump tcp == Capture only TCP packets
    // tcpdump udp == Capture only UDP packets
    // tcpdump -tt == Print the timestamp, as seconds since January 1, 1970, 00:00:00, UTC, and fractions of a second since that time, on each dump line.
    String[] shellCommand = new String[] {
            "/bin/bash",
            "-c",
            "sudo tcpdump -nn -tt",
    };
    return shellCommand;
  }
}
