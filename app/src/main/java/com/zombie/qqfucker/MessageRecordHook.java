package com.zombie.qqfucker;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;

public class MessageRecordHook extends XC_MethodHook {

    @Override
    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
        super.beforeHookedMethod(param);
        XposedBridge.log(param.args[3].toString());
        param.args[3] = "HookMessageRecord";
    }
    
    
}
