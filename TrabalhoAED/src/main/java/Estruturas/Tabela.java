/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import Funcoes.MergeSort;
import Funcoes.QuickSort;
import java.awt.print.Book;
import java.util.ArrayList;

/**
 *
 * @author nicolasferranti
 */
public class Tabela {

    public String nome;
    private Tabela prox = null;

    private ArrayList<String> colunas;
    private boolean[] ehPrimaria;

    private Registro[] registros;
    private int tamanhoInicial;
    private int qtdInserida;
    private int indice;
    private boolean estaOrdenada;

    /*
        Esta variavel armazena qual o indice do ultimo campo que foi escolhido para ordenação
        para fins de otimização, no caso de o ultimo campo ser o id criado a partir das chaves primarias
        indiceUltimaOrdenação = -1
     */
    private int indiceUltimaOrdenação;

    public Tabela(String name, ArrayList<String> collumns) {
        this.nome = name;
        this.colunas = collumns;
        this.ehPrimaria = new boolean[this.colunas.size()];
        this.qtdInserida = 0;
        this.indice = 0;
        this.tamanhoInicial = 1000;
        this.registros = new Registro[tamanhoInicial];
        this.estaOrdenada = false;
        this.indiceUltimaOrdenação = -2;
    }

    private float fatorDeCarga() {
        return ((float) this.qtdInserida / (float) this.tamanhoInicial);
    }

    public void inserirRegistro(Registro r) {
        this.registros[this.indice] = r;
        this.indice++;
        this.qtdInserida++;
        if (this.fatorDeCarga() > 0.99) {
            this.aumentarTabela();
        }
        this.estaOrdenada = false;
    }

    private void aumentarTabela() {
        this.tamanhoInicial = tamanhoInicial * 2;
        Registro[] novoRegistro = new Registro[this.tamanhoInicial];
        for (int i = 0; i < this.indice; i++) {
            novoRegistro[i] = this.getRegistros()[i];
        }
        this.registros = novoRegistro;
    }

    private void ordenarTabela(int coluna) {
        MergeSort ms = new MergeSort();
        //QuickSort q = new QuickSort();
        //q.sort(registros, this.indice);
        if (this.indice > 0) {
            // se for pela chave primária
            if (coluna == -1) {
                ms.Sort(this.registros, this.indice);
                this.estaOrdenada = true;
                this.indiceUltimaOrdenação = -1;
            } else if (coluna > 0 && coluna < this.registros[0].dados.length) {
                ms.Sort(this.registros, this.indice, coluna);
                this.estaOrdenada = true;
                this.indiceUltimaOrdenação = coluna;
            }
        }
    }

    protected int getIndiceColuna(String chave) {
        for (int i = 0; i < this.colunas.size(); i++) {
            if (this.colunas.get(i).equals(chave)) {
                return i;
            }
        }
        return -2;
    }

//    public void ordenaColuna(String coluna) {
//        int indiceColuna = this.getIndiceColuna(coluna);
//        if (indiceColuna != -2) {
//            this.ordenarTabela(indiceColuna);
//        }
//    }

    protected boolean ordenaColunas(String[] colunas) {
        int[] col = new int[colunas.length];
        int indiceColuna, i = 0;
        for (String c : colunas) {
            indiceColuna = this.getIndiceColuna(c);
            if (indiceColuna != -2 && this.ehPrimaria[indiceColuna]) {
                col[i] = indiceColuna;
                i++;
            } else {
                return false;
            }
        }
        MergeSort ms = new MergeSort();
        ms.SortMultiplosCampos(registros, this.indice, col);
        //this.printTabela();
        return true;
    }

    public Registro buscarRegistro(String identificador) {
        if (this.indice == 0) {
            return null;
        }
        if (!this.estaOrdenada || this.indiceUltimaOrdenação != -1) {
            this.ordenarTabela(-1);
        }
        int lo = 0;
        int hi = this.indice;
        while (lo <= hi) {

            int mid = lo + (hi - lo) / 2;
            if (identificador.compareTo(this.registros[mid].id) < 0) {
                hi = mid - 1;
            } else if (identificador.compareTo(this.registros[mid].id) > 0) {
                lo = mid + 1;
            } else {
                return this.getRegistros()[mid];
            }
        }
        return null;
    }

