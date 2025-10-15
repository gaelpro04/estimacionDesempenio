public class DatoPrueba {
    private int id;
    private String claseV;
    private String claseP;

    public DatoPrueba(int id, String claseV, String claseP) {
        this.id = id;
        this.claseV = claseV;
        this.claseP = claseP;
    }

    public DatoPrueba() {
        id = 0;
        claseV = null;
        claseP = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClaseV() {
        return claseV;
    }

    public void setClaseV(String claseV) {
        this.claseV = claseV;
    }

    public String getClaseP() {
        return claseP;
    }

    public void setClaseP(String claseP) {
        this.claseP = claseP;
    }

    @Override
    public String toString() {
        return "[ " + id + " | " + claseV + " | " + claseP + " ]";
    }
}
