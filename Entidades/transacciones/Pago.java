//Soure packages/Entidades.transacciones/Pago.java
package Entidades.transacciones;

public class Pago {
    private int pagIde;
    private int pagAnio;
    private int pagMes;
    private int pagDia;
    private double pagMon;
    private int pagFacIde;
    private int pagForPagIde;
    private String pagoForEstReg;

    public Pago(int pagIde, int pagAnio, int pagMes, int pagDia, double pagMon, int pagFacIde, int pagForPagIde, String pagoForEstReg) {
        this.pagIde = pagIde;
        this.pagAnio = pagAnio;
        this.pagMes = pagMes;
        this.pagDia = pagDia;
        this.pagMon = pagMon;
        this.pagFacIde = pagFacIde;
        this.pagForPagIde = pagForPagIde;
        this.pagoForEstReg = pagoForEstReg;
    }

    public int getPagIde() {
        return pagIde;
    }

    public int getPagAnio() {
        return pagAnio;
    }

    public int getPagMes() {
        return pagMes;
    }

    public int getPagDia() {
        return pagDia;
    }

    public double getPagMon() {
        return pagMon;
    }

    public int getPagFacIde() {
        return pagFacIde;
    }

    public int getPagForPagIde() {
        return pagForPagIde;
    }

    public String getPagoForEstReg() {
        return pagoForEstReg;
    }

    public void setPagoForEstReg(String pagoForEstReg) {
        this.pagoForEstReg = pagoForEstReg;
    }
}
