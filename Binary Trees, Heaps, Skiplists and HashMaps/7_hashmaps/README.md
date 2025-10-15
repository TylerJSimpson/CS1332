# HashMaps

# Index
- [Introduction to HashMaps](#introduction-to-hashmaps)
- [Collision Handling - External Chaining](#collision-handling---external-chaining)
- [Collision Handling - Probing Strategies](#collision-handling---probing-strategies)
    - [Linear Probing](#linear-probing)
    - [Quadratic Probing](#quadratic-probing)
- [Collision Handling - Double Hashing](#collision-handling---double-hashing)
- [Summary](#summary)

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

### Linear Probing

There are 3 main open addressing policies we will be looking at, the first is **linear probing**.

Backing structure here is just an array since each index can only store one entry.

Probing strategies are considered open addressing because the 1st index we calculate is not necessarily where the key-value pair will end up.

**linear probing**: if a collision occurs at a given index, increment the index by one and check again. Incrementing by 1 is a linear function.

Index = (h + origIndex) % backingArray.length

h = number of times probed (1, 2, ... n)

Below we will have an example that ignores resizing to fully demonstrate the collisions.

Put: 8,1,15,5,2,22,50

idx = H(n) % 7

8 % 7 = 1

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
||8||||||

1 % 7 = 1

idx = 1 -> 1 + 1 = 2

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
||8|1|||||

15 % 7 = 1

idx = 1 + 1 = 2 + 1 = 3

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
||8|1|15||||

Eventually we get to:

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
||8|1|15|2|5|22|

Now when we get to 50 that compresses to 1 but the array is full downstream. Once we get to index 6 we have to wrap around like we have done in previoust ADT.

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
|50|8|1|15|2|5|22|

Next we will look at removing. When removing from a HashMap a standard removal does not work we have to do a **soft removal**. This is where we leave the entry there but mark it with a flag called a **DEL marker** or sometimes a **tombstone**.

We will start with the same backing array as we used above.

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
|50|8|1|15|2|5|22|

1st we will try to remove 1. 

1 % 7 = 1

We attempt a GET at index 1. It is not null but the keys are not equal because we GET a key 8.

Now we probe forward to 2 and see it is not null and the keys are equal.

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
|50|8||15|2|5|22|

Now imagine if we went to search for 15, we would not be able to find it because we would run into a null at index 2 before we reach index 3. This is why we use a **DEL marker**, usually a boolean marker.

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
|50|8|DEL|15|2|5|22|

Let's look at another example where we have performed several removals already.

Again we look for key of 1 and 1 % 7 = 1 so we begin searching at index = 1.

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
||8|DEL|15|DEL||22|

We come across 8, DEL, 15, DEL, null. The GET procedure searched the backing array for all possible *contiguous, consecutive, subsequent, occupied* cells to index 1 and thus key 1 is not in the hashmap.

Probing scenarios:
- valid (not null or deleted) and unequal key
- valid and equal key
- deleted
- null

Let's try to PUT key 1 this time

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
||8|DEL|15|DEL||22|

1 % 7 = 1

index 1 we see 8 this is an unequal key so we probe index + 1.

index 2 we see a DEL marker so we **save** the value index + 1 (2) for later because if our continued probing does not find the matching key then we will PUT here. Recall if we find a matching key then we would simply update the value at the matching key index.

index + 2 = 3 keys are not equal

index + 3 = 4 DEL marker but it is not the 1st DEL marker so we continue

index + 4 = 5 is null so we will PUT to index + 1

Now if there were no DEL markers then we would be putting at index + 4

Resizing backing array:
- Create a new backing array of capacity 2N + 1
- Loop thru old backing array
- Rehash all cells to new backing array
- Skip over all DEL markers

The DEL markers no longer serve a purpose so they can be skipped on resizing.

Think of a situation where we have a table where we have removed everyhing so it contains only DEL markers. In this situation any GET and PUT is going to be O(n) since it will traverse the length of the table. This is one reason why we remove DEL markers in the resize. Another way to minimize this issue is by including DEL markers in the load factor calculation.

### Quadratic Probing

One of the problems with the previous open addressing technique (linear probing) was that we develop these contiguous blocks of data that must be searched over. For example you could try to PUT at index 1 but traverse dozens of indices until finding an unoccupied block. This problem is known as **primary clustering** or **turtling**. **Quadratic probing** aims to solve this issue.

**Quadratic probing**: if a collision occurs at a given index, add h^2 to the original index and check again. This effectively breaks up clusters.

Index = (h^2 + origIndex) % backingArray.length

Put: 8,1,15,2,5,22

idx = H(n) % 7

H(8) % 7 = 1

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
||8||||||

H(1) % 7 = 1

1 is taken

idx + 1^2 = 2

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
||8|1|||||

H(15) % 7 = 1

1 is taken

idx + 1^2 = 2

2 is taken

idx + 2^2 = 5

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
||8|1|||15||

Eventually we get to this point

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
||8|1|2||15|5|

H(22) % 7 = 1

1 is taken, idx + 1^2 = 2 is taken, idx + 2^2 = 5 is taken and the next quadratic will have us wrap around to (idx + 3^2) % 7 = 3, (idx + 4^2) % 7 = 3, (idx + 5^2) % 7 = 5 etc. etc. demonstrating a loop

Generally there is a guardrail of stopping at **h** probes where **h** is the length of the backing array. At this point we can resize the table. Due to the quadratic nature you can actually have collisions in resizing causing a resize inside of a resize so this must be considered.

Solution 1: continually resize until a spot is eventually found

Solution 2: Impose a set of conditions on the table to ensure that this scenario never occurs. For example if load factor is 0.5 and the table length is a prime number.

While we tried to solve the issue of **primary clustering** we now suffer from **secondary clustering** where keys belonging to different indices collide in quadratic steps rather than linear ones.


## Collision Handling - Double Hashing

**Double hashing**: if a collision occurs at a given index, add a multiple of c to the original index and check again. This adds aspects of linear and quadratic probing.
- Breaks up clusters created by linear probing
- index = (c * h + origIndex) % backingArray.length
- h = number of times we've probed (1,2,3...n)
- c = result of second hash function (linear probing if c = 1)

First hash: H(k), used to calculate origIndex

Second hash: D(k), used to calculate probing constant
- D(k) = q - H(k) % q
- q = some prime number and q < N
    - Another prime number added to help further break up clusters

Put: 8,1,15,2,5,22

idx = H(8) % 7 = 1

c = 5 - H(8) % 5 = 2

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
||8||||||

We place the 8 and index 1 because it is open. We only use the 2nd hashing if the index is not open.

idx = H(1) % 7 = 1

c = 5 - H(1) % 5 = 5

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
||8||||1||

Above we used the 2nd hash since index 1 was occupied.

Eventually we get to:

|0|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
||8|2|5|22|1|15|

When removing here we will need to use DEL flags like in linear probing. Also, double hashing can run into infinite probing problems.

**Linear probing** should be considered a special case of **double hashing** but **quadratic hashing** is not. This is because the secondary hash functions depends on the key as input whereas if we wanted the same effect as in quadratic proving, we'd need the probe count as the input.

## Summary

In the wild you will mostly run into external chaining and linear probing. External chaining is very popular because it is easy to implement but fails to utilize the **locality of reference**. Computers are designed to access adjacent/nearby memory locations quickly which open addressing schemes take advantage of not closed addressing schemes.

The O(1) performance of HashMaps happens when there are little to no collisions so keep in mind you want to reasses if there are many collisions happening. Quadratic probing and double hashing are very fast but computer can increment faster than do math operations so linear probing will be faster.

Quadratic probing and double hashing can both run into infinite probing. To avoid this the table length must be a prime number and your max load factor must be no more than 0.5. 

An example of an implementation of a data structure that uses hashing is **bloom filters**.