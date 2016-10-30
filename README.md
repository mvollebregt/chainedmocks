Wildmock is a mocking framework for Java. The current version is 1.0.0.-alpha.1.

Features are:

## Input wildcards
Input wildcards enable you to match method arguments with plain boolean expressions:

```java
// simulate behaviour for specific input values
when(a -> someMock.absolute(a), Integer.class).
    with(a -> a < 0).
    then(a -> -a);
```

## Output wildcards
Output wildcards enable you to easily check the usage of return values in subsequent method calls:

```java
// verify that the output of the first method is passed into the second method
verify(() -> {
    String value = someMock.getValue();
    someOtherMock.call(value);
```

## Comprehensive feedback
Comprehensive feedback helps you understand where it went wrong:

```
The expected call sequence in the verify clause was not observed.

The observed calls were:
1. MockSomeClass@404214852.getValue()
--- 2. MockSomeOtherClass@1822971466.call(wildmock!)

Expected calls:
--- 2. MockSomeOtherClass@1822971466.call(output of 1)
```

I'm currently working on a step by step tutorial on how to use wildmock in your own projects.
Want to know more? Go to the [Basics](https://github.com/mvollebregt/wildmock/wiki/Basics).