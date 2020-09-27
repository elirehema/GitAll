package azaa.android.com.azaa.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import azaa.android.com.azaa.R;
import azaa.android.com.azaa.UpdateClasses.GooglePlayStoreAppVersionNameLoader;
import azaa.android.com.azaa.UpdateClasses.WSCallerVersionListener;
import azaa.android.com.azaa.activities.transformer.BackgroundToForegroundTransformer;
import azaa.android.com.azaa.fragments.fragmentFour;
import azaa.android.com.azaa.fragments.fragmentOne;
import azaa.android.com.azaa.fragments.fragmentThree;
import azaa.android.com.azaa.fragments.fragmentTwo;
import azaa.android.com.azaa.network.Config;
import azaa.android.com.azaa.user.editProfile;
import azaa.android.com.azaa.user.itemStores;
import azaa.android.com.azaa.user.viewProfile;
import butterknife.BindView;
import butterknife.ButterKnife;

import static azaa.android.com.azaa.network.Config.HELP_URL;
import static azaa.android.com.azaa.network.Config.MY_PREFS_NAME;
import static azaa.android.com.azaa.network.Config.TERMS_URL;

public class MainActivity extends AppCompatActivity implements WSCallerVersionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    private int isOpen = 0;
    boolean isForceUpdate = true;
    SharedPreferences prefs;
    String phone, name, location, email;


    private int[] tabIcons = {
            R.drawable.tshirt_crew,
            R.drawable.shoe_formal,
            R.drawable.diamond,
            R.drawable.cellphone

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        new GooglePlayStoreAppVersionNameLoader(getApplicationContext(), this).execute();
        configureNavigationDrawer();
        configureToolBar();

        //MobileAds.initialize(this, AD_MOBI_APP_ID);


        if (savedInstanceState != null) {
            //Restore the fragment's instance

            //mContent = getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");
        }

        toolbar.setNavigationIcon(R.drawable.dots_vertical);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter((getResources().getColor(R.color.tabselected)), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter((getResources().getColor(R.color.tabunselected)), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter((getResources().getColor(R.color.tabreselected)), PorterDuff.Mode.SRC_IN);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(getBaseContext(), "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });


    }


    private void configureToolBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.inflateMenu(R.menu.drawer_view_2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        MainActivity.ViewPagerAdapter adapter = new MainActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new fragmentOne(), null);
        adapter.addFragment(new fragmentTwo(), null);
        adapter.addFragment(new fragmentThree(), null);
        adapter.addFragment(new fragmentFour(), null);
        viewPager.setPageTransformer(true, new BackgroundToForegroundTransformer());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
    }


    @Override
    public void onGetResponse(boolean isUpdateAvailable) {
        Log.e("ResultAPPMAIN", String.valueOf(isUpdateAvailable));
        if (isUpdateAvailable) {
            showUpdateDialog();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void configureNavigationDrawer() {
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment f = null;
                Intent intent = new Intent(getBaseContext(), website.class);
                int itemId = menuItem.getItemId();


                switch (itemId) {
                    case R.id.nav_home:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_help:
                        intent.putExtra("link", HELP_URL);
                        startActivity(intent);
                        break;
                    case R.id.nav_terms:
                        intent.putExtra("link", TERMS_URL);
                        startActivity(intent);
                        break;
                    case R.id.nav_editprofile:
                        startActivity(new Intent(getApplicationContext(), editProfile.class));
                        break;
                    case R.id.nav_viewprofile:
                        startActivity(new Intent(getApplicationContext(), viewProfile.class));
                        break;
                    case R.id.nav_viewitems:
                        Intent intent1 = new Intent(getApplicationContext(), itemStores.class);
                        intent1.putExtra("id", "3");
                        intent1.putExtra("title", "My Store");
                        startActivity(intent1);
                        break;
                    case R.id.nav_wish:
                        Intent inten_t = new Intent(getApplicationContext(), itemStores.class);
                        inten_t.putExtra("id", "1");
                        inten_t.putExtra("title", "MY WISH LIST");
                        startActivity(inten_t);
                        break;
                    case R.id.nav_upload:
                        startActivity(new Intent(getApplicationContext(), ProductUpload.class));
                        break;

                }
                toolbar.setNavigationIcon(R.drawable.dots_vertical);
                isOpen = 0;
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                switch (isOpen) {
                    case 0:
                        drawerLayout.openDrawer(Gravity.START);
                        toolbar.setNavigationIcon(R.drawable.arrow_left);
                        isOpen = 1;
                        break;
                    case 1:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        toolbar.setNavigationIcon(R.drawable.dots_vertical);
                        isOpen = 0;
                        break;
                }

                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    private void logUser() {

    }

    /**
     * Method to show update dialog
     */
    public void showUpdateDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        alertDialogBuilder.setTitle(MainActivity.this.getString(R.string.app_name));
        alertDialogBuilder.setMessage(MainActivity.this.getString(R.string.update_message));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isForceUpdate) {
                    finish();
                }
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(Config.TAG, "landscape");

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(Config.TAG, "portrait");

        }
    }


}

