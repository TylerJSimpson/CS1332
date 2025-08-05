# Prerequisite Review

# Index

- [Java Review](#java-review)
    - [Visibility Modifiers and Constructors](#visibility-modifiers-and-constructors)
        - [Visibility Modifiers](#visibility-modifiers)
        - [Constructors](#constructors)
    - [Value/Reference Equality with Primitives and Strings](#valuereference-equality-with-primitives-and-strings)
    - [Value/Reference Equality with Objects](#valuereference-equality-with-objects)
    - [Pass by Value/Reference](#pass-by-valuereference)
    - [Generics](#generics)
    - [Generics Syntax](#generics-syntax)
- [Itera(ble/tor) and Compara(ble/tor) ](#iterabletor-and-comparabletor)
    - [Iterable and Iterator](#iterable-and-iterator)
    - [Comparable and Comparator](#comparable-and-comparator)
- [Big-O Concepts](#big-o-concepts)
    - [Motivation and Definitions](#motivation-and-definitions)
    - [Conventions and Examples](#conventions-and-examples)

## Java Review

### Visibility Modifiers and Constructors

#### Visibility Modifiers

```java
public class Point {
    public double x;
    public double y;
}
```

If these variables are `public` outside entities can access them and change the values.

If these variables are `private` we can control the access and changing of the values via `getter` and `setter` methods.

```java
public class Point {
    private double x;
    private double y;

    public double getX() {return x;}
    public double setX(double x) {this.x = x;}
}
```

|Visibility Modifier|Class and Inner Classes|Package|Subclasses|World|
|-------------------|-----------------------|-------|----------|-----|
|public|X|X|X|X|
|protected|X|X|X||
|Package-Private|X|X|||
|private|X||||

We will use primarily `public` or `private` in this course.

#### Constructors

```java
public class Point {
    private double x;
    private double y;

    public Point(double a, double b) {
        x = a;
        y = b;
    }
}
```

This is a full constructor. 

```java
public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
```

`this.x` is necessary when the variable name is the same to specify instance variable.

```java
public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double a) {
        this(a, 0);
    }
}
```

Here we use **constructor chaining** where the value `a` is set to `x` and the value `0` is set to `y`. This is accessing the more detailed `Point` to make these assignments. This allows object instantiation flexibility where we can provide only a portion of the objects entities.

### Value/Reference Equality with Primitives and Strings

There are two main types of equality checking **value** and **reference**.

**Reference Equality** - Check if two objects are the exact same object in memory.

`==` operator

**Value Equality** - Check if two objects are equal based on the user's specific use case and design.

`.equals()` operator

`.equals()` will default to `==` unless it has been overwritten. 

Primitive types (int, long, double, char, etc.) do not extend the `Object` class so they do not inherit `.equals()` and you must use `==` operator.

```java
int one1 = 1;
int one2 = 1;
int two1 = 2;
int two2 = 2;

one1 == one2; // => true
two1 == two2; // => true
one1 == two1; // => false
one1 == 1; // => true
two1 == 2; // => true
```

When creating a string in Java you can do so by simply putting the string in quotes `"THIS IS A STRING"`. This creates a **string literal**. This behaves similar to the primitives above but because `String` extends `Object` you can also use `.equals()`.

```java
String literal = "This is a string.";
String object = new String("This is a string.");
String unequal = "Nope.";

// Using == On Value Equal Strings
literal == object;                           // => false
literal == "This is a string.";              // => true
"This is a string." == "This is a string.";  // => true
object == "This is a string.";               // => false
object == new String("This is a string.");   // => false

// Using .equals() On Value Equal Strings
literal.equals(object);                      // => true
object.equals(literal);                      // => true
literal.equals("This is a string.");         // => true
object.equals("This is a string.");          // => true
"This is a string.".equals("This is a string.");               // => true
"This is a string.".equals(new String("This is a string."));   // => true

// Using == and .equals() on Unequal Strings
literal == unequal;          // => false
object == unequal;           // => false
literal.equals(unequal);     // => false
object.equals(unequal);      // => false
```

### Value/Reference Equality with Objects

Again we will mostly use `==` operator to check if an `Object` is `null`. This is because `equals()` will result in a `NullPointerException`.

```java
String nullObject = null;
String normalObject = "normal";

// The correct way of checking if an object is null
nullObject == null;   // => true
normalObject == null; // => false

// The String class checks if argument is null, so it will throw NPE.  
// If a .equals() method doesn't check for a null argument, it will crash and throw a NPE.
// Here, we are comparing the value stored in normalObject to the values null, and nullObject
normalObject.equals(null);       // => false
normalObject.equals(nullObject); // => false

// However, here we invoking the .equals() method on a null object stored in nullOject
// This will cause a NPE to be thrown, regardless of the parameter in the .equals() method
nullObject.equals(normalObject);  // causes NullPointerException
nullObject.equals(null);          // causes NullPointerException
```

Note that sometimes for code to work you will need to wrap primitive types in their wrapper `Object` class such as int -> Integer.

```java
Integer primitive = 1;
Integer object = new Integer(1);
Integer unequal = 2;

// Using == On Value Equal Integers
primitive == object;        // => false
primitive == 1;             // => true
object == 1;                // => true
object == new Integer(1);   // => false

// Using .equals() On Value Equal Integers
primitive.equals(object);                 // => true
object.equals(primitive);                 // => true
primitive.equals(1);                      // => true
object.equals(1);                         // => true
(new Integer(1)).equals(1);               // => true
(new Integer(1)).equals(new Integer(1));  // => true

// Using == and .equals() on Unequal Integers
primitive == unequal;        // => false
object == unequal;           // => false
primitive.equals(unequal);   // => false
object.equals(unequal);      // => false

// If run, the following do not work since 1 is considered a primitive without autoboxing
1.equals(primitive);  // causes Error
1.equals(1);          // causes Error
```

In this course unless checking for `null` default to using `.equals()`.

### Pass by Value/Reference

```java
public static void main(String[] args) {
    int count = 0;
    helper(count);
    System.out.println(count);
}

public static void helper(int x) {
    x = x + 1;
}
```

In the code above, when we run `main` we get an output of 0. But the `helper` function should increase this to 1, should it not?

**Pass by Value** - The `helper` method takes in the *value* of what was passed in. Changing the value will change only the value of the local variable.

**Pass by Reference** - The `helper` method takes in the *reference* of what was passed in. Changing the value will change the value of the local variable and the original variable.

**Java is a pass by value language.**

```java
class Container {
    private int item;

    public Container(int item) {
        this.item = item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getItem() {
        return item;
    }
}
```

If we run this code:

```java
public static void main(String[] args) {
    Container count = new Container(0);     // step 1: create new Container named count
    helper(count);                          // step 2: call the helper method on count
    System.out.println(count.getItem());    // step 4: print out the value of count
}

public static void helper(Container x) {
    x = new Container(x.getItem() + 1);     // step 3: create a new object reference with 
                                            // its item set to x's item + 1
}
```

This will return 0.

This is because `count` is not a `Container` object it just stores a value that points to a `Container` object. This is called a **reference variable**. i.e. `count` points to a `Container` object.

When we pass the value to `helper` it stores a duplicate since **java is a pass by value language**. 

```java
public static void main(String[] args) {
    int count = 0;                      // step 1: create a new int named count
    count = helper(count);              // step 2: call helper on count
                                        // step 5: assign the returned value to count (still on the line above, but after the function call)
    System.out.println(count);          // step 6: print the value of count
}

public static int helper(int x) {
    x = x + 1;                          // step 3: increment the value of x
    return x;                           // step 4: return the value of x
}
```

This approach will return 1 because in the `helper` method we change the value of x and return the new value. 

```java
public static void main(String[] args) {
    Container count = new Container(0);     // step 1: create new Container named count
    helper(count);                          // step 2: call helper on count
    System.out.println(count.getItem());    // step 4: print the value of count
}

public static void helper(Container x) {
    x.setItem(x.getItem() + 1);             // step 3: set x's item to x's item + 1, modifying the original object
}
```

Here we can do the same with Objects as we did above with primitive types. 

### Generics

```java
public class Container {
    private Object object;

    public void set(Object object) {
        this.object = object;
    }

    public Object get() {
        return object;
    }
}
```

When using specific data types inside the `Container` notice that each time we wanted to use `String s` we would need to specify that it is a string.

```java
Container c1 = new Container();
c1.set("hello");
String s = (String)c1.get();
```

Further, this allows us to do unknowingly get `ClassCastException` errors like below:

```java
Integer i = (Integer)c2.get();
```

The fix for this is to use **Generics** where we specify a type parameter such as `T` below.

```java
public class Container<T> {
    private T t;

    public void set(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }
}
```

Anywhere inside the class we refer to `T` we know it will be the same type. Now we can use a generic method for using the variables assigned this type as well without casting to String as above.

```java
Container<String> c1 = new Container<String>();
c1.set("hello");
String s = c1.get();
```

Further, the error above will now be caught at compilation instead of run time:

```java
Container<String> c2 = new Container<String>();
c2.set("hello");
Integer i = (Integer)c2.get();
```

### Generics Syntax

We use the angle brackets often called *the diamond* `<>` for generic classes. Generally you use a single upper-case letter with `T` often being the default.

```java
class ArrayList<T> {}
```

We also use the diamond for declaring instanes of the generic classes.

```java
ArrayList<Integer> listOne = new ArrayList<Integer>();
```

You can also remove the type from the declaration because the Java compiler can infer it.

```java
ArrayList<String> list = new ArrayList<>();
```

In data structures and algorithms we will frequently need to use generic arrays. To do this you must cast the array to the generic type, you cannot create the array of the generic type directly.

Error:

```java
T[] backingArray = new T[10];
```

Correct:

```java
T[] backingArray = (T[]) new Object[10];
```

We will also encounter instances where classes require multiple generic type parameters:

```java
class HashMap<K, V> { ... }
```

```java
HashMap<String, Integer> map = new HashMap<>();
```

Sometimes generic type parameters are even nested during declaring and instantiation:

```java
ArrayList<BSTNode<T>> nodeList = new ArrayList<>();
```

## Itera(ble/tor) and Compara(ble/tor) 

### Iterable and Iterator

The **iterable** interface:
- An abstraction that allows the implementing class to be iterated through with a for-each loop
- Has abstract method called `iterator()` that returns an `Iterator` object to handle traversing the data structure

You can implement the iterator interface directly by overriding the `next()` and `hasNext()` methods and optionally the `remove()` method.

Abstract Methods:

```java
// Returns next data in iteration order
public abstract T next();

// Returns whether there is more data
public abstract boolean hasNext();
```

Non-Abstract Methods:

```java
// Removes the data returned by next()
public void remove();
```

Using **iterator**:
```java
import java.util.Iterator;
public class BookList<Book> implements Iterator<Book> {
    
    public void next() {}
    public boolean hasNext() {}
}

while (bookList.hasNext()) {
    System.out.println(bookList.next());
}
```

Using **iterable**:
```java
import java.util.Iterator;
public class BookList<Book> implements Iterable<Book>{

    public Iterator<Book> Iterator() {}
}

Iterator<Book> bookIterator = bookList.iterator();
while (bookIterator.hasNext()) {
    System.out.println(bookIterator.next());
}
```

**Iterable**:
- Allows for direct control of object iteration for more custom behavior
- Depends on both data structure and iterator

**Iterator**:
- Abstracts control away from user to allow convenience for more generic needs
- Has no dependencies besides data structure to iterate through

In this course we will mostly just use a for-each loop to iterate through Java collections like `ArrayList` and `LinkedList` which implement `Iterable` already. 

### Comparable and Comparator

## Big-O Concepts

### Motivation and Definitions

### Conventions and Examples