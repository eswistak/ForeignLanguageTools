/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.util.List;
import DataModel.User;
import DataModel.LanguagePair;
import DataModel.Group;
import DataModel.Doc;
import DataModel.Card;
import DataModel.Note;

/**
 *
 * @author Hyung Kang
 */
public interface API {
    public User getUser();
    public List<LanguagePair> getLangPair();
    public List<Group> getGroups(LanguagePair langPair);
    public List<Doc> getDocuments(Group group);
    public List<Card> getCards(Doc doc);
    public List<Note> getNote(Doc doc);
    public List<Card> getAllCards(LanguagePair langPair);
}
