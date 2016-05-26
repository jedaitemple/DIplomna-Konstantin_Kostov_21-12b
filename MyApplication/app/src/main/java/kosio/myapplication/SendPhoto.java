package kosio.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

import kosio.myapplication.custom.CustomActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseFile;
import com.parse.ParseObject;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.Toast;
import kosio.myapplication.custom.CustomActivity;

import android.widget.ImageView;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

public class SendPhoto extends CustomActivity {

    private String receiver;
    /**
     * The handler.
     */
    private static Handler handler;
    private static int RESULT_LOAD_IMAGE = 1;
    Button button;
    ImageView viewImage;
    Button b;
    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    Bitmap bitmap = null;
    // GUI components


    protected void onCreate(Bundle savedInstanceState) {  // Image loading result to pass to startActivityForResult method.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendphoto);

        receiver = getIntent().getStringExtra("receiver");

        getActionBar().setTitle(receiver);
        Button buttonLoadImage = (Button) findViewById(R.id.btnSelectPhoto);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        final Button switchact =(Button)findViewById(R.id.photo);
        switchact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                display();

            }
        });



        Button buttonsaveImage = (Button) findViewById(R.id.btn1);
        buttonsaveImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                // Create the ParseFile
                ParseFile file = new ParseFile("androidbegin.png", image);
                // Upload the image into Parse Cloud
                file.saveInBackground();

                // Create a New Class called "ImageUpload" in Parse
                ParseObject imgupload = new ParseObject("Chat1");

                // Create a column named "ImageName" and set the string
                imgupload.put("ImageName", "AndroidBegin Logo");
                imgupload.put("sender", Users.user.getUsername());
                imgupload.put("receiver", receiver);
                // Create a column named "ImageFile" and insert the image
                imgupload.put("ImageFile", file);

                // Create the class and the columns
                imgupload.saveInBackground();

                // Show a simple toast message
                Toast.makeText(SendPhoto.this, "Image Uploaded",
                        Toast.LENGTH_SHORT).show();


            }
        });

        handler = new Handler();
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onResume()
     */
    private void display(){
        Intent intent = new Intent(this, images.class);
        intent.putExtra("buddy", receiver);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.viewImage);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            try {
                bitmap = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }


    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}
