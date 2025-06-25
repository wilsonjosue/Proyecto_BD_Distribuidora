//Soure packages/Entidades/FormaPago.java
package Entidades.referenciales;

public class FormaPago {
    private int forPagIde;
    private String forPagDes;
    private String forPagEstReg; // A, I, *

    public FormaPago() {}

    public FormaPago(int forPagIde, String forPagDes, String forPagEstReg) {
        this.forPagIde = forPagIde;
        this.forPagDes = forPagDes;
        this.forPagEstReg = forPagEstReg;
    }

    public int getForPagIde() {
        return forPagIde;
    }

    public void setForPagIde(int forPagIde) {
        this.forPagIde = forPagIde;
    }

    public String getForPagDes() {
        return forPagDes;
    }

    public void setForPagDes(String forPagDes) {
        this.forPagDes = forPagDes;
    }

    public String getForPagEstReg() {
        return forPagEstReg;
    }

    public void setForPagEstReg(String forPagEstReg) {
        this.forPagEstReg = forPagEstReg;
    }
}
