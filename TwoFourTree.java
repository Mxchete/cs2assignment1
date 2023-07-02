// functions i want to implement
// resizeWrapper(int value) - to replace resize function
// adopt(TwoFourTreeItem adoptee) - adopt a single node as a child
// adoptChildren(TwoFourTreeItem node1, TwoFourTreeItem node2) - take children from 1 or 2 nodes and attach them to a new node
// remove(int value) - opposite of append
public class TwoFourTree {

    private class TwoFourTreeItem {
        // provided variables for tree node item
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

        // provided is_____ functions filled out
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

        // provided tree item node constructors filled out
        // includes logic for sorting values by size
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

            // case where all values are the same
            if (value1 == value2 && value2 == value3) {
                this.values = 1;
                this.value1 = value1;
            }
            // case where values 1 and 2 are the same
            else if (value1 == value2 || value2 == value3) {
                this.values = 2;
                if (value1 > value3) {
                    this.value1 = value3;
                    this.value2 = value1;
                }
                else {
                    this.value1 = value1;
                    this.value2 = value3;
                }
            }
            // case where values 2 and 3 are the same
            else if (value1 == value3) {
                this.values = 2;
                if (value2 > value3) {
                    this.value1 = value3;
                    this.value2 = value2;
                }
                else {
                    this.value1 = value2;
                    this.value2 = value3;
                }
            }
            // case where all values are different
            else {
                this.values = 3;
                if (value1 > value2 && value2 > value3) {
                    this.value1 = value3; 
                    this.value2 = value2; 
                    this.value3 = value1;
                }
                else if (value2 > value1 && value1 > value3) {
                    this.value1 = value3; 
                    this.value2 = value1; 
                    this.value3 = value2;
                }
                else if (value1 > value3 && value3 > value2) {
                    this.value1 = value2; 
                    this.value2 = value3; 
                    this.value3 = value1;
                }
                else if (value2 > value3 && value3 > value1) {
                    this.value1 = value1; 
                    this.value2 = value3; 
                    this.value3 = value2;
                }
                else if (value3 > value2 && value2 > value1) {
                    this.value1 = value1; 
                    this.value2 = value2; 
                    this.value3 = value3;
                }
                else {
                    this.value1 = value2; 
                    this.value2 = value1; 
                    this.value3 = value3;
                }
            }

        }

        // ____________________________________________
        // Function: searchNodeForValue
        // Parameters: value to look for
        // Purpose: find if a node contains a certain value
        // Returns: bool value; true if value is in node, false otherwise
        // Documented Anomolies: none
        private boolean searchNodeForValue(int value) {

            // check all values in node and return true if value matches value that is being searched for
            if (this.value1 == value) return true;
            else if (this.value2 == value) return true;
            else if (this.value3 == value) return true;
            return false;

        }

