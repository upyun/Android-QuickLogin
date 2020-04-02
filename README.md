<h1 id="android-sdk-api">Android 一键登录 SDK</h1>
------------------
##SDK 概述

 - 此 SDK 整合了三大运营商的网关认证能力，为开发者提供了一键登录功能，优化用户注册/登录、号码验证体验，提高安全性
 - 使用场景：注册，登录，验证
 - 目前SDK只支持 Android 4.0 或以上版本的手机系统

##SDK 接入指南

 - 将 SDK aar 文件放入工程 lib 下。
 - 在工程 module 的 gradle 文件中加入

 	<pre><code>    repositories {
   					flatDir {
        					dirs 'libs'   // aar目录
   				 	}
   				 }

	</code></pre>
 - 在 dependencies 下加入
 	<pre><code> implementation(name: 'verification-release', ext: 'aar')
	</code></pre>
	
 - 添加 manifest 变量
 	<pre><code> manifestPlaceholders = [
                // 你的应用 Appkey
                UPYUN_APPKEY : "5195ff1bded312cfe2ac46aa",
                //你的应用渠道
                UPYUN_CHANNEL: "default_developer",
        ]
	</code></pre>
 - 包名签名设置
 	包名需要与控制后台申请包名一致，签名通过[签名工具](https://github.com/upyun/Android-QuickLogin/apk/AppSignGet.apk)获取后上传控制后台。
 - Demo APK [下载演示](https://github.com/upyun/Android-QuickLogin/apk/loginDemo.apk)。


<h2 id="sdk">SDK接口说明</h2>
<ul>
<li>UpVerificationInterface，包含SDK所有接口</li>
</ul>
<h2 id="sdk_1">SDK初始化（支持超时时间配置、回调参数）</h2>
<h3 id="_2">接口定义</h3>
<ul>
<li><strong><em>UpVerificationInterface.init(Context context,int timeOut,RequestCallback<String> callback)</em></strong><ul>
<li>接口说明：<ul>
<li>初始化接口。建议在Application的onCreate中调用</li>
</ul>
</li>
<li>参数说明：<ul>
<li>context：android的上下文</li>
<li>timeOut: 超时时间（毫秒）,有效取值范围(0,30000],若小于等于0或大于30000则取默认值10000.推荐设置为5000-10000.</li>
<li>callback：回调接口</li>
</ul>
</li>
<li>回调说明：
    <strong><em>onResult(int code, String msg)</em></strong>
        + code: 返回码，8000代表初始化成功，其他为失败，详见错误码描述
        + msg：结果描述</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>    UpVerificationInterface.init(this, 5000, new RequestCallback&lt;String&gt;() {
                @Override
                public void onResult(int code, String msg) {
                    Log.d("tag","code = " + code + " msg = " + msg);
                }
            });
</code></pre>

<h2 id="sdk_2">SDK初始化（新增回调参数）</h2>
<h3 id="_4">接口定义</h3>
<ul>
<li><strong><em>UpVerificationInterface.init(Context context,RequestCallback<String> callback)</em></strong><ul>
<li>接口说明：<ul>
<li>初始化接口。建议在Application的onCreate中调用</li>
</ul>
</li>
<li>参数说明：<ul>
<li>context：android的上下文</li>
<li>callback：回调接口</li>
</ul>
</li>
<li>回调说明：<strong><em>onResult(int code, String msg)</em></strong><ul>
<li>code: 返回码，8000代表初始化成功，其他为失败，详见错误码描述</li>
<li>msg：结果描述</li>
</ul>
</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>    UpVerificationInterface.init(this, new RequestCallback&lt;String&gt;() {
                @Override
                public void onResult(int code, String msg) {
                    Log.d("tag","code = " + code + " msg = " + msg);
                }
            });
</code></pre>

<h2 id="sdk_3">SDK初始化</h2>
<h3 id="_6">接口定义</h3>
<ul>
<li><strong><em>UpVerificationInterface.init(Context context)</em></strong><ul>
<li>接口说明：<ul>
<li>初始化接口。建议在Application的onCreate中调用</li>
</ul>
</li>
<li>参数说明：<ul>
<li>context：android的上下文</li>
</ul>
</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>    UpVerificationInterface.init(this);
</code></pre>

<h2 id="sdk_4">获取sdk初始化是否成功标识</h2>
<h3 id="_8">接口的定义</h3>
<ul>
<li><strong><em>UpVerificationInterface.isInitSuccess()</em></strong><ul>
<li>接口说明：<ul>
<li>获取sdk是否整体初始化成功的标识</li>
</ul>
</li>
<li>返回结果<ul>
<li>boolean : true - 成功，false - 失败</li>
</ul>
</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>    boolean isSuccess = UpVerificationInterface.isInitSuccess();
</code></pre>

<h2 id="sdkdebug">SDK设置debug模式</h2>
<h3 id="_10">接口定义</h3>
<ul>
<li><strong><em>UpVerificationInterface.setDebugMode(boolean enable)</em></strong><ul>
<li>接口说明：<ul>
<li>设置是否开启debug模式。true则会打印更多的日志信息。建议在init接口之前调用。</li>
</ul>
</li>
<li>参数说明：<ul>
<li>enable：debug开关</li>
</ul>
</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>    UpVerificationInterface.setDebugMode(true);
</code></pre>

<h2 id="sdk_5">SDK判断网络环境是否支持</h2>
<h3 id="_12">接口定义</h3>
<ul>
<li><strong><em>UpVerificationInterface.checkVerifyEnable(Context context)</em></strong><ul>
<li>接口说明：<ul>
<li>判断当前的手机网络环境是否可以使用认证。</li>
</ul>
</li>
<li>参数说明：<ul>
<li>context：android的上下文</li>
</ul>
</li>
<li>返回说明：<ul>
<li>返回true代表可以使用；返回false建议使用其他验证方式。</li>
</ul>
</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>    boolean verifyEnable = UpVerificationInterface.checkVerifyEnable(this);
        if(!verifyEnable){
            Log.d(TAG,"当前网络环境不支持认证");
            return;
        }
</code></pre>


<h2 id="sdk_7">SDK一键登录预取号</h2>
<ul>
<li><strong>sdk会缓存预取号结果，提升之后授权页拉起速度。所以建议拉起授权页前，比如在开屏页或者业务入口页预先调用此接口进行预取号。</strong></li>
<li><strong>请求成功后，不要频繁重复调用。</strong></li>
<li><strong>不要在预取号回调中重复调用预取号或者拉起授权页接口。</strong></li>
</ul>
<h3 id="_20">接口定义</h3>
<ul>
<li><strong><em>UpVerificationInterface.preLogin(Context context, int timeOut, PreLoginListener listener){</em></strong><ul>
<li>接口说明：<ul>
<li>验证当前运营商网络是否可以进行一键登录操作，该方法会缓存取号信息，提高一键登录效率。建议发起一键登录前先调用此方法。</li>
</ul>
</li>
<li>参数说明：<ul>
<li>context：android的上下文</li>
<li>timeOut: 超时时间（毫秒）,有效取值范围(0,10000],若小于等于0则取默认值5000.大于10000则取10000, 为保证预取号的成功率，建议设置为3000-5000ms.</li>
<li>listener：接口回调</li>
</ul>
</li>
<li>回调说明：
<strong><em>onResult(int code, String  content)</em></strong><ul>
<li>code: 返回码，7000代表获取成功，其他为失败，详见错误码描述</li>
<li>content：调用结果信息描述</li>
</ul>
</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>        UpVerificationInterface.preLogin(this, 5000,new PreLoginListener() {
            @Override
            public void onResult(final int code, final String content) {
                Log.d(TAG,"[" + code + "]message=" +  content );
            }
        });
</code></pre>

<h2 id="sdk_8">SDK清除预取号缓存</h2>
<h3 id="_22">接口定义</h3>
<ul>
<li><strong><em>UpVerificationInterface.clearPreLoginCache(){</em></strong><ul>
<li>接口说明：<ul>
<li>清除sdk当前预取号结果缓存。</li>
</ul>
</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>        UpVerificationInterface.clearPreLoginCache();
</code></pre>

<h2 id="sdk_9">SDK请求授权一键登录</h2>
<ul>
<li><strong>一键登录需要依赖预取号结果，如果没有预取号，一键登录时会自动预取号。</strong></li>
<li><strong>建议拉起授权页前，比如在开屏页或者业务入口页预先调用此接口进行预取号，可以提升授权页拉起速度，优化体验。</strong></li>
<li><strong>一键登录请求成功后，不要频繁重复调用。运营商会限制单位时间内请求次数。</strong></li>
<li><strong>不要在一键登录回调中重复调用预取号或者拉起授权页接口。</strong></li>
</ul>
<h3 id="_24">接口的定义</h3>
<ul>
<li><strong><em>UpVerificationInterface.loginAuth(final Context context, LoginSettings settings, final VerifyListener listener)</em></strong><ul>
<li>接口说明：<ul>
<li>调起一键登录授权页面，在用户授权后获取loginToken，同时支持授权页事件监听</li>
</ul>
</li>
<li>参数说明：<ul>
<li>context：android的上下文</li>
<li>settings：登录接口设置项。</li>
<li>listener：登录授权结果回调</li>
</ul>
</li>
<li>回调说明：
VerifyListener<br />
<strong><em>onResult(int code, String  content, String operator)</em></strong><ul>
<li>code: 返回码，6000代表loginToken获取成功，6001代表loginToken获取失败，其他返回码详见描述</li>
<li>content：返回码的解释信息，若获取成功，内容信息代表loginToken。</li>
<li>operator：成功时为对应运营商，CM代表中国移动，CU代表中国联通，CT代表中国电信。失败时可能为null  </li>
</ul>
</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>    LoginSettings settings = new LoginSettings();
    settings.setAutoFinish(true);//设置登录完成后是否自动关闭授权页
    settings.setTimeout(15 * 1000);//设置超时时间，单位毫秒。 合法范围（0，30000],范围以外默认设置为10000
    settings.setAuthPageEventListener(new AuthPageEventListener() {
        @Override
        public void onEvent(int cmd, String msg) {
            //do something...
        }
    });//设置授权页事件监听
    UpVerificationInterface.loginAuth(this, settings, new VerifyListener() {
         @Override
              public void onResult(int code, String content, String operator) {
                 if (code == 6000){
                    Log.d(TAG, "code=" + code + ", token=" + content+" ,operator="+operator);
                }else{
                    Log.d(TAG, "code=" + code + ", message=" + content);
                }
              }
          });
</code></pre>

<h3 id="_26">接口的定义</h3>
<ul>
<li><strong><em>UpVerificationInterface.loginAuth(final Context context, boolean autoFinish, final VerifyListener listener, final AuthPageEventListener authPageEventListener)</em></strong><ul>
<li>接口说明：<ul>
<li>调起一键登录授权页面，在用户授权后获取loginToken，同时支持授权页事件监听</li>
</ul>
</li>
<li>参数说明：<ul>
<li>context：android的上下文</li>
<li>boolean：是否自动关闭授权页，true - 是，false - 否</li>
<li>listener：登录授权结果回调</li>
<li>authPageEventListener：授权页事件回调</li>
</ul>
</li>
<li>回调说明：<ul>
<li>VerifyListener<br />
<strong><em>onResult(int code, String  content, String operator)</em></strong><ul>
<li>code: 返回码，6000代表loginToken获取成功，6001代表loginToken获取失败，其他返回码详见描述</li>
<li>content：返回码的解释信息，若获取成功，内容信息代表loginToken。</li>
<li>operator：成功时为对应运营商，CM代表中国移动，CU代表中国联通，CT代表中国电信。失败时可能为null  </li>
</ul>
</li>
<li>AuthPageEventListener<br />
<strong><em>onEvent(int code, String  content)</em></strong><ul>
<li>cmd: 返回码，具体见事件返回码表。</li>
<li>content：内容描述。</li>
</ul>
</li>
</ul>
</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>    UpVerificationInterface.loginAuth(this, false, new VerifyListener() {
         @Override
              public void onResult(int code, String content, String operator) {
                 if (code == 6000){
                    Log.d(TAG, "code=" + code + ", token=" + content+" ,operator="+operator);
                }else{
                    Log.d(TAG, "code=" + code + ", message=" + content);
                }
              }
          },new AuthPageEventListener() {
              @Override
              public void onEvent(int cmd, String msg) {
                  Log.d(TAG, "[onEvent]. [" + cmd + "]message=" + msg);
              }
          });
</code></pre>

<h3 id="_27">事件返回码</h3>
<table>
<thead>
<tr>
<th align="center">code</th>
<th align="center">message</th>
<th align="center">备注</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">1</td>
<td align="center">login activity closed.</td>
<td align="center">授权页关闭事件</td>
</tr>
<tr>
<td align="center">2</td>
<td align="center">login activity started.</td>
<td align="center">授权页打开事件</td>
</tr>
<tr>
<td align="center">3</td>
<td align="center">carrier privacy clicked.</td>
<td align="center">运营商协议点击事件</td>
</tr>
<tr>
<td align="center">4</td>
<td align="center">privacy 1 clicked.</td>
<td align="center">自定义协议1点击事件</td>
</tr>
<tr>
<td align="center">5</td>
<td align="center">privacy 2 clicked.</td>
<td align="center">自定义协议2点击事件</td>
</tr>
<tr>
<td align="center">6</td>
<td align="center">checkbox checked.</td>
<td align="center">协议栏checkbox变为选中事件</td>
</tr>
<tr>
<td align="center">7</td>
<td align="center">checkbox unchecked.</td>
<td align="center">协议栏checkbox变为未选中事件</td>
</tr>
<tr>
<td align="center">8</td>
<td align="center">login button clicked.</td>
<td align="center">一键登录按钮（可用状态下）点击事件</td>
</tr>
</tbody>
</table>
<h2 id="sdk_10">SDK请求授权一键登录</h2>
<h3 id="_29">接口的定义</h3>
<ul>
<li><strong><em>UpVerificationInterface.loginAuth(final Context context, boolean autoFinish, final VerifyListener listener)</em></strong><ul>
<li>接口说明：<ul>
<li>调起一键登录授权页面，在用户授权后获取loginToken</li>
</ul>
</li>
<li>参数说明：<ul>
<li>context：android的上下文</li>
<li>boolean：是否自动关闭授权页，true - 是，false - 否；若此字段设置为false，请在收到一键登录回调后调用SDK提供的关闭授权页面方法。</li>
<li>listener：接口回调</li>
</ul>
</li>
<li>回调说明：
<strong><em>onResult(int code, String  content, String operator)</em></strong><ul>
<li>code: 返回码，6000代表loginToken获取成功，6001代表loginToken获取失败，其他返回码详见描述</li>
<li>content：返回码的解释信息，若获取成功，内容信息代表loginToken。</li>
<li>operator：成功时为对应运营商，CM代表中国移动，CU代表中国联通，CT代表中国电信。失败时可能为null</li>
</ul>
</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>    UpVerificationInterface.loginAuth(this, false, new VerifyListener() {
         @Override
              public void onResult(int code, String content, String operator) {
                 if (code == 6000){
                    Log.d(TAG, "code=" + code + ", token=" + content+" ,operator="+operator);
                }else{
                    Log.d(TAG, "code=" + code + ", message=" + content);
                }
              }
          });
</code></pre>

<p><strong><em>说明</em></strong>：获取到一键登录的loginToken后，将其返回给应用服务端，从服务端调用<a href="https://docs.jiguang.cn/jverification/server/rest_api/loginTokenVerify_api/">REST API</a>来获取手机号码</p>
<h2 id="sdk_11">SDK请求授权一键登录</h2>
<h3 id="_31">接口的定义</h3>
<ul>
<li><strong><em>UpVerificationInterface.loginAuth(final Context context, final VerifyListener listener)</em></strong><ul>
<li>接口说明：<ul>
<li>调起一键登录授权页面，在用户授权后获取loginToken</li>
</ul>
</li>
<li>参数说明：<ul>
<li>context：android的上下文</li>
<li>listener：接口回调</li>
</ul>
</li>
<li>回调说明：
<strong><em>onResult(int code, String  content, String operator)</em></strong><ul>
<li>code: 返回码，6000代表loginToken获取成功，6001代表loginToken获取失败，其他返回码详见描述</li>
<li>content：返回码的解释信息，若获取成功，内容信息代表loginToken。</li>
<li>operator：成功时为对应运营商，CM代表中国移动，CU代表中国联通，CT代表中国电信。失败时可能为null</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
</li>
</ul>
<pre><code>    UpVerificationInterface.loginAuth(this, new VerifyListener() {
         @Override
              public void onResult(int code, String content, String operator) {
                 if (code == 6000){
                    Log.d(TAG, "code=" + code + ", token=" + content+" ,operator="+operator);
                }else{
                    Log.d(TAG, "code=" + code + ", message=" + content);
                }
              }
          });
</code></pre>

<h2 id="sdk_12">SDK关闭授权页面</h2>
<h3 id="_33">接口的定义</h3>
<ul>
<li><strong><em>dismissLoginAuthActivity(boolean needCloseAnim, RequestCallback<String> callback)</em></strong><ul>
<li>接口说明：<ul>
<li>关闭登录授权页，如果当前授权正在进行，则loginAuth接口会立即触发6002取消回调。</li>
</ul>
</li>
<li>参数说明：<ul>
<li>needCloseAnim：是否需要展示默认授权页关闭的动画（如果有）。true - 需要，false - 不需要</li>
</ul>
</li>
<li>回调说明：
RequestCallback<String><br />
<strong><em>onResult(int code, String desc)</em></strong><ul>
<li>code: 返回码，0 标识成功关闭授权页</li>
<li>desc：返回码的描述信息。</li>
</ul>
</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>    UpVerificationInterface.dismissLoginAuthActivity(true, new RequestCallback&lt;String&gt;() {
        @Override
        public void onResult(int code, String desc) {
            Log.i(TAG, "[dismissLoginAuthActivity] code = " + code + " desc = " + desc);
        }
    });
</code></pre>

<h3 id="_35">接口的定义</h3>
<ul>
<li><strong><em>UpVerificationInterface.dismissLoginAuthActivity()</em></strong><ul>
<li>接口说明：<ul>
<li>关闭登录授权页，如果当前授权正在进行，则loginAuth接口会立即触发6002取消回调。</li>
</ul>
</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>    UpVerificationInterface.dismissLoginAuthActivity();
</code></pre>

<h2 id="sdkui">SDK自定义授权页面UI样式</h2>
<h3 id="_37">接口的定义</h3>
<ul>
<li><strong><em>UpVerificationInterface.setCustomUIWithBuilder(UpVerifyUiBuilder uiBuilder)</em></strong><ul>
<li>接口说明：<ul>
<li>修改授权页面主题，开发者可以通过 setCustomUIWithBuilder 方法修改授权页面主题，需在 <em>loginAuth</em> 接口之前调用</li>
</ul>
</li>
<li>参数说明：<ul>
<li>uiConfig：主题配置对象，开发者在 UpVerifyUiBuilder.java 类中调用对应的方法配置授权页中对应的元素</li>
</ul>
</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>    UpVerifyUiBuilder uiBuilder = new UpVerifyUiBuilder()
                    .setAuthBGImgPath("main_bg")
                    .setNavColor(0xff0086d0)
                    .setNavText("登录")
                    .setNavTextColor(0xffffffff)
                    .setNavReturnImgPath("umcsdk_return_bg")
                    .setLogoWidth(70)
                    .setLogoHeight(70)
                    .setLogoHidden(false)
                    .setNumberColor(0xff333333)
                    .setLogBtnText("本机号码一键登录")
                    .setLogBtnTextColor(0xffffffff)
                    .setLogBtnImgPath("umcsdk_login_btn_bg")
                    .setAppPrivacyOne("应用自定义服务条款一","https://www.jiguang.cn/about")
                    .setAppPrivacyTwo("应用自定义服务条款二","https://www.jiguang.cn/about")
                    .setAppPrivacyColor(0xff666666,0xff0085d0)
                    .setUncheckedImgPath("umcsdk_uncheck_image")
                    .setCheckedImgPath("umcsdk_check_image")
                    .setSloganTextColor(0xff999999)
                    .setLogoOffsetY(50)
                    .setLogoImgPath("logo_cm")
                    .setNumFieldOffsetY(170)
                    .setSloganOffsetY(230)
                    .setLogBtnOffsetY(254)
                    .setNumberSize(18)
                    .setPrivacyState(false)
                    .setNavTransparent(false)
                    .addCustomView(mBtn, true, new UpVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            Toast.makeText(context,"动态注册的其他按钮",Toast.LENGTH_SHORT).show();
                        }
                    }).addCustomView(mBtn2, false, new UpVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            Toast.makeText(context,"动态注册的其他按钮222",Toast.LENGTH_SHORT).show();
                        }
                    }).addNavControlView(navBtn, new UpVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                        Toast.makeText(context,"导航栏按钮点击",Toast.LENGTH_SHORT).show();
                        }
                    }).setPrivacyOffsetY(30);
    UpVerificationInterface.setCustomUIWithBuilder(uiBuilder);

