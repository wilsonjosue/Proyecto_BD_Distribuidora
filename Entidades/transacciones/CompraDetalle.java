//Soure packages/Entidades.transacciones/CompraDetalle.java
package Entidades.transacciones;

public class CompraDetalle {
 
    private int comDetIde;
    private int comDetComIde;
    private int comDetProIde;
    private int comDetCant;
    private double comDetPreUni;
    private String comDetEstReg;

    public CompraDetalle() {}

    public CompraDetalle(int comDetIde, int comDetComIde, int comDetProIde,
                     int comDetCant, double comDetPreUni, String comDetEstReg) {
        this.comDetIde = comDetIde;
        this.comDetComIde = comDetComIde;
        this.comDetProIde = comDetProIde;
        this.comDetCant = comDetCant;
        this.comDetPreUni = comDetPreUni;
        this.comDetEstReg = comDetEstReg;
    }

    public int getComDetIde() { return comDetIde; }
    public void setComDetIde(int comDetIde) { this.comDetIde = comDetIde; }

    public int getComDetComIde() { return comDetComIde; }
    public void setComDetComIde(int comDetComIde) { this.comDetComIde = comDetComIde; }

    public int getComDetProIde() { return comDetProIde; }
    public void setComDetProIde(int comDetProIde) { this.comDetProIde = comDetProIde; }

    public int getComDetCant() { return comDetCant; }
    public void setComDetCant(int comDetCant) { this.comDetCant = comDetCant; }

    public double getComDetPreUni() { return comDetPreUni; }
    public void setComDetPreUni(double comDetPreUni) { this.comDetPreUni = comDetPreUni; }

    public String getComDetEstReg() { return comDetEstReg; }
    public void setComDetEstReg(String comDetEstReg) { this.comDetEstReg = comDetEstReg; }
}
