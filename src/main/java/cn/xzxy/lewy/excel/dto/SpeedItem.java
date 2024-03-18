package cn.xzxy.lewy.excel.dto;

import com.alibaba.excel.annotation.ExcelProperty;

public class SpeedItem {

  @ExcelProperty(index = 0, value = "id")
  private Integer id;

  @ExcelProperty(index = 1, value = "times")
  private Integer times;

  @ExcelProperty(index = 2, value = "server_id")
  private Integer serverId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getTimes() {
    return times;
  }

  public void setTimes(Integer times) {
    this.times = times;
  }

  public Integer getServerId() {
    return serverId;
  }

  public void setServerId(Integer serverId) {
    this.serverId = serverId;
  }
}
