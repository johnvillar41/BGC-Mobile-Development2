package emp.project.softwareengineeringprojectcustomer.Views.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import emp.project.softwareengineeringprojectcustomer.IntroActivityView;
import emp.project.softwareengineeringprojectcustomer.R;
import emp.project.softwareengineeringprojectcustomer.UserCredentials;
import emp.project.softwareengineeringprojectcustomer.Views.Fragments.ProfileFragment;
import emp.project.softwareengineeringprojectcustomer.Views.Fragments.HomeFragment;
import emp.project.softwareengineeringprojectcustomer.Views.Fragments.NewsFragment;

public class MainActivityView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(!UserCredentials.isLoggedIn){
            this.finish();
        }

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        toolbar.setTitle(HOME);
    }

    @Override
    protected void onResume() {
        if(!UserCredentials.isLoggedIn){
            this.finish();
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setIcon(R.drawable.ic_baseline_exit_to_app_24)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UserCredentials.isLoggedIn = false;
                            moveTaskToBack(true);
                        }
                    }).setNegativeButton("Cancel",null);
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_view, menu);
        return true;
    }

    private static final String HOME = "Home";
    private static final String GALLERY = "Gallery";
    private static final String SLIDE_SHOW = "Slide Show";
    private static final String CHECK_OUT = "Checkout";


    @SuppressLint({"NonConstantResourceId"})
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        new HomeFragment()).commit();
                toolbar.setTitle(HOME);
                break;
            case R.id.nav_gallery:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        new ProfileFragment()).commit();
                toolbar.setTitle(GALLERY);
                break;
            case R.id.nav_slideshow:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        new NewsFragment()).commit();
                toolbar.setTitle(SLIDE_SHOW);
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(this, IntroActivityView.class);
                startActivity(intent);
                MainActivityView.this.finish();
                UserCredentials.isLoggedIn = false;
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cart){
            Intent intent = new Intent(MainActivityView.this, CheckoutActivityView.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}