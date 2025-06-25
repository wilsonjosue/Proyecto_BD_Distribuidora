//Soure packages/Entidades/TipoTransaccionInventario.java
package Entidades.referenciales;

public class TipoTransaccionInventario {
    private int tipTraIde;
    private String tipTraDes;
    private String tipTraEstReg;

    public TipoTransaccionInventario() {}

    public TipoTransaccionInventario(int tipTraIde, String tipTraDes, String tipTraEstReg) {
        this.tipTraIde = tipTraIde;
        this.tipTraDes = tipTraDes;
        this.tipTraEstReg = tipTraEstReg;
    }

    public int getTipTraIde() {
        return tipTraIde;
    }

    public void setTipTraIde(int tipTraIde) {
        this.tipTraIde = tipTraIde;
    }

    public String getTipTraDes() {
        return tipTraDes;
    }

    public void setTipTraDes(String tipTraDes) {
        this.tipTraDes = tipTraDes;
    }

    public String getTipTraEstReg() {
        return tipTraEstReg;
    }

    public void setTipTraEstReg(String tipTraEstReg) {
        this.tipTraEstReg = tipTraEstReg;
    }
}
