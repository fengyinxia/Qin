package com.junzilan.qin;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws
            Throwable {
        if("com.dmzj.manhua".equals(loadPackageParam.packageName)){
            XposedHelpers.findAndHookMethod("com.dmzj.manhua.api.CApplication",loadPackageParam.classLoader, "attachBaseContext", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);

                    Context context = (Context) param.args[0];

                    ClassLoader classLoader = context.getClassLoader();

                    XposedHelpers.findAndHookMethod("com.dmzj.manhua.ad.adv.LTUnionADPlatform",classLoader, "LoadShowInfo", int.class,String.class, new XC_MethodReplacement() {

                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param)
                                throws Throwable {
                           XposedHelpers.callMethod(param.thisObject,"onAdVisibleView");
                           return null ;
                        }
                    });
                    XposedHelpers.findAndHookMethod("com.dmzj.manhua.ui.home.MainSceneCartoonActivity",classLoader, "generateMainLayout", new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                           ImageView S=(ImageView) XposedHelpers.getObjectField(param.thisObject,"gameView");
                           S.setVisibility(View.GONE);

                        }
                    });
                    XposedHelpers.findAndHookMethod("com.dmzj.manhua.ui.SettingAboutUsActivity",classLoader, "initData", new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            TextView S=(TextView) XposedHelpers.getObjectField(param.thisObject,"versionText");
                            S.setText("大妈之家绿化版");
                            S.setTextColor(0xFF17A98C);

                        }
                    });
             }
            });

        }
    }
}

