package com.codinghub.miniSpring.core;

import java.util.Iterator;


/**
 * @author 莱特0905
 * @Description: 把外部资源进行抽象，方便后续扩展，如从xml文件读取，或者数据库读取等方式
 * @Date: 2024/09/09 16:20:03
 */
public interface Resources extends Iterator<Object> {
}
