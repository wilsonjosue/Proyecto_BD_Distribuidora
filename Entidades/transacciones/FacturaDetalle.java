//Soure packages/Entidades.transacciones/FacturaDetalle.java
package Entidades.transacciones;

public class FacturaDetalle {
    private int facDetIde;
    private int facDetFacIde;
    private int facDetProIde;
    private int facDetCan;
    private double facDetPreUni;
    private String facDetEstReg;

    public FacturaDetalle(int facDetIde, int facDetFacIde, int facDetProIde, int facDetCan, double facDetPreUni, String facDetEstReg) {
        this.facDetIde = facDetIde;
        this.facDetFacIde = facDetFacIde;
        this.facDetProIde = facDetProIde;
        this.facDetCan = facDetCan;
        this.facDetPreUni = facDetPreUni;
        this.facDetEstReg = facDetEstReg;
    }

    public int getFacDetIde() { return facDetIde; }
    public int getFacDetFacIde() { return facDetFacIde; }
    public int getFacDetProIde() { return facDetProIde; }
    public int getFacDetCan() { return facDetCan; }
    public double getFacDetPreUni() { return facDetPreUni; }
    public String getFacDetEstReg() { return facDetEstReg; }

    public void setFacDetIde(int facDetIde) { this.facDetIde = facDetIde; }
    public void setFacDetFacIde(int facDetFacIde) { this.facDetFacIde = facDetFacIde; }
    public void setFacDetProIde(int facDetProIde) { this.facDetProIde = facDetProIde; }
    public void setFacDetCan(int facDetCan) { this.facDetCan = facDetCan; }
    public void setFacDetPreUni(double facDetPreUni) { this.facDetPreUni = facDetPreUni; }
    public void setFacDetEstReg(String facDetEstReg) { this.facDetEstReg = facDetEstReg; }
}
