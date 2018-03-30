package com.hj8nb.bixinxposed;

import android.app.Application;
import android.content.Context;


import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


/**
 * Created by ThundeRobot on 2018/2/24.
 */

public class Main implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                ClassLoader cl=((Context)param.args[0]).getClassLoader();
                Class<?> hookclass=null;
                try {
                    hookclass=cl.loadClass("com.bixin.live.data.bean.BaseResponse");
                    XposedHelpers.findAndHookMethod(hookclass, "getData", new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            try{
                                String className=param.getResult().getClass().getName().toString();
                                String patt = "PrivateLimitBean";
                                Pattern r = Pattern.compile(patt);
                                Matcher m = r.matcher(className);
                                while (m.find()) {
                                    XposedBridge.log("====之前===="+param.getResult());
                                    Object obj=param.getResult();
                                    Class userCla =  obj.getClass();
                                    Field[] fs = userCla.getDeclaredFields();
                                    for(int i = 0 ; i < fs.length; i++) {
                                        Field f = fs[i];
                                        f.setAccessible(true); //设置些属性是可以访问的
                                        String type = f.getType().toString();//得到此属性的类型
                                        if (type.endsWith("String")) {
                                            f.set(obj, null);        //给属性设值
                                        } else if (type.endsWith("int") || type.endsWith("Integer")) {
                                            f.set(obj, 1);       //给属性设值
                                        }
                                    }
                                    param.setResult(obj);
                                    XposedBridge.log("====之后===="+param.getResult());
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }catch (Exception e){
                    XposedBridge.log("寻找com.bixin.live.data.bean.room报错");
                }
            }
        });
    }
}
