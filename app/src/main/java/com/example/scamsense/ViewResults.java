package com.example.scamsense;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ViewResults extends AppCompatActivity {


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
        ScamImages scamImages = dataManager.getScamImages();
        Levels levels = dataManager.getLevels();

        ExpandableListView expandableListView = findViewById(R.id.viewResultsList);

        ArrayList<String> listGroups = new ArrayList<>();
        listGroups.add("Group 1");
        listGroups.add("Group 2");
        listGroups.add("Group 3");
        listGroups.add("Group 4");

        for (int i = 0; i < scamImages.getScamImages().size(); i++) {
            listGroups.add("Q" + (i + 1) + ": " + scamImages.getScamImages().get(i).getTitle());
        }


        HashMap<String, ArrayList<String>> listItems = new HashMap<>();
        ArrayList<String> childItems1 = new ArrayList<>();
        childItems1.add("Child Title 1");
        childItems1.add("Child Title 2");
        childItems1.add("Child Title 3");
        ArrayList<String> childItems2 = new ArrayList<>();
        childItems2.add("Child Title 1");
        childItems2.add("Child Title 2");
        childItems2.add("Child Title 3");
        ArrayList<String> childItems3 = new ArrayList<>();
        childItems3.add("Child Title 1");
        childItems3.add("Child Title 2");
        childItems3.add("Child Title 3");
        ArrayList<String> childItems4 = new ArrayList<>();
        childItems4.add("Child Title 1");
        childItems4.add("Child Title 2");
        childItems4.add("Child Title 3");

        listItems.put(listGroups.get(0), childItems1);
        listItems.put(listGroups.get(1), childItems2);
        listItems.put(listGroups.get(2), childItems3);
        listItems.put(listGroups.get(3), childItems4);

        CustomExpandableAdapter customExpandableAdapter = new CustomExpandableAdapter(this, listGroups, listItems);
        expandableListView.setAdapter(customExpandableAdapter);

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
