package com.ceshi.android.ui;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.util.SharePrefUtil;
import com.ceshi.android.ui.fragment.FoundFragment;
import com.ceshi.android.ui.fragment.FragmentmineGestureLogin;
import com.ceshi.android.ui.fragment.FragmentmineLogin;
import com.ceshi.android.ui.fragment.MainFragment;
import com.ceshi.android.ui.fragment.MineFragment;
import com.ceshi.android.util.OkHttpClientUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.rg_main)
    public RadioGroup rg_main;
    @Bind(R.id.rb_main)
    public RadioButton rb_main;
    @Bind(R.id.rb_mine)
    public RadioButton rb_mine;
    @Bind(R.id.rb_found)
    public RadioButton rb_found;

    private int i;
    private int childCount;
    private long mTaskId;
    private DownloadManager downloadManager;
    private final String apkName = "爱生息.apk";
    private String lgname_earnings, mempsw_earnings, memcookie_earnings;
    public List<BaseFragment> mBaseFragment;
    private Fragment mContent;
    private String productId, activitys;

    /**
     * 选中的Fragment的对应的位置
     */
    private int position;
    private FragmentmineGestureLogin gestureLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        //初始化View
        initView();
        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
        checkVersionUpdate();//检查版本更新
    }

    /**
     *版本检查更新
     */
    private void checkVersionUpdate() {
        final OkHttpClient client = OkHttpClientUtil.getInstance();
        RequestBody formBody = new FormEncodingBuilder()
                .add("version", getVersionCode()+"")
                .build();
        final Request request = new Request.Builder()
                .url(Const.checkForUpdate)
                .post(formBody)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                        }

                        @Override
                        public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                            String resultStr = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(resultStr);
                                final String message = jsonObject.getString("message");
                                final String downLoadUrl = jsonObject.getString("downLoadUrl");
