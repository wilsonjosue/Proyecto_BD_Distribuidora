//Soure packages/Entidades.transacciones/FacturacionPedido.java
package Entidades.transacciones;

public class FacturacionPedido {
    
    private int facPedIde;
    private int facPedFacIde;
    private int facPedCabIde;
    private String facPedEstReg;

    public FacturacionPedido(int facPedIde, int facPedFacIde, int facPedCabIde, String facPedEstReg) {
        this.facPedIde = facPedIde;
        this.facPedFacIde = facPedFacIde;
        this.facPedCabIde = facPedCabIde;
        this.facPedEstReg = facPedEstReg;
    }

    public int getFacPedIde() {
        return facPedIde;
    }

    public void setFacPedIde(int facPedIde) {
        this.facPedIde = facPedIde;
    }

    public int getFacPedFacIde() {
        return facPedFacIde;
    }

    public void setFacPedFacIde(int facPedFacIde) {
        this.facPedFacIde = facPedFacIde;
    }

    public int getFacPedCabIde() {
        return facPedCabIde;
    }

    public void setFacPedCabIde(int facPedCabIde) {
        this.facPedCabIde = facPedCabIde;
    }

    public String getFacPedEstReg() {
        return facPedEstReg;
    }

    public void setFacPedEstReg(String facPedEstReg) {
        this.facPedEstReg = facPedEstReg;
    }
}
