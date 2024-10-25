package com.codinghub.miniSpring.core;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * @author 莱特0905
 * @Description: 类路径下的xml资源
 * @Date: 2024/09/09 16:27:01
 */
public class ClassPathXmlResource implements Resources{
    Element element;
    Document document;
    Iterator<Element> elementIterator;

    public ClassPathXmlResource(String fileName){
        SAXReader saxReader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
        try {
            this.document = saxReader.read(xmlPath);
            this.element = document.getRootElement();
            this.elementIterator = element.elementIterator();
        }catch (Throwable ex){
            throw new RuntimeException("ClassPathXmlResource UnKnow Error");
        }
    }

    @Override
    public boolean hasNext() {
        return this.elementIterator.hasNext();
    }

    @Override
    public Object next() {
        return this.elementIterator.next();
    }
}
