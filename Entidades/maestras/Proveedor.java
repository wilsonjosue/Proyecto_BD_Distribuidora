//Soure packages/Entidades.maestras/Proveedor.java
package Entidades.maestras;


public class Proveedor {
    
    private int prvIde;
    private String prvNom;
    private String prvDir;
    private String prvTel;
    private String prvCor;
    private String prvEstReg;

    public Proveedor() {
    }

    public Proveedor(int prvIde, String prvNom, String prvDir, String prvTel, String prvCor, String prvEstReg) {
        this.prvIde = prvIde;
        this.prvNom = prvNom;
        this.prvDir = prvDir;
        this.prvTel = prvTel;
        this.prvCor = prvCor;
        this.prvEstReg = prvEstReg;
    }

    public int getPrvIde() {
        return prvIde;
    }

    public void setPrvIde(int prvIde) {
        this.prvIde = prvIde;
    }

    public String getPrvNom() {
        return prvNom;
    }

    public void setPrvNom(String prvNom) {
        this.prvNom = prvNom;
    }

    public String getPrvDir() {
        return prvDir;
    }

    public void setPrvDir(String prvDir) {
        this.prvDir = prvDir;
    }

    public String getPrvTel() {
        return prvTel;
    }

    public void setPrvTel(String prvTel) {
        this.prvTel = prvTel;
    }

    public String getPrvCor() {
        return prvCor;
    }

    public void setPrvCor(String prvCor) {
        this.prvCor = prvCor;
    }

    public String getPrvEstReg() {
        return prvEstReg;
    }

    public void setPrvEstReg(String prvEstReg) {
        this.prvEstReg = prvEstReg;
    }
}
