# AVLs

# Index
- [AVLs](#avls-1)
    - [BST Review](#bst-review)
        - [BST Search](#bst-search)
        - [BST Add](#bst-add)
        - [BST Remove](#bst-remove)
    - [Introduction to AVLs](#introduction-to-avls)
    - [AVL Rotations](#avl-rotations)

## AVLs

### BST Review

#### BST Search

O(n) time complexity when searching for a piece of data is a drawback of linear data structures. Binary search aims to optimize search capabilities.

Recall in binary search you can search for an element in Olog(n) time by sub dividing the subset of data you are examining.

For example, in a sorted list check the median and if the data is larger check the right, if it is lower then check the left half and repeat.

Recall in BST it is similar but you use the roots of subtrees to find elements. 1st check the root of the tree and if larger go down to the right child and continue.

Searching a degenerate tree is the worst case time complexity as it is essentially searching a linked list resulting in O(n).

#### BST Add

|Recursive Cases|Search Behavior|Add Behavior|
|---------------|---------------|------------|
|data < node.data|Traverse to left child|Traverse to left child|
|data > node.data|Traverse to right child|Traverse to right child|
|data == node.data|Data found, terminate search|Duplicate found, do not add|
|node is null|Data not in BST, terminate search|Data not in BST, add at that position|

Recall in this course we are not handling duplicates for the add method in BSTs.

Adding the following data to a BST:

24, 13, 6, 21, 40, 30, 8, 45, 33

24 becomes the root. 13 < 24 add as left child of 24. 6 < 24, 6 < 13 add as left child of 13. etc.

**Pointer reinforcement** is used when implementing the add method in this course and is used in place of the **look-ahead** method to minimize edge cases on more complex operations.

**Look-ahead**
- keep track of parent instead of the current node
- never actually reaches a null node
- instead, checks if child is null before traversing that direction
- add: if child is null, then we can add there
- works for simple restructuring operations like add, but becomes less effective for more complex operations (many edge cases)

**Pointer Reinforcement**
- use the return field to restructure the tree
    - return the node that should be in the direction of traversal
    - i.e. if you traverse left, return what should be the left child
- set left/right pointer to what is returned
- responsibility of restructuring is moved from the child's recursive call to the parent

```
public void add(int data):
    root <- rAdd(root, data)
    
private Node rAdd(Node curr, int data):
    if curr == null:
        size++
        return new Node(data)
    else if data < curr.data:
        curr.left <- rAdd(curr.left, data)
    else if data > curr.data:
        curr.right <- rAdd(curr.right, data)
    return curr
```

The public add wrapper method is the method the user calls in the bst. Assign the return value from the recursive helper method to the root. The root is also the pointer to reinforce so without this step there is no way to make changes to the root of the tree.

rAdd is private to stop external sources from making changes. It takes in the node and the data to add and returns a node. First check the base case of the current node being null. This results in creating the new node, incrementing size, and returning the node.

Note the importance of `return curr` this is what reinforces the pointers that were not changed. Once the new node is added the method returns all other nodes that were unchanged back to their parents. This also handles detecting and returning duplicates.

#### BST Remove

|Recursive Cases|Search Behavior|Add Behavior|Remove Behavior|
|---------------|---------------|------------|---------------|
|data < node.data|Traverse to left child|Traverse to left child|Traverse to left child|
|data > node.data|Traverse to right child|Traverse to right child|Traverse to right child|
|data == node.data|Data found, terminate search|Duplicate found, do not add|Data found, remove it|
|node is null|Data not in BST, terminate search|Data not in BST, add at that position|Data not in BST, throw exception|

In removal search is the same and the easiest case is throwing an exception when data is not found.

When data is found there are 3 different cases that can occur.

**Zero-Child Case** 

The node being removed has no children. Then the node's parent can have that pointer set to null without disrupting the structure of the tree.

**One-Child Case**

In this case the data in the Node's child cannot be lost. The pointer of the Node's parent must be set to the Node's child i.e. pointing to it's grandchild.

**Two-Child Case**

The Node pointers cannot be nulled or redirected. The node with 2 children has a more prominant position compared to those with 1 or no children. The remove operations will relegate the remove operations to a node with a less prominant position and copies the data. But how can you choose the lower level node? There are 2 candidates **predecessor** and **successor**.

**Predecessor**

The next largest element that is still smaller than the data we want to remove. 

In this case you traverse left from the node you want to remove and then continue right until you reach a null which will be the next lowest value.

![](/AVL%20and%202-4%20Trees,%20Divide%20and%20Conquer%20Algorithms/8_avls/images/BSTRemovePredecessor.png)

**Successor**

The next smallest element that is still larger than the data we want to remove.

In this case you traverse right from the node then you continue left until you reach a null which will be the next highest value.

![](/AVL%20and%202-4%20Trees,%20Divide%20and%20Conquer%20Algorithms/8_avls/images/BSTRemoveSuccessor.png)

![](/AVL%20and%202-4%20Trees,%20Divide%20and%20Conquer%20Algorithms/8_avls/images/BSTRemoving1.png)

```
public int remove(int data):
    Node dummy <- new Node(-1)
    root <- rRemove(root, data, dummy)
    return dummy.data

private Node rRemove(Node curr, int data, Node dummy):
    if curr = null:
        // data not found case
    else if data < curr.data:
        curr.left <- rRemove(curr.left, data, dummy)
    else if data > curr.data:
        curr.right <- rRemove(curr.right, data, dummy)
    else:
        // data found case
    return curr
```

The public remove wrapper method takes in the data to be removed and returns some data that was removed. It must have a way of passing data back through the recursive step so a dummy node is created to be used as a storage container. 

The private remove method has the same right and left pointer reinforcement logic used in the add method. There are 2 different base cases, if the node is null or if the node contains the data we want. 

Data found case:
```
dummy.data <- curr.data
decrement size
if 0 children:
    return null
else if left child is non-null:
    return left child
else if right child is non-null:
    return right child
else:
    Node dummy2 <- new Node(-1)
    curr.right <- removeSuccessor(curr.right, dummy2)
    curr.data <- dummy2.data
```

Set the dummy data to current node data then check the 3 different child cases.

If there are 2 children then there is another call to the recursive method to remove the predecessor or successor. Here you setup a 2nd dummy node to hold the pred/successor data then get that data. To do this we make a call to a helper method that will find and remove the pred/successor. This helper method should complete both tasks at once to be most efficient. This method will also use pointer reinforcement.

Successor helper method:
```
private Node removeSuccessor(Node curr, Node dummy):
    if curr.left == null:
        dummy.data <- curr.data
        return curr.right
    else:
        curr.left <- removeSuccessor(curr.left, dummy)
```

Takes in a node and dummy node. The base case is the left child being null where we return the right child which covers the no child and 1 child remove cases. If the left child is not null then make a recursive call to that left child and the return reinforces the pointers in unchanged nodes. Once there is a return then set the current node's data to the successor method call dummy node data. 

Now the current node contains the data we want and `return curr` in the private rRemove method returns us up the stack reinforcing the pointers that were not changed. Then we return back to the public method where the dummy nodes data is returned.

### Introduction to AVLs

AVLs are a sub classification of BSTs. They share the same order and shape properties, though AVLs have additional shape properties restricting the height of the tree.

AVLs are self balancing BSTs that are used to eliminate O(n) worst case. The worst case for search/add/remove becomes O(log n).

Rotations are performed on nodes that are "unbalanced".

`
Height(node) = max{Height(left), Height(right)} + 1
`

Base case: Height(leaf) = 1, Height(null) = -1

`
BalanceFactor(node) = Height(left) - Height(right)
`

BalanceFactor and Height are stored inside the node to make calculations O(1) as opposed to O(n).

![](/AVL%20and%202-4%20Trees,%20Divide%20and%20Conquer%20Algorithms/8_avls/images/AVLHeightBF1.png)

When calculating balance factors it is important to calculate from the bottom up since each node relies on the heights of it's children. 

Node is unbalanced if |BF| > 1

Node is balanced if BF = -1, 0, 1

If BF is < 0 it is right heavy i.e. has more nodes in the right tree such as in node 4 in the example above with BF = -2.

The goal is to minimize the depths of all nodes which minimizes the unbalance encountered in the tree. We must except some lenience i.e. `|node.balanceFactor| <= 1` because there are cases where we must unbalance the tree i.e. if it was a root with 1 child on either side no matter where we add the 4th node it will be unbalanced with a |BF| of 1.

![](/AVL%20and%202-4%20Trees,%20Divide%20and%20Conquer%20Algorithms/8_avls/images/AVLLazyUpdate.png)

In the example above if we tolerate no imbalance we can make a simple Add O(n). If we tolerate the standard BST addition or "Lazy update" then we save on costs. AVLs hit a sweet spot between allowing some imbalance while keeping operations efficient. The height of an AVL tree is at most 1.44log(n).

### AVL Rotations

