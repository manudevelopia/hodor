# Hodor
Hodor is the lib that holds the door before application closes in a crash, proving a last chance to inform someone that something was not good.

# Why Hodor?
Sometimes you want to make your java application to say something on a crash situation and send a metric, one message or something similar, to make someone to know about the failure. 
Hodor wraps your application execution and catches Throwable, maybe this is not beautiful, but does the trick.

# Usage
Hodor can hold the door for your application with or without @Annotations, it's your choice. 
- Annotate or modify 'main'.
- Provide a 'HoldTheDoor' method to be called while crash. 

### What to add to 'HoldTheDoor' method?
Application failures may vary a lot but Hodor should manage all those special situations so consider seriously @HoldTheDoor annotated method content.
Avoid doing heavy stuff there, it's not the place and might also fail.

Use this method to: write a log, send a metric, a message, etc, etc... in the end, simple operation(s) that might run without errors even in a _OutOfMemory_ application crash scenario.


### Using Annotation
- Add dependency to build.gradle
```
compileOnly 'info.developia:hodor:0.1'
implementation 'info.developia:hodor:0.1'
```
- Add @Hodor annotation to your current main method and create a method with your desired name but that accepts Throwable as argument.
```java
package info.developia.application;

public class App {
    @Hodor
    public static void main(String[] args) {
        Application application = new Application();
    }
    @HoldTheDoor
    private static void sendSOS(Throwable t) {
        System.out.println("Sending a SOS!");
    }
}
```
- After your project gets built, check on 'build' at 'generated/sources/annotationProcessor/' folder and follow @Hodor annotated application class package. You will find a generated class with your class name ending with Hodor.

 On our example, App class is on _info.developia.application_ package, so class to find will be at:
```
build/generated/sources/annotationProcessor/info/developia/application/AppHodor.java
``` 
- Execute generated AppHodor.java on your IDE or modify your jar command to make AppHodor class as starting point. 



### Not using Annotation
- Add dependency to build.gradle
```
implementation 'info.developia:hodor:0.1'
```
- Move your main content to a new method with your desired name and add Hodor.holdTheDoor() to call it.
```java
package info.developia.application;

public class App {
    public static void main(String[] args) {
        Hodor.holdTheDoor((a) -> App.application(args), (t) -> App.sendSOS(t));
    }
    
    public void application(String[] args){
        Application application = new Application();
    }
    
    private static void sendSOS(Throwable t) {
        System.out.println("Sending a SOS!");
    }
}
```
- Execute modified class on your IDE. 

