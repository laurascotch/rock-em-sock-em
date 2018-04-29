/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxe;

/**
 *
 * @author Laura
 */
public class Pugile {
    
    int hp;
    
    Pugile(){
        hp = 500;
    }
    
    int getHP(){
        return hp;
    }
    
    void aggiornaHP(int d){
        hp = hp - d;
    }
    
}
