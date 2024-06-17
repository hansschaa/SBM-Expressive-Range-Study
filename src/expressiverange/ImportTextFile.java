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
        String csvFields = "ID;Empty Spaces;Negative Space;Interesting Elements;Significant Jumps;Linearity;Lenience;Avg Enemy Comp;Enemy Count;Density";

        String fileNamePrefix = "";
        int rowCount = 0;
        int fileIndex = 1;
        BufferedWriter writer = null;
        try {
            for (String data : dataList) {
                if (rowCount == 0 || rowCount == 200 || rowCount == 400 || rowCount == 600) {
                    if (writer != null) {
                        writer.close();
                    }
                    switch(fileIndex){
                        case 1:
                            fileNamePrefix = "data - ga";
                            break;
                        case 2:
                            fileNamePrefix = "data - mc";
                            break;
                        case 3:
                            fileNamePrefix = "data - gan";
                            break;
                        case 4:
                            fileNamePrefix = "data - smb";
                            break;
                    }
                    
                    writer = new BufferedWriter(new FileWriter(fileNamePrefix +".csv"));
                    writer.write(csvFields);
                    writer.newLine();
                    fileIndex++;
                }
                writer.write(data);
                writer.newLine();
                rowCount++;
            }

            if (writer != null) {
                writer.close();
            }

            System.out.println("Data successfully exported");
        } catch (IOException e) {
            System.err.println("Error exporting data to CSV: " + e.getMessage());
        } finally {
            // Ensure the writer is closed in case of an exception
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error closing the writer: " + e.getMessage());
                }
            }
        }
    }
}
