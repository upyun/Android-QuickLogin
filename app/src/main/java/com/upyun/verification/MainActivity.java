package com.upyun.verification;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.upyun.verifysdk.api.AuthPageEventListener;
import com.upyun.verifysdk.api.PreLoginListener;
import com.upyun.verifysdk.api.UpVerificationInterface;
import com.upyun.verifysdk.api.UpVerifyUIClickCallback;
import com.upyun.verifysdk.api.UpVerifyUiBuilder;
import com.upyun.verifysdk.api.VerifyListener;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private TextView tvLog;
    private Button btn_login;
    private Button btnPreLogin;
    private CheckBox isDialogModeCB;
    private boolean autoFinish = true;
    private static final int CENTER_ID = 1000;
    private static final int LOGIN_CODE_UNSET = -1562;
    private static final String LOGIN_CODE = "login_code";
    private static final String LOGIN_CONTENT = "login_content";
    private static final String LOGIN_OPERATOR = "login_operator";
    private static Bundle savedLoginState = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        requestPermissions();
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            String[] str = new String[2];
            str[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                str[1] = Manifest.permission.READ_PHONE_NUMBERS;
            } else {
                str[1] = Manifest.permission.READ_PHONE_STATE;
            }
            requestPermissions(str, 100);
        }
    }

    private void initView() {

        tvLog = findViewById(R.id.tv_log);
        tvLog.setOnClickListener(this);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btnPreLogin = findViewById(R.id.btn_pre_login);
        btnPreLogin.setOnClickListener(this);
        isDialogModeCB = findViewById(R.id.cb_dialog_mode);
        findViewById(R.id.cb_auto_finish).setOnClickListener(this);
        findViewById(R.id.btn_del_pre_login_cache).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != savedLoginState) {
            initLoginResult(savedLoginState);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (null != intent.getExtras()) {
            initLoginResult(intent.getExtras());
        }
    }

    private void initLoginResult(Bundle extras) {
        int loginCode = extras.getInt(LOGIN_CODE, LOGIN_CODE_UNSET);
        if (loginCode != LOGIN_CODE_UNSET) {
            String loginContent = extras.getString(LOGIN_CONTENT);
            String operator = extras.getString(LOGIN_OPERATOR);
            if (null != tvLog) {
                tvLog.setText("[" + loginCode + "]message=" + loginContent + ", operator=" + operator);
            }
        }
    }

    @Override
    public void onClick(View v) {
//        int result;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            if (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.R) {
//                result = checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS);
//            } else {
//                result = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
//            }
//            if (result != PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "[2016],msg = 当前缺少权限", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }

        Log.d(TAG, "is init success = " + UpVerificationInterface.isInitSuccess());
        switch (v.getId()) {
            case R.id.btn_pre_login:
                preLogin();
                break;
            case R.id.btn_login:
                loginAuth(isDialogModeCB.isChecked());
                break;
            case R.id.cb_auto_finish:
                autoFinish = ((CheckBox) v).isChecked();
                break;
            case R.id.btn_del_pre_login_cache:
                delPreLoginCache();
                break;
        }
    }

    private void delPreLoginCache() {
        UpVerificationInterface.clearPreLoginCache();
        tvLog.setText("删除成功");
    }

    private void preLogin() {
        boolean verifyEnable = UpVerificationInterface.checkVerifyEnable(this);
        if (!verifyEnable) {
            tvLog.setText("[2016],msg = 当前网络环境不支持认证");
            return;
        }
        showLoadingDialog();
        UpVerificationInterface.preLogin(this, 5000, new PreLoginListener() {
            @Override
            public void onResult(final int code, final String content) {
                savedLoginState = null;
                tvLog.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "[" + code + "]message=" + content);
                        tvLog.setText("[" + code + "]message=" + content);
                        MainActivity.this.dismissLoadingDialog();
                    }
                });
            }
        });
    }

    private void loginAuth(boolean isDialogMode) {
        boolean verifyEnable = UpVerificationInterface.checkVerifyEnable(this);
        if (!verifyEnable) {
            tvLog.setText("[2016],msg = 当前网络环境不支持认证");
            return;
        }
        showLoadingDialog();

        setUIConfig(isDialogMode);
        UpVerificationInterface.loginAuth(this, autoFinish, new VerifyListener() {
            @Override
            public void onResult(final int code, final String content, final String operator) {
                Log.d(TAG, "[" + code + "]message=" + content + ", operator=" + operator);
                Bundle bundle = new Bundle();
                bundle.putInt(LOGIN_CODE, code);
                bundle.putString(LOGIN_CONTENT, content);
                bundle.putString(LOGIN_OPERATOR, operator);
                savedLoginState = bundle;
                //这里通过static bundle保存数据是为了防止出现授权页方向和MainActivity不相同时，MainActivity被销毁重新初始化导致回调数据无法展示到MainActivity
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvLog.setText("[" + code + "]message=" + content + ", operator=" + operator);
                        dismissLoadingDialog();
                    }
                });
            }
        }, new AuthPageEventListener() {
            @Override
            public void onEvent(int cmd, String msg) {
                Log.d(TAG, "[onEvent]. [" + cmd + "]message=" + msg);
            }
        });
    }

    private void setUIConfig(boolean isDialogMode) {
        UpVerifyUiBuilder portrait = getPortraitBuilder(isDialogMode);
        UpVerifyUiBuilder landscape = getLandscapeBuilder(isDialogMode);

        //支持授权页设置横竖屏两套config，在授权页中触发横竖屏切换时，sdk自动选择对应的config加载。
        UpVerificationInterface.setCustomUIWithBuilder(portrait, landscape);
    }

    private UpVerifyUiBuilder getPortraitBuilder(boolean isDialogMode) {
        UpVerifyUiBuilder configBuilder = new UpVerifyUiBuilder();

        ImageView loadingView = new ImageView(getApplicationContext());
        loadingView.setImageResource(R.drawable.umcsdk_load_dot_white);
        RelativeLayout.LayoutParams loadingParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        loadingView.setLayoutParams(loadingParam);

        TextView textView = new TextView(this);
        textView.setText("其他方式");
        textView.setTextColor(0xFF1A97FF);
        RelativeLayout.LayoutParams textParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParam.addRule(RelativeLayout.CENTER_HORIZONTAL);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.umcsdk_anim_loading);

        if (isDialogMode) {
            //窗口竖屏
            textParam.setMargins(0, dp2Pix(this, 250.0f), 0, 0);
            textView.setLayoutParams(textParam);

            //自定义返回按钮示例
            ImageButton sampleReturnBtn = new ImageButton(getApplicationContext());
            sampleReturnBtn.setImageResource(R.drawable.umcsdk_return_bg);
            RelativeLayout.LayoutParams returnLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            returnLP.setMargins(10, 10, 0, 0);
            sampleReturnBtn.setLayoutParams(returnLP);

            configBuilder.setAuthBGImgPath("login_bg")
                    .setNavColor(0xff0086d0)
                    .setNavText("登录")
                    .setNavTextColor(0xffffffff)
                    .setNavReturnImgPath("umcsdk_return_bg")
                    .setLogoWidth(60)
                    .setLogoHeight(60)
                    .setLogoHidden(false)
                    .setNumberColor(0xff333333)
                    .setLogBtnText("一键登录")
                    .setSloganHidden(true)
                    .setLogBtnTextColor(0xffffffff)
                    .setLogBtnImgPath("umcsdk_login_btn_bg")
                    .setLogBtnWidth(280)
                    .setLogBtnHeight(48)
                    .setLogBtnTextSize(16)
                    .setAppPrivacyColor(0xff999999, 0xFF1A97FF)
                    .setPrivacyTextSize(12)
                    .setPrivacyText("我已阅读并同意", "并授权又拍获取本机号码")
                    .setUncheckedImgPath("ic_uncheck")
                    .setCheckedImgPath("ic_check")
                    .setPrivacyCheckboxSize(14)
                    .setSloganTextColor(0xff999999)
                    .setLogoOffsetY(25)
                    .setLogoImgPath("icon_login")
                    .setNumFieldOffsetY(130)
                    .setSloganOffsetY(160)
                    .setLogBtnOffsetY(184)
                    .setNumberSize(20)
                    .setPrivacyState(true)
                    .setNavTransparent(false)
                    .setPrivacyOffsetY(5)
                    .setDialogTheme(360, 390, 0, 0, false)
                    .setLoadingView(loadingView, animation)
                    .enableHintToast(true, Toast.makeText(getApplicationContext(), "checkbox未选中，自定义提示", Toast.LENGTH_SHORT))
                    .addCustomView(textView, false, new UpVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            MainActivity.this.startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    })
                    .addCustomView(sampleReturnBtn, true, new UpVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            Toast.makeText(MainActivity.this, "关闭授权页", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            //全屏竖屏
            textParam.setMargins(0, dp2Pix(this, 330.0f), 0, 0);
            textView.setLayoutParams(textParam);

            //导航栏按钮示例
            Button navBtn = new Button(this);
            navBtn.setText("导航栏按钮");
            navBtn.setTextColor(0xff3a404c);
            navBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            RelativeLayout.LayoutParams navBtnParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            navBtnParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            navBtn.setLayoutParams(navBtnParam);

            configBuilder.setAuthBGImgPath("login_bg")
                    .setNavColor(0xff0086d0)
                    .setNavText("登录")
                    .setNavTextColor(0xffffffff)
                    .setNavReturnImgPath("umcsdk_return_bg")
                    .setLogoWidth(60)
                    .setLogoHeight(60)
                    .setLogoHidden(false)
                    .setNumberColor(0xff333333)
                    .setLogBtnText("一键登录")
                    .setSloganHidden(true)
                    .setLogBtnTextColor(0xffffffff)
                    .setLogBtnImgPath("umcsdk_login_btn_bg")
                    .setLogBtnWidth(280)
                    .setLogBtnHeight(48)
                    .setLogBtnTextSize(16)
                    .setAppPrivacyColor(0xff999999, 0xFF1A97FF)
                    .setPrivacyTextSize(12)
                    .setPrivacyText("我已阅读并同意", "并授权又拍获取本机号码")
                    .setUncheckedImgPath("ic_uncheck")
                    .setCheckedImgPath("ic_check")
                    .setPrivacyCheckboxSize(14)
                    .setSloganTextColor(0xff999999)
                    .setLogoOffsetY(86)
                    .setLogoImgPath("icon_login")
                    .setNumFieldOffsetY(190)
                    .setSloganOffsetY(220)
                    .setLogBtnOffsetY(254)
                    .setNumberSize(20)
                    .setPrivacyState(true)
                    .setNavTransparent(false)
                    .setPrivacyOffsetY(35)
                    .addCustomView(textView, false, new UpVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            MainActivity.this.startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    });
        }
        return configBuilder;
    }

    private UpVerifyUiBuilder getLandscapeBuilder(boolean isDialogMode) {
        UpVerifyUiBuilder configBuilder = new UpVerifyUiBuilder();

        TextView textView = new TextView(this);
        textView.setText("其他方式");
        textView.setTextColor(0xFF1A97FF);
        RelativeLayout.LayoutParams textParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParam.addRule(RelativeLayout.CENTER_HORIZONTAL);

        if (isDialogMode) {
            //窗口横屏
            textParam.setMargins(0, dp2Pix(this, 250.0f), 0, 0);
            textView.setLayoutParams(textParam);

            //自定义返回按钮示例
            ImageButton sampleReturnBtn = new ImageButton(getApplicationContext());
            sampleReturnBtn.setImageResource(R.drawable.umcsdk_return_bg);
            RelativeLayout.LayoutParams returnLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            returnLP.setMargins(10, 10, 0, 0);
            sampleReturnBtn.setLayoutParams(returnLP);

            configBuilder.setAuthBGImgPath("login_bg")
                    .setNavColor(0xff0086d0)
                    .setNavText("登录")
                    .setNavTextColor(0xffffffff)
                    .setNavReturnImgPath("umcsdk_return_bg")
                    .setLogoWidth(60)
                    .setLogoHeight(60)
                    .setLogoHidden(false)
                    .setNumberColor(0xff333333)
                    .setLogBtnText("一键登录")
                    .setSloganHidden(true)
                    .setLogBtnTextColor(0xffffffff)
                    .setLogBtnImgPath("umcsdk_login_btn_bg")
                    .setAppPrivacyColor(0xff999999, 0xFF1A97FF)
                    .setPrivacyTextSize(12)
                    .setPrivacyText("我已阅读并同意", "，并授权又拍获取本机号码")
                    .setUncheckedImgPath("ic_uncheck")
                    .setCheckedImgPath("ic_check")
                    .setPrivacyCheckboxSize(14)
                    .setPrivacyState(true)
                    .setSloganTextColor(0xff999999)
                    .setLogoOffsetY(25)
                    .setLogoImgPath("icon_login")
                    .setNumFieldOffsetY(120)
                    .setSloganOffsetY(155)
                    .setLogBtnOffsetY(180)
                    .setPrivacyOffsetY(10)
                    .setDialogTheme(500, 350, 0, 0, false)
                    .enableHintToast(true, null)
                    .addCustomView(textView, false, new UpVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            MainActivity.this.startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    })
                    .addCustomView(sampleReturnBtn, true, null);
        } else {
            //全屏横屏
            textParam.setMargins(0, dp2Pix(this, 250.0f), 0, 0);
            textView.setLayoutParams(textParam);


            //导航栏按钮示例
            Button navBtn = new Button(this);
            navBtn.setText("导航栏按钮");
            navBtn.setTextColor(0xff3a404c);
            navBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            RelativeLayout.LayoutParams navBtnParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            navBtnParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            navBtn.setLayoutParams(navBtnParam);
            configBuilder
                    .setAuthBGImgPath("main_bg")
                    .setNavColor(0xff0086d0)
                    .setNavText("登录")
                    .setNavTextColor(0xffffffff)
                    .setNavReturnImgPath("umcsdk_return_bg")
                    .setLogoWidth(70)
                    .setLogoHeight(70)
                    .setLogoHidden(false)
                    .setNumberColor(0xff333333)
                    .setLogBtnText("一键登录")
                    .setSloganHidden(true)
                    .setLogBtnTextColor(0xffffffff)
                    .setLogBtnImgPath("umcsdk_login_btn_bg")
                    .setAppPrivacyColor(0xff999999, 0xFF1A97FF)
                    .setPrivacyTextSize(12)
                    .setPrivacyText("我已阅读并同意", "并授权又拍获取本机号码")
                    .setUncheckedImgPath("ic_uncheck")
                    .setCheckedImgPath("ic_check")
                    .setPrivacyCheckboxSize(14)
                    .setPrivacyState(true)
                    .setLogoOffsetY(30)
                    .setLogoImgPath("icon_login")
                    .setNumFieldOffsetY(150)
                    .setSloganOffsetY(185)
                    .setLogBtnOffsetY(210)
                    .setPrivacyOffsetY(10)
                    .addCustomView(textView, false, new UpVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            MainActivity.this.startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    })
                    .addNavControlView(navBtn, new UpVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            Toast.makeText(context, "导航栏自定义按钮", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        return configBuilder;
    }


    private AlertDialog alertDialog;

    public void showLoadingDialog() {
        dismissLoadingDialog();
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        alertDialog.setCancelable(false);
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK)
                    return true;
                return false;
            }
        });
        alertDialog.show();
        alertDialog.setContentView(R.layout.loading_alert);
        alertDialog.setCanceledOnTouchOutside(false);
    }

    public void dismissLoadingDialog() {
        if (null != alertDialog && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    private int dp2Pix(Context context, float dp) {
        try {
            float density = context.getResources().getDisplayMetrics().density;
            return (int) (dp * density + 0.5F);
        } catch (Exception e) {
            return (int) dp;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadingDialog();
    }
}
