/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package default_package;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



/**
 *
 * @author main4db
 */
public class Actions {
    	public static void ExecuteNewGame(){
            
	}
        public static void Validate()
        {
            Alert a = new Alert(AlertType.INFORMATION);
            String[] ints = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14"," "};
            String[] chars= {"A","B","C","D","E","F","G","H","I","J","K","L","M","N"," "};
            String[] Bin={"0001","0010","0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1101","1110"," ",};
            int count=0;
            for(int i=0; i<16; i++)
            {
                if(default_package.Main.arraycelula[i].getNum().equals(ints[i]))
                {
                    count++;
                }
            }
            if(count==16)
            {
               a.setTitle("Ganhou!");
               a.setHeaderText("Completou o puzzle!");
               a.setContentText("Ganhou o jogo em <Insert TIME>");
                a.show();
            }
                        
            count=0;
        }
}
