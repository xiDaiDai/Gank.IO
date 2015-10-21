package com.isee.goose;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.isee.goose.app.Config;
import com.isee.goose.app.Global;
import com.isee.goose.fragment.AndroidFragment;
import com.isee.goose.fragment.GirlsFragment;
import com.isee.goose.fragment.MeizhiFragment;
import com.isee.goose.vo.MenuItem;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.srain.cube.util.CLog;

public class MainActivity extends AppCompatActivity {
    FrameLayout contentFrameLayout;
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private FragmentManager fragmentManager;
    private  MenuItem.FragmentType currentFragment = MenuItem.FragmentType.ANDROID;
    private List<Fragment> fragmentList;
    private String[] drawerTitles = {"Android", "MEiZHI", "Setting"};
    private Class[] classes = {AndroidFragment.class, MeizhiFragment.class, GirlsFragment.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
        initToolBar();
        initTogle();
        setDrawerContent(navigationView);
        selectItem(0);
        EventBus.getDefault().register(this);
    }

    public void initView(){
        contentFrameLayout = (FrameLayout) findViewById(R.id.id_content_container);
        toolbar= (Toolbar) findViewById(R.id.id_toolbar);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.id_drawerlayout);
        navigationView= (NavigationView) findViewById(R.id._main_navigation_view);
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            fragmentList.add(null);
        }
    }

    private void  initToolBar(){
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(0xffeeeeee);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_popup_sync);

    }

    private void initTogle() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar,R.string.abc_action_bar_home_description_format, R.string.abc_action_bar_home_description);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    private void setDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(android.view.MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_android:
                       selectItem(0);
                        break;
                    case R.id.nav_meizhi:
                        selectItem(1);
                        break;
                    case R.id.nav_settings:
                        selectItem(2);
                        break;
                    case R.id.nav_favorite:
                        break;
                }
                menuItem.setChecked(true);
                closeDrawer();
                return false;
            }
        });
    }

    private void selectItem(int position) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //先隐藏所有fragment
        for (Fragment fragment : fragmentList) {
            if (null != fragment) fragmentTransaction.hide(fragment);
        }

        Fragment fragment;
        if (null == fragmentList.get(position)) {
            Bundle bundle = new Bundle();
            bundle.putString(Config.TITLE, drawerTitles[position]);
            fragment = Fragment.instantiate(this, classes[position].getName(), bundle);
            fragmentList.set(position, fragment);
            fragmentTransaction.add(R.id.id_content_container, fragment);
        } else {
            fragment = fragmentList.get(position);
            fragmentTransaction.show(fragment);
            CLog.e("fragment","------------------"+position);
        }
        fragmentTransaction.commit();

        getSupportActionBar().setTitle(drawerTitles[position]);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(MenuItem event){

        try {
            if (currentFragment != event.getType()) {
                Fragment fragment = (Fragment) Class.forName(event.getFragment()
                        .getName()).newInstance();
                replaceFragment(R.id.id_content_container, fragment, event.getTitle());
                currentFragment = event.getType();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

            closeDrawer();

    }

    public void replaceFragment(int id_content, Fragment fragment,String value) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id_content, fragment);
        transaction.commit();
    }
    public void closeDrawer(){
        mDrawerLayout.closeDrawers();
    }

    private long lastMillis;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - lastMillis) > 2000) {
            Global.showToast("再按退出应用");
            lastMillis = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
