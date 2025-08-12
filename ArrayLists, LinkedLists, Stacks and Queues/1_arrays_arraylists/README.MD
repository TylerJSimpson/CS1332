# ArrayLists and Recursion

# Index

- [Arrays and ArrayLists](#arrays-and-arraylists)
    - [Arrays](#arrays)
    - [ArrayLists](#arraylists)
    - [Amortized Analysis](#amortized-analysis)
- [Recursion](#recursion)

## Arrays and ArrayLists

### Arrays

Arrays are statically allocated contiguous blocks of memory provided that O(1) access to data of a single type stored at a specified index.

- Allocations of contiguous blocks of memory
- Flexible in what data types they can store (but must be same datatype)
- Accessing a specific index is O(1)

**Access** is when location (index) is known and is O(1).

**Search** is when location is unknown and is O(n).

Array capacity is static. To copy over n elements into new array when making a larger array is O(n) because elements are copied over 1 by 1.

There are a few things that need to occur for the accessing of an index of an array to be an O(1) operation:
- The memory allocated needs to be contiguous i.e. each array index occupies adjacent memory location
- The array needs to know the memory address of the very first entry in the array, index 0
- The array's data typing needs to be defined beforehand

**Pointer arithmetic** is used i.e. if we know the data type is `int` which is always 32-bits and we know the starting memory address (index 0) then `new_address = start_address + i * data_size` where i represents the index i that we want to get.

### ArrayLists

**Abstract data type (ADT)** is a model description of a data type that is defined by its behaviors and operations. Much like a Java interface where they set the framework for what operations (methods) are available as well as what these operations do but leave the actual implementation details abstract.

**List ADT** is a sequence of data values that are accessible via indexing.

Required minimum operations:

- `addAtIndex(int index, T data)`: Adds `data` to the list at the specified `index`. All data with index i GTE `index` has a new index i + 1 to make room for the new data.
- `removeAtIndex(int index)`: Removes the data at the specified `index` from the list. Any data with index i > `index` has a new index i - 1.
- `get(int index)`: Returns the data at the specified `index`.
- `isEmpty()`: Returns whether the list is empty or not.
- `clear()`: Resets the list to an initial configuration with no data.
- `size()`: Returns the number of data currently stored in the list.

We will discuss other methods such as `addToFront(T data)`, `addToBack(T data)`, `removeFromFront(T data)`, `removeFromBack(T data)` but these aren't strictly necessary to implement a **List ADT**.

`ArrayList<String> list = new ArrayList<>();`

- `ArrayList` from java.util

- `<String>` data type: must be an Object

- `<>` generic typing is implied

`ArrayList` are dynamic

- abstraction/wrapper of an array
- Resize is handled automatically by the implementation without user knowledge
- Dynamically allocated in that the array is reallocated and copied when more space is necessary

[a][b][c][d][e][ ][ ][ ]
- size = 5
- capacity = 8

[b][a][ ][d][ ]
- data is not contiguous

[ ][b][a][d][ ]
- data is contiguous but not zero-aligned

[b][a][d][ ][ ]
- proper ArrayList

[b][a][d][ ][ ] -> [b][a][d][e][ ]
- `addToBack(e)`
- O(1)

[b][a][d][e][d] -> [b][a][d][e][d][g][ ][ ][ ][ ]
- `addToBack(e)` resize necessary
- O(n)

`addToBack()` being O(n) is worst case because you will not have to resize every time, it should be somewhat rare. A single add is O(1).

Adding to an arbitrary index is O(n) because we have to shift data to keep contiguity.

Removing from the back is O(1) but removing from an arbitrary point again requires a data shift which is O(n) due to shifting.

### Amortized Analysis

**Amortization** referes to the spread of cost overtime. As mentioned previously the operation cost O(1) is the standard with the occasionally O(n). The concept of amortization is important because it more accurately depicts the average cost over time.

![](/ArrayLists,%20LinkedLists,%20Stacks%20and%20Queues/1_arrays_arraylists/images/AmortizedCost.png)

When adding to the back there can be a resize (O(n)) or not (O(1)).

Generally when we resize we double the capacity. A resize occurrs when the size n was euqal to the capacity. 

The formula dictates the worst case scenario of time complexity to adding tot he back of an ArrayList is either O(n) or amortized O(1) they are both correct. Sometimes **O(1)*** is used for amortization.

There is also a distinction between **hard removals** and **soft removals**. 

**hard removal** - data is completely removed from the data structure.

**soft removal** - leave the data in the data structure, for example `ArrayList` can be controlled by **size** variable so you may not necessarily need a hard removal of data at the end since **size** will take care of it.

**We will be using hard removal unless explicitly stated otherwise.**

## Recursion

