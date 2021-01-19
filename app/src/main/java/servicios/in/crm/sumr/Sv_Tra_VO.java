package servicios.in.crm.sumr;

/**
 * Created by serviciosin on 29/01/18.
 */

public class Sv_Tra_VO {

    private String tt;

    private String fch;

    public Sv_Tra_VO(String tt, String fch){

        this.setTt(tt);
        this.setFch(fch);

    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    public String getFch() {
        return fch;
    }

    public void setFch(String fch) {
        this.fch = fch;
    }

}
