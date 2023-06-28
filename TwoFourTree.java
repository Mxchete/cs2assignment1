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

            if (value1 == value) return true;
            else if (isThreeNode() || isFourNode() && this.value2 == value) return true;
            else if (isFourNode() && value3 == value) return true;
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

        // TODO: WORKING HERE NOW
        private void fuse(TwoFourTreeItem sibling) {
            // if (value1 == parent.leftChild.value1) {
            //     if (parent.isThreeNode()) {
            //         append(parent.value1);
            //         parent.resize(parent.value1);
            //         append(parent.centerChild.value1);
            //         parent.centerChild = null;
            //     }
            // }
            // else if (value1 == parent.rightChild.value1) {
            //     rightChild = newChild;
            // }
            // else if (isThreeNode()) {
            //     centerChild = newChild;
            // }
            // else if (value1 == parent.centerLeftChild.value1) {
            //     centerLeftChild = newChild;
            // }
            // else {
            //     centerRightChild = newChild;
            // }
            int parentVal = -1;
            if (parent.isThreeNode()) {
                if (parent.leftChild.value1 == value1 
                    || parent.leftChild.value1 == sibling.value1 
                    && parent.centerChild.value1 == value1) {
                    parentVal = parent.value1;
                }
                else {
                    parentVal = parent.value2;
                }
            }
            else {
                if (parent.leftChild.value1 == value1 
                    || parent.leftChild.value1 == sibling.value1 
                    && parent.centerLeftChild.value1 == value1) {
                    parentVal = parent.value1;
                    }
                else if (parent.centerLeftChild.value1 == value1 
                        || parent.centerRightChild.value1 == value1 
                        && parent.rightChild.value1 != sibling.value1) {
                    parentVal = parent.value2;
                }
                else {
                    parentVal = parent.value3;
                }
            }
            if (parentVal != -1) parent = parent.resize(parentVal);
            TwoFourTreeItem fusedNode = new TwoFourTreeItem(parentVal, value1, sibling.value1);
            if (parent.isTwoNode()) {
            }
        }

        private TwoFourTreeItem rotate(sibling) {}

        private TwoFourTreeItem moveUp(TwoFourTreeItem node) {

            if (leftChild.value1 == node.value1) {
                if (isTwoNode()) {
                    append(node.value1);
                    if (!node.isLeaf) {
                        leftChild = node.leftChild;
                        leftChild.parent = this;
                        centerChild = node.rightChild;
                        centerChild.parent = this;
                    }
                }
                else {
                    append(node.value1);
                    if (!node.isLeaf) {
                        leftChild = node.leftChild;
                        leftChild.parent = this;
                        centerLeftChild = node.rightChild;
                        centerLeftChild.parent = this;
                        centerRightChild = centerChild;
                        centerChild = null;
                    }
                }
            }
            else if (rightChild.value1 == node.value1) {
                if (isTwoNode()) {
                    append(node.value1);
                    if (!node.isLeaf) {
                        centerChild = node.leftChild;
                        centerChild.parent = this;
                        rightChild = node.rightChild;
                        rightChild.parent = this;
                    }
                }
                else {
                    append(node.value1);
                    if (!node.isLeaf) {
                        centerRightChild = node.leftChild;
                        centerRightChild.parent = this;
                        rightChild = node.rightChild;
                        rightChild.parent = this;
                        centerLeftChild = centerChild;
                        centerChild = null;
                    }
                }
            }
            else {
                append(node.value1);
                if (!node.isLeaf) {
                    centerLeftChild = node.leftChild;
                    centerLeftChild.parent = this;
                    centerRightChild = node.rightChild;
                    centerRightChild.parent = this;
                    centerChild = null;
                }
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

        // TODO: FINISH THIS CLUSTERFUCK
        private TwoFourTreeItem findSibling() {
            // if (value1 != parent.leftChild.value1 && !parent.leftChild.isTwoNode()) {
            //     if (parent.isThreeNode() && value1 != parent.centerChild.value1) return parent.centerChild;
            //     else if (parent.isFourNode() && value1 == parent.centerLeftChild.value1) return parent.leftChild;
            // }
            // if (value1 != parent.rightChild.value1 && !parent.rightChild.isTwoNode()) {
            //     if (parent.isThreeNode() && value1 != parent.centerChild.value1) return parent.centerChild;
            //     else if (parent.isFourNode() && value1 == parent.centerRightChild.value1) return parent.rightChild;
            // }
            // if (parent.isThreeNode() && value1 != parent.centerChild.value1 && !parent.centerChild.isTwoNode()) {
            //     return parent.centerChild;
            // }
            // if (value1 != parent.centerLeftChild.value1 && !parent.centerLeftChild.isTwoNode()) {
            //     if (value1 != parent.centerRightChild.value1 && !parent.centerRightChild.isTwoNode())
            //         return parent.centerLeftChild;
            // }
            // if (value1 != parent.centerRightChild.value1 && !parent.centerRightChild.isTwoNode()) {
            //     return parent.centerRightChild;
            // }
            TwoFourTreeItem sibling = null;
            if (value1 != parent.leftChild.value1 && !parent.leftChild.isTwoNode()) {
                if (value1 > parent.leftChild.value1) sibling = parent.leftChild; }
            if (value1 != parent.rightChild.value1 && !parent.rightChild.isTwoNode()) {
                if (value1 < parent.leftChild.value1) sibling = parent.rightChild; }
            if (parent.isThreeNode() && value1 != parent.centerChild.value1 && !parent.centerChild.isTwoNode()) {
                sibling = parent.centerChild; }
            else if (parent.isFourNode()) {
                
                if (value1 != parent.centerLeftChild.value1 && !parent.centerLeftChild.isTwoNode()) {
                    if (value1 < parent.rightChild.value1) sibling = parent.centerLeftChild; }
                if (value1 != parent.centerRightChild.value1 && !parent.centerRightChild.isTwoNode()) {
                    if (value1 > parent.leftChild.value1) sibling = parent.centerRightChild; }
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
        // if (node.isLeaf) {
        //     int childFlag = whichChild(node);
        //     if (childFlag == 1) {
        //         if (node.parent.isThreeNode()) {
        //             if (node.parent.centerChild.isTwoNode()) {
        //                 node.resize(node.parent.value1);
        //                 node.resize(node.parent.centerChild.value1);
        //                 node.parent.resize(node.parent.value1);
        //                 if (!node.parent.centerChild.isLeaf) {//TODO: THIS IF NECESSARY: SHOULD NOT BE NECESSARY
        //                                                       }
        //                 else {
        //                     node.parent.centerChild = null;
        //                 }
        //
        //             }
        //             else {
        //                 node.resize(node.parent.value1);
        //                 node.parent.resize(node.parent.centerChild.value1);
        //                 node.parent.centerChild.resize(node.parent.centerChild.value1);
        //                 if (!node.parent.centerChild.isLeaf) {}
        //             }
        //         }
        //     }
        //     else if (childFlag == 2) {}
        //     else if (childFlag == 3) {}
        //     else if (childFlag == 4) {}
        //     else if (childFlag == 5) {}
        //     else {
        //         // System.out.println("Error: parent not found to contain child.");
        //         return node;
        //     }
        // }
        // else if (node.leftChild.isTwoNode() && node.rightChild.isTwoNode() && !node.isRoot()) {
        //     node.isLeaf = node.leftChild.isLeaf & node.rightChild.isLeaf;
        //     node = node.moveUp(node.leftChild);
        //     node = node.moveUp(node.rightChild);
        // }
        // else {
        //
        // }
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



    private TwoFourTreeItem search(TwoFourTreeItem currentNode, int key, boolean hasMerge, boolean hasSplit) {

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

            System.out.println("Sono qui. Il node e' una leaf.");
            searchNode.resize(value);

            return true;

        }
        else {
            // find leftmost right node
            TwoFourTreeItem leftmostRight;
            // System.out.println("Il node non e' una leaf.");
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
            leftmostRight.resize(leftmostRight.value1);

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
