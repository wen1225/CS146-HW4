/*
*   Author: Wen Yao Ho
*   Class: CS146, HW4
*   Professor: Faramarz Mortezaie
*/

package Tree;

public class BinarySearchTree<T extends Comparable<? super T>> {
    private BinaryNode<T> root;

    /* PUBLIC METHODS */
    public BinarySearchTree() { root = null; }
    public void add(T value) {
        root = insert(root, value);
    }
    public BinaryNode<T> search(BinaryNode<T> node, T value) {
        if (node == null || node.value.compareTo(value) == 0)
            return node;
        else {
            if (value.compareTo(node.value) < 0)
                return search(node.leftNode, value);
            else
                return search(node.rightNode, value);
        }
    }
    public int treeHeight() {
        return height(root);
    }
    public void printInOrder() {
        inOrderTraversal(root);
        System.out.println();
    }
    public void printPostOrder() {
        postOrderTraversal(root);
        System.out.println();
    }
    public void printPreOrder() {
        preOrderTraversal(root);
        System.out.println();
    }
    public int numberOfNodes() {
        //Any traversal method is fine because we have to count every node anyways.
        //T(n) = O(n)
        return countAllNodes(root);

    }

    /* PRIVATE METHODS */
    private BinaryNode<T> insert(BinaryNode<T> node, T value) {
        if (node == null)
            return new BinaryNode<>(value);
        else {
            if (value.compareTo(node.value) < 0) {
                node.leftNode = insert(node.leftNode, value);
            }
            else if (value.compareTo(node.value) > 0) {
                node.rightNode = insert(node.rightNode, value);
            }
            else {
                //do nothing with duplicate values
            }
        }
        return node;

    }
    private int height(BinaryNode<T> root) {
        if (root == null)
            return 0;
        else {
            //Each recursive call means height + 1. That's why we don't explicitly +1 when root is null
            int leftHeight = height(root.leftNode) + 1;
            int rightHeight = height(root.rightNode) + 1;
            return Math.max(leftHeight, rightHeight);
        }
    }
    private boolean passedTest(BinaryNode<T> node) {
        if (node == null)
            return false;
        int leftHeight = height(node.leftNode);
        int rightHeight = height(node.rightNode);
        return Math.abs(leftHeight - rightHeight) <= 1;
    }
    private boolean allPassed(BinaryNode<T> node) {
        boolean isLeftPass = passedTest(node.leftNode);
        boolean isRightPass = passedTest(node.rightNode);
        return isLeftPass == isRightPass;
    }
    private void inOrderTraversal(BinaryNode<T> root) {
        if (root == null)
            return;
        else {
            //If leftNode has child, go deeper to the left
            if (root.hasLeftChild()) {
                inOrderTraversal(root.leftNode);
            }
            //If here, means we're at the leaf and no more left child,
            //so print the value at the leaf
            System.out.print(root.value + " ");
            //Then, check if leaf has right child, and repeat
            if (root.hasRightChild()) {
                inOrderTraversal(root.rightNode);
            }
        }
    }
    private void postOrderTraversal(BinaryNode<T> root) {
        if (root == null)
            return;
        else {
            if (root.hasLeftChild()) {
                postOrderTraversal(root.leftNode);
            }
            if (root.hasRightChild()) {
                postOrderTraversal(root.rightNode);
            }
            System.out.print(root.value + " ");
        }
    }
    private void preOrderTraversal(BinaryNode<T> root) {
        if (root == null)
            return;
        else {
            System.out.print(root.value + " ");
            if (root.hasLeftChild()) {
                preOrderTraversal(root.leftNode);
            }
            if (root.hasRightChild()) {
                preOrderTraversal(root.rightNode);
            }
        }
    }
    private int countAllNodes(BinaryNode<T> root) {
        //Traversal method doesn't matter because we need to visit every node. O(n).
        //I used post-order. "sum" is always 1, so sum always increment by 1 for each
        //recursive call
        if (root == null)
            return 0;
        else {
            int sum = 1;
            if (root.hasLeftChild()) {
                sum += countAllNodes(root.leftNode);
            }
            if (root.hasRightChild()) {
                sum += countAllNodes(root.rightNode);
            }
            return sum;
        }
    }

