/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ir.darkdeveloper;

public class App {
    public String getGreeting() {
        return "Hello, World!";
    }

    public int division(int a, int b) {
        if (b == 0)
            throw new IllegalArgumentException("Division by zero");
        return a / b;
    }

}