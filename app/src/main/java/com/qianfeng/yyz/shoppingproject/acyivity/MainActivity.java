package com.qianfeng.yyz.shoppingproject.acyivity;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.yyz.shoppingproject.R;
import com.qianfeng.yyz.shoppingproject.callback.InitBallTitleCallback;
import com.qianfeng.yyz.shoppingproject.callback.UpdateContentCallback;
import com.qianfeng.yyz.shoppingproject.fragment.MainFragment;
import com.qianfeng.yyz.shoppingproject.fragment.MyCarFragment;
import com.qianfeng.yyz.shoppingproject.fragment.MyCollectFragment;
import com.qianfeng.yyz.shoppingproject.fragment.NavigationFragment;


public class MainActivity extends AppCompatActivity implements InitBallTitleCallback ,UpdateContentCallback{

    private FragmentTransaction tran ;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_rl,new MainFragment());
        transaction.add(R.id.main_navigation,new NavigationFragment());
        transaction.commit();


        //查找控件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.tv_title);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.app_name,R.string.app_name
        );
       drawerLayout.addDrawerListener(actionBarDrawerToggle);
       actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("===111=","==back=");
    }

    long time = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("=11===","=down=");
        if (time==0){
            time = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            long timeNow = System.currentTimeMillis();
            if ((timeNow-time)<2000){
                return super.onKeyDown(keyCode, event);
            }else {
                time = timeNow;
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    @Override
    public void updateCallback(int pos) {
        tran = getSupportFragmentManager().beginTransaction();
        switch (pos){

            case 0:
                tran.replace(
                        R.id.main_rl,new MainFragment()
                );
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case 1:

                tran.replace(
                        R.id.main_rl,new MyCollectFragment()
                );
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case 2:
                tran.replace(
                        R.id.main_rl,new MyCarFragment()
                );
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case 3:

                break;
        }
        tran.commit();
    }

    @Override
    public void setTextListener(String str) {
        toolbar.setTitle(str);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }else {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
