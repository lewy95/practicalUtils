package cn.xzxy.lewy.shell;

public class DoShellResult {

  private boolean isSuccess; // 脚本是否顺利执行

  private String info;


  public DoShellResult() {
  }

  public DoShellResult(String info) {
    this.isSuccess = false;
    this.info = info;
  }

  public boolean isSuccess() {
    return isSuccess;
  }

  public void setSuccess(boolean success) {
    isSuccess = success;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }
}
