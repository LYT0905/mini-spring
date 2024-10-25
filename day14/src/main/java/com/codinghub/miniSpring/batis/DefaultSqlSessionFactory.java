package com.codinghub.miniSpring.batis;

import com.codinghub.miniSpring.beans.factory.annotation.Autowired;
import com.codinghub.miniSpring.jdbc.core.JdbcTemplate;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 莱特0905
 * @Description: 默认SQL会话工厂实现类
 * @Date: 2024/10/16 18:01:26
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory{

    /**
     * JDBC模板
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Mapper文件位置
     */
    private String mapperLocations;

    /**
     * 存放Mapper里编写的信息
     */
    Map<String, MapperNode> mapperNodeMap = new HashMap<>();

    public DefaultSqlSessionFactory() {
    }

    /**
     * 初始化
     */
    public void init(){
        scanLocation(this.mapperLocations);
        for (Map.Entry<String, MapperNode> nodeEntry : this.mapperNodeMap.entrySet()) {
            System.out.println(nodeEntry.getKey() + " : " + nodeEntry.getValue());
        }
    }

    /**
     * 扫描路径
     * @param locations 路径
     */
    private void scanLocation(String locations){
        String sLocationPath = this.getClass().getClassLoader().getResource("").getPath() + locations;
        System.out.println("mapper location : "+sLocationPath);
        File dir = new File(sLocationPath);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()){
                scanLocation(locations + "/" + file.getName());
            }else {
                buildMapperNodes(locations + "/" + file.getName());
            }
        }
    }

    /**
     * 构建Mapper节点里的信息
     * @param filePath 文件地址
     * @return 存储了Mapper节点里的信息的Map
     */
    private Map<String, MapperNode> buildMapperNodes(String filePath){
        System.out.println(filePath);
        SAXReader saxReader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(filePath);
        try {
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            String namespace = rootElement.attributeValue("namespace");
            Iterator<Element> nodes = rootElement.elementIterator();
            while (nodes.hasNext()){
                Element node = nodes.next();
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();
                MapperNode selectnode = new MapperNode();
                selectnode.setNamespace(namespace);
                selectnode.setId(id);
                selectnode.setParameterType(parameterType);
                selectnode.setResultType(resultType);
                selectnode.setSql(sql);
                // 默认没有参数
                selectnode.setParameter("");
                this.mapperNodeMap.put(namespace + "." + id, selectnode);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return this.mapperNodeMap;
    }

    /**
     * 打开SQL会话
     * @return SQL会话
     */
    @Override
    public SqlSession openSession() {
        SqlSession newSqlSession = new DefaultSqlSession();
        newSqlSession.setJdbcTemplate(this.jdbcTemplate);
        newSqlSession.setSqlSessionFactory(this);
        return newSqlSession;
    }

    /**
     * 获取Mapper写的节点内容
     * @param name Mapper文件名字
     * @return Mapper写的节点内容
     */
    @Override
    public MapperNode getMapperNode(String name) {
        return this.mapperNodeMap.get(name);
    }


    public Map<String, MapperNode> getMapperNodeMap() {
        return mapperNodeMap;
    }

    public void setMapperNodeMap(Map<String, MapperNode> mapperNodeMap) {
        this.mapperNodeMap = mapperNodeMap;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }
}