        // ____________________________________________
        // Function: replaceChild
        // Parameters: old child of this node, replacement child
        // Purpose: replace a node's child with a new child
        // Returns: Nothing
        // Documented Anomolies: none
        private void replaceChild(TwoFourTreeItem oldChild, TwoFourTreeItem newChild) {

            // if child value matches old child value, replace it with new child
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

        // ____________________________________________
        // Function: fuse
        // Parameters: node's sibling node (directly right or left of this node)
        // Purpose: take a two node, its two node sibling,
        // and the parent value between them, and combine
        // them into a single four node
        // Returns: TwoFourTreeItem Node; contains fused node
        // Documented Anomolies: none
        private TwoFourTreeItem fuse(TwoFourTreeItem sibling) {

            // case where one of the nodes to fuse is the left child of the parent
            if (parent.leftChild.value1 == value1 || parent.leftChild.value1 == sibling.value1) { 
                // use append to add parent & sibling value to current node,
                // then remove value from parent and reset child in case sibling was left
                append(parent.value1);
                append(sibling.value1);
                parent.remove(parent.value1);
                parent.leftChild = this;
                // handle removing old sibling based on whether the parent is now a 2 or 3 node
                // (parent was resized and is now smaller, used to be 3 or 4 node)
                if (parent.isTwoNode()) {
                    parent.centerChild = null;
                }
                else {
                    parent.centerLeftChild = null;
                    parent.centerChild = parent.centerRightChild;
                    parent.centerRightChild = null;
                }
            }
            // case where one of the nodes to fuse is the right child of the parent
            else if (parent.rightChild.value1 == value1 || parent.rightChild.value1 == sibling.value1) {
                // fuse in this case where one node is the center child
                // fuse values and then remove center child as parent becomes a two node
                if (parent.isThreeNode()) {
                    append(parent.value2);
                    append(sibling.value1);
                    parent.remove(parent.value2);
                    parent.centerChild = null;
                }
                // fuse where one node is the center right child
                // fuse values & turn 4 node into 3 node
                else {
                    append(parent.value3);
                    append(sibling.value1);
                    parent.remove(parent.value3);
                    parent.centerRightChild = null;
                    parent.centerChild = parent.centerLeftChild;
                    parent.centerLeftChild = null;
                }
                // in case sibling was right child, make sure right child points to current node
                parent.rightChild = this;
            }
            // case where the parent is a four node with nodes to fuse in the center
            else {
                // basically combine 2 center children and middle parent value into a 4 node center child of 3 node parent
                append(parent.value2);
                append(sibling.value1);
                parent.remove(parent.value2);
                parent.centerRightChild = null;
                parent.centerChild = this;
                parent.centerLeftChild = null;
            }
            // if node is not a leaf, children should be reorganized
            if (!isLeaf) {
                // sibling is larger, so its old children will be the right childs of new 4 node
                if (sibling.value1 > value1) {

                    centerLeftChild = rightChild;

                    centerRightChild = sibling.leftChild;
                    sibling.leftChild.parent = this;
                    rightChild = sibling.rightChild;
                    sibling.rightChild.parent = this;
                }
                // sibling is smaller, so its old children will be the left childs of new 4 node
                else {
                    centerRightChild = leftChild;

                    centerLeftChild = sibling.rightChild;
                    sibling.rightChild.parent = this;
                    leftChild = sibling.leftChild;
                    sibling.leftChild.parent = this;

                }
            }
            return this;
        }

        // ____________________________________________
        // Function: rotate
        // Parameters: node's sibling node (directly right or left of this node)
        // Purpose: rotate value from parent node into this node,
        // replace parent value with value from sibling
        // Returns: TwoFourTreeItem Node; contains rotated node
        // Documented Anomolies: none
        private TwoFourTreeItem rotate(TwoFourTreeItem sibling) {

            // move parent value into node and move sibling value to parent for all cases
            // case where node to rotate into is the left child of its parent
            if (parent.leftChild.value1 == value1) { 
                this.append(parent.value1);
                parent.remove(parent.value1);
                parent.append(sibling.value1);
                sibling.remove(sibling.value1);
            }
            // case where sibling node is the left child
            else if (parent.leftChild.value1 == sibling.value1) {
                this.append(parent.value1);
                parent.remove(parent.value1);
                // determine whether rightmost sibling value is value2 or value2=3
                if (sibling.isThreeNode()) {
                    parent.append(sibling.value2);
                    sibling.remove(sibling.value2);
                }
                else {
                    parent.append(sibling.value3);
                    sibling.remove(sibling.value3);
                }
            }
            // case where node to rotate into is the right child
            else if (parent.rightChild.value1 == value1) {

                // move rightmost parent value down based on if it is a 3 or 4 node
                if (parent.isThreeNode()) {
                    this.append(parent.value2);
                    parent.remove(parent.value2);
                }
                else {
                    this.append(parent.value3);
                    parent.remove(parent.value3);
                }
                // then move rightmost sibling value up
                if (sibling.isThreeNode()) {
                    parent.append(sibling.value2);
                    sibling.remove(sibling.value2);
                }
                else {
                    parent.append(sibling.value3);
                    sibling.remove(sibling.value3);
                }

            }
            // case where sibling is the right child
            else if (parent.rightChild.value1 == sibling.value1) {
                // move rightmost parent value down based on if it is a 3 or 4 node
                if (parent.isThreeNode()) {
                    this.append(parent.value2);
                    parent.remove(parent.value2);
                }
                else {
                    this.append(parent.value3);
                    parent.remove(parent.value2);
                }
                // then move leftmost sibling value up
                parent.append(sibling.value1);
                sibling.remove(sibling.value1);
            }
            // case where node to rotate is the center left child
            else if (parent.centerLeftChild.value1 == value1) {
                this.append(parent.value2);
                parent.remove(parent.value2);
                parent.append(sibling.value1);
                sibling.remove(sibling.value1);
            }
            // case where node to rotate is center right child
            else {
                this.append(parent.value2);
                parent.remove(parent.value2);
                // find rightmost sibling value and remove it
                if (sibling.isThreeNode()) {
                    parent.append(sibling.value2);
                    sibling.remove(sibling.value2);
                }
                else {
                    parent.append(sibling.value3);
                    sibling.remove(sibling.value3);
                }
            }

            // adjust children if node is not a leaf
            if (!isLeaf) {
                // if sibling is bigger than node
                if (sibling.value1 > value1) {
                    // move siblings old left child to be node's right child
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
                // if sibling is smaller than node
                else {
                    // move sibling's old right child to be node's left child
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

        // ____________________________________________
        // Function: moveUp
        // Parameters: this node's child
        // Purpose: move a two node into its parent
        // Returns: TwoFourTreeItem Node; contains parent with moved value
        // Documented Anomolies: none
        private TwoFourTreeItem moveUp(TwoFourTreeItem node) {

            // case where node to move up is the left child
            if (leftChild.value1 == node.value1) {
                // move child into two node parent and adjust children
                if (isTwoNode()) {
                    append(node.value1);
                    leftChild = node.leftChild;
                    leftChild.parent = this;
                    centerChild = node.rightChild;
                    centerChild.parent = this;
                }
                // move child into 3 node parent and adjust children
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
            // case where node to move up is the right child
            else if (rightChild.value1 == node.value1) {
                // move child into two node parent and adjust children
                if (isTwoNode()) {
                    append(node.value1);
                    centerChild = node.leftChild;
                    centerChild.parent = this;
                    rightChild = node.rightChild;
                    rightChild.parent = this;
                }
                // move child into 3 node parent and adjust children
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
            // case where node to move up is the center child of a 3 node
            // no need to worry about 4 node since a four node cannot be added to
            else {
                // move child value up and adjust children
                append(node.value1);
                centerLeftChild = node.leftChild;
                centerLeftChild.parent = this;
                centerRightChild = node.rightChild;
                centerRightChild.parent = this;
                centerChild = null;
            }
            return this;

        }

        // ____________________________________________
        // Function: splitFourNode
        // Parameters: none
        // Purpose: take a four node and split it into 3 two nodes
        // Returns: TwoFourTreeItem Node; contains new middle two node
        // Documented Anomolies: none
        private TwoFourTreeItem splitFourNode() {
            // create the three nodes from the original four node
            TwoFourTreeItem left = new TwoFourTreeItem(value1);
            TwoFourTreeItem middle = new TwoFourTreeItem(value2);
            TwoFourTreeItem right = new TwoFourTreeItem(value3);
            middle.isLeaf = false;
            middle.leftChild = left;
            left.parent = middle;
            middle.rightChild = right;
            right.parent = middle;

            // if old node was not a leaf, rehook its children
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

            // if old node was the root, make the new middle node the new root
            if (isRoot()) {
                root = middle;
                
            }
            // merge middle into parent
            else {
                middle.parent = parent;
                parent.replaceChild(this, middle);
                middle = parent.moveUp(middle);
            }
            return middle;
        }

        // ____________________________________________
        // Function: append
        // Parameters: value to add to this node
        // Purpose: add a value to a node
        // Returns: Nothing
        // Documented Anomolies: none
        private void append(int value) {
            // if the root got removed in edge case, this will prevent errors
            if (values == 0) {
                value1 = value;
            }
            // case where a value is appended to a two node
            else if (isTwoNode()) {
                if (value1 > value) {
                    value2 = value1;
                    value1 = value;
                }
                else {
                    value2 = value;
                }
            }
            // case where a value is appended to a three node
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
                // this should never be reached
                else {
                    return;
                }
            }
            // this case should never be reached
            else {
                return;
            }
            // increment values to reflect added value
            values++;
        }

        // ____________________________________________
        // Function: remove
        // Parameters: value to remove from this node
        // Purpose: delete a value from a node
        // Returns: Nothing
        // Documented Anomolies: none
        private void remove(int value) {

            // if value to remove is the first value
            if (value == value1) {
                value1 = value2;
                value2 = value3;
                value3 = 0;
            }
            // if value to remove is the second value
            else if (value == value2) {
                value2 = value3;
                value3 = 0;
            }
            // if value to remove is the third value
            else if (value == value3) {
                value3 = 0;
            }
            // decrement values to reflect removed value
            values--;
        }

        // ____________________________________________
        // Function: findSibling
        // Parameters: none
        // Purpose: get a sibling node for a given node
        // Returns: TwoFourTreeItem Node; contains sibling node
        // Documented Anomolies: none
        private TwoFourTreeItem findSibling() {

            // create sibling, which will be initialized in the function
            TwoFourTreeItem sibling;

            // if parent is a two node root, then it is the only case where a 2 node is the parent
            if (parent.isRoot() && parent.isTwoNode()) {
                if (parent.leftChild.value1 == value1) {
                    sibling = parent.rightChild;
                }
                else {
                    sibling = parent.leftChild;
                }
            }
            // case where the parent is a 3 node
            else if (parent.isThreeNode()) {
                if (parent.centerChild.value1 == value1) {
                    sibling = parent.leftChild;
                    if (!parent.rightChild.isTwoNode()) sibling = parent.rightChild;
                }
                else {
                    sibling = parent.centerChild;
                }
            }
            // case where the parent is a 4 node
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

        // provided print functions below
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

    // provided root
    TwoFourTreeItem root = null;

    // ____________________________________________
    // Function: mergeNode
    // Parameters: two node to be merged
    // Purpose: wrapper function for rotating/fusing nodes together
    // when searching for delete
    // Returns: TwoFourTreeItem Node; contains merged node
    // Documented Anomolies: none
    private TwoFourTreeItem mergeNode(TwoFourTreeItem node) {

        // case where node to be merged is a non-leaf root
        // Note: can only merge if both children are two nodes as well, otherwise just return
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
        // if first case is not met, find sibling
        else {
            TwoFourTreeItem sibling = node.findSibling();
            // fuse two node sibling
            if (sibling.isTwoNode()) {
                node = node.fuse(sibling);
            }
            // rotate if sibling is not two node
            else {
                node = node.rotate(sibling);
            }
        }
        return node;

    }

    // ____________________________________________
    // Function: search
    // Parameters: current node to be searched, key containing value to search for,
    // bool values to control whether merge or split should be called when iterating through tree
    // Purpose: recursively iterate through two four tree
    // to find node closest to or equal to given key value
    // Returns: TwoFourTreeItem Node; contains node closest to value/containing value
    // Documented Anomolies: none
    private TwoFourTreeItem search(TwoFourTreeItem currentNode, int key, boolean hasMerge, boolean hasSplit) {

        // should not occur, but just in case, this should prevent errors
        if (currentNode == null) {
            
            return root;
        }
        // split if it is a four node and you are adding a node to the tree
        if (hasSplit && currentNode.isFourNode()) {
            currentNode = currentNode.splitFourNode();
        }
        // else merge if it is a two node and you are deleting a node
        else if (hasMerge && currentNode.isTwoNode()) {
            currentNode = mergeNode(currentNode);
        }

        // if value was found or current node is a leaf, return current node
        if (currentNode.searchNodeForValue(key) || currentNode.isLeaf) return currentNode;

        // otherwise, find children
        TwoFourTreeItem child = closestChild(key, currentNode);
        // if child exists, recurse
        if (child != null) return search(child, key, hasMerge, hasSplit);
        // otherwise closest node is current node, return it
        // (should never occur since leaf will return)
        else return currentNode;


    }
    
    // ____________________________________________
    // Function: closestChild
    // Parameters: value to search for, node to search through
    // Purpose: returns child with value closest to given
    // key value
    // Returns: TwoFourTreeItem Node; returns node with value closest to key value
    // Documented Anomolies: none
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

    // ____________________________________________
    // Function: addValue
    // Parameters: value to add to tree
    // Purpose: add a value to the tree
    // Returns: bool; true if value was already inserted, false if value needed to be inserted
    // Documented Anomolies: none
    public boolean addValue(int value) {

        // case where tree is empty
        if (root == null) {
            root = new TwoFourTreeItem(value);
            return false;
        }

        // call search with split set to true
        TwoFourTreeItem searchNode = search(root, value, false, true);
        // check to make sure it is not already in tree
        if (searchNode.searchNodeForValue(value)) return true;
        // append to leaf
        searchNode.append(value);
        return false;
    }

    // ____________________________________________
    // Function: hasValue
    // Parameters: value to search for
    // Purpose: find if a value exists in the tree
    // Returns: bool; true if value is in tree, false otherwise
    // Documented Anomolies: none
    public boolean hasValue(int value) {

        // check to make sure tree is not empty
        if (root == null) return false;

        // call search
        TwoFourTreeItem searchNode = search(root, value, false, false);
        // if the value is there return true
        if (searchNode.searchNodeForValue(value)) return true;

        return false;
    }

    // ____________________________________________
    // Function: deleteValue
    // Parameters: value to remove from tree
    // Purpose: remove a value from the tree
    // Returns: bool; true if value was deleted, false otherwise
    // Documented Anomolies: none
    public boolean deleteValue(int value) {

        // check to make sure the tree is not empty
        if (root == null) return false;

        // call search with merge set to true
        TwoFourTreeItem searchNode = search(root, value, true, false);
        // check if value exists
        if (!searchNode.searchNodeForValue(value)) return false;
        // if value is in a leaf, it can just be removed
        if (searchNode.isLeaf) {

            searchNode.remove(value);

            return true;

        }
        // find leftmost right node
        else {
            TwoFourTreeItem leftmostRight;
            // case where value to delete was found in root
            if (searchNode.isRoot()) {
                leftmostRight = search(searchNode.rightChild, value - 1, true, false);
            }
            // where value was found in left value of node
            else if (searchNode.value1 == value) {
                if (searchNode.isThreeNode()) {
                    leftmostRight = search(searchNode.centerChild, value - 1, true, false);
                }
                else {
                    leftmostRight = search(searchNode.centerLeftChild, value - 1, true, false);
                }
            }
            // value was found in middle / right value
            else if (searchNode.value2 == value) {
                if (searchNode.isThreeNode()) {
                    leftmostRight = search(searchNode.rightChild, value - 1, true, false);
                }
                else {
                    leftmostRight = search(searchNode.centerRightChild, value - 1, true, false);
                }
            }
            // value was found in right value of a four node
            else {
                leftmostRight = search(searchNode.rightChild, value - 1, true, false);
            }
            // if value to delete was merged into the leftmostright node
            if (leftmostRight.searchNodeForValue(value)) {
                leftmostRight.remove(value);
                // if value is a two node after removal, call merge again to prevent errors
                if (leftmostRight.isTwoNode()) leftmostRight = mergeNode(leftmostRight);
                leftmostRight.remove(value);
                return true;
            }
            // in case searchnode was moved when moving down to find leftmost right
            searchNode = search(searchNode, value, false, false);
            // then replace value with value from leftmostRight and remove leftmostRight's value
            if (searchNode.value1 == value) searchNode.value1 = leftmostRight.value1;
            else if (searchNode.value2 == value) searchNode.value2 = leftmostRight.value1;
            else if (searchNode.value3 == value) searchNode.value3 = leftmostRight.value1;
            leftmostRight.remove(leftmostRight.value1);

            return true;
        }
    }

    // provided print function
    public void printInOrder() {
        if(root != null) root.printInOrder(0);
    }

    // provided constructor
    public TwoFourTree() {

    }

}
