/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author TheHybrid
 */
public class Person implements Comparable<Person>{
    
    private final int number;
    private final TreeSet<Person> friends;
    private final TreeSet<Interest> interests;
    private final ArrayList<Question> questions_to_answer;
    private final ArrayList<Question> asked_questions;
    private final ArrayList<Notification> notifications;
    
    public Person(int number){
        this.number = number;
        friends = new TreeSet<>();
        interests = new TreeSet<>();
        questions_to_answer = new ArrayList<>();
        asked_questions = new ArrayList<>();
        notifications = new ArrayList<>();
    }
    
    public void clearNotifications(){
        notifications.clear();
    }
    
    public void addNotification(Notification notification){
        notifications.add(notification);
    }
    public void addInterest(int interest){
        interests.add(Interest.getValueFromInteger(interest));
    }
    
    public void addQuestionToAnswer(Question question){
        questions_to_answer.add(question);
    }
    
    public void addToAskedQuestions(Question question){
        asked_questions.add(question);
    }
    
    public ArrayList<Question> getQuestionsToAnswer(){
        return questions_to_answer;
    }
    
    public ArrayList<Question> getAskedQuestions(){
        return asked_questions;
    }
    
    public Boolean isInterestedIn(Interest interest){
        return interests.contains(interest);
    }
    
    public void addFriend(Person friend){
        friends.add(friend);
    }
    
    public static ArrayList<Person> getPersons(BufferedReader in,BufferedWriter out){
        //Scanner in = new Scanner(System.in);
        int number_of_persons;
        String user_input;
        Person temp;
        ArrayList<Person> result = new ArrayList<>();
        try{
            out.write("how many peoples do you want to add?");
            out.newLine();
            out.flush();
            number_of_persons = Integer.valueOf(in.readLine());
            // create person's--------------------------------------------------
            for (int i=0;i<number_of_persons;i++){
                temp = new Person(i+1);
                result.add(temp);
            }
            // get person's interests
            out.write("please choose persons interests.");
            out.newLine();
            out.flush();
            Interest.printInterest(out);
            out.write("enter end to go to next person.");
            out.newLine();
            out.flush();
            for (int i=0;i<number_of_persons;){
                out.write("person " + (i+1) + " :");
                out.flush();
                user_input = in.readLine();
                if (user_input.equalsIgnoreCase("end")){
                    i++;
                }
                else {
                    int choosen_interest = Integer.valueOf(user_input);
                    if (choosen_interest<=Interest.getTotalInterestNumber())
                        result.get(i).addInterest(choosen_interest);
                    else {
                        System.err.println("wrong entry");
                    }
                }
            }
            // get friendship relationships-------------------------------------
            out.write("choose person's who have friendship.");
            out.newLine();
            out.write("notice that friendship is bilateral.");
            out.newLine();
            out.write("enter end when you have finished.");
            out.newLine();
            out.flush();
            int first_person,second_person;
            while (true){
                out.write("enter first person's number: ");
                out.flush();
                user_input = in.readLine();
                if (user_input.equalsIgnoreCase("end"))
                    break;
                first_person = Integer.valueOf(user_input);
                out.write("enter second person's number: ");
                out.flush();
                user_input = in.readLine();
                if (user_input.equalsIgnoreCase("end"))
                    break;
                second_person = Integer.valueOf(user_input);
                if (first_person>number_of_persons || second_person > number_of_persons)
                    System.err.println("wrong number!");
                result.get(first_person-1).addFriend(result.get(second_person-1));
                result.get(second_person-1).addFriend(result.get(first_person-1));
            }
            
        }catch(InputMismatchException | NumberFormatException e){
            System.err.println("wrong entry");
        }catch (IOException e){
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    
    
    public int getNumber(){
        return number;
    }
    
    public void print(){
        System.out.println("person " + number + ":");
        System.out.println("person's interest :");
        for (final Interest temp :interests)
            System.out.println(temp.toString());
        System.out.println("friend list:");
        for(final Person temp:friends)
            System.out.println("person " + temp.getNumber());
        System.out.println("----------------------------");
    }

    public void printlnName(){
        System.out.println("person " + number + ":");
    }
    
    public void printName(){
        System.out.print("person " + number + ":");
    }
    
    @Override
    public int compareTo(Person o) {
        if (number<o.getNumber())
            return -1;
        else if (number == o.getNumber())
            return 0;
        else 
            return 1;
    }
    
    public TreeSet<Person> answeringCandidates(Question question){
        TreeSet<Person> candidates = new TreeSet<>();
        for (final Person temp:friends)
            if (temp.isInterestedIn(question.getCatagory()))
                candidates.add(temp);
        if (candidates.isEmpty())
            System.out.println("Sorry :(\nnone of your friends can help you at the moment");
        else {
                System.out.println("there was a  " + candidates.size() + " candidates to answer your question");
            if (question.getPriority() == 1){
                System.out.println("because of the high priority of question, your question will be asked from all the candidates");
            }
            else{
                System.out.println("your question will be asked from the best candidate");
                Person best = getBestAnsweringCandiate(candidates);
                candidates.removeAll(candidates);
                candidates.add(best);
            }
        }
        return candidates;
    }
    
    private Person getBestAnsweringCandiate(TreeSet<Person> candidates){
        int max_candidate_score = -1;
        Person best_candidate = null;
        for (final Person temp:candidates)
            if (getRelationScoreTwoards(temp)>max_candidate_score){
                best_candidate = temp;
                max_candidate_score = getRelationScoreTwoards(temp);
            }
                
        return best_candidate;
    }
    
   public int getRelationScoreTwoards(Person friend){
       int score = 0;
       for (final Question temp: asked_questions)
           score += temp.getScoreOfReplier(friend);
       return score;
   }
    
    public void printQustionsToanswer(){
        for (final Question temp : questions_to_answer)
            temp.printToAnswer();
    }
    
    public void printAskedQustions(){
        if (asked_questions.isEmpty()){
            System.out.println("you have not asked a question.");
            return;
        }
        for (int i=0;i<asked_questions.size();i++){
            System.out.print((i+1) + ")");
            asked_questions.get(i).printToOwner();
        }
    }

    public void answerToQuestion() {
        int question_number;
        String answer;
        Scanner sc = new Scanner(System.in);
        for (int i=0;i<questions_to_answer.size();i++){
            System.out.print((i+1) + "}");
            questions_to_answer.get(i).printToAnswer();
        }
        System.out.println("which question do you want to answer?");
        question_number = sc.nextInt();
        if (question_number>questions_to_answer.size()){
            System.err.println("there is no such question.");
            return;
        }
        System.out.println("enter your answer.");
        sc.useDelimiter("\n");
        answer = sc.next();
        System.out.println("your answer was saved successfully.\nthank you :)");
        questions_to_answer.get(question_number-1).addAnswer(new Answer(answer, this));
        questions_to_answer.get(question_number-1).getAsker().addNotification(new Notification(NotificationType.NEW_RESPOND, 
                "there is new respond form person " + number + " to your question"));
        questions_to_answer.remove(question_number-1);
    }
    
    public void printNotifications(){
        if (notifications.isEmpty())
            return;
        System.out.println("Notifications");
        for (final Notification temp:notifications)
            temp.print();
    }
}
