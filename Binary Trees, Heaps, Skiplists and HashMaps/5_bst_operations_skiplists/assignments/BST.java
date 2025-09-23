import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The new data should become a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Should be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to add to the tree.
     * @throws java.lang.IllegalArgumentException If data is null.
     */
    public void add(T data) {

        // Throw null data error
        if (data == null) {
            throw new IllegalArgumentException("Error: null data cannot be added.");
        }

        // Recursive call
        root = add(root, data);
    }

    private BSTNode<T> add(BSTNode<T> node, T data) {
        
        // Base case: data does not exist
        if (node == null) {
            size++;
            return new BSTNode<T>(data);
        }

        // Compare data to node (+ go right, - go left)
        int comparison = data.compareTo(node.getData());
        if (comparison < 0) { // go left
            node.setLeft(add(node.getLeft(), data));
        }
        else if (comparison > 0) { // go right
            node.setRight((add(node.getRight(), data)));
        }
        else {
        }
        
        // Base case: duplicate data exists
        return node;

    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the SUCCESSOR to
     * replace the data. You should use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do NOT return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to remove.
     * @return The data that was removed.
     * @throws java.lang.IllegalArgumentException If data is null.
     * @throws java.util.NoSuchElementException   If the data is not in the tree.
     */
    public T remove(T data) {
        // Throw null data error
        if (data == null) {
            throw new IllegalArgumentException("Error: null data cannot be added.");
        }

        Ref<T> removed = new Ref<>();
        root = remove(root, data, removed);
        if (removed.val == null) {
            throw new NoSuchElementException("Error: data not found.");
        }
        size --;
        return removed.val;
    }

    private BSTNode<T> remove(BSTNode<T> node, T data, Ref<T> removed) {
        // Base case: data does not exist
        if (node == null) {
            return null;
        }

        // Compare data to node (+ go right, - go left)
        int comparison = data.compareTo(node.getData());
        if (comparison < 0) { // go left
            node.setLeft(remove(node.getLeft(), data, removed));
            return node;
        }
        else if (comparison > 0) { // go right
            node.setRight(remove(node.getRight(), data, removed));
            return node;
        }
        
        // Found node
        removed.val = node.getData();

        // No children

        // One child

        // Two children
    }

    private static class Ref<E> {
        E val;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}