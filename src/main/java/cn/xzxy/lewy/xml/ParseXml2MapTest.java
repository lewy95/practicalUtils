package cn.xzxy.lewy.xml;

import org.dom4j.*;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ParseXml2MapTest {

    @Test
    public void testDom2Map() {
        Document doc = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            FileInputStream fis = new FileInputStream("data/xml/检查报告.xml");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String str = new String(b);
            doc = DocumentHelper.parseText(str);

            //Element node = (Element) doc.selectNodes("/ClinicalDocument/recordTarget/patientRole/patient")[0];
            Node node = doc.selectSingleNode("/ClinicalDocument/typeId");
            System.out.println(node.getName());
//            List<Element> elements = element.elements();
//            for (Element ele: elements) {
//                System.out.println(ele.getName());
//            }
            //System.out.println(element.getName());
            //输出这个节点的所有属性值
//            for(Iterator it = element.attributeIterator(); it.hasNext();){
//                Attribute conAttr = (Attribute)it.next();
//                String conTxt = conAttr.getValue();
//                String conAttrName = conAttr.getName();
//                System.out.println(conAttrName+" = "+conTxt);
//            }

            //map = ParseXml2Map.Dom2Map(node);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //for (Map.Entry<String, Object> m: map.entrySet()) {
        //    System.out.println("key: " + m.getKey() + ", value: " + m.getValue());
        //}
    }

    @Test
    public void testXml2Map(){
        String str = "<!-- 审核医师签名 -->\n" +
                "\t<legalAuthenticator>\n" +
                "\t\t<time/>\n" +
                "\t\t<signatureCode/>\n" +
                "\t\t<assignedEntity>\n" +
                "\t\t\t<id root=\"2.16.156.10011.1.4\" extension=\"医务人员编号\"/>\n" +
                "\t\t\t<code displayName=\"审核医师\"></code>\n" +
                "\t\t\t<assignedPerson classCode=\"PSN\" determinerCode=\"INSTANCE\">\n" +
                "\t\t\t\t<name>刘医生</name>\n" +
                "\t\t\t</assignedPerson>\n" +
                "\t\t</assignedEntity>\n" +
                "\t</legalAuthenticator>";

        Map<String,Object> map = ParseXml2Map.xmlTransferToMap(str);

        for (Map.Entry<String, Object> m: map.entrySet()) {
            System.out.println("key: " + m.getKey() + ", value: " + m.getValue());
        }
    }

}
