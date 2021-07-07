package com.pkg.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Map数据处理工具类
 * Liz 2020-12-1
 */
public class MapUtil {

    private static Logger log = LoggerFactory.getLogger(MapUtil.class);

    public static String safeStr(String key, Map param){
        if(StringUtils.isEmpty(key)){
            return "";
        }else if(param.containsKey(key)){
            return param.get(key).toString();
        }else{
            return "";
        }
    }

    public static int safeInt(String key, Map param){
        String str = safeStr(key, param);
        if(StringUtils.isEmpty(str)){
            return -1;
        }else{
            try{
                return Integer.parseInt(str);
            }catch(NumberFormatException e){
                log.error("MapUtil获取"+key+"值为"+str+"转换int错误", e);
                return -1;
            }
        }
    }

    public static long safeLong(String key, Map param){
        String str = safeStr(key, param);
        if(StringUtils.isEmpty(str)){
            return -1;
        }else{
            try{
                return Long.getLong(str);
            }catch(NumberFormatException e){
                log.error("MapUtil获取"+key+"值为"+str+"转换long错误", e);
                return -1;
            }
        }
    }

    public static <T>T safeObj(String key, Class<T> c,  Map param) {
        if(StringUtils.isEmpty(key)){
            return null;
        }
        if(param.containsKey(key) && param.get(key).getClass().equals(c)){
            return (T) param.get(key);
        }
        param.getClass();
        return null;
    }

}
