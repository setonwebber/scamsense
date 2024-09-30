package com.example.scamsense;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.content.res.AssetManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.app.Activity;
import android.util.DisplayMetrics;

import java.io.IOException;
import java.io.InputStream;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LevelActivity extends AppCompatActivity {
    private ImageView scamImage;
    private ImageButton scamButton;
    private ImageButton safeButton;
    private ImageButton menuButton;

    @SuppressLint({"SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // init all activiy and viewer attributes
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_level);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        ((View) decorView).setSystemUiVisibility(uiOptions);

        // load datamanger and get objects we will need in this activity
        dataManager dataManager = com.example.scamsense.dataManager.getInstance();
        QuestionImages questionImages = dataManager.getQuestionImages();
        Levels levels = dataManager.getLevels();

        // init all views from xml activity
        scamImage = findViewById(R.id.scamImage);
        scamButton = findViewById(R.id.btnScam);
        safeButton = findViewById(R.id.btnSafe);
        menuButton = findViewById(R.id.menuButton);

        // load the screen (buttons and image)
        loadScreen(questionImages);

        // when the scam button is clicked.
        scamButton.setOnClickListener(v -> {
            // get the current image the user answered
            ScamImage currentImage = questionImages.getQuestionImages().get(questionImages.getCurrentImageIndex());

            // if the user got the question right (the image is a scam)
            if (currentImage.getScamStatus()){
                // increment rightanswers
                levels.getCurrentLevel().setRightAnswers(levels.getCurrentLevel().getRightAnswers() + 1);
                // set answer to true
                currentImage.setAnswer(true);
                // set correct to true
                currentImage.setCorrect(true);
            } else{ // got the question wrong
                // set answer to false
                currentImage.setAnswer(false);
                // set correct to false
                currentImage.setCorrect(false);
            }
            // set completed to true
            currentImage.setCompleted(true);
            // reload the image and buttons
            loadScreen(questionImages);
            // load the popup window 
            popup_window(questionImages, v);
        });

        // when the safe button is clicked.
        safeButton.setOnClickListener(v -> {
            // get the current image the user answered
            ScamImage currentImage = questionImages.getQuestionImages().get(questionImages.getCurrentImageIndex());

            // if the user got the question right (the image isnt a scam)
            if (!currentImage.getScamStatus()){
                // increment rightanswers
                levels.getCurrentLevel().setRightAnswers(levels.getCurrentLevel().getRightAnswers() + 1);
                // set answer to true
                currentImage.setAnswer(true);
                // set correct to true
                currentImage.setCorrect(true);
            } else { // got the question wrong
                // set answer to false
                currentImage.setAnswer(false);
                // set correct to false
                currentImage.setCorrect(false);
            }
            // set completed to true
            currentImage.setCompleted(true);
            // reload the image and buttons
            loadScreen(questionImages);
            // load the popup window 
            popup_window(questionImages, v);
        });

        // if the menubutton is clicked
        menuButton.setOnClickListener(v-> {
            // start the mainactivity
            Intent intent=new Intent(LevelActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    public void loadScreen(QuestionImages questionImages){
        ScamImage currentImage = questionImages.getQuestionImages().get(questionImages.getCurrentImageIndex());

        // If the current image hasnt been completed before.
        if (!currentImage.getCompleted()){
            // Set scam and safe buttons to default values.
            scamButton.setClickable(true);
            scamButton.setImageResource(R.drawable.level_button_scam);
            safeButton.setClickable(true);
            safeButton.setImageResource(R.drawable.level_button_safe);

            scamButton.setVisibility(View.VISIBLE);
            safeButton.setVisibility(View.VISIBLE);

            // load image
            loadImageFromAssets(scamImage, currentImage.getFileLocation());

        } else {
            // turn scam and safe buttons off
            scamButton.setClickable(false);
            safeButton.setClickable(false);

            scamButton.setVisibility(View.INVISIBLE);
            safeButton.setVisibility(View.INVISIBLE);

            // load overlay image
            loadImageFromAssets(scamImage, currentImage.getOverlayFileLocation());

        }
    }

    // inflator function for the popupwindow that shows the result of the answer
    @SuppressLint("ClickableViewAccessibility")
    public void popup_window(QuestionImages questionImages, View v){
        ScamImage currentImage = questionImages.getQuestionImages().get(questionImages.getCurrentImageIndex());

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height);

        // get display details
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) v.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // show the popup window
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, displayMetrics.heightPixels / 4);

        // init popup variables
        TextView popupText = popupView.findViewById(R.id.popup_text);
        ImageButton nextButton = popupView.findViewById(R.id.nextbutton);
        RelativeLayout frameLayout = popupView.findViewById(R.id.framelayout);

        // dismiss the popup window when touched
        nextButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                scamButton.setClickable(true);
                safeButton.setClickable(true);

                if (questionImages.nextImage()){
                    // because of how functions work, if nextImage is true it wouldve already iterated to the next image, if it's false, it runs the else and doesnt iterate, funny right.
                    loadScreen(questionImages);
                } else {
                    Intent intent=new Intent(LevelActivity.this, LevelCompleteActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });
        if (currentImage.getAnswer()) {
            // if they got the answer right, set the background to green and add a little msg
            frameLayout.setBackgroundColor(Color.parseColor("#68dd65"));
            popupText.setText("You got this image right!\n" + currentImage.getScamIndicators()); // change this message so its less shitty.
        } else {
            // if they got the answer wrong, set the background to red and add a different msg
            frameLayout.setBackgroundColor(Color.parseColor("#dd6565"));
            popupText.setText("You got this question wrong!\n" + currentImage.getScamIndicators()); // change this message so its less shitty.
        }
    }

    // function that loads images from assets folder.
    public void loadImageFromAssets(ImageView scamImageView, String filePath) {
        AssetManager assetManager = getAssets();
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