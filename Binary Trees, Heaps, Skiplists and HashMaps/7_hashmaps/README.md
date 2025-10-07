# HashMaps

# Index
-[Introduction to HashMaps](#introduction-to-hashmaps)
-[Collision Handling - External Chaining](#collision-handling---external-chaining)
-[Collision Handling - Probing Strategies](#collision-handling---probing-strategies)
-[Collision Handling - Double Hashing](#collision-handling---double-hashing)

## Introduction to HashMaps 

**HashMaps** are a type of **Map**. A **Map** is a collection of key-value pairs <K,V> that are searchable. 

**Maps**:
- A collection of key-value pairs <K,V>
- Searchable
- Unordered (when used with hasing)
- Keys are unique (<A,3> and <A,4> cannot exist in the same map)
- Keys are immutable (cannot be changed)
- Values do not need to be unique and are not necessarily immutable
- Maps are sometimes called dictionaries
- Example: Names and phone numbers (phone number is key in this example)

**Map ADT**:
- Methods:
    - V put(<K,V>) - adds an entry or replaces an existing if key already exists
    - V get(K) - gets value for associated key
    - V remove(K) - removes the entry with the associated key and returns the value
    - int size() - returns number of entries in the map

**Size** refers to the number of entries, n.

**Capacity** refers to the length of the backing array, N.

**HashMaps**
- Backing Structure
    - Array of Map Entries (O(1) access)
- Hash Function
    - The result of applying a hash function to a key is the hashcode
    - Represents our key object as an integer value (hashcode)
    - Maps the hashcode to an integer in the backing array
    - If A.equals(B) -> A.hashcode() == B.hashcode()
    - If A.hashcode() == B.hashcode(), we CANNOT assume A.equals(B)
    - Having the same hashcode does not mean the instances are equal


|V: value|K: key|H(K): K.hashcode()|index: H(K) % 100|
|--------|------|------------------|-----------------|
|Men's Basketball|4048944424|4424|24|
|Women's Basketball|4048945406|5406|06|
|Baseball|4048952261|2261|61|
|Golf|4048940961|0961|61|


Here we compress the hashcode by moding the keys hashcode by the capacity of the backing array. This is called a **compression function**. Because the backing array has size of 100 that is why we are using % 100.

Notice above we have what is called a **collision** when trying to add golf as the index 61 already exists of baseball. This is a common issue with multiple strategies to handle it.

First let's discuss how to reduce the chance of collisions before we look at strategies for handling.

**Avoiding Collisions**
- Resize to a larger table
    - newCapacity = (2 * currCapacity) + 1
- Resize when maximum load factor is hit
    - Load factor = size / capacity = n/N
    - Typical maximum load factor 60-70% as opposed to resize when full like we have done previously

Let's consider some hashing examples. Say we have the string 'ABAB' and we need to convert it to an integer so we assign A = 1 and B = 2 so we get the has value of 1 + 2 + 1 + 2 = 6. The weakness here is that order is ignored so any string of length 4 with an equal amount of A and B will collide. We can improve this by multiplying these values by powers of 10 based on the position from right to left:

1 * 10^3 + 2 * 10^2 + 1 * 10^1 + 2 * 10^0 = 1212

Once a hash function is chosen there are 3 other things we need to consider: **compression function**, **table size**, and **load factor**.

The **compression function** shrinks and shifts the output of the hash function so that it fits into the range of the backing array. As above the most common is simply mod by the table length.

Because of the compression function mod we tend to make the **table size** a prime number in order to minimize collisions due to compression. 

**load factor** is the size / capacity. This parameter has to do with how high we're willing to let the load factor become before we resize the table. As the load factor increases, the table will become inundated with entries, and it becomes more likely for a collision to occur. If we keep the threshold low then we can almost guarantee O(1) search times but we may have to resize more often and we will use more memory than we may want to.

## Collision Handling - External Chaining

External chaining is our first example of collision handling. Collision handling policies can be roughly categorized into 2 different types:

- **Closed addressing**: a policy where all keys that collide into the same location are stored at that location by some means

- **Open addressing**: a policy where additional colliding keys can be stored at a different location based on the original location

**External chaining** is a closed addressing policy. 

Backing structure: Array of LinkedLists where each index can store multiple entries. Essentially each of the colliding keys are stored at a linkedList at that location.

This is called a "closed addressing" strategy because entries are always found at the exact index calculated.

![](/Binary%20Trees,%20Heaps,%20Skiplists%20and%20HashMaps/7_hashmaps/images/HashmapsExternalChaining.png)

In the previous example we talked about index 61 being duplicated. In the external chaining example this is how it would look. The 2nd key-value pair placed at index 61 will be placed at the head of the linked list at that index. This keeps the operation O(1) time complexity. There are instances of adding to the back in external chaining as well.

When using external chaining we don't run out of space but long chains impact runtimes. So we still use the size / capacity load factor to avoid excessive chaining.

Now when it comes to size don't think of it as across indices. You could have an example Array of capacity 10 and size 5 if all 5 instances are externally chained to index 1 causing a 50% load factor.

When it comes to resizing with external chaining you have to both iterate through the array index as well as each linked list.

Each time a resize occurs it requires a compression of the hash codes.

```
key.hashcode() % newBackingArray.length
```

*You do not want to just copy everything over because it will disrupt retrieval attempts and it is possible for keys to collide in the resized backing array*

Of note since there will not be duplicates you can just simply add to the front of the new chain when resizing.

## Collision Handling - Probing Strategies

## Collision Handling - Double Hashing