# Network Traffic Analyzer

A project built for a Network Security class to monitor the TCP connections over the machine's NIC.

Features:
  - Number of connections currently in TCP handshake state between a client IP (and port) and server IP in past minute.
  - Number of open (ongoing) TCP connections between a client IP (and port) and server IP in past minute.
  - Number of successfully closed TCP connections between a client IP (and port) and server IP in past minute.
  - Number of failed TCP connections between a client IP (and port) and server IP in past minute.
  - Average number of open connections per second over the past minute.
  - Average number of failed connections persecond over the past minute.

System will display warnings for any client IP's that is suspicious (activity rises above thresholds).
