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
        service.create(new Star("kate bosworth", "https://upload.wikimedia.org/wikipedia/commons/e/e6/Kate_Bosworth_Deauville_2011.jpg", 3.5f));
        service.create(new Star("george clooney", "https://upload.wikimedia.org/wikipedia/commons/8/8d/George_Clooney_2016.jpg", 3));
        service.create(new Star("michelle rodriguez", "https://upload.wikimedia.org/wikipedia/commons/f/fd/Michelle_Rodriguez_Cannes_2018_cropped.jpg", 5));
        service.create(new Star("tom hardy", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Tom_Hardy_by_Gage_Skidmore.jpg/800px-Tom_Hardy_by_Gage_Skidmore.jpg", 1));
        service.create(new Star("louise bouroin", "https://upload.wikimedia.org/wikipedia/commons/5/54/Louise_Bourgoin_Cannes_2010.jpg", 5));
        service.create(new Star("rami malek", "https://upload.wikimedia.org/wikipedia/commons/8/8e/Rami_Malek_in_2015_%282%29_%28cropped%29.jpg", 1));
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