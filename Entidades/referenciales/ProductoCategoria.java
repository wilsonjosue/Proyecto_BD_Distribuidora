//Soure packages/Entidades/ProductoCategoria.java
package Entidades.referenciales;

public class ProductoCategoria {
    private int proCatIde;
    private String proCatNom;
    private String proCatDes;
    private String proCatEstReg;

    public ProductoCategoria() {}

    public ProductoCategoria(int proCatIde, String proCatNom, String proCatDes, String proCatEstReg) {
        this.proCatIde = proCatIde;
        this.proCatNom = proCatNom;
        this.proCatDes = proCatDes;
        this.proCatEstReg = proCatEstReg;
    }

    public int getProCatIde() {
        return proCatIde;
    }

    public void setProCatIde(int proCatIde) {
        this.proCatIde = proCatIde;
    }

    public String getProCatNom() {
        return proCatNom;
    }

    public void setProCatNom(String proCatNom) {
        this.proCatNom = proCatNom;
    }

    public String getProCatDes() {
        return proCatDes;
    }

    public void setProCatDes(String proCatDes) {
        this.proCatDes = proCatDes;
    }

    public String getProCatEstReg() {
        return proCatEstReg;
    }

    public void setProCatEstReg(String proCatEstReg) {
        this.proCatEstReg = proCatEstReg;
    }
}
