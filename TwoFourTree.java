/*******************************
 * TODO:
 * Create split function
 * Finish add value function 
 * Finish has value function 
 * Finish delete value function 
 ******************************/
import java.util.Arrays;

public class TwoFourTree {

    private class TwoFourTreeItem {
        int values = 1;
        int value1 = 0;                             // always exists.
        int value2 = 0;                             // exists iff the node is a 3-node or 4-node.
        int value3 = 0;                             // exists iff the node is a 4-node.
        boolean isLeaf = true;
        
        TwoFourTreeItem parent = null;              // parent exists iff the node is not root.
        TwoFourTreeItem leftChild = null;           // left and right child exist iff the note is a non-leaf.
        TwoFourTreeItem rightChild = null;          
        TwoFourTreeItem centerChild = null;         // center child exists iff the node is a non-leaf 3-node.
        TwoFourTreeItem centerLeftChild = null;     // center-left and center-right children exist iff the node is a non-leaf 4-node.
        TwoFourTreeItem centerRightChild = null;

        public boolean isTwoNode() {
            if (values == 1) return true;
            return false;
        }

        public boolean isThreeNode() {
            if (values == 2) return true;
            return false;
        }

        public boolean isFourNode() {
            if (values == 3) return true;
            return false;
        }

        public boolean isRoot() {
            if (parent == null) return true;
            return false;
        }

        public TwoFourTreeItem(int value1) {

            this.value1 = value1;

        }

        public TwoFourTreeItem(int value1, int value2) {

            this.values = 2;
            if (value2 > value1) {
                this.value1 = value1;
                this.value2 = value2;
            }
            else if (value1 > value2) {
                this.value2 = value1;
                this.value1 = value2;
            }
            else {
                this.value1 = value1;
                this.values = 1;
            }
        }

        public TwoFourTreeItem(int value1, int value2, int value3) {

            int[] sortedValue = {value1, value2, value3};
            Arrays.sort(sortedValue);
            
            // case where all values are the same
            if (sortedValue[0] == sortedValue[2]) {
                this.values = 1;
                this.value1 = sortedValue[0];
            }
            // case where values 1 and 2 are the same
            else if (sortedValue[0] == sortedValue[1]) {
                this.values = 2;
                this.value1 = sortedValue[0];
                this.value2 = sortedValue[2];
            }
            // case where values 2 and 3 are the same
            else if (sortedValue[1] == sortedValue[2]) {
                this.values = 2;
                this.value1 = sortedValue[0];
                this.value2 = sortedValue[1];
            }
            // case where all values are different
            else {
                this.values = 3;
                this.value1 = sortedValue[0];
                this.value2 = sortedValue[1];
                this.value3 = sortedValue[2];
            }

        }

        private void printIndents(int indent) {
            for(int i = 0; i < indent; i++) System.out.printf("  ");
        }

        public void printInOrder(int indent) {

            if(!isLeaf) leftChild.printInOrder(indent + 1);
            printIndents(indent);
            System.out.printf("%d\n", value1);

            if(isThreeNode()) {

                if(!isLeaf) centerChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);

            } 
            else if(isFourNode()) {

                if(!isLeaf) centerLeftChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
                if(!isLeaf) centerRightChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value3);

            }

            if(!isLeaf) rightChild.printInOrder(indent + 1);

        }
    }

    TwoFourTreeItem root = null;

    // This function should split a four node
    // should follow how its done in pwpnt.
    // Middle value moved to parent node, 
    // edge values become 2 new nodes with 1 value each
    private void splitFourNode(TwoFourTreeItem fourNode) {

        TwoFourTreeItem tmp = fourNode;
        TwoFourTreeItem parentNode;

        if (root == fourNode) {
            parentNode = new TwoFourTreeItem(fourNode.value2);
            root = parentNode;
        }
        else 
            parentNode = fourNode.parent;

        if (fourNode.parent.isTwoNode()) {
            // TODO: METHOD FOR ADDING MIDDLE VALUE TO PARENT
        } 
        else if (fourNode.parent.isThreeNode()) {
            // TODO: METHOD FOR ADDING MIDDLE VALUE TO PARENT
        } // Parent cannot reasonably be a four node or leaf node, so only should have to check these cases
        fourNode.parent.values++;

    }

    private void mergeNode(TwoFourTreeItem node) {

    }
    
    private boolean searchNodeForValue(int value, TwoFourTreeItem node) {

        if (node.value1 == value) return true;
        else if (node.isThreeNode() && node.value2 == value) return true;
        else if (node.isFourNode() && node.value3 == value) return true;
        return false;

    }

    private TwoFourTreeItem closestChild (int value, TwoFourTreeItem node) {

        // case 2 node
        if (node.isTwoNode()) {
            if (value > node.value1) return node.rightChild;
            else return node.leftChild;
        }
        // case 3 node
        else if (node.isThreeNode()) {
            if (value > node.value2) return node.rightChild;
            else if (value > node.value1) return node.centerChild;
            else return node.leftChild;
        }
        // case 4 node
        else {
            if (value > node.value3) return node.rightChild;
            else if (value > node.value2) return node.centerRightChild;
            else if (value > node.value1) return node.centerLeftChild;
            else return node.leftChild;
        }

    }

    // TODO: FINISH THIS METHOD
    // returns true if value already exists
    public boolean addValue(int value) {

        // case where tree is empty
        if (root == null) {
            root = new TwoFourTreeItem(value);
            return false;
        }

        TwoFourTreeItem searchNode = root;

        if (searchNodeForValue(value, searchNode)) return true;

        while (true) {

            if (searchNode.isFourNode()) {
                splitFourNode(searchNode);
            }

            if (searchNode.isLeaf) break;

        }

        return false;
    }

    public boolean hasValue(int value) {

        if (root == null) return false;

        TwoFourTreeItem searchNode = root;

        while (true) {

            if (searchNode == null) break;
            if (searchNodeForValue(value, searchNode)) return true;
            if (searchNode.isLeaf) break;

            TwoFourTreeItem child = closestChild(value, searchNode);
            if (child == null) {
                //TODO: ADD VALUE TO NODE IF ITS CHILD IS NULL
            }
            
            
        }

        return false;
    }

    public boolean deleteValue(int value) {

        TwoFourTreeItem searchNode = root;
        while (true) {
            break;
        }
        return false;
    }

    public void printInOrder() {
        if(root != null) root.printInOrder(0);
    }

    public TwoFourTree() {

    }

}
