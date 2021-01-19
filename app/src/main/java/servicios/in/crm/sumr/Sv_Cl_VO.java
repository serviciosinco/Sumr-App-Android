package servicios.in.crm.sumr;

/**
 * Created by serviciosin on 29/01/18.
 */

public class Sv_Cl_VO {

    private String nm;
    private String img;
    private String clr;

    public Sv_Cl_VO(String nm, String clr){

        this.setNm(nm);
        this.setImg(img);
        this.setClr(clr);

    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getClr() {
        return clr;
    }

    public void setClr(String clr) {
        this.clr = clr;
    }

}
