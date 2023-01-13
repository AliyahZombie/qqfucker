
package com.zombie.qqfucker;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
    private Properties configs;
    static String filePath = "/sdcard/qqfucker/config.cfg";

    public ConfigUtil() {
        configs = new Properties(); 
        FileInputStream fis = null; 
        try { 
            fis = new FileInputStream(filePath); 
            configs.load(fis); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally {  // 关闭文件流 
            if (fis != null) { 
                try { fis.close();} catch (IOException e) {} 
            } 
        }  
    }

    public void setConfig(String key, String value) { // 设置参数，并且持久化保存到文件。  

        configs.setProperty(key, value);

        FileOutputStream oFile = null;  

        try {  

            oFile = new FileOutputStream(filePath,false);  

            configs.store(oFile, "Configurations");  

            oFile.close();  

        } catch (IOException e) {  

            e.printStackTrace();  

        } finally{   

            if(oFile!=null){  

                try { oFile.close();} catch (IOException e) {}    

            }     

        }     

    }    

    public String getConfig(String key) { // 获取参数值。  

        return configs.getProperty(key);    

    }         
}
