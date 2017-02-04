// Import the MAIN package resource files in k100 app (manifest,layout,drables etc)
package k100.a1a;

// Import Other Libraries (like #include in C/C++)
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import android.view.MotionEvent;
import android.view.GestureDetector;
import android.support.v4.view.GestureDetectorCompat;

import android.view.GestureDetector.OnGestureListener;
import android.os.Environment;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

// Activity "a" begins here
public class a extends AppCompatActivity {

    //WIDGET DECLARATION FORMAT: Widget References -> PrivateMethod StaticReturnType WidgetType VariableName;
    public static TextView txtView;  // Name for TextView widget
    public static Button buttonSw; // Name for Button 1 widget
    public static Button buttonBu; // Name for Button 2 widget
    public static Button buttonMo; // Name for Button 3 widget
    public static Button buttonCa; // Name for Button 3 widget
    public static ImageView imgView;  // Name for ImageView widget
    private GestureDetectorCompat gestDetect;
    private static final String TAG = "MainActivity";
    public static  final int REQUEST_CAPTURE = 1;


    // Declare activity variables (accessible to all methods within this activity)
    public int current_image = 1; // Image array index variable
    public int REQUEST_CODE = 007; // Required to allow Permissions
    final String path = (Environment.getExternalStorageDirectory() + "/Pictures/memes/"); // Storage Path

    // Main Activity Loop
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a); // Load current activity layout.xml to the screen

        // Run Methods We Created
        initiate(); // Method for initializing and handling button clicks

    }


    //.........................Declaring Other Methods/Functions Below.......................................


    // INITIATE STUFF + HANDLE BUTTON CLICKS
    public void initiate() {

        /*
        // PERMISSIONS FOR SDK 23 (and greater) MIGHT NEED THIS JUST ONCE
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG,"Permission is granted");
            //File write logic here
            return;
        }
        */

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

        // Declaring Variables within Method -> VariableNane = (WidgetType) MethodOfDefiningClass (ActualWidget ID)
        imgView = (ImageView) findViewById(R.id.imageView);
        buttonSw = (Button) findViewById(R.id.buttonS); // Declare Next button with button id = button_1
        buttonBu = (Button) findViewById(R.id.buttonB); // Declare Previous button with button id = button_2
        buttonMo = (Button) findViewById(R.id.buttonM); // Declare Go button with button id = button_2
        buttonCa = (Button) findViewById(R.id.button_Cam); // Declare Go button with button id = button_2

        // Import data from other activities
        Intent mIntent = getIntent();
        int importedIndex = mIntent.getIntExtra("current_image", 1);
        current_image = importedIndex;


        // Camera
        buttonCa.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cam, REQUEST_CAPTURE);
                    }

                }
        );

        // Switch to SWIPE gallery Activity B
        buttonSw.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntentB = new Intent(a.this, b.class);
                        myIntentB.putExtra("current_image", current_image);
                        startActivity(myIntentB);
                    }

                }
        );

        // Switch to MOTION gallery Activity C
        buttonMo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntentC = new Intent(a.this, c.class);
                        myIntentC.putExtra("current_image", current_image);
                        startActivity(myIntentC);
                    }

                }
        );

        // Switch to BUTTON gallery Activity D
        buttonBu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntentD = new Intent(a.this, d.class);
                        myIntentD.putExtra("current_image", current_image);
                        startActivity(myIntentD);
                    }

                }
        );


    }

    // HANDLE CAMERA IMAGE SAVING
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK)   {
            OutputStream output;

            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            //result_photo.setImageBitmap(photo);


            File filepath = Environment.getExternalStorageDirectory();
            File dir = new File(filepath.getAbsoluteFile() + "/Pictures/memes/");
            dir.mkdirs(); // make directory if doesn't exist
            File file = new File(path, "camera_image" + timeStamp + ".jpeg");

            //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            try {
                output = new FileOutputStream(file);
                photo.compress(Bitmap.CompressFormat.PNG,100,output);
                output.flush();
                output.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
} // End Activity A



