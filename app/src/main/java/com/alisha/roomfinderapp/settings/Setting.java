package com.alisha.roomfinderapp.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alisha.roomfinderapp.R;
import com.alisha.roomfinderapp.utils.FirebaseHelper;

//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.SwitchCompat;

//import com.example.roomrental.R;
//import com.example.roomrental.room.FirebaseHelper;
//import com.example.roomrental.startup.LoginActivity;

public class Setting extends AppCompatActivity {

    private Context mContext = Setting.this;

    boolean stateSwitch;
    SharedPreferences preferences;
    private FirebaseHelper mFirebaseHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        preferences = getSharedPreferences("PREFS",0);
        ImageButton imageButton = findViewById(R.id.about);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), aboutActivity.class));
            }
        });

        // logout();
    }
//    private void logout() {
//        findViewById(R.id.log_out).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                confirmLogOut();
//            }
//        });
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.log_out_menu, menu);
////        menu.findItem(R.id.edit_post_action).setVisible(false);
////        menu.findItem(R.id.delete_post_action).setVisible(false);
//        return true;
//    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.log_out:
//
//                confirmLogOut();
//
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
////    private void confirmLogOut() {
////        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialog);
////        builder.setTitle("Confirm  Log out?");
////        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////
////                mFirebaseHelper.signOut();
////                Intent intent = new Intent(mContext, LoginActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
////                startActivity(intent);
////                dialog.cancel();
////
////
////            }
////        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.show();
//    }
}
////,kjjjjhdsdddsu
//
//
