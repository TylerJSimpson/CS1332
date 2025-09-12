# BST Operations & Skiplists

# Index
 - [Searching in a BST](#searching-in-a-bst)
    - [Binary Search Algorithm Review](#binary-search-algorithm-review)
    - [BST Search Algorithm](#bst-search-algorithm)
 - [Adding to a BST](#adding-to-a-bst)
 - [Removing from a BST](#removing-from-a-bst)
 - [SkipLists](#skiplists)

## Searching in a BST

Linear data structures have O(n) time complexity when searching, BSTs aim to optimize this search capability.

### Binary Search Algorithm Review

Binary search splits at the median on sorted data structures.

Imagine running search(10) on this data structure:

[1][4][6][7][10][14][19][21][22][25][27][37][41]

1st it would split at the median because 10 < 19

[1][4][6][7][10][14]

2nd median split because 10 > 6

[7][10][14]

3rd median 10 is found 10 == 10

The algorithm terminates when the median == target or when it reaches the base case of size 0 i.e. the algorithm terminates without finding the target.

### BST Search Algorithm

The search algorithm in BST is similar but instead of medians the BST uses the roots of subtrees for comparisons.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/BSTSearch1.png)

If we were to `search(10)` in this BST you would first look at root node 21, 7, 14, then reach 10 == 10. If the search number is less than a node it goes to the left child and if the search number is greater than a node it goes to the right child.

If we ran `search(30)` the method would either return False, NULL, throw an exception, etc. 

The efficiency of BST on average is O(log n) but a degenerate tree has O(n) because no data can be cut out and it is essentially searching a linked list.

Moving forward we will classify time complexity into 3 buckets **Best**, **Average**, and **Worst** cases.

The **Best** case in a BST is that the data is at the root resulting in O(1) but this is not very useful in this course. 

**Average** case is tricky to calculate in most cases and we will just trust without proving for the sake of this course. The average case is different from the amortized analysis we looked at previously, it is a probabilistic expected performance i.e. expectation of a random variable cost. We can trust the output as O(log n) for BST search average case.

To summarize average it is a type of analysis where we average (over some probability distribution) the performance of a single operation over all configurations of the data structure.

It is important to bring up just how efficienct O(log n) is. It is extremely efficiecnt and very close to O(1).

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/LogarithmScaleComparison.png)

## Adding to a BST

Here we will look at the `add` method of a BST. 

|Recursive Cases|Search Behavior|Add Behavior|
|---------------|---------------|------------|
|data < node.data|Traverse to left child|Traverse to left child|
|data > node.data|Traverse to right child|Traverse to right child|
|data == node.data|Data found, terminate search|Duplicate found, do not add|
|node is null|Data not in BST, terminate search|Data not in BST, add at that position|

Note that the `search` and `add` behavior are identical for the cases where we are traversing. Then if the data is found in search we do not add because we are not handling BST duplicates in this course.

When a node is added at the position we can assume it will always be a leaf.

When you add you will compare starting with the root each comparison down a search. See example below.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/BSTAddingExample.png)

For adding in a BST we use a technique called **pointer reinforcement**. There is another method called the **look-ahead method**. 

**Look-Ahead**
- Keep track of parent instead of the current node
- Never actually reaches a null node
- Instead, check if child is null before traversing that direction
- Add: If child is null, then we can add there
- Works for simple restructuring operations like add, but becomes less effective for more complex operations

**Pointer Reinforcement**
- Use the return field to restructure tree
    - Return the node that should be in the direction of traversal
    - If you traverse left, return what should be the left child
    - If you traverse right, return what should be the right child
- Set left/right pointer to what is returned
- Responsibility of restructuring is moved from the child's recursive call to the parent

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/ChangingTreeStructureBST.png)

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

Public add wrapper method that just takes in the data to add. The user will call this and we assign the return value to the recursive helper method to the root. This is very important because the root is the pointer to reinforce.

```
public void add(int data):
    root <- rAdd(root, data)
```

Now the private recursive helper method. It must be private since we do not want outside sources calling it. The method takes in a node and the data to add and it returns a node.

```
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

The base case we check if the current node is null. If it is, we increment size and return the new node. Note that the return value is assigned to the root in the non recursive method `add`. 

```
    if curr == null:
        size++
        return new Node(data)
```

If the base case does not hit then we look at the 2 other possible cases where we traverse left or right until we get to a null node.

```
    else if data < curr.data:
        curr.left <- rAdd(curr.left, data)
    else if data > curr.data:
        curr.right <- rAdd(curr.right, data)
```

If the data is greater than the current node's data then set the current node's right child equal to the recursive call on the right child.

Once a node is added we want to return the current node. This is what reinforces the pointers that are not changed. 

```
    return curr
```

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/BSTAddReturnExample.png)


Note that this psuedo code also handles duplicate nodes because it will skip down to the return statement when curr == data.

## Removing from a BST

## SkipLists