package cn.xzxy.lewy.shell;

import cn.xzxy.lewy.os.OSUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 支持 Linux Shell 和 Windows Cmd
 * todo 后面找个 脚本试一下
 */
public class AppShellUtils {

  /**
   * 执行shell命令，只有当操作系统是linux时可使用
   */
  public static DoShellResult doShell(String cmd) {

    DoShellResult result = new DoShellResult();
    final String[] command;

    String charset;
    if (OSUtils.isLinux()) {
      command = new String[]{"/bin/bash", "-c", cmd};
      charset = "UTF-8";
    } else if (OSUtils.isWindows()) {
      command = new String[]{"cmd", "/c", cmd};
      charset = "GBK";
    } else {
      result.setInfo("server.not.linux");
      return result;
    }

    Process process = null;
    StringBuilder sucInfo = new StringBuilder();
    StringBuilder errorInfo = new StringBuilder();
    BufferedReader infoBr = null;
    BufferedReader errorBr = null;

    try {

      process = Runtime.getRuntime().exec(command);

      String errorLine;

      errorBr = new BufferedReader(new InputStreamReader(process.getErrorStream(), charset));
      while ((errorLine = errorBr.readLine()) != null) {
        errorInfo.append(errorLine).append("\n");
      }

      String infoLine;
      infoBr = new BufferedReader(new InputStreamReader(process.getErrorStream(), charset));
      while ((infoLine = infoBr.readLine()) != null) {
        sucInfo.append(infoLine).append("\n");
      }

      int exitValue = process.waitFor();

      String errorStr = errorInfo.toString().trim();
      if (exitValue != 0) {
        // Loggers.FILE.error("进程等待失败：errorInfo：{}，exitValue：{}", errorStr, exitValue);
        result.setInfo(errorStr);
        return result;
      }

      result.setSuccess(true);
      result.setInfo(sucInfo.toString().trim());

    } catch (Exception e) {
      // AppExceptionUtils.logException("执行shell：", e);
      result.setInfo(e.getMessage());
    } finally {
      if (infoBr != null) {
        try {
          infoBr.close();
        } catch (IOException e) {
          // AppExceptionUtils.logException("关闭流：", e);
        }
      }
      if (errorBr != null) {
        try {
          errorBr.close();
        } catch (IOException e) {
          // AppExceptionUtils.logException("关闭流：", e);
        }
      }
      if (process != null) {
        process.destroy();
      }
    }

    return result;
  }

}