</code></pre>

<h2 id="sdk_13">SDK授权页面添加自定义控件</h2>
<h3 id="_39">接口的定义</h3>
<ul>
<li>
<p><strong><em>addCustomView(View view, boolean finishFlag,UpVerifyUIClickCallback callback)</em></strong></p>
<ul>
<li>接口说明： <ul>
<li>在授权页面添加自定义控件</li>
</ul>
</li>
<li>参数说明：<ul>
<li>view：开发者传入自定义的控件，开发者需要提前设置好控件的布局属性，SDK只支持RelativeLayout布局</li>
<li>finishFlag：是否在授权页面通过自定义控件的点击finish授权页面</li>
<li>callback： 自定义控件的点击回调</li>
</ul>
</li>
<li>
<p>回调说明： <strong><em>onClicked(Context context, View view)</em></strong>               </p>
<ul>
<li>context：android的上下文</li>
<li>view：自定义的控件的对象</li>
</ul>
</li>
<li>
<p>调用示例：</p>
</li>
</ul>
</li>
</ul>
<pre><code>        Button mBtn = new Button(this);
        mBtn.setText("其他方式登录");
        RelativeLayout.LayoutParams mLayoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams1.setMargins(0, LoginUIHelper.dp2Pix(this,450.0f),0,0);
        mBtn.setLayoutParams(mLayoutParams1);
        new UpVerifyUiBuilderUpVerifyUiBuilder().addCustomView(mBtn, true, new UpVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            Toast.makeText(context,"动态注册的其他按钮",Toast.LENGTH_SHORT).show();
                        }
                    });                                    

