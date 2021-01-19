package servicios.in.crm.sumr;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * Created by serviciosin on 29/01/18.
 */

public class Sv_Cl_Adptr extends ArrayAdapter{

    public Sv_Cl_Adptr(Activity cntx, Sv_Cl_VO[] dts){
        super(cntx, R.layout.activity_sv__cl_adptr, dts);
    }

    /*public Sv_Cl_Adptr(Activity cntx, Sv_Cl_VO[] dts){
        super(cntx, R.layout.activity_sv__cl_adptr, dts);
    }*/

    List list = new ArrayList();

    public Sv_Cl_Adptr(Context cntx, int resource) {
        super(cntx, resource);
    }

    public void add(Sv_Cl_VO object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public View getView(int pos, View row, ViewGroup prnt) {

        VarDclr varDclr;

        if(row == null){

            LayoutInflater lyut = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = lyut.inflate(R.layout.activity_sv__cl_adptr, prnt, false);
            varDclr = new VarDclr();
            varDclr.cl_nm = (TextView) row.findViewById(R.id.cl_nm);
            row.setTag(varDclr);

        }else{
            varDclr = (VarDclr) row.getTag();
        }

        Sv_Cl_VO dtsVo = (Sv_Cl_VO) this.getItem(pos);
        varDclr.cl_nm.setText(dtsVo.getNm());
        varDclr.cl_nm.setTextColor(Color.parseColor(dtsVo.getClr()));

        return row;

    }

    static class VarDclr{
        TextView cl_nm, cl_img;
        ImageView img;
    }

}
