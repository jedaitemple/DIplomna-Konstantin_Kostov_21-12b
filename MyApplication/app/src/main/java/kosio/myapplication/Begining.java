package kosio.myapplication;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;

import com.parse.ParseUser;

import android.app.Application;
import com.parse.Parse;

/**
 * The Class Beginning is the Main Application class of this app. The onCreate
 * method of this class initializes the Parse.
 */
public class Beginning extends Application
{

	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();

		Parse.initialize(this, "nE2rM3YtEq45TU6ktVBTzekMIFilQzg5c693IueV",
				"v3NTJ6khp6X2q7uqU7fNhFMsWfMiMnuWjuigz6KQ");
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this
		// line.
		defaultACL.setPublicReadAccess(true);

		Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

		ParseUser.enableAutomaticUser();

		// If you would like all objects to be private by default, remove this
		// line.
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);

	}
}
