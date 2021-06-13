package com.inc.admin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换工具
 *
 */
public class DateUtils {


    /**
     * 转换日期格式
     * @param date
     * @param formatter
     * @author 64301325@qq.com
     * @return
     */
    public static String format(Date date, String formatter) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(formatter);
        try {
            return format.format(date);
        } catch (Exception e) {
            return null;
        }
    }

}
