package movies.a3dmovies;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvanceSearch extends Fragment implements View.OnClickListener {
    Button btnsoryby,btnrating,btnquality,btngenre,btnsearch;
    EditText edsearchtext;
    Dialog dialogshow;
    Button dialogok;
    RadioGroup radioGroupgeners,radioGroupall;
    String genreselected="",sortbyselected="",qualityselected="",ratingselected="";
    String[] options;
    String baseUrl="https://yts.ag/api/v2/list_movies.json?";
    public AdvanceSearch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_advance_search, container, false);
        initComponents(v);
         clickMethods();
        return v;
    }

    private void initComponents(View v) {
        edsearchtext= (EditText) v.findViewById(R.id.advancesearch_ed_entername);
        btnsoryby= (Button) v.findViewById(R.id.advancesearch_btn_shortby);
        btngenre= (Button) v.findViewById(R.id.advancesearch_btn_genres);
        btnquality= (Button) v.findViewById(R.id.advancesearch_btn_quality);
        btnrating= (Button) v.findViewById(R.id.advancesearch_btn_rating);
        btnsearch= (Button) v.findViewById(R.id.advancesearch_btn_search);
    }

    private void createGenersDialog(int id) {
        options= getResources().getStringArray(id);
        dialogshow = new Dialog(getContext());
        dialogshow.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogshow.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogshow.setContentView(R.layout.shortdialog);
        radioGroupall= (RadioGroup) dialogshow.findViewById(R.id.radiogroup_items);
        for (int i = 0; i < options.length; i++) {
            RadioButton radios = new RadioButton(getContext());
            radios.setId(i);
            radios.setText(options[i]);
            radioGroupall.addView(radios);
        }
        dialogok = (Button) dialogshow.findViewById(R.id.btn_dialogok);
        switch (id){
            case R.array.sortyby_array:
                dialogok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogshow.dismiss();
                        Log.d("checkselection",""+radioGroupgeners.getCheckedRadioButtonId());
                    }
                });
                Log.d("showing dialog","sortyby");
                break;
            case R.array.qualtiy_array:
                Log.d("showing dialog","quality");
                break;
            case R.array.int_array_rating:
                Log.d("showing dialog","rating");
                break;
        }
        dialogshow.show();
    }
    private void createGenersDialogGeners(int id) {
        options= getResources().getStringArray(id);
        dialogshow = new Dialog(getContext());
        dialogshow.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogshow.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogshow.setContentView(R.layout.dialoggeners);
        radioGroupgeners= (RadioGroup) dialogshow.findViewById(R.id.radiogroup_genre);
        for (int i = 0; i <options.length; i++) {
            RadioButton radios = new RadioButton(getContext());
            radios.setId(i);
            radios.setText(options[i]);
            radioGroupgeners.addView(radios);
        }
        dialogok = (Button) dialogshow.findViewById(R.id.btn_dialogok_genre);
                Log.d("showing dialog","genersarray");
        dialogshow.show();
    }

    private void clickMethods(){
        btnrating.setOnClickListener(this);
        btnsoryby.setOnClickListener(this);
        btnsearch.setOnClickListener(this);
        btnquality.setOnClickListener(this);
        btngenre.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v==btngenre){
            createGenersDialogGeners(R.array.genersarray);
        }
        if(v==btnquality){
           createGenersDialog(R.array.qualtiy_array);
        }
        if(v==btnsearch){

        }
        if(v==btnsoryby){
            createGenersDialog(R.array.sortyby_array);
        }
        if(v==btnrating){
            createGenersDialog(R.array.int_array_rating);
        }
    }

    private void stringConcatebaseUrl(String rating,String quality,String sortby,String genre){
        String urlapi=baseUrl.trim()+"minimum_rating="+rating+"&quality="+quality+"&sort_by="+sortby+"&genre="+genre;
    }
}
