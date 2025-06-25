//Soure packages/Entidades/Almacen.java
package Entidades.referenciales;

public class Almacen {
    private int almIde;
    private String almNom;
    private String almDir;
    private int almCap;
    private String almEstReg;

    public Almacen() {}

    public Almacen(int almIde, String almNom, String almDir, int almCap, String almEstReg) {
        this.almIde = almIde;
        this.almNom = almNom;
        this.almDir = almDir;
        this.almCap = almCap;
        this.almEstReg = almEstReg;
    }

    public int getAlmIde() { return almIde; }
    public void setAlmIde(int almIde) { this.almIde = almIde; }
    public String getAlmNom() { return almNom; }
    public void setAlmNom(String almNom) { this.almNom = almNom; }
    public String getAlmDir() { return almDir; }
    public void setAlmDir(String almDir) { this.almDir = almDir; }
    public int getAlmCap() { return almCap; }
    public void setAlmCap(int almCap) { this.almCap = almCap; }
    public String getAlmEstReg() { return almEstReg; }
    public void setAlmEstReg(String almEstReg) { this.almEstReg = almEstReg; }
}
