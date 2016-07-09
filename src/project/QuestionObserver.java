/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.ArrayList;
import java.util.TreeSet;
import rx.Observer;

/**
 *
 * @author TheHybrid
 */
public class QuestionObserver implements Observer<Question>{

    @Override
    public void onCompleted() {
        System.out.println("observer is terminating");    
    }

    @Override
    public void onError(Throwable thrwbl) {
        System.err.println("Some thing went wrong :(");
    }

    @Override
    public void onNext(Question t) {
        t.getAsker().addToAskedQuestions(t);
        TreeSet<Person> candidates = t.getAsker().answeringCandidates(t);
        t.setCandidates(candidates);
        for(final Person temp:candidates){
            temp.addQuestionToAnswer(t);
            temp.addNotification(new Notification(NotificationType.NEW_QUESTION, "there is a new question from "
                    + "person " + t.getAsker().getNumber() + "."));
        }
            
            
    }
    
}
