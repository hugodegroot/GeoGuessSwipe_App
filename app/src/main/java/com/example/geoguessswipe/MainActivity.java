package com.example.geoguessswipe;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    List<GeoObject> mGeoObjects;

    RecyclerView mGeoRecyclerView;

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGeoObjects = new ArrayList<>();

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override

            public boolean onSingleTapUp(MotionEvent e) {

                return true;

            }

        });

        for (int i = 0; i < GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES.length; i++) {


            mGeoObjects.add(new GeoObject(GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES[i],

                    GeoObject.PRE_DEFINED_GEO_OBJECT_IMAGE_IDS[i]));

        }

        mGeoRecyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);


        mGeoRecyclerView.setLayoutManager(mLayoutManager);

        mGeoRecyclerView.setHasFixedSize(true);

        final GeoObjectAdapter mAdapter = new GeoObjectAdapter(this, mGeoObjects);

        mGeoRecyclerView.setAdapter(mAdapter);

        mGeoRecyclerView.addOnItemTouchListener(this);

        /*

Add a touch helper to the RecyclerView to recognize when a user swipes to delete a list entry.

An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,

and uses callbacks to signal when a user is performing these actions.

*/

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =

                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    // The first integer parameter refers to the dragging directions. We ignore these here.

                    // The second integer parameter refers to the swiping directions.


                    @Override

                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                        return false;

                    }


                    //Called when a user swipes left or right on a ViewHolder

                    @Override

                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {




                        //Get the index corresponding to the selected position

                        int position = (viewHolder.getAdapterPosition());

                        if (swipeDir == ItemTouchHelper.LEFT) { //swipe left

                            if(inEurope(mGeoObjects.get(position))) {
                                Toast.makeText(getApplicationContext(),"Not!",Toast.LENGTH_SHORT).show();
                            } else Toast.makeText(getApplicationContext(),"Correct!",Toast.LENGTH_SHORT).show();


                            mGeoObjects.remove(position);
                            mAdapter.notifyItemRemoved(position);





                        }else {//swipe right

                            if(inEurope(mGeoObjects.get(position))) {
                                Toast.makeText(getApplicationContext(),"Correct!",Toast.LENGTH_SHORT).show();
                            } else Toast.makeText(getApplicationContext(),"Not!",Toast.LENGTH_SHORT).show();

                            mGeoObjects.remove(position);
                            mAdapter.notifyItemRemoved(position);


                        }



                    }

                };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(mGeoRecyclerView);
    }

    @Override

    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());

        int mAdapterPosition = rv.getChildAdapterPosition(child);

        if (child != null && mGestureDetector.onTouchEvent(e)) {

            Toast.makeText(this, mGeoObjects.get(mAdapterPosition).getmGeoName(), Toast.LENGTH_SHORT).show();

        }

        return false;

    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    public boolean inEurope(GeoObject obj){
        if(obj.getmGeoName() == "Denmark" || obj.getmGeoName() == "Kazachstan" || obj.getmGeoName() == "Poland"){
            return true;
        } else return false;
    }


}
