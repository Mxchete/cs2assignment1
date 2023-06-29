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

        private boolean searchNodeForValue(int value) {

            if (this.value1 == value) return true;
            else if (this.isThreeNode() && this.value2 == value) return true;
            else if (this.isFourNode() && this.value3 == value) return true;
            return false;

        }

        private void replaceChild(TwoFourTreeItem oldChild, TwoFourTreeItem newChild) {
            if (leftChild.value1 == oldChild.value1) {
                leftChild = newChild;
            }
            else if (rightChild.value1 == oldChild.value1) {
                rightChild = newChild;
            }
            else if (isThreeNode()) {
                centerChild = newChild;
            }
            else if (centerLeftChild.value1 == oldChild.value1) {
                centerLeftChild = newChild;
            }
            else {
                centerRightChild = newChild;
            }
        }

        // functions i want to implement
        // resizeWrapper(int value) - to replace resize function
        // adopt(TwoFourTreeItem adoptee) - adopt a single node as a child
        // adoptChildren(TwoFourTreeItem node1, TwoFourTreeItem node2) - take children from 1 or 2 nodes and attach them to a new node
        // remove(int value) - opposite of append

        // TODO: CHECK THIS!!!!
        /*
         *
         *
         *
         *
         *
         *
         *
         * **********DONT FORGET TO CHECK BEFORE YOU TEST
         * */
        private TwoFourTreeItem fuse(TwoFourTreeItem sibling) {
            TwoFourTreeItem newNode;
            if (parent.leftChild.value1 == value1 || parent.leftChild.value1 == sibling.value1) { 
                newNode = new TwoFourTreeItem(value1, parent.value1, sibling.value1);
                parent.remove(parent.value1);
                parent.leftChild = newNode;
                if (parent.isThreeNode()) {
                    parent.centerChild = null;
                }
                else {
                    parent.centerLeftChild = null;
                    parent.centerChild = parent.centerRightChild;
                    parent.centerRightChild = null;
                }
            }
            else if (parent.rightChild.value1 == value1 || parent.rightChild.value1 == sibling.value1) {


                if (parent.isThreeNode()) {
                    newNode = new TwoFourTreeItem(value1, parent.value2, sibling.value1);
                    parent.remove(parent.value2);
                    parent.centerChild = null;
                }
                else {
                    newNode = new TwoFourTreeItem(value1, parent.value3, sibling.value1);
                    parent.remove(parent.value3);
                    parent.centerRightChild = null;
                    parent.centerChild = parent.centerLeftChild;
                    parent.centerLeftChild = null;
                }
                parent.rightChild = newNode;
            }
            else {
                newNode = new TwoFourTreeItem(value1, parent.value2, sibling.value1);
                parent.remove(parent.value2);
                parent.centerRightChild = null;
                parent.centerChild = newNode;
                parent.centerLeftChild = null;
            }
            newNode.isLeaf = isLeaf;
            newNode.parent = parent;
            if (!isLeaf) {
                if (sibling.value1 > value1) {
                    sibling.leftChild = newNode.centerRightChild;
                    sibling.leftChild.parent = newNode;
                    sibling.rightChild = newNode.rightChild;
                    sibling.rightChild.parent = newNode;

                    leftChild = newNode.leftChild;
                    leftChild.parent = newNode;
                    rightChild = newNode.centerLeftChild;
                    rightChild.parent = newNode;
                }
                else {
                    sibling.leftChild = newNode.leftChild;
                    sibling.leftChild.parent = newNode;
                    sibling.rightChild = newNode.centerLeftChild;
                    sibling.rightChild.parent = newNode;

                    leftChild = newNode.centerRightChild;
                    leftChild.parent = newNode;
                    rightChild = newNode.rightChild;
                    rightChild.parent = newNode;
                }
            }
            return newNode;
        }

        // this node is a two node but sibling and parent are not
        private TwoFourTreeItem rotate(TwoFourTreeItem sibling) {
            System.out.println(value1 + " " + sibling.value1);
            System.out.println(isLeaf + " " + sibling.isLeaf);
            if (parent.leftChild.value1 == value1) { 
                this.append(parent.value1);
                parent.remove(parent.value1);
                parent.append(sibling.value1);
                sibling.remove(sibling.value1);
            }
            else if (parent.leftChild.value1 == sibling.value1) {
                this.append(parent.value1);
                parent.remove(parent.value1);
                if (sibling.isThreeNode()) {
                    parent.append(sibling.value2);
                    sibling.remove(sibling.value2);
                }
                else {
                    parent.append(sibling.value3);
                    sibling.remove(sibling.value3);
                }
            }
            else if (parent.rightChild.value1 == value1) {

                if (parent.isThreeNode()) {
                    this.append(parent.value2);
                    parent.remove(parent.value2);
                }
                else {
                    this.append(parent.value3);
                    parent.remove(parent.value2);
                }
                if (sibling.isThreeNode()) {
                    parent.append(sibling.value2);
                    sibling.remove(sibling.value2);
                }
                else {
                    parent.append(sibling.value3);
                    sibling.remove(sibling.value3);
                }

            }
            else if (parent.rightChild.value1 == sibling.value1) {
                if (parent.isThreeNode()) {
                    this.append(parent.value2);
                    parent.remove(parent.value2);
                }
                else {
                    this.append(parent.value3);
                    parent.remove(parent.value2);
                }
                parent.append(sibling.value1);
                sibling.remove(sibling.value1);
            }
            else if (parent.centerLeftChild.value1 == value1) {
                this.append(parent.value2);
                parent.remove(parent.value2);
                parent.append(sibling.value1);
                sibling.remove(sibling.value1);
            }
            else {
                this.append(parent.value2);
                parent.remove(parent.value2);
                if (sibling.isThreeNode()) {
                    parent.append(sibling.value2);
                    sibling.remove(sibling.value2);
                }
                else {
                    parent.append(sibling.value3);
                    sibling.remove(sibling.value3);
                }
            }
            // ensureLeafiness();
            // sibling.ensureLeafiness();
            System.out.println(value1 + " " + sibling.value1);
            System.out.println(isLeaf + " " + sibling.isLeaf);
            if (!isLeaf) {
                if (sibling.value1 > value1) {
                    centerChild = rightChild;
                    rightChild = sibling.leftChild;
                    rightChild.parent = this;
                    // sibling is 2 or 3 node since it has been resized
                    if (sibling.isTwoNode()) {
                        sibling.leftChild = sibling.centerChild;
                        sibling.centerChild = null;
                    }
                    else {
                        sibling.leftChild = sibling.centerLeftChild;
                        sibling.centerChild = sibling.centerRightChild;
                        sibling.centerLeftChild = null;
                        sibling.centerRightChild = null;
                    }
                }
                else {
                    centerChild = leftChild;
                    leftChild = sibling.rightChild;
                    leftChild.parent = this;
                    // sibling is 2 or 3 node since it has been resized
                    if (sibling.isTwoNode()) {
                        sibling.rightChild = sibling.centerChild;
                        sibling.centerChild = null;
                    }
                    else {
                        sibling.rightChild = sibling.centerRightChild;
                        sibling.centerChild = sibling.centerLeftChild;
                        sibling.centerLeftChild = null;
                        sibling.centerRightChild = null;
                    }
                }
            }
            return this;
        }


        private TwoFourTreeItem moveUp(TwoFourTreeItem node) {

            if (leftChild.value1 == node.value1) {
                if (isTwoNode()) {
                    append(node.value1);
                    leftChild = node.leftChild;
                    leftChild.parent = this;
                    centerChild = node.rightChild;
                    centerChild.parent = this;
                }
                else {
                    append(node.value1);
                    leftChild = node.leftChild;
                    leftChild.parent = this;
                    centerLeftChild = node.rightChild;
                    centerLeftChild.parent = this;
                    centerRightChild = centerChild;
                    centerChild = null;
                }
            }
            else if (rightChild.value1 == node.value1) {
                if (isTwoNode()) {
                    append(node.value1);
                    centerChild = node.leftChild;
                    centerChild.parent = this;
                    rightChild = node.rightChild;
                    rightChild.parent = this;
                }
                else {
                    append(node.value1);
                    centerRightChild = node.leftChild;
                    centerRightChild.parent = this;
                    rightChild = node.rightChild;
                    rightChild.parent = this;
                    centerLeftChild = centerChild;
                    centerChild = null;
                }
            }
            else {
                append(node.value1);
                centerLeftChild = node.leftChild;
                centerLeftChild.parent = this;
                centerRightChild = node.rightChild;
                centerRightChild.parent = this;
                centerChild = null;
            }
            return this;
        }

        // This function should split a four node
        // should follow how its done in pwpnt.
        // Middle value moved to parent node, 
        // edge values become 2 new nodes with 1 value each
        private TwoFourTreeItem splitFourNode() {
            TwoFourTreeItem left = new TwoFourTreeItem(value1);
            TwoFourTreeItem middle = new TwoFourTreeItem(value2);
            TwoFourTreeItem right = new TwoFourTreeItem(value3);
            middle.isLeaf = false;
            middle.leftChild = left;
            left.parent = middle;
            middle.rightChild = right;
            right.parent = middle;

            if (!isLeaf) {

                left.leftChild = leftChild;
                leftChild.parent = left;
                left.rightChild = centerLeftChild;
                centerLeftChild.parent = left;
                left.isLeaf = isLeaf;

                right.leftChild = centerRightChild;
                centerRightChild.parent = right;
                right.rightChild = rightChild;
                rightChild.parent = right;
                right.isLeaf = isLeaf;

            }

            if (isRoot()) {
                root = middle;
                
            }
            else {
                // merge middle into parent
                middle.parent = parent;
                parent.replaceChild(this, middle);
                middle = parent.moveUp(middle);
            }
            return middle;
        }

        private void append(int value) {
            if (isTwoNode()) {
                if (value1 > value) {
                    value2 = value1;
                    value1 = value;
                }
                else {
                    value2 = value;
                }
            }
            else if (isThreeNode()) {
                if (value > value2) {
                    value3 = value;
                }
                else if (value > value1 && value != value2) {
                    value3 = value2;
                    value2 = value;
                }
                else if (value < value1) {
                    value3 = value2;
                    value2 = value1;
                    value1 = value;
                }
                else {
                    System.out.println("value is equal to an existing value");
                }
            }
            else {
                System.out.println("cannot append 4 node");
            }
            values++;
        }

        private void remove(int value) {
            if (value == value1) {
                value1 = value2;
                value2 = value3;
                value3 = 0;
            }
            else if (value == value2) {
                value2 = value3;
                value3 = 0;
            }
            else if (value == value3) {
                value3 = 0;
            }
            values--;
        }

        // TODO: FINISH THIS CLUSTERFUCK
        private TwoFourTreeItem findSibling() {
            TwoFourTreeItem sibling;
            if (parent.isThreeNode()) {
                if (parent.centerChild.value1 == value1) {
                    sibling = parent.leftChild;
                    if (!parent.rightChild.isTwoNode()) sibling = parent.rightChild;
                }
                else {
                    sibling = parent.centerChild;
                }
            }
            else {
                if (parent.centerLeftChild.value1 == value1) {
                    sibling = parent.leftChild;
                    if (!parent.centerRightChild.isTwoNode()) sibling = parent.centerRightChild;
                }
                else if (parent.centerRightChild.value1 == value1) {
                    sibling = parent.rightChild;
                    if (!parent.centerLeftChild.isTwoNode()) sibling = parent.centerLeftChild;
                }
                else if (parent.leftChild.value1 == value1) {
                    sibling = parent.centerLeftChild;
                }
                else {
                    sibling = parent.centerRightChild;
                }
            }

            return sibling;

        }


        private TwoFourTreeItem resize(int value) {

            // determine by looking for the value in the node if it should be resized or not
            boolean resizeDown = searchNodeForValue(value);
            TwoFourTreeItem tmp = this;
            // TwoFourTreeItem parent = node.parent;
            // System.out.println("IN resize!");

            if (isTwoNode()) {
                if (resizeDown) {

                    if (isRoot()) {
                        tmp = null;
                        return null;
                    }
                    System.out.println("Unmerged Two node in delete value!");
                    return this;
                }
                else {
                    // System.out.println("Here!");
                    // tmp = new TwoFourTreeItem(tmp.value1, value);
                    this.append(value);
                }
            }
            else if (isThreeNode()) {
                if (resizeDown) {
                    if (value1 == value) {
                        tmp = new TwoFourTreeItem(tmp.value2);
                    }
                    else {
                        tmp = new TwoFourTreeItem(tmp.value1);
                    }
                }
                else {
                    // tmp = new TwoFourTreeItem(tmp.value1, tmp.value2, value);
                    this.append(value);
                }
            }
            // if node is a four node, it must be resized down
            else {
                if (!resizeDown) {
                    System.out.println("Not Good!");
                    return this;
                }
                else {
                    if (this.value1 == value) {
                        tmp = new TwoFourTreeItem(tmp.value2, tmp.value3);
                    }
                    else if (this.value2 == value) {
                        tmp = new TwoFourTreeItem(tmp.value1, tmp.value3);
                    }
                    else {
                        tmp = new TwoFourTreeItem(tmp.value1, tmp.value2);
                    }
                }
            }

            this.rehook(tmp);
            // System.out.println(tmp.value1 + " " + tmp.value2);
            return tmp;

        }
        // take a newly created node and add it into its spot in a tree
        // this method does not adjust for new node being a different size, that should be done in resize
        private TwoFourTreeItem rehook(TwoFourTreeItem oldNode) {
            if (!oldNode.isRoot()) {
                // point to old parent
                this.parent = oldNode.parent;
                // have parent point to new child
                if (this.parent.leftChild.value1 == oldNode.value1) {
                    this.parent.leftChild = this;
                }
                else if (this.parent.rightChild.value1 == oldNode.value1) {
                    this.parent.rightChild = this;
                }
                else if (this.parent.isThreeNode()) {
                    this.parent.centerChild = this;
                }
                else if (this.parent.centerLeftChild.value1 == oldNode.value1) {
                    this.parent.centerLeftChild = this;
                }
                else {
                    this.parent.centerRightChild = this;
                }
            }
            if (!oldNode.isLeaf) {
                this.isLeaf = false;
                this.leftChild = oldNode.leftChild;
                this.leftChild.parent = this;
                this.rightChild = oldNode.rightChild;
                this.rightChild.parent = this;
                if (oldNode.isThreeNode()) {
                    this.centerChild = oldNode.centerChild;
                    this.centerChild.parent = this;
                }
                if (oldNode.isFourNode()) {
                    this.centerLeftChild = oldNode.centerLeftChild;
                    this.centerLeftChild.parent = this;
                    this.centerRightChild = oldNode.centerRightChild;
                    this.centerRightChild.parent = this;
                }
            }
            return this;
        }

        private void ensureLeafiness() {
            if (leftChild == null) {
                isLeaf = true;
            }
            else isLeaf = false;
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





    private int whichChild(TwoFourTreeItem node) {

        if (node.isRoot()) return -1;
        
        if (node.parent.leftChild.value1 == node.value1) return 1;
        if (node.parent.rightChild.value1 == node.value1) return 5;
        if (node.isThreeNode()) return 3;
        if (node.parent.centerLeftChild.value1 == node.value1) return 2;
        return 4;

    }


    //TODO: WTF IS THIS?
    private TwoFourTreeItem mergeNode(TwoFourTreeItem node) {

        System.out.println("In merge for " + node.value1);
        if (node.isRoot() && !node.isLeaf) {
            if (node.leftChild.isTwoNode() && node.rightChild.isTwoNode()) {
                
                node.isLeaf = node.leftChild.isLeaf & node.rightChild.isLeaf;
                node = node.moveUp(node.leftChild);
                node = node.moveUp(node.rightChild);

            }
            else {
                return node;
            }
        }
        else if (!node.isLeaf) {
            TwoFourTreeItem sibling = node.findSibling();
            if (sibling.isTwoNode()) {
                node = node.fuse(sibling);
            }
            else {
                node = node.rotate(sibling);
            }
            }
            // case where non 2-node sibling exists
            else {}
        // prevent errors for now
        return node;
    }


    private TwoFourTreeItem search(TwoFourTreeItem currentNode, int key, boolean hasMerge, boolean hasSplit) {

        currentNode.ensureLeafiness();
        // split if it is a four node and you are adding a node to the tree
        if (hasSplit && currentNode.isFourNode()) {
            // System.out.println("Split called!");
            currentNode = currentNode.splitFourNode();
        }
        // else merge if it is a two node and you are deleting a node
        else if (hasMerge && currentNode.isTwoNode()) {
            currentNode = mergeNode(currentNode);
        }

        // if value was found or current node is a leaf, return current node
        if (currentNode.searchNodeForValue(key) || currentNode.isLeaf) return currentNode;

        // System.out.println("Leaf not found!");
        // otherwise, find children
        TwoFourTreeItem child = closestChild(key, currentNode);
        // if child exists, recurse
        if (child != null) return search(child, key, hasMerge, hasSplit);
        // otherwise closest node is current node, return it
        // (should never occur since leaf will return)
        else return currentNode;


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
            // System.out.println("Adding Root!");
            root = new TwoFourTreeItem(value);
            return false;
        }

        // System.out.println("Initiating search!");
        TwoFourTreeItem searchNode = search(root, value, false, true);
        if (searchNode.searchNodeForValue(value)) return true;
        searchNode = searchNode.resize(value);
        // if (searchNode.isTwoNode()) {
        //
        //     // searchNode.values++;
        //     // if (searchNode.value1 > value) {
        //     //     searchNode.value2 = searchNode.value1;
        //     //     searchNode.value1 = value;
        //     //     // TODO: EVERYTHING HERE DOWN
        //     //     // if (searchNode.leftChild != null && searchNodeForValue(value + 1, searchNode.leftChild)) {
        //     //     //     searchNode.centerChild = searchNode.leftChild;
        //     //     //
        //     //     // }
        //     // }
        //     // else {
        //     //     searchNode.value2 = value;
        //     // }
        //     
        //     // searchNode = resize(searchNode, value);
        //
        // }
        // else {
        //
        //     // searchNode.values++;
        //     // if (searchNode.value1 > value) {
        //     //     searchNode.value3 = searchNode.value2;
        //     //     searchNode.value2 = searchNode.value1;
        //     //     searchNode.value1 = value;
        //     // }
        //     // else if (value > searchNode.value2) {
        //     //     searchNode.value3 = value;
        //     // }
        //     // else {
        //     //     searchNode.value3 = searchNode.value2;
        //     //     searchNode.value2 = value;
        //     // }
        //
        // }
        return false;
    }

    public boolean hasValue(int value) {

        if (root == null) return false;

        TwoFourTreeItem searchNode = search(root, value, false, false);
        if (searchNode.searchNodeForValue(value)) return true;

        return false;
    }

    public boolean deleteValue(int value) {

        if (root == null) return false;

        TwoFourTreeItem searchNode = search(root, value, true, false);
        if (!searchNode.searchNodeForValue(value)) return false;
        if (searchNode.isLeaf) {

            searchNode.remove(value);

            return true;

        }
        else {
            // find leftmost right node
            TwoFourTreeItem leftmostRight;
            if (searchNode.value1 == value) {
                if (searchNode.isThreeNode()) {
                    leftmostRight = search(searchNode.centerChild, value, true, false);
                }
                else {
                    leftmostRight = search(searchNode.centerLeftChild, value, true, false);
                }
            }
            else if (searchNode.value2 == value) {
                if (searchNode.isThreeNode()) {
                    leftmostRight = search(searchNode.rightChild, value, true, false);
                }
                else {
                    leftmostRight = search(searchNode.centerRightChild, value, true, false);
                }
            }
            else {
                leftmostRight = search(searchNode.rightChild, value, true, false);
            }
            searchNode.value1 = leftmostRight.value1;
            leftmostRight.remove(leftmostRight.value1);

            return true;
        }
        // searchNode = resize(searchNode, value);

        // return false;
    }

    public void printInOrder() {
        if(root != null) root.printInOrder(0);
    }

    public TwoFourTree() {

    }

}
