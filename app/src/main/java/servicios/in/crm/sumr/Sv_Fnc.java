package servicios.in.crm.sumr;

/**
 * Created by serviciosin on 22/01/18.
 */

public class Sv_Fnc {

    public String isN(String p){

        if(p.trim() != "" && p.trim() != null){
            return "no";
        }else{
            return "ok";
        }

    }

}
