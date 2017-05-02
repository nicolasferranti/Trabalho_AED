/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aed.trabalhoaed;

import Estruturas.Database;
import Estruturas.Registro;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author nicolasferranti
 */
public class Main {

    public static String[] getPKs(String file, String tableName) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        while (line != null) {
            if (line.contains("ADD CONSTRAINT") && line.contains(tableName + "_pkey")) {
                String linha = line.replace(")", "\t");
                linha = linha.replace("(", "\t");
                String campos = linha.split("\t")[1];
                String[] pks = campos.split(", ");
                br.close();
                return pks;
            }
            line = br.readLine();
        }
        br.close();
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
        br.close();
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

    public static void EscreveTodasConsultas(String file, String temp) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

        while (line != null) {
            if (line.startsWith("COPY")) {
                String tableName = line.split(" ")[1];
                writer.write("begin\t " + tableName);
                writer.newLine();
                String[] pks = getPKs(file, tableName);
                if (pks == null) {
                    pks = getPKbyFK(file, tableName);
                }
                int[] indiceChaves = getIndex(line, pks);

                line = br.readLine();
                while (!line.startsWith("\\.")) {

                    line = line.replace("\t", "\t ");
                    String[] valores = line.split("\t");
                    String identificador = "";
                    for (int indice : indiceChaves) {
                        identificador += valores[indice];
                    }
                    identificador = identificador.replace(" ", "");

                    line = br.readLine();

                    writer.write(identificador);
                    writer.newLine();
                }
            }
            line = br.readLine();

        }
        br.close();
        writer.flush();
        writer.close();
        System.out.println("Arquivo criado em: " + temp);
    }

    public static void ExecutaTodasConsultas(String file, Database d) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();

        long startTime = System.currentTimeMillis();
        String tableName = null;

        while (line != null) {
            if (line.startsWith("begin")) {
                tableName = line.split("\t ")[1];
                System.out.println("Consultando a tabela: " + tableName);
                line = br.readLine();
            }
            d.ConsultaSemPrint(tableName, line);
            line = br.readLine();
        }
        br.close();
        long duration = (System.currentTimeMillis() - startTime);
        System.out.println();
        System.out.println("Consultas terminadas (" + duration + " milliseconds)");
    }

    public static void ExecutaTodasConsultasCImpressao(String file, Database d) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();

        long startTime = System.currentTimeMillis();
        String tableName = null;

        while (line != null) {
            if (line.startsWith("begin")) {
                tableName = line.split("\t ")[1];
                System.out.println("Consultando a tabela: " + tableName);
                line = br.readLine();
            }
            d.Consulta(tableName, line);
            line = br.readLine();
        }
        br.close();
        long duration = (System.currentTimeMillis() - startTime);
        System.out.println();
        System.out.println("Consultas terminadas (" + duration + " milliseconds)");
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        DataInputStream in = new DataInputStream(System.in);
        System.out.print("Digite o caminho do arquivo de entrada(ex.: /home/usda.sql) :");
        String file = in.readLine();

        //String file = "/home/nicolasferranti/Downloads/usda-r18-1.0/usda.sql";
        
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();

        System.out.print("Digite a quantidade de tabelas :");
        Database d = new Database(Integer.parseInt(in.readLine()));

        long startTime = System.currentTimeMillis();

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
                d.addTable(tableName, colunas);

            }

            if (line.startsWith("COPY")) {
                String tableName = line.split(" ")[1];
                String[] pks = getPKs(file, tableName);
                int[] indiceChaves = null;
                if (pks == null) {
                    pks = getPKbyFK(file, tableName);
                }
                indiceChaves = getIndex(line, pks);

                line = br.readLine();
                while (!line.startsWith("\\.")) {

                    line = line.replace("\t", "\t ");
                    String[] valores = line.split("\t");
                    String identificador = "";
                    for (int indice : indiceChaves) {
                        identificador += valores[indice];
                    }
                    identificador = identificador.replace(" ", "");

                    line = br.readLine();

                   Registro r = new Registro(identificador, valores);
                    d.addRegistro(tableName, r);
                }
            }

            line = br.readLine();
        }
        br.close();
        System.out.println("Banco construído");
        long duration = (System.currentTimeMillis() - startTime);
        System.out.println();
        System.out.println("(" + duration + " milliseconds)");
        int op = -1;
        while (op != 0) {
            System.out.println("----------------");
            System.out.println("(0) Sair");
            System.out.println("(1) Fazer consulta informando tabela e id");
            System.out.println("(2) Executar todas as consultas sem impressão");
            System.out.println("(3) Executar todas as consultas com impressão");
            System.out.println("(4) Exibir tabelas");
            System.out.println("----------------");
            op = Integer.parseInt(in.readLine());
            switch (op) {
                case 0:
                    System.exit(0);
                case 1:
                    System.out.print("Digite o nome da tabela :");
                    String tabelaNome = in.readLine();
                    System.out.print("Digite o id do registro(chaves compostas estão concatenadas, * seleciona tudo) :");
                    String id = in.readLine();
                    d.Consulta(tabelaNome, id);
                    break;
                case 2:
                    System.out.print("Digite um local e nome para armazenar temporariamente as consultas( ex.: /tmp/a1.txt) :");
                    String temp = in.readLine();
                    EscreveTodasConsultas(file, temp);
                    System.out.println("Iniciando leitura de todas as consultas");
                    ExecutaTodasConsultas(temp, d);
                    break;
                case 3:
                    System.out.print("Digite um local e nome para armazenar temporariamente as consultas( ex.: /tmp/a1.txt) :");
                    String tempo = in.readLine();
                    EscreveTodasConsultas(file, tempo);
                    System.out.println("Iniciando leitura de todas as consultas");
                    ExecutaTodasConsultasCImpressao(tempo, d);
                    break;
                case 4:
                    d.printBD();
                    break;
                default:
                    System.out.println("Opção não reconhecida");
            }
        }
        //d.Consulta("data_src", "D1107");
    }
}
