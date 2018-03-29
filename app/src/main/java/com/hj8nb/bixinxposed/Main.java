package com.hj8nb.bixinxposed;

import android.app.Application;
import android.content.Context;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XCallback;


/**
 * Created by ThundeRobot on 2018/2/24.
 */

public class Main implements IXposedHookLoadPackage,IXposedHookInitPackageResources {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                ClassLoader cl=((Context)param.args[0]).getClassLoader();
                Class<?> hookclass=null;
                try {
                    hookclass=cl.loadClass("com.bixin.live.data.bean.room.PrivateLimitBean");
                }catch (Exception e){
                    XposedBridge.log("寻找com.bixin.live.data.bean.room报错");
                    return;
                }
                XposedBridge.log("寻找com.bixin.live.data.bean.room成功");
                XposedHelpers.findAndHookMethod(hookclass, "getBsid", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult("");
                        XposedBridge.log("修改返回值getBsid");
                    }
                });
                XposedHelpers.findAndHookMethod(hookclass, "getCome", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(0);
                        XposedBridge.log("修改返回值getCome");
                    }
                });
                XposedHelpers.findAndHookMethod(hookclass, "getId", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(null);
                        XposedBridge.log("修改返回值getId");
                    }
                });
                XposedHelpers.findAndHookMethod(hookclass, "getMoney", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult("");
                        XposedBridge.log("修改返回值getMoney");
                    }
                });
                XposedHelpers.findAndHookMethod(hookclass, "getOnline", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(1);
                        XposedBridge.log("修改返回值");
                    }
                });
                XposedHelpers.findAndHookMethod(hookclass, "getPrerequisite", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(null);
                        XposedBridge.log("修改返回值getPrerequisite");
                    }
                });
                XposedHelpers.findAndHookMethod(hookclass, "getPtname", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(null);
                        XposedBridge.log("修改返回值getPtname");
                    }
                });
                XposedHelpers.findAndHookMethod(hookclass, "getPtid", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(null);
                        XposedBridge.log("修改返回值getPtid");
                    }
                });


            }
        });
    }


    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {

    }
}
