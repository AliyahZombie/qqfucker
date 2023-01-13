package com.zombie.qqfucker;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XC_MethodHook;
import android.app.Activity;

public class HookInit extends XC_LoadPackage {

	@Override
	public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
	    try{
		if ("com.tencent.mobileqq".equals(loadPackageParam.packageName)) {
            XposedBridge.log("Get Target");
            AppConfig.getInstance().setClassLoader(loadPackageParam.classLoader);
            
			XposedBridge.hookAllMethods(Activity.class,"onCreate",new XC_MethodHook(){
                @Override
                public void beforeHookedMethod(XC_MethodHook.MethodHookParam param){
                    AppConfig.setTopActivity((Activity)param.thisObject);
                }
            });
            // Class<?> MessageRecord = XposedHelpers.findClass("com.tencent.mobileqq.data.MessageRecord",loadPackageParam.classLoader);
            // XposedBridge.log(MessageRecord.toString());
            // if(!(MessageRecord == null))
            // XposedBridge.hookAllMethods(MessageRecord,"initInner",new MessageRecordHook());
            // else XposedBridge.log("Can find class");
           
            /* Hook msg pipe*/
            Class<?> BaseChatPie = XposedHelpers.findClass("com.tencent.mobileqq.activity.aio.core.BaseChatPie",loadPackageParam.classLoader);
            XposedBridge.hookAllMethods(BaseChatPie,"update",new ChatPieHook());
		}}
        catch(Exception e){
            XposedBridge.log(e);
            XposedBridge.log("Error");
        }
		
	}

}
