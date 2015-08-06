package com.mediwit.app.util;

import java.util.UUID;
import java.util.logging.Logger;



public class StringUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static boolean isNotBlank(String str){
        if(null==str || "".compareTo(str) == 0 || "null".equalsIgnoreCase(str)){
            return false;
        }
        return true;
    }
    public static boolean isBlank(String str){
        if(null==str || "".compareTo(str) == 0 || "null".equalsIgnoreCase(str)){
            return true;
        }
        return false;
    }
    /**
     * 去掉所有的空格符号、回车、换行符、制表符
     * @param str
     * @return
     */
    public String replaceBlank(String str){


        if(str != null){
            str = str.replaceAll( "\\s", "" );
        }


        return str;
    }

    public String replaceBlank2(String str){


        if(str != null){

        }


        return str;
    }

    /**
     *
     * 功能描述：处理文件名，将文件名中存在的非法字符替换掉
     *
     * @author zyx/张影宣
     * 参数解释：
     * 返回值：String
     *
     */
    public static String processFileName(String fileName) {

        if(fileName == null) {

            return fileName;
        }
        String returnString = fileName.replace("\\","、").replace("/","、").replace("|"," ").replace("<","《").replace(">","》").replace("\"","“").replace("*","※").replace("?","？").replace(":","：");

        return returnString;
    }
    public static void main(String[] args) {
        System.out.println(System.getProperty("file.encoding"));
    }
}

