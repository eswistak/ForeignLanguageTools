
package Logic;

import org.w3c.dom.Document;

/**
 * This class holds the persistence datastore for the application
 * since it's not a great persistence solution, you've still got to manually sync everything
 * but it works!
 * 
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
