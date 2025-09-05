# BST Introduction

# Index
-[Trees](#trees)
    -[Trres](#trees-1)
    -[Binary Trees](#binary-trees)

## Trees

### Trees

So far we have been able to move from ArrayList, Singly-Linked List (w/ tail) and Doubly-Linked List (w/ tail) slowly improving to the point where adding/removing to/from the front/back got to a point of all O(1) with Doubly-Linked Lists. While that is great, a main limitation is that the **access** and **search** were still O(n) for these linear data structures (with the exception of **access** in an ArrayList which is O(1)).

**Access** refers to finding an element with a *known* index. 

**Search** refers to finding an element with an *unknown* index.

Trees are essentially Linked Lists with branching in order to optimize for access and search.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/4_bst_introduction/images/TreeTerminology.png)

Do not assume that data is bidirectional it will mostly flow from **parent** to **child** in this course.

The node without a parent is called the **node** in the image above that would be 5.

If a node has children then it is called an **internal node** which would be 5, 7, and 1 in this image above. If the node does not have chidlren it is called either a **leaf** or an **external node** i.e. nodes 3, 0, 2, 8, 9.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/4_bst_introduction/images/TreeTerminology2.png)

We use family naming conventions with trees including **parent**, **grandparent**, **sibling**, and **cousin**.

Each child can be considered the **root** of their own **subtree**. There are also empty (null) trees and trees with 1 element.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/4_bst_introduction/images/TreeTerminologyDepth.png)

The **depth** of a node is simply the distance from the root.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/4_bst_introduction/images/TreeTerminologyHeight.png)

The **height** of a node refers to the distance from the furthest leaf that can be reached from that node. 

The height of any leaf node is 0. Calculating the height is easiest bottom up from the leaves.

Nodes t, s, ! have height 0 so c is 0 + 1 since all of it's children have a height of 0.

Node g has children a and c which respectively have height 0 and 1. So 1 + 1 = 2.

Additional tree terminology:
- Trees are connected linked structures with no cycles
- Can be implemented in multiple ways i.e. as Arrays but generally we use Linked Lists.
- Trees can be further categorized by some other properties:
    - **Shape** - What is the structure of the nodes in the tree?
    - **Order** - How is the data arranged in the tree? 

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/4_bst_introduction/images/TreeClassification.png)

### Binary Trees

**Binary trees** are trees with a shape restriction where each node can have a maximum of 2 children. There is also a further sub classification called the **binary search tree (BST)** which are binary trees with an imposed order property.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/4_bst_introduction/images/BinaryTreeDefinition.png)

Each node in a binary tree stores at minimum data and references to it's two childre which are referred to as left and right children.

Additional information they can store includes parent reference, depth, height.

A binary tree is **full** if every node has either 0 or 2 children. A binary tree is **complete** if all levels are completely filled left to right except the bottom. A binary tree is **degenerate** if all nodes have 1 child except the leaf which would have 0 i.e. a linked list.

Node references either point to a child or null. 


Iterating through a binary tree:
```java
public void traverse(Node node) {
    if (node != null) {
        traverse(node.left)
        traverse(node.right)
    }
}
```

**Binary search trees (BST)** inherit the properties from binary trees but enforce an ordering policy where left child data is less than parent data which is less than the right child node data. 

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/4_bst_introduction/images/BinarySearchTreeDefinition.png)

Notice 4 < 12 < 19 and 12 < 29 < 103 in the example.

In java the BST must implement the Comparable interface.

BST helps by on average splitting the search space in half per comparison leading to O(log n) runtimes because each comparison tells you the data is to the left or to the right. This O(log n) is guaranteed when the tree is filled in or complete but if it is degenerate this goes up to O(n).

Traversals
- Depth Traversals (Stack-based)
    - Travel down the tree taking a branch as deep as it can go before reaching a null child
        - preorder traversal
        - inorder traversal
        - postorder traversal
- Breadth Traversal (Queue-based)
    - Goes down in depth 1 step at a time exploring everything i.e. everything 1 step away from the root, then 2 steps away from the root...
        - levelorder traversal

Of note when giving a number of nodes you can calculate the number of distinct binary trees and binary search trees using the catalan number. 3 nodes in a binary search tree 123 can form 5 distinct trees and in a binary tree can form 30 distinct trees.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/4_bst_introduction/images/BinarySearchTreeRoots.png)