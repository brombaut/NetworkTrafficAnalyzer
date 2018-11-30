import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NetworkTrafficAnalyzer {
  ConnectionBuilder cb = new ConnectionBuilder();
  ConnectionListManager clm = new ConnectionListManager();

  int count = 0;
  public static void main(String[] args) {
    NetworkTrafficAnalyzer nta = new NetworkTrafficAnalyzer();
    nta.createOutputRunnable();
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
        Connection c = cb.buildConnection(line);
        if (c != null) {
          clm.handleNewConnectionLine(c);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void createOutputRunnable() {
    Runnable printRunnable = new Runnable() {
      public void run() {
        clm.printNumberOfOpenConnectionsForProtocolInPastSeconds("TCP", 30);
      }
    };

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    executor.scheduleAtFixedRate(printRunnable, 5, 5, TimeUnit.SECONDS);
  }

  public String[] getShellCommand() {
    /**
    tcpdump -s 0 == Capture all bytes of data within the packet
    tcpdump -s 500 == Capture 500 bytes of data for each packet rather than the default of 68 bytes
    tcpdump -n == Display IP addresses and port numbers instead of domain and service names
                  when capturing packets (note: on some systems you need to specify -nn to display port numbers)
    tcpdump -B 524288
    tcpdump tcp == Capture only TCP packets
    tcpdump udp == Capture only UDP packets
    tcpdump -tt == Print the timestamp, as seconds since January 1, 1970, 00:00:00, UTC,
                   and fractions of a second since that time, on each dump line.

    Flags
      client -> server [S]  'Syn'
      server -> client [S.] 'Syn Ack'
      client -> server [.]  'Ack'
      .
      .
      .
      client -> server [P.] 'Push' ? I dont know
      server -> client [.] 'Ack' ? I dont know
      .
      .
      .
      server -> client [F.]
      client -> server [F.]
      server -> client [.]
      OR
      client -> server [F.]
      server -> client [F.]
      client -> server [.]
    */
    String[] shellCommand = new String[] {
            "/bin/bash",
            "-c",
            "sudo tcpdump -nn -tt",
    };
    return shellCommand;
  }
}
