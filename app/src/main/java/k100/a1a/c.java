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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.text.DecimalFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.io.File;

// Activity "c" begins here
public class c extends a implements SensorEventListener{

    //WIDGET DECLARATION FORMAT: Widget References -> PrivateMethod StaticReturnType WidgetType VariableName;
    private SensorManager sensorManager;
    private Sensor acceleration;
    private TextView textX;
    public TextView imageName;
    public long LastUpdate = 1;
    public float last_x = 0;
    public float last_y = 0;
    public float last_z = 0;
    private static final int SHAKE_THRESHOLD = 200;
    private static final int delay = 450; //mili-seconds



    // Declare activity variables (accessible to all methods within this activity)
    final String path = (Environment.getExternalStorageDirectory() + "/Pictures/memes/"); // Storage Path

    // Main Activity Loop
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c); // Load current activity layout.xml to the screen

        textX = (TextView) findViewById(R.id.TextViewX);

        // Run Methods We Created
        enlarge();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // Which sensor to read: Accelerometer
        sensorManager.registerListener(this, acceleration, SensorManager.SENSOR_DELAY_FASTEST); // Register the Listener  + Sampling Rate
    }


    //.........................Declaring Other Methods/Functions Below.......................................


    // Initial Setup + Initial view when first activated
    public void enlarge() {
        // Declare variables within method
        imgView = (ImageView) findViewById(R.id.expanded_image);
        imageName = (TextView)findViewById(R.id.TextView1);

        // Create array of all discovered files within "path"
        final File[] files = new File(path).listFiles();

        // Enlarge Image Initially from previous activity
        imgView.setImageURI(Uri.fromFile(new File(path + files[current_image].getName())));
        imageName.setText(files[current_image].getName());
    }


    private void getAccelerometer(SensorEvent event) {
        // Create array of all discovered files within "path"
        final File[] files = new File(path).listFiles();

        // Collect data from sensor
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        long WaitTime = System.currentTimeMillis();


        // Wait delay time before processing
            if ((WaitTime - LastUpdate) > delay) {
                long diffTime = (WaitTime - LastUpdate);
                LastUpdate = WaitTime;

                // Round off Numbers
                DecimalFormat dF = new DecimalFormat("#.#");
                x = Float.parseFloat(dF.format(x));
                y = Float.parseFloat(dF.format(y));
                z = Float.parseFloat(dF.format(z));
                textX.setText(String.valueOf(x) + " m/s2");

                float shake = Math.abs((z) - (last_z)) / diffTime * 10000;

                // SHAKE return to home activity
                if (shake > SHAKE_THRESHOLD) {
                    Intent myIntentB = new Intent(c.this, a.class);
                    myIntentB.putExtra("current_image", current_image);
                    startActivity(myIntentB);
                }
                else if (shake < SHAKE_THRESHOLD) {
                    // TILT right
                    if (x < -3) {
                        current_image++; // Increment Current Image index
                        current_image = current_image % files.length; // Set index to new index based on image location in array
                        imgView.setImageURI(Uri.fromFile(new File(path + files[current_image].getName())));
                        imageName.setText(files[current_image].getName());
                    }
                    // TILT left
                    if (x > 3) {
                        if (current_image == 0) // If reached end of array
                            current_image = files.length - 1; // Reset index to the top
                        else
                            current_image--; // Decrement index

                        imgView.setImageURI(Uri.fromFile(new File(path + files[current_image].getName())));
                        imageName.setText(files[current_image].getName());
                    }
                }

                // Update Old Values
                last_x = x;
                last_y = y;
                last_z = z;
            }
    }





    @Override
    protected void onResume() {
        super.onResume(); // register this class as a listener for the
        // orientation and
        // accelerometer sensors
        sensorManager.registerListener(this, acceleration, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() { // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub
    }
    // ON SENSOR CHANGE
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) { // CHECK TO MAKE SURE IT IS ACCELEROMETER
            getAccelerometer(event);


        }
    }

}// End of Activitiy





