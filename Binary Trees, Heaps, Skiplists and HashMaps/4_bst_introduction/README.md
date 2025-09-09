# BST Introduction

# Index
- [Trees](#trees)
    - [Trres](#trees-1)
    - [Binary Trees](#binary-trees)
- [Tree Traversals](#tree-traversals)
    - [Depth (Recursive) Traversals](#depth-recursive-traversals)
        - [Preorder Traversal](#preorder-traversal)
        - [Postorder Traversal](#postorder-traversal)
        - [Inorder Traversal](#inorder-traversal)
    - [Breadth (Iterative) Traversals](#breadth-iterative-traversals)
        - [Levelorder Traversal](#levelorder-traversal)
    - [Choosing a Traversal](#choosing-a-traversal)

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

## Tree Traversals

### Depth (Recursive) Traversals

Traversals essentially accomplish what an iterable/iterator in Java does but with a different ordering of events.

#### Preorder Traversal

```
preorder (Node node):
    if node is not null:
        look at the data in the node
        recurse left
        recurse right
```

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/4_bst_introduction/images/PreorderRecursiveTracing.png)

In the image we would then go down to the right child from the root and continue resulting in this list:

[13,7,5,11,29,19]

The preorder list generated is useful in the case you want to create an exact copy of the current BST. **The preorder traversal uniquely describes the structure of the BST.**

#### Postorder Traversal

```
postorder (Node node):
    if node is not null:
        recurse left
        recurse right
        look at the data in the node
```

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/4_bst_introduction/images/PostorderRecursiveTracing.png)

Final list:

[5,11,7,19,29,13]

This traversal is useful where we remove data since generally you remove from the leaves.

#### Inorder Traversal

```
inorder (Node node):
    if node is not null:
    recurse left
    look at the data in the node
    recurse right
```

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/4_bst_introduction/images/InorderRecursiveTracing.png)

Final list:

[5,7,11,13,19,29]

Notice the returned list has the nodes listed in sorted order. This depends on the BST having the left < middle < right structure.

### Breadth (Iterative) Traversals

There are multiple breadth traversals but we will only be covering **levelorder** here. Instead of utilizing the recursive stack like depths traversals, breathd traversals utilize a queue to access each data in an order sorted by closeness to the root.

#### Levelorder Traversal

The goal here is to get a list of all the data in the levels or **depths** they appear in the tree.

```
levelorder():
    Create Queue q
    Add root to q
    while q is not empty:
        Node curr = q.dequeue()
        if curr is not null:
            q.enqueue(curr.left)
            q.enqueue(curr.right)
```

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/4_bst_introduction/images/LevelorderIterativeTracing1.png)

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/4_bst_introduction/images/LevelorderIterativeTracing2.png)

Final list:

[20,15,32,13,29,50,6,31,67]


### Choosing a Traversal

- **Preorder**: The preorder traversal uniquely identifies a BST, when adding the data to an empty tree in the order given by this traversal. Although we haven't discussed how the add method works in BSTs, you can double check this feature of preorder traversal in the visualization tool. Most definitely, check this feature out  after you've learned about the adding procedure. The preorder traversal is a hybrid depth approach that biases towards giving you data closer to the root faster, than data in leaf nodes.

- **Inorder**: The most notable property of the inorder traversal is that if implemented on a BST, the data is returned in a sorted order. However, please note that the inorder traversal is the only one of the four traversals that does not uniquely define the BST. If the traversal is operating on a general Binary Tree, then it will give you the data on a left-to-right basis (if the tree is properly spaced out when drawn).

- **Postorder**: The postorder traversal is similar to preorder in that it uniquely identifies the BST. One useful feature of the postorder traversal is that if you were to remove data from this ordering, you would only remove leaf nodes. This particular property will come in handy when you learn the remove procedure of a BST. The postorder traversal is a hybrid depth approach that biases towards giving you data in leaf nodes faster, than data closer to the root.

- **Levelorder**: This traversal is the black sheep in implementation, but it's also the easiest to understand conceptually. The levelorder traversal gives you the data in an ordering sorted based on the depth of the data. Thus, it is the fully realized version of the preorder traversal where it gives you full control of getting internal nodes before deeper leaves. As you might have guessed, it also uniquely defines a BST.