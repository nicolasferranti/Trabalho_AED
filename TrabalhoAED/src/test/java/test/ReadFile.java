/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author nicolasferranti
 */
public class ReadFile {

    public static String[] getPKs(String file, String tableName) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        while (line != null) {
            if (line.contains("ADD CONSTRAINT") && line.contains(tableName + "_pkey")) {
                String linha = line.replace(")", "\t");
                linha = linha.replace("(", "\t");
                String campos = linha.split("\t")[1];
                String[] pks = campos.split(", ");
                for (int i = 0; i < pks.length; i++) {
                    System.out.print(pks[i] + " ");
                }
                System.out.println();
            }
            line = br.readLine();
        }
        return null;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String file = "/home/nicolasferranti/Downloads/usda-r18-1.0/usda.sql";
//        getPKs(file, "data_src");
//        getPKs(file, "datsrcln");
//        getPKs(file, "deriv_cd");
//        getPKs(file, "fd_group");
//        getPKs(file, "food_des");
//        getPKs(file, "nut_data");
//        getPKs(file, "nutr_def");
//        getPKs(file, "src_cd");
//        getPKs(file, "weight");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        while (line != null) {
            if (line.contains("ADD CONSTRAINT")) {
                System.out.println(line);
            }
            line = br.readLine();
        }
    }
}
