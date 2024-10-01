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
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.view_results);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        ((View) decorView).setSystemUiVisibility(uiOptions);

        dataManager dataManager = com.example.scamsense.dataManager.getInstance();
        QuestionImages questionImages = dataManager.getQuestionImages();
        Levels levels = dataManager.getLevels();

        ExpandableListView expandableListView = findViewById(R.id.viewResultsList);
        menuButton = findViewById(R.id.menuButton);

        ArrayList<String> listGroups = new ArrayList<>();

        HashMap<String, ArrayList<String>> listItems = new HashMap<>(); // Ensure this is declared before the loop

        for (int i = 0; i < questionImages.getQuestionImages().size(); i++) {
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

        //listener for back button to close ViewResults
        menuButton.setOnClickListener(v-> {
            //goes back to the level complete activity
            Intent intent=new Intent(ViewResults.this, LevelCompleteActivity.class);
            startActivity(intent);
        });

        //indicator to show when a group is clicked/touched
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                Toast.makeText(ViewResults.this, listGroups.get(groupPosition) + " Clicked", Toast.LENGTH_SHORT);

                return false;
            }
        });

        //indicator to show when a child is clicked/touched
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Toast.makeText(ViewResults.this, listItems.get(listGroups.get(groupPosition)).get(childPosition) + " Clicked", Toast.LENGTH_SHORT);

                return false;
            }
        });
    }
}
