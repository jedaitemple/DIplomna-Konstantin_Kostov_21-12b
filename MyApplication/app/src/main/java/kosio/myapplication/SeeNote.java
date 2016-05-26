package kosio.myapplication;

/**
 * Created by Kosioplay on 5.2.2016 г..
 */
/**
 * Created by Kosioplay on 5.2.2016 г..
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import kosio.myapplication.custom.CustomActivity;
import kosio.myapplication.Conv.Conversation;
import kosio.myapplication.Utilities.Const;

public class SeeNote extends CustomActivity {
    Bitmap bmp = null;
    /**
     * The Conversation list.
     */
    private ArrayList<Conversation> convList;

    /**
     * The chat adapter.
     */

    /**
     * The Editext to compose the message.
     */
    private EditText txt;

    /**
     * The user name of buddy.

     */
    private String buddy;

    /**
     * The date of last message in conversation.
     */
    private Date lastMsgDate;

    /**
     * Flag to hold if the activity is running or not.
     */
    private boolean isRunning;

    /**
     * The handler.
     */
    private static Handler handler;
    private static int RESULT_LOAD_IMAGE = 1;
    Button button;
    ImageView viewImage;
    Button b;
    String a;
    private Blog blog;
    private EditText titleEditText;
    private EditText idEditText;
    private EditText contentEditText;
    private String postTitle;
    private String postContent;
    private Button saveNoteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity);

        Intent intent = this.getIntent();

        TextView creator = (TextView) findViewById(R.id.creator);
        creator.setText(Users.user.getUsername());
      TextView titleEditText = (TextView) findViewById(R.id.noteTitle);
      TextView contentEditText = (TextView) findViewById(R.id.noteContent);

        if (intent.getExtras() != null) {
            blog = new Blog(intent.getStringExtra("noteId"), intent.getStringExtra("noteTitle"), intent.getStringExtra("noteContent"));
            titleEditText.setText(blog.getTitle());
            contentEditText.setText(blog.getContent());
           Display();
        }

        final Button switchact =(Button)findViewById(R.id.comments);
        switchact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                display();

            }
        });

    }

private void display(){
    Intent intent = new Intent(this, Comments.class);
    intent.putExtra("noteId", blog.getId());
    intent.putExtra("noteTitle", blog.getTitle());
    intent.putExtra("noteContent", blog.getContent());
    startActivity(intent);
}



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();

        }

        return super.onOptionsItemSelected(item);
    }
    public  void Display() {

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
// Locate the class table named "ImageUpload" in Parse.com
Uri fileUri1;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.getInBackground(blog.getId(), new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    ParseFile file = object.getParseFile("ImageFile");
                    Uri fileUri1 = Uri.parse(file.getUrl());

                    Picasso.with(getApplicationContext())
                            .load(fileUri1)
                            .resize(300, 300)
                            .into(imageView);
                } else {

                }
            }
        });

    }

}

