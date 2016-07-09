/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.Scanner;

/**
 *
 * @author TheHybrid
 */
public class Answer {
    private String text;
    private Person person_who_answered;

    public Person getPerson_who_answered() {
        return person_who_answered;
    }

    public int getScore() {
        return score;
    }
    private int score;

    public Answer(String text, Person person_who_answered) {
        this.text = text;
        this.person_who_answered = person_who_answered;
        score = -1;
    }
    
    public void setScore(int score){
        if (score<0 || score >5){
            System.err.println("the score is not in the correct range.");
            return;
        }
            
        this.score = score;
    }
    
    public void printAndGetScore() {
        Scanner sc = new Scanner(System.in);
        String user_input;
        int score;
        person_who_answered.printlnName();
        System.out.println(text);
        if (this.score == -1 ){
            System.out.println("do you wnat to give score to this answer?(yes or no)");
            user_input = sc.next();
            if (user_input.equalsIgnoreCase("no"))
                return;
            else if (user_input.equalsIgnoreCase("yes")){
                System.out.println("please give score in range 0 to 5");
                score = sc.nextInt();
                setScore(score);
                System.out.println("score stored successfully.");
        }
            
        }
        
    }
    
}
