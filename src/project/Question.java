/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 *
 * @author TheHybrid
 */
public class Question {
    private final String question;
    private final Interest catagory;
    private final int priority;
    private final Person asker;
    private final ArrayList<Answer> answers;
    private  TreeSet<Person> candidates_to_answer;

    public Person getAsker() {
        return asker;
    }
    
    public Question(String question, Person asker , Interest catagory , int priority) {
        this.question = question;
        this.asker = asker;
        this.catagory = catagory;
        this.priority = priority;
        answers = new ArrayList<>();
        candidates_to_answer = new TreeSet<>();
    }

    public String getQuestion() {
        return question;
    }
    
    public int getScoreOfReplier(Person person){
        for (final Answer temp:answers)
            if (temp.getPerson_who_answered() == person)
                return temp.getScore();
        return 0;
    }

    public Interest getCatagory() {
        return catagory;
    }

    public int getPriority() {
        return priority;
    }
    
    public void setCandidates(TreeSet<Person> candidates){
        candidates_to_answer = candidates;
    }
    
    public void printToAnswer(){
        asker.printlnName();
        System.out.println("Field:\t" + catagory.toString() + "\t\tPriority:\t" + priority);
        System.out.println(question);
    }
    public void printToOwner(){
        System.out.println("Field: " + catagory.toString() + "\tPriority: " + priority);
        System.out.println("question: " + question);
        printAnsweringCandidates();
        printAnswers();
    }
    
    private void printAnsweringCandidates(){
        if (candidates_to_answer.isEmpty())
            System.out.println("this question has no answering candidates");
        else {
            System.out.print("candiates:\t");
            for (final Person temp:candidates_to_answer){
                temp.printName();
                System.out.print("\t");
            }
            System.out.println("");
                
        }
    }
    
    private void printAnswers(){
        if (answers.isEmpty())
            System.out.println("this question is not answered yet");
        else {
            System.out.println("answers:");
            for (final Answer temp:answers)
                temp.printAndGetScore();
        }
    }
    
    public void addAnswer(Answer answer){
        answers.add(answer);
    }
    
}
