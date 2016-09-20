/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_package;
//
/**
 *
 * @author main4db
 */
public class Jogador {
    String nome;
    String data;
    float tempo;
    
    public Jogador(String nome,String data,float tempo){
        this.nome=nome;
        this.data=data;
        this.tempo=tempo;
    }
    
        public Jogador(){
    }

    
    public String getNome(){return nome;}
    public String getData(){
        
        return data;}
    public float getTempo(){return tempo;}
    
    public void setNome(String nome){this.nome=nome;}
    public void setData(String data){this.data=data;}
    public void setTempo(float tempo){this.tempo=tempo;}
}
