package com.zz.cpptest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private final int REQUEST_PERMISSION = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Replace your path
        final File f = new File("/storage/emulated/0","test.qmc3");
        Log.e("FILE","file exist " + f.exists());
        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(""+f.getAbsolutePath());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String absPath = f.getAbsolutePath();
                try {
                    boolean isConvert = convert(absPath);
                    Toast.makeText(MainActivity.this,
                            "convert =" +isConvert + "\n"+ absPath,
                            Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,"Permission granted." , Toast.LENGTH_SHORT).show();
                // Permission granted.
            } else {
                Toast.makeText(MainActivity.this,"User refused to grant permission." , Toast.LENGTH_SHORT).show();
                // User refused to grant permission.
            }
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native boolean convert(String fileName);

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}