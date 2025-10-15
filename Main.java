import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RWestimacion archivos = new RWestimacion();

        System.out.println("Ingresa el archivo.csv");
        String nombreArchivo = sc.next();

        System.out.println();
        System.out.println("======Tabla resultado de las clases evaluadas por el ML=======");
        ClaseClasificacion clase = archivos.lecturaClase(nombreArchivo);
        System.out.println(clase);

        System.out.println("=====Evaluaciones por clase=====");
        EstimacionD esti = new EstimacionD();
        List<Map<Integer, String>> evaluacion = esti.evaluacionClases(clase);
        archivos.escrituraResultados(evaluacion, clase.getClasesActuales());

        for (int i = 0; i < clase.getClasesActuales().size(); ++i) {
            System.out.println("==Clase " + clase.getClasesActuales().get(i) + "==");

            for (Integer claseID : evaluacion.get(i).keySet()) {
                System.out.println(claseID + "," + evaluacion.get(i).get(claseID));
            }
        }

        System.out.println("=====Resultados=====");
        List<String> resultados = esti.evaluacionesFinales(evaluacion, clase.getClasesActuales());
        archivos.escrituraDatosFinales(resultados,clase.getClasesActuales());

        for (String resultado : resultados) {
            System.out.println(resultado);
        }

        System.out.println("=====Matriz de confusion=====");
        int[][] matrizConfusion = esti.matrizConfusion(clase);
        archivos.escrituraMatriz(matrizConfusion, clase.getClasesActuales());
        StringBuilder sb = getStringBuilder(clase, matrizConfusion);

        System.out.println(sb);
    }

    private static StringBuilder getStringBuilder(ClaseClasificacion clase, int[][] matrizConfusion) {
        StringBuilder sb = new StringBuilder();
        sb.append("Clase/Prediccion");

        for (String claseRe : clase.getClasesActuales()) {
            sb.append("," + claseRe);
        }
        sb.append("\n");

        for (int i = 0; i < clase.getCantidadClases(); i++) {
            sb.append(clase.getClasesActuales().get(i));
            for (int j = 0; j < clase.getCantidadClases(); j++) {
                sb.append("," + matrizConfusion[i][j]);
            }
            sb.append("\n");
        }
        return sb;
    }
}
