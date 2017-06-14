package com.ceshi.android.ui.fragment;

import android.Manifest;
import android.app.Dialog;
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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ceshi.android.R;
import com.ceshi.android.base.AsyncBitmapLoader;
import com.ceshi.android.base.BaseFragment;
import com.ceshi.android.base.Const;
import com.ceshi.android.base.HttpNet;
import com.ceshi.android.entity.MainPictureEntity;
import com.ceshi.android.ui.AboutusActivity;
import com.ceshi.android.ui.CustomerActivity;
import com.ceshi.android.ui.HelpCenterActivity;
import com.ceshi.android.ui.OfficialDataActivity;
import com.ceshi.android.util.OkHttpClientUtil;
import com.ceshi.android.util.ServicePhoneDialog;
import com.recker.flybanner.FlyBanner;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

//import static com.ishengxi.android.R.id.ll_found_idea;

/**
 * 发现界面
 * Created by ztr on 2016/04/25.
 */
public class FoundFragment extends BaseFragment implements View.OnClickListener {

    public static final int INT = 7;
    @Bind(R.id.ll_found_women)
    LinearLayout ll_found_women;  //关于我们
    @Bind(R.id.ll_found_gd)
    LinearLayout ll_found_gd;  //官方动态
    @Bind(R.id.ll_found_help)
    LinearLayout ll_found_help;  //帮助中心
    @Bind(R.id.ll_found_kf)
    LinearLayout ll_found_kf;  //官方客服
//    @Bind(ll_found_idea)
//    LinearLayout ll_found_idea;  //意见反馈
    @Bind(R.id.ll_found_version)
    LinearLayout ll_found_version;   //当前版本
    @Bind(R.id.iv_zhuce)
    ImageView iv_zhuce;
    @Bind(R.id.widget_title)
    TextView widgetTitle;
    @Bind(R.id.tv_found_version)
    TextView tv_found_version;
    @Bind(R.id.find_service_phone)
    ImageView service_phone;

    String picInfo;
    private String loadImages;
    private AsyncBitmapLoader asyncBitmapLoader = new AsyncBitmapLoader();
    private static final String FOUNDFILENAME = "foundjinr.txt";
    FileOutputStream fileOutputStream = null;
    private ByteArrayOutputStream byteArrayOutputStream;
    private HttpNet httpNet;
    MainPictureEntity mResult;
    private final String apkName ="爱生息.apk";
    private DownloadManager downloadManager;
    private long mTaskId;
    private FlyBanner mFlyBanner;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_find, container, false);
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        Log.d("TAG", "请求发现界面初始化");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews() {
        Log.d("TAG", "请求发现界面初始化界面");
        iv_zhuce.setVisibility(View.GONE);
        widgetTitle.setText("发现");
        setVersionName();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void initEvents() {
        ll_found_gd.setOnClickListener(this);
        ll_found_help.setOnClickListener(this);
        ll_found_kf.setOnClickListener(this);
        ll_found_women.setOnClickListener(this);
//        ll_found_idea.setOnClickListener(this);
        ll_found_version.setOnClickListener(this);
        service_phone.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case ll_found_idea://意见反馈
//                startActivity(FindIdeaActivity.class);
//                break;
            case R.id.ll_found_version: //下载最新版本
                checkVersionUpdate();//检查版本更新
                break;
            case R.id.ll_found_gd://官方动态
                startActivity(OfficialDataActivity.class);
                break;
            case R.id.ll_found_help://帮助中心
                startActivity(HelpCenterActivity.class);
                break;
            case R.id.ll_found_kf://QQ客服
                startActivity(CustomerActivity.class);
                break;
            case R.id.ll_found_women://关于我们
                startActivity(AboutusActivity.class);
                break;
            case R.id.find_service_phone://客服电话
                servicePhonedialog();
                break;
        }
    }

    //客服电话
    private void servicePhonedialog() {
        final ServicePhoneDialog servicePhoneDialog = new ServicePhoneDialog(mContext);

        servicePhoneDialog.setYesOnclickListener("拨打", new ServicePhoneDialog.onYesOnclickListener() {
            public static final int REQUEST_CODE = 110;

            @Override
            public void onYesClick() {
                //检查权限
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //进入到这里代表没有权限.
                    if(ActivityCompat.shouldShowRequestPermissionRationale(mActivity,Manifest.permission.CALL_PHONE)){
                        //已经禁止提示了
                        Toast.makeText(mActivity, "您已禁止该权限，需要重新开启。", Toast.LENGTH_SHORT).show();
                    }else{
                        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:0353-288-7777"));
                    startActivity(intent);
                }
                servicePhoneDialog.dismiss();
            }
        });
        servicePhoneDialog.setNoOnclickListener("取消", new ServicePhoneDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                servicePhoneDialog.dismiss();
            }
        });
        servicePhoneDialog.show();

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
                                if(result&&"".equals(downLoadUrl)){
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            newestVersiondialog();
                                        }
                                    });
                                }
                                if (result&&!"".equals(downLoadUrl)){
                                    getActivity().runOnUiThread(new Runnable() {
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
        View view = View.inflate(mContext, R.layout.dialog_version_update, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        builder.setView(view);
        final AlertDialog dialog = builder.show();
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
        downloadManager= (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Log.d(TAG,"MainActivity里面的下载数据进入了...");
        //将下载任务加入下载队列，否则不会进行下载
        mTaskId = downloadManager.enqueue(request);
        mContext.registerReceiver(receiver,
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
        mContext.startActivity(intent);
    }


    //获取版本信息
    private void setVersionName() {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            String version = info.versionName;
            tv_found_version.setText("V." + version);
        } catch (Exception e) {
            e.printStackTrace();
            tv_found_version.setText("V.1.0.0");
        }
    }

    //获取版本信息
    private int getVersionCode() {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Dialog dialog;


    //已经是最新版本对话框
    private void newestVersiondialog() {

        dialog = new Dialog(mActivity,R.style.ActionSheetDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.newest_version_dialog,null);
        dialog.setContentView(inflate);
        dialog.show();
    }
}
