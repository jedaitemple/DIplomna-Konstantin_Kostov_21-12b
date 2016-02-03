package  kosio.myapplication;
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
import android.os.Bundle;
import com.parse.CountCallback;
import com.chatt.demo.custom.CustomActivity;

/**
 * Created by Kosioplay on 1.2.2016 г..
 */
public class Statistics  extends CustomActivity {
   int a=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Chat");
        query.whereEqualTo("sender", UserList.user.getUsername());
        query.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {

                    a = count;
                    final TextView txtValue = (TextView) findViewById(R.id.txtExample);
                    txtValue.setText(Integer.toString(count));
                } else {

                }
            }
        });
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Chat");
        query1.whereEqualTo("receiver",UserList.user.getUsername() );
        query1.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {

                    a = count;
                    final TextView txtValue = (TextView) findViewById(R.id.txtExample1);
                    txtValue.setText(Integer.toString(count));
                } else {

                }
            }
        });


    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}
