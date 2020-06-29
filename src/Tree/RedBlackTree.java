package Tree;

public class RedBlackTree <T extends Comparable<T>> {
    private RedBlackNode<T> root;
    private RedBlackNode<T> nil;
    private enum COLOR { BLACK, RED; }
    private static class RedBlackNode<T extends Comparable<T>> {
        //Member Variables
        T key;
        COLOR color;
        RedBlackNode<T> parent;
        RedBlackNode<T> left;
        RedBlackNode<T> right;
    }
    public RedBlackTree() {
        nil = new RedBlackNode<>();
        //nil node is always black
        nil.color = COLOR.BLACK;
        nil.left = null;
        nil.right = null;
        nil.parent = null;

        /* why this way doesn't work? but root = nil does? hmmm
        root = new RedBlackNode<>();
        root.color = COLOR.BLACK;
        //when root node is first created,  it has no child, so point both to nil
        root.left = nil;
        root.right = nil;
        //parent of root node is always nil node
        root.parent = nil;
        */
        root = nil;

    }
    public void printTree() {
        print(this.root, "", true);
    }
    private void print(RedBlackNode<T> root, String indent, boolean last) {
        if (root != nil) {
            System.out.print(indent);
            if (last) {
                if (root.parent == null) {
                    System.out.print("ROOT-");
                }
                else {
                    System.out.print("R----");
                }
                indent += "    ";
            }
            else {
                System.out.print("L----");
                indent += "|    ";
            }
            String c = (root.color == COLOR.RED) ? "RED" : "BLACK";
            System.out.println(root.key + "(" + c + ")");
            print(root.left, indent, false);
            print(root.right, indent, true);
        }
    }
    private void rotateLeft(RedBlackNode<T> x) {
        RedBlackNode<T> y = x.right;
        x.right = y.left;
        if (y.left != nil) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        }
        else if (x == x.parent.left) {
            x.parent.left = y;
        }
        else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }
    private void rotateRight(RedBlackNode<T> x) {
        RedBlackNode<T> y = x.left;
        x.left = y.right;
        if (y.right != nil) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        }
        else if (x == x.parent.right) {
            x.parent.right = y;
        }
        else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }
    public void insert(T key) {
        insertNode(key);
    }
    private void insertNode(T key) {
        RedBlackNode<T> newNode = new RedBlackNode<>();
        newNode.key = key;
        //if insert is called, means newNode is not the root node, so its parent is not nil.
        //"I guess I could still technically let it point to nil, but we'll see"
        newNode.parent = null;
        newNode.left = nil;
        newNode.right = nil;
        //new node insertion must always be red.
        newNode.color = COLOR.RED;

        //Create temporary node pointers
        RedBlackNode<T> y = null;
        //this.root refers to the caller's (our RBT object) root node. So, point x to the caller's root.
        RedBlackNode<T> x = this.root;

        while (x != nil) {
            y = x;
            //if newNode's key < root's key
            if (newNode.key.compareTo(x.key) < 0) {
                x = x.left;
            }
            else if (newNode.key.compareTo(x.key) > 0) {
                x = x.right;
            }
            else {
                //do nothing with duplicate keys
            }
        }

        newNode.parent = y;
        if (y == null) {
            root = newNode;
        }
        else if (newNode.key.compareTo(y.key) < 0) {
            y.left = newNode;
        }
        else {
            y.right = newNode;
        }

        if (newNode.parent == null) {
            newNode.color = COLOR.BLACK;
            return;
        }

        //Fix possible violation
        insertFixUp(newNode);
    }
    public void insertFixUp(RedBlackNode<T> currNode) {
        RedBlackNode<T> tempNode;
        while (currNode.parent.color == COLOR.RED) {
            if (currNode.parent == currNode.parent.parent.right) {
                tempNode = currNode.parent.parent.left;
                if (tempNode.color == COLOR.RED) {
                    tempNode.color = COLOR.BLACK;
                    currNode.parent.color = COLOR.BLACK;
                    currNode.parent.parent.color = COLOR.RED;
                    currNode = currNode.parent.parent;
                }
                else {
                    if (currNode == currNode.parent.left) {
                        currNode = currNode.parent;
                        rotateRight(currNode);
                    }
                    currNode.parent.color = COLOR.BLACK;
                    currNode.parent.parent.color = COLOR.RED;
                    rotateLeft(currNode.parent.parent);
                }
            }
            else {
                tempNode = currNode.parent.parent.right;
                if (tempNode.color == COLOR.RED) {
                    tempNode.color = COLOR.BLACK;
                    currNode.parent.color = COLOR.BLACK;
                    currNode.parent.parent.color = COLOR.RED;
                    currNode = currNode.parent.parent;
                }
                else {
                    if (currNode == currNode.right) {
                        currNode = currNode.parent;
                        rotateLeft(currNode);
                    }
                    currNode.parent.color = COLOR.BLACK;
                    currNode.parent.parent.color = COLOR.RED;
                    rotateRight(currNode.parent.parent);
                }
            }
            if (currNode == root) {
                break;
            }
        }
        root.color = COLOR.BLACK;
    }
    private void transplant(RedBlackNode<T> u, RedBlackNode<T> v) {
        if (u.parent == null) {
            root = v;
        }
        else if (u == u.parent.left) {
            u.parent.left = v;
        }
        else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }
    public void delete(T key) {
        deleteNode(this.root, key);
    }
    private void deleteNode(RedBlackNode<T> n, T key) {
        RedBlackNode<T> z = nil;
        RedBlackNode<T> x, y;
        while (n != nil) {
            if (n.key == key) {
                z = n;
            }
            if (n.key.compareTo(key) <= 0) {
                n = n.right;
            }
            else {
                n = n.left;
            }
        }
        if (z == nil) {
            System.out.println("Node not found.");
            return;
        }
        y = z;
        COLOR yInitialColor = y.color;
        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right);
        }
        else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left);
        }
        else {
            y = min(z.right);
            yInitialColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            }
            else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yInitialColor == COLOR.BLACK)
            deleteFixUp(x);
    }
    //"I dont get why we need to pass n tho, why can't we just call it w/ no arg?"
    public RedBlackNode<T> min(RedBlackNode<T> n) {
        while (n.left != nil)
            n = n.left;
        return n;
    }
    public void deleteFixUp(RedBlackNode<T> x) {
        RedBlackNode<T> s;
        while (x != root && x.color == COLOR.BLACK) {
            if (x == x.parent.left) {
                s = x.parent.left;
                if (s.color == COLOR.RED) {
                    s.color = COLOR.BLACK;
                    x.parent.color = COLOR.RED;
                    rotateLeft(x.parent);
                    s = x.parent.right;
                }
                if (s.left.color == COLOR.BLACK && s.right.color == COLOR.BLACK) {
                    s.color = COLOR.RED;
                    x = x.parent;
                }
                else {
                    if (s.right.color == COLOR.BLACK) {
                        s.left.color = COLOR.BLACK;
                        s.color = COLOR.RED;
                        rotateRight(s);
                        s = x.parent.right;
                    }
                    s.color = x.parent.color;
                    x.parent.color = COLOR.BLACK;
                    s.right.color = COLOR.BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            }
            else {
                s = x.parent.left;
                if (s.color == COLOR.RED) {
                    s.color = COLOR.BLACK;
                    x.parent.color = COLOR.RED;
                    rotateRight(x.parent);
                    s = x.parent.left;
                }
                if (s.left.color == COLOR.BLACK && s.right.color == COLOR.BLACK) {
                    s.color = COLOR.RED;
                    x = x.parent;
                }
                else {
                    if (s.left.color == COLOR.BLACK) {
                        s.right.color = COLOR.BLACK;
                        s.color = COLOR.RED;
                        rotateLeft(s);
                        s = x.parent.left;
                    }
                    s.color = x.parent.color;
                    x.parent.color = COLOR.BLACK;
                    s.left.color = COLOR.BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = COLOR.BLACK;
    }
    public void printPreOrder() {
        preOrderTraversal(root);
        System.out.println();
    }
    private void preOrderTraversal(RedBlackNode<T> root) {
        if (root == null)
            return;
        else {
            System.out.println(root.key + "    " + root.color);
            if (root.left != nil) {
                preOrderTraversal(root.left);
            }
            if (root.right != nil) {
                preOrderTraversal(root.right);
            }
        }
    }
    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(100);
        tree.insert(200);
        tree.insert(150);
        tree.insert(170);
        tree.insert(165);
        tree.insert(180);
        tree.insert(220);
        tree.insert(163);
        tree.insert(164);

        //tree.printTree();
        tree.printPreOrder();
    }
}