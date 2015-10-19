package com.isee.goose;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.isee.goose.fragment.AndroidFragment;
import com.isee.goose.fragment.LeftFragment;
import com.isee.goose.vo.ItemOne;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.id_content_container)
    FrameLayout contentFrameLayout;

    @ViewById(R.id.id_toolbar)
    Toolbar toolbar;

    @ViewById(R.id.id_drawerlayout)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private String itemValue;


    private FragmentManager fragmentManager;
    private LeftFragment leftFragment;
    @AfterViews
    public void initView(){
        initToolBar();
        initTogle();
        fragmentManager = getSupportFragmentManager();
        leftFragment = new LeftFragment();
        fragmentManager.beginTransaction().add(R.id.id_left_menu_container,leftFragment).commit();
        EventBus.getDefault().register(this);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(ItemOne event) {
        if(!event.getValue().equals(itemValue))
        replaceFragment(R.id.id_content_container, new AndroidFragment(),event.getValue());
    }

    public void replaceFragment(int id_content, Fragment fragment,String value) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        itemValue = value;
        transaction.replace(id_content, fragment);
        transaction.commit();
    }
    public void closeDrawer(){
        mDrawerLayout.closeDrawers();
    }

}
