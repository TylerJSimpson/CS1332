# Prerequisite Review

# Index

- [Java Review](#java-review)
    - [Visibility Modifiers and Constructors](#visibility-modifiers-and-constructors)
        - [Visibility Modifiers](#visibility-modifiers)
        - [Constructors](#constructors)
    - [Value/Reference Equality with Primitives and Strings](#valuereference-equality-with-primitives-and-strings)
    - [Value/Reference Equality with Objects](#valuereference-equality-with-objects)
    - [Pass by Value/Reference](#pass-by-valuereference)


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