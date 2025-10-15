import java.util.ArrayList;
import java.util.List;

public class ClaseClasificacion {
    private List<DatoPrueba> datos;
    private int cantidadClases;

    public ClaseClasificacion(List<DatoPrueba> datos, int cantidadClases) {
        this.datos = datos;
        this.cantidadClases = cantidadClases;
    }

    public ClaseClasificacion() {
        datos = new ArrayList<>();
        cantidadClases = 0;
    }

    public void addDato(DatoPrueba dato) {
        datos.add(dato);
    }

    public void deleteDato(DatoPrueba dato) {
        datos.remove(dato);
    }

    public DatoPrueba getDato(int id) {
        for (DatoPrueba dato : datos) {
            if (dato.getId() == id) {
                return dato;
            }
        }

        return null;
    }

    public int sizeDatos() {
        return datos.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("| ID | V | P |\n");

        for (DatoPrueba dato : datos) {
            sb.append(dato + "\n");
        }

        return sb.toString();
    }

    public int getCantidadClases() {
        return cantidadClases;
    }

    public void setCantidadClases(int cantidadClases) {
        this.cantidadClases = cantidadClases;
    }

    public List<String> getClasesActuales() {
        List<String> clases = new ArrayList<>();
        clases.add(datos.getFirst().getClaseV());
        String primerClase = clases.getFirst();

        for (DatoPrueba dato : datos) {

            if (!primerClase.equals(dato.getClaseV())) {
                clases.add(dato.getClaseV());
                primerClase = dato.getClaseV();
            }
        }

        return clases;
    }
}
