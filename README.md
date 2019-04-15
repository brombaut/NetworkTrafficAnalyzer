# Network Traffic Analyzer

A project built for a Network Security class to monitor the TCP connections over the machine's NIC.

Features:
  - Number of connections currently in TCP handshake state between a client IP (and port) and server IP in past minute.
  - Number of open (ongoing) TCP connections between a client IP (and port) and server IP in past minute.
  - Number of successfully closed TCP connections between a client IP (and port) and server IP in past minute.
  - Number of failed TCP connections between a client IP (and port) and server IP in past minute.
  - Average number of open connections per second over the past minute.
  - Average number of failed connections persecond over the past minute.

### System during regular browser usage
![Regular Browser Usage](photos/terminalOutput.png?raw=true "Regular Browser Usage")


### System during a slowloris attack
![Slow loris Attack](photos/slowloris_attack.png?raw=true "Slowloris Attack")

System will display warnings for any client IP's that is suspicious (activity rises above thresholds).
