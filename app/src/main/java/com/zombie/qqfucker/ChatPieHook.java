package com.zombie.qqfucker;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zombie.qqfucker.AppConfig;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import android.widget.Button;
import android.app.AndroidAppHelper;

public class ChatPieHook extends XC_MethodHook {

    
    @Override
    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
        super.beforeHookedMethod(param);
        /*
        By AliyahZombie(23/01/11)
        禁止用于违法行为
        */
        ConfigUtil cfg = new ConfigUtil();
        
        /* Msg Receive Framework */
        // Check argument type
        // Get MessageRecord class
        ClassLoader cld = AppConfig.getInstance().getClassLoader();
        if(cld == null) {
            XposedBridge.log("No classloader");
            return;
            }
        //Class<?> MessageRecord = XposedHelpers.findClass("com.tencent.mobileqq.data.MessageRecord",cld);
        Class<?> MessageRecord = XposedHelpers.findClass("com.tencent.imcore.message.Message",cld);
        if(MessageRecord == null) {
            XposedBridge.log("No MessageRecord");
            return;}
        if(false){//(!(param.args[1].getClass().toString().contains("com.tencent.imcore.message.Message"))){ 
            XposedBridge.log("No ideal obj");
            XposedBridge.log(param.args[0].getClass().toString());
            XposedBridge.log(param.args[1].getClass().toString());
            XposedBridge.log(param.args[0].toString());
            XposedBridge.log(param.args[1].toString());
            return;
        }
        
        /* Format msg */
        Object MsgObj = param.args[1];
        String content = (String) XposedHelpers.getObjectField(MsgObj,"msg") == null ? "" : (String) XposedHelpers.getObjectField(MsgObj,"msg") ;
        String selfuin = (String) XposedHelpers.getObjectField(MsgObj,"selfuin");
        String senderuin = (String) XposedHelpers.getObjectField(MsgObj,"senderuin");
        String frienduin = (String) XposedHelpers.getObjectField(MsgObj,"frienduin");
        int issend = XposedHelpers.getIntField(MsgObj,"issend");
        int sendFailCode = XposedHelpers.getIntField(MsgObj,"sendFailCode");
        
        selfuin = selfuin == null ? "" : selfuin;
        senderuin = senderuin == null ? "" : senderuin;
        frienduin = frienduin == null ? "" : frienduin;
        
        
        /* Debug Codes */
        // except the msg which was sent by userself and successfully sent
        /*
        if(senderuin==selfuin){
            XposedBridge.log("["+String.valueOf(sendFailCode)+"]"+content);
        }
        else XposedBridge.log(content);
            
            R.I.P (((不会整放弃了
            
        */
        
        /* Command Parsing */
        
        // 防止他人触发
        if(senderuin == selfuin){
            if(content.startsWith(":test")){
                stopMsg(MsgObj);
                editMsg(MsgObj,"[QQFucker] Successfully ran a test");
                
            }
            else if(content.startsWith(":alert")){
                String[] args = content.replace(":alert","").split(" ");
                showFloatAlert(args[0],args[1],args[2]);
                stopMsg(MsgObj);
                editMsg(MsgObj,"[QQFucker] float view created");
            }
            else if(content.startsWith(":config")){
                String[] args = content.replace(":config ","").split(" ");
                cfg.setConfig(args[0],args[1]);
                stopMsg(MsgObj);
                editMsg(MsgObj,"[QQFucker] Successfully configured");
                
            }
            else if(content.startsWith(":getConfig")){
                String[] args = content.replace(":getConfig ","").split(" ");
                stopMsg(MsgObj);
                editMsg(MsgObj,cfg.getConfig(args[0]));
            }
            else{
                editMsg(MsgObj,content.replace("\\:",":"));
            }
        }
    }
    
    public void stopMsg(Object msg){
        XposedHelpers.setIntField(msg,"msgtype",1145141919);
    }
    
    public void editMsg(Object msg,String text){
        XposedHelpers.setObjectField(msg,"msg",text);
    }
    
    public void showFloatAlert(String title,String content,String buttonText){
        /* display a float alert */
        Activity currentActivity = AppConfig.getTopActivity();
        //if (currentActivity==null) currentActivity = (Activity) AndroidAppHelper.currentApplication();
        //Context currentActivity = AndroidAppHelper.currentApplication();
        
        /* Get Window Manager */
        final WindowManager wm =(WindowManager)
        currentActivity.getSystemService(Context.WINDOW_SERVICE);
        
        final WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
            WindowManager.LayoutParams.TYPE_PHONE;
        lp.flags = lp.FLAG_NOT_FOCUSABLE;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.x = 0;
        lp.y = 0;
        lp.alpha = 1;
        lp.gravity = Gravity.CENTER;
        
            
        /* create view */
        TextView heading = new TextView(currentActivity);
        TextView cont = new TextView(currentActivity);
        final LinearLayout back = new LinearLayout(currentActivity);
        heading.setText(title);
        cont.setText(content);
        back.addView(heading);
        back.addView(cont);
        back.setOrientation(1);
        back.setAlpha(1);
        //back.setBackgroundColor(0xFF00DDFF);
        back.setOnTouchListener(new View.OnTouchListener(){
                private int
                mTouchStartX,
                mTouchStartY,
                mTouchCurrentX,
                mTouchCurrentY,
                mMoveX,
                mMoveY;

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        mTouchStartX = (int) event.getRawX();
                        mTouchStartY = (int) event.getRawY();

                        mMoveX = 0;
                        mMoveY = 0;
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        mTouchCurrentX = (int) event.getRawX();
                        mTouchCurrentY = (int) event.getRawY();
                        mMoveX = mTouchCurrentX - mTouchStartX;
                        mMoveY = mTouchCurrentY - mTouchStartY;
                        mTouchStartX = mTouchCurrentX;
                        mTouchStartY = mTouchCurrentY;
                        lp.x += mMoveX;
                        lp.y += mMoveY;

                        wm.updateViewLayout(back, lp);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {

                        wm.updateViewLayout(back, lp);
                    }
                    return false;
                }
        });
        Button b = new Button(currentActivity);
        b.setText(buttonText);
        b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                wm.removeView(back);
                
            }
        });
        back.addView(b);
        wm.addView(back,lp);
        //currentActivity.setContentView(back);
    }
}
