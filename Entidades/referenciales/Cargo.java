//Soure packages/Entidades/Cargo.java
package Entidades.referenciales;

public class Cargo {
   
    private int carIde;
    private double carCuoPre;
    private double carVenRea;
    private String carDes;
    private double carSue;
    private String carEstReg; // A, I, *
    private int carFlaAct;  // 0: No modificar, 1: Modificar

    public Cargo() {}

    public Cargo(int CarIde, double CarCuoPre, double CarVenRea, String CarDes, double CarSue, String CarEstReg) {
        this.carIde = CarIde;
        this.carCuoPre = CarCuoPre;
        this.carVenRea = CarVenRea;
        this.carDes = CarDes;
        this.carSue = CarSue;
        this.carEstReg = CarEstReg;
    }

    // Getters y Setters
    public int getCarIde() { return carIde; }
    public void setCarIde(int CarIde) { this.carIde = CarIde; }

    public double getCarCuoPre() { return carCuoPre; }
    public void setCarCuoPre(double CarCuoPre) { this.carCuoPre = CarCuoPre; }

    public double getCarVenRea() { return carVenRea; }
    public void setCarVenRea(double CarVenRea) { this.carVenRea = CarVenRea; }

    public String getCarDes() { return carDes; }
    public void setCarDes(String CarDes) { this.carDes = CarDes; }

    public double getCarSue() { return carSue; }
    public void setCarSue(double CarSue) { this.carSue = CarSue; }

    public String getCarEstReg() { return carEstReg; }
    public void setCarEstReg(String CarEstReg) { this.carEstReg = CarEstReg; }
    
    public int getCarFlaAct() {
    return carFlaAct;
    }

    public void setCarFlaAct(int carFlaAct) {
        this.carFlaAct = carFlaAct;
    }
}

