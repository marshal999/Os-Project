/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import rx.subjects.PublishSubject;



/**
 *
 * @author TheHybrid
 */
public class Project {

    private static ArrayList<Person> graph_members;
    private static Person current_user = null;

    
    public static void main(String[] args) {
        String user_input;
        Scanner sc = new Scanner(System.in);
        Boolean exit = false;
        populateGraph();
        PublishSubject<Question> ps = PublishSubject.create();
        QuestionObserver question_observer = new QuestionObserver();
        ps.subscribe(question_observer);
        System.out.println("type help to see Possible commands");
        while (!exit){
            try{
                user_input = sc.next();
                user_input = user_input.toLowerCase();
                switch (user_input){
                    case "help":
                        help();
                        break;
                    case "print":
                            printGraph();
                            break;
                    case "login":
                        login(sc.nextInt());
                        break;
                    case "logout":
                        logout();
                        break;
                    case "ask":
                        ps.onNext(askQuestion());
                        break;
                    case "questions":
                        showQuestionList();
                        break;
                    case "answer":
                        answer();
                        break;
                    case "exit":
                       exit = true;
                       break;
                    default:
                        System.err.println("wrong input");
                }
            }catch(InputMismatchException e){
                System.err.println("wrong input");
                System.err.println("type help to see Possible commands!");
            }
        }
    }
    
    private static void printGraph(){
        System.out.println("there is " + graph_members.size() + " people in graph.");
        System.out.println("----------------------------");
        for (final Person temp:graph_members)
            temp.print();
    }
    
    private static void populateGraph(){
        BufferedReader console_reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter console_writer = new BufferedWriter(new OutputStreamWriter(System.out));
        String user_input;
        System.out.println("the friendship graph is empty.");
        System.out.println("how do you want to populate it?\n1. manually\n2. from input file");
        while (true){
            try {
                user_input = console_reader.readLine();
                if (user_input.equalsIgnoreCase("1")){
                    graph_members = Person.getPersons(console_reader,console_writer);
                    break;
                }else if(user_input.equalsIgnoreCase("2")){
                    System.out.println("the graph will be populated from input.txt file");
                    FileInputStream file_input_stream= new FileInputStream("input.txt");
                    FileOutputStream file_output_stram = new FileOutputStream("log.txt");
                    BufferedReader file_reader = new BufferedReader(new InputStreamReader(file_input_stream));
                    BufferedWriter file_writer = new BufferedWriter(new OutputStreamWriter(file_output_stram));
                    graph_members = Person.getPersons(file_reader,file_writer);
                    break;
                }
                else{
                    System.err.println("wrong entry");
                }
                    
            } catch (IOException ex) {
                if (ex instanceof FileNotFoundException)
                    System.err.println("file not find!\nplease check program directory");
                Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }             
        }
        
    }
    
    private static Question askQuestion()throws InputMismatchException{
        if (current_user == null){
            System.err.println("you should login first!");
            return null;
        }
        Scanner sc = new Scanner(System.in);
        String text_of_question;
        Interest field_of_question;
        int priority;
        System.out.println("enter the field of qustion");
        Interest.printInterest(new BufferedWriter(new OutputStreamWriter(System.out)));
        field_of_question = Interest.getValueFromInteger(sc.nextInt());
        System.out.println("enter the priority of your question(1 is highest).");
        priority = sc.nextInt();
        System.out.println("enter the text of your question");
        sc.useDelimiter("\n");
        text_of_question = sc.next();
        return new Question(text_of_question, current_user, field_of_question,priority);
            
    }

    private static void help() {
        System.out.println("help: shows list of possible commands.");
        System.out.println("print: prints all users information plus friendship realtions.");
        System.out.println("login x: logins to user with user number x.");
        System.out.println("logout: logouts from current user.");
        System.out.println("Ask:starts procedure to ask a question.");
        System.out.println("questions: shows list of user's asked questions");
        System.out.println("answer: starts procedure to respond to a question.");
        System.out.println("exit:exits the application.");
    }
    
    private static Boolean login(int user_number){
        if (current_user != null){
            System.err.println("you must logout first!");
            return false;
        }
        if (user_number > graph_members.size()){
            System.err.println("user does not exists!");
            return false;
        }
        current_user = graph_members.get(user_number-1);
        System.out.println("Logged in to user " + current_user.getNumber() + ": ");
        current_user.printNotifications();
        current_user.clearNotifications();
        return true;
    }
    
    private static void logout(){
        if (current_user != null){
            System.out.println("your logout was successfull.");
            current_user = null;
        }
        else 
            System.err.println("you are not currently logged in any user.");
        
        
    }
    
    private static void answer(){
        if (current_user == null){
            System.err.println("you must login first.");
            return;
        }
        current_user.answerToQuestion();
    }
        
       
            
    

    private static void showQuestionList() {
        if (current_user == null){
            System.err.println("you must login first.");
            return;
        }       
        current_user.printAskedQustions();
    }
}
