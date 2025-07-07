//Soure packages/Entidades.transacciones/Factura.java
package Entidades.transacciones;

public class Factura {
    private int facIde, facAnio, facMes, facDia;
    private double facMonImp, facMonTot;
    private String facTipImp, facEstReg;

    public Factura(int facIde, int facAnio, int facMes, int facDia,
                   double facMonImp, double facMonTot, String facTipImp, String facEstReg) {
        this.facIde = facIde;
        this.facAnio = facAnio;
        this.facMes = facMes;
        this.facDia = facDia;
        this.facMonImp = facMonImp;
        this.facMonTot = facMonTot;
        this.facTipImp = facTipImp;
        this.facEstReg = facEstReg;
    }

    // Getters y setters
    public int getFacIde() { return facIde; }
    public int getFacAnio() { return facAnio; }
    public int getFacMes() { return facMes; }
    public int getFacDia() { return facDia; }
    public double getFacMonImp() { return facMonImp; }
    public double getFacMonTot() { return facMonTot; }
    public String getFacTipImp() { return facTipImp; }
    public String getFacEstReg() { return facEstReg; }

    public void setFacEstReg(String facEstReg) { this.facEstReg = facEstReg; }
}

