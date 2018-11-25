import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetworkTrafficAnalyzer {
    public static void main(String[] args) {
        Process proc = null;
        int count = 0;

        // tcpdump -s 0 == Capture all bytes of data within the packet
        // tcpdump -s 500 == Capture 500 bytes of data for each packet rather than the default of 68 bytes
        // tcpdump -n == Display IP addresses and port numbers instead of domain and service names when capturing packets (note: on some systems you need to specify -nn to display port numbers)
        // tcpdump -B 524288
        // tcpdump tcp == Capture only TCP packets
        // tcpdump udp == Capture only UDP packets
        String[] shellCommandArgs2 = new String[] {
                "/bin/bash",
                "-c",
                "tcpdump -i en0 -nn tcp",
        };
        try {
            System.out.println("Starting...\n");
            //proc = new ProcessBuilder("pwd").start();
            proc = new ProcessBuilder(shellCommandArgs2).start();
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while((line = in.readLine()) != null){
                // System.out.println(line);
                String[] splitLine = line.split(" ");
                String time = splitLine[0];
                String pro = splitLine[1];
                String sourceIpPort = splitLine[2];
                String destIpPort = splitLine[4];
                System.out.println("    ----- TIME -----  |  ----- Src  -----  |  ----- Dest -----  ");
                System.out.println(" [" + count++ + "]  " + time + "    " + sourceIpPort + "    " + destIpPort);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
