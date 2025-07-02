//Soure packages/Entidades.maestras/Producto.java
package Entidades.maestras;

public class Producto {
    private int proIde;
    private String proNom;
    private String proDes;
    private double proPreUni;
    private int proSto;
    private String proUniMed;
    private String proClaABC;
    private int proStoMin;
    private int proStoMax;
    private int proCatIde;
    private String proEstReg;

    public Producto() {}

    public Producto(int proIde, String proNom, String proDes, double proPreUni, int proSto,
                    String proUniMed, String proClaABC, int proStoMin, int proStoMax, int proCatIde, String proEstReg) {
        this.proIde = proIde;
        this.proNom = proNom;
        this.proDes = proDes;
        this.proPreUni = proPreUni;
        this.proSto = proSto;
        this.proUniMed = proUniMed;
        this.proClaABC = proClaABC;
        this.proStoMin = proStoMin;
        this.proStoMax = proStoMax;
        this.proCatIde = proCatIde;
        this.proEstReg = proEstReg;
    }

    // Getters y Setters
    public int getProIde() { return proIde; }
    public void setProIde(int proIde) { this.proIde = proIde; }

    public String getProNom() { return proNom; }
    public void setProNom(String proNom) { this.proNom = proNom; }

    public String getProDes() { return proDes; }
    public void setProDes(String proDes) { this.proDes = proDes; }

    public double getProPreUni() { return proPreUni; }
    public void setProPreUni(double proPreUni) { this.proPreUni = proPreUni; }

    public int getProSto() { return proSto; }
    public void setProSto(int proSto) { this.proSto = proSto; }

    public String getProUniMed() { return proUniMed; }
    public void setProUniMed(String proUniMed) { this.proUniMed = proUniMed; }

    public String getProClaABC() { return proClaABC; }
    public void setProClaABC(String proClaABC) { this.proClaABC = proClaABC; }

    public int getProStoMin() { return proStoMin; }
    public void setProStoMin(int proStoMin) { this.proStoMin = proStoMin; }

    public int getProStoMax() { return proStoMax; }
    public void setProStoMax(int proStoMax) { this.proStoMax = proStoMax; }

    public int getProCatIde() { return proCatIde; }
    public void setProCatIde(int proCatIde) { this.proCatIde = proCatIde; }

    public String getProEstReg() { return proEstReg; }
    public void setProEstReg(String proEstReg) { this.proEstReg = proEstReg; }
}