//                                final String downLoadUrl = "https://services.gradle.org/distributions/gradle-2.11-rc-3-src.zip";
                                boolean result = jsonObject.getBoolean("result");
                                Log.d(TAG,"foundActivity里面的result="+result+",message="+message+",downLoadUrl="+downLoadUrl);
                                if (result&&!"".equals(downLoadUrl)){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showUpdateDialog(message, downLoadUrl);
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showUpdateDialog(String msg, final String downloadUrl) {
        Log.d(TAG,"MainActivity里面的Dialog初始化...");
        View view = View.inflate(this, R.layout.dialog_version_update, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(view);
        final AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(true);
        Log.d(TAG,"MainActivity里面的Dialog显示");
        TextView tv_version_text = (TextView) view.findViewById(R.id.tv_version_text);
        tv_version_text.setText(msg);
        view.findViewById(R.id.btn_version_cancel).setOnClickListener(new View.OnClickListener() {//取消按钮监听器
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_version_ensure).setOnClickListener(new View.OnClickListener() {//确定按钮监听器
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (downloadUrl != null && !"".equals(downloadUrl)){
                    downloadLatestClient(downloadUrl);
                }
            }
        });
    }

    /**
     * 下载最新的客户端
     */
    private void downloadLatestClient(String downloadURL) {
        //创建下载任务,downloadUrl就是下载链接
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadURL));
        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir("/download/", apkName);
        request.setTitle("爱生息");
        //设置文件类型，可以在下载结束后自动打开该文件
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(downloadURL));
        request.setMimeType(mimeString);
        //获取下载管理器
        downloadManager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Log.d(TAG,"MainActivity里面的下载数据进入了...");
        //将下载任务加入下载队列，否则不会进行下载
        mTaskId = downloadManager.enqueue(request);
        registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //广播接受者，接收下载状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"MainActivity里面的广播接收器....");
            checkDownloadStatus();//检查下载状态
        }
    };

    //检查下载状态
    private void checkDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mTaskId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_SUCCESSFUL:
                    //下载完成安装APK
                    String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + apkName;
                    Log.d(TAG,"MainActivity里面的下载文件路径downloadPath="+downloadPath);
                    showShortToast("下载成功");
                    installAPK(new File(downloadPath));
                    break;
                case DownloadManager.STATUS_FAILED:
                    showShortToast("下载失败");
                    break;
            }
        }
    }

    //下载到本地后执行安装
    protected void installAPK(File file) {
        if (!file.exists()) return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://" + file.toString());
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        //在服务中开启activity必须设置flag,后面解释
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //获取版本信息
    private int getVersionCode() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        Log.d(TAG, "MainActivity里面的onNewIntent执行了");
        Intent intent2 = getIntent();
        boolean depositSuccess = intent2.getBooleanExtra("depositSuccess", false);
        boolean quitSuccess = intent2.getBooleanExtra("quitSuccess", false);
        boolean gestureLoginSuccess = intent2.getBooleanExtra("gestureLoginSuccess", false);
        boolean backFindFragment = intent2.getBooleanExtra("backFindFragment", false);
        Log.d(TAG, "MainActivity里面的gestureLoginSuccess=" + gestureLoginSuccess + ",quitSuccess==" + quitSuccess + ",depositSuccess==" + depositSuccess
                + ",backFindFragment==" + backFindFragment);
        if (quitSuccess) { //切换到登陆界面
//            rb_main.isChecked();
            rg_main.check(R.id.rb_mine);
            switchFrament(mContent,getFragment(3));
//            transaction.replace(R.id.fl,getFragment(3)).commitAllowingStateLoss();
        } else if (depositSuccess) {//切换到我的界面
            Log.d(TAG, "MainActivity里面的充值界面返回到主界面了");
//            switchFragment(FragmentInstanceManager.getInstance().getFragment(MineFragment.class));
//            switchFrament(mContent, getFragment(2));
//            switchFrament(getFragment(2), getFragment(3));
            rg_main.check(R.id.rb_mine);
            rb_mine.setChecked(true);
        } else if (backFindFragment) {
            Log.d(TAG, "发现界面条目跳转返回了");
            rg_main.check(R.id.rb_found);
            rb_found.setChecked(true);
        } else if (gestureLoginSuccess) {//
            Log.d(TAG, "MainActivity里面的手势输入成功了");
            rg_main.setVisibility(View.VISIBLE);
            switchFrament(getFragment(4), getFragment(2));
            rb_mine.setChecked(true);
        }
        super.onNewIntent(intent);
    }


    private void setListener() {
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中首页
        rg_main.check(R.id.rb_main);
//        Intent intent3 = getIntent();
//        Intent intent4 = getIntent();
//        boolean wantToLogin = intent3.getBooleanExtra("wantToLogin", false);
//        boolean depositSuccess = intent4.getBooleanExtra("depositSuccess", false);
//        if (wantToLogin | depositSuccess) {
//            //替换
//            //switchFragment(FragmentInstanceManager.getInstance().getFragment(FragmentmineLogin.class));
//            rg_main.check(R.id.rb_main);
//            rb_main.setChecked(true);
//        }
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_main://首页
                    position = 0;
                    break;
                case R.id.rb_found://发现
                    position = 1;
                    break;
                case R.id.rb_mine://我的
                    String lgname = SharePrefUtil.getString(MainActivity.this, SharePrefUtil.sp_phone, null);
                    String mempsw = SharePrefUtil.getString(MainActivity.this, SharePrefUtil.sp_pwd, null);
                    Log.d(TAG, "MainActivity里面的用户名和密码lgname=" + lgname + ",mempsw=" + mempsw);
                    if (lgname != null && mempsw != null) {
                        mApplication.mUserPhone = lgname;
                        mApplication.mUserMima = mempsw;
                        position = 2;  //我的界面
                    } else {
//                        showShortToast("请登录！");
                        position = 3;  //登陆界面
                    }
                    break;
                default:
                    position = 0;
                    break;
            }
            //根据位置得到对应的Fragment
            BaseFragment to = getFragment(position);
            //替换
            switchFrament(mContent, to);
        }
    }

    /**
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to   马上要切换到的Fragment，一会要显示
     */
    private void switchFrament(Fragment from, Fragment to) {

        if (from != to) {
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if (!to.isAdded()) {
                //to没有被添加
                //from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //添加to
                if (to != null) {
                    ft.add(R.id.fl, to).commitAllowingStateLoss();
                }

            } else {
                //to已经被添加
                // from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //显示to
                if (to != null) {
                    ft.show(to).commitAllowingStateLoss();
                }
            }
        }else {
            Log.e(TAG, "两个fragment相同");
        }
    }

    /**
     * 根据位置得到对应的Fragment
     *
     * @return
     */
    private BaseFragment getFragment(int position) {
        BaseFragment fragment = mBaseFragment.get(position);
        return fragment;
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new MainFragment());//首页Fragment
        mBaseFragment.add(new FoundFragment());//发现Fragment
        mBaseFragment.add(new MineFragment());//我的Fragment
        mBaseFragment.add(new FragmentmineLogin());//登陆Fragment

        String lgname = SharePrefUtil.getString(MainActivity.this, SharePrefUtil.sp_phone, null);
        String mempsw = SharePrefUtil.getString(MainActivity.this, SharePrefUtil.sp_pwd, null);
        mBaseFragment.add(new FragmentmineGestureLogin(lgname, mempsw));//手势fragment
        Log.e(TAG, "MainActivity的initFragment:调用了 常见手势图形密码" + mBaseFragment.get(4) + "");
    }


    @Override
    protected void onStart() {
        Log.d(TAG, "MainActivity里面的界面onStart()...");
        super.onStart();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    public static final int REQUEST_CODE = 110;


    //发现界面客服电话后的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户同意授权
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0353-288-7777"));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(intent);
                } else {
                    //用户拒绝授权
                }
                break;
        }
    }



}
