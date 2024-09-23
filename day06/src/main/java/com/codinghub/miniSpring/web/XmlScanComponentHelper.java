package com.codinghub.miniSpring.web;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 莱特0905
 * @Description: 自动解析XML配置的包扫描
 * @Date: 2024/09/23 20:24:56
 */
public class XmlScanComponentHelper {
    public static List<String> getNodeValue(URL xmlPath){
        List<String> packages = new ArrayList<>();
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(xmlPath);
        }catch (DocumentException ex){
            ex.printStackTrace();
        }
        Element root = document.getRootElement();
        Iterator<Element> iterator = root.elementIterator();
        while (iterator.hasNext()){
            Element element = iterator.next();
            packages.add(element.attributeValue("base-package"));
        }
        return packages;
    }
}
