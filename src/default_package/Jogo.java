/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_package;

/**
 *
 * @author main4db
 */
public class Jogo {
    Jogador jogador;
    int jogada;
    boolean completo;
    
    public Jogo(Jogador jogador,int jogada,boolean completo){
        this.jogador=jogador;
        this.jogada=jogada;
        this.completo=completo;
    }
    public Jogador getJogador(){return jogador;}
    public int getJogada(){return jogada;}
    public boolean getcompleto(){return completo;}
    
    public void setJogador(Jogador jogador){this.jogador=jogador;}
    public void setJogada(int jogada){this.jogada=jogada;}
    public void setcompleto (boolean estado){this.completo=completo;}
}
