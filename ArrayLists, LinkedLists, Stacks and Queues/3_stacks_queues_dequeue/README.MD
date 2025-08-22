# Stacks and Queues

# Index
- [Stacks](#stacks)
    - [Singly-Linked List Backed Stack](#singly-linked-list-backed-stack)
    - [Array Backed Stacks](#array-backed-stacks)
- [Queues](#queues)
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

## Variations of Stacks and Queues

### Priority Queues

### Deques