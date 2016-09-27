package br.com.pagueamigo.testsendpicture;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.pagueamigo.testsendpicture.entities.Transaction;
import br.com.pagueamigo.testsendpicture.rest.OnTaskListener;
import br.com.pagueamigo.testsendpicture.rest.requestpojo.ApiErrorBody;
import br.com.pagueamigo.testsendpicture.rest.tasks.AddRequestTransactionTask;

public class MainActivity extends AppCompatActivity {

    private Uri mCurrentPhotoURI;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            dispatchTakePictureIntent();
        }



        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Transaction transaction = new Transaction();
                transaction.message = "message";
                transaction.subject = "subject";
                transaction.friend = "http://pagueamigo-api.cfapps.io/api/person/59abd30ca1b35221075826f728fba89d";
                transaction.value = "BRL " + "1000,00";
                transaction.imagePath = mCurrentPhotoURI.getPath();

                new AddRequestTransactionTask(
                        MainActivity.this,
                        new OnTaskListener() {
                            @Override
                            public void onSuccess(AsyncTask task, Object result) {

                            }

                            @Override
                            public void onFailure(AsyncTask task, Object error) {

                            }

                            @Override
                            public void onCancel(AsyncTask task) {

                            }
                        }).execute(transaction);
            }
        });




    }




    /**
     * Take previewPicture.
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "br.com.pagueamigo.testsendpicture.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    setPic();
                    break;
            }
        }
    }

    /**
     * setPic.
     */
    private void setPic() {
        ImageView imageView = (ImageView) findViewById
                (R.id.image_view);

        imageView.setVisibility(View.VISIBLE);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoURI.getPath(), bmOptions);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = 4;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoURI.getPath(), bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    /**
     * Create an image file name.
     *
     * @return image file.
     * @throws IOException IOException.
     */
    private File createImageFile() throws IOException {
        //
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir.getPath() + File.separator + "I_" + timeStamp + ".jpg");

        mCurrentPhotoURI = Uri.fromFile(image);


        return image;
    }

}
