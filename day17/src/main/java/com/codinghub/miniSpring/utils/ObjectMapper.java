package com.codinghub.miniSpring.utils;

/**
 * @author 莱特0905
 * @Description: 对象映射接口
 * @Date: 2024/10/02 18:51:47
 */
public interface ObjectMapper {
    /**
     * 设置日期格式化类型
     * @param dateFormat 日期格式化类型
     */
    void setDateFormat(String dateFormat);

    /**
     * 设置小数格式类型
     * @param decimalFormat 小数格式类型
     */
    void setDecimalFormat(String decimalFormat);

    /**
     * 将值作为字符串写入
     * @param obj 对象
     * @return JSON字符串类型的对象
     */
    String writeValuesAsString(Object obj);
}
