package kosio.myapplication;

/**
 * Created by Kosioplay on 2.3.2016 г..
 */
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentOne extends Fragment {

    public View onCreateView(LayoutInflater inflater,

                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(

                R.layout.new_article, container, false);


    }
}