    private static class BinaryNode<T extends Comparable<? super T>> {
        T value;

        BinaryNode<T> leftNode;
        BinaryNode<T> rightNode;

        BinaryNode() {}
        BinaryNode(T value) {
            this(value, null, null);
        }
        BinaryNode(T value, BinaryNode<T> leftNode, BinaryNode<T> rightNode) {
            this.value = value;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

        boolean hasLeftChild() { return leftNode != null; }
        boolean hasRightChild() { return rightNode != null; }
    }
    public static void main(String[] args) {
        /* Question 2
        //Empty tree, T(1) = O(1) => height = 0
        BinarySearchTree<Integer> root = new BinarySearchTree<>();
        System.out.println(root.treeHeight());

        //Right-skewed tree of height 5, T(n) = O(n) => height = 5
        root.add(1);
        root.add(2);
        root.add(3);
        root.add(4);
        root.add(5);
        System.out.println(root.treeHeight());
        */

        /* Question 3
        //Empty tree, T(1) = O(1) => height = 0
        BinarySearchTree<Integer> root = new BinarySearchTree<>();
        System.out.println(root.treeHeight());

        //left tree height = 0, right tree height = 1. Height difference = 1
        //expect true.
        root.add(1);
        root.add(2);

        //left tree height = 0, right tree height = 2. Height difference = 2 > 1,
        //expect false;
        root.add(3);

        System.out.println(root.passedTest(root.root));
        */

        /* Question 4
        //Empty tree, T(1) = O(1) => height = 0
        BinarySearchTree<Integer> root = new BinarySearchTree<>();

        //complete tree in the making...difference of height in each node is not > 1. So
        // expect allPassed to return true
        root.add(6);
        root.add(3);
        root.add(17);
        root.add(2);
        root.add(5);
        root.add(10);
        root.add(18);
        root.add(1);

        System.out.println(root.passedTest(root.root));
        */

        /* Question 5
        //Empty tree, T(1) = O(1) => height = 0
        BinarySearchTree<Integer> root = new BinarySearchTree<>();

        root.add(50);
        root.add(12);
        root.add(60);
        root.add(7);
        root.add(30);
        root.add(55);
        root.add(70);
        root.add(4);
        root.add(54);
        root.add(58);
        root.add(100);
        root.add(3);
        root.add(6);
        root.add(2);
        root.add(52);

        //expect tree to be printed in sorted order so:
        // 2 3 4 6 7 12 30 50 52 54 55 58 60 70 100
        root.printInOrder();
        */

        /*Question 6
        //Empty tree, T(1) = O(1) => height = 0
        BinarySearchTree<Integer> root = new BinarySearchTree<>();

        root.add(50);
        root.add(12);
        root.add(60);
        root.add(7);
        root.add(30);
        root.add(55);
        root.add(70);
        root.add(4);
        root.add(54);
        root.add(58);
        root.add(100);
        root.add(3);
        root.add(6);
        root.add(2);
        root.add(52);

        //expect tree to be printed such order:
        // 2 3 6 4 7 30 12 52 54 58 55 100 70 60 50
        root.printPostOrder();
        */

        /* Question 7
        //Empty tree, T(1) = O(1) => height = 0
        BinarySearchTree<Integer> root = new BinarySearchTree<>();

        root.add(50);
        root.add(12);
        root.add(60);
        root.add(7);
        root.add(30);
        root.add(55);
        root.add(70);
        root.add(4);
        root.add(54);
        root.add(58);
        root.add(100);
        root.add(3);
        root.add(6);
        root.add(2);
        root.add(52);

        //expect number of nodes to be: 15
        System.out.println(root.numberOfNodes());
        */

        // Question 10
        BinarySearchTree<Integer> root = new BinarySearchTree<>();

        root.add(100);
        root.add(50);
        root.add(20);
        root.add(75);
        root.add(200);
        root.add(150);
        root.add(170);
        root.add(250);

        root.printPostOrder();
        root.printPreOrder();
    }
}
