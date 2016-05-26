package kosio.myapplication;

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
import  kosio.myapplication.custom.CustomActivity;
import  kosio.myapplication.model.Conversation;
import  kosio.myapplication.utils.Const;
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
/**
 * The Class Chat is the Activity class that holds main chat screen. It shows
 * all the conversation messages between two users and also allows the user to
 * send and receive messages.
 */
public class Chat extends CustomActivity {
	Bitmap bmp = null;
	/**
	 * The Conversation list.
	 */
	private ArrayList<Conversation> convList;

	/**
	 * The chat adapter.
	 */
	private ChatAdapter adp;

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
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	private GestureDetectorCompat gestureDetectorCompat;
	// GUI components


	protected void onCreate(Bundle savedInstanceState) {  // Image loading result to pass to startActivityForResult method.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);

		convList = new ArrayList<Conversation>();
		ListView list = (ListView) findViewById(R.id.list);
		adp = new ChatAdapter();
		list.setAdapter(adp);
		list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		list.setStackFromBottom(true);

		txt = (EditText) findViewById(R.id.txt);
		txt.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		setTouchNClick(R.id.btnSend);
		buddy = getIntent().getStringExtra(Const.EXTRA_DATA);
		getActionBar().setTitle(buddy);
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
		final Button switchact3 =(Button)findViewById(R.id.image);
		switchact3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent act2 = new Intent(view.getContext(), images.class);
				startActivity(act2);

			}
		});
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

		tabHost.setup();

		TabHost.TabSpec tabSpec = tabHost.newTabSpec("Contacts");
		tabSpec.setContent(R.id.tabContactList);
		tabSpec.setIndicator("chat");
		tabHost.addTab(tabSpec);



		tabSpec = tabHost.newTabSpec("images");
		tabSpec.setContent(R.id.tabGroups);
		tabSpec.setIndicator("images");
		tabHost.addTab(tabSpec);


		Button buttonsaveImage = (Button) findViewById(R.id.btnSendimage);
		buttonsaveImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {


				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(txt.getWindowToken(), 0);

				String s = "Click here to see image";
				final Conversation c = new Conversation(s, new Date(),
						UserList.user.getUsername());
				c.setStatus(Conversation.STATUS_SENDING);
				convList.add(c);
				adp.notifyDataSetChanged();
				txt.setText(null);
				// Locate the image in res > drawable-hdpi
				// Convert it to byte
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				// Compress image to lower quality scale 1 - 100
				bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] image = stream.toByteArray();

				// Create the ParseFile
				ParseFile file = new ParseFile("androidbegin.png", image);
				// Upload the image into Parse Cloud
				file.saveInBackground();

				// Create a New Class called "ImageUpload" in Parse
				ParseObject imgupload = new ParseObject("Chat");
				imgupload.put("sender", UserList.user.getUsername());
				imgupload.put("receiver", buddy);
				// po.put("createdAt", "");
				imgupload.put("message", s);
				imgupload.saveEventually(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null)
							c.setStatus(Conversation.STATUS_SENT);
						else
							c.setStatus(Conversation.STATUS_FAILED);
						adp.notifyDataSetChanged();
					}
				});
				// Create a column named "ImageName" and set the string
				imgupload.put("message", "image");
				// Create a column named "ImageFile" and insert the image
				imgupload.put("ImageFile", file);

				// Create the class and the columns
				imgupload.saveInBackground();

				// Show a simple toast message
				Toast.makeText(Chat.this, "Image Uploaded",
						Toast.LENGTH_SHORT).show();
				bmp = null;
				finish();
				startActivity(getIntent());


			}
		});

		handler = new Handler();
	}
	public boolean onTouchEvent(MotionEvent event) {
		this.gestureDetectorCompat.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
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
			ImageView imageView = (ImageView) findViewById(R.id.T);
			imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			try {
				bmp = getBitmapFromUri(selectedImage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}


	}



	private Bitmap getBitmapFromUri(Uri uri) throws IOException {
		ParcelFileDescriptor parcelFileDescriptor =
				getContentResolver().openFileDescriptor(uri, "r");
		FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
		Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
		parcelFileDescriptor.close();
		return image;
	}


	protected void onResume()
	{
		super.onResume();
		isRunning = true;
		loadConversationList();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onPause()
	 */
	@Override
	protected void onPause()
	{
		super.onPause();
		isRunning = false;
	}

	/* (non-Javadoc)
	 * @see com.socialshare.custom.CustomFragment#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (v.getId() == R.id.btnSend)
		{
			sendMessage();
		}

	}



	/**
	 * Call this method to Send message to opponent. It does nothing if the text
	 * is empty otherwise it creates a Parse object for Chat message and send it
	 * to Parse server.
	 */
	private void sendMessage() {
		if (txt.length() == 0)
			return;
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(txt.getWindowToken(), 0);

		String s = txt.getText().toString();
		final Conversation c = new Conversation(s, new Date(),
				UserList.user.getUsername());
		c.setStatus(Conversation.STATUS_SENDING);
		convList.add(c);
		adp.notifyDataSetChanged();
		txt.setText(null);


		ParseObject po = new ParseObject("Chat");
		po.put("sender", UserList.user.getUsername());
		po.put("receiver", buddy);
		// po.put("createdAt", "");
		po.put("message", s);
		po.saveEventually(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null)
					c.setStatus(Conversation.STATUS_SENT);
				else
					c.setStatus(Conversation.STATUS_FAILED);
				adp.notifyDataSetChanged();
			}
		});
	}

	public void deleteStudent(){
		ParseQuery<ParseObject> query=ParseQuery.getQuery("Chat");
		query.whereEqualTo("special", "special");
		query.whereEqualTo("receiver", UserList.user.getUsername());
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> parseObjects, ParseException e) {
				if(e==null) {


					for (ParseObject delete : parseObjects) {
						delete.deleteInBackground();
						Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(getApplicationContext(), "error in deleting", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	/**
	 * Load the conversation list from Parse server and save the date of last
	 * message that will be used to load only recent new messages
	 */
	private void loadConversationList()
	{
		ParseQuery<ParseObject> q = ParseQuery.getQuery("Chat");
		if (convList.size() == 0)
		{
			// load all messages...
			ArrayList<String> al = new ArrayList<String>();
			al.add(buddy);
			al.add(UserList.user.getUsername());
			q.whereContainedIn("sender", al);
			q.whereContainedIn("receiver", al);
		}
		else
		{
			// load only newly received message..
			if (lastMsgDate != null)
				q.whereGreaterThan("createdAt", lastMsgDate);
			q.whereEqualTo("sender", buddy);
			q.whereEqualTo("receiver", UserList.user.getUsername());
		}
		q.orderByDescending("createdAt");
		q.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> li, ParseException e)
			{
				if (li != null && li.size() > 0)
				{
					for (int i = li.size() - 1; i >= 0; i--)
					{



						ParseObject po = li.get(i);
						Conversation c = new Conversation(po
								.getString("message"), po.getCreatedAt(), po
								.getString("sender"));


						convList.add(c);
						if (lastMsgDate == null
								|| lastMsgDate.before(c.getDate()))

							lastMsgDate = c.getDate();
						adp.notifyDataSetChanged();
					}
				}
				handler.postDelayed(new Runnable() {

					@Override
					public void run()
					{
						if (isRunning)
							loadConversationList();
					}
				}, 1000);
			}
		});

	}

	/**
	 * The Class ChatAdapter is the adapter class for Chat ListView. This
	 * adapter shows the Sent or Receieved Chat message in each list item.
	 */
	private class ChatAdapter extends BaseAdapter
	{

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount()
		{
			return convList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Conversation getItem(int arg0)
		{
			return convList.get(arg0);
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int arg0)
		{
			return arg0;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int pos, View v, ViewGroup arg2) {
			Conversation c = getItem(pos);
			if (c.isSent())
				v = getLayoutInflater().inflate(R.layout.chat_item_sent, null);
			else
				v = getLayoutInflater().inflate(R.layout.chat_item_rcv, null);

			TextView lbl = (TextView) v.findViewById(R.id.lbl1);

			lbl.setText(DateUtils.getRelativeDateTimeString(Chat.this, c
							.getDate().getTime(), DateUtils.SECOND_IN_MILLIS,
					DateUtils.DAY_IN_MILLIS, 0));
		//	String mystring =(DateUtils.getRelativeDateTimeString(Chat.this, c
		//					.getDate().getTime();
			lbl = (TextView) v.findViewById(R.id.lbl2);

			lbl.setText(c.getMsg());

			lbl = (TextView) v.findViewById(R.id.lbl3);


			if (c.isSent()) {
				if (c.getStatus() == Conversation.STATUS_SENT)
					lbl.setText("");
				else if (c.getStatus() == Conversation.STATUS_SENDING)
					lbl.setText("Sending...");
				else
					lbl.setText("Failed");
			}
			else
				lbl.setText("");

			return v;
		}

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == android.R.id.home)
		{
			deleteStudent();
			finish();

		}

		return super.onOptionsItemSelected(item);
	}

}
