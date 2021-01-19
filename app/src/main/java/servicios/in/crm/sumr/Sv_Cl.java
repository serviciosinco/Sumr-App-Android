package servicios.in.crm.sumr;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;

public class Sv_Cl extends AppCompatActivity{

    ListView LsCl;
    Sv_Sess sess;

    JSONObject dts, cl_clr_ls;
    JSONArray dts_array;
    Sv_Cl_Adptr cl_adptr;

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sv__cl);
        getSupportActionBar().hide();

        sess = new Sv_Sess(getApplicationContext());

        LsCl = (ListView) findViewById(R.id.LsCl);

        cl_adptr = new Sv_Cl_Adptr(this, R.layout.activity_sv__cl_adptr);
        LsCl.setAdapter(cl_adptr);

        try{

            dts = new JSONObject(sess.getSess("Sess_Cl_Dt").toString());
            dts_array = dts.getJSONArray("ls");

            int count = 0;
            String nm, cl_clr;
            while (count < dts_array.length()){

                JSONObject cl_dts = dts_array.getJSONObject(count);
                nm = cl_dts.getString("nm");

                if(!cl_dts.get("clr").toString().equals("null") && !cl_dts.get("clr").toString().equals(null)){

                    cl_clr_ls = cl_dts.getJSONObject("clr");

                    //Valida y selecciona el color del cliente
                    if(cl_clr_ls.has("menu-app")){
                        cl_clr = cl_clr_ls.getString("menu-app").toString();
                    }else if(cl_clr_ls.has("main")){
                        cl_clr = cl_clr_ls.getString("main").toString();
                    }else{
                        cl_clr = "#000000";
                    }

                }else{
                    cl_clr = "#000000";
                }

                Sv_Cl_VO cl_vo = new Sv_Cl_VO(nm, cl_clr);
                cl_adptr.add(cl_vo);
                count++;

            }

            //Click en el item seleccionado
            LsCl.setOnItemClickListener(new OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> a, View v,int slct, long id){
                    try {
                        Toast.makeText(getBaseContext(), "Click "+dts_array.getJSONObject(slct).getString("nm").toString() , Toast.LENGTH_SHORT).show();

                        sess.setSess("Sess_Cl_Slc_Dt", dts_array.getJSONObject(slct).toString());
                        Intent sv_Cl = new Intent(Sv_Cl.this, Sv_Mnu.class);
                        startActivity(sv_Cl);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
             });

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

}
