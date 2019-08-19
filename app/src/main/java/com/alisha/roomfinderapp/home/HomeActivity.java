package com.alisha.roomfinderapp.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.alisha.roomfinderapp.R;
import com.alisha.roomfinderapp.admin.ComplaintsActivity;
import com.alisha.roomfinderapp.models.UserFCM;
import com.alisha.roomfinderapp.notifications.NotificationFragment;
import com.alisha.roomfinderapp.profile.ProfileFragment;
import com.alisha.roomfinderapp.rooms.RoomAddActivity;
import com.alisha.roomfinderapp.rooms.normal.RoomFragment;
import com.alisha.roomfinderapp.search.SearchActivity;
import com.alisha.roomfinderapp.settings.Setting;
import com.alisha.roomfinderapp.utils.FilePaths;
import com.alisha.roomfinderapp.utils.FirebaseHelper;
import com.alisha.roomfinderapp.utils.SharedPreferenceHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomeActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener {
    private static final String TAG = "HomeActivity";
    private ImageButton mimageButton;
    //ImageButton imageButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        bottomNav = findViewById(R.id.nav_view);
        ImageButton imageButton = findViewById(R.id.settings);
        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.roomNotifications));
        final FirebaseHelper firebaseHelper = new FirebaseHelper(getApplicationContext());
        SharedPreferenceHelper sharedPreferenceHelper= new SharedPreferenceHelper(getApplicationContext());


        ImageButton admin_reports = findViewById(R.id.admin_reports);


        if (sharedPreferenceHelper.getUserInfo().getUser_type() == 0){
            admin_reports.setVisibility(View.VISIBLE);
            admin_reports.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), ComplaintsActivity.class));
                }
            });
        }

        bottomNav.setOnNavigationItemSelectedListener(navListner);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Setting.class));
            }
        });


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoomFragment()).commit();


        setupFirebaseFCM();
    }

    private void setupFirebaseFCM() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            reference.child(FilePaths.USERS_FCM)
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(new UserFCM(FirebaseAuth.getInstance().getCurrentUser().getUid(), token,
                                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
                        }

                        Toast.makeText(getApplicationContext(), token.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new RoomFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                            break;
                        case R.id.nav_search_button:
//                            bottomNav.setSelectedItemId(R.id.nav_home);
//                            selectedFragment = new PopularRoomFragment();
                            Intent searchIn = new Intent(getApplicationContext(), SearchActivity.class);
                            startActivity(searchIn);
                            break;
                        case R.id.nav_add:
                            bottomNav.setSelectedItemId(R.id.nav_home);
                            Intent in = new Intent(getApplicationContext(), RoomAddActivity.class);
                            startActivity(in);


                            break;
                        case R.id.nav_notification_background:
                            selectedFragment = new NotificationFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                            break;
                        case R.id.nav_setting:
                            selectedFragment = new ProfileFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                            break;
                    }

                    return true;
                }
            };

    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
