package com.ceshi.android.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.ceshi.android.R;
import com.ceshi.android.base.BaseActivity;
import com.ceshi.android.ui.fragment.FragmentMessage;

import java.util.List;

import butterknife.Bind;

/**
 * Created by ztr on 2016/05/10.
 */
public class MessageActivity extends BaseActivity {
    @Bind(R.id.fl)
    FrameLayout fl;
    private FragmentTransaction ft;
    private FragmentMessage fragmentMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        ft = getSupportFragmentManager().beginTransaction();
        fragmentMessage = new FragmentMessage(fl);
        ft.replace(R.id.fl, fragmentMessage);
        ft.commit();
    }


    @Override
    protected void initEvent() {

    }

    @Override
    public void onBackPressed() {
        Log.i("yan","onBackPressed执行");
        ft = getSupportFragmentManager().beginTransaction();
        FragmentMessage fragment = new FragmentMessage(fl);
        ft.replace(R.id.fl, fragment);
        ft.commit();
        //super.onBackPressed();
    }
   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("yan","onKeyDown 执行");
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            ft = getSupportFragmentManager().beginTransaction();
            FragmentMessage fragment = new FragmentMessage(fl);
            ft.replace(R.id.fl, fragment);
            ft.commit();
        }
        return true;
    }*/
   @Override
   public boolean onKeyDown(int keyCode, KeyEvent event) {
       Log.i("yan","onKeyDown 执行");
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
       for(Fragment fragment:fragments){
           if(fragment instanceof  FragmentMessage){
               finish();
               return true;
           }else {
               Log.i("yan","不是FragmentMessage");
           }
           Log.i("yan","\tfragmentMessage.isHidden()"+fragmentMessage.isHidden()
                   + "\tfragmentMessage.isVisible()"+fragmentMessage.isVisible()
                   +"\tfragmentMessage.isAdded()"+fragmentMessage.isAdded()
                   +"\tfragmentMessage.isResumed()"+fragmentMessage.isResumed()
                   +"\tfragmentMessage.isInLayout()"+fragmentMessage.isInLayout()
                   +"\tfragmentMessage.isRemoving()"+fragmentMessage.isRemoving()
                   +"\tfragmentMessage.isDetached()"+fragmentMessage.isDetached()
           );
       }
       if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN ) {
           ft = getSupportFragmentManager().beginTransaction();
           FragmentMessage fragment = new FragmentMessage(fl);
           ft.replace(R.id.fl, fragmentMessage);
           ft.commit();
       }
       return true;
   }

}
