package cn.xzxy.lewy.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParseXml2MapTest {

    @Test
    public void testDom2Map() {
        Document doc = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            FileInputStream fis = new FileInputStream("data/xml/jcbg.xml");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String str = new String(b);
            doc = DocumentHelper.parseText(str);
            map = ParseXml2Map.Dom2Map(doc);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, Object> m: map.entrySet()) {
            System.out.println("key: " + m.getKey() + ", value: " + m.getValue());
        }
    }

    @Test
    public void testXml2Map(){
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<note>\n" +
                "  <to>\n" +
                "      <hello>fyy</hello>\n" +
                "      <world>beloved</world>\n" +
                "  </to>\n" +
                "  <from>Jani</from>\n" +
                "  <heading>Reminder</heading>\n" +
                "  <body>Don't forget me this weekend!</body>\n" +
                "</note>";

        Map<String,Object> map = ParseXml2Map.xmlTransferToMap(str);

        //for (Map.Entry<String, Object> m: map.entrySet()) {
        //    System.out.println("key: " + m.getKey() + ", value: " + m.getValue());
        //}


        System.out.println(map.get("to"));
        Map<String,String> obj = (Map<String, String>) map.get("to");
        System.out.println(obj.get("hello"));
    }

}
