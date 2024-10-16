package com.codinghub.miniSpring.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

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
        StringBuilder sJsonStr = new StringBuilder("{");

        // 处理单个对象或列表
        if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            for (Object item : list) {
                appendFieldsToJson(sJsonStr, item);
                sJsonStr.append(","); // 在每个对象间添加逗号
            }
            // 移除最后一个多余的逗号
            if (sJsonStr.length() > 1) {
                sJsonStr.deleteCharAt(sJsonStr.length() - 1);
            }
        } else {
            appendFieldsToJson(sJsonStr, obj);
        }

        sJsonStr.append("}");
        return sJsonStr.toString();
    }

    /**
     * 负责将对象的字段信息转换为 JSON 并追加到 StringBuilder 中
     * @param sJsonStr Json格式字符串
     * @param obj 对象
     */
    private void appendFieldsToJson(StringBuilder sJsonStr, Object obj) {
        Class<?> clz = obj.getClass();
        Field[] fields = clz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // 开启私有字段的访问权限
            try {
                Object value = field.get(obj);
                String name = field.getName();
                String strValue = formatValue(value);

                // 拼接字段名和值
                if (sJsonStr.length() > 1 && sJsonStr.charAt(sJsonStr.length() - 1) != '{') {
                    sJsonStr.append(",");
                }
                sJsonStr.append("\"").append(name).append("\":\"").append(strValue).append("\"");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 负责根据字段类型将值格式化为字符串
     * @param value 值
     * @return 字符串
     */
    private String formatValue(Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof Date) {
            LocalDate localDate = ((Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate.format(this.datetimeFormatter);
        } else if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
            return this.decimalFormatter.format(value);
        } else {
            return value.toString();
        }
    }

}
