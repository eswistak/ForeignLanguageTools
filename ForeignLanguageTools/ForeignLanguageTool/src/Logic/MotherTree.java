/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import org.w3c.dom.Document;

/**
 *
 * @author Ethan Swistak
 */
public class MotherTree {
    
    private MotherTree() {
    }
    
    private Document nodes;
    
    public static MotherTree getInstance() {
        return MotherTreeHolder.INSTANCE;
    }
    
    private static class MotherTreeHolder {

        private static final MotherTree INSTANCE = new MotherTree();
    }

    public Document getNodes() {
        return nodes;
    }

    public void setNodes(Document nodes) {
        this.nodes = nodes;
    }
    
    
}
