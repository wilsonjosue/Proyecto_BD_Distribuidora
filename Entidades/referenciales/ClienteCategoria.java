//Soure packages/Entidades/ClienteCategoria.java
package Entidades.referenciales;

public class ClienteCategoria {
    private int cliCatIde;
    private String cliCatDes;
    private double cliCatLimMin;
    private double cliCatLimMax;
    private String cliCatEstReg;

    public ClienteCategoria() {}

    public ClienteCategoria(int cliCatIde, String cliCatDes, double cliCatLimMin, double cliCatLimMax, String cliCatEstReg) {
        this.cliCatIde = cliCatIde;
        this.cliCatDes = cliCatDes;
        this.cliCatLimMin = cliCatLimMin;
        this.cliCatLimMax = cliCatLimMax;
        this.cliCatEstReg = cliCatEstReg;
    }

    public int getCliCatIde() {
        return cliCatIde;
    }

    public void setCliCatIde(int cliCatIde) {
        this.cliCatIde = cliCatIde;
    }

    public String getCliCatDes() {
        return cliCatDes;
    }

    public void setCliCatDes(String cliCatDes) {
        this.cliCatDes = cliCatDes;
    }

    public double getCliCatLimMin() {
        return cliCatLimMin;
    }

    public void setCliCatLimMin(double cliCatLimMin) {
        this.cliCatLimMin = cliCatLimMin;
    }

    public double getCliCatLimMax() {
        return cliCatLimMax;
    }

    public void setCliCatLimMax(double cliCatLimMax) {
        this.cliCatLimMax = cliCatLimMax;
    }

    public String getCliCatEstReg() {
        return cliCatEstReg;
    }

    public void setCliCatEstReg(String cliCatEstReg) {
        this.cliCatEstReg = cliCatEstReg;
    }
}
