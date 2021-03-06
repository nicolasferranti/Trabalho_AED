/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcoes;

import Estruturas.Registro;

/**
 *
 * @author nicolasferranti
 */
public class MergeSort {

    private Registro[] array;
    private Registro[] tempMergArr;
    private int length;

    public void Sort(Registro inputArr[], int tam) {
        this.array = inputArr;
        this.length = tam;
        this.tempMergArr = new Registro[length];
        doMergeSort(0, length - 1);
    }

    private void doMergeSort(int lowerIndex, int higherIndex) {

        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            doMergeSort(lowerIndex, middle);
            doMergeSort(middle + 1, higherIndex);
            mergeParts(lowerIndex, middle, higherIndex);
        }
    }

    private void mergeParts(int lowerIndex, int middle, int higherIndex) {

        for (int i = lowerIndex; i <= higherIndex; i++) {
            tempMergArr[i] = array[i];
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            if (tempMergArr[i].id.compareTo(tempMergArr[j].id) <= 0) {
                array[k] = tempMergArr[i];
                i++;
            } else {
                array[k] = tempMergArr[j];
                j++;
            }
            k++;
        }
        while (i <= middle) {
            array[k] = tempMergArr[i];
            k++;
            i++;
        }

    }

    public void Sort(Registro inputArr[], int tam, int index) {
        if (index > -1 && index < inputArr[0].dados.length) {
            this.array = inputArr;
            this.length = tam;
            this.tempMergArr = new Registro[length];
            doMergeSort(0, length - 1, index);
        } else {
            System.out.println("O valor não é um indice válido");
        }
    }

    private void doMergeSort(int lowerIndex, int higherIndex, int indexToSort) {

        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            doMergeSort(lowerIndex, middle, indexToSort);
            doMergeSort(middle + 1, higherIndex, indexToSort);
            mergeParts(lowerIndex, middle, higherIndex, indexToSort);
        }
    }

    private void mergeParts(int lowerIndex, int middle, int higherIndex, int compareIndex) {

        for (int i = lowerIndex; i <= higherIndex; i++) {
            tempMergArr[i] = array[i];
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            if (tempMergArr[i].dados[compareIndex].compareTo(tempMergArr[j].dados[compareIndex]) <= 0) {
                array[k] = tempMergArr[i];
                i++;
            } else {
                array[k] = tempMergArr[j];
                j++;
            }
            k++;
        }
        while (i <= middle) {
            array[k] = tempMergArr[i];
            k++;
            i++;
        }

    }

    public void SortMultiplosCampos(Registro inputArr[], int tam, int[] index) {
        this.array = inputArr;
        this.length = tam;
        this.tempMergArr = new Registro[length];
        doMergeSortMultiplos(0, length - 1, index);

    }

    private void doMergeSortMultiplos(int lowerIndex, int higherIndex, int[] indexToSort) {

        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            doMergeSortMultiplos(lowerIndex, middle, indexToSort);
            doMergeSortMultiplos(middle + 1, higherIndex, indexToSort);
            mergeParts(lowerIndex, middle, higherIndex, indexToSort);
        }
    }

    private void mergeParts(int lowerIndex, int middle, int higherIndex, int[] compareIndex) {
        String compare1 = "";
        String compare2 = "";

        for (int i = lowerIndex; i <= higherIndex; i++) {
            tempMergArr[i] = array[i];
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            for (int coluna = 0; coluna < compareIndex.length; coluna++) {
                compare1 += tempMergArr[i].dados[coluna];
                compare2 += tempMergArr[j].dados[coluna];
            }
            
            if (compare1.compareTo(compare2) <= 0) {
                array[k] = tempMergArr[i];
                i++;
            } else {
                array[k] = tempMergArr[j];
                j++;
            }
            k++;
            compare1 = "";
            compare2 = "";
        }
        while (i <= middle) {
            array[k] = tempMergArr[i];
            k++;
            i++;
        }

    }
}
