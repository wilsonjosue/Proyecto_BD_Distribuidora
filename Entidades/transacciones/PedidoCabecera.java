//Soure packages/Entidades.transacciones/PedidoCabecera.java
package Entidades.transacciones;

public class PedidoCabecera {
    private int pedCabIde;
    private int pedCabAnio;
    private int pedCabMes;
    private int pedCabDia;
    private double pedCabMonTot;
    private int pedCabCliIde;
    private int pedCabRepVenIde;
    private int pedCabTraIde;
    private String pedCabEstReg;

    public PedidoCabecera() {
    }

    public PedidoCabecera(int pedCabIde, int pedCabAnio, int pedCabMes, int pedCabDia,
                          double pedCabMonTot, int pedCabCliIde, int pedCabRepVenIde,
                          int pedCabTraIde, String pedCabEstReg) {
        this.pedCabIde = pedCabIde;
        this.pedCabAnio = pedCabAnio;
        this.pedCabMes = pedCabMes;
        this.pedCabDia = pedCabDia;
        this.pedCabMonTot = pedCabMonTot;
        this.pedCabCliIde = pedCabCliIde;
        this.pedCabRepVenIde = pedCabRepVenIde;
        this.pedCabTraIde = pedCabTraIde;
        this.pedCabEstReg = pedCabEstReg;
    }

    public int getPedCabIde() {
        return pedCabIde;
    }

    public void setPedCabIde(int pedCabIde) {
        this.pedCabIde = pedCabIde;
    }

    public int getPedCabAnio() {
        return pedCabAnio;
    }

    public void setPedCabAnio(int pedCabAnio) {
        this.pedCabAnio = pedCabAnio;
    }

    public int getPedCabMes() {
        return pedCabMes;
    }

    public void setPedCabMes(int pedCabMes) {
        this.pedCabMes = pedCabMes;
    }

    public int getPedCabDia() {
        return pedCabDia;
    }

    public void setPedCabDia(int pedCabDia) {
        this.pedCabDia = pedCabDia;
    }

    public double getPedCabMonTot() {
        return pedCabMonTot;
    }

    public void setPedCabMonTot(double pedCabMonTot) {
        this.pedCabMonTot = pedCabMonTot;
    }

    public int getPedCabCliIde() {
        return pedCabCliIde;
    }

    public void setPedCabCliIde(int pedCabCliIde) {
        this.pedCabCliIde = pedCabCliIde;
    }

    public int getPedCabRepVenIde() {
        return pedCabRepVenIde;
    }

    public void setPedCabRepVenIde(int pedCabRepVenIde) {
        this.pedCabRepVenIde = pedCabRepVenIde;
    }

    public int getPedCabTraIde() {
        return pedCabTraIde;
    }

    public void setPedCabTraIde(int pedCabTraIde) {
        this.pedCabTraIde = pedCabTraIde;
    }

    public String getPedCabEstReg() {
        return pedCabEstReg;
    }

    public void setPedCabEstReg(String pedCabEstReg) {
        this.pedCabEstReg = pedCabEstReg;
    }
}
