//Soure packages/Entidades.maestras/Oficina.java
package Entidades.maestras;

public class Oficina {
    private int ofiIde;
    private String ofiCiu;
    private String ofiReg;
    private String ofiDirn;
    private double ofiObjPro;
    private double ofiVenRea;
    private String ofiEstReg;

    public Oficina() {}

    public Oficina(int ofiIde, String ofiCiu, String ofiReg, String ofiDirn,
                   double ofiObjPro, double ofiVenRea, String ofiEstReg) {
        this.ofiIde = ofiIde;
        this.ofiCiu = ofiCiu;
        this.ofiReg = ofiReg;
        this.ofiDirn = ofiDirn;
        this.ofiObjPro = ofiObjPro;
        this.ofiVenRea = ofiVenRea;
        this.ofiEstReg = ofiEstReg;
    }

    // Getters y Setters
    public int getOfiIde() { return ofiIde; }
    public void setOfiIde(int ofiIde) { this.ofiIde = ofiIde; }

    public String getOfiCiu() { return ofiCiu; }
    public void setOfiCiu(String ofiCiu) { this.ofiCiu = ofiCiu; }

    public String getOfiReg() { return ofiReg; }
    public void setOfiReg(String ofiReg) { this.ofiReg = ofiReg; }

    public String getOfiDirn() { return ofiDirn; }
    public void setOfiDirn(String ofiDirn) { this.ofiDirn = ofiDirn; }

    public double getOfiObjPro() { return ofiObjPro; }
    public void setOfiObjPro(double ofiObjPro) { this.ofiObjPro = ofiObjPro; }

    public double getOfiVenRea() { return ofiVenRea; }
    public void setOfiVenRea(double ofiVenRea) { this.ofiVenRea = ofiVenRea; }

    public String getOfiEstReg() { return ofiEstReg; }
    public void setOfiEstReg(String ofiEstReg) { this.ofiEstReg = ofiEstReg; }
}
