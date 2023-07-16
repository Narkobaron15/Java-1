# Java notes for beginner

### Common

Method `public static void main` in a public class is an entry point to the program.  

`package` is a namespace of classes declared in a file.  
`import` is used for surprisingly importing other files' classes.  

### Databases

**JDBC (Java Database Connectivity)** is a protocol for database manipulations in Java.  
There is a common ORM used across Java project - **Hibernate**.

### Console

`System.out.print` prints a line.  
`System.out.printf` prints formatted string. `%s` is replaced with a string argument.  
`System.out.println` prints a line terminated with `"\n"`.  

Console input is handled via `System.in` directly 
or using Scanner instance (passing `System.in` as a parameter).

### Inheritance (same as in TypeScript and PHP)
`extends` directive means derivation from a base class.  
`implements` means implementation of an interface.

### String

`String` class is *CAPITALIZED*.  
Compare string using `.equals()` or `.equalsIgnoreCase()`, operator `==` will act as `ReferenceEquals()`.

### Try-catch-finally

'Try-catch-finally' trio works as in any other language.  
Also `try` may hold disposable items (they implement `AutoCloseable` interface), 
then `catch` and `finally` statements are optional.  
Also `catch` and/or `finally` statements are optional 
when the signature of a method declares possible exception using throws directive.