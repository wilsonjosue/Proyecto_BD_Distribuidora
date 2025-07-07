//Soure packages/Entidades.transacciones/Compra.java
package Entidades.transacciones;

public class Compra {
    private int comIde;
    private int comAnio;
    private int comMes;
    private int comDia;
    private double comMonTot;
    private int comPrvIde;
    private int comEmpIde;
    private String comEstReg;

    public Compra() {}

    public Compra(int comIde, int comAnio, int comMes, int comDia,
                  double comMonTot, int comPrvIde, int comEmpIde, String comEstReg) {
        this.comIde = comIde;
        this.comAnio = comAnio;
        this.comMes = comMes;
        this.comDia = comDia;
        this.comMonTot = comMonTot;
        this.comPrvIde = comPrvIde;
        this.comEmpIde = comEmpIde;
        this.comEstReg = comEstReg;
    }

    // Getters y setters
    public int getComIde() { return comIde; }
    public void setComIde(int comIde) { this.comIde = comIde; }

    public int getComAnio() { return comAnio; }
    public void setComAnio(int comAnio) { this.comAnio = comAnio; }

    public int getComMes() { return comMes; }
    public void setComMes(int comMes) { this.comMes = comMes; }

    public int getComDia() { return comDia; }
    public void setComDia(int comDia) { this.comDia = comDia; }

    public double getComMonTot() { return comMonTot; }
    public void setComMonTot(double comMonTot) { this.comMonTot = comMonTot; }

    public int getComPrvIde() { return comPrvIde; }
    public void setComPrvIde(int comPrvIde) { this.comPrvIde = comPrvIde; }

    public int getComEmpIde() { return comEmpIde; }
    public void setComEmpIde(int comEmpIde) { this.comEmpIde = comEmpIde; }

    public String getComEstReg() { return comEstReg; }
    public void setComEstReg(String comEstReg) { this.comEstReg = comEstReg; }
}