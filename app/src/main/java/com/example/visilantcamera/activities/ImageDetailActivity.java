package com.example.visilantcamera.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.visilantcamera.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class ImageDetailActivity extends AppCompatActivity {

    // creating a string variable, image view variable
    // and a variable for our scale gesture detector class.
    int position;
    ArrayList<String> imagePathList= new ArrayList();
    String imgPath;
    private ImageView imageView;
    private ScaleGestureDetector scaleGestureDetector;

    // on below line we are defining our scale factor.
    private float mScaleFactor = 1.0f;
    private TextView tvImageName;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        // on below line getting data which we have passed from our adapter class.
        position = getIntent().getIntExtra("pos", 0);
        imagePathList= getIntent().getStringArrayListExtra("imgPathList");
        //imgPath = getIntent().getStringExtra("imgPath");
        imgPath=imagePathList.get(position);


        // initializing our image view.
        imageView = findViewById(R.id.idIVImage);
        tvImageName=findViewById(R.id.imageName);

        // on below line we are initializing our scale gesture detector for zoom in and out for our image.
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        // on below line we are getting our image file from its path.
        File imgFile = new File(imgPath);
        tvImageName.setText(imgPath);

        // if the file exists then we are loading that image in our image view.
        if (imgFile.exists()) {
            Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background).into(imageView);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // inside on touch event method we are calling on
        // touch event method and passing our motion event to it.
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        Toast.makeText(this, "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show ();
                        if(position!=0){
                            position-=1;
                            imgPath=imagePathList.get(position);
                            File imgFile = new File(imgPath);
                            tvImageName.setText(imgPath);

                            // if the file exists then we are loading that image in our image view.
                            if (imgFile.exists()) {
                                Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background).into(imageView);
                            }
                        }

                    }

                    // Right to left swipe action
                    else
                    {
                        Toast.makeText(this, "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();
                        if(position!=imagePathList.size()-1){
                            position +=1;
                            imgPath=imagePathList.get(position);
                            File imgFile = new File(imgPath);
                            tvImageName.setText(imgPath);

                            // if the file exists then we are loading that image in our image view.
                            if (imgFile.exists()) {
                                Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background).into(imageView);
                            }
                        }

                    }

                }

                break;
        }
        scaleGestureDetector.onTouchEvent(event);

        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        // on below line we are creating a class for our scale
        // listener and  extending it with gesture listener.
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {

            // inside on scale method we are setting scale
            // for our image in our image view.
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            // on below line we are setting
            // scale x and scale y to our image view.
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }
}
