# BST Operations & Skiplists

# Index
 - [Searching in a BST](#searching-in-a-bst)
    - [Binary Search Algorithm Review](#binary-search-algorithm-review)
    - [BST Search Algorithm](#bst-search-algorithm)
 - [Adding to a BST](#adding-to-a-bst)
 - [Removing from a BST](#removing-from-a-bst)
    - [Zero-Child Case](#zero-child-case)
    - [One-Child Case](#one-child-case)
    - [Two-Child Case](#two-child-case)
        - [Predecessor](#predecessor)
        - [Successor](#successor)
    - [Pointer Reinforcement Removal](#pointer-reinforcement-removal)
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

|Recursive Cases|Search Behavior|Add Behavior|Remove Behavior|
|---------------|---------------|------------|---------------|
|data < node.data|Traverse to left child|Traverse to left child|Traverse to left child|
|data > node.data|Traverse to right child|Traverse to right child|Traverse to right child|
|data == node.data|Data found, terminate search|Duplicate found, do not add|Data found, remove it|
|node is null|Data not in BST, terminate search|Data not in BST, add at that position|Data not in BST, throw exception|

There are several different cases to consider when removing an element.

The remove method is similar to search in that you must first find an element before it can be removed.

If data is found there are 3 different types of removals that must be considered.

### Zero-Child Case

The simplest removal is when the node has no children. Set the parent of the node's pointer to that child to null. In the example below the right child pointer of 40 is set to null.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/ZeroChildCase.png)

### One-Child Case

The data in the node's child cannot be lost in the case of there being a child. Instead the pointer of the parent (13 in the example) to the child (node 8) so instead of pointing to it's child it points to it's grandchild skipping the child altogether.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/OneChildCase.png)

### Two-Child Case

In this case the pointers cannot be null'd our or redirected without the risk of losing data. There are 2 different way to go about this **successor** and **predecessor**.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/TwoChildCase.png)

#### Predecessor

To arrive at the predecessor you first go to the left child of the root then you go right until you reach a null node this will get you to the predecessor the node that is closest to the root but lower.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/TwoChildCasePredecessor.png)

#### Successor

To find the successor you go to the right child and then to the left until you reach a left null child.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/TwoChildCaseSuccessor.png)

### Pointer Reinforcement Removal

```
public int remove(int data):
    Node dummy <- new Node(-1)
    root <- rRemove(root, data, dummy)
    return dummy.data

private Node rRemove(Node curr, int data, Node dummy):
    if curr == null:
        // data not found case
    else if data < curr.data:
        curr.left <- rRemove(curr.left, data, dummy)
    else if data > curr.data:
        curr.right <- rRemove(curr.right, data, dummy)
    else:
        // data found case
    return curr
```

The public wrapper takes in some data to remove and returns some data that was removed. The dummy node is created with null data to be used as a storage container. Once we reach the node of the data to be removed we set that data to the dummy node. Beyond this change the recursion is the same as we saw previously.

```
public int remove(int data):
    Node dummy <- new Node(-1)
    root <- rRemove(root, data, dummy)
    return dummy.data
```


The left and right traversal is the same as before

```
    else if data < curr.data:
        curr.left <- rRemove(curr.left, data, dummy)
    else if data > curr.data:
        curr.right <- rRemove(curr.right, data, dummy)
```

There are actually 2 different base cases now. Whether the data is not found and whether the data is found.

When the data is not found generally you return a null or throw an exception.

When the data is found:

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

first we set the dummy node data to the data in the current node and decrease size.

```
dummy.data <- curr.data
decrement size
```

Then we have the 3 cases **zero-child**, **one-child**, and **two-child**.

zero-child case:

Returning null here is the equivalent of setting the parent's pointer to null.

```
if 0 children:
    return null
```

one-child case:

Returning the child here is the equivalent of setting the parent's pointer to it's grandchild.

```
else if left child is non-null:
    return left child
else if right child is non-null:
    return right child
```

two-child case:

First we need to make another recursive call to remove either the successor or predecessor. We are using the successor in this example. 

1st set up a 2nd dummy to hold the successor data. 2nd make a call to another recursive helper method. This method should both search for the successor and remove it instead of having 1 method for each.

```
else:
    Node dummy2 <- new Node(-1)
    curr.right <- removeSuccessor(curr.right, dummy2)
    curr.data <- dummy2.data
```

Successor helper method:

```
private Node removeSuccessor(Node curr, Node dummy):
    if curr.left == null:
        dummy.data <- curr.data
        return curr.right
    else:
        curr.left <- removeSuccessor(curr.left, dummy)
```

Takes in a node and dummy node. The base case is if the node's left child is null. Then we set the dummy node's data to the current node's data and return the node's right child. This handles the no child and 1 child remove cases. Lastly the left child is not null so we make a recursive call to the left child and the return reinforces the pointer in unchanged nodes. 

Finally we of course return up the recursive stack reinforcing the pointers.

```
return curr
```

Then the return to the public method where the dummy data is returned.

```
return dummy.data
```

## SkipLists

We have used BST to get an O(log(n)) in searching sorted data structures but what if there is 
randomness involved? This is where we can look at **SkipLists**.

SkipLists usually have a data pointer and an up, down, left, and right pointer. Sometimes they will not have the up and left pointers. Each linked list at each level is sorted in ascending order.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/SkipListsIntroduction.png)

SkipLists are probability based. 

- Number of times a node is duplicated depends on coin flips.
    - Heads promotes a node. Tails terminates promotion in adding.
- Examples:
    - T would promote 0 times, giving 1 node
    - HT would promote 1 time, giving 2 nodes
    - HHT would promote 2 times, giving 3 nodes
- Probability of a node reaching level i is (1/2)^i

You can see above all of the data is on level 0. Half of the data is on level 1, etc.

Example:

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/SkipListAddingExample1.png)

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/SkipListAddingExample2.png)

Notice that once you reach the tails that is an end sequence. If we continue there would be 3 occurrences of 45.

The reason it is called a **SkipList** is because the goal is to be able to skip over a lot of data while traversing.

General Algorithm:
- Begin the search at the head (the top left infinity node)
- Look at the right node if it exists and compare with data
    - If data < right then continue moving right
    - If data > right then move down a level
    - if data equals right then data has been found
- If at the bottom level data isn't in the SkipList
    - For adding this means we've found where to add

Adding to SkipList:

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/SkipListAddingExample3.png)

- Start at the `-inf` upper left node
- 25 > 20 so we move to `20` node to the right
- 25 < inf so we move down to `20` level 2
- 25 < 45 so we move down to `20` level 1
- 25 < 45 so we move down to `20` level 0
- 25 < 45 so we add 25 to the right of `20` and left of `45`

Removing from SkipList:

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/5_bst_operations_skiplists/images/SkipListRemovingExample1.png)

We use a similar process as above. Since we always begin at the upper left -inf node we will always find the target data at the highest level first. To remove all occurrences you perform a series of doubly-linked list remove operations until you reach level 0. This means 45 will be removed 3 times in this example.

Efficiency:

|Scenario|Best Case|Average Case|Worst Case|
|--------|---------|------------|----------|
|Search|O(log n)|O(log n)|O(n)|
|Add|O(log n)|O(log n)|O(n)|
|Remove|O(log n)|O(log n)|O(n)|
|Space|O(n)|O(n)|O(n log n)|

O(n) can occur when all data is on level 0 so no data can be skipped over.

Space complexity average is O(n) since it becomes a geometric series that converges. There are 2 different worst case scenarios. If no data is promoted then it degenerates into a linked list. If all data is promoted to level cap then this results in a massive grid resulting in O(n log n).