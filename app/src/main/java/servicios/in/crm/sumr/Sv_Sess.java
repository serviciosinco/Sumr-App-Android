package servicios.in.crm.sumr;

import android.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.content.Context;

import java.util.Hashtable;

/**
 * Created by serviciosin on 25/01/18.
 */

public class Sv_Sess {

    private SharedPreferences prfs;

    public Sv_Sess(Context v) {
        //Instancia la clase PreferenceManager que realiza las sesiones
        prfs = PreferenceManager.getDefaultSharedPreferences(v);
    }

    //Crear la session
    public void setSess(String k, String v) {
        prfs.edit().putString(k, v).commit();
    }

    //Crear la session con objetos o arreglos
    public void setSessObject(String k, Object v) {
        prfs.edit().putString(k, v.toString()).commit();
    }

    //Mostrar la session
    public String getSess(String v) {
        String v_get = prfs.getString(v,"");
        return v_get;
    }

    //Eliminar la session
    public void eliSess(String k) {
        prfs.edit().putString(k, null).commit();
        prfs.edit().remove(k);
    }

}
