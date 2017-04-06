package upgradekaro.a3dmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    GridView recyclermovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        setRecAdapter();
    }

    private void initComponents() {
        recyclermovies= (GridView) findViewById(R.id.Main_grid);
    }
    private void setRecAdapter(){
       // MoviecardsAdapter adpter=new MoviecardsAdapter(MainActivity.this);
        MaincardsGridAdapter adpter=new MaincardsGridAdapter(MainActivity.this);
       // recyclermovies.setLayoutManager(new LinearLayoutManager(this));
        recyclermovies.setAdapter(adpter);
    }
}
