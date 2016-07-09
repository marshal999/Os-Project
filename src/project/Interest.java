/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TheHybrid
 */
public enum Interest {
    COMPUTER,SCIENCE,SPORTS,GENERAL_INFORMATION,POLITICS;
    
    public static void printInterest(BufferedWriter out){
        try {
            out.write("enter 1 for COMPUTER\n");
            out.write("      2 for SCIENCE\n");
            out.write("      3 for SPORTS\n");
            out.write("      4 for GENERAL_INFORMATION\n");
            out.write("      5 for POLITICS\n");
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Interest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static Interest getValueFromInteger(int integer){
        switch(integer){
            case 1:
                return COMPUTER;
            case 2:
                return SCIENCE;
            case 3: 
                return SPORTS;
            case 4: 
                return GENERAL_INFORMATION;
            case 5: 
                return POLITICS;      
        }
        return null;
    }
    
    public static int getTotalInterestNumber(){
        return 5;
    }
}
