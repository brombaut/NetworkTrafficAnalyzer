public class ConnectionIPObject{
  int open, close, fail;
  String source, dest;
  public ConnectionIPObject(String sourceIn, String destIn) {
    open = 0;
    close = 0;
    fail = 0;
    source = sourceIn;
    dest = destIn;
  }

  public void addOpen() {
    open++;
  }

  public void addClose() {
    close++;
  }

  public void addFail() {
    fail++;
  }

  public int getOpen() {
    return open;
  }

  public int getClose() {
    return close;
  }

  public int getFail() {
    return fail;
  }

  public String getSource() {
    return source;
  }

  public String getDest() {
    return dest;
  }
}
