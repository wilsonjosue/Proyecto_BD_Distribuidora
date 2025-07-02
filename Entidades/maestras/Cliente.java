//Soure packages/Entidades.maestras/Cliente.java
package Entidades.maestras;

public class Cliente {
    private int cliIde;
    private String cliEmp;
    private String cliNom;
    private String cliApePat;
    private String cliApeMat;
    private String cliDir;
    private String cliTel;
    private String cliCor;
    private String cliDep;
    private int cliCatIde;
    private String cliEstReg;

    public Cliente() {}

    public Cliente(int cliIde, String cliEmp, String cliNom, String cliApePat, String cliApeMat,
                   String cliDir, String cliTel, String cliCor, String cliDep, int cliCatIde, String cliEstReg) {
        this.cliIde = cliIde;
        this.cliEmp = cliEmp;
        this.cliNom = cliNom;
        this.cliApePat = cliApePat;
        this.cliApeMat = cliApeMat;
        this.cliDir = cliDir;
        this.cliTel = cliTel;
        this.cliCor = cliCor;
        this.cliDep = cliDep;
        this.cliCatIde = cliCatIde;
        this.cliEstReg = cliEstReg;
    }

    // Getters y Setters
    public int getCliIde() { return cliIde; }
    public void setCliIde(int cliIde) { this.cliIde = cliIde; }

    public String getCliEmp() { return cliEmp; }
    public void setCliEmp(String cliEmp) { this.cliEmp = cliEmp; }

    public String getCliNom() { return cliNom; }
    public void setCliNom(String cliNom) { this.cliNom = cliNom; }

    public String getCliApePat() { return cliApePat; }
    public void setCliApePat(String cliApePat) { this.cliApePat = cliApePat; }

    public String getCliApeMat() { return cliApeMat; }
    public void setCliApeMat(String cliApeMat) { this.cliApeMat = cliApeMat; }

    public String getCliDir() { return cliDir; }
    public void setCliDir(String cliDir) { this.cliDir = cliDir; }

    public String getCliTel() { return cliTel; }
    public void setCliTel(String cliTel) { this.cliTel = cliTel; }

    public String getCliCor() { return cliCor; }
    public void setCliCor(String cliCor) { this.cliCor = cliCor; }

    public String getCliDep() { return cliDep; }
    public void setCliDep(String cliDep) { this.cliDep = cliDep; }

    public int getCliCatIde() { return cliCatIde; }
    public void setCliCatIde(int cliCatIde) { this.cliCatIde = cliCatIde; }

    public String getCliEstReg() { return cliEstReg; }
    public void setCliEstReg(String cliEstReg) { this.cliEstReg = cliEstReg; }
}
