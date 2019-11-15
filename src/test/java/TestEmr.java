import cn.xzxy.lewy.xml.ParseXml2Map;
import org.dom4j.*;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestEmr {

    @Test
    public void testEmr() {
        Document doc = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            FileInputStream fis = new FileInputStream("data/xml/检查报告.xml");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String str = new String(b);
            String patten = "<ClinicalDocument[^>]*?>";
            String newStr = str.replaceFirst(patten, "<ClinicalDocument>");
            String nnStr = newStr.replaceAll("xsi:type","trash");
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

    @Test
    public void testEmr2() {
        Document doc = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            FileInputStream fis = new FileInputStream("data/xml/检验报告.xml");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String str = new String(b);
            String patten = "<ClinicalDocument[^>]*?>";
            String newStr = str.replaceFirst(patten, "<ClinicalDocument>");
            String nnStr = newStr.replaceAll("xsi:type","trash");
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
            FileInputStream fis = new FileInputStream("data/xml/检验报告.xml");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String str = new String(b);
            String patten = "<ClinicalDocument[^>]*?>";
            String newStr = str.replaceFirst(patten, "<ClinicalDocument>");
            String nnStr = newStr.replaceAll("xsi:type","trash");
            doc = DocumentHelper.parseText(nnStr);
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

}
