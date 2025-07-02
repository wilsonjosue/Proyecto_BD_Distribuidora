//Soure packages/Entidades.maestras/RepresentantesVentas.java
package Entidades.maestras;

public class RepresentantesVentas {
    private int repVenIde;
    private String repVenNom;
    private String repVenApe;
    private int repVenEda;
    private int repVenOfi;
    private int repVenCar;
    private int repVenAnioCon;
    private int repVenMesCon;
    private int repVenDiaCon;
    private int repVenNumVen;
    private String repVenEstReg;

    public RepresentantesVentas() {}

    public RepresentantesVentas(int repVenIde, String repVenNom, String repVenApe, int repVenEda, int repVenOfi, int repVenCar,
                                int repVenAnioCon, int repVenMesCon, int repVenDiaCon, int repVenNumVen, String repVenEstReg) {
        this.repVenIde = repVenIde;
        this.repVenNom = repVenNom;
        this.repVenApe = repVenApe;
        this.repVenEda = repVenEda;
        this.repVenOfi = repVenOfi;
        this.repVenCar = repVenCar;
        this.repVenAnioCon = repVenAnioCon;
        this.repVenMesCon = repVenMesCon;
        this.repVenDiaCon = repVenDiaCon;
        this.repVenNumVen = repVenNumVen;
        this.repVenEstReg = repVenEstReg;
    }

    // Getters y Setters
    public int getRepVenIde() { return repVenIde; }
    public void setRepVenIde(int repVenIde) { this.repVenIde = repVenIde; }

    public String getRepVenNom() { return repVenNom; }
    public void setRepVenNom(String repVenNom) { this.repVenNom = repVenNom; }

    public String getRepVenApe() { return repVenApe; }
    public void setRepVenApe(String repVenApe) { this.repVenApe = repVenApe; }

    public int getRepVenEda() { return repVenEda; }
    public void setRepVenEda(int repVenEda) { this.repVenEda = repVenEda; }

    public int getRepVenOfi() { return repVenOfi; }
    public void setRepVenOfi(int repVenOfi) { this.repVenOfi = repVenOfi; }

    public int getRepVenCar() { return repVenCar; }
    public void setRepVenCar(int repVenCar) { this.repVenCar = repVenCar; }

    public int getRepVenAnioCon() { return repVenAnioCon; }
    public void setRepVenAnioCon(int repVenAnioCon) { this.repVenAnioCon = repVenAnioCon; }

    public int getRepVenMesCon() { return repVenMesCon; }
    public void setRepVenMesCon(int repVenMesCon) { this.repVenMesCon = repVenMesCon; }

    public int getRepVenDiaCon() { return repVenDiaCon; }
    public void setRepVenDiaCon(int repVenDiaCon) { this.repVenDiaCon = repVenDiaCon; }

    public int getRepVenNumVen() { return repVenNumVen; }
    public void setRepVenNumVen(int repVenNumVen) { this.repVenNumVen = repVenNumVen; }

    public String getRepVenEstReg() { return repVenEstReg; }
    public void setRepVenEstReg(String repVenEstReg) { this.repVenEstReg = repVenEstReg; }
}
