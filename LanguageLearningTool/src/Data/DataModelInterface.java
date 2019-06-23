/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Ethan Swistak
 */
public interface DataModelInterface {
    
    /**
     * This should be run on startup to load all the data stored to disk into the application
     * @return whether initialization was successful
     */
    public boolean initializeDataModel();
    
    /**
     * Saves the datamodel to disk
     * @return whether save was successful 
     */
    public boolean saveToDisk();
    
    /**
     * Returns all the data associated with a user, which at this time, is all the data there is.
     * @return A treemodel containing all docs and cards associated with a user
     */
    public DefaultTreeModel getUser();
    
    /**
     * 
     * @param path, a path specifying the node requested
     * @return the document requested
     */
    public DefaultMutableTreeNode getDocument(TreePath path);
    
    /**
     * 
     * @param path, path to a particular card set
     * @return all cards that match the path
     */
    public DefaultMutableTreeNode[] getCards(TreePath path);
    
    /**
     * 
     * @param path, where to add the document
     * @return the document added
     */
    public DefaultMutableTreeNode addDocument(TreePath path);
    /**
     * 
     * @param path, adds a card to the given document
     * @return whether add was successful
     */
    public boolean addCard(TreePath path);
    
    /**
     * 
     * @param path, path to the document requesting to be modified
     * @param parameter, the parameter to be changed
     * @return whether update was successful
     */
    public boolean setDocument(TreePath path, String parameter);
    
    /**
     * 
     * @param path, path to the card requesting to be modified
     * @param parameter, which field requesting to be changed
     * @return whether update was successful
     */
    public boolean setCard(TreePath path, String parameter);
    
    /**
     * 
     * @param path, path to document needing to be removed
     * @return whether delete was successful
     */
    public boolean removeDocument(TreePath path);
    
    /**
     * 
     * @param path, path to card requesting to be removed
     * @return whether delete was successful
     */
    public boolean removeCard(TreePath path);
}
