package kosio.myapplication;

        import java.util.ArrayList;
        import java.util.List;

        import android.app.ListActivity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.Window;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TabHost;

        import com.parse.FindCallback;
        import com.parse.ParseException;
        import com.parse.ParseObject;
        import com.parse.ParseQuery;
        import com.parse.ParseUser;

        import android.widget.Button;


public class MyPosts extends ListActivity {
    Blog blog;

    private List<Blog> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            loadLoginView();
        }
        refreshPostList();



        posts = new ArrayList<Blog>();

        ArrayAdapter<Blog> adapter = new ArrayAdapter<Blog>(this,
                R.layout.list_item_layout, posts);

        setListAdapter(adapter);


    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Blog blog = posts.get(position);

        Intent intent = new Intent(this, SeeNote.class);
        intent.putExtra("noteId", blog.getId());
        intent.putExtra("noteTitle", blog.getTitle());
        intent.putExtra("noteContent", blog.getContent());
        startActivity(intent);

    }




    private void refreshPostList() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("author", ParseUser.getCurrentUser());

        setProgressBarIndeterminateVisibility(true);

        query.findInBackground(new FindCallback<ParseObject>() {


            public void done(List<ParseObject> postList, ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                if (e == null) {
                    // If there are results, update the list of posts
                    // and notify the adapter
                    posts.clear();
                    for (ParseObject post : postList) {
                        Blog blog = new Blog(post.getObjectId(), post
                                .getString("title"), post.getString("content"));
                        posts.add(blog);

                    }
                    ((ArrayAdapter<Blog>) getListAdapter())
                            .notifyDataSetChanged();
                } else {

                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }

            }

        });

    }
}