</code></pre>

<h2 id="sdk_14">SDK授权页面顶部导航栏添加自定义控件</h2>
<h3 id="_41">接口的定义</h3>
<ul>
<li>
<p><strong><em>addNavControlView(View view, UpVerifyUIClickCallback callback)</em></strong></p>
<ul>
<li>接口说明：<ul>
<li>在授权页中顶部导航栏添加自定义控件</li>
</ul>
</li>
<li>参数说明：<ul>
<li>view：开发者传入自定义的控件，开发者需要提前设置好控件的布局属性，SDK只支持RelativeLayout布局</li>
<li>callback： 自定义控件的点击回调</li>
</ul>
</li>
<li>回调说明：<ul>
<li><strong><em>onClicked(Context context, View view)</em></strong><ul>
<li>context：android的上下文</li>
<li>view：自定义的控件的对象</li>
</ul>
</li>
</ul>
</li>
<li>调用示例：</li>
</ul>
</li>
</ul>
<pre><code>        Button navBtn = new Button(this);
        navBtn.setText("导航栏按钮");
        RelativeLayout.LayoutParams navBtnParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        navBtnParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        navBtn.setLayoutParams(navBtnParam);
        new UpVerifyUiBuilderUpVerifyUiBuilder.addNavControlView(navBtn, new UpVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            Toast.makeText(context,"导航栏按钮点击",Toast.LENGTH_SHORT).show();
                        }
                    });

