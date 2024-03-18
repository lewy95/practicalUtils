package cn.xzxy.lewy.excel.dto;

import com.alibaba.excel.annotation.ExcelProperty;

public class WeaponItem {

  @ExcelProperty(index = 0, value = "player_id")
  private String playerId;

  @ExcelProperty(index = 1, value = "draw_num")
  private Integer drawNum;

  @ExcelProperty(index = 2, value = "p1")
  private String p1;

  @ExcelProperty(index = 3, value = "p2")
  private String p2;

  @ExcelProperty(index = 4, value = "p3")
  private String p3;

  @ExcelProperty(index = 5, value = "p4")
  private String p4;

  @ExcelProperty(index = 6, value = "p5")
  private String p5;

  @ExcelProperty(index = 7, value = "p6")
  private String p6;

  @ExcelProperty(index = 8, value = "p7")
  private String p7;

  @ExcelProperty(index = 9, value = "p8")
  private String p8;

  @ExcelProperty(index = 10, value = "p9")
  private String p9;

  @ExcelProperty(index = 11, value = "p10")
  private String p10;

  public String getPlayerId() {
    return playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public Integer getDrawNum() {
    return drawNum;
  }

  public void setDrawNum(Integer drawNum) {
    this.drawNum = drawNum;
  }

  public String getP1() {
    return p1;
  }

  public void setP1(String p1) {
    this.p1 = p1;
  }

  public String getP2() {
    return p2;
  }

  public void setP2(String p2) {
    this.p2 = p2;
  }

  public String getP3() {
    return p3;
  }

  public void setP3(String p3) {
    this.p3 = p3;
  }

  public String getP4() {
    return p4;
  }

  public void setP4(String p4) {
    this.p4 = p4;
  }

  public String getP5() {
    return p5;
  }

  public void setP5(String p5) {
    this.p5 = p5;
  }

  public String getP6() {
    return p6;
  }

  public void setP6(String p6) {
    this.p6 = p6;
  }

  public String getP7() {
    return p7;
  }

  public void setP7(String p7) {
    this.p7 = p7;
  }

  public String getP8() {
    return p8;
  }

  public void setP8(String p8) {
    this.p8 = p8;
  }

  public String getP9() {
    return p9;
  }

  public void setP9(String p9) {
    this.p9 = p9;
  }

  public String getP10() {
    return p10;
  }

  public void setP10(String p10) {
    this.p10 = p10;
  }
}
