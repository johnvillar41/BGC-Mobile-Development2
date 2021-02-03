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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.mysql.jdbc.Blob;

import java.sql.SQLException;

import de.hdodenhof.circleimageview.CircleImageView;
import emp.project.softwareengineeringprojectcustomer.Interface.IMain;
import emp.project.softwareengineeringprojectcustomer.IntroActivityView;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CartModel;
import emp.project.softwareengineeringprojectcustomer.Models.Bean.CustomerModel;
import emp.project.softwareengineeringprojectcustomer.Models.Database.Service.MainService;
import emp.project.softwareengineeringprojectcustomer.Presenter.MainPresenter;
import emp.project.softwareengineeringprojectcustomer.R;
import emp.project.softwareengineeringprojectcustomer.UserCredentials;
import emp.project.softwareengineeringprojectcustomer.Views.Fragments.HomeFragment;
import emp.project.softwareengineeringprojectcustomer.Views.Fragments.InformationFragment;
import emp.project.softwareengineeringprojectcustomer.Views.Fragments.TrackOrderFragment;
import emp.project.softwareengineeringprojectcustomer.Views.Fragments.UserProfileFragment;

public class MainActivityView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IMain.IMainView {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private TextView navFullname, navEmail, navUsername;
    private CircleImageView profile_picture;
    private IMain.IMainPresenter presenter;

    @SuppressLint("StaticFieldLeak")
    public static TextView txt_total_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt_total_cart = findViewById(R.id.txt_number_notification);

        if (!UserCredentials.getInstance().checkLoginStatus()) {
            this.finish();
        }

        //Side Header
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        navFullname = headerView.findViewById(R.id.txt_full_name);
        navEmail = headerView.findViewById(R.id.txt_user_email);
        navUsername = headerView.findViewById(R.id.txt_user_username);
        profile_picture = headerView.findViewById(R.id.profile_picture);

        drawer = findViewById(R.id.drawer_layout);
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

        presenter = new MainPresenter(this, MainService.getInstance());
        presenter.loadUserDetails();
    }

    @Override
    protected void onResume() {
        if (!UserCredentials.getInstance().checkLoginStatus()) {
            this.finish();
        }
        presenter.loadCartNumber();
        super.onResume();
    }

    private static final String LOGOUT = "Logout";
    private static final String LOGOUT_MESSAGE = "Are you sure you want to logout?";
    private static final String CONFIRM = "Confirm";
    private static final String CANCEL = "Cancel";

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(LOGOUT)
                    .setMessage(LOGOUT_MESSAGE)
                    .setIcon(R.drawable.ic_baseline_exit_to_app_24)
                    .setPositiveButton(CONFIRM, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UserCredentials.getInstance().logoutAccount();
                            moveTaskToBack(true);
                        }
                    }).setNegativeButton(CANCEL, null);
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_view, menu);
        return true;
    }

    private static final String HOME = "Home";
    private static final String TRACK_ORDER = "Track Order";
    private static final String INFORMATION = "Information";
    private static final String USER_PROFILE = "User Profile";


    @SuppressLint({"NonConstantResourceId"})
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        new HomeFragment()).commit();
                toolbar.setTitle(HOME);
                break;
            case R.id.nav_track_order:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        new TrackOrderFragment()).commit();
                toolbar.setTitle(TRACK_ORDER);
                break;
            case R.id.nav_slideshow:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        new InformationFragment()).commit();
                toolbar.setTitle(INFORMATION);
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        new UserProfileFragment()).commit();
                toolbar.setTitle(USER_PROFILE);
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(this, IntroActivityView.class);
                startActivity(intent);
                MainActivityView.this.finish();
                UserCredentials.getInstance().logoutAccount();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cart) {
            Intent intent = new Intent(MainActivityView.this, CheckoutActivityView.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayUserDetails(CustomerModel userModel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                navFullname.setText(userModel.getUser_username());
                navEmail.setText(userModel.getUser_email());
                navUsername.setText(userModel.getUser_username());
                Blob b = (Blob) userModel.getPicture();
                int[] blobLength = new int[1];
                try {
                    blobLength[0] = (int) b.length();
                    byte[] blobAsBytes = b.getBytes(1, blobLength[0]);
                    Glide.with(MainActivityView.this)
                            .load(blobAsBytes)
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                            .skipMemoryCache(true)
                            .into(profile_picture);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void displayTotalNumberOfCartNumbers() {
        if (Integer.parseInt(CartModel.getInstance().getTotalNumberOfOrders()) > 0) {
            txt_total_cart.setText(CartModel.getInstance().getTotalNumberOfOrders());
        } else {
            txt_total_cart.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResumeFragments() {
        presenter.loadCartNumber();
        super.onResumeFragments();
    }
}