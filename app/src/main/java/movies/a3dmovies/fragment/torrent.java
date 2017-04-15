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

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;

import movies.a3dmovies.R;
import movies.a3dmovies.Utils;

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



    private void downloadClient(){
        try {
            utils.createDestination();
            Client client=new Client(
                    InetAddress.getLocalHost(),
                    SharedTorrent.fromFile(new File(filelocation),new File(filetostorelocation.toString().trim()+"")));
            client.setMaxDownloadRate(50.0);
            client.setMaxUploadRate(50.0);
            client.download();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    private void trackDownload(){
        Tracker tracker;
        try {
            tracker=new Tracker(new InetSocketAddress(6969));
            FilenameFilter filter=new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".torrent");
                }
            };
            for (File f:new File("/path/to/torrents/files").listFiles(filter)){
                tracker.announce(TrackedTorrent.load(f));
            }
            tracker.start();
            tracker.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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
                    Log.d("check locations",filetostorelocation+"-file storage location "+filelocation+"file location");
                  //  downloadClient();

                    // filelocation=path;


                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
