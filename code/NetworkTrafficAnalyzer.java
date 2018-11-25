import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetworkTrafficAnalyzer {
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
        String[] splitLine = line.split(" ");
        String time = splitLine[0];
        String pro = splitLine[1];
        String sourceIpPort = splitLine[2];
        String destIpPort = splitLine[4];

        //Connection c = new Connection();
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
    String[] shellCommand = new String[] {
            "/bin/bash",
            "-c",
            "tcpdump -nn tcp",
    };
    return shellCommand;
  }
}
