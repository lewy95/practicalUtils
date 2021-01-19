import cn.xzxy.lewy.xml.ParseXml2Map;
import org.dom4j.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestXmlParse {

    @Test
    public void testEmr() {
        Document doc = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            FileInputStream fis = new FileInputStream("data/xml/jcbg.xml");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String str = new String(b);
            String patten = "<ClinicalDocument[^>]*?>";
            String newStr = str.replaceFirst(patten, "<ClinicalDocument>");
            String nnStr = newStr.replaceAll("xsi:type", "trash");
            //System.out.println(nnStr);
            doc = DocumentHelper.parseText(nnStr);
            //System.out.println(doc);

            Node patient = doc.selectSingleNode("/ClinicalDocument/recordTarget/patientRole/patient");
            //System.out.println(node.getText());
            System.out.println(patient.selectSingleNode("name").getText());
            System.out.println(patient.selectSingleNode("administrativeGenderCode/@code").getText());
            System.out.println(patient.selectSingleNode("age/@value").getText() +
                    patient.selectSingleNode("age/@unit").getText());

            System.out.println(doc.selectSingleNode("/ClinicalDocument/author/time/@value").getText());
            System.out.println(doc.selectSingleNode("/ClinicalDocument/author/assignedAuthor/assignedPerson/name").getText());

            Node organization = doc.selectSingleNode("/ClinicalDocument/custodian/assignedCustodian/representedCustodianOrganization");
            System.out.println(organization.selectSingleNode("id/@extension").getText());
            System.out.println(organization.selectSingleNode("name").getText());

            Node sqOrganization = doc.selectSingleNode("/ClinicalDocument/participant/associatedEntity/scopingOrganization");
            System.out.println(sqOrganization.selectSingleNode("name").getText());

            Node operation = doc.selectSingleNode("/ClinicalDocument/component/structuredBody/component/section/code[@code=\"47519-4\"]");
            System.out.println(operation.getParent().selectSingleNode("entry/procedure/targetSiteCode/@code").getStringValue());

            Node zdms = doc.selectSingleNode("//code[@code=\"DE04.01.117.00\"]").getParent();
            System.out.println(zdms.selectSingleNode("value").getText());

            System.out.println(doc.selectSingleNode("//code[@code=\"DE06.00.179.00\"]").getParent().elementText("value"));
            System.out.println(doc.selectSingleNode("//code[@code=\"DE04.50.132.00\"]").getParent().elementText("value"));
            System.out.println(doc.selectSingleNode("//code[@code=\"DE04.50.131.00\"]").getParent().elementText("value"));


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
    public void testXml2Map() {
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

        Map<String, Object> map = ParseXml2Map.xmlTransferToMap(str);

        for (Map.Entry<String, Object> m : map.entrySet()) {
            System.out.println("key: " + m.getKey() + ", value: " + m.getValue());
        }
    }

    @Test
    public void testEmr2() {
        Document doc = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            FileInputStream fis = new FileInputStream("data/xml/jybg.xml");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String str = new String(b);
            String patten = "<ClinicalDocument[^>]*?>";
            String newStr = str.replaceFirst(patten, "<ClinicalDocument>");
            String nnStr = newStr.replaceAll("xsi:type", "trash");
            //System.out.println(nnStr);
            doc = DocumentHelper.parseText(nnStr);
            //System.out.println(doc);

            Node patient = doc.selectSingleNode("/ClinicalDocument/recordTarget/patientRole/patient");
            //System.out.println(node.getText());
            System.out.println(patient.selectSingleNode("name").getText());
            System.out.println(patient.selectSingleNode("administrativeGenderCode/@code").getText());
            System.out.println(patient.selectSingleNode("age/@value").getText() +
                    patient.selectSingleNode("age/@unit").getText());

            System.out.println(doc.selectSingleNode("/ClinicalDocument/author/time/@value").getText());
            System.out.println(doc.selectSingleNode("/ClinicalDocument/author/assignedAuthor/assignedPerson/name").getText());

            Node organization = doc.selectSingleNode("/ClinicalDocument/custodian/assignedCustodian/representedCustodianOrganization");
            System.out.println(organization.selectSingleNode("id/@extension").getText());
            System.out.println(organization.selectSingleNode("name").getText());

            Node sqOrganization = doc.selectSingleNode("/ClinicalDocument/participant/associatedEntity/scopingOrganization");
            System.out.println(sqOrganization.selectSingleNode("id/@extension").getText());
            System.out.println(sqOrganization.selectSingleNode("name").getText());

            System.out.println(doc.selectSingleNode("//id[@root=\"2.16.156.10011.1.33\"]/@extension").getText());

            Element element = doc.selectSingleNode("//id[@root=\"2.16.156.10011.1.3\"]").getParent();
            System.out.println(element.getName());
            System.out.println(element.selectSingleNode("administrativeGenderCode/@code").getText());

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
    public void testEmr3() {
        Document doc = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            FileInputStream fis = new FileInputStream("data/xml/ryjl.xml");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String str = new String(b);
            String patten = "<ClinicalDocument[^>]*?>";
            String newStr = str.replaceFirst(patten, "<ClinicalDocument>");
            String nnStr = newStr.replaceAll("xsi:type", "trash");
            doc = DocumentHelper.parseText(nnStr);
            //Element element = doc.selectSingleNode("//id[@root=\"2.16.156.10011.1.5\"]").getParent();
            //Element element = doc.selectSingleNode("//id[@root=\"2.16.156.10011.1.12\"]").getParent();
            Element element = doc.selectSingleNode("//code[@code=\"DE05.01.024.00\"]").getParent();
            String diagnosis;
            if (element.selectSingleNode("value/@displayName") != null) {
                diagnosis = element.selectSingleNode("value/@displayName").getText();
            } else {
                diagnosis = element.selectSingleNode("value/@code").getText();
            }
            System.out.println(diagnosis);
            System.out.println(doc.selectSingleNode("//encompassingEncounter/effectiveTime/@value").getText());
            //map = ParseXml2Map.Dom2Map(element);
            String value = doc.selectSingleNode("//code[@code=\"DE02.10.071.00\"]").getParent().elementText("value");
            System.out.println(value);
            System.out.println(doc.selectSingleNode("//informant/assignedEntity/assignedPerson/name").getText());
            List<Node> zyzds = doc.selectNodes("//code[@code=\"DE05.10.172.00\"]");
            System.out.println(zyzds.size());
            System.out.println(zyzds.get(0).selectSingleNode("@displayName").getText());
            System.out.println(zyzds.get(1).getParent().elementText("value"));
            System.out.println(zyzds.get(2).selectSingleNode("@displayName").getText());
            System.out.println(zyzds.get(3).getParent().elementText("value"));
            System.out.println(zyzds.get(4).selectSingleNode("@displayName").getText());
            System.out.println(zyzds.get(5).getParent().elementText("value"));
            System.out.println(doc.selectSingleNode("//code[@displayName=\"主治医师\"]").getParent().selectSingleNode("assignedPerson/name").getText());
            System.out.println(doc.selectSingleNode("//code[@code=\"DE05.10.143.00\"]").getParent().selectSingleNode("value/@value").getText());
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
    public void testEmr4() {
        Document doc = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            FileInputStream fis = new FileInputStream("data/xml/jybg.xml");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String str = new String(b);
            String patten = "<ClinicalDocument[^>]*?>";
            String newStr = str.replaceFirst(patten, "<ClinicalDocument>");
            String nnStr = newStr.replaceAll("xsi:type", "trash");
            doc = DocumentHelper.parseText(nnStr);
            //Element element = doc.selectSingleNode("//id[@root=\"2.16.156.10011.1.5\"]").getParent();
            //Element element = doc.selectSingleNode("//id[@root=\"2.16.156.10011.1.22\"]").getParent();
            Element element = doc.selectSingleNode("//code[@root=\"DE05.01.024.00\"]").getParent();

            //System.out.println(element.selectSingleNode("id/@extension").getText());
            map = ParseXml2Map.Dom2Map(element);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, Object> m : map.entrySet()) {
            System.out.println("key: " + m.getKey() + ", value: " + m.getValue());
        }
    }

    @Test
    public void testEmr5() {
        Document doc = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            FileInputStream fis = new FileInputStream("data/xml/cyxj.xml");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String str = new String(b);
            String patten = "<ClinicalDocument[^>]*?>";
            String newStr = str.replaceFirst(patten, "<ClinicalDocument>");
            String nnStr = newStr.replaceAll("xsi:type", "trash");
            doc = DocumentHelper.parseText(nnStr);
            Map<String, String> siteMap = getSiteInfo(doc);
            System.out.println(siteMap.get("ksmc"));
            System.out.println(siteMap.get("bqmc"));
            System.out.println(siteMap.get("bch"));
            System.out.println(siteMap.get("bfh"));
            System.out.println(doc.selectSingleNode("//low/@value").getText());
            System.out.println(doc.selectSingleNode("//high/@value").getText());
            Element zyts = doc.selectSingleNode("//code[@code=\"DE06.00.310.00\"]").getParent();
            System.out.println(zyts.selectSingleNode("value/@value").getText() + zyts.selectSingleNode("value/@unit").getText());
            System.out.println(doc.selectSingleNode("//code[@code=\"DE05.10.148.00\"]").selectSingleNode("@displayName").getText());
            System.out.println(doc.selectSingleNode("//code[@code=\"DE04.50.128.00\"]").getParent().elementText("value"));
            System.out.println(doc.selectSingleNode("//code[@code=\"DE06.00.296.00\"]").getParent().elementText("value"));
            System.out.println(doc.selectSingleNode("//code[@code=\"DE05.10.113.00\"]").getParent().selectSingleNode("value/@code").getText());

            List<Node> element = doc.selectNodes("//code[@code=\"DE05.01.024.00\"]");
            Element ryELe = element.get(0).getParent();
            String diagnosis;
            //有displayName直接展示
            if (ryELe.selectSingleNode("value/@displayName") != null) {
                diagnosis = ryELe.selectSingleNode("value/@displayName").getText();
            } else {
                //没有displayName则取code进行字典转换
                String code = ryELe.selectSingleNode("value/@code").getText();
                //diagnosis = EmrConvert.getDicValueByCode("ICD10", code);
                diagnosis = code;
            }
            System.out.println(diagnosis);

            System.out.println(doc.selectSingleNode("//code[@displayName=\"住院医师\"]").getParent().selectSingleNode("assignedPerson/name").getText());

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
    public void testEmr6() {
        Document doc = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            FileInputStream fis = new FileInputStream("data/xml/jzlgbl.xml");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String str = new String(b);
            String patten = "<ClinicalDocument[^>]*?>";
            String newStr = str.replaceFirst(patten, "<ClinicalDocument>");
            String nnStr = newStr.replaceAll("xsi:type", "trash");
            doc = DocumentHelper.parseText(nnStr);
            //2.16.156.10011.1.26
            System.out.println(doc.selectSingleNode("//id[@root=\"2.16.156.10011.1.26\"]").getParent().elementText("name"));
            System.out.println(doc.selectSingleNode("//code[@code=\"DE06.00.181.00\"]").getParent().elementText("value"));
            Node node = (Node) doc.selectNodes("//code[@code=\"DE06.00.181.00\"]").get(1);
            System.out.println(node.getParent().elementText("value"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    private static Map<String, String> getSiteInfo(Document doc) {
        Map<String, String> siteMap = new HashMap<String, String>();
        Element ele1 = doc.selectSingleNode("//id[@root=\"2.16.156.10011.1.22\"]").getParent();
        //Map<String, Object> map = ParseXml2Map.Dom2Map(element);
        if (ele1.element("name") != null) {
            siteMap.put("bch", ele1.elementText("name"));
        }
        Element ele2 = doc.selectSingleNode("//id[@root=\"2.16.156.10011.1.21\"]").getParent();
        if (ele2.element("name") != null) {
            siteMap.put("bfh", ele2.elementText("name"));
        }
        Element ele3 = doc.selectSingleNode("//id[@root=\"2.16.156.10011.1.26\"]").getParent();
        if (ele3.element("name") != null) {
            siteMap.put("ksmc", ele3.elementText("name"));
        }
        Element ele4 = doc.selectSingleNode("//id[@root=\"2.16.156.10011.1.27\"]").getParent();
        if (ele4.element("name") != null) {
            siteMap.put("bqmc", ele4.elementText("name"));
        }
        return siteMap;
    }

}
