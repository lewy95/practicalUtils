import cn.xzxy.lewy.excel.dto.SpeedItem;
import cn.xzxy.lewy.excel.dto.WeaponItem;
import cn.xzxy.lewy.excel.listener.SpeedExcelListener;
import cn.xzxy.lewy.excel.listener.WeaponExcelListener;
import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

public class TestExcel {

    @Test
    public void calcSpeedTimes() {
        LinkedHashMap<String, Integer> drawMap;
        SpeedExcelListener listener = new SpeedExcelListener();
        File file = new File("C:\\Users\\xxx\\Desktop\\speed.xlsx");
        EasyExcel.read(file, SpeedItem.class, listener).sheet().doRead();

        drawMap = listener.getSpeedMap();

        System.out.println("Result：");
        for (Map.Entry<String, Integer> entry : drawMap.entrySet()) {
            String[] parts = entry.getKey().split("\\|");

            System.out.println(parts[0] + "\t" + entry.getValue() + "\t" + parts[1]);
        }
    }

    @Test
    public void calcWeapon() {
        LinkedHashMap<String, Integer> drawMap;
        WeaponExcelListener listener = new WeaponExcelListener();
        File file = new File("C:\\Users\\xxx\\Desktop\\weapon5.xlsx");
        EasyExcel.read(file, WeaponItem.class, listener).sheet().doRead();

        drawMap = listener.getDrawMap();

        System.out.println("Result：");
        for (Map.Entry<String, Integer> entry : drawMap.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
            // System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

    }

}
