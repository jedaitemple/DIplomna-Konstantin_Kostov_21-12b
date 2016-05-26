package kosio.myapplication;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseObject;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import android.widget.RadioButton;

import android.widget.RadioGroup;

import android.widget.RadioGroup.OnCheckedChangeListener;

import android.app.AlertDialog;
import android.util.Log;
import android.view.Window;

import com.parse.GetCallback;
import com.parse.ParseUser;

public class CreateBlog extends Activity {

    private Blog blog;
    private EditText titleEditText;
    private EditText contentEditText;
    private String postTitle;
    private String postContent;
    private Button saveNoteButton;
    private RadioGroup radioGroup;
String text;
    private RadioButton mychoice;
    private static int RESULT_LOAD_IMAGE = 1;
    private Button button;
    Bitmap bitmap = null;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.createblog);

        Intent intent = this.getIntent();



        if (intent.getExtras() != null) {
            blog = new Blog(intent.getStringExtra("noteId"), intent.getStringExtra("noteTitle"), intent.getStringExtra("noteContent"));

            titleEditText.setText(blog.getTitle());
            contentEditText.setText(blog.getContent());
        }
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

        saveNoteButton = (Button)findViewById(R.id.saveNote);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {


            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected

                if (checkedId == R.id.sport) {
                    mychoice = (RadioButton) findViewById(R.id.sport);
                    text="sport";
                    Toast.makeText(getApplicationContext(), "choice: sport",

                            Toast.LENGTH_SHORT).show();

                } else if (checkedId == R.id.school) {
                    mychoice = (RadioButton) findViewById(R.id.school);
                    text="school";
                    Toast.makeText(getApplicationContext(), "choice: school",

                            Toast.LENGTH_SHORT).show();

                } else if (checkedId == R.id.work) {
                    mychoice = (RadioButton) findViewById(R.id.work);
                    text="work";
                    Toast.makeText(getApplicationContext(), "choice: work",

                            Toast.LENGTH_SHORT).show();

                } else if (checkedId == R.id.other) {
                   text="other";
                    Toast.makeText(getApplicationContext(), "choice: other",

                            Toast.LENGTH_SHORT).show();

                }


            }

        });


        contentEditText = (EditText) findViewById(R.id.noteContent);





    }




    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
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

    private void saveNote() {

        postTitle = text;
        postContent = contentEditText.getText().toString();

        postContent = postContent.trim();

        // If user doesn't enter a title or content, do nothing
        // If user enters title, but no content, save
        // If user enters content with no title, give warning
        // If user enters both title and content, save

        if (!postTitle.isEmpty()) {

            // Check if post is being created or edited

            if (blog == null) {
                // create new post
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                // Create the ParseFile
                ParseFile file = new ParseFile("androidbegin.png", image);
                // Upload the image into Parse Cloud
                file.saveInBackground();

                // Create a New Class called "ImageUpload" in Parse
                final ParseObject post = new ParseObject("Post");
                // Create a column named "ImageName" and set the string
                post.put("ImageName", "AndroidBegin Logo");
                // Create a column named "ImageFile" and insert the image
                post.put("ImageFile", file);

                // Create the class and the columns
                post.saveInBackground();

                // Show a simple toast message
                Toast.makeText(CreateBlog.this, "Image Uploaded",
                        Toast.LENGTH_SHORT).show();

                post.put("title", postTitle);
                post.put("content", postContent);
                post.put("author", ParseUser.getCurrentUser());
                setProgressBarIndeterminateVisibility(true);
                post.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        setProgressBarIndeterminateVisibility(false);
                        if (e == null) {
                            // Saved successfully.
                            blog = new Blog(post.getObjectId(), postTitle, postContent);
                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                        } else {
                            // The save failed.
                            Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                            Log.d(getClass().getSimpleName(), "User update error: " + e);
                        }
                    }
                });

            }
            else {
                // update post

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");

                // Retrieve the object by id
                query.getInBackground(blog.getId(), new GetCallback<ParseObject>() {
                    public void done(ParseObject post, ParseException e) {
                        if (e == null) {
                            // Now let's update it with some new data.
                            post.put("title", postTitle);
                            post.put("content", postContent);
                            setProgressBarIndeterminateVisibility(true);
                            post.saveInBackground(new SaveCallback() {
                                public void done(ParseException e) {
                                    setProgressBarIndeterminateVisibility(false);
                                    if (e == null) {
                                        // Saved successfully.
                                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // The save failed.
                                        Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                                        Log.d(getClass().getSimpleName(), "User update error: " + e);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
        else if (postTitle.isEmpty() && !postContent.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateBlog.this);
            builder.setMessage(R.string.edit_error_message)
                    .setTitle(R.string.edit_error_title)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
