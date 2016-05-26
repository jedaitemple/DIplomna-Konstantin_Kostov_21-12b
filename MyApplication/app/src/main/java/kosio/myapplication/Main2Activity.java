package kosio.myapplication;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseUser;

public class Main2Activity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        Intent intent;
        Fragment fragment;
        switch (number) {

            case 1:
                mTitle = getString(R.string.title_section1);
                fragment = new FragmentOne();
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                intent = new Intent(this, Myprofile.class);
                startActivity(intent);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                 intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                intent = new Intent(this, MyPosts.class);
                startActivity(intent);

                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                intent = new Intent(this, SpecialChat.class);
                startActivity(intent);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                intent = new Intent(this, Statistics.class);
                startActivity(intent);
                break;
            case 7:
                mTitle = getString(R.string.title_section7);
                intent = new Intent(this, CreateBlog.class);
                startActivity(intent);
                break;
            case 8:
                mTitle = getString(R.string.title_section8);
                break;
            case 9:
                mTitle = getString(R.string.title_section9);
                intent = new Intent(this, Users.class);
                startActivity(intent);
                break;
            case 10:
                mTitle = getString(R.string.title_section10);
                intent = new Intent(this, group.class);
                startActivity(intent);
                break;
            case 11:
                mTitle = getString(R.string.title_section11);
                intent = new Intent(this, profile.class);
                startActivity(intent);
                break;
            case 12:
                mTitle = getString(R.string.title_section12);
                intent = new Intent(this, Login.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main22, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Main2Activity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
