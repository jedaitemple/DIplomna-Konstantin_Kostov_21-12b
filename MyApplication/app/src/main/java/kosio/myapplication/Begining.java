package kosio.myapplication;

/**
 * Created by Kosioplay on 3.2.2016 г..
 */

        import android.app.Application;
        import com.parse.Parse;
        import com.parse.ParseACL;

        import com.parse.ParseUser;

/**
 * The main class connection to Parse
 */
public class Begining extends Application
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


        defaultACL.setPublicReadAccess(true);

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);



    }
}
