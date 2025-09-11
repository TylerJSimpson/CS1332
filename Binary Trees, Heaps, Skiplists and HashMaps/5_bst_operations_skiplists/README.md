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

## Removing from a BST

## SkipLists