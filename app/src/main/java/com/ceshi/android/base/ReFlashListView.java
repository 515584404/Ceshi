package com.ceshi.android.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ceshi.android.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.widget.AbsListView.OnScrollListener;

/**
 * Created by ztr on 2016/05/14.
 */
public class ReFlashListView extends ListView implements OnScrollListener {
    View header;
    View footer;
    int headerHeight;
    int firstVisibleItem; // 当前第一个可见Item的位置
    boolean isRemark; // 标记第一个出现的Item
    int startY; // 按下时的Y值
    int state; // 当前的状态
    int scrollState; // 滚动状态
    final int NONE = 0; // 正常状态
    final int PULL = 1; // 下拉刷新状态
    final int RELESE = 2; // 松开释放状态
    final int REFLASHING = 3; // 正在刷新状态
    IReflashListener iReflashListener;
    int totalItemCount;
    int lastVisibleItem;
    boolean isLoading;  //正在加载
    private TextView txtViewLoading = null;
    private ProgressBar prgBarLoading = null;

    public ReFlashListView(Context context) {
        super(context);
        initView(context);
    }

    public ReFlashListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ReFlashListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    // 初始化界面，添加顶部布局至ListView
    public void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.header_layout, null);
        footer=inflater.inflate(R.layout.footer_layout, null);
        footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
        measureView(header);
        headerHeight = header.getMeasuredHeight();
        topPadding(-headerHeight);
        this.addHeaderView(header);
        this.addFooterView(footer);
        this.setOnScrollListener(this);

        txtViewLoading = (TextView)footer.findViewById(R.id.tip);
        prgBarLoading = (ProgressBar)footer.findViewById(R.id.progress);
    }

    // 设置header布局的上边距
    private void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding,
                header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }

    // 通知父布局占用的宽，高
    private void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight,
                    MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(tempHeight,
                    MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.totalItemCount=totalItemCount;
        this.lastVisibleItem=firstVisibleItem+visibleItemCount;

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
        if(totalItemCount==lastVisibleItem && scrollState == SCROLL_STATE_IDLE){
            if(!isLoading){
                isLoading=true;
                footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
                iReflashListener.onLoad();
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(getContext(),"iiii"+firstVisibleItem,Toast.LENGTH_LONG).show();
                if (firstVisibleItem == 0) {
                    isRemark = true;
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELESE) {
                    state = REFLASHING;
                    //刷新数据
                    iReflashListener.onReflash();
                    reflashViewByState();
                } else if (state == PULL) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /*
     * 判断移动过程中的操作
     */
    private void onMove(MotionEvent e) {
        int tempY = (int) e.getY();
        int space = tempY - startY;
        int topPadding = space - headerHeight;
        if (!isRemark) {
            return;
        }
        switch (state) {
            case NONE:
                if (space > 0) {
                    state = PULL;
                    reflashViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if (space > headerHeight + 30
                        && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    state = RELESE;
                    reflashViewByState();
                }
                break;
            case RELESE:
                topPadding(topPadding);
                if (space < headerHeight + 30) {
                    state = PULL;
                    reflashViewByState();
                } else if (space <= 0) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }
    }

    /*
     * 根据当前状态，改变界面显示
     */
    private void reflashViewByState() {
        TextView tip = (TextView) header.findViewById(R.id.tip);
        ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
        ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);
        RotateAnimation anim = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        anim.setFillAfter(true);
        RotateAnimation anim1 = new RotateAnimation(180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim1.setDuration(500);
        anim1.setFillAfter(true);
        switch (state) {
            case NONE:
                topPadding(-headerHeight);
                arrow.clearAnimation();
                break;
            case PULL:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("下拉可以刷新");
                arrow.clearAnimation();
                arrow.setAnimation(anim1);
                break;
            case RELESE:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("松开可以刷新");
                arrow.clearAnimation();
                arrow.setAnimation(anim);
                break;
            case REFLASHING:
                topPadding(50);
                arrow.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                arrow.clearAnimation();
                tip.setText("正在刷新...");
                break;
        }
    }

    public void reflashComplete(){
        state=NONE;
        reflashViewByState();
        isRemark=false;
        TextView lastupdatetime=(TextView)header.findViewById(R.id.latupdate_time);
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        String time=format.format(date);
        lastupdatetime.setText(time);
    }

    public void loadingComplete(){
        isLoading=false;
        footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
    }

    public void allDataLoadingComplete(){
        isLoading=false;
        txtViewLoading.setText("全部数据已经加载完成");
        prgBarLoading.setVisibility(View.GONE);
    }
    public void setInterfacs(IReflashListener iReflashListener){
        this.iReflashListener=iReflashListener;
    }

    public interface IReflashListener{
        public void onReflash();
        public void onLoad();
    }


}
