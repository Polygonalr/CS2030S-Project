# NUS CS2030S AY 21/22 Sem 1 Project

This is an event scheduling program written in Java. There are multiple `Customer` which arrives at different times (type `double`). There are a specified number of `Server`, which can serve one `Customer` at any given point of time. `Server` has a specified `Queue` length as well, and when a `Customer` arrives while all the `Queues` are full, they will not be served and will instead leave.

## Compile and run

```javac -d . *.java```

Then, execute any of the Main classes with the given input files.

```java Main3 < 3_1.in```