/*
File: MockAPI.java
Author: Ethan Swistak
Date: Jul 21, 2019
Purpose:
*/

package Logic;

import DataModel.Card;
import DataModel.Doc;
import DataModel.Group;
import DataModel.LanguagePair;
import DataModel.Note;
import DataModel.User;
import java.util.ArrayList;
import java.util.List;


public class MockAPI implements API{
    
    private MockAPI() {
    }
    
    public static MockAPI getInstance() {
        return MockAPIHolder.INSTANCE;
    }

    @Override
    public void updateGroup(Group group) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateDoc(Doc doc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteLangPair(LanguagePair langPair) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteGroup(Group group) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteDoc(Doc doc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteCard(Card card) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteNote(Note note) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Doc getDoc(Card card) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static class MockAPIHolder {

        private static final MockAPI INSTANCE = new MockAPI();
    }

    @Override
    public User getUser() {
        User user = new User();
        user.setName("Ethan");
        user.setNativeLanguage("English");
        return user;
    }

    @Override
    public List<LanguagePair> getLangPair() {
        LanguagePair langPair1 = new LanguagePair();
        langPair1.setNat("English");
        langPair1.setTarget("German");
        LanguagePair langPair2 = new LanguagePair();
        langPair2.setNat("English");
        langPair2.setTarget("Chinese");
        List<LanguagePair> list = new ArrayList<>();
        list.add(langPair2);
        list.add(langPair1);
        return list;
    }

    @Override
    public List<Group> getGroups(LanguagePair langPair) {
        Group group = new Group();
        group.setName("Test Group");
        List<Group> list = new ArrayList<>();
        list.add(group);
        return list;
    }

    @Override
    public List<Doc> getDocuments(Group group) {
        Doc doc = new Doc();
        doc.setTitle("Test");
        doc.setText("Testing Text");
        List<Doc> list = new ArrayList<>();
        list.add(doc);
        return list;
    }

    @Override
    public List<Card> getCards(Doc doc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Note> getNote(Doc doc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Card> getAllCards(LanguagePair langPair) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
        @Override
    public LanguagePair createLangPair(LanguagePair Langpair) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Group createGroup(LanguagePair langPair, Group group) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Doc createDoc(Group group, Doc doc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Card createCard(Doc doc, Card card) {
        System.out.println("Card created");
        return card;
    }

    @Override
    public Note createNote(Doc doc, Note note) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void updateGroup(LanguagePair langPair) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
    public void updateDocument(Doc doc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateCard(Card card) {
        System.out.println("Card Updated");
    }

    @Override
    public void updateNote(Note note) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
