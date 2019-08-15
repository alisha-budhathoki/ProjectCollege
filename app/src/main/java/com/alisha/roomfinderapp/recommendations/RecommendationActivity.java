package com.alisha.roomfinderapp.recommendations;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alisha.roomfinderapp.R;
import com.alisha.roomfinderapp.models.ReviewRatingMerge;
import com.alisha.roomfinderapp.models.Room;
import com.alisha.roomfinderapp.models.User;
import com.alisha.roomfinderapp.rooms.RoomRecyclerAdapter;
import com.alisha.roomfinderapp.utils.FilePaths;
import com.alisha.roomfinderapp.utils.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecommendationActivity extends AppCompatActivity {
    private static final String TAG = "RecommendationActivity";
    private FirebaseHelper firebaseHelper;
    private List<Room> items;
    private List<User> userList;
    List<ReviewRatingMerge> reviewRatingMerges;
    Map<User, HashMap<Room, Double>> data;
    Set<Room> newRecommendationSet;
    HashMap<Room, Double> newUser;


    private List<Room> mList;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private RoomRecyclerAdapter adapter;
    private FirebaseHelper mFirebaseHelper;
    private SwipeRefreshLayout refresh;

    private Map<User, HashMap<Room, Double>> predictedResultOutput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        setupAdapter();

        firebaseHelper = new FirebaseHelper(getApplicationContext());
        userList = new ArrayList<>();

        items = new ArrayList<>();
        reviewRatingMerges = new ArrayList<>();
        loadRecommendations();


    }

    private void loadRecommendations() {
        refresh.setRefreshing(true);
        userList.clear();
        firebaseHelper.getMyRef().child(FilePaths.USER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds :
                        dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    userList.add(user);
                }
                Log.d(TAG, "onDataChange: Total users:"+userList.size());


                firebaseHelper.getMyRef().child(FilePaths.ROOM).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        data = new HashMap<>();

                        items.clear();

                        for (DataSnapshot ds :
                                dataSnapshot.getChildren()) {
                            Room room = ds.getValue(Room.class);
                            items.add(room);

                        }
                        Log.d(TAG, "onDataChange: Total items:" +items.size());


                        firebaseHelper.getMyRef().child(FilePaths.USER_REVIEWS).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                reviewRatingMerges.clear();
                                for (DataSnapshot ds :
                                        dataSnapshot.getChildren()) {
                                    ReviewRatingMerge ratingMerge = ds.getValue(ReviewRatingMerge.class);
                                    reviewRatingMerges.add(ratingMerge);
                                }

                                for (final User user :
                                        userList) {
                                    newUser = new HashMap<Room, Double>();
                                    newRecommendationSet = new HashSet<>();

                                    for(int i = 0;i<items.size();i++){
                                        newRecommendationSet.add()
                                    }

                                    firebaseHelper.getMyRef().child(FilePaths.USER_REVIEWS).child(room.getId())
                                            .child(user.getUser_id()).equalTo(user.getUser_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            reviewRatingMerges.clear();
                                            for (Room room :
                                                    items) {
                                                for (DataSnapshot ds :
                                                        dataSnapshot.getChildren()) {
                                                    ReviewRatingMerge reviewRatingMerge = ds.getValue(ReviewRatingMerge.class);
                                                    reviewRatingMerges.add(reviewRatingMerge);
                                                    for (Room rooom :
                                                            items) {
                                                        if (reviewRatingMerge.getPost_id().equals(rooom.getId())) {
                                                            newRecommendationSet.add(rooom);
                                                        }
                                                    }
                                                    for (Room item : newRecommendationSet) {
                                                        newUser.put(item, Math.random());
                                                    }
                                                    data.put(user, newUser);
                                                    SlopeOne slopeOne = new SlopeOne(data, items);
                                                    predictedResultOutput = slopeOne.slopeOne();

                                                    for (User user : predictedResultOutput.keySet()) {
                                                        System.out.println(user.getUsername() + ":");
                                                        if (user.getUser_id().equals(mFirebaseHelper.getAuth().getCurrentUser().getUid())) {

                                                            print(data.get(user));
                                                        }
                                                    }
                                                    refresh.setRefreshing(false);
                                                }

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void print(HashMap<Room, Double> hashMap) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        for (Room j : hashMap.keySet()) {
            mList.add(j);
            Log.d(TAG, "print: " + hashMap.get(j).doubleValue());
        }

    }

//    public Map<User, HashMap<Room, Double>> initializeData() {
//        Map<User, HashMap<Room, Double>> data = new HashMap<>();
//        HashMap<Room, Double> newUser;
//        Set<Room> newRecommendationSet;
//        for (int i = 0; i < numberOfUsers; i++) {
//            newUser = new HashMap<Room, Double>();
//            newRecommendationSet = new HashSet<>();
//            for (int j = 0; j < 3; j++) {
//                newRecommendationSet.add(items.get((int) (Math.random() * 5)));
//            }
//            for (Room item : newRecommendationSet) {
//                newUser.put(item, Math.random());
//            }
////            data.put(new User("User " + i), newUser);
//        }
//        return data;
//    }


    private void setupAdapter() {

        mList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        refresh = findViewById(R.id.refresh);

        manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(manager);

        adapter = new RoomRecyclerAdapter(getApplicationContext(), mList);

        recyclerView.setAdapter(adapter);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRecommendations();
            }
        });
    }

}
