package cn.xzxy.lewy.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * 将xml文件解析成map
 */
public class ParseXml2Map {

    /**
     * 将document对象转化为map（String→Document→Map）
     * 改方法会将整个文档都转成一个map
     *
     * @param doc xml file
     * @return map
     */
    public static Map<String, Object> Dom2Map(Document doc) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (doc == null)
            return map;
        Element root = doc.getRootElement();
        for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {
            Element e = (Element) iterator.next();
            List list = e.elements();
            if (list.size() > 0) {
                map.put(e.getName(), Dom2Map(e));
            } else
                map.put(e.getName(), e.getText());
        }
        return map;
    }

    /**
     * 将Element对象转为Map（String→Document→Element→Map）
     * 对某一个节点进行
     *
     * @param e element
     * @return map
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map<String, Object> Dom2Map(Element e) {
        Map<String, Object> map = new HashMap<String, Object>();
        List list = e.elements();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Element iter = (Element) list.get(i);
                List mapList = new ArrayList();

                if (iter.elements().size() > 0) {
                    Map<String, Object> m = Dom2Map(iter);
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(m);
                        }
                        if (obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = (List) obj;
                            mapList.add(m);
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), m);
                } else {
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(iter.getText());
                        }
                        if (obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = (List) obj;
                            mapList.add(iter.getText());
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), iter.getText());
                }
            }
        } else
            map.put(e.getName(), e.getText());
        return map;
    }

    /**
     * 将字符串类型的xml内容转化为map
     * @param xml str
     * @return map
     */
    public static Map xmlTransferToMap(String xml){
        Document document;
        try {
            document = DocumentHelper.parseText(xml);
            return Dom2Map(document);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new HashMap();
    }


}