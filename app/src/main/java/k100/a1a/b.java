// Import the MAIN package resource files in k100 app (manifest,layout,drables etc)
package k100.a1a;

// Import Other Libraries (like #include in C/C++)
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.TextView;
import android.view.MotionEvent;
import android.view.GestureDetector;
import android.support.v4.view.GestureDetectorCompat;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector.OnGestureListener;
import android.widget.Toast;

import java.io.File;

// Activity "b" begins here
public class b extends a implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{

    //WIDGET DECLARATION FORMAT: Widget References -> PrivateMethod StaticReturnType WidgetType VariableName;
    private GestureDetectorCompat gestDetect;
    public TextView imageName;


    // Declare activity variables (accessible to all methods within this activity)
    final String path = (Environment.getExternalStorageDirectory() + "/Pictures/memes/"); // Storage Path

    // Main Activity Loop
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b); // Load current activity layout.xml to the screen

        // Run Methods We Created
        enlarge();
        this.gestDetect = new GestureDetectorCompat(this,this); // Detect Gestures
        gestDetect.setOnDoubleTapListener(this); // Detect double taps
    }

    //.........................Declaring Other Methods/Functions Below.......................................


    // Initial Setup + Initial view when first activated
    public void enlarge() {
        // Declare variables within method
        imgView = (ImageView) findViewById(R.id.expanded_image);
        imageName = (TextView)findViewById(R.id.textView1);

        // Create array of all discovered files within "path"
        final File[] files = new File(path).listFiles();

        // Enlarge Image Initially from previous activity
        imgView.setImageURI(Uri.fromFile(new File(path + files[current_image].getName())));
        imageName.setText(files[current_image].getName());
    }

    // When SWIPED
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // Create array of all discovered files within "path"
        final File[] files = new File(path).listFiles();

        // Swipe Right
        if(e2.getX() > e1.getX()){

            current_image++; // Increment Current Image index
            current_image = current_image % files.length; // Set index to new index based on image location in array
            imgView.setImageURI(Uri.fromFile(new File(path + files[current_image].getName())));
            imageName.setText(files[current_image].getName());
        }
        // Swipe Left
        else
        if(e2.getX() < e1.getX()){
            if (current_image == 0) // If reached end of array
                current_image = files.length - 1; // Reset index to the top
            else
                current_image--; // Decrement index

            imgView.setImageURI(Uri.fromFile(new File(path + files[current_image].getName())));
            imageName.setText(files[current_image].getName());
        }
        return true;
    }

    // When SINGLE-TAPPED return to previous activity and return data
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Intent myIntentB = new Intent(b.this, a.class);
        myIntentB.putExtra("current_image",current_image);
        startActivity(myIntentB);
        return true;
    }

    // REQUIRED FOR GESTURES DONT TOUCH THIS! DEFAULT METHOD THAT GETS CALLED WHEN USER TOUCHES SCREEN
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestDetect.onTouchEvent(event); // If touch event, pass event
        return super.onTouchEvent(event);
    }

    // GESTURE METHODS NOT USED IN ACTIVITY A BUT REQUIRED TO RUN
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }
    @Override
    public void onLongPress(MotionEvent e) {
    }

}// End of Activitiy





