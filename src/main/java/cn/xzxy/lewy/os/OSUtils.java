package cn.xzxy.lewy.os;

public class OSUtils {
  public static boolean isLinux() {
    return System.getProperty("os.name").toLowerCase().contains("linux");
  }

  public static boolean isWindows() {
    return System.getProperty("os.name").toLowerCase().contains("windows");
  }

  public String JudgeSystem() {
    if (isLinux()) {
      return "linux";
    } else if (isWindows()) {
      return "windows";
    } else {
      return "other system";
    }
  }
}
