//Soure packages/Entidades.transacciones/TransaccionInventario.java
package Entidades.transacciones;

public class TransaccionInventario {
    private int traInvIde;
    private int traInvProIde;
    private int traInvTipTraIde;
    private int traInvAlmIde;
    private int traInvRepVenIde;
    private int traInvAnio;
    private int traInvMes;
    private int traInvDia;
    private int traInvCan;
    private String traInvEstReg;

    public TransaccionInventario() {}

    public TransaccionInventario(int traInvIde, int traInvProIde, int traInvTipTraIde, int traInvAlmIde,
                                  int traInvRepVenIde, int traInvAnio, int traInvMes, int traInvDia,
                                  int traInvCan, String traInvEstReg) {
        this.traInvIde = traInvIde;
        this.traInvProIde = traInvProIde;
        this.traInvTipTraIde = traInvTipTraIde;
        this.traInvAlmIde = traInvAlmIde;
        this.traInvRepVenIde = traInvRepVenIde;
        this.traInvAnio = traInvAnio;
        this.traInvMes = traInvMes;
        this.traInvDia = traInvDia;
        this.traInvCan = traInvCan;
        this.traInvEstReg = traInvEstReg;
    }

    public int getTraInvIde() { return traInvIde; }
    public void setTraInvIde(int traInvIde) { this.traInvIde = traInvIde; }

    public int getTraInvProIde() { return traInvProIde; }
    public void setTraInvProIde(int traInvProIde) { this.traInvProIde = traInvProIde; }

    public int getTraInvTipTraIde() { return traInvTipTraIde; }
    public void setTraInvTipTraIde(int traInvTipTraIde) { this.traInvTipTraIde = traInvTipTraIde; }

    public int getTraInvAlmIde() { return traInvAlmIde; }
    public void setTraInvAlmIde(int traInvAlmIde) { this.traInvAlmIde = traInvAlmIde; }

    public int getTraInvRepVenIde() { return traInvRepVenIde; }
    public void setTraInvRepVenIde(int traInvRepVenIde) { this.traInvRepVenIde = traInvRepVenIde; }

    public int getTraInvAnio() { return traInvAnio; }
    public void setTraInvAnio(int traInvAnio) { this.traInvAnio = traInvAnio; }

    public int getTraInvMes() { return traInvMes; }
    public void setTraInvMes(int traInvMes) { this.traInvMes = traInvMes; }

    public int getTraInvDia() { return traInvDia; }
    public void setTraInvDia(int traInvDia) { this.traInvDia = traInvDia; }

    public int getTraInvCan() { return traInvCan; }
    public void setTraInvCan(int traInvCan) { this.traInvCan = traInvCan; }

    public String getTraInvEstReg() { return traInvEstReg; }
    public void setTraInvEstReg(String traInvEstReg) { this.traInvEstReg = traInvEstReg; } 
}
