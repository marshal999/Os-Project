/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author TheHybrid
 */
public enum NotificationType {
    NEW_QUESTION,NEW_RESPOND;
    
    @Override
    public String toString(){
        if (this==NEW_QUESTION)
            return "New Question";
        return "New Answer";
    }
}


