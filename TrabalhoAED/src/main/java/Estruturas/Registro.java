/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas;

import java.util.ArrayList;

/**
 *
 * @author nicolasferranti
 */
public class Registro {
    
    public String []dados;
    public String id;
    
    public Registro(String identificador, String []valores){
        this.id = identificador;
        this.dados = valores;
    }
    
}
