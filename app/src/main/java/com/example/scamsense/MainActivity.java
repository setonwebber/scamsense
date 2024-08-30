package com.example.scamsense;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Log;
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
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView scamImage;
    private ImageButton scamButton;
    private ImageButton safeButton;
    private ImageButton nextButton;
    private ImageButton backButton;
    private ImageButton informationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // initialize all values
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scamImage = findViewById(R.id.scamImage);
        scamButton = findViewById(R.id.btnScam);
        safeButton = findViewById(R.id.btnSafe);
        nextButton = findViewById(R.id.btnNext);
        backButton = findViewById(R.id.btnBack);
        informationButton = findViewById(R.id.btnInformation);



        // load scamimages list full of scam image objects.
        scamImagesVec scamImages = new scamImagesVec();
        scamImages.loadVector(getAssets());

        // load the screen
        loadScreen(scamImages);

        // when the scam button is clicked.
        scamButton.setOnClickListener(v -> {
            scamImage currentImage = scamImages.getScamImages().get(scamImages.getCurrentImageIndex());
            if (currentImage.getScamStatus()){
                // some type of comfirmation that the answer was correct
                Log.d("PRINTCONSOLE", "scam button pressed, answer correct.");
            } else{
                // some type of comfirmation that the answer was incorrect
                Log.d("PRINTCONSOLE", "scam button pressed, answer incorrect.");
                popup_window(scamImages, v);
            }
            currentImage.setCompleted(true);
            currentImage.setAnswer("scam");

            loadScreen(scamImages);
        });

        // when the safe button is clicked.
        safeButton.setOnClickListener(v -> {
            scamImage currentImage = scamImages.getScamImages().get(scamImages.getCurrentImageIndex());

            if (!currentImage.getScamStatus()){
                // some type of comfirmation that the answer was correct
                Log.d("PRINTCONSOLE", "safe button pressed, answer correct.");
            } else{
                // some type of comfirmation that the answer was incorrect
                Log.d("PRINTCONSOLE", "safe button pressed, answer incorrect.");
                popup_window(scamImages, v);
            }
            currentImage.setCompleted(true);
            currentImage.setAnswer("safe");

            Log.d("PRINTCONSOLE", String.valueOf(currentImage.getScamStatus()));

            loadScreen(scamImages);
        });

        nextButton.setOnClickListener(v -> {
            scamImages.nextImage();
            loadScreen(scamImages);
        });

        backButton.setOnClickListener(v -> {
            scamImages.backImage();
            loadScreen(scamImages);
        });

        informationButton.setOnClickListener(v -> {
            popup_window(scamImages, v);
        });
    }

    public void loadScreen(scamImagesVec scamImages){
        scamImage currentImage = scamImages.getScamImages().get(scamImages.getCurrentImageIndex());

        // if we are at the first image...
        if (scamImages.getCurrentImageIndex() == 0) {
            // make the back button inactive.
            backButton.setClickable(false);
            backButton.setImageResource(R.drawable.back_inactive);
        } else{ // at any other image.
            // make the back button active.
            backButton.setClickable(true);
            backButton.setImageResource(R.drawable.back_active);
        }

        // if we are at the last image, or the image is not completed.
        if (scamImages.getCurrentImageIndex() == scamImages.getScamImages().size() - 1 || !currentImage.getCompleted()) {
            // make the next button inactive.
            nextButton.setClickable(false);
            nextButton.setImageResource(R.drawable.next_inactive);
        } else{ // if we are at any other image that has also been completed.
            // make the next button active.
            nextButton.setClickable(true);
            nextButton.setImageResource(R.drawable.next_active);
        }

        // If the current image hasnt been completed before.
        if (!currentImage.getCompleted()){
            // Set scam and safe buttons to default values.
            scamButton.setClickable(true);
            scamButton.setImageResource(R.drawable.scam);
            safeButton.setClickable(true);
            safeButton.setImageResource(R.drawable.safe);

            // load image
            loadImageFromAssets(scamImage, currentImage.getFileLocation());

            // set info button to inactive
            informationButton.setClickable(false);
            informationButton.setImageResource(R.drawable.info_inactive);
        } else{
            // turn scam and safe buttons off
            scamButton.setClickable(false);
            safeButton.setClickable(false);

            // set scam and safe buttons to click the one the user clicked.
            if (currentImage.getAnswer() == "scam"){
                scamButton.setImageResource(R.drawable.scam_clicked);
                safeButton.setImageResource(R.drawable.safe);
            } else{
                scamButton.setImageResource(R.drawable.scam);
                safeButton.setImageResource(R.drawable.safe_clicked);
            }

            // load overlay image
            loadImageFromAssets(scamImage, currentImage.getOverlayFileLocation());

            // set info button to inactive
            informationButton.setClickable(true);
            informationButton.setImageResource(R.drawable.info_active);
        }
    }

    public void popup_window(scamImagesVec scamImages, View v){
        scamImage currentImage = scamImages.getScamImages().get(scamImages.getCurrentImageIndex());

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched outside
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        // Set the text to the subtext.
        TextView popupText = popupView.findViewById(R.id.popup_text);

        boolean answerScam;
        if (currentImage.getAnswer().equals("scam")){ answerScam = true; } else { answerScam = false;}

        if (currentImage.getScamStatus() == answerScam) {
            popupText.setBackgroundColor(Color.parseColor("#68dd65"));
            popupText.setText("You got this image right!\n\n" + currentImage.getSubtext()); // change this message so its less shitty.
        } else {
            popupText.setBackgroundColor(Color.parseColor("#dd6565"));
            popupText.setText("You got this question wrong!\n\n" + currentImage.getSubtext()); // change this message so its less shitty.
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