    public Registro buscarRegistro(String identificador, String valorChave) {
        if (this.indice == 0) {
            return null;
        }
        int indiceColuna = this.getIndiceColuna(valorChave);

        if (indiceColuna == -2) {
            System.out.println("Valor chave de coluna invalido");
            return null;
        }
        if (!this.estaOrdenada || this.indiceUltimaOrdenação != indiceColuna) {
            this.ordenarTabela(indiceColuna);
        }

        int lo = 0;
        int hi = this.indice;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (mid > this.indice) {
                return null;
            }
            //System.out.println("mid :"+ mid);
            //System.out.println("Indice :"+ indiceColuna);

            if (identificador.compareTo(this.registros[mid].dados[indiceColuna]) < 0) {
                hi = mid - 1;
            } else if (identificador.compareTo(this.registros[mid].dados[indiceColuna]) > 0) {
                lo = mid + 1;
            } else {
                return this.getRegistros()[mid];
            }
        }
        return null;
    }

    public Registro buscarRegistros(String identificador, String valorChave) {
        if (this.indice == 0) {
            return null;
        }
        int indiceColuna = this.getIndiceColuna(valorChave);

        if (indiceColuna == -2) {
            System.out.println("Valor chave de coluna invalido");
            return null;
        }
        if (!this.estaOrdenada || this.indiceUltimaOrdenação != indiceColuna) {
            this.ordenarTabela(indiceColuna);
        }

        int lo = 0;
        int hi = this.indice;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (mid > this.indice) {
                return null;
            }
            //System.out.println("mid :"+ mid);
            //System.out.println("Indice :"+ indiceColuna);

            if (identificador.compareTo(this.registros[mid].dados[indiceColuna]) < 0) {
                hi = mid - 1;
            } else if (identificador.compareTo(this.registros[mid].dados[indiceColuna]) > 0) {
                lo = mid + 1;
            } else {
                return this.getRegistros()[mid];
            }
        }
        return null;
    }

//    private Registro[] consultaVizinho(int mid, String identificador, int indiceColuna){
//        Registro [] rs = new Registro[50];
//        rs[0] = this.getRegistros()[mid];
//        int i = 50;
//        int esq = mid-1;
//        int dir = mid+1;
//        while( esq >0 && this.registros[esq].dados[indiceColuna].equals(identificador)){
//            rs[]
//        }
//        return null;
//    }
    public void printTabela() {
        for (String col : this.colunas) {
            System.out.print(col + " | ");
        }
        System.out.println();
        for (int i = 0; i < this.indice; i++) {
            for (int j = 0; j < this.registros[i].dados.length; j++) {
                System.out.print(this.registros[i].dados[j] + " | ");
            }
            System.out.println();
        }
    }

    public void removerRegistro(String identificador) {
        int iRemove = -1;
        for (int i = 0; i < this.indice; i++) {
            if (this.registros[i].id.equals(identificador)) {
                iRemove = i;
                break;
            }
        }
        //System.out.println("indice do cara a ser removido :" + iRemove);
        if (iRemove != -1) {
            if (!this.estaOrdenada) {
                if (iRemove != this.indice - 1) {
                    this.registros[iRemove] = this.registros[this.indice - 1];
                }
                this.indice--;
                this.qtdInserida--;
            } else {
                for (int i = iRemove + 1; i < this.indice; i++) {
                    this.registros[i - 1] = this.registros[i];
                }
                this.indice--;
                this.qtdInserida--;
            }

        }
    }

    /**
     * @return the prox
     */
    public Tabela getProx() {
        return prox;
    }

    /**
     * @param next the next to set
     */
    public void setProx(Tabela next) {
        this.prox = next;
    }

    /**
     * @return the colunas size
     */
    public int getTamColunas() {
        return colunas.size();
    }

    /**
     * @return the colunas
     */
    public ArrayList<String> getColunas() {
        return colunas;
    }

    /**
     * @return the registros
     */
    public Registro[] getRegistros() {
        return registros;
    }

    public int getTamanho() {
        return this.tamanhoInicial;
    }

    public int getCountRegistros() {
        return this.indice;
    }

    public void setChavesPrimarias(int[] indicesPrimarios) {
        int i = 0;
        for (int pk : indicesPrimarios) {
            if (i == pk) {
                this.ehPrimaria[pk] = true;
            } else {
                this.ehPrimaria[i] = false;
            }
            i++;
        }
        i = 0;
        for (String c : this.colunas) {
            System.out.print(c + "[" + this.ehPrimaria[i] + "] | ");
            i++;
        }
        System.out.println();
    }

    public boolean ehChave(String coluna) {
        int indiceChave = this.getIndiceColuna(coluna);
        if (indiceChave == -2) {
            return false;
        }
        return this.ehPrimaria[indiceChave];
    }

    public int countCondicional(String coluna, String chave) {
        int indiceColuna = this.getIndiceColuna(coluna);
        if (indiceColuna == -2) {
            return -1;
        }
        int count = 0;
        for (int i = 0; i < this.indice; i++) {
            if (this.registros[i].dados[indiceColuna].equals(chave)) {
                count++;
            }
        }
        return count;
    }
}
