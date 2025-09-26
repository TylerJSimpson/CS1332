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

Because heaps are clean and orderly data structures backed by arrays the operations will be simpler than for the BST. Heaps are not designed to search for arbitrary data. The only operations we need to consider are **add** and **remove**.

It is important the **shape** and **order** properties are maintained.

We tend to handle **shape** first and then **order** because order is harder to maintain.

**Add**

- Add to the next spot in the array to maintain completeness
- Up-Heap starting from the new data to fix order property
    - Compare the data with the parent
    - If the child does not have the correct order with the parent then swap the 2 nodes
        - Repeat this comparison up the tree to the root or until the correct relationship is found

In this example we want to add 99 to the MAX heap. So first we maintain shape and then order. To maintain shape we simply add at the highest index which is 8. Now we need to compare with the parent iteratively to fix the order.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/6_heaps/images/HeapExampleAdd1.png)

Because this is a max heap (notice largest node is the root) 99 compared to 35 means we need to swap. Again 99 is greater than 56 and 95 so we need to continue swapping until we get this result.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/6_heaps/images/HeapExampleAdd2.png)

**Overall time complexity of adding is O(log n)** this is because the Up-Heap activity is O(log n) despite the add being O(1)

**Remove**

- Instead of adding to the bottom and Up-Heap (heapify) we remove from the root and Down-Heap
- Move the last element of the heap to replace the root
    - We must save the root before removing it to ensure we can bring it back later.
- Down-Heap starting from the root to fix the order property
    - If two children, compare data with the higher priority child
    - If one child (must be left), compare data with the child
    - If node has no children, the method terminates
    - Swap if necessary based on comparison
    - Continue moving down the heap until no swap is made or a leaf is reached

Remove example:

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/6_heaps/images/HeapExampleRemove1.png)

Because this is a max heap we are removing the highest value which is always the root.

1st we store 95 in a variable. 2nd we copy the data in the last element i.e. the node at index == size which in this case is index 7 i.e. 30.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/6_heaps/images/HeapExampleRemove2.png)

Now we need to begin Down-Heaping from the root. 30 has two children 56 aND 63. We need to assess the child with the highest priority. **Because this is a max heap the higher priority child is the one with the higher value**. So we will compare 30 with right child data 63. The order property is violated so we will prepare a Down-Heap. The next comparison is 30 with 50 because there is only 1 child. Again the order property is violated and 30 is swapped with 50.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/6_heaps/images/HeapExampleRemove3.png)

**Overall time complexity of removing is O(log n)** just as before the step of maintaining the shape moving the last element to the root is O(1) but re-ordering (Down-Heaping) is O(log n).

Briefly on time complexity let's review a few cases:
- Worst case time complexity of adding an element to a heap is O(n) in the case of a resize operation needed
- Worst case amortized time complexity of adding an element to a heap is O(log n) because the cost of resizing is averaged down
- Worst case time complexity of removing from a heap is O(log n) because the removal is just O(1) but fixing can be O(log n)
- Worst case time complexity of accessing the minimum element of a MinHeap is O(1) since it is always at the 1st index and n/2 for finding the max element in a MinHeap because the max will always be a leaf node due to the order property so we only need to pinspect the 2nd half of the array
- Worst case time complexity for building a heap by adding n items to an empty heap by using the add method n times (assming no resize needed) is O(n log n) because adding is O(log n) and we are doing this n times
- O(n) is worst case when trying to find a certain data value to change it to a different value because just finding it is O(n)
- If we know the index and want to change the value then we just need to upheap/downheap which is O(log n)

Briefly we can touch on two other methods that are used on Heaps including **union** and **increase priority**.

**Union** takes two valid heaps of the same type (min or max) and fuses them together into one larger heap of the same type. 

**Increase priority** simply boosts the priority level of an item in the heap. As we used an emergency room example previously maybe a patient in the waiting room has had their symptoms worsen and need to be seen sooner rather than later. Note there is a caveat of it being difficult to find the item we are wanting to boost.

Generally with union and increase priority we are not using a Binary Heap and instead using something like a **binomial heap** or **fibonacci heap**.


## The BuildHeap Algorithm

Recall that building a heap 1 by 1 gives us a time complexity of O(n log n). Using the BuildHeap algorithm we aim to build a heap in linear time.

Problem Description:
- Input: Unsorted, unordered array
- Problem: Construct a heap from the input
- Possible Solutions:
    - Use the add method to add all data to an empty heap one by one
        - Adding to heap O(log n)
        - Add to heap n times
        - O(n log n)
    - BuildHeap

BuildHeap:
- Accepts unordered data as presented
- Places data in backing array to ensure shape property is enforced
- Using subheaps in Down Heap process we can optimize the process of building a heap

You can build a heap by repeatedly calling the downheap method starting from the bottom up creating valid subheaps. You loop from index size/2 to index 1 and a downheap is performed at each input. You use size/2 because size gives us the last element in a heap and by dividing it's index by 2 you find it's parent. size/2 gives us the last element that has a child.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/6_heaps/images/BuildHeapExample1.png)

In this example we want to build a MinHeap from the array pictured.

The algorithm starts at index 4. This is because the last element (size = 9) index 9 // 2 = 4 (integer division).

Now in the downheap process we compare the parent with the children. We would swap the parent with the smallest child. In this case 5 is the smallest so it needs to swap with the parent. The Downheap method would then look at the child that was swapped i.e. 32 in this case. If this is a leaf it terminates otherwise it would continue comparing until no swaps happen.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/6_heaps/images/BuildHeapExample2.png)

The algorithm then decrements the index from 4 to 3 which is data point 14. 

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/6_heaps/images/BuildHeapExample3.png)

In this case both children's data is smaller. 4 is the smallest so it will be the one that swaps.

Then we decrement to inde 2 which is data point 71. Both 5 and 33 are smaller. Since 5 is smallest we swap 71 and 5. Now 71 has children so we must compare again to 17 and 32 which are both smaller. Since 17 is smallest we swap it with 71.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/6_heaps/images/BuildHeapExample4.png)

Now the algorithm decrements the index to 1 and we are at the root. We continue then to downheap from index 1 which is data point 1. Because 1 is smaller than both children the order property is not violated and the downheap method terminates.

We will not present a formal proof on the time complexity but **buildheap is an O(n) operation**. Roughly, this is because there is more data in the bottom half than the top half. The number of times you go down the tree the data doubles, meaning exponential growth. Downheap cost is O(1) at the bottom of the tree and increases linearly as you approach the root. 

This means most of the data (bottom) has O(1) cost, and a few data (top) have the O(log n) cost. If you sum the series this balances out to O(n).

**The BuildHeap algorithm's worst case beats the average case of the naive algorithm even in constant factors**.