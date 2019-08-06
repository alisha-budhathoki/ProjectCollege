package com.alisha.roomfinderapp.rooms.normal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alisha.roomfinderapp.R;
import com.alisha.roomfinderapp.models.CommentRatingMerge;
import com.alisha.roomfinderapp.models.Notification;
import com.alisha.roomfinderapp.models.Room;
import com.alisha.roomfinderapp.rooms.RoomAddActivity;
import com.alisha.roomfinderapp.utils.CommentsRatingRecyclerAdapter;
import com.alisha.roomfinderapp.utils.ComplainActivity;
import com.alisha.roomfinderapp.utils.FilePaths;
import com.alisha.roomfinderapp.utils.FirebaseHelper;
import com.alisha.roomfinderapp.utils.SharedPreferenceHelper;
import com.alisha.roomfinderapp.utils.UniversalImageLoader;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.hsalf.smilerating.SmileRating;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class RoomDetailActivity extends AppCompatActivity {
    private static final String TAG = "RoomDetailActivity";
    private Intent in;
    private Context mContext = RoomDetailActivity.this;
    private Room post;
    private TextView roomName, location, price, contact_no, owner_name, no_of_rooms, description, bookmark;
    private ImageView house_pic;
    private Button call_now;

    private FirebaseHelper mFirebaseHelper;
    private Button lin_directions;
    private AlertDialog.Builder commentBuilder;

    private CircleImageView imageUser;
    private SmileRating smile_rating;
    private TextInputEditText inputReview;
    private Button btn_send;
    private RecyclerView recyclerView;
    private float userRated = 0;
    private List<CommentRatingMerge> mCommentList;
    private LinearLayoutManager manager;
    private CommentsRatingRecyclerAdapter adapter;
    private SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        mFirebaseHelper = new FirebaseHelper(mContext);
        sharedPreferenceHelper = new SharedPreferenceHelper(mContext);
        setupWidgets();
        getIncomingIntent();

        setupToolbar();
        setupAdapter();
        setupCommentsList();
////        tempData();
//
        initSendComment();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void setupAdapter() {
        mCommentList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, true);

        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setLayoutManager(manager);

        adapter = new CommentsRatingRecyclerAdapter(mContext, mCommentList, new CommentsRatingRecyclerAdapter.OnCommentRemoveListener() {
            @Override
            public void onCommentRemove(CommentRatingMerge commentUserMerge) {
                popupForDelete(commentUserMerge);
            }
        });

        recyclerView.setAdapter(adapter);


    }

    private void setupWidgets() {
//        title = findViewById(R.id.title);
//        title.setText(post.getDesc());

        house_pic = findViewById(R.id.house_pic);
        roomName = findViewById(R.id.roomName);
        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        no_of_rooms = findViewById(R.id.no_of_rooms);
        price = findViewById(R.id.price);
        call_now = findViewById(R.id.call_now);
        lin_directions = findViewById(R.id.directions);
        contact_no = findViewById(R.id.contact_no);
        owner_name = findViewById(R.id.owner_name);

        commentBuilder = new AlertDialog.Builder(mContext);
        commentBuilder.setTitle("Delete comment?");

        imageUser = findViewById(R.id.imageUser);
        smile_rating = findViewById(R.id.smile_rating);

        inputReview = findViewById(R.id.inputReview);
        btn_send = findViewById(R.id.btn_send);

        recyclerView = findViewById(R.id.recyclerView);


    }

    private void popupForDelete(final CommentRatingMerge commentUserMerge) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialog);
        builder.setTitle("Delete comment  " + commentUserMerge.getComment_desc() + " ?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                mFirebaseHelper.getMyRef().child(FilePaths.USER_COMMENTS)
                        .child(post.getId())
                        .child(commentUserMerge.getComment_id())
                        .removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                mCommentList.remove(commentUserMerge);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(mContext, "Deleted artSales: " + post.getName(), Toast.LENGTH_SHORT).show();
                            }
                        });

                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void popupForDeleteItem() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialog);
        builder.setTitle("Delete art item: " + post.getName() + " ?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                mFirebaseHelper.getmStorageReference().child(FilePaths.ROOM + "/room" + post.getId())
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("l", "onSuccess: File deleted succesfully.");
                        mFirebaseHelper.getMyRef().child(FilePaths.ROOM)
                                .child(post.getId())
                                .removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                        Toast.makeText(mContext, "Success!", Toast.LENGTH_SHORT).show();

                                        mFirebaseHelper.getMyRef().child(FilePaths.USER_COMMENTS)
                                                .child(post.getId())
                                                .removeValue();
                                        mFirebaseHelper.getMyRef().child(FilePaths.USER_COMPLAINTS)
                                                .child(post.getId())
                                                .removeValue();
                                        finish();
                                    }
                                });

                        dialog.cancel();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(mContext, "Error occured", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post_details_menu, menu);
