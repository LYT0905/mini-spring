package com.codinghub.miniSpring.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author 莱特0905
 * @Description: 对象映射接口实现类
 * @Date: 2024/10/02 18:57:40
 */
public class DefaultObjectMapper implements ObjectMapper{
    // 日期格式化类型
    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern(dateFormat);

    // 小数格式类型
    String decimalFormat = "#,##0.00";
    DecimalFormat decimalFormatter = new DecimalFormat(decimalFormat);

    public DefaultObjectMapper() {
    }

    /**
     * 设置日期格式化类型
     * @param dateFormat 日期格式化类型
     */
    @Override
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.datetimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    /**
     * 设置小数格式类型
     * @param decimalFormat 小数格式类型
     */
    @Override
    public void setDecimalFormat(String decimalFormat) {
        this.decimalFormat = decimalFormat;
        this.decimalFormatter = new DecimalFormat(decimalFormat);
    }

    /**
     * 将值作为字符串写入
     * @param obj 对象
     * @return JSON字符串类型的对象
     */
    @Override
    public String writeValuesAsString(Object obj) {
        String sJsonStr = "{";

        Class<?> clz = obj.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            String sField = "";
            Object value = null;
            Class<?> type = null;
            String name = field.getName();
            String strValue = "";
            try {
                field.setAccessible(true);
                value = field.get(obj);
            }catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            type = field.getType();
            if (value instanceof Date){
                LocalDate localDate = ((Date)value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                strValue = localDate.format(this.datetimeFormatter);
            } else if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
                strValue = this.decimalFormatter.format(value);
            }else {
                strValue = value.toString();
            }

            if (sJsonStr.equals("{")) {
                sField = "\"" + name + "\":\"" + strValue + "\"";
            }else {
                sField = ",\"" + name + "\":\"" + strValue + "\"";
            }

            sJsonStr += sField;
        }
        sJsonStr += "}";
        return sJsonStr;
    }
}
