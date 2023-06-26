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

    // take a newly created node and add it into its spot in a tree
    // this method does not adjust for new node being a different size, that should be done in resize
    private TwoFourTreeItem rehook(TwoFourTreeItem newNode, TwoFourTreeItem oldNode) {
        if (!oldNode.isRoot()) {
            // point to old parent
            newNode.parent = oldNode.parent;
            // have parent point to new child
            if (newNode.parent.leftChild.value1 == oldNode.value1) {
                newNode.parent.leftChild = newNode;
            }
            else if (newNode.parent.rightChild.value1 == oldNode.value1) {
                newNode.parent.rightChild = newNode;
            }
            else if (newNode.parent.isThreeNode()) {
                newNode.parent.centerChild = newNode;
            }
            else if (newNode.parent.centerLeftChild.value1 == oldNode.value1) {
                newNode.parent.centerLeftChild = newNode;
            }
            else {
                newNode.parent.centerRightChild = newNode;
            }
        }
        if (!oldNode.isLeaf) {
            newNode.isLeaf = false;
            newNode.leftChild = oldNode.leftChild;
            newNode.leftChild.parent = newNode;
            newNode.rightChild = oldNode.rightChild;
            newNode.rightChild.parent = newNode;
            if (oldNode.isThreeNode()) {
                newNode.centerChild = oldNode.centerChild;
                newNode.centerChild.parent = newNode;
            }
            if (oldNode.isFourNode()) {
                newNode.centerLeftChild = oldNode.centerLeftChild;
                newNode.centerLeftChild.parent = newNode;
                newNode.centerRightChild = oldNode.centerRightChild;
                newNode.centerRightChild.parent = newNode;
            }
        }
        return newNode;
    }

    // This function should split a four node
    // should follow how its done in pwpnt.
    // Middle value moved to parent node, 
    // edge values become 2 new nodes with 1 value each
    private TwoFourTreeItem splitFourNode(TwoFourTreeItem fourNode) {

        TwoFourTreeItem tmp = fourNode;
        TwoFourTreeItem parentNode;

        if (fourNode.isRoot()) {
            parentNode = new TwoFourTreeItem(fourNode.value2);
            parentNode.leftChild = new TwoFourTreeItem(fourNode.value1);
            parentNode.rightChild = new TwoFourTreeItem(fourNode.value3);
            parentNode.isLeaf = false;

            // parentNode.leftChild.leftChild = fourNode.leftChild;
            // parentNode.leftChild.rightChild = fourNode.centerLeftChild;
            // parentNode.leftChild.isLeaf = fourNode.isLeaf;
            // parentNode.leftChild.parent = parentNode;
            //
            // parentNode.rightChild.leftChild = fourNode.centerRightChild;
            // parentNode.rightChild.rightChild = fourNode.rightChild;
            // parentNode.rightChild.isLeaf = fourNode.isLeaf;
            // parentNode.rightChild.parent = parentNode;

            root = parentNode;
            // return root;
        }
        else 
            parentNode = fourNode.parent;

        if (parentNode.isTwoNode() && !parentNode.isRoot()) {
            TwoFourTreeItem oldParent = parentNode;
            parentNode = new TwoFourTreeItem(parentNode.value1, fourNode.value2);
            parentNode.parent = oldParent.parent;
            // TODO: REHOOK parents children
            if (parentNode.parent.leftChild.value1 == oldParent.value1) {
                parentNode.parent.leftChild = parentNode;
            }
            else if (parentNode.parent.rightChild.value1 == oldParent.value1) {
                parentNode.parent.rightChild = parentNode;
            }
            else if (parentNode.parent.isTwoNode()) {
                parentNode.parent.centerChild = parentNode;
            }
            else {
                if (parentNode.parent.centerLeftChild.value1 == oldParent.value1) {
                    parentNode.parent.centerLeftChild = parentNode;
                }
                else {
                    parentNode.parent.centerRightChild = parentNode;
                }
            }

            if (oldParent.value1 == parentNode.value1) {

                parentNode.leftChild = oldParent.leftChild;
                parentNode.leftChild.parent = parentNode;

                parentNode.centerChild = new TwoFourTreeItem(fourNode.value1);
                parentNode.rightChild = new TwoFourTreeItem(fourNode.value3);

                parentNode.centerChild.leftChild = fourNode.leftChild;
                parentNode.centerChild.rightChild = fourNode.centerLeftChild;
                parentNode.centerChild.isLeaf = fourNode.isLeaf;
                parentNode.centerChild.parent = parentNode;

                parentNode.rightChild.leftChild = fourNode.centerRightChild;
                parentNode.rightChild.rightChild = fourNode.rightChild;
                parentNode.rightChild.isLeaf = fourNode.isLeaf;
                parentNode.rightChild.parent = parentNode;

            }
            else {

                parentNode.rightChild = oldParent.rightChild;
                parentNode.rightChild.parent = parentNode;

                parentNode.leftChild = new TwoFourTreeItem(fourNode.value1);
                parentNode.centerChild = new TwoFourTreeItem(fourNode.value3);

                parentNode.leftChild.leftChild = fourNode.leftChild;
                parentNode.leftChild.rightChild = fourNode.centerLeftChild;
                parentNode.leftChild.isLeaf = fourNode.isLeaf;
                parentNode.leftChild.parent = parentNode;

                parentNode.centerChild.leftChild = fourNode.centerRightChild;
                parentNode.centerChild.rightChild = fourNode.rightChild;
                parentNode.centerChild.isLeaf = fourNode.isLeaf;
                parentNode.centerChild.parent = parentNode;

            }
            // TODO: METHOD FOR ADDING MIDDLE VALUE TO PARENT
        } 
        else if (parentNode.isThreeNode()) {

            TwoFourTreeItem oldParent = parentNode;
            parentNode = new TwoFourTreeItem(parentNode.value1, fourNode.value2);
            parentNode.parent = oldParent.parent;
            // TODO: REHOOK parents children
            if (parentNode.parent.leftChild.value1 == oldParent.value1) {
                parentNode.parent.leftChild = parentNode;
            }
            else if (parentNode.parent.rightChild.value1 == oldParent.value1) {
                parentNode.parent.rightChild = parentNode;
            }
            else if (parentNode.parent.isTwoNode()) {
                parentNode.parent.centerChild = parentNode;
            }
            else {
                if (parentNode.parent.centerLeftChild.value1 == oldParent.value1) {
                    parentNode.parent.centerLeftChild = parentNode;
                }
                else {
                    parentNode.parent.centerRightChild = parentNode;
                }
            }
            // TODO: METHOD FOR ADDING MIDDLE VALUE TO PARENT
        } // Parent cannot reasonably be a four node or leaf node, so only should have to check these cases
        else {
            // create new children of new parent root and rehook
            parentNode.leftChild = new TwoFourTreeItem(fourNode.value1);
            parentNode.rightChild = new TwoFourTreeItem(fourNode.value3);

            parentNode.leftChild.leftChild = fourNode.leftChild;
            parentNode.leftChild.rightChild = fourNode.centerLeftChild;
            parentNode.leftChild.isLeaf = fourNode.isLeaf;
            parentNode.leftChild.parent = parentNode;

            parentNode.rightChild.leftChild = fourNode.centerRightChild;
            parentNode.rightChild.rightChild = fourNode.rightChild;
            parentNode.rightChild.isLeaf = fourNode.isLeaf;
            parentNode.rightChild.parent = parentNode;

        }

        // prevent errors for now
        return parentNode;

    }

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

        if (node.isLeaf) {
            int childFlag = whichChild(node);
            if (childFlag == 1) {
                if (node.parent.isThreeNode()) {
                    if (node.parent.centerChild.isTwoNode()) {
                        node = resize(node, node.parent.value1);
                        node = resize(node, node.parent.centerChild.value1);
                        node.parent = resize(node.parent, node.parent.value1);
                        if (!node.parent.centerChild.isLeaf) {//TODO: THIS IF NECESSARY: SHOULD NOT BE NECESSARY
                                                              }
                        else {
                            node.parent.centerChild = null;
                        }

                    }
                    else {
                        node = resize(node, node.parent.value1);
                        node.parent = resize(node.parent, node.parent.centerChild.value1);
                        node.parent.centerChild = resize(node.parent.centerChild, node.parent.centerChild.value1);
                        if (!node.parent.centerChild.isLeaf) {}
                    }
                }
            }
            if (childFlag == 2) {}
            if (childFlag == 3) {}
            if (childFlag == 4) {}
            if (childFlag == 5) {}
            else {
                System.out.println("Error: parent not found to contain child.");
                return node;
            }
        }
        if (node.leftChild.isTwoNode() && node.rightChild.isTwoNode()) {}
        // if (node.isRoot()) {
        //     if (node.leftChild.isTwoNode() && node.rightChild.isTwoNode()) {
        //
        //         TwoFourTreeItem newNode = new TwoFourTreeItem(node.leftChild.value1, node.rightChild.value1, node.value1);
        //         newNode.isLeaf = node.leftChild.isLeaf & node.rightChild.isLeaf;
        //
        //         newNode.leftChild = node.leftChild.leftChild;
        //         if (newNode.leftChild != null) newNode.leftChild.parent = newNode;
        //
        //         newNode.centerLeftChild = node.leftChild.rightChild;
        //         if (newNode.centerLeftChild != null) newNode.centerLeftChild.parent = newNode;
        //
        //         newNode.centerRightChild = node.rightChild.leftChild;
        //         if (newNode.centerRightChild != null) newNode.centerRightChild.parent = newNode;
        //
        //         newNode.rightChild = node.rightChild.rightChild;
        //         if (newNode.rightChild != null) newNode.rightChild.parent = newNode;
        //
        //         return newNode;
        //
        //     }
        //     else {}
        // }
        // else {
        //     if (node.parent.isTwoNode()) {
        //         System.out.println("Two Node That was unmerged above!");
        //         return node;
        //     }
        //     if (node.parent.isThreeNode()) {
        //         if (node.parent.leftChild.value1 == node.value1) {
        //
        //             if (node.parent.centerChild.isTwoNode() 
        //             && node.parent.rightChild.isTwoNode()) {
        //
        //                 TwoFourTreeItem newNode = new TwoFourTreeItem(  node.value1, 
        //                                                                 node.parent.value1, 
        //                                                                 node.parent.centerChild.value1);
        //                 node.parent = resize(node.parent, node.parent.value1);
        //
        //             }
        //
        //         }
        //         else if (node.parent.centerChild.value1 == node.value1) {}
        //         else {}
        //     }
        //     if (node.parent.isFourNode()) {}
        // }
        // prevent errors for now
        return node;
    }

    private TwoFourTreeItem resize(TwoFourTreeItem node, int value) {

        // determine by looking for the value in the node if it should be resized or not
        boolean resizeDown = searchNodeForValue(value, node);
        TwoFourTreeItem tmp = node;
        TwoFourTreeItem parent = node.parent;

        if (node.isTwoNode()) {
            if (resizeDown) {

                if (node.isRoot()) {
                    node = null;
                    return null;
                }
                System.out.println("Unmerged Two node in delete value!");
                return node;
            }
            else {
                node = new TwoFourTreeItem(tmp.value1, value);
            }
        }
        else if (node.isThreeNode()) {
            if (resizeDown) {
                if (node.value1 == value) {
                    node = new TwoFourTreeItem(tmp.value2);
                }
                else {
                    node = new TwoFourTreeItem(tmp.value1);
                }
            }
            else {
                node = new TwoFourTreeItem(tmp.value1, tmp.value2, value);
            }
        }
        // if node is a four node, it must be resized down
        else {
            if (!resizeDown) {
                System.out.println("Not Good!");
                return node;
            }
            else {
                if (node.value1 == value) {
                    node = new TwoFourTreeItem(tmp.value2, tmp.value3);
                }
                else if (node.value2 == value) {
                    node = new TwoFourTreeItem(tmp.value1, tmp.value3);
                }
                else {
                    node = new TwoFourTreeItem(tmp.value1, tmp.value2);
                }
            }
        }

        node = rehook(node, tmp);
        return node;

    }

    private TwoFourTreeItem search(TwoFourTreeItem currentNode, int key, boolean hasMerge, boolean hasSplit) {

        // split if it is a four node and you are adding a node to the tree
        if (hasSplit && currentNode.isFourNode()) {
            currentNode = splitFourNode(currentNode);
        }
        // else merge if it is a two node and you are deleting a node
        else if (hasMerge && currentNode.isTwoNode()) {
            currentNode = mergeNode(currentNode);
        }

        // if value was found or current node is a leaf, return current node
        if (searchNodeForValue(key, currentNode) || currentNode.isLeaf) return currentNode;

        // otherwise, find children
        TwoFourTreeItem child = closestChild(key, currentNode);
        // if child exists, recurse
        if (child != null) return search(child, key, hasMerge, hasSplit);
        // otherwise closest node is current node, return it
        else return currentNode;


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

        TwoFourTreeItem searchNode = search(root, value, false, true);
        if (searchNodeForValue(value, searchNode)) return true;
        searchNode = resize(searchNode, value);
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
        if (searchNodeForValue(value, searchNode)) return true;

        return false;
    }

    public boolean deleteValue(int value) {

        if (root == null) return false;

        TwoFourTreeItem searchNode = search(root, value, true, false);
        if (!searchNodeForValue(value, searchNode)) return false;
        if (searchNode.isLeaf) {

            searchNode = resize(searchNode, value);

            return true;

        }
        else {
            // find leftmost right node
            TwoFourTreeItem leftmostRight;
            if (searchNode.value1 == value) {
                if (searchNode.isThreeNode()) {
                    leftmostRight = search(searchNode.centerChild, value, false, false);
                }
                else {
                    leftmostRight = search(searchNode.centerLeftChild, value, false, false);
                }
            }
            else if (searchNode.value2 == value) {
                if (searchNode.isThreeNode()) {
                    leftmostRight = search(searchNode.rightChild, value, false, false);
                }
                else {
                    leftmostRight = search(searchNode.centerRightChild, value, false, false);
                }
            }
            else {
                leftmostRight = search(searchNode.rightChild, value, false, false);
            }
            searchNode.value1 = leftmostRight.value1;
            leftmostRight = resize(leftmostRight, leftmostRight.value1);

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
