

package Logic;

import java.util.List;
import DataModel.DTO.User;
import DataModel.DTO.LanguagePair;
import DataModel.DTO.Group;
import DataModel.DTO.Doc;
import DataModel.DTO.Card;
import DataModel.DTO.Item;
import DataModel.DTO.Note;

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
    public Doc getDoc(Card card);

    //Create methods
    public LanguagePair createLangPair(LanguagePair langPair);
    public Group createGroup(LanguagePair langPair, Group group);
    public Doc createDoc(Group group, Doc doc);
    public Card createCard(Doc doc, Card card);
    public Note createNote(Doc doc, Note note);

    //Overloaded
    //public Group createGroup(Group group, LangPair langpair);

    //Update methods
    public void updateGroup(Group group);
    public void updateDoc(Doc doc);
    public void updateCard(Card card);
    public void updateNote(Note note);

    //Delete methods
    public void deleteLangPair(LanguagePair langPair);
    public void deleteGroup(Group group);
    public void deleteDoc(Doc doc);
    public void deleteCard(Card card);
    public void deleteNote(Note note);

}
