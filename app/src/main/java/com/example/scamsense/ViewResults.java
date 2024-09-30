package com.example.scamsense;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ViewResults extends AppCompatActivity {
    private ImageButton menuButton;

    @SuppressLint({"SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // init all activiy and viewer attributes
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.view_results);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        ((View) decorView).setSystemUiVisibility(uiOptions);

        // load datamanger and get objects we will need in this activity
        dataManager dataManager = com.example.scamsense.dataManager.getInstance();
        QuestionImages questionImages = dataManager.getQuestionImages();
        Levels levels = dataManager.getLevels();

        // init all views from xml activity
        ExpandableListView expandableListView = findViewById(R.id.viewResultsList);
        menuButton = findViewById(R.id.menuButton);

        // init listGroups and listItems lists for the expandableListView
        ArrayList<String> listGroups = new ArrayList<>();
        HashMap<String, ArrayList<String>> listItems = new HashMap<>(); // Ensure this is declared before the loop

        // loop through every question that was just answered in the level
        for (int i = 0; i < questionImages.getQuestionImages().size(); i++) {
            // add parentnode as the string of it's index value, this is used in the ExpandableListView.java to get the values we need
            String groupPosition = String.valueOf(i);
            listGroups.add(groupPosition);

            // Create a new ArrayList for child items related to the current group
            ArrayList<String> childItems = new ArrayList<>();
            // Add a child node with default value.
            childItems.add(String.valueOf(i));

            // Add the group and its child items to the HashMap
            listItems.put(groupPosition, childItems);
        }

        CustomExpandableAdapter customExpandableAdapter = new CustomExpandableAdapter(this, listGroups, listItems);
        expandableListView.setAdapter(customExpandableAdapter);

        // if the menubutton is clicked
        menuButton.setOnClickListener(v-> {
            // start the LevelCompleteActivity
            Intent intent=new Intent(ViewResults.this, LevelCompleteActivity.class);
            startActivity(intent);
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                Toast.makeText(ViewResults.this, listGroups.get(groupPosition) + " Clicked", Toast.LENGTH_SHORT);

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Toast.makeText(ViewResults.this, listItems.get(listGroups.get(groupPosition)).get(childPosition) + " Clicked", Toast.LENGTH_SHORT);

                return false;
            }
        });
    }




}
