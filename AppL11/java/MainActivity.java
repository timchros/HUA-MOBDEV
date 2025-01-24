package hua.dit.mobdev.ec.appl11;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Read Web Doc - HTML
        findViewById(R.id.button1).setOnClickListener(v->{
            Log.d(TAG, "Button 1 - Read Web Doc - HTML");
            new Thread(()->{
                try {
                    final String pageURL = "https://www.enikos.gr/";
                    final URL url = new URL(pageURL);
                    final HttpsURLConnection httpsConn =  (HttpsURLConnection) url.openConnection();
                    final int responseCode =  httpsConn.getResponseCode();
                    Log.i(TAG, "ResponseCode: " + responseCode);
                    final InputStream is = httpsConn.getInputStream();
                    final String pageContent = getStringFromIS(is);
                    Log.i(TAG, "PageContent.length(): " + pageContent.length());
                    httpsConn.disconnect();
                    final String toastStr = responseCode + " :: page-length: " + pageContent.length();
                    runOnUiThread(()->{
                        Toast.makeText(this, toastStr, Toast.LENGTH_LONG).show();
                    });
                }catch (Throwable t) {
                    throw new RuntimeException("Cannot access a Web Resource 1...", t);
                }
            }).start();
        }); // END OF button1 Listener

        // Read Web Doc - JSON
        findViewById(R.id.button2).setOnClickListener(v->{
            Log.d(TAG, "Button 2 - Read Web Doc - JSON");
            new Thread(()->{
                try {
                    final String jsonFileURL = "http://api.open-notify.org/iss-now.json";
                    final URL jsonURl = new URL(jsonFileURL);
                    final HttpURLConnection httpConn =  (HttpURLConnection) jsonURl.openConnection();
                    int responseCode =  httpConn.getResponseCode();
                    Log.i(TAG, "responseCode: " + responseCode);
                    final InputStream is = httpConn.getInputStream();
                    final String pageContent = getStringFromIS(is);
                    // Json Data
                    final JSONObject jo = new JSONObject(pageContent);
                    String timestamp = jo.getString("timestamp");
                    Log.i(TAG, "JSONObject timestamp: " + timestamp);
                    final String toastStr= "JSON - Timestamp: " + timestamp;
                    runOnUiThread(()->{
                        Toast.makeText(this, toastStr, Toast.LENGTH_SHORT).show();
                    });
                }catch (Throwable t) {
                    throw new RuntimeException("Cannot access a Web Resource 2...", t);
                }
            }).start();
        }); // END OF button2 Listener

        // Test My Firebase DB
        findViewById(R.id.button3).setOnClickListener(v->{
            Log.d(TAG, "Button 3 - Test My Firebase DB");
            new Thread(()->{
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                // Write Data
                database.getReference().child("app11-key").setValue("lecture 11");
                Log.i(TAG, "Data written !");
                // Read Data
                database.getReference().child("key11").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Log.i(TAG, "Data Read - Data: " + task.getResult().getValue());
                        runOnUiThread(()->{
                            Toast.makeText(MainActivity.this, "Data: " + task.getResult().getValue(), Toast.LENGTH_SHORT).show();
                        });
                    }
                }); // END OF Listener with onComplete
            }).start();
        }); // END OF button3 Listener

        // Get Current Location
        findViewById(R.id.button4).setOnClickListener(v->{
            Log.d(TAG, "Button 4 - Get Current Location");
            // Check Runtime Permissions
            boolean have_permissions = checkAllPermissions(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            // Request Permissions if being necessary
            if (!have_permissions)
                requestPermissions(new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                }, 123);
            else {
                new Thread(()->{
                    final LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
                    final boolean isGpsProviderEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    Log.i(TAG, "Check - isGpsProviderEnabled: " + isGpsProviderEnabled);
                    if (isGpsProviderEnabled) {
                        @SuppressLint("MissingPermission")
                        final Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Log.i(TAG, "User - Location: " + location);
                        if (location != null) {
                            final String toastStr = "lat: " + location.getLatitude() + " , lng: " + location.getLongitude();
                            runOnUiThread(()->{
                                Toast.makeText(this, toastStr, Toast.LENGTH_SHORT).show();
                            });
                        }
                    } else {
                        Log.d(TAG, "GPS provider is not enabled !");
                    }
                }).start();
            }
        }); // END OF button4 Listener

    } // END OF Activity onCreate(..)

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult - requestCode: " + requestCode);
        for (int i = 0; i <permissions.length ; i++) {
            Log.d(TAG, " * Permission: " + permissions[i] + " --> GrantResult: " + grantResults[i]);
        }
    } // END OF onRequestPermissionsResult(..)

    /**
     * @return true if all permissions granted
     */
    private boolean checkAllPermissions(String... permissions) {
        for (String permission : permissions) {
            int permission_check = checkSelfPermission(permission);
            if (permission_check != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    } // END OF checkAllPermissions(..)

    /**
     * @return A String with the data read from the given InputStream
     */
    private String getStringFromIS(InputStream is) {
        try {
            final StringBuilder sb = new StringBuilder();
            final BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        } catch (Throwable t) {
            throw new RuntimeException("Reading Data from InputStream problem !", t);
        }
    } // END OF getStringFromIS(..)

}