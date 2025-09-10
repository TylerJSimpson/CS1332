import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Your implementation of the pre-order, in-order, and post-order
 * traversals of a tree.
 */
public class Traversals<T extends Comparable<? super T>> {

    /**
     * DO NOT ADD ANY GLOBAL VARIABLES!
     */

    /**
     * Given the root of a binary search tree, generate a
     * pre-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the pre-order traversal of the tree.
     */
    public List<T> preorder(TreeNode<T> root) {
        List<T> list = new ArrayList<>();
        preorder(root, list);
        return list;
    }

    private void preorder(TreeNode<T> node, List<T> list) {
        // base case
        if (node == null) {
            return;
        }

        list.add(node.getData());
        preorder(node.getLeft(), list);
        preorder(node.getRight(), list);

    }

    /**
     * Given the root of a binary search tree, generate an
     * in-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the in-order traversal of the tree.
     */
    public List<T> inorder(TreeNode<T> root) {
        List<T> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }

    private void inorder(TreeNode<T> node, List<T> list) {
        // base case
        if (node == null) {
            return;
        }

        inorder(node.getLeft(), list);
        list.add(node.getData());
        inorder(node.getRight(), list);
    }

    /**
     * Given the root of a binary search tree, generate a
     * post-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the post-order traversal of the tree.
     */
    public List<T> postorder(TreeNode<T> root) {
        List<T> list = new ArrayList<>();
        postorder(root, list);
        return list;

    }

    private void postorder(TreeNode<T> node, List<T> list) {
        // base case
        if (node == null) {
            return;
        }

        postorder(node.getLeft(), list);
        postorder(node.getRight(), list);
        list.add(node.getData());
    }

}