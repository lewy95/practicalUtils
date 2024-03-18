package cn.xzxy.lewy.excel.listener;

import cn.xzxy.lewy.excel.dto.WeaponItem;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.LinkedHashMap;

public class WeaponExcelListener extends AnalysisEventListener<WeaponItem> {

  private LinkedHashMap<String, Integer> drawMap = new LinkedHashMap<>();

  /**
   * 一行一行的读取excel内容
   */
  @Override
  public void invoke(WeaponItem data, AnalysisContext context) {
    int pos = 0;
    if (data.getP1() != null && !data.getP1().isEmpty()) pos = 1;
    if (data.getP2() != null && !data.getP2().isEmpty()) pos = 2;
    if (data.getP3() != null && !data.getP3().isEmpty()) pos = 3;
    if (data.getP4() != null && !data.getP4().isEmpty()) pos = 4;
    if (data.getP5() != null && !data.getP5().isEmpty()) pos = 5;
    if (data.getP6() != null && !data.getP6().isEmpty()) pos = 6;
    if (data.getP7() != null && !data.getP7().isEmpty()) pos = 7;
    if (data.getP8() != null && !data.getP8().isEmpty()) pos = 8;
    if (data.getP9() != null && !data.getP9().isEmpty()) pos = 9;
    if (data.getP10() != null && !data.getP10().isEmpty()) pos = 10;
    if (pos > 0) {
      if (data.getDrawNum() == 1) {
        drawMap.put(data.getPlayerId(), 0);
      } else {
        drawMap.put(data.getPlayerId(), 10 - pos);
      }
    } else {
      if (!drawMap.containsKey(data.getPlayerId())) {
        drawMap.put(data.getPlayerId(), data.getDrawNum());
      } else {
        int curNum = drawMap.get(data.getPlayerId());
        drawMap.put(data.getPlayerId(), curNum + data.getDrawNum());
      }
    }
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    System.out.println("读取Excel完毕");
  }

  public LinkedHashMap<String, Integer> getDrawMap() {
    return drawMap;
  }

  public void setDrawMap(LinkedHashMap<String, Integer> drawMap) {
    this.drawMap = drawMap;
  }
}
