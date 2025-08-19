# LinkedLists

# Index

- [Singly-Linked Lists](#singly-linked-lists)
- [LinkedLists in Code](#linkedlists-in-code)
- [Variations of LinkedLists](#variations-of-linkedlists)
    - [Doubly-Linked Lists (DLL)](#doubly-linked-lists-dll)
    - [Circularly-Linked Lists (CLL)](#circularly-linked-lists-cll)

## Singly-Linked Lists

`Arrays` and `ArrayLists` had some drawbacks which can be solved by `LinkedLists` which also implement ADT.

- Linked List data structure is a collection of nodes, and data is stored in Node object
- Connections between nodes is done by having each node point to the next node in the list

A **Singly-Linked List (SLL)** contains only 1 pointer.

The first node is called the **head point** and ever linked list must have one.

You will know if an **SLL** is empty if the **head point** is `null`.

```java
public class SinglyLinkedList {
    private static class Node {
        private int data; // assuming data type of integer but this is not always the case
        private Node next; // this is the reference to the next node

        private Node(int data, Node next) { // full constructor
            this.data = data;
            this.next = next;
        }
        private Node(int data) { // overloaded constructor
            this(data, null); // sets next to null as it only receives data there is no next node yet
        }
    }

    private Node head;

    public void addToFront(int data) {
        Note newNode = new Node(data); // create new node with the data
        newNode.next = head; // point to the head i.e. the head is now 2nd
        head = newNode; // set this node as the head i.e. it is now 1st
    }

    public void addToBack(int data) {
        if (head == null) { // if list is empty
            head = new Node(data);
        }
        else {
            Node current = head;
            while (current.next != null) { // current.next is null so we stop at the last node
                current = current.next;
            }
            current.next = new Node(data;)
        }
    }

    public void removeFromFront() {
        head = head.next; // garbage collection will delete the old head no need to do so manually
    }

    public void removeFromBack() {
        if (head == null) { // if list is empty
            return null;
        }
        else if(head.next == null) { // if list has only 1 node
            head = null;
        }
        else {
            Node current = head;
            while (current.next.next != null) { // this forces iteration to land on node before the last
                current = current.next;
            }
            current.next = null;
        }
    }

    public String toString() {
        String answer = ""; // data placed into empty string
        Node current = head; // keep track of node, start at head
        while (current != null) { // the last node in a linked list points to null
            answer += current + " ";
            current = current.next;
        }
        return answer;
    }

}
```

Note that this example can be optimized by adding a `size` variable so you can check edge cases such as empty lists, single node lists, using the `size` variable.

Likewise, you can add a `tail` pointer to point to the last node in the list. This makes adding to the back become O(1) time. Removing from the back is still difficult though since you need to iterate thru the entire list to the node just before the `tail`.

- Empty list when `head` and `tail` are both null
- Single node list when `head` and `tail` point to the same node

Now above we used `int` as an example but we want the `LinkedList` to generalize to all data types.


```java
public class SinglyLinkedList<T> {
    private static class Node<T> {
        private T data;
        private Node<T> next;

        private Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
        private Node(T data) {
            this(data, null);
        }
    }
}

```

Above whenever there was an `int` replace it with `T`.

```java
Node<T> temp = new Node<>(data);
```

## LinkedLists in Code

One problem with LinkedLists is that we cannot simply use indexes to access elements, we must loop through the data.

Previously we used while loops to iterate through the data structure, but we can implement Iterable.

```java
import java.util.Iterator;
public class LinkedList<T> implements Iterable<T> {
    // Other methods omitted
    public Iterator<T> iterator() {
        return new LLIterator<>();
    }

    private class LLIterator implements Iterator<T> {
        private Node<T> curr;
        LLIterator () {
            curr = head;
        }
        public boolean hasNext() { // Recall hasNext and next are required to implement Iterator
            return curr != null;
        }
        public T next() {// Recall hasNext and next are required to implement Iterator
            if (hasNext())  {
                T temp = curr.data;
                curr = curr.next;
                return temp;
            }
            return null;
        }
    }
}
```

Using the iterator:

```java
LinkedList<String> courses = new LinkedList<>();
// Assume this list has elements despite it not being stated here

// To access each element you can setup a foreach loop
for (String course : courses) {
    // Accesses each course but course cannot be modified in this case
}

Iterator<String> courseIt = courses.iterator();
while (courseIt.hasNext()) {
    String data = courseIt.next()
    // More control over each step
}
```

The for-each method is great for quick access but the limitation is that it cannot be used to modify the list or the data itself.

Let us look at an example of using recursion in a singly linked list.

Assume all data elements are in sorted order such that duplicates are contiguous and there is a `head` reference but no tail reference.

We need to write a function that recursively removes all duplicate nodes from the linked list.

```java
// first we create public wrapper method for users to call
public void removeDuplicates() {
    head = rRemove(head);
}


private Node<T> rRemove(Node<T> curr) { // Recursive method with the parameter for current node to examine
    if (curr == null) { // simple base case checking if end of list has been reached
        return null;
    }
    curr.next = rRemove(curr.next); // if not null set current to next
    if (curr.next != null && curr.data.equals(curr.next.data) {
        return curr.next // if current data = next data return only the next data
    })
    return curr; // return current if no duplicate was present
}
```

One way to think of this at a high level is that our step is to recursively make smaller and smaller sublists. In this way the `Node` that is passed in represents the head of the smaller new sublist.

In this situation the base case is simply passing in an empty list.

In the actual recusrive call we use the concept of **pointer reinforcement** where the return field is used to restructure and relink nodes. The code *reinforces* the link that was already here if nothing changes in the structure.

Essentially we are breaking off the node and comparing it to the next node's data. If the data is equal then we have a duplicate to remove. If the data is not euqal then we relink the list as normal.

If you want to remove the current node you just need to return the node after it (next) since the recursion links the current node to what is returned. If you don't want to remove the current node then just return the current node and it will be relinked. 

## Variations of LinkedLists

### Doubly-Linked Lists (DLL)

**Doubly-Linked Lists (DLL)** are essentially **SLL** with a `previous` reference so that they can be traversed in either direction.

DLL almost always have a `head` and `tail` pointer as well.

They require extra memory usage in exchange for better time efficiency.

Adding to a DLL has similar edge cases to SLL of size 0 and 1. When it is empty both the `head` and `tail` are null. When there is 1 node both the `head` and `tail` should point to this node.

**Adding to the front:**
- Create the new node
- Set the new node's next point to the head
- set the head's previous pointer to the new node
- set head to the new node

**Adding to the back:**
- Create the new node
- Set the new node's previous pointer to the tail
- Set the tail's next pointer to the new node
- Set the tail to the new node

Recall we could not remove from the back in O(1) with an SLL, now we can with a DLL.

**Removing from the back**
- O(1) achieved by using the `previous` pointer of the tail

**Edge cases**
- Removing from a list of size 1
- head and tail will both need to null to effectively remove the single node

### Circularly-Linked Lists (CLL)

Similarly to **DLL** except in **CLL** the `head` and `tail` nodes connect to eachother instead of a null reference.

Usually does not include a tail pointer.

Instead of looping until the `current.next` is null we now need to loop until it is the head.

You can singly our doubly link a CLL. 

**Adding to the front:**
- Create a new empty node
- Add the new node at index 1 (directly after head)
    - Set newNode.next to head.next and set head.next to newNode
- Copy data from the head to the new node
- Add the new data into the head node
- This maintains that the next reference of the final node still points to the head and maintains O(1)

**Adding to the back:**
- Again insert empty newNode between head and head.next
- Copy head data into newNode
- Add new data into head
- Move the head reference to head.next (newNode)

**Removing from the front:**
- Move the data from head.next into the head
- Redirect head.next to the 3rd node skipping over the 2nd node
- Now at O(1) the new head had the data of the 2nd node and now skips the 2nd node
- Edge case of list of size 1 the head.next will need to be set to null instead of 3rd node

**Removing from the back:**
- No way to get O(1) since you will need to access the node prior to the last node requiring a complete list traversal
- Redirect secondToLast.next to the head bypassing the lastNode

**Removing from the middle:**
- Same as removing from the middle of an SLL
- Traverse the list until you get to the correct node

Where are these different linked lists used?

**SLL** generally used in history of web browser searches (back button).

**DLL** mostly used in cache i.e. a music app keeping track of your most recent songs.

**CLL** can be found in music playlists that start over once they complete.

Imagine now in a linked list that each node can only receive 1 pointer but can have as many outgoing pointers as we want. This transforms the linked list into a **tree**.

![](/ArrayLists,%20LinkedLists,%20Stacks%20and%20Queues/2_linkedlists/images/PowerTrees.png)

One of the most common applications of a tree are file systems. Think of each folder as a node and each file as the end of the path.

What about nodes where they can have only 1 outgoing pointer but can receive any number of incoming pointers, this creates a **disjoint set**. This allows for sequences of nodes to eventually lead to a single unique node.

If you remove all limitations on the number of incoming and outgoing pointers you get a **graph**. Graphs are used to model relationships between data. 

![](/ArrayLists,%20LinkedLists,%20Stacks%20and%20Queues/2_linkedlists/images/PowerGraphs.png)

