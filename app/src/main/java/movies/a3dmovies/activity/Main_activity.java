package movies.a3dmovies.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;


import movies.a3dmovies.database.DbHelper;
import movies.a3dmovies.fragment.AdvanceSearch;
import movies.a3dmovies.R;
import movies.a3dmovies.fragment.FavouritesFragment;
import movies.a3dmovies.fragment.Searchresult;
import movies.a3dmovies.adapter.MaincardsGridAdapter;
import movies.a3dmovies.utils.Utils;

public class Main_activity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup radioGroupgeners;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 0;
    MaincardsGridAdapter mvo;
    Dialog dialogshow;
    NavigationView navmainview;
    DrawerLayout drawerLayoutmain;
    ImageView toolopenmenu;
    TextView toolbartitle;
    String titlename="Movie Land";
    TextView menubuttonadvancesearch,menubuttonshare,menubuttonguide,menubuttonfav,menubuttonsupportapps;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionAccess();
        initComponents();
        clickMethods();
        Searchresult searchresult=new Searchresult();
        android.support.v4.app.FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_container,searchresult);
        ft.commit();
    }

    private void clickMethods() {
        menubuttonshare.setOnClickListener(this);
        menubuttonguide.setOnClickListener(this);
        menubuttonfav.setOnClickListener(this);
        menubuttonadvancesearch.setOnClickListener(this);
        menubuttonsupportapps.setOnClickListener(this);
        toolopenmenu.setOnClickListener(this);
    }



    private void initComponents() {
        menubuttonadvancesearch= (TextView) findViewById(R.id.sidemenu_tvmenuadvsearch);
        menubuttonfav= (TextView) findViewById(R.id.sidemenu_tvmenufavourite);
        menubuttonguide= (TextView) findViewById(R.id.sidemenu_tvmenuguide);
        menubuttonshare= (TextView) findViewById(R.id.sidemenu_tvmenushare);
        menubuttonsupportapps= (TextView) findViewById(R.id.sidemenu_tvmenusupportapps);
        navmainview= (NavigationView) findViewById(R.id.nav_menu_main);
        drawerLayoutmain= (DrawerLayout) findViewById(R.id.activiy_main);
        toolopenmenu= (ImageView) findViewById(R.id.iv_navmenu);
        toolbartitle= (TextView) findViewById(R.id.iv_navmenutitle);
        toolbartitle.setText(titlename);
        Utils utils=new Utils();
        utils.createDestination();
    }


    @Override
    public void onClick(View v) {
        if (v==toolopenmenu){
            drawerLayoutmain.openDrawer(GravityCompat.START);
        }
        if(v==menubuttonadvancesearch){
            drawerLayoutmain.closeDrawer(GravityCompat.START);
            AdvanceSearch advanceSearch=new AdvanceSearch();
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_container,advanceSearch);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(v==menubuttonfav){
            FavouritesFragment favouritesFragment=new FavouritesFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_container,favouritesFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            drawerLayoutmain.closeDrawer(GravityCompat.START);

        }
        if(v==menubuttonshare){
            drawerLayoutmain.closeDrawer(GravityCompat.START);
            shareAppMethod();
        }
        if(v==menubuttonsupportapps){
            drawerLayoutmain.closeDrawer(GravityCompat.START);

        }
        if(v==menubuttonguide){
            drawerLayoutmain.closeDrawer(GravityCompat.START);

        }
    }





    @RequiresApi(api = Build.VERSION_CODES.M)
    public void permissionAccess(){
        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_STORAGE);

            // MY_PERMISSIONS_REQUEST_WRITE_STORAGE is an
            // app-defined int constant

            return;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayoutmain.isDrawerOpen(GravityCompat.START)) {
            drawerLayoutmain.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void shareAppMethod(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
