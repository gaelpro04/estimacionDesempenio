import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstimacionD {

    public List<Map<Integer, String>> evaluacionClases(ClaseClasificacion clase) {

        //Lista para guardar la evaluacion del ML
        List<Map<Integer,String>> clasesEvaluadas = new ArrayList<>();

        for (int i = 0; i < clase.getCantidadClases(); ++i) {
            clasesEvaluadas.add(new HashMap<>());
        }

        //Se registran las cantidad de clases
        List<String> clasesRegistradas = clase.getClasesActuales();

        //Ciclo para evaluar el ML
        //FP,FN,TN,TP
        for (int i = 0; i < clasesRegistradas.size(); i++) {
            String claseRegistrada = clasesRegistradas.get(i);


            for (int j = 0; j < clase.sizeDatos(); j++) {
                DatoPrueba dato = clase.getDato(j+1);
                //Para TP y FN
                if (claseRegistrada.equals(dato.getClaseV())) {

                    //Para TP
                    if (dato.getClaseV().equals(dato.getClaseP())) {
                        clasesEvaluadas.get(i).put(j+1,"TP");

                        //Para FN
                    } else if (!dato.getClaseV().equals(dato.getClaseP())) {
                        clasesEvaluadas.get(i).put(j+1, "FN");
                    }

                    //Para TN y FP
                } else {

                    //Para FP
                    if (!dato.getClaseV().equals(dato.getClaseP()) && dato.getClaseP().equals(claseRegistrada)) {
                        clasesEvaluadas.get(i).put(j+1, "FP");

                        //Para TN
                    } else if (!dato.getClaseP().equals(claseRegistrada)) {
                        clasesEvaluadas.get(i).put(j+1, "TN");
                    }
                }
            }
        }

        return clasesEvaluadas;
    }

    public List<String> evaluacionesFinales(List<Map<Integer, String>> resultados, List<String> clasesRegistradas) {

        List<StringBuilder> sbs = new ArrayList<>();
        List<Double> evaluaciones = new ArrayList<>();

        for (int i = 0; i < clasesRegistradas.size(); i++) {
            sbs.add(new StringBuilder());
            evaluaciones.add(0.0);
        }

        int contador = 0;
        for (String claseRegistrada : clasesRegistradas) {
            int sumaTP = 0;
            int sumaTN = 0;
            int sumaFP = 0;
            int sumaFN = 0;

            double reCall = 0;
            double accuracy = 0;
            double fScore = 0;

            for (String claseE : resultados.get(contador).values()) {
                switch (claseE) {
                    case "TP":
                        ++sumaTP;
                        break;
                    case "TN":
                        ++sumaTN;
                        break;
                    case "FP":
                        ++sumaFP;
                        break;
                    case "FN":
                        ++sumaFN;
                        break;
                }
            }
            sbs.getFirst().append(claseRegistrada + ":TP=" + sumaTP + "TN=" + sumaTN + "FP=" + sumaFP + "FN=" + sumaFN + ",");

            accuracy = (double) (sumaTP + sumaTN) / (sumaTP + sumaTN + sumaFP + sumaFN);
            reCall = (double) sumaTP / (sumaTP + sumaFN);
            fScore = (double) sumaTP / (sumaTP + sumaFP);

            String  accuracyS = String.format("%.2f", accuracy);
            String reCallS = String.format("%.2f", reCall);
            String fScoreS = String.format("%.2f", fScore);

            sbs.get(1).append(claseRegistrada + ":accuracy=" + accuracyS + "recall=" + reCallS + "FScore=" + fScoreS + ",");

            Double actual = evaluaciones.getFirst();
            evaluaciones.set(0,actual + accuracy);

            Double actual1 = evaluaciones.get(1);
            evaluaciones.set(1,actual1 + reCall);

            Double actual2 = evaluaciones.get(2);
            evaluaciones.set(2,actual2 + fScore);

            ++contador;
        }

        Double promAcc = evaluaciones.getFirst() / clasesRegistradas.size();
        Double promReCall = evaluaciones.get(1) / clasesRegistradas.size();
        Double promFScore = evaluaciones.get(2) / clasesRegistradas.size();

        String promAccS = String.format("%.2f", promAcc);
        String promReCallS = String.format("%.2f", promReCall);
        String promFScoreS = String.format("%.2f", promFScore);

        sbs.get(2).append("Avg-accuracy=" + promAccS + "," + "Avg-reCall=" + promReCallS + "," +"Avg-FScore=" + promFScoreS);

        List<String> resultadosF = new ArrayList<>();
        for (int i = 0; i < sbs.size(); ++i) {
            resultadosF.add(sbs.get(i).toString());
        }

        return resultadosF;
    }

    public int[][] matrizConfusion(ClaseClasificacion clase) {

        int clasesSize = clase.sizeDatos();
        int[][] matriz = new int[clase.getClasesActuales().size()][clase.getClasesActuales().size()];
        String[] clases = new String[clasesSize];

        for (int i = 0; i < clase.getClasesActuales().size(); ++i) {
            clases[i] = clase.getClasesActuales().get(i);
        }

        for (int i = 0; i < clasesSize; ++i) {
            String real = clase.getDato(i+1).getClaseV();
            String prediccion = clase.getDato(i+1).getClaseP();

            int fila = indexOf(clases, real);
            int columna = indexOf(clases, prediccion);

            matriz[fila][columna]++;
        }

        return matriz;
    }

    private int indexOf(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }
}
