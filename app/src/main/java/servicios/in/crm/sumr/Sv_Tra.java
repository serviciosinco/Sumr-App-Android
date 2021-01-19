package servicios.in.crm.sumr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import org.json.JSONObject;

import java.util.Iterator;

public class Sv_Tra extends Fragment implements CllInfc{

    Sv_Sess sess;

    static String data = null;
    String cl_enc;
    int tra_tot;

    ListView LsTra;
    TextView TraTot;
    View view;
    Sv_Tra_Adptr tra_adptr;

    JSONObject dts, cl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sess = new Sv_Sess(getActivity());

        try {
            cl = new JSONObject(sess.getSess("Sess_Cl_Slc_Dt").toString());
            cl_enc = cl.getString("enc").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {

            //Datos a enviarse por POST
            Uri.Builder dts = new Uri.Builder()
                    .appendQueryParameter("_p1", "tra")
                    .appendQueryParameter("tp", "_tra_ls")
                    .appendQueryParameter("cl", cl_enc);
            String dtsPst = dts.build().getEncodedQuery();

            if(Sv_Tra.data == null) {
                new Sv_Http((CllInfc) this).execute("&"+dtsPst);
            }
            else {
                System.out.println("Error");
            }

        }catch (Exception e){
            System.out.println("Error en la petición "+ e.getMessage());
        }

        view = inflater.inflate(R.layout.fragment_sv__tra, container, false);

        LsTra = (ListView) view.findViewById(R.id.LsTra);
        TraTot = (TextView) view.findViewById(R.id.TraTot);
        //LsTra.setBackgroundColor(Color.parseColor("#FF0000"));

        tra_adptr = new Sv_Tra_Adptr(getActivity(), R.layout.activity_sv__tra_adptr);
        LsTra.setAdapter(tra_adptr);

        /* ************************************* */


        /* ************************************* */


        return view;

    }

    //Funcion callback que devuelve los datos
    @Override
    public void fetchDataCallback(String dtsJson) {

        try{

            dts = new JSONObject(dtsJson);
            dts = dts.getJSONObject("tra");

            //Recorrer el Key y traer el value de un JSON
            Iterator<String> iter = dts.keys();
            while (iter.hasNext()) {
                try {

                    String k = iter.next();
                    String v = dts.get(k).toString();

                    JSONObject dts_iter = new JSONObject(v);

                    if(!k.toString().equals("tot") && !k.toString().equals("tot")){
                        Sv_Tra_VO tra_vo = new Sv_Tra_VO(dts_iter.getString("tt"), dts_iter.getString("fch"));
                        tra_adptr.add(tra_vo);
                        tra_tot ++;
                    }

                } catch (JSONException e) {
                    System.out.println("Error en iterator: "+e.getMessage());
                }
            }

            TraTot.setText(" "+tra_tot+" ");

            //Click en el item seleccionado
            LsTra.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> a, View v,int slct, long id){
                    //try {
                        Object listItem = LsTra.getItemAtPosition(slct);
                        Toast.makeText(getActivity(), "Click en "+dts.toString() , Toast.LENGTH_SHORT).show();
                    /*} catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                }
            });

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

}
