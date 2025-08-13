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

For a method to be recursive if must have the following requirements:

- Base case(s): termination condition(s) that ensure all branches of recursion stop eventually
- Recursive case(s): method calls to itself
- Progress to base case: each recursive call must move towards the base case in some way

```java
public return_type recursiveEx(param_1, ..., param_n) {
    if (base_case1) {
        // termination logic for base case 1
    } 
    else if (base_case2) {
        // termination logic for base case 2
    } 
    // ... other base cases if necessary
    else {
        // logic for setting up recursive calls
        return recursiveEx(newParam_1, ..., newParam_n)
    }
}
```

- Generally it is easiest to identify the base case and write those out first.
- Ensure base cases do not overlap if possible.

Euclidean Algorithm for Greatest Common Divisor, GCD:
- finding the greatest common divisor of two or more integers, x and y, where x and y are not both zero, is the largest integer dividing both x and y with no remainder.
- There are multiple ways to solve the GCD problem.
    - Subtraction-based where the smaller is subtracted from the larger repeatedly
    - Division-based where larger is divided by the smaller and remainder is kept
    - Divide both numbers by 2 and track binary division count

The modulo division-based solution is what we will demo because it is the most efficient.

Given x>y, then:
- x = q * y + r
- r = x mod y
- y > r

Rcursive case:
- gcd(x,y) = gcd(y,r)

Base case:
- gcd(z,0) return z

```
gcd(1482, 819) -> 1482 % 819 = 663
gcd(819, 663) -> 819 % 663 = 156
gcd(663, 156) -> 663 % 156 = 39
gcd(156, 39) -> 156 % 39 = 0
gcd(39,0) -> 39
```

```java
public int gcd(int x, int y) {
    if (y == 0) { // base case
        return x;
    }
    else { // recursive call
        int remainder = x % y;
        return gcd(y, remainder);
    }
}

```