</code></pre>

<h2 id="UpVerifyUiBuilderUpVerifyUiBuilder">UpVerifyUiBuilder 配置元素说明</h2>
<p><strong><em>x轴未设置偏移则所有组件默认横向居中</em></strong></p>
<ul>
<li>
<p>设置授权页背景</p>
<ul>
<li>说明：图片会默认拉伸铺满整个屏幕，适配不同尺寸手机，建议使用 .9.png 图片来解决适配问题。</li>
</ul>
<table>
<thead>
<tr>
<th align="center">方法</th>
<th align="center">参数类型</th>
<th align="center">说明</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">setAuthBGImgPath</td>
<td align="center">String</td>
<td align="center">设置背景图片</td>
</tr>
</tbody>
</table>
</li>
<li>
<p>状态栏 </p>
<table>
<thead>
<tr>
<th align="center">方法</th>
<th align="center">参数类型</th>
<th align="center">说明</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">setStatusBarColorWithNav</td>
<td align="center">boolean</td>
<td align="center">设置状态栏与导航栏同色。仅在android 5.0以上设备生效。</td>
</tr>
<tr>
<td align="center">setStatusBarDarkMode</td>
<td align="center">boolean</td>
<td align="center">设置状态栏暗色模式。仅在android 6.0以上设备生效。</td>
</tr>
<tr>
<td align="center">setStatusBarTransparent</td>
<td align="center">boolean</td>
<td align="center">设置状态栏是否透明。仅在android 4.4以上设备生效。 </td>
</tr>
<tr>
<td align="center">setStatusBarHidden</td>
<td align="center">boolean</td>
<td align="center">设置状态栏是否隐藏。</td>
</tr>
<tr>
<td align="center">setVirtualButtonTransparent</td>
<td align="center">boolean</td>
<td align="center">设置虚拟按键栏背景是否透明。</td>
</tr>
</tbody>
</table>
</li>
<li>
<p>授权页导航栏</p>
</li>
</ul>
<table>
<thead>
<tr>
<th align="center">方法</th>
<th align="center">参数类型</th>
<th align="center">说明</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">setNavColor</td>
<td align="center">int</td>
<td align="center">设置导航栏颜色</td>
</tr>
<tr>
<td align="center">setNavText</td>
<td align="center">String</td>
<td align="center">设置导航栏标题文字</td>
</tr>
<tr>
<td align="center">setNavTextColor</td>
<td align="center">int</td>
<td align="center">设置导航栏标题文字颜色</td>
</tr>
<tr>
<td align="center">setNavReturnImgPath</td>
<td align="center">String</td>
<td align="center">设置导航栏返回按钮图标</td>
</tr>
<tr>
<td align="center">setNavTransparent</td>
<td align="center">boolean</td>
<td align="center">设置导航栏背景是否透明。默认不透明。</td>
</tr>
<tr>
<td align="center">setNavTextSize</td>
<td align="center">int</td>
<td align="center">设置导航栏标题文字字体大小（单位：sp）。</td>
</tr>
<tr>
<td align="center">setNavReturnBtnHidden</td>
<td align="center">boolean</td>
<td align="center">设置导航栏返回按钮是否隐藏。默认不隐藏。</td>
</tr>
<tr>
<td align="center">setNavReturnBtnWidth</td>
<td align="center">int</td>
<td align="center">设置导航栏返回按钮宽度。</td>
</tr>
<tr>
<td align="center">setNavReturnBtnHeight</td>
<td align="center">int</td>
<td align="center">设置导航栏返回按钮高度。</td>
</tr>
<tr>
<td align="center">setNavReturnBtnOffsetX</td>
<td align="center">int</td>
<td align="center">设置导航栏返回按钮距屏幕左侧偏移。</td>
</tr>
<tr>
<td align="center">setNavReturnBtnRightOffsetX</td>
<td align="center">int</td>
<td align="center">设置导航栏返回按钮距屏幕右侧偏移。</td>
</tr>
<tr>
<td align="center">setNavReturnBtnOffsetY</td>
<td align="center">int</td>
<td align="center">设置导航栏返回按钮距上端偏移。</td>
</tr>
<tr>
<td align="center">setNavHidden</td>
<td align="center">boolean</td>
<td align="center">设置导航栏是否隐藏。</td>
</tr>
</tbody>
</table>
<ul>
<li>
<p>授权页logo</p>
<table>
<thead>
<tr>
<th align="center">方法</th>
<th align="center">参数类型</th>
<th align="center">说明</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">setLogoWidth</td>
<td align="center">int</td>
<td align="center">设置logo宽度（单位：dp）</td>
</tr>
<tr>
<td align="center">setLogoHeight</td>
<td align="center">int</td>
<td align="center">设置logo高度（单位：dp）</td>
</tr>
<tr>
<td align="center">setLogoHidden</td>
<td align="center">boolean</td>
<td align="center">隐藏logo</td>
</tr>
<tr>
<td align="center">setLogoOffsetY</td>
<td align="center">int</td>
<td align="center">设置logo相对于标题栏下边缘y偏移</td>
</tr>
<tr>
<td align="center">setLogoImgPath</td>
<td align="center">String</td>
<td align="center">设置logo图片</td>
</tr>
<tr>
<td align="center">setLogoOffsetX</td>
<td align="center">int</td>
<td align="center">设置logo相对于屏幕左边x轴偏移。</td>
</tr>
<tr>
<td align="center">setLogoOffsetBottomY</td>
<td align="center">int</td>
<td align="center">设置logo相对于屏幕底部y轴偏移。</td>
</tr>
</tbody>
</table>
</li>
<li>
<p>授权页号码栏</p>
</li>
</ul>
<table>
<thead>
<tr>
<th align="center">方法</th>
<th align="center">参数类型</th>
<th align="center">说明</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">setNumberColor</td>
<td align="center">int</td>
<td align="center">设置手机号码字体颜色</td>
</tr>
<tr>
<td align="center">setNumberSize</td>
<td align="center">Number</td>
<td align="center">设置手机号码字体大小（单位：sp）。</td>
</tr>
<tr>
<td align="center">setNumFieldOffsetY</td>
<td align="center">int</td>
<td align="center">设置号码栏相对于标题栏下边缘y偏移</td>
</tr>
<tr>
<td align="center">setNumFieldOffsetX</td>
<td align="center">int</td>
<td align="center">设置号码栏相对于屏幕左边x轴偏移。</td>
</tr>
<tr>
<td align="center">setNumberFieldOffsetBottomY</td>
<td align="center">int</td>
<td align="center">设置号码栏相对于屏幕底部y轴偏移。</td>
</tr>
<tr>
<td align="center">setNumberFieldWidth</td>
<td align="center">int</td>
<td align="center">设置号码栏宽度。</td>
</tr>
<tr>
<td align="center">setNumberFieldHeight</td>
<td align="center">int</td>
<td align="center">设置号码栏高度。</td>
</tr>
</tbody>
</table>
<ul>
<li>授权页登录按钮</li>
</ul>
<table>
<thead>
<tr>
<th align="center">方法</th>
<th align="center">参数类型</th>
<th align="center">说明</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">setLogBtnText</td>
<td align="center">String</td>
<td align="center">设置登录按钮文字</td>
</tr>
<tr>
<td align="center">setLogBtnTextColor</td>
<td align="center">int</td>
<td align="center">设置登录按钮文字颜色</td>
</tr>
<tr>
<td align="center">setLogBtnImgPath</td>
<td align="center">String</td>
<td align="center">设置授权登录按钮图片</td>
</tr>
<tr>
<td align="center">setLogBtnOffsetY</td>
<td align="center">int</td>
<td align="center">设置登录按钮相对于标题栏下边缘y偏移</td>
</tr>
<tr>
<td align="center">setLogBtnOffsetX</td>
<td align="center">int</td>
<td align="center">设置登录按钮相对于屏幕左边x轴偏移。</td>
</tr>
<tr>
<td align="center">setLogBtnWidth</td>
<td align="center">int</td>
<td align="center">设置登录按钮宽度。</td>
</tr>
<tr>
<td align="center">setLogBtnHeight</td>
<td align="center">int</td>
<td align="center">设置登录按钮高度。</td>
</tr>
<tr>
<td align="center">setLogBtnTextSize</td>
<td align="center">int</td>
<td align="center">设置登录按钮字体大小。</td>
</tr>
<tr>
<td align="center">setLogBtnBottomOffsetY</td>
<td align="center">int</td>
<td align="center">设置登录按钮相对屏幕底部y轴偏移。</td>
</tr>
</tbody>
</table>
<ul>
<li>授权页隐私栏</li>
</ul>
<table>
<thead>
<tr>
<th align="center">方法</th>
<th align="center">参数类型</th>
<th align="left">说明</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">setAppPrivacyOne</td>
<td align="center">String,String</td>
<td align="left">设置开发者隐私条款1名称和URL(名称，url)</td>
</tr>
<tr>
<td align="center">setAppPrivacyTwo</td>
<td align="center">String,String</td>
<td align="left">设置开发者隐私条款2名称和URL(名称，url)</td>
</tr>
<tr>
<td align="center">setAppPrivacyColor</td>
<td align="center">int,int</td>
<td align="left">设置隐私条款名称颜色(基础文字颜色，协议文字颜色)</td>
</tr>
<tr>
<td align="center">setPrivacyOffsetY</td>
<td align="center">int</td>
<td align="left">设置隐私条款相对于授权页面底部下边缘y偏移</td>
</tr>
<tr>
<td align="center">setCheckedImgPath</td>
<td align="center">String</td>
<td align="left">设置复选框选中时图片</td>
</tr>
<tr>
<td align="center">setUncheckedImgPath</td>
<td align="center">String</td>
<td align="left">设置复选框未选中时图片</td>
</tr>
<tr>
<td align="center">setPrivacyState</td>
<td align="center">boolean</td>
<td align="left">设置隐私条款默认选中状态，默认不选中。</td>
</tr>
<tr>
<td align="center">setPrivacyOffsetX</td>
<td align="center">int</td>
<td align="left">设置隐私条款相对于屏幕左边x轴偏移。</td>
</tr>
<tr>
<td align="center">setPrivacyTextCenterGravity</td>
<td align="center">boolean</td>
<td align="left">设置隐私条款文字是否居中对齐（默认左对齐）。</td>
</tr>
<tr>
<td align="center">setPrivacyText</td>
<td align="center">String,String,String,String</td>
<td align="left">设置隐私条款名称外的文字。<br>如：登录即同意...和...、...并使用本机号码登录<br>参数1为："登录即同意"。<br>参数2为："和"。<br>参数3为："、"。<br>参数4为："并使用本机号码登录"。<br></td>
</tr>
<tr>
<td align="center">setPrivacyTextSize</td>
<td align="center">int</td>
<td align="left">设置隐私条款文字字体大小（单位：sp）。</td>
</tr>
<tr>
<td align="center">setPrivacyTopOffsetY</td>
<td align="center">int</td>
<td align="left">设置隐私条款相对导航栏下端y轴偏移。</td>
</tr>
<tr>
<td align="center">setPrivacyCheckboxHidden</td>
<td align="center">boolean</td>
<td align="left">设置隐私条款checkbox是否隐藏。</td>
</tr>
<tr>
<td align="center">setPrivacyCheckboxSize</td>
<td align="center">int</td>
<td align="left">设置隐私条款checkbox尺寸。</td>UpVerifyUiBuilder
</tr>
<tr>
<td align="center">setPrivacyWithBookTitleMark</td>
<td align="center">boolean</td>
<td align="left">设置隐私条款运营商协议名是否加书名号。</td>
</tr>
<tr>
<td align="center">setPrivacyCheckboxInCenter</td>
<td align="center">boolean</td>
<td align="left">设置隐私条款checkbox是否相对协议文字纵向居中。默认居顶。</td>
</tr>
<tr>
<td align="center">setPrivacyTextWidth</td>
<td align="center">int</td>
<td align="left">设置隐私条款文字栏宽度，单位dp。</td>
</tr>
<tr>
<td align="center">enableHintToast</td>
<td align="center">boolean Toast</td>
<td align="left">协议栏checkbox未选中时，点击登录按钮是否弹出toast提示用户勾选协议，默认不弹。支持自定义Toast。</td>
</tr>
</tbody>
</table>
<ul>
<li>
<p>授权页隐私协议web页面</p>
<table>
<thead>
<tr>
<th align="center">方法</th>
<th align="center">参数类型</th>
<th align="center">说明</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">setPrivacyNavColor</td>
<td align="center">int</td>
<td align="center">设置协议展示web页面导航栏背景颜色。</td>
</tr>
<tr>
<td align="center">setPrivacyNavTitleTextColor</td>
<td align="center">int</td>
<td align="center">设置协议展示web页面导航栏标题文字颜色。</td>
</tr>
<tr>
<td align="center">setPrivacyNavTitleTextSize</td>
<td align="center">int</td>
<td align="center">设置协议展示web页面导航栏标题文字大小（sp）。</td>
</tr>
<tr>
<td align="center">setPrivacyNavReturnBtn</td>
<td align="center">View</td>
<td align="center">设置协议展示web页面导航栏返回按钮图标。</td>
</tr>
<tr>
<td align="center">setAppPrivacyNavTitle1</td>
<td align="center">String</td>
<td align="center">设置自定义协议1对应web页面导航栏文字内容。</td>
</tr>
<tr>
<td align="center">setAppPrivacyNavTitle2</td>
<td align="center">String</td>
<td align="center">设置自定义协议2对应web页面导航栏文字内容。</td>
</tr>
<tr>
<td align="center">setPrivacyStatusBarColorWithNav</td>
<td align="center">boolean</td>
<td align="center">设置授权协议web页面状态栏与导航栏同色。仅在android 5.0以上设备生效。</td>
</tr>
<tr>
<td align="center">setPrivacyStatusBarDarkMode</td>
<td align="center">boolean</td>
<td align="center">设置授权协议web页面状态栏暗色模式。仅在android 6.0以上设备生效。</td>
</tr>
<tr>
<td align="center">setPrivacyStatusBarTransparent</td>
<td align="center">boolean</td>
<td align="center">设置授权协议web页面状态栏是否透明。仅在android 4.4以上设备生效。</td>
</tr>
<tr>
<td align="center">setPrivacyStatusBarHidden</td>
<td align="center">boolean</td>
<td align="center">设置授权协议web页面状态栏是否隐藏。</td>
</tr>
<tr>
<td align="center">setPrivacyVirtualButtonTransparent</td>
<td align="center">boolean</td>
<td align="center">设置授权协议web页面虚拟按键栏背景是否透明。</td>
</tr>
</tbody>
</table>
</li>
<li>
<p>授权页slogan</p>
</li>
</ul>
<table>
<thead>
<tr>
<th align="center">方法</th>
<th align="center">参数类型</th>
<th align="center">说明</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">setSloganTextColor</td>
<td align="center">int</td>
<td align="center">设置移动slogan文字颜色</td>
</tr>
<tr>
<td align="center">setSloganOffsetY</td>
<td align="center">int</td>
<td align="center">设置slogan相对于标题栏下边缘y偏移</td>
</tr>
<tr>
<td align="center">setSloganOffsetX</td>
<td align="center">int</td>
<td align="center">设置slogan相对于屏幕左边x轴偏移。</td>
</tr>
<tr>
<td align="center">setSloganBottomOffsetY</td>
<td align="center">int</td>
<td align="center">设置slogan相对于屏幕底部下边缘y轴偏移。</td>
</tr>
<tr>
<td align="center">setSloganTextSize</td>
<td align="center">int</td>
<td align="center">设置slogan字体大小。</td>
</tr>
<tr>
<td align="center">setSloganHidden</td>
<td align="center">int</td>
<td align="center">设置slogan是否隐藏。</td>
</tr>
</tbody>
</table>
<ul>
<li>自定义loading view</li>
</ul>
<table>
<thead>
<tr>
<th align="center">方法</th>
<th align="center">参数类型</th>
<th align="center">说明</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">setLoadingView</td>
<td align="center">View,Animation</td>
<td align="center">设置login过程中展示的loading view以及动画效果。</td>
</tr>
</tbody>
</table>
<ul>
<li>授权页动画</li>
</ul>
<table>
<thead>
<tr>
<th align="center">方法</th>
<th align="center">参数类型</th>
<th align="center">说明</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">setNeedStartAnim</td>
<td align="center">boolean</td>
<td align="center">设置拉起授权页时是否需要显示默认动画。默认展示。</td>
</tr>
<tr>
<td align="center">setNeedCloseAnim</td>
<td align="center">boolean</td>
<td align="center">设置关闭授权页时是否需要显示默认动画。默认展示。</td>
</tr>
</tbody>
</table>
<ul>
<li>开发者自定义控件</li>
</ul>
<table>
<thead>
<tr>
<th align="center">方法</th>
<th align="center">参数类型</th>
<th align="center">说明</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">addCustomView</td>
<td align="center">见以上方法定义</td>
<td align="center">在授权页空白处添加自定义控件以及点击监听</td>
</tr>
<tr>
<td align="center">addNavControlView</td>
<td align="center">见以上方法定义</td>
<td align="center">在授权页面顶部导航栏添加自定义控件以及点击监听</td>
</tr>
</tbody>
</table>
<h2 id="_42">授权页弹窗模式</h2>
<h3 id="_43">支持的版本</h3>
<p>开始支持的版本 2.3.8</p>
<h3 id="_44">接口定义</h3>
<ul>
<li>
<p><strong><em>setDialogTheme(int dialogWidth, int dialogHeight, int offsetX, int offsetY, boolean isBottom)</em></strong></p>
<ul>
<li>接口说明：<ul>
<li>设置授权页为弹窗模式</li>
</ul>
</li>
<li>
<p>参数说明：</p>
<ul>
<li>dialogWidth:窗口宽度，单位dp</li>
<li>dialogHeight:窗口高度，单位dp</li>
<li>offsetX:窗口相对屏幕中心的x轴偏移量，单位dp</li>
<li>offsetY:窗口相对屏幕中心的y轴偏移量，单位dp</li>
<li>isBottom: 窗口是否居屏幕底部。设置后offsetY将失效</li>
</ul>
</li>
<li>
<p>调用示例：</p>
</li>
</ul>
</li>
</ul>
<pre><code>    new UpVerifyUiBuilder().setDialogTheme(410, 390, 0, 0, false)
</code></pre>

