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
    private int auxJoinCount;
    private Tabela tAux;

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
        this.auxJoinCount = 0;
        this.tAux = null;
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

    private void auxJoin(String tabelaNome, String tabelaNome2, String campo) {
        Tabela t1 = this.getTabela(tabelaNome);
        Tabela t2 = this.getTabela(tabelaNome2);
        tAux = null;
        if (t1 != null && t2 != null && t1.ehChave(campo) && t2.ehChave(campo)) {
            ArrayList<String> colunas = new ArrayList<>();
            for (String c : t1.getColunas()) {
                colunas.add(c);
            }
            for (String c : t2.getColunas()) {
                colunas.add(c);
            }
            tAux = new Tabela("temp", colunas);

            int indiceColuna1 = t1.getIndiceColuna(campo);
            int indiceColuna2 = t2.getIndiceColuna(campo);
            //System.out.println("indice do campo em " + tabelaNome + " :" + indiceColuna1);
            //Registro[] conjuntoFinal = new Registro[t1.getCountRegistros() + t2.getCountRegistros()];
            this.auxJoinCount = 0;
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

                            valores = new String[r1[indiceValido].dados.length + r2[indiceValido2].dados.length];
                            for (u = 0; u < r1[indiceValido].dados.length; u++) {
                                valores[u] = r1[indiceValido].dados[u];
                            }
                            for (u = 0; u < r2[indiceValido2].dados.length; u++) {
                                valores[u + r1[indiceValido].dados.length] = r2[indiceValido2].dados[u];
                            }
                            Registro novo = new Registro(r1[indiceValido].id, valores);
                            tAux.inserirRegistro(novo);
                            //conjuntoFinal[this.auxJoinCount] = novo;
                            this.auxJoinCount++;
                        }

                    }
                    //                    iteradorRegistro = t2.buscarRegistro(r1[indiceValido].dados[indiceColuna1], campo);
                    //
                    //                    if (iteradorRegistro != null) {
                    //
                    //                        valores = new String[r1[indiceValido].dados.length + iteradorRegistro.dados.length];
                    //                        for (u = 0; u < r1[indiceValido].dados.length; u++) {
                    //                            valores[u] = r1[indiceValido].dados[u];
                    //                        }
                    //                        for (u = 0; u < iteradorRegistro.dados.length; u++) {
                    //                            valores[u + r1[indiceValido].dados.length] = iteradorRegistro.dados[u];
                    //                        }
                    //                        Registro novo = new Registro(null, valores);
                    //                        conjuntoFinal[i] = novo;
                    //                        i++;
                    //                    }
                }
                //return conjuntoFinal;
            } else {
                System.out.println("coluna invalida");
            }
        } else {
            System.out.println("Nomes incorretos ou não existe tabela");
        }
        //return null;
    }

    public void ConsultaInnerJoin(String tabelaNome, String tabelaNome2, String campo) {
        this.auxJoin(tabelaNome, tabelaNome2, campo);
        if (this.tAux != null) {
            this.tAux.printTabela();
        }
        System.out.println("total :" + this.tAux.getCountRegistros());
//        Registro[] conjuntoFinal = this.auxJoin(tabelaNome, tabelaNome2, campo);
//        if (conjuntoFinal != null) {
//            Tabela t1 = this.getTabela(tabelaNome);
//            Tabela t2 = this.getTabela(tabelaNome2);
//
//            for (String coluna : t1.getColunas()) {
//                System.out.print(coluna + " | ");
//            }
//            for (String coluna : t2.getColunas()) {
//                System.out.print(coluna + " | ");
//            }
//            System.out.println();
//            for (int k = 0; k < this.auxJoinCount; k++) {
//                for (int j = 0; j < conjuntoFinal[k].dados.length; j++) {
//                    System.out.print(conjuntoFinal[k].dados[j] + " |");
//                }
//                System.out.println();
//            }
//        }
    }

    public void ConsultaOuterJoin(String tabelaNome, String tabelaNome2, String campo) {
        this.auxJoin(tabelaNome, tabelaNome2, campo);
        if (this.tAux != null) {
            Tabela t1 = this.getTabela(tabelaNome);
            for (int i = 0; i < t1.getCountRegistros(); i++) {
                if (this.tAux.buscarRegistro(t1.getRegistros()[i].id) == null) {
                    this.tAux.inserirRegistro(t1.getRegistros()[i]);
                }
            }
            this.tAux.printTabela();
            System.out.println("total :" + this.tAux.getCountRegistros());

        }
    }
}
