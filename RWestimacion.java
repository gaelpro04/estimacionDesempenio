import java.io.*;
import java.util.List;
import java.util.Map;

public class RWestimacion {

    public ClaseClasificacion lecturaClase(String ruta) {

        String linea;
        ClaseClasificacion claseClasi = new ClaseClasificacion();
        DatoPrueba datoPrueba = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            int contador = 0;

            while ((linea = br.readLine()) != null) {
                String[] contenido = linea.trim().split(",");

                DatoPrueba dato = new DatoPrueba(Integer.parseInt(contenido[0]), contenido[1], contenido[2]);
                claseClasi.addDato(dato);

                if (contador == 0) {
                    datoPrueba = claseClasi.getDato(Integer.parseInt(contenido[0]));
                }

                contador++;
            }
            br.close();

            assert datoPrueba != null;
            String datoActual = datoPrueba.getClaseV();
            int cantidadClases = 1;
            for (int i = 1; i < claseClasi.sizeDatos(); i++) {
                if (!datoActual.equals(claseClasi.getDato(i).getClaseV())) {
                    ++cantidadClases;
                    datoActual = claseClasi.getDato(i).getClaseV();
                }
            }

            claseClasi.setCantidadClases(cantidadClases);
            return claseClasi;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void escrituraResultados(List<Map<Integer, String>> resultados, List<String> clasesRegistradas) {

        StringBuilder nombreBase = new StringBuilder();
        nombreBase.append("claseEvaluada");

        int contadorClases = 0;
        for (String claseRegistrada : clasesRegistradas) {
            nombreBase.append(claseRegistrada);

            String extension = ".csv";
            File file = new File(nombreBase + extension);

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));

                bw.write("CLASE," + clasesRegistradas.get(contadorClases));
                bw.newLine();

                int maximo = resultados.get(contadorClases).size();
                int contador = 0;
                for (Integer claseID : resultados.get(contadorClases).keySet()) {
                    bw.write(claseID + "," + resultados.get(contadorClases).get(claseID));
                    if (contador != maximo - 1) {
                        bw.newLine();
                    }

                    ++contador;
                }
                bw.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ++contadorClases;
        }
    }

    public void escrituraDatosFinales(List<String> datosFinales, List<String> clasesRegistradas) {

        String nombreBase = "clasesEvaluadas";
        String extension = ".csv";
        StringBuilder sb = new StringBuilder();

        for (String clase : clasesRegistradas) {
            sb.append(clase);
        }

        File file = new File(nombreBase + sb + extension);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            int contador = 0;
            for (String datoFinal : datosFinales) {
                bw.write(datoFinal);

                if (contador != datosFinales.size() - 1) {
                    bw.newLine();
                }
                ++contador;
            }
            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void escrituraMatriz(int[][] matriz, List<String> clasesRegistradas) {

        String nombreBase = "matrizConfusion";
        String extension = ".csv";
        StringBuilder sb = new StringBuilder();

        for (String claseRegistrada : clasesRegistradas) {
            sb.append(claseRegistrada);
        }

        File file = new File(nombreBase + sb + extension);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("Clase/Prediccion,");

            int contador = 0;
            for (String claseRegistrada : clasesRegistradas) {

                if (contador != clasesRegistradas.size() - 1) {
                    bw.write(claseRegistrada + ",");
                } else {
                    bw.write(claseRegistrada);
                }

                ++contador;
            }
            bw.newLine();

            int total = clasesRegistradas.size();


            for (int i = 0; i < total; i++) {
                bw.write(clasesRegistradas.get(i));
                for (int j = 0; j < total; j++) {
                    bw.write("," + matriz[i][j]);
                }
                if (i != 2) {
                    bw.newLine();
                }
            }
            bw.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