<h3 id="_45">窗口模式样式设置</h3>
<h4 id="manifestactivitystyle">在manifest中为授权页activity设置窗口样式style</h4>
<p>AndroidManifest.xml</p>
<pre><code>&lt;activity android:name="cn.jiguang.verifysdk.CtLoginActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:theme="@style/ActivityDialogStyle"   &lt;!-- 设置自定义style --&gt;
            android:screenOrientation="unspecified"
            android:launchMode="singleTop"&gt;
&lt;/activity&gt;
</code></pre>

<h4 id="style">style中增加具体弹窗样式</h4>
<p>res/values/styles.xml</p>
<pre><code>&lt;style name="ActivityDialogStyle"&gt;
         &lt;!--隐藏action bar和title bar--&gt;
        &lt;item name="android:windowActionBar"&gt;false&lt;/item&gt;
        &lt;item name="android:windowNoTitle"&gt;true&lt;/item&gt;
        &lt;!--背景透明--&gt;
        &lt;item name="android:windowIsTranslucent"&gt;true&lt;/item&gt;
        &lt;!--dialog圆角--&gt;
        &lt;item name="android:windowBackground"&gt;@drawable/dialog_bg&lt;/item&gt;
&lt;/style&gt;
</code></pre>

<h4 id="_46">定义窗口圆角属性</h4>
<p>res/drawable/dialog_bg.xml</p>
<pre><code>&lt;shape xmlns:android="http://schemas.android.com/apk/res/android"&gt;
    &lt;corners android:radius="5dp"/&gt;
