package istd.eric.myapp1checkoffs;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class CheckoffCameraActivity extends AppCompatActivity {

    String imagepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoff_camera);
        // Request permission
        ActivityCompat.requestPermissions(
                this,
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                              Manifest.permission.WRITE_EXTERNAL_STORAGE,
                              Manifest.permission.CAMERA
                },
                1
        );
        //takephoto(null); // Take photo first, then display. Cannot. Need to request permission first.
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            boolean granted = true;
            for (int res : grantResults){
                if (res != PackageManager.PERMISSION_GRANTED){
                    granted = false;
                }
            }
            if (granted) {
                takephoto(null);
            }
            else {
                ((TextView) findViewById(R.id.takephoto)).setText("Take Photo (DO NOT CLICK OR APP WILL CRASH)");
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        //String timeStamp = String.valueOf((new Date()).getTime());
        String imageFileName = "myapp_img";
        File appdirectory = new File(Environment.getExternalStorageDirectory(), "Myapp1");
        System.out.println(appdirectory.getPath());
        System.out.println(appdirectory.exists());
        if (!appdirectory.exists()) {
            System.out.println(appdirectory.mkdir());
            System.out.println(appdirectory.mkdirs());
        }
        File image = File.createTempFile(imageFileName,  ".jpg", appdirectory); // createTempFile appends a random unique number to the file name.

        imagepath = image.getAbsolutePath();
        // Save a file: path for use with ACTION_VIEW intents
        // mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public void takephoto(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) == null) {
            return;
        }
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (photoFile == null) {
            return;
        }
        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), "istd.eric.myapp1checkoffs", photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(takePictureIntent, 10314); // Arbitrary request code 10314
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10314 && resultCode == RESULT_OK) {
            if (data != null) { // Always null if saving to external path.
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ((ImageView) findViewById(R.id.photothumb)).setImageBitmap(imageBitmap);
            } else { // this always runs.
                Bitmap imageBitmap = BitmapFactory.decodeFile(imagepath);
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                imageBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
                ((ImageView) findViewById(R.id.photothumb)).setImageBitmap(imageBitmap);
            }
            ((TextView) findViewById(R.id.saveinlocation)).setText(imagepath);
        } else {
            ((TextView) findViewById(R.id.saveinlocation)).setText("DID NOT SAVE");
            (new File(imagepath)).delete();
        }
    }
}
