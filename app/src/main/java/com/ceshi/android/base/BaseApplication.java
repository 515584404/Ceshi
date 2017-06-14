package com.ceshi.android.base;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ceshi.android.R;
import com.ceshi.android.base.util.SharePrefUtil;
import com.ceshi.android.base.util.UILImageLoader;
import com.ceshi.android.base.util.UILPauseOnScrollListener;
import com.ceshi.android.db.MySharedPreferences;
import com.ceshi.android.util.Logger;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import org.xutils.x;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import cn.finalteam.galleryfinal.BuildConfig;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 */
public class BaseApplication extends Application {
    //Application的全局托管
    private static BaseApplication mInstence = null;

    public MySharedPreferences mSharedPreferences;
    public String mUserPhone, mUserMima;
    public String mUserCookie;
    public String mYouquCode, mYouquName;
    public double latitude, longitude;// 纬度，经度
    public String regionName = "";
    private List<Activity> mActivityList = new LinkedList<>();
    private RequestQueue mRequestQueue;



    public static BaseApplication getInstence() {
        return mInstence;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstence = this;
        mSharedPreferences = new MySharedPreferences(getApplicationContext());
        initImageLoaderConfiguration();//初始化图片加载框架
        //初始化个人数据
        mUserPhone = SharePrefUtil.getString(getApplicationContext(), SharePrefUtil.sp_name, null);
        mUserMima = SharePrefUtil.getString(getApplicationContext(), SharePrefUtil.sp_uuid, null);
        mUserCookie= SharePrefUtil.getString(getApplicationContext(), SharePrefUtil.sp_cookie,null);

        Logger.d("BaseApplication", "用户已经登录,mUserName" + mUserPhone
                + ",mUserUuid:" + mUserMima);
        mYouquCode = SharePrefUtil.getString(getApplicationContext(), SharePrefUtil.sp_youqu_citycode, null);
        mYouquName = SharePrefUtil.getString(getApplicationContext(), SharePrefUtil.sp_youqu_cityName, null);
        //初始化定位
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        initGalleryFinal();
        x.Ext.init(this);   //Xutils初始化

        //有盟分享配置
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);

    }
    {
        //有盟分享
        PlatformConfig.setWeixin("1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setQQZone("1106127021", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }

    /**
     * 获取volley的队列
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    private void initGalleryFinal() {
        //建议在application中配置
        //设置主题
        ThemeConfig themeConfig = new ThemeConfig.Builder()
                .setTitleBarBgColor(getResources().getColor(R.color.colorPrimary))
                .setTitleBarIconColor(Color.WHITE)
                .setTitleBarTextColor(Color.WHITE)
                .setFabNornalColor(getResources().getColor(R.color.colorPrimary))
                .setFabNornalColor(getResources().getColor(R.color.colorPrimaryDark))
                .setCheckNornalColor(Color.WHITE)
                .setCheckSelectedColor(getResources().getColor(R.color.colorPrimary))
                        //                .setIconBack(R.mipmap.ic_action_previous_item)
                        //                .setIconRotate(R.mipmap.ic_action_repeat)
                        //                .setIconCrop(R.mipmap.ic_action_crop)
                        //                .setIconCamera(R.mipmap.ic_action_camera)
                .build();

        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(3)
                .build();
//        cn.finalteam.galleryfinal.ImageLoader imageLoader=new cn.finalteam.galleryfinal.ImageLoader() {
//            @Override
//            public void displayImage(Activity activity, String path, GFImageView imageView, Drawable defaultDrawable, int width, int height) {
//
//            }
//
//            @Override
//            public void clearMemoryCache() {
//
//            }
//        };
        CoreConfig coreConfig = new CoreConfig.Builder(this, new UILImageLoader(), themeConfig)
                .setDebug(BuildConfig.DEBUG)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new UILPauseOnScrollListener(false, true))
                .build();
        GalleryFinal.init(coreConfig);
    }

    //-----------------Activity管理-------------------
    public void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    public List<Activity> getActivityList() {
        return mActivityList;
    }

    public void removeActivity(Activity activity) {
        for (int i = 0; i < mActivityList.size(); i++) {
            if (mActivityList.get(i) == activity) {
                mActivityList.remove(i);
            }
        }
    }

    public void removeAllActivity() {
        for (int i = 0; i < mActivityList.size(); i++) {
            mActivityList.remove(i);
        }
    }

    //-----------------Activity管理-------------------

    //-----------------图片加载框架的初始化-------------------

    public void AppExit() {
        for (Activity activity : mActivityList) {
            activity.finish();
        }
//        System.exit(0);
    }

    private void initImageLoaderConfiguration() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), "MXimageloader/Cache");

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .threadPoolSize(3)// 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()// 缓冲显示不同大小的同一张图片
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))//最少使用的会先被删除
                        // You can pass your own memory cache
                        // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(200 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                        // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 队列模式,后进先出
                        // .discCacheFileCount(100) // 缓存的文件数量
                .diskCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                        // connectTimeout (5 s),readTimeout (30s)超时时间
                .imageDownloader(
                        new BaseImageDownloader(getApplicationContext(),
                                5 * 1000, 20 * 1000))
                        //.writeDebugLogs() // Remove for release app
                .build();// 开始构建
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);// 全局初始化此配置
    }








}
