# Heaps

# Index
- [Introduction to Heaps](#introduction-to-heaps)
- [Heap Operations](#heap-operations)
- [The BuildHeap Algorithm](#the-buildheap-algorithm)

## Introduction to Heaps

**(binary) Heaps** are the cousin of BSTs. They are more of a specialist data structure that utilize the Priority Queue ADT where BSTs are generalist data structures useful for many different operations. Heaps tend to prioritize accessing either the smallest or largest data depending on their type.

Note there are other types of heaps but we will be referring to binary heaps for this portion.

Like a BST every node can have at most 2 children. But they must be **complete**. Recall **complete** trees are filled left to right with no gaps. 

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/6_heaps/images/HeapComplete.png)

There are 2 types of heaps, a **Min Heap** and a **Max Heap**.

In a Min Heap the smallest element in the dataset is at the root and the children are larger than the parent. There is no relationship between the siblings and the parent.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/6_heaps/images/MinHeap.png)

Similarly a Max Heap has the largest element in the dataset at the root and the children are always smaller than the parent.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/6_heaps/images/MaxHeap.png)

Indexing goes left to right omitting index 0 starting at index 1 but otherwise just like a level-order traversal.

Because there are no gaps you can calculate given data at index n:
- Left Child: 2 * n
- Right Child: 2 * n + 1
- Parent: n / 2
- Index of **size** gives last data

Heap use cases:
- Designed for accessing the root
- Not designed for arbitrary searching
- Often used for back priority queues
    - i.e. emergency room waitlist


## Heap Operations

## The BuildHeap Algorithm
