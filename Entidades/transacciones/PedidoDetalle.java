//Soure packages/Entidades.transacciones/PedidoDetalle.java
package Entidades.transacciones;
  
public class PedidoDetalle {
    private int pedDetIde;
    private int pedDetPed;
    private int pedDetProIde;
    private int pedDetCan;
    private double pedDetPreUni;
    private String pedDetEstReg;

    public PedidoDetalle() {}

    public PedidoDetalle(int pedDetIde, int pedDetPed, int pedDetProIde, int pedDetCan,
                          double pedDetPreUni, String pedDetEstReg) {
        this.pedDetIde = pedDetIde;
        this.pedDetPed = pedDetPed;
        this.pedDetProIde = pedDetProIde;
        this.pedDetCan = pedDetCan;
        this.pedDetPreUni = pedDetPreUni;
        this.pedDetEstReg = pedDetEstReg;
    }

    public int getPedDetIde() { return pedDetIde; }
    public void setPedDetIde(int pedDetIde) { this.pedDetIde = pedDetIde; }

    public int getPedDetPed() { return pedDetPed; }
    public void setPedDetPed(int pedDetPed) { this.pedDetPed = pedDetPed; }

    public int getPedDetProIde() { return pedDetProIde; }
    public void setPedDetProIde(int pedDetProIde) { this.pedDetProIde = pedDetProIde; }

    public int getPedDetCan() { return pedDetCan; }
    public void setPedDetCan(int pedDetCan) { this.pedDetCan = pedDetCan; }

    public double getPedDetPreUni() { return pedDetPreUni; }
    public void setPedDetPreUni(double pedDetPreUni) { this.pedDetPreUni = pedDetPreUni; }

    public String getPedDetEstReg() { return pedDetEstReg; }
    public void setPedDetEstReg(String pedDetEstReg) { this.pedDetEstReg = pedDetEstReg; }
}