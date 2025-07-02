//Soure packages/Entidades.transacciones/Compra.java
package Entidades.transacciones;

public class Compra {
    private int comIde;
    private java.sql.Date comFec;
    private double comMonTot;
    private int comPrvIde;
    private int comEmpIde;
    private String comEstReg;

    public Compra() {}

    public Compra(int comIde, java.sql.Date comFec, double comMonTot, int comPrvIde, int comEmpIde, String comEstReg) {
        this.comIde = comIde;
        this.comFec = comFec;
        this.comMonTot = comMonTot;
        this.comPrvIde = comPrvIde;
        this.comEmpIde = comEmpIde;
        this.comEstReg = comEstReg;
    }

    public int getComIde() { return comIde; }
    public void setComIde(int comIde) { this.comIde = comIde; }

    public java.sql.Date getComFec() { return comFec; }
    public void setComFec(java.sql.Date comFec) { this.comFec = comFec; }

    public double getComMonTot() { return comMonTot; }
    public void setComMonTot(double comMonTot) { this.comMonTot = comMonTot; }

    public int getComPrvIde() { return comPrvIde; }
    public void setComPrvIde(int comPrvIde) { this.comPrvIde = comPrvIde; }

    public int getComEmpIde() { return comEmpIde; }
    public void setComEmpIde(int comEmpIde) { this.comEmpIde = comEmpIde; }

    public String getComEstReg() { return comEstReg; }
    public void setComEstReg(String comEstReg) { this.comEstReg = comEstReg; }
}