&lt;/shape&gt;
</code></pre>

<h2 id="ui">授权页横竖屏UI动态切换</h2>
<h3 id="_48">接口定义</h3>
<ul>
<li>
<p><strong><em>setCustomUIWithBuilder(UpVerifyUiBuilder uiBuilderPortrait,UpVerifyUiBuilder uiBuilderLandscape)</em></strong></p>
<ul>
<li>接口说明：</li>
<li>修改授权页面主题，支持传入竖屏和横屏两套config。sdk会根据当前横竖屏状态动态切换。需在每次调用 <em>loginAuth</em> 接口之前调用。</li>
<li>参数说明：</li>
<li>uiConfigPortrait:竖屏config</li>
<li>
<p>uiConfigLandscape:横屏config</p>
</li>
<li>
<p>调用示例：</p>
</li>
</ul>
</li>
</ul>
<pre><code>


			UpVerifyUiBuilder portrait = new UpVerifyUiBuilder();
    		 	   portrait.setAuthBGImgPath("main_bg")
                        .setNavColor(0xff0086d0)
                        .setNavText("登录")
                        .setNavTextColor(0xffffffff)
                        .setNavReturnImgPath("umcsdk_return_bg")
                        .setLogoWidth(70)
                        .setLogoHeight(70)
                        .setLogoHidden(false)
                        .setNumberColor(0xff333333)
                        .setLogBtnText("本机号码一键登录")
                        .setLogBtnTextColor(0xffffffff)
                        .setLogBtnImgPath("umcsdk_login_btn_bg")
                        .setAppPrivacyOne("应用自定义服务条款一", "https://www.jiguang.cn/about")
                        .setAppPrivacyTwo("应用自定义服务条款二", "https://www.jiguang.cn/about")
                        .setAppPrivacyColor(0xff666666, 0xff0085d0)
                        .setUncheckedImgPath("umcsdk_uncheck_image")
                        .setCheckedImgPath("umcsdk_check_image")
                        .setSloganTextColor(0xff999999)
                        .setLogoOffsetY(50)
                        .setLogoImgPath("logo_cm")
                        .setNumFieldOffsetY(190)
                        .setSloganOffsetY(220)
                        .setLogBtnOffsetY(254)
                        .setNumberSize(18)
                        .setPrivacyState(false)
                        .setNavTransparent(false);

			
			UpVerifyUiBuilder landscape = new UpVerifyUiBuilder();
    		landscape.setAuthBGImgPath("main_bg")
                        .setNavColor(0xff0086d0)
                        .setNavText("登录")
                        .setNavTextColor(0xffffffff)
                        .setNavReturnImgPath("umcsdk_return_bg")
                        .setLogoWidth(70)
                        .setLogoHeight(70)
                        .setLogoHidden(false)
                        .setNumberColor(0xff333333)
                        .setLogBtnText("本机号码一键登录")
                        .setLogBtnTextColor(0xffffffff)
                        .setLogBtnImgPath("umcsdk_login_btn_bg")
                        .setAppPrivacyOne("应用自定义服务条款一", "https://www.jiguang.cn/about")
                        .setAppPrivacyTwo("应用自定义服务条款二", "https://www.jiguang.cn/about")
                        .setAppPrivacyColor(0xff666666, 0xff0085d0)
                        .setUncheckedImgPath("umcsdk_uncheck_image")
                        .setCheckedImgPath("umcsdk_check_image")
                        .setSloganTextColor(0xff999999)
                        .setLogoOffsetY(30)
                        .setLogoImgPath("logo_cm")
                        .setNumFieldOffsetY(150)
                        .setSloganOffsetY(185)
                        .setLogBtnOffsetY(210)
                        .setPrivacyOffsetY(30);

    UpVerificationInterface.setCustomUIWithBuilder(portrait, landscape);
