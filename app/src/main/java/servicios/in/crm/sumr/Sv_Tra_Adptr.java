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

public class Sv_Tra_Adptr extends ArrayAdapter{

    public Sv_Tra_Adptr(Activity cntx, Sv_Tra_VO[] dts){
        super(cntx, R.layout.activity_sv__tra_adptr, dts);
    }

    String tra_tt, tra_fch;
    List list = new ArrayList();

    public Sv_Tra_Adptr(Context cntx, int resource) {
        super(cntx, resource);
    }

    public void add(Sv_Tra_VO object) {
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
            row = lyut.inflate(R.layout.activity_sv__tra_adptr, prnt, false);
            varDclr = new VarDclr();
            varDclr.tra_tt = (TextView) row.findViewById(R.id.tra_tt);
            varDclr.tra_fch = (TextView) row.findViewById(R.id.tra_fch);
            row.setTag(varDclr);

        }else{
            varDclr = (VarDclr) row.getTag();
        }

        Sv_Tra_VO dtsVo = (Sv_Tra_VO) this.getItem(pos);

        if(!dtsVo.getTt().equals("null")){
            tra_tt = dtsVo.getTt();
        }else{
            tra_tt = "- NA -";
        }

        if(!dtsVo.getFch().equals("null")){
            tra_fch = dtsVo.getFch();
        }else{
            tra_fch = "- NA -";
        }

        varDclr.tra_tt.setText(tra_tt);
        varDclr.tra_fch.setText(tra_fch);

        return row;

    }

    static class VarDclr{
        TextView tra_tt, tra_fch;
    }

}
