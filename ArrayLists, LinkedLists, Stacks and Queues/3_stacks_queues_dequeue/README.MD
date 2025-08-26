# Stacks and Queues

# Index
- [Stacks](#stacks)
    - [Singly-Linked List Backed Stack](#singly-linked-list-backed-stack)
    - [Array Backed Stacks](#array-backed-stacks)
- [Queues](#queues)
    - [Linked List Backed Queues](#linked-list-backed-queues)
    - [Array Backed Queues](#array-backed-queues)
- [Variations of Stacks and Queues](#variations-of-stacks-and-queues)
    - [Priority Queues](#priority-queues)
    - [Deques](#deques)

## Stacks

Recall **linear** abstract data types **(ADT)** rules:

**Linear:**
- Finite collection
- Each object has one immediate predecessor (except the 1st object)
- Each object has one immediate successor (except the last object)

**ADT:**
- Data structure that is a container with a finite number of objects
- There is a relationship between the objects being stored
- Defined by operations or behavior of the operations

Stacks are linear ADTs.

Potential backing data structures for a stack ADT inclue **arrays** and **linked lists**.

Stack behavior includes `push` which adds data. This adds the data on the top of the stack. To remove is to `pop` which removes from the top. This follows last in, first out or *LIFO*. 

Stacks are not made for searching beyond the element first added.

Supported operations:
- `void push(x)`
- `x pop()`
- `x peek() / x top()` - returns element on top of the stack without removing it
- `boolean isEmpty()` - checks size or if top is null
- `void clear()` - sets top of stack to null clearing it

Unsupported operations:
- Searching for data
- Arbitrary index access
- Arbitrary add or remove

Examples include the undo button in a word processing application, the stack of apps open on a smart phone, etc.

Another example is the method call stack. Making a method call translates to pushing a method call onto the stack. This is why infinite recursion results in a StackOverflowError and we follow method calls in stack order.

### Singly-Linked List Backed Stack

When the list is empty i.e. `head` points to `null` then we push the data to the stack. 

`push(1)` -> now 1 is on top of the stack and the head points to it.

`push(3)` -> now 3 is on top of the stack and 1 is under it, head points to 3.

Now say we want to remove using `pop`. 

`pop()` -> 3 is removed and the new head is 1.

Linked-List backed stack performance:
- No `tail` pointer
- Stacks are lightweight
- Singly-Linked lists are best (compared to doubly since they have more memory overhead)

### Array Backed Stacks

Instead of checking the `head` here we check the `size`. When an array is empty the `size` == 0.

Here using `push(x)` adds `x` to index 0.

Critically here we do not `push` further data to the **front** but to the **back** because if we were to push to the front this would be O(n) because we would need to move the indexes of n data in the Array on each add. By keeping track of the `size` we can simply `push` and `pop` from the `size` index to maintain O(1).

`push(1)` -> 1 at index 0

`push(3)` -> 3 at index 1

`push(2)` ->  2 at index 2

`pop()` -> (from size-1 i.e. last index added) remove from index 2 (2), size--

`pop*()` -> remove from index 1, size--

To clear an array you don't want to just set size to 0 and overwrite because the garbage collector wont work. You could alternatively set size to 0 then reset each index to null but that would be O(n).

The correct approach is to set size = 0 and then reassign the backing array to a new array. This is O(1) and the old data is collected by the garbage collector.

## Queues

Unlike stacks which are LIFO, queues are **first in first out (LIFO)**. 

Supported operations:
- `void enqueue(x)` - adds to an end of the queue
- `x dequeue()` - removes data from the opposite end of the queue
- `x peek() / x top()` - returns data at the front but doesn't remove it
- `boolean isEmpty()` - checks size for 0 or the front for null
- `void clear()` - sets queue to null

Much like stacks we can back queues with various linear data structures like **Arrays** and **LinkedLists**.

Unsupported operations:
- Searching for data
- Arbitrary index access
- Arbitrary add or remove

### Linked List Backed Queues

Recall queues must add at 1 end and remove from the other.

Adding and removing from the front of a linked list is O(1) but only adding to the end is O(1), removing from the back is O(n). *This means we will want to always add to the back and remove from the front.* 

**enqueue from the tail, dequeue from the head**

Typically we do not use a doubly linked list despite that it can support all necessary operations because it has a higher memory requirement.

Think of this as a printer queue, a customer queue purchasing tickets online, etc.

### Array Backed Queues

Recall adding and removing from the front has a cost of O(n) in an array. We want to avoid shifting altogether, this is why we use Arrays and not ArrayLists. We will need to use a wrap-around/circular array to accomplish this. It is not the same as a circular ArrayList.

Required variables:
- `size`
- `capacity`
- `front`
- `back`

[1][2][3][2][ ][ ][ ]

Here `size` = 4 and `capacity` = 7. `Front` is at index 0 and `back` is at front index + `size`.

Here we will `enqueue` at the back and `dequeue` at the back again.

r, a, m, b, l, i, n

[ ][ ][ ][ ][ ][ ][ ]

[r][a][m][b][l][i][n]

`back` index is equal to the `size` as you add data. But what about at this point when `size` = 7 but there is no `index` of 7?

To account for this we always use mod on the back i.e. `back % capacity` or `back % 7` so `back` becomes 0 in this case and the `back` and `front` are now both = 0.

Now at this point say we `dequeue` decreasing the size by 1.

Now the `back` points to index 0 but the `front` now points to index 1.

[ ][a][m][b][l][i][n]

`Dequeue` again:

[ ][ ][m][b][l][i][n]

[ ][ ][ ][b][l][i][n]

[ ][ ][ ][ ][l][i][n]

Now `back` = index 0, `front` = index 4 (l).

If we `enqueue` w and r:

[w][ ][ ][ ][l][i][n]

Now `back` is index 1 and `front` is still index 4.

[w][r][ ][ ][l][i][n]

Something that stands out is in both the **stack** and **queue** examples - the big O of the Linked List ADT backing was superior compared to the Array ADT backing. Big O does not contain the full picture though. There is a concept called **spatial locality** where a processor may prefetch data in adjacent memory optimizing the process. This cannot happen with a Linked List but it will happen with an Array blurring this line. Big O is a high level tool and not always the best indicator of efficiency in practice.

## Variations of Stacks and Queues

### Priority Queues

**Priority queues** are similar to stacks and queues in that they are linear and data can be added and removed as necessary.

Priority queues are an ADT with multiple implementations again like stacks and queues.

Stack ADT:
- Remove -> Most recently added element

Queue ADT
- Remove -> Least recently added element

Priority Queue ADT
- Remove -> element with highest priority

While a queue is like the waiting area of a restaurant the priority queue is like the waiting area of an emergency room.

Often used to find min and max values of a dataset. 

Requirements:
- Comparable data

A challenge is how to efficiently rank by priority because it will become expensive to always search through all the data. A backing solution we will look at later is a `heap`.

Other places to find priority queues:
- Boarding of a plane
- Graph algorithms (minimum spanning trees) finding the shortest path from A to B

In theory priority queues are linear ADTs but in practice the linear model does not work well. For an array backed version the element with the highest priority could be at the front so dequeuing would be O(1) but enqueuing would be O(n). For the linked list version dequeuing again would be O(1) but enqueuing would be O(n). So we will need to assess non linear backing structures later.

### Deques

Deque stands for **double-ended queue**. They do not exhibit LIFO or FIFO behavior. They are defined by the ability to add or remove from either end of the data structure. 

Supported Operations:
- `void addFirst(x)`
- `void addLast(x)`
- `x removeFirst()`
- `x removeLast()`
- `boolean isEmpty()`
- `int size()`

Deques can be backed by an array or doubly linked list allowing all methods to have a time complexity of O(1).

Unsupported Operations:
- Searching for data
- Arbitrary index access
- Arbitrary adding/removing

Array Backed Deques:
- Use circular wrap around implementation
- `addLast()` - increment back -> Add to back
- `removeFirst()` - remove at front -> increment front
- `addFirst()` - decrement front (check if <0) -> add to front
- `removeLast()` - remove at back -> decrement back (check if <0)
- All O(1) armotized due to resize

Linked List Backed Deques:
- `addLast()` - add at tail
- `removeLast()` - remove at tail
- `removeFirst()` - remove at head
- `addFirst()` - add at head
- All O(1)

Note that java Modulo may work different than expected so a helper function will need to be created when using the circular arrays. For example -9 % 5 you may expect -10 = 5 (-2) + 1 so it would return 1 but in reality it will return -4 because -4 is closest to 0 of the negative remainders. Generally we think of the smallest non-negative remainder.

