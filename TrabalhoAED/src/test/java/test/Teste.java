/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Estruturas.Database;
import Estruturas.Registro;
import Estruturas.Tabela;
import Funcoes.MergeSort;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author nicolasferranti
 */
public class Teste {

    public static String[] getPKs(String file, String tableName) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        while (line != null) {
            if (line.contains("ADD CONSTRAINT") && line.contains(tableName + "_pkey")) {
                String linha = line.replace(")", "\t");
                linha = linha.replace("(", "\t");
                String campos = linha.split("\t")[1];
                String[] pks = campos.split(", ");
                return pks;
            }
            line = br.readLine();
        }
        return null;
    }

    public static String[] getPKbyFK(String file, String tableName) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        String[] pks = null;
        String chaves = "";
        while (line != null) {
            if (line.contains("ADD CONSTRAINT") && line.contains(tableName) && line.contains("FOREIGN KEY")) {
                String linha = line.replace(")", "\t");
                linha = linha.replace("(", "\t");
                String campos = linha.split("\t")[1];
                chaves += campos + "\t";
            }
            line = br.readLine();
        }
        if (!chaves.equals("")) {
            pks = chaves.split("\t");
        }
        return pks;
    }

    public static int[] getIndex(String line, String[] pks) {
        if (line == null || pks == null) {
            return null;
        }
        String linha = line.replace(")", "\t");
        linha = linha.replace("(", "\t");
        String[] x = linha.split("\t");
        String campos = linha.split("\t")[1];
        String[] colunas = campos.split(", ");

        int[] indices = new int[pks.length];
        int indiceIncremento = 0;
        for (int i = 0; i < pks.length; i++) {
            for (int j = 0; j < colunas.length; j++) {
                if (pks[i].equals(colunas[j])) {
                    indices[indiceIncremento] = j;
                    indiceIncremento++;
                }
            }
        }
        if (indiceIncremento == 0) {
            return null;
        }
        return indices;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        String file = "/home/nicolasferranti/Downloads/usda-r18-1.0/usda.sql";
        // Buffered é mais rápido do que Scanner
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();

        Database d = new Database(10);

        while (line != null) {

            if (line.startsWith("CREATE TABLE")) {
                String tableName = line.split(" ")[2];
                line = br.readLine();

                ArrayList<String> colunas = new ArrayList<>();
                while (!line.startsWith(");")) {

                    String collumn = line.split(" ")[4];
                    colunas.add(collumn);
                    line = br.readLine();
                }
                //d.addTable(tableName, colunas);

                //System.out.println("------");
            }

            if (line.startsWith("COPY")) {
//                System.out.println(line);
                String tableName = line.split(" ")[1];
                //Tabela t = d.getTabela(tableName);
                String[] pks = getPKs(file, tableName);
                int[] indiceChaves = null;
                if (pks == null) {
                    pks = getPKbyFK(file, tableName);
                }
                indiceChaves = getIndex(line, pks);

//                for (int i : indiceChaves) {
//                    System.out.print(i + " ");
//                }
//                System.out.println("");
                line = br.readLine();
                while (!line.startsWith("\\.")) {

                    line = line.replace("\t", "\t ");
                    String[] valores = line.split("\t");
                    String identificador = "";
                    for (int indice : indiceChaves) {
                        identificador += valores[indice];
                    }
                    identificador = identificador.replace(" ", "");
//                    System.out.println("id: " + identificador);
//                    for (String i : valores) {
//                        System.out.print("r>"+i + "| ");
//                    }
//                    System.out.println("");
                    line = br.readLine();

                    Registro r = new Registro(identificador, valores);
                   // t.inserirRegistro(r);
                }
                //t.printTabela();

//                MergeSort s = new MergeSort();
//                s.Sort(t.getRegistros(), t.indice);
//                System.out.println("");
//                t.printTabela();
                //break;
            }
            line = br.readLine();
        }
        d.Consulta("data_src", "D1107");
        d.Consulta("data_src", "D1107");

    }
}
