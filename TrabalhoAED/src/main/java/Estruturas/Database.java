/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import Funcoes.Hash;
import java.util.ArrayList;

/**
 *
 * @author nicolasferranti
 */
public class Database {

    private Tabela[] tabelas = null;
    private int tam;

    /*
     *   Cria novo banco aumentando o tamanho inicial de tabelas
     *   @param qtdTabelas a quantidade de tabelas iniciais
     *
     */
    public Database(int qtdTabelas) {
        float qtd = (float) qtdTabelas;
        float pct = (float) 4 / 3;
        this.tam = Math.round(qtd * pct);
        this.tabelas = new Tabela[this.tam];
    }

    /*
     * Adiciona nova tabela vazia ao banco
     * @param nome da tabela a ser adicionada
     * @param vetor de colunas dessa tabela
     * @return o objeto Tabela já adicionado
     */
    public Tabela addTable(String tableName, ArrayList<String> colunas) {
        try {
            Tabela t = new Tabela(tableName, colunas);

            int indice = Hash.Hash1TableSize(tableName, this.tam);
            this.insereTabela(indice, t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // insere tabela em um dado indice, busca posiçã olivre em caso de conflito
    private void insereTabela(int indice, Tabela t) {
        if (this.tabelas[indice] == null) {
            this.tabelas[indice] = t;
        } else {
            Tabela aux = this.tabelas[indice];
            while (aux.getProx() != null) {
                aux = aux.getProx();
            }
            aux.setProx(t);
        }
    }

    public boolean addRegistro(String tableName, Registro r) {
        Tabela t = this.getTabela(tableName);
        if (t == null || r == null) {
            return false;
        }
        t.inserirRegistro(r);
        return true;
    }

    public void removerRegistro(String tableName, String id) {
        Tabela t = this.getTabela(tableName);
        if (t == null) {
            System.out.println("Table null");
        } else if (id != null) {
            t.removerRegistro(id);
            //System.out.println("Registro removido");
        }

    }

    public void printBD() {
        for (int i = 0; i < this.tam; i++) {
            if (this.tabelas[i] != null) {
                Tabela it = this.tabelas[i];
                System.out.print(i + " " + it.nome + "(" + it.getColunas().toString() + ")");
                Tabela aux = this.tabelas[i].getProx();
                while (aux != null) {
                    System.out.print(" -> " + aux.nome + "(" + aux.getColunas().toString() + ")");
                    aux = aux.getProx();
                }
            } else {
                System.out.print(i);
            }
            System.out.println();
        }
    }

    public Tabela getTabela(String tableName) {
        int indice = Hash.Hash1TableSize(tableName, this.tam);
        Tabela t = this.tabelas[indice];
        if (t == null) {
            return null;
        } else {
            while (t != null && !t.nome.equals(tableName)) {
                t = t.getProx();
            }
            if (t == null) {
                return null;
            }
            return t;
        }
    }

    public int countSimples(String tableName) {
        Tabela t = this.getTabela(tableName);
        if (t == null) {
            return 0;
        } else {
            return t.getCountRegistros();
        }
    }

    public int countCondicional(String tableName, String campo, String chave) {
        Tabela t = this.getTabela(tableName);
        if (t == null) {
            return -1;
        } else {
            return t.countCondicional(campo, chave);
        }
    }

    public void Consulta(String tableName, String id) {
        long startTime = System.currentTimeMillis();

        int indice = Hash.Hash1TableSize(tableName, this.tam);
        Tabela t = this.tabelas[indice];
        if (t == null) {
            System.out.println("Table null");
        } else {
            try {
                while (t != null && !t.nome.equals(tableName)) {
                    t = t.getProx();
                }
                if (t == null) {
                    System.out.println("Table not found");
                } else if (id.equals("*")) {
                    t.printTabela();
                    long endTime = System.currentTimeMillis();
                    long duration = (endTime - startTime);
                    System.out.println();
                    System.out.println("(" + duration + " milliseconds)");
                } else {
                    Registro r = t.buscarRegistro(id);
                    if (r != null) {
                        for (String col : t.getColunas()) {
                            System.out.print(col + " | ");
                        }
                        System.out.println();
                        for (String data : r.dados) {
                            System.out.print(data + " | ");
                        }
                        long endTime = System.currentTimeMillis();
                        long duration = (endTime - startTime);
                        System.out.println();
                        System.out.println("(" + duration + " milliseconds)");
                    } else {
                        long endTime = System.currentTimeMillis();
                        long duration = (endTime - startTime);
                        System.out.println();
                        System.out.println("Empty set(" + duration + " milliseconds)");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Consulta(String tableName, String id, String campo) {
        long startTime = System.currentTimeMillis();

        int indice = Hash.Hash1TableSize(tableName, this.tam);
        Tabela t = this.tabelas[indice];
        if (t == null) {
            System.out.println("Table null");
        } else {
            try {
                while (t != null && !t.nome.equals(tableName)) {
                    t = t.getProx();
                }
                if (t == null) {
                    System.out.println("Table not found");
                } else if (id.equals("*")) {
                    t.printTabela();
                    long endTime = System.currentTimeMillis();
                    long duration = (endTime - startTime);
                    System.out.println();
                    System.out.println("(" + duration + " milliseconds)");
                } else {
                    Registro r = t.buscarRegistro(id, campo);
                    if (r != null) {
                        for (String col : t.getColunas()) {
                            System.out.print(col + " | ");
                        }
                        System.out.println();
                        for (String data : r.dados) {
                            System.out.print(data + " | ");
                        }
                        long endTime = System.currentTimeMillis();
                        long duration = (endTime - startTime);
                        System.out.println();
                        System.out.println("(" + duration + " milliseconds)");
                    } else {
                        long endTime = System.currentTimeMillis();
                        long duration = (endTime - startTime);
                        System.out.println();
                        System.out.println("Empty set(" + duration + " milliseconds)");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void ConsultaSemPrint(String tableName, String id) {
        long startTime = System.currentTimeMillis();

        int indice = Hash.Hash1TableSize(tableName, this.tam);
        Tabela t = this.tabelas[indice];
        if (t == null) {
            System.out.println("Table null");
        } else {
            try {
                while (t != null && !t.nome.equals(tableName)) {
                    t = t.getProx();
                }
                if (t == null) {
                    System.out.println("Table not found");
                } else {
                    Registro r = t.buscarRegistro(id);
                    if (r != null) {
                    } else {
                        System.out.println("registro nulo");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
        Consulta Inner Join O(n*m) sem print com apenas uma coluna a comparar
        para cada registro em t1 busca o equivalente em t2 tal que t1.campo=t2.campo
     */
    public void ConsultaInnerJoin(String tabelaNome, String tabelaNome2, String campo) {
        int total = 0;
        long begin = System.currentTimeMillis();
        Tabela t1 = this.getTabela(tabelaNome);
        Tabela t2 = this.getTabela(tabelaNome2);
        if (t1 != null && t2 != null && t1.ehChave(campo) && t2.ehChave(campo)) {

            int indiceColuna1 = t1.getIndiceColuna(campo);
            int indiceColuna2 = t2.getIndiceColuna(campo);

            int u;

            Registro iteradorRegistro = null;
            String[] valores;
            if (indiceColuna1 != -2 && indiceColuna2 != -2) {
                Registro[] r1 = t1.getRegistros();
                Registro[] r2 = t2.getRegistros();

                for (int indiceValido = 0; indiceValido < t1.getCountRegistros(); indiceValido++) {
                    for (int indiceValido2 = 0; indiceValido2 < t2.getCountRegistros(); indiceValido2++) {
                        if (r1[indiceValido].dados[indiceColuna1].equals(r2[indiceValido2].dados[indiceColuna2])) {
                            total++;
                            for (u = 0; u < r1[indiceValido].dados.length; u++) {
                                // System.out.print(" | "+r1[indiceValido].dados[u]);
                            }
                            for (u = 0; u < r2[indiceValido2].dados.length; u++) {
                                //System.out.print(" | "+r2[indiceValido2].dados[u]);
                            }
                        }
                    }
                }
            } else {
                System.out.println("coluna invalida");
            }
        } else {
            System.out.println("Nomes incorretos ou não existe tabela");
        }
        System.out.println("Tempo de duração :" + (System.currentTimeMillis() - begin));
        System.out.println("Total de registros: " + total);

    }

    /*
        Consulta Inner Join O(n*m) com print com apenas uma coluna a comparar
        para cada registro em t1 busca o equivalente em t2 tal que t1.campo=t2.campo
     */
    public void ConsultaInnerJoinPrint(String tabelaNome, String tabelaNome2, String campo) {
        int total = 0;
        long begin = System.currentTimeMillis();
        Tabela t1 = this.getTabela(tabelaNome);
        Tabela t2 = this.getTabela(tabelaNome2);
        if (t1 != null && t2 != null && t1.ehChave(campo) && t2.ehChave(campo)) {

            for (String c : t1.getColunas()) {
                System.out.print(" | " + c);
            }
            for (String c : t2.getColunas()) {
                System.out.print(" | " + c);
            }
            int indiceColuna1 = t1.getIndiceColuna(campo);
            int indiceColuna2 = t2.getIndiceColuna(campo);

            int u;

            Registro iteradorRegistro = null;
            String[] valores;
            if (indiceColuna1 != -2 && indiceColuna2 != -2) {
                Registro[] r1 = t1.getRegistros();
                Registro[] r2 = t2.getRegistros();
                //para todo registro em t1
                for (int indiceValido = 0; indiceValido < t1.getCountRegistros(); indiceValido++) {
                    for (int indiceValido2 = 0; indiceValido2 < t2.getCountRegistros(); indiceValido2++) {
                        if (r1[indiceValido].dados[indiceColuna1].equals(r2[indiceValido2].dados[indiceColuna2])) {
                            total++;

                            for (u = 0; u < r1[indiceValido].dados.length; u++) {
                                System.out.print(" | " + r1[indiceValido].dados[u]);
                            }
                            for (u = 0; u < r2[indiceValido2].dados.length; u++) {
                                System.out.print(" | " + r2[indiceValido2].dados[u]);
                            }
                            System.out.println();

                        }

                    }
                }
            } else {
                System.out.println("coluna invalida");
            }
        } else {
            System.out.println("Nomes incorretos ou não existe tabela");
        }
        System.out.println("Tempo de duração :" + (System.currentTimeMillis() - begin) + " milliseconds");
        System.out.println("Total de registros: " + total);

    }

    /*
        Consulta Outer Join O(n*m) com print, com apenas uma coluna a comparar,
        para cada registro em t1 busca o equivalente em t2 tal que t1.campo=t2.campo
        exibe o par ou, caso não ocorra match, apenas o valor da tabela1
     */
    public void ConsultaOuterJoinPrint(String tabelaNome, String tabelaNome2, String campo) {
        int total = 0;
        boolean existeJoin;
        long begin = System.currentTimeMillis();
        Tabela t1 = this.getTabela(tabelaNome);
        Tabela t2 = this.getTabela(tabelaNome2);
        if (t1 != null && t2 != null && t1.ehChave(campo) && t2.ehChave(campo)) {

            for (String c : t1.getColunas()) {
                System.out.print(" | " + c);
            }
            for (String c : t2.getColunas()) {
                System.out.print(" | " + c);
            }
            int indiceColuna1 = t1.getIndiceColuna(campo);
            int indiceColuna2 = t2.getIndiceColuna(campo);
            int u;

            Registro iteradorRegistro = null;
            String[] valores;
            if (indiceColuna1 != -2 && indiceColuna2 != -2) {
                Registro[] r1 = t1.getRegistros();
                Registro[] r2 = t2.getRegistros();
                //para todo registro em t1
                for (int indiceValido = 0; indiceValido < t1.getCountRegistros(); indiceValido++) {
                    existeJoin = false;

                    for (int indiceValido2 = 0; indiceValido2 < t2.getCountRegistros(); indiceValido2++) {

                        if (r1[indiceValido].dados[indiceColuna1].equals(r2[indiceValido2].dados[indiceColuna2])) {
                            existeJoin = true;
                            total++;
                            for (u = 0; u < r1[indiceValido].dados.length; u++) {
                                System.out.print(" | " + r1[indiceValido].dados[u]);
                            }
                            for (u = 0; u < r2[indiceValido2].dados.length; u++) {
                                System.out.print(" | " + r2[indiceValido2].dados[u]);
                            }
                            System.out.println();
                        }

                    }
                    if (!existeJoin) {
                        total++;
                        for (u = 0; u < r1[indiceValido].dados.length; u++) {
                            System.out.print(" | " + r1[indiceValido].dados[u]);
                        }
                        System.out.println();
                    }
                }
            } else {
                System.out.println("coluna invalida");
            }
        } else {
            System.out.println("Nomes incorretos ou não existe tabela");
        }
        System.out.println("Tempo de duração :" + (System.currentTimeMillis() - begin) + " milliseconds");
        System.out.println("Total de registros: " + total);
    }

    /*
        Consulta Outer Join O(n*m) sem print, com apenas uma coluna a comparar,
        para cada registro em t1 busca o equivalente em t2 tal que t1.campo=t2.campo
        exibe o par ou, caso não ocorra match, apenas o valor da tabela1
     */
    public void ConsultaOuterJoin(String tabelaNome, String tabelaNome2, String campo) {
        int total = 0;
        boolean existeJoin;
        long begin = System.currentTimeMillis();
        Tabela t1 = this.getTabela(tabelaNome);
        Tabela t2 = this.getTabela(tabelaNome2);
        if (t1 != null && t2 != null && t1.ehChave(campo) && t2.ehChave(campo)) {

            int indiceColuna1 = t1.getIndiceColuna(campo);
            int indiceColuna2 = t2.getIndiceColuna(campo);

            int u;

            Registro iteradorRegistro = null;
            String[] valores;
            if (indiceColuna1 != -2 && indiceColuna2 != -2) {
                Registro[] r1 = t1.getRegistros();
                Registro[] r2 = t2.getRegistros();
                //para todo registro em t1
                for (int indiceValido = 0; indiceValido < t1.getCountRegistros(); indiceValido++) {
                    existeJoin = false;
                    for (int indiceValido2 = 0; indiceValido2 < t2.getCountRegistros(); indiceValido2++) {

                        if (r1[indiceValido].dados[indiceColuna1].equals(r2[indiceValido2].dados[indiceColuna2])) {
                            //valores = new String[r1[indiceValido].dados.length + r2[indiceValido2].dados.length];
                            existeJoin = true;
                            total++;
                            for (u = 0; u < r1[indiceValido].dados.length; u++) {
                                //System.out.print(" | " + r1[indiceValido].dados[u]);
                            }
                            for (u = 0; u < r2[indiceValido2].dados.length; u++) {
                                //System.out.print(" | " + r2[indiceValido2].dados[u]);
                            }

                        }

                    }
                    if (!existeJoin) {
                        total++;
                        for (u = 0; u < r1[indiceValido].dados.length; u++) {
                            //System.out.print(" | " + r1[indiceValido].dados[u]);
                        }
                        //System.out.println();
                    }
                }
            } else {
                System.out.println("coluna invalida");
            }
        } else {
            System.out.println("Nomes incorretos ou não existe tabela");
        }
        System.out.println("Tempo de duração :" + (System.currentTimeMillis() - begin) + " milliseconds");
        System.out.println("Total de registros: " + total);
    }

    /*
        Consulta Inner Join O( nlogn + mlogm + m+n(melhor caso) ) sem print, com apenas uma coluna a comparar,
        ordena-se t1 e t2 em ordem crescente de 'campo', para cada registro em t1 busca o equivalente em t2 tal que t1.campo=t2.campo
        de forma sequencial, armazenando um valor de salto para diminuir o numero de comparações
        exibe o par
     */
    public void mergeJoin(String tabelaNome, String tabelaNome2, String campo) {
        Tabela t1 = this.getTabela(tabelaNome);
        Tabela t2 = this.getTabela(tabelaNome2);

        int u, total = 0;
        String[] campos = new String[1];
        campos[0] = campo;

        if (t1 != null && t2 != null && t1.ordenaColunas(campos) && t2.ordenaColunas(campos)) {

            long begin = System.currentTimeMillis();

            int indiceColuna1 = t1.getIndiceColuna(campo);
            int indiceColuna2 = t2.getIndiceColuna(campo);
            if (indiceColuna1 != -2 && indiceColuna2 != -2) {

                Registro[] r1 = t1.getRegistros();
                Registro[] r2 = t2.getRegistros();

                int atual = 0;
                int old = 0;

                for (int indiceValido = 0; indiceValido < t1.getCountRegistros(); indiceValido++) {

                    // se o anterior é valido e é diferente do atual posso saltar
                    if (indiceValido - 1 > -1 && !r1[indiceValido - 1].dados[indiceColuna1].equals(r1[indiceValido].dados[indiceColuna1])) {
                        old = atual;
                    } else {
                        atual = old;
                    }

                    while (atual < t2.getCountRegistros() && r1[indiceValido].dados[indiceColuna1].equals(r2[atual].dados[indiceColuna2])) {

                        for (u = 0; u < r1[indiceValido].dados.length; u++) {
                            //System.out.print(" | " + r1[indiceValido].dados[u]);
                        }
                        for (u = 0; u < r2[atual].dados.length; u++) {
                            //System.out.print(" | " + r2[atual].dados[u]);
                        }
                        atual++;
                        total++;
                        //System.out.println();
                    }
                }
                System.out.println("Tempo de duração :" + (System.currentTimeMillis() - begin) + " milliseconds");
                System.out.println("Total de registros: " + total);
            }
        }
    }

    /*
        Consulta Inner Join O( nlogn + mlogm + m+n(melhor caso) ) com print, com apenas uma coluna a comparar,
        ordena-se t1 e t2 em ordem crescente de 'campo', para cada registro em t1 busca o equivalente em t2 tal que t1.campo=t2.campo
        de forma sequencial, armazenando um valor de salto para diminuir o numero de comparações
        exibe o par
     */
    public void mergeJoinPrint(String tabelaNome, String tabelaNome2, String campo) {
        Tabela t1 = this.getTabela(tabelaNome);
        Tabela t2 = this.getTabela(tabelaNome2);

        int u, total = 0;
        String[] campos = new String[1];
        campos[0] = campo;

        if (t1 != null && t2 != null && t1.ordenaColunas(campos) && t2.ordenaColunas(campos)) {

            for (String c : t1.getColunas()) {
                System.out.print(" | " + c);
            }
            for (String c : t2.getColunas()) {
                System.out.print(" | " + c);
            }

            long begin = System.currentTimeMillis();

            t1.ordenaColuna(campo); // O(nlogn)
            t2.ordenaColuna(campo); // O(mlogm)

            int indiceColuna1 = t1.getIndiceColuna(campo);
            int indiceColuna2 = t2.getIndiceColuna(campo);
            if (indiceColuna1 != -2 && indiceColuna2 != -2) {

                Registro[] r1 = t1.getRegistros();
                Registro[] r2 = t2.getRegistros();

                int atual = 0;
                int old = 0;

                for (int indiceValido = 0; indiceValido < t1.getCountRegistros(); indiceValido++) {

                    // se o anterior é valido e é diferente do atual posso saltar
                    if (indiceValido - 1 > -1 && !r1[indiceValido - 1].dados[indiceColuna1].equals(r1[indiceValido].dados[indiceColuna1])) {
                        old = atual;
                    } else {
                        atual = old;
                    }

                    while (atual < t2.getCountRegistros() && r1[indiceValido].dados[indiceColuna1].equals(r2[atual].dados[indiceColuna2])) {

                        for (u = 0; u < r1[indiceValido].dados.length; u++) {
                            System.out.print(" | " + r1[indiceValido].dados[u]);
                        }
                        for (u = 0; u < r2[atual].dados.length; u++) {
                            System.out.print(" | " + r2[atual].dados[u]);
                        }
                        atual++;
                        total++;
                        System.out.println();
                    }
                }
                System.out.println("Tempo de duração :" + (System.currentTimeMillis() - begin) + " milliseconds");
                System.out.println("Total de registros: " + total);
            }
        }
    }

    /*
        Consulta Outer Join O( nlogn + mlogm + m+n(melhor caso) ) com print, com apenas uma coluna a comparar,
        ordena-se t1 e t2 em ordem crescente de 'campo', para cada registro em t1 busca o equivalente em t2 tal que t1.campo=t2.campo
        de forma sequencial, armazenando um valor de salto para diminuir o numero de comparações
        exibe o par ou, se nao houver match, o valor de t1 
     */
    public void mergeOuterJoinPrint(String tabelaNome, String tabelaNome2, String campo) {
        Tabela t1 = this.getTabela(tabelaNome);
        Tabela t2 = this.getTabela(tabelaNome2);

        int u = 0, total = 0;
        boolean primeiro;

        String[] campos = new String[1];
        campos[0] = campo;
        if (t1 != null && t2 != null && t1.ordenaColunas(campos) && t2.ordenaColunas(campos)) {

            for (String c : t1.getColunas()) {
                System.out.print(" | " + c);
            }
            for (String c : t2.getColunas()) {
                System.out.print(" | " + c);
            }

            long begin = System.currentTimeMillis();

            t1.ordenaColuna(campo); // O(nlogn)
            t2.ordenaColuna(campo); // O(mlogm)

            int indiceColuna1 = t1.getIndiceColuna(campo);
            int indiceColuna2 = t2.getIndiceColuna(campo);
            if (indiceColuna1 != -2 && indiceColuna2 != -2) {

                Registro[] r1 = t1.getRegistros();
                Registro[] r2 = t2.getRegistros();

                int atual = 0;
                int old = 0;

                for (int indiceValido = 0; indiceValido < t1.getCountRegistros(); indiceValido++) {

                    primeiro = true;

                    // se o anterior é valido e é diferente do atual posso saltar
                    if (indiceValido - 1 > -1 && !r1[indiceValido - 1].dados[indiceColuna1].equals(r1[indiceValido].dados[indiceColuna1])) {
                        old = atual;
                    } else {
                        atual = old;
                    }

                    while (atual < t2.getCountRegistros() && r1[indiceValido].dados[indiceColuna1].equals(r2[atual].dados[indiceColuna2])) {

                        primeiro = false;
                        for (u = 0; u < r1[indiceValido].dados.length; u++) {
                            System.out.print(" | " + r1[indiceValido].dados[u]);
                        }
                        for (u = 0; u < r2[atual].dados.length; u++) {
                            System.out.print(" | " + r2[atual].dados[u]);
                        }
                        atual++;
                        total++;
                        System.out.println();
                    }

                    if (primeiro) {
                        for (u = 0; u < r1[indiceValido].dados.length; u++) {
                            System.out.print(" | " + r1[indiceValido].dados[u]);
                        }
                        total++;
                        System.out.println();
                    }
                }
                System.out.println("Tempo de duração :" + (System.currentTimeMillis() - begin) + " milliseconds");
                System.out.println("Total de registros: " + total);
            }
        }
    }

    /*
        Consulta Outer Join O( nlogn + mlogm + m+n(melhor caso) ) sem print, com apenas uma coluna a comparar,
        ordena-se t1 e t2 em ordem crescente de 'campo', para cada registro em t1 busca o equivalente em t2 tal que t1.campo=t2.campo
        de forma sequencial, armazenando um valor de salto para diminuir o numero de comparações
     */
    public void mergeOuterJoin(String tabelaNome, String tabelaNome2, String campo) {
        Tabela t1 = this.getTabela(tabelaNome);
        Tabela t2 = this.getTabela(tabelaNome2);

        int u = 0, total = 0;
        boolean primeiro;

        String[] campos = new String[1];
        campos[0] = campo;
        if (t1 != null && t2 != null && t1.ordenaColunas(campos) && t2.ordenaColunas(campos)) {

            long begin = System.currentTimeMillis();

            t1.ordenaColuna(campo); // O(nlogn)
            t2.ordenaColuna(campo); // O(mlogm)

            int indiceColuna1 = t1.getIndiceColuna(campo);
            int indiceColuna2 = t2.getIndiceColuna(campo);
            if (indiceColuna1 != -2 && indiceColuna2 != -2) {

                Registro[] r1 = t1.getRegistros();
                Registro[] r2 = t2.getRegistros();

                int atual = 0;
                int old = 0;

                for (int indiceValido = 0; indiceValido < t1.getCountRegistros(); indiceValido++) {

                    primeiro = true;

                    // se o anterior é valido e é diferente do atual posso saltar
                    if (indiceValido - 1 > -1 && !r1[indiceValido - 1].dados[indiceColuna1].equals(r1[indiceValido].dados[indiceColuna1])) {
                        old = atual;
                    } else {
                        atual = old;
                    }

                    while (atual < t2.getCountRegistros() && r1[indiceValido].dados[indiceColuna1].equals(r2[atual].dados[indiceColuna2])) {

                        primeiro = false;
                        for (u = 0; u < r1[indiceValido].dados.length; u++) {
                            //System.out.print(" | " + r1[indiceValido].dados[u]);
                        }
                        for (u = 0; u < r2[atual].dados.length; u++) {
                            //System.out.print(" | " + r2[atual].dados[u]);
                        }
                        atual++;
                        total++;
                        //System.out.println();
                    }

                    if (primeiro) {
                        for (u = 0; u < r1[indiceValido].dados.length; u++) {
                            //System.out.print(" | " + r1[indiceValido].dados[u]);
                        }
                        total++;
                        //System.out.println();
                    }
                }
                System.out.println("Tempo de duração :" + (System.currentTimeMillis() - begin) + " milliseconds");
                System.out.println("Total de registros: " + total);
            }
        }
    }

    public void mergeJoinMultiplosCampos(String tabelaNome, String tabelaNome2, String[] splitted) {
        Tabela t1 = this.getTabela(tabelaNome);
        Tabela t2 = this.getTabela(tabelaNome2);

        // se as tabelas existem, ordene-as pelo conjunto de campos enviado
        if (t1 != null && t2 != null && t1.ordenaColunas(splitted) && t2.ordenaColunas(splitted)) {
            long begin = System.currentTimeMillis();

            Registro[] r1 = t1.getRegistros();
            Registro[] r2 = t2.getRegistros();

            String compare1;
            String compare2;

            int atual = 0;
            int old = 0;
            int coluna;

            //escreva os nomes das colunas
            for (String c : t1.getColunas()) {
                System.out.print(" | " + c);
            }
            for (String c : t2.getColunas()) {
                System.out.print(" | " + c);
            }

            /// Consulta os indices das colunas definidas pelo usuario e armazena em dois vetores(col1 e col2)
            int[] col1 = new int[splitted.length];
            int[] col2 = new int[splitted.length];
            int indiceColuna;
            int i = 0;
            for (int k = 0; k < splitted.length; k++) {

                indiceColuna = t1.getIndiceColuna(splitted[k]);
                if (indiceColuna != -2 && t1.ehChave(splitted[k])) {
                    col1[i] = indiceColuna;
                    i++;
                } else {
                    System.out.println(splitted[k] + " não é chave");
                    return;
                }
            }
            i = 0;
            for (int k = 0; k < splitted.length; k++) {
                indiceColuna = t2.getIndiceColuna(splitted[k]);
                if (indiceColuna != -2 && t2.ehChave(splitted[k])) {
                    col2[i] = indiceColuna;
                    i++;
                } else {
                    System.out.println(splitted[k] + " não é chave");
                    return;
                }
            }
            System.out.println();
            /// fim da consulta por indices

            int u;
            int total = 0;
            String compareAnterior = "";

            // para cada registro em t1
            for (int indiceValido = 0; indiceValido < t1.getCountRegistros(); indiceValido++) {

                // armazena o valor do registro atual para comparar
                compare1 = "";
                for (coluna = 0; coluna < col1.length; coluna++) {
                    compare1 += r1[indiceValido].dados[coluna];
                }

                // se o anterior é valido e é diferente do atual posso saltar
                // na primeira execução nao entra aqui pois indice-1 = -1
                if (indiceValido - 1 > -1) {
                    if (!compareAnterior.equals(compare1)) {
                        old = atual;
                    } else {
                        atual = old;
                    }
                } else {
                    atual = old;
                }

                // armazena o valor do registro anterior em t1 para testar salto
                compareAnterior = compare1;

                // armazena o valor de comparação do registro em quetão na tabela t2
                compare2 = "";
                for (coluna = 0; coluna < col2.length; coluna++) {
                    // se o atual é maior que a qtd de registros em t2,
                    // entao acabou o algoritmo,
                    // pois esta em indice maior em r2 e o restante em r1 não tem possibilidade de match
                    if (atual >= t2.getCountRegistros()) {
                        System.out.println("Tempo de duração :" + (System.currentTimeMillis() - begin) + " milliseconds");
                        System.out.println("Total de registros: " + total);
                        return;
                    }
                    compare2 += r2[atual].dados[coluna];
                }

                // enquanto meu indice atual é valido e r1.chaves=r2.chaves
                while (atual < t2.getCountRegistros() && compare1.equals(compare2)) {

                    //escreve ambos
                    for (u = 0; u < r1[indiceValido].dados.length; u++) {
                        //System.out.print(" | " + r1[indiceValido].dados[u]);
                    }
                    for (u = 0; u < r2[atual].dados.length; u++) {
                        //System.out.print(" | " + r2[atual].dados[u]);
                    }
                    atual++;
                    total++;
                    //System.out.println();

                    // atualiza o valor de comparação para o proximo registro
                    // em t2, caso o indice seja válido
                    if (atual < t2.getCountRegistros()) {
                        compare2 = "";
                        for (coluna = 0; coluna < col2.length; coluna++) {
                            compare2 += r2[atual].dados[coluna];
                        }
                    }
                }
            }
            System.out.println("Tempo de duração :" + (System.currentTimeMillis() - begin) + " milliseconds");
            System.out.println("Total de registros: " + total);
        }
    }
}
