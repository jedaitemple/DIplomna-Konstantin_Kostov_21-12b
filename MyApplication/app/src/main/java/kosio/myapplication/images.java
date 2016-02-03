package kosio.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TabHost;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import com.parse.ParseImageView;
import com.parse.ParseFile;
import com.parse.ParseObject;
import android.graphics.Bitmap;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.chatt.demo.custom.CustomActivity;
import com.chatt.demo.model.Conversation;
import com.chatt.demo.utils.Const;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import com.parse.GetCallback;
import com.parse.GetDataCallback;

public class images extends Activity {
    Button button;
    private ProgressDialog progressDialog;

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.imagedisplay);
        // Show progress dialog
        String objectIdOfObject = "87gVwtbqxY";
        ParseQuery<ParseObject> query=ParseQuery.getQuery("ImageUpload");
        query.getInBackground(objectIdOfObject, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    ParseFile picture = object.getParseFile("ImageFile");
                    ParseImageView imageView = (ParseImageView) findViewById(android.R.id.icon1);
                    imageView.setParseFile(picture);
                    imageView.loadInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {

                        }
                    });




        // Locate the button in main.xml
                    if (picture == null) {
                        // no parseFile in column "profilePicture" in this user.
                        return; // there will be no data to retrieve
                    }

                    picture.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                if (data.length == 0) {
                                    // data found, but nothing to extract. bad image or upload?
                                    return; // failing out
                                }

                                // SUCCESS
                                // convert data and display in an imageview
                            } else {
                                // ParseFile contained no data. data not added to ParseFile?
                            }
                        }
                    });
                } else {
                    // no users had this objectId
                }
            }
        });
    }
}
