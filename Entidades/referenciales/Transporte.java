//Soure packages/Entidades/Transporte.java
package Entidades.referenciales;

public class Transporte {
    private int traIde;
    private String traNomTrp;
    private String traPlaVeh;
    private String traTel;
    private String traEmpTra;
    private String traEstReg;

    public Transporte() {}

    public Transporte(int traIde, String traNomTrp, String traPlaVeh, String traTel, String traEmpTra, String traEstReg) {
        this.traIde = traIde;
        this.traNomTrp = traNomTrp;
        this.traPlaVeh = traPlaVeh;
        this.traTel = traTel;
        this.traEmpTra = traEmpTra;
        this.traEstReg = traEstReg;
    }

    public int getTraIde() {
        return traIde;
    }

    public void setTraIde(int traIde) {
        this.traIde = traIde;
    }

    public String getTraNomTrp() {
        return traNomTrp;
    }

    public void setTraNomTrp(String traNomTrp) {
        this.traNomTrp = traNomTrp;
    }

    public String getTraPlaVeh() {
        return traPlaVeh;
    }

    public void setTraPlaVeh(String traPlaVeh) {
        this.traPlaVeh = traPlaVeh;
    }

    public String getTraTel() {
        return traTel;
    }

    public void setTraTel(String traTel) {
        this.traTel = traTel;
    }

    public String getTraEmpTra() {
        return traEmpTra;
    }

    public void setTraEmpTra(String traEmpTra) {
        this.traEmpTra = traEmpTra;
    }

    public String getTraEstReg() {
        return traEstReg;
    }

    public void setTraEstReg(String traEstReg) {
        this.traEstReg = traEstReg;
    }
}