</code></pre>

<p><strong><em>同时需要在manifest对应授权页Activity：LoginAuthActivity、CtLoginActivity中配置<code>android:configChanges="orientation|keyboardHidden|screenSize"</code>属性</em></strong> </p>
<p><strong><em>targetSDKVersion &gt; 26的应用，在8.0系统上如果指定了授权页方向如："android:screenOrientation="portrait"", 会报错Only fullscreen opaque activities can request orientation</em></strong> </p>
<p>解决方法： </p>
<ol>
<li>将授权页"android:screenOrientation"设置为"portrait"、"landscape"之外的值。  </li>
<li>targetSDKVersion 改成 &lt;= 26  </li>
</ol>
<h2 id="_49">错误码</h2>
<table>
<thead>
<tr>
<th align="center">code</th>
<th align="center">message</th>
<th align="center">备注</th>
</tr>
</thead>
<tbody>
<tr>
<td align="center">1000</td>
<td align="center">verify consistent</td>
<td align="center">手机号验证一致</td>
</tr>
<tr>
<td align="center">1001</td>
<td align="center">verify not consistent</td>
<td align="center">手机号验证不一致</td>
</tr>
<tr>
<td align="center">1002</td>
<td align="center">unknown result</td>
<td align="center">未知结果</td>
</tr>
<tr>
<td align="center">1003</td>
<td align="center">token expired</td>
<td align="center">token失效</td>
</tr>
<tr>
<td align="center">1004</td>
<td align="center">sdk verify has been closed</td>
<td align="center">SDK发起认证未开启</td>
</tr>
<tr>
<td align="center">1005</td>
<td align="center">包名和 AppKey 不匹配</td>
<td align="center">请检查客户端配置的包名与官网对应 Appkey 应用下配置的包名是否一致</td>
</tr>
<tr>
<td align="center">1006</td>
<td align="center">frequency of verifying single number is beyond the maximum limit</td>
<td align="center">同一号码自然日内认证消耗超过限制</td>
</tr>
<tr>
<td align="center">1007</td>
<td align="center">beyond daily frequency limit</td>
<td align="center">appKey自然日认证消耗超过限制</td>
</tr>
<tr>
<td align="center">1008</td>
<td align="center">AppKey 非法</td>
<td align="center">请到官网检查此应用信息中的 appkey，确认无误</td>
</tr>
<tr>
<td align="center">1009</td>
<td align="center"></td>
<td align="center">请到官网检查此应用的应用详情；更新应用中集成的极光SDK至最新</td>
</tr>
<tr>
<td align="center">1010</td>
<td align="center">verify interval is less than the minimum limit</td>
<td align="center">同一号码连续两次提交认证间隔过短</td>
</tr>
<tr>
<td align="center">1011</td>
<td align="center">appSign invalid</td>
<td align="center">应用签名错误，检查签名与Portal设置的是否一致</td>
</tr>
<tr>
<td align="center">2000</td>
<td align="center">内容为token</td>
<td align="center">获取token成功</td>
</tr>
<tr>
<td align="center">2001</td>
<td align="center">fetch token failed</td>
<td align="center">获取token失败</td>
</tr>
<tr>
<td align="center">2002</td>
<td align="center">init failed</td>
<td align="center">SDK初始化失败</td>
</tr>
<tr>
<td align="center">2003</td>
<td align="center">network not reachable</td>
<td align="center">网络连接不通</td>
</tr>
<tr>
<td align="center">2004</td>
<td align="center">get uid failed</td>
<td align="center">极光服务注册失败</td>
</tr>
<tr>
<td align="center">2005</td>
<td align="center">request timeout</td>
<td align="center">请求超时</td>
</tr>
<tr>
<td align="center">2006</td>
<td align="center">fetch config failed</td>
<td align="center">获取应用配置失败</td>
</tr>
<tr>
<td align="center">2007</td>
<td align="center">内容为异常信息</td>
<td align="center">验证遇到代码异常</td>
</tr>
<tr>
<td align="center">2008</td>
<td align="center">Token requesting, please try again later</td>
<td align="center">正在获取token中，稍后再试</td>
</tr>
<tr>
<td align="center">2009</td>
<td align="center">verifying, please try again later</td>
<td align="center">正在认证中，稍后再试</td>
</tr>
<tr>
<td align="center">2010</td>
<td align="center">don't have READ_PHONE_STATE permission</td>
<td align="center">未开启读取手机状态权限</td>
</tr>
<tr>
<td align="center">2011</td>
<td align="center">内容为异常信息</td>
<td align="center">获取配置时代码异常</td>
</tr>
<tr>
<td align="center">2012</td>
<td align="center">内容为异常信息</td>
<td align="center">获取token时代码异常</td>
</tr>
<tr>
<td align="center">2013</td>
<td align="center">内容为具体错误原因</td>
<td align="center">网络发生异常</td>
</tr>
<tr>
<td align="center">2014</td>
<td align="center">internal error while requesting token</td>
<td align="center">请求token时发生内部错误</td>
</tr>
<tr>
<td align="center">2016</td>
<td align="center">network type not supported</td>
<td align="center">当前网络环境不支持认证</td>
</tr>
<tr>
<td align="center">2017</td>
<td align="center">carrier config invalid</td>
<td align="center">运营商配置错误</td>
</tr>
<tr>
<td align="center">4001</td>
<td align="center">parameter invalid</td>
<td align="center">参数错误。请检查参数，比如是否手机号格式不对</td>
</tr>
<tr>
<td align="center">4014</td>
<td align="center">appkey is blocked</td>
<td align="center">功能被禁用</td>
</tr>
<tr>
<td align="center">4018</td>
<td align="center"></td>
<td align="center">没有足够的余额</td>
</tr>
<tr>
<td align="center">4031</td>
<td align="center"></td>
<td align="center">不是认证SDK用户</td>
</tr>
<tr>
<td align="center">4032</td>
<td align="center"></td>
<td align="center">获取不到用户配置</td>
</tr>
<tr>
<td align="center">4033</td>
<td align="center">appkey is not support login</td>
<td align="center">不是一键登录用户</td>
</tr>
<tr>
<td align="center">5000</td>
<td align="center">bad server</td>
<td align="center">服务器未知错误</td>
</tr>
<tr>
<td align="center">6000</td>
<td align="center">内容为token</td>
<td align="center">获取loginToken成功</td>
</tr>
<tr>
<td align="center">6001</td>
<td align="center">fetch loginToken failed</td>
<td align="center">获取loginToken失败</td>
</tr>
<tr>
<td align="center">6002</td>
<td align="center">fetch loginToken canceled</td>
<td align="center">用户取消获取loginToken</td>
</tr>
<tr>
<td align="center">6003</td>
<td align="center">UI 资源加载异常</td>
<td align="center">未正常添加sdk所需的资源文件</td>
</tr>
<tr>
<td align="center">6004</td>
<td align="center">authorization requesting, please try again later</td>
<td align="center">正在登录中，稍后再试</td>
</tr>
<tr>
<td align="center">6006</td>
<td align="center">prelogin scrip expired.</td>
<td align="center">预取号结果超时，需要重新预取号</td>
</tr>
<tr>
<td align="center">7000</td>
<td align="center">preLogin success</td>
<td align="center">sdk 预取号成功</td>
</tr>
<tr>
<td align="center">7001</td>
<td align="center">preLogin failed</td>
<td align="center">sdk 预取号失败</td>
</tr>
<tr>
<td align="center">7002</td>
<td align="center">preLogin requesting, please try again later</td>
<td align="center">正在预取号中，稍后再试</td>
</tr>
<tr>
<td align="center">8000</td>
<td align="center">init success</td>
<td align="center">初始化成功</td>
</tr>
<tr>
<td align="center">8004</td>
<td align="center">init failed</td>
<td align="center">初始化失败，详见日志</td>
</tr>
<tr>
<td align="center">8005</td>
<td align="center">init timeout</td>
<td align="center">初始化超时，稍后再试</td>
</tr>
<tr>
<td align="center">-994</td>
<td align="center">网络连接超时</td>
<td align="center"></td>
</tr>
<tr>
<td align="center">-996</td>
<td align="center">网络连接断开</td>
<td align="center"></td>
</tr>
<tr>
<td align="center">-997</td>
<td align="center">注册失败/登录失败</td>
<td align="center">（一般是由于没有网络造成的）如果确保设备网络正常，还是一直遇到此问题，则还有另外一个原因：JPush 服务器端拒绝注册。而这个的原因一般是：你当前 App 的 Android 包名以及 AppKey，与你在 Portal 上注册的应用的 Android 包名与 AppKey 不相同。</td>
</tr>
</tbody>
</table></article>
    <footer/>
            <li><a href="#sdk">SDK接口说明</a></li>
            <li><a href="#sdk_1">SDK初始化（支持超时时间配置、回调参数）</a></li>
            <li><a href="#sdk_2">SDK初始化（新增回调参数）</a></li>
            <li><a href="#sdk_3">SDK初始化</a></li>
            <li><a href="#sdk_4">获取sdk初始化是否成功标识</a></li>
            <li><a href="#sdkdebug">SDK设置debug模式</a></li>
            <li><a href="#sdk_5">SDK判断网络环境是否支持</a></li>
            <li><a href="#sdktoken">SDK获取号码认证token（新）</a></li>
            <li><a href="#sdktoken_1">SDK获取号码认证token（旧）</a></li>
            <li><a href="#sdk_6">SDK发起号码认证</a></li>
            <li><a href="#sdk_7">SDK一键登录预取号</a></li>
            <li><a href="#sdk_8">SDK清除预取号缓存</a></li>
            <li><a href="#sdk_9">SDK请求授权一键登录（新）</a></li>
            <li><a href="#sdk_10">SDK请求授权一键登录（旧）</a></li>
            <li><a href="#sdk_11">SDK请求授权一键登录（旧）</a></li>
            <li><a href="#sdk_12">SDK关闭授权页面</a></li>
            <li><a href="#sdkui">SDK自定义授权页面UI样式</a></li>
            <li><a href="#sdk_13">SDK授权页面添加自定义控件</a></li>
            <li><a href="#sdk_14">SDK授权页面顶部导航栏添加自定义控件</a></li>
            <li><a href="#UpVerifyUiBuilder">UpVerifyUiBuilderUpVerifyUiBuilder配置元素说明</a></li>
            <li><a href="#_42">授权页弹窗模式</a></li>
            <li><a href="#ui">授权页横竖屏UI动态切换</a></li>
            <li><a href="#_49">错误码</a></li>