/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expressiverange;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Hans
 */
public class ImportTextFile {
    public static char[][] leerMatrizDesdeArchivo(String nombreArchivo) {
        char[][] matriz = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));

            String linea;
            int filas = 0;
            int columnas = 0;

            // Obtener el número de filas y columnas
            while ((linea = br.readLine()) != null) {
                filas++;
                columnas = linea.length();
            }

            // Volver a leer el archivo para construir la matriz
            matriz = new char[filas][columnas];
            br = new BufferedReader(new FileReader(nombreArchivo));
            int filaActual = 0;

            while ((linea = br.readLine()) != null) {
                for (int i = 0; i < linea.length(); i++) {
                    matriz[filaActual][i] = linea.charAt(i);
                }
                filaActual++;
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matriz;
    }
    
    public static void exportDataToCSV(String[] dataList, String fileName) {
        

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String data : dataList) {
                writer.write(data);
                writer.newLine();
            }

            System.out.println("Data successfully exported to " + fileName);
        } catch (IOException e) {
            System.err.println("Error exporting data to CSV: " + e.getMessage());
        }
    }
}
