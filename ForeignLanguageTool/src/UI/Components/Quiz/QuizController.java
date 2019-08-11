/*
File: QuizController.java
Author: Ethan Swistak
Date: Jul 25, 2019
Purpose:
*/
package UI.Components.Quiz;

import DataModel.DTO.Card;
import Logic.ActualAPI;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;




public class QuizController  implements Initializable{
    
    //Methods:
    //Initialize GUI screen
    //Forward
    //Back
    //Mark Correct-retrieve that card's object and increment timesCorrect->
    //Mark Incorrect-retrive that card's object and increment timesCorrect->call updateCard(Card yourcard);
    //private StringProperty firstNameString = new SimpleStringProperty();
    private List cardList;
    private Card cardSel;
    private String ownerDoc;
    private String hint;
    private String note;
    private String generic;
    private String otherTrans;
    private String partSp;
    private int numCorr;
    private int numIncorr;
    private String trans;
    private String word;
    private boolean frontOrNot;
    private int thisCard;
    private int totalCorrect;
    private int totalIncorrect;

    /**
     * Accepts the cards and stores them to specific instance variables
     * 
     * @param cards
     * 
     */
    public QuizController(List<Card> cards) {
        //firstNameString.set(firstName);
        cardList = cards;
    }    
    @FXML

    private Text cardNum;
    @FXML

    private Text numIncorrect;
    @FXML

    private Text numCorrect;
    @FXML
    
    WebView cardView;
    private WebEngine webEngine;
    
    String front;
    String back;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        frontOrNot = true;
        thisCard = 0;
        totalCorrect = 0;
        totalIncorrect = 0;
        getCardDetails(thisCard);
        cardNum.setText((thisCard+1)+"/"+cardList.size());
        numCorrect.setText(String.valueOf(totalCorrect));
        numIncorrect.setText(String.valueOf(totalIncorrect));
        // URL quiz = this.getClass().getResource("/UI/languageQuiz.html");
        webEngine = cardView.getEngine();
        // webEngine.load(quiz.toString());
        webEngine.loadContent(front);
        webEngine.setUserStyleSheetLocation(getClass().getClassLoader().getResource("UI/Components/Quiz/quiz.css").toString());
    }    

    @FXML
    
    private void nextCard(ActionEvent event) {

        System.out.println("nextCard");
        if(thisCard<cardList.size()-1){
            thisCard++;
        }else{
            thisCard=0;
        }
        cardNum.setText((thisCard+1)+"/"+cardList.size());
        getCardDetails(thisCard);
        webEngine.loadContent(front);
        frontOrNot=true;
    }
    @FXML

    private void prevCard(ActionEvent event) {

        System.out.println("prevCard");
        if(thisCard>0){
            thisCard--;
        }else{
            thisCard=cardList.size()-1;
        }
        cardNum.setText((thisCard+1)+"/"+cardList.size());
        getCardDetails(thisCard);
        webEngine.loadContent(front);
        frontOrNot=true;
    }
    @FXML

    private void flipCard(ActionEvent event) {

        System.out.println("flipCard");
        if(frontOrNot){
            webEngine.loadContent(back);
            frontOrNot=false;
        }else{
            webEngine.loadContent(front);
            frontOrNot=true;
        }
    }
    @FXML

    private void markCorrect(ActionEvent event) {

        System.out.println("correct");
        cardSel.setTimesCorrect(numCorr+1);
        ActualAPI.getInstance().updateCard(cardSel);
        //cardSel.update();
        totalCorrect++;
        numCorrect.setText(String.valueOf(totalCorrect));
        getCardDetails(thisCard);
        showCurrentSide();
    }
    @FXML

    private void markIncorrect(ActionEvent event) {

        System.out.println("incorrect");
        cardSel.setTimesIncorrect(numIncorr+1);
        ActualAPI.getInstance().updateCard(cardSel);
        //cardSel.update();
        totalIncorrect++;
        numIncorrect.setText(String.valueOf(totalIncorrect));
        getCardDetails(thisCard);
        showCurrentSide();
    }
    private void getCardDetails(int cardNum){
        cardSel = (Card) cardList.get(cardNum);
        ownerDoc = ActualAPI.getInstance().getDoc(cardSel).getTitle();
        hint = cardSel.getHint();
        note = cardSel.getCardNote();
        generic = cardSel.getGeneric();
        otherTrans = cardSel.getOtherTrans();
        partSp = cardSel.getPartOfSpeech();
        numCorr = cardSel.getTimesCorrect();
        numIncorr = cardSel.getTimesIncorrect();
        trans = cardSel.getTransInContext();
        word = cardSel.getWordAsAppears();
        front = "<!DOCTYPE html>" +
        "<html>" +
            "<head>" +
                "<link rel='stylesheet' href='quiz.css'>" +
            "</head>"+
            "<body>"+
                "<div>"+
                    "<div class='header'>"+
                        "<div id='source' class='left'>"+ownerDoc+"</div>"+
                        "<div class='right'>"+
                            "<div id='correct'>"+numCorr+"</div>"+
                            "<div id='incorrect'>"+numIncorr+"</div>"+
                        "</div>"+
                    "</div>"+
                    "<div class='main'><strong>" + word + "</strong></div>"+
                    "<div class='footer'>Hint: " + hint + "</div>"+
                "</div>"+
            "</body>"+
        "</html>";
        back = "<!DOCTYPE html>" +
        "<html>" +
            "<head>" +
                "<link rel='stylesheet' href='quiz.css'>" +
            "</head>"+
            "<body>"+
                "<div>"+
                    "<div class='header'>"+
                        "<div id='source' class='left'>"+ownerDoc+"</div>"+
                        "<div class='right'>"+
                            "<div id='correct'>"+numCorr+"</div>"+
                            "<div id='incorrect'>"+numIncorr+"</div>"+
                        "</div>"+
                    "</div>"+
                    "<div class='main'>"+
                        "<span id='spType'>"+partSp+" </span>"+
                        "<span id='def'>"+trans+"</span>"+
                    "</div>"+
                    "<div class='footer'>"+note+"</div>"+
                "</div>"+
            "</body>"+
        "</html>";
    }
    private void showCurrentSide(){
        if(frontOrNot){
            webEngine.loadContent(front);
        }else{
            webEngine.loadContent(back);
        }
    }
}
