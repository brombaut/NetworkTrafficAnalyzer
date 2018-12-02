public class ConnectionIPObject{
  int open, close;
  String source, dest;
  public ConnectionIPObject(String sourceIn, String destIn) {
    open = 0;
    close = 0;
    source = sourceIn;
    dest = destIn;
  }

  public void addOpen() {
    open++;
  }

  public void addClose() {
    // open--;
    close++;
  }

  public int getOpen() {
    return open;
  }

  public int getClose() {
    return close;
  }

  public String getSource() {
    return source;
  }

  public String getDest() {
    return dest;
  }
}
