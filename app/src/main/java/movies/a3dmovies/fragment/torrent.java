package movies.a3dmovies.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import movies.a3dmovies.R;
import movies.a3dmovies.utils.Utils;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class torrent extends Fragment implements View.OnClickListener {

    private String filelocation;
    private Uri filetostorelocation;
    TextView tvfilepath;
    Button btnpickfile;
    Utils utils;
    // file choose methods
    private  static final int File_select_code=0;
    public torrent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_torrent, container, false);
        initComponents(v);
        clicksOnViews();
        return v;
    }


    private void initComponents(View v){
        filetostorelocation=Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/moviesupport"));
        btnpickfile= (Button) v.findViewById(R.id.btn_pickuptorrent);
        tvfilepath= (TextView) v.findViewById(R.id.tv_filelocationpath);
        utils=new Utils();
    }
    private void clicksOnViews() {
        btnpickfile.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v==btnpickfile){
        showFileChooser();
        }
    }

    private void showFileChooser(){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    File_select_code);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case File_select_code:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("file  uri", "File Uri: "+uri.toString());
                    String path=Utils.getPath(getContext(),uri);
                    Log.d("getfilepath",""+path);
                    tvfilepath.setText(""+path);
                    filelocation=path;
                 /*   try {
                       torrentsDownload(path);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    Log.d("check locations",filetostorelocation+" storage location "+filelocation+"file location");
                    // filelocation=path;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*public static void torrentsDownload(String args) throws InterruptedException {

        // comment this line for a real application
       // args = new String[]{"/Users/aldenml/Downloads/Honey_Larochelle_Hijack_FrostClick_FrostWire_MP3_May_06_2016.torrent"};

        File torrentFile = new File(args);

        System.out.println("Using libtorrent version: " + LibTorrent.version());

        final SessionManager s = new SessionManager();

        final CountDownLatch signal = new CountDownLatch(1);

        s.addListener(new AlertListener() {
            @Override
            public int[] types() {
                return null;
            }

            @Override
            public void alert(Alert<?> alert) {
                AlertType type = alert.type();

                switch (type) {
                    case ADD_TORRENT:
                        System.out.println("Torrent added");
                        ((AddTorrentAlert) alert).handle().resume();
                        break;
                    case BLOCK_FINISHED:
                        BlockFinishedAlert a = (BlockFinishedAlert) alert;
                        int p = (int) (a.handle().status().progress() * 100);
                        System.out.println("Progress: " + p + " for torrent name: " + a.torrentName());
                        System.out.println(s.stats().totalDownload());
                        break;
                    case TORRENT_FINISHED:
                        System.out.println("Torrent finished");
                        signal.countDown();
                        break;
                }
            }
        });

        s.start();

        TorrentInfo ti = new TorrentInfo(torrentFile);
        s.download(ti, torrentFile.getParentFile());

        signal.await();

        s.stop();
    }*/
}
