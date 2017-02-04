// Import the MAIN package resource files in k100 app (manifest,layout,drables etc)
package k100.a1a;

// Import Other Libraries (like #include in C/C++)
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;



// Activity "b" begins here
public class d extends a {

    //WIDGET DECLARATION FORMAT: Widget References -> PrivateMethod StaticReturnType WidgetType VariableName;
    public static TextView txtView;  // Name for TextView widget
    public static Button buttonNxt; // Name for Button 1 widget
    public static Button buttonPre; // Name for Button 2 widget
    public static Button buttonGo; // Name for Button 3 widget
    public static ImageView imgView;  // Name for ImageView widget
    public TextView imageName;

    // Declare activity variables (accessible to all methods within this activity)
    final String path = (Environment.getExternalStorageDirectory() + "/Pictures/memes/"); // Storage Path

    // Main Activity Loop
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d); // Load current activity layout.xml to the screen

        // Run Methods We Created
        enlarge();
    }

    //.........................Declaring Other Methods/Functions Below.......................................


    // Initial Setup + Initial view when first activated
    public void enlarge() {
        // Create array of all discovered files within "path"
        final File[] files = new File(path).listFiles();

        // Declaring Variables within Method -> VariableNane = (WidgetType) MethodOfDefiningClass (ActualWidget ID)
        imgView = (ImageView) findViewById(R.id.imageView);
        txtView = (TextView) findViewById(R.id.image_name);
        imageName = (TextView)findViewById(R.id.textView1);
        buttonNxt = (Button) findViewById(R.id.button_N); // Declare Next
        buttonPre = (Button) findViewById(R.id.button_P); // Declare Previous
        buttonGo = (Button) findViewById(R.id.buttonB); // Declare Go button

        // Enlarge Image Initially from previous activity
        imgView.setImageURI(Uri.fromFile(new File(path + files[current_image].getName())));
        imageName.setText(files[current_image].getName());

        // When NEXT button pressed
        buttonNxt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        current_image++; // Increment Current Image index
                        current_image = current_image % files.length; // Set index to new index based on image location in array
                        imgView.setImageURI(Uri.fromFile(new File(path + files[current_image].getName())));
                        imageName.setText(files[current_image].getName());
                    }

                }
        );

        // When PREVIOUS button pressed
        buttonPre.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (current_image == 0) // If reached end of array
                            current_image = files.length - 1; // Reset index to the top
                        else
                            current_image--; // Decrement index

                        imgView.setImageURI(Uri.fromFile(new File(path + files[current_image].getName())));
                        imageName.setText(files[current_image].getName());
                    }

                }
        );

        // When EXPAND button pressed -> Switch Activity and Export data
        buttonGo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntentB = new Intent(d.this, a.class);
                        myIntentB.putExtra("current_image",current_image);
                        startActivity(myIntentB);
                    }

                }
        );

    }
}// End of Activitiy





