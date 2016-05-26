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

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kosio.myapplication.custom.CustomActivity;
import kosio.myapplication.Conv.Conversation;
import kosio.myapplication.Utilities.Const;

public class Myprofile extends CustomActivity {
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
        setContentView(R.layout.myprofile);

            Display();




    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();

        }

        return super.onOptionsItemSelected(item);
    }


    public  void Display() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User1");
        ArrayList<String> al = new ArrayList<String>();
        al.add(Users.user.getUsername());

        query.whereContainedIn("sender", al);
        query.orderByDescending("updatedAt");

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {

                } else {

                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    ParseFile file = object.getParseFile("ImageFile");
                    Uri fileUri1 = Uri.parse(file.getUrl());

                    Picasso.with(getApplicationContext())
                            .load(fileUri1)
                            .resize(300, 300)
                            .into(imageView);


                    // got the most recently modified object... do something with it here
                }
            }
        });



    }

}

