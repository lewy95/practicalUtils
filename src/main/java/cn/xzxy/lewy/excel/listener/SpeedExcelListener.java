package cn.xzxy.lewy.excel.listener;

import cn.xzxy.lewy.excel.dto.SpeedItem;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.LinkedHashMap;

public class SpeedExcelListener extends AnalysisEventListener<SpeedItem> {

  private LinkedHashMap<String, Integer> speedMap = new LinkedHashMap<>();

  /**
   * 一行一行的读取excel内容
   */
  @Override
  public void invoke(SpeedItem data, AnalysisContext context) {
    // System.out.println("****" + data);
    String speedKey = data.getId() + "|" + data.getServerId();
    if (speedMap.containsKey(speedKey)) {
      int newTimes = speedMap.get(speedKey) + data.getTimes();
      speedMap.put(speedKey, newTimes);
    } else {
      speedMap.put(speedKey, data.getTimes());
    }

  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    System.out.println("读取Excel完毕");
  }

  public LinkedHashMap<String, Integer> getSpeedMap() {
    return speedMap;
  }

  public void setSpeedMap(LinkedHashMap<String, Integer> speedMap) {
    this.speedMap = speedMap;
  }
}
