package com.mediwit.app.config;

import java.io.IOException;
import java.util.Properties;

import android.util.Log;

public class Config {
    Properties pro=null;
    private Config(){
        pro=new Properties();
        try {
            pro.load(this.getClass().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            Log.e("erro", "config file load error");
        }
    }

    public String getUUMServiceURI(){
        return this.pro.getProperty("service_uum");
    }
    private static Config _inst=new Config();
    public static Config getInst(){
        return _inst;
    }

    public static void main(String[] args) {
        System.out.println(Config.getInst().getUUMServiceURI());
    }

}
