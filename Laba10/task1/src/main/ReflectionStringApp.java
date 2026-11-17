package main;

public class ReflectionStringApp {

    public static void main(String[] args) {
        StringReflectionModifier modifier = new StringReflectionModifier();
        ConsoleStringReader reader = new ConsoleStringReader(System.in);
        ReflectionDemoService demoService = new ReflectionDemoService(modifier, reader);

        demoService.runLiteralDemo();
        demoService.runKeyboardInputDemo();
    }
}