//        menu.findItem(R.id.edit_post_action).setVisible(false);
//        menu.findItem(R.id.delete_post_action).setVisible(false);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!post.getOwner_id().equals(mFirebaseHelper.getAuth().getCurrentUser().getUid())) {
            menu.findItem(R.id.edit_post_action).setVisible(false);
            menu.findItem(R.id.delete_post_action).setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.complain_action:
                Intent intent = new Intent(this, ComplainActivity.class);
                intent.putExtra(mContext.getString(R.string.calling_room_complaint), post);
                startActivity(intent);

                return true;
            case R.id.edit_post_action:
                Intent editIntent = new Intent(this, RoomAddActivity.class);
                editIntent.putExtra(mContext.getString(R.string.calling_room_edit), post);
                startActivity(editIntent);

                return true;
            case R.id.delete_post_action:
                popupForDeleteItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void getIncomingIntent() {
        in = getIntent();

        //for edit calls from another activity
        post = in.getParcelableExtra(mContext.getString(R.string.calling_room_detail));
        UniversalImageLoader.setImage(post.getImage(), house_pic, null, "");
        roomName.setText(post.getName());
        location.setText(post.getLocation());
        description.setText(post.getDesc());
        no_of_rooms.setText(post.getNo_of_rooms() + "");
        location.setText(post.getLocation());
        price.setText(post.getPrice() + "");
        contact_no.setText(post.getContact_no() + "");
        owner_name.setText(post.getOwner_name());

        call_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + post.getContact_no()));
                startActivity(intent);
            }
        });

        lin_directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "google.navigation:q=%.8f,%.8f",
                        Double.parseDouble(post.getLattitude()), Double.parseDouble(post.getLongitutde()));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mContext.startActivity(intent);
            }
        });

        //increase view count by 1
        mFirebaseHelper.getMyRef().child(FilePaths.ROOM).child(post.getId()).child("viewCount").setValue(post.getViewCount()+1);



    }

    private void initSendComment() {

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String textComment = inputReview.getText().toString();


                final String keyId;


                keyId = mFirebaseHelper.getMyRef()
                        .child(FilePaths.USER_COMMENTS)
                        .child(post.getId()).push().getKey();
                final CommentRatingMerge comment = new CommentRatingMerge(
                        keyId,
                        post.getId(),
                        textComment,
                        mFirebaseHelper.getAuth().getCurrentUser().getUid(),
                        sharedPreferenceHelper.getUserInfo().getUsername(),
                        sharedPreferenceHelper.getUserInfo().getAvatar_img_link(),
                        userRated
                );
                Toast.makeText(mContext, "sending your comment..", Toast.LENGTH_SHORT).show();
                mFirebaseHelper.getMyRef().child(FilePaths.USER_COMMENTS)
                        .child(post.getId())
                        .child(keyId)
                        .setValue(comment, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                Toast.makeText(mContext, "Comment added", Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();

                                sendNotificationToOwner(textComment);
                            }
                        });


                inputReview.setText("");


            }
        });
    }

    private void sendNotificationToOwner(String textComment) {
        Notification notification = new Notification();

        notification.setKeyId(post.getId());
        notification.setMessage(String.format("%s commented: %s", sharedPreferenceHelper.getUserInfo().getUsername(),
                textComment));
        notification.setUserId(post.getOwner_id());
        notification.setSenderId(mFirebaseHelper.getAuth().getCurrentUser().getUid());
        notification.setDate_added(DateTimeUtils.formatDate(new Date()));
        //insert to notification table only if other users send comment
        if (!mFirebaseHelper.getAuth().getCurrentUser().getUid().equals(post.getOwner_id())){
            String keyId = mFirebaseHelper.getMyRef().child(FilePaths.NOTIFICATIONS)
                    .child(post.getOwner_id()).push().getKey();
            mFirebaseHelper.getMyRef().child(FilePaths.NOTIFICATIONS)
                    .child(post.getOwner_id())
                    .child(post.getId())
                    .child(keyId)
                    .setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(mContext, "Message sent to owner", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void setupCommentsList() {

        mFirebaseHelper.getCommentRatingList(post.getId(), new CommentRatingListener() {
            @Override
            public void onLoaded(List<CommentRatingMerge> mComments) {
                mCommentList.clear();
                mCommentList.addAll(mComments);
                adapter.notifyDataSetChanged();
            }
        });


    }


    public interface CommentRatingListener {
        void onLoaded(List<CommentRatingMerge> mCommentList);
    }

    public interface CheckRatingExistListener {
        void onResult(Boolean ratingForUserExist, String postId);
    }

    public interface GetUserRatingListener {
        void onResult(int userRating);
    }
}
