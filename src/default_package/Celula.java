/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_package;
//
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author main4db
 */
public class Celula extends Button{
    
    int pos;
    String num;
    boolean mov, hole;
    
    public Celula(int pos, String num, boolean mov, boolean hole){ //extends button
        this.pos=pos;
        this.mov=mov;
        this.hole=hole;
        this.num=num;
        
    }
     public Celula()
     {
        new Button();
     }
   
    public int getPos(){return pos;}
    public String getNum(){return num;}
    public boolean getMov(){return mov;}
    public boolean getHole(){return hole;}
    
    public void setPos(int pos){
        
        setId(Integer.toString(pos));
        this.pos=pos;
       
    }
    public void setNum(String num){
        
        this.num=num;
        setText(num);
    }
    public void setMov(boolean mov){
        this.mov=mov;
    }
    public void setHole(boolean hole){
        this.hole=hole;
        if(hole==true)
        setText(" ");
    } 

    void setGraphic(ImageView image) {
        setGraphic(image);
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
