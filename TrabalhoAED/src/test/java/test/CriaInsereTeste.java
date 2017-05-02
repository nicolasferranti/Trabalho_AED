/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Estruturas.Database;
import Estruturas.Registro;
import Estruturas.Tabela;
import java.util.ArrayList;

/**
 *
 * @author nicolasferranti
 */
public class CriaInsereTeste {

    public static void main(String[] args) {
        Database d = new Database(10);
        ArrayList<String> colunas = new ArrayList<>();
        colunas.add("id");
        colunas.add("nome");
        colunas.add("sexo");

        //Tabela t = d.addTable("tabelateste", colunas);

        for (int i = 0; i < 5000; i++) {
            String[] valores = new String[3];
            valores[0] = String.valueOf(5000 - i);
            valores[1] = "nome" + i;
            if (i % 2 == 0) {
                valores[2] = "M";
            } else{
                valores[2] = "F";
            }
            Registro r = new Registro(valores[0], valores);
            //t.inserirRegistro(r);
        }
        //t.printTabela();
        //System.out.println("tamanho :"+ t.getTamanho());
        
        d.Consulta("tabelateste", "2500");


    }
}
