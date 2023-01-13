package com.zombie.qqfucker;
import android.app.Activity;

/* By AliyahZombie(23/01/11)
    禁止用于违法行为
    AppConfig: Store global variables 
*/

public class AppConfig {
    public Activity topActivity = null;
    public ClassLoader cld = null;
    private static AppConfig instance = new AppConfig();
    // 单例模式
    private AppConfig(){}
    
    public static AppConfig getInstance(){
        return instance;
    }
    
    public static void setTopActivity(Activity a){
        getInstance().topActivity = a;
    }
    
    public static Activity getTopActivity(){
        return getInstance().topActivity;
    }
    
    public static void setClassLoader(ClassLoader cld){
        getInstance().cld = cld;
    }
    
    public static ClassLoader getClassLoader(){
        return getInstance().cld;
    }
}
