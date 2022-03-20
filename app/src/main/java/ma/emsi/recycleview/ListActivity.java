package ma.emsi.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import ma.emsi.recycleview.adapter.StarAdapter;
import ma.emsi.recycleview.beans.Star;
import ma.emsi.recycleview.service.StarService;

public class ListActivity extends AppCompatActivity {
    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private StarService service;
    private static final String TAG = "StarAdapter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        stars = new ArrayList<>();
        service = StarService.getInstance();
        init();
        recyclerView = findViewById(R.id.recycle_view);

        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void init() {
        service.create(new Star("Jason Statham", "https://upload.wikimedia.org/wikipedia/commons/d/d3/Jason_Statham_2018.jpg", 5, "Male"));
        service.create(new Star("maisie williams", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Maisie_Williams_by_Gage_Skidmore_3.jpg/330px-Maisie_Williams_by_Gage_Skidmore_3.jpg", 3, "Female"));
        service.create(new Star("emilia clarke", "https://upload.wikimedia.org/wikipedia/commons/d/d5/Emilia_Clarke_by_Gage_Skidmore_2_%28cropped%29.jpg", 4, "Female"));
        service.create(new Star("tom holland", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/800px-Tom_Holland_by_Gage_Skidmore.jpg", 5, "Male"));
        service.create(new Star("dwayne johnson", "https://upload.wikimedia.org/wikipedia/commons/1/1f/Dwayne_Johnson_2014_%28cropped%29.jpg", 4, "Male"));
        service.create(new Star("kit harington", "https://upload.wikimedia.org/wikipedia/commons/3/32/Kit_harrington_by_sachyn_mital_%28cropped_2%29.jpg", 4, "Male"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null){
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Stars")
                    .setText(txt)
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }


}