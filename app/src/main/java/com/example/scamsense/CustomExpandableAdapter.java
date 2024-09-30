package com.example.scamsense;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.ImageView;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class CustomExpandableAdapter extends BaseExpandableListAdapter {
    dataManager dataManager = com.example.scamsense.dataManager.getInstance();
    QuestionImages questionImages = dataManager.getQuestionImages();

    private Context context;
    private ArrayList<String> listGroups;
    private HashMap<String, ArrayList<String>> listItems;

    public CustomExpandableAdapter(Context context, ArrayList<String> listGroups, HashMap<String, ArrayList<String>> listItems) {
        this.context = context;
        this.listGroups = listGroups;
        this.listItems = listItems;
    }

    @Override
    public int getGroupCount() {
        return listGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listItems.get(listGroups.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listItems.get(listGroups.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ScamImage currentImage = questionImages.getQuestionImages().get(groupPosition);

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_parent, null);
        }

        if(currentImage.getCorrect()){
            convertView.setBackgroundColor(Color.parseColor("#68dd65"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#dd6565"));
        }

        TextView parentTitle = convertView.findViewById(R.id.ParentTitle);
        parentTitle.setText(currentImage.getTitle().substring(8));


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ScamImage currentImage = questionImages.getQuestionImages().get(groupPosition);

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_child, null);
        }

        if(currentImage.getCorrect()){
            convertView.setBackgroundColor(Color.parseColor("#68dd65"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#dd6565"));
        }
        TextView childTitle = convertView.findViewById(R.id.ChildTitle);
        String childText;
        if(currentImage.getScamStatus()){
            childText = "This is an example of a scam image!\n\n";
        } else {
            childText = "This is an example of a safe image!\n\n";
        }

        childTitle.setText(childText + currentImage.getSubtext() + "\n" + currentImage.getScamIndicators());

        ImageView scamImage = convertView.findViewById(R.id.scamImage);
        loadImageFromAssets(scamImage, currentImage.getOverlayFileLocation());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void loadImageFromAssets(ImageView scamImageView, String filePath) {
        AssetManager assetManager = context.getAssets();
        try {
            // open the file from assets using the AssetManager
            InputStream inputStream = assetManager.open(filePath);

            // decode the input stream to a Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // set the Bipmap to the ImageView
            scamImageView.setImageBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            // catch for a java exception (just in case)
            e.printStackTrace();
        }
    }
}
