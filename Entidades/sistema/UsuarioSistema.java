//Soure packages/Entidades.sistema/UsuarioSistema.java
package Entidades.sistema;
public class UsuarioSistema {
    private int usuSisIde;
    private String usuSisNom;
    private String usuSisCon;
    private String usuSisRol;
    private int usuSisRepVen;
    private String usuSisEstReg;

    public UsuarioSistema() {}

    public UsuarioSistema(int usuSisIde, String usuSisNom, String usuSisCon,
                          String usuSisRol, int usuSisRepVen, String usuSisEstReg) {
        this.usuSisIde = usuSisIde;
        this.usuSisNom = usuSisNom;
        this.usuSisCon = usuSisCon;
        this.usuSisRol = usuSisRol;
        this.usuSisRepVen = usuSisRepVen;
        this.usuSisEstReg = usuSisEstReg;
    }

    public int getUsuSisIde() {
        return usuSisIde;
    }

    public void setUsuSisIde(int usuSisIde) {
        this.usuSisIde = usuSisIde;
    }

    public String getUsuSisNom() {
        return usuSisNom;
    }

    public void setUsuSisNom(String usuSisNom) {
        this.usuSisNom = usuSisNom;
    }

    public String getUsuSisCon() {
        return usuSisCon;
    }

    public void setUsuSisCon(String usuSisCon) {
        this.usuSisCon = usuSisCon;
    }

    public String getUsuSisRol() {
        return usuSisRol;
    }

    public void setUsuSisRol(String usuSisRol) {
        this.usuSisRol = usuSisRol;
    }

    public int getUsuSisRepVen() {
        return usuSisRepVen;
    }

    public void setUsuSisRepVen(int usuSisRepVen) {
        this.usuSisRepVen = usuSisRepVen;
    }

    public String getUsuSisEstReg() {
        return usuSisEstReg;
    }

    public void setUsuSisEstReg(String usuSisEstReg) {
        this.usuSisEstReg = usuSisEstReg;
    }

    @Override
    public String toString() {
        return "UsuarioSistema{" +
                "ID=" + usuSisIde +
                ", Usuario='" + usuSisNom + '\'' +
                ", Rol='" + usuSisRol + '\'' +
                ", Representante=" + usuSisRepVen +
                ", Estado='" + usuSisEstReg + '\'' +
                '}';
    }
}
