# org.brutusin:logging-instrumentation [![Build Status](https://api.travis-ci.org/brutusin/logging-instrumentation.svg?branch=master)](https://travis-ci.org/brutusin/logging-instrumentation)
This module is an extension of [instrumentation module](https://github.com/brutusin/instrumentation) that defines an *interceptor* ([LoggingInterceptor](src/main/java/org/brutusin/instrumentation/logging/LoggingInterceptor.java)) aimed at logging executions of third-party code.

**Table of Contents**

- [org.brutusin:logging-instrumentation](#orgbrutusinlogging-instrumentation)
  - [Output](#output)
  - [Tests](#tests)
  - [TODO](#todo)
  - [Support, bugs and requests](#support-bugs-and-requests)
  - [Authors](#authors)
  - [License](#license)

## Output

For each execution of an instrumented method, the agent generates a file with the following information of the execution:

* **Source**: Method instrumented
* **Start**: Execution start date
* **Parameters**: JSON Serialization (if possible) of the method arguments
* **Elapsed**: Execution duration
* **Returned**: JSON Serialization (if possible) of the method arguments/exceptions

Files are ordered according to their execution order, and grouped in folders by the execution thread. Root logging folder is passed as an interceptor parameter via the agent parameters.

## Tests
Remark that project tests are run after a fat-agent-jar ([jar-with-dependencies](http://maven.apache.org/plugins/maven-assembly-plugin/descriptor-refs.html#jar-with-dependencies)) is created, and the *interceptor* class name is passed as the agent options (see [pom.xml line 91](pom.xml#L91))

In this example the only instrumented methods are these of [SimpleClass](/src/test/java/org/brutusin/instrumentation/logging/SimpleClass.java):
```java
public static String sayHello(String name) {
    return "Hello " + name + ", you fool, I love youuu! " + joinTheJoyRide();
}

public static String joinTheJoyRide() {
    return "C'mon join the joyrideee!";
}

public static String sayGoodBye() {
    return "Goodbye to you, goodbye to broken hearts";
}
```

, being the relevant application code ([LoggingInterceptorTest](src/test/java/org/brutusin/instrumentation/logging/LoggingInterceptorTest.java)):
```java
SimpleClass.sayHello("world");
SimpleClass.sayGoodBye();
```

Causing the following three files being generated under `${java.io.tmpdir}/${project.artifactId}-tests` (one per method execution):
```
1-1-org.brutusin.instrumentation.logging.SimpleClass.sayHello().log
1-2-org.brutusin.instrumentation.logging.SimpleClass.joinTheJoyRide().log
1-3-org.brutusin.instrumentation.logging.SimpleClass.sayGoodBye().log
```

with content:

*1-1-org.brutusin.instrumentation.logging.SimpleClass.sayHello().log*:
```
#Source: public static java.lang.String org.brutusin.instrumentation.logging.SimpleClass.sayHello(java.lang.String)
#Start: Thu Jan 22 12:45:20 CET 2015
#Parameters:
[ "world" ]
#Elapsed: 455 ms
#Returned:
"Hello world, you fool, I love youuu! C'mon join the joyrideee!"
```
*1-2-org.brutusin.instrumentation.logging.SimpleClass.joinTheJoyRide().log*:
```
#Source: public static java.lang.String org.brutusin.instrumentation.logging.SimpleClass.joinTheJoyRide()
#Start: Thu Jan 22 12:45:21 CET 2015
#Parameters:
[ ]
#Elapsed: 5 ms
#Returned:
"C'mon join the joyrideee!"
```
*1-3-org.brutusin.instrumentation.logging.SimpleClass.sayGoodBye().log*:
```
#Source: public static java.lang.String org.brutusin.instrumentation.logging.SimpleClass.sayGoodBye()
#Start: Thu Jan 22 12:45:21 CET 2015
#Parameters:
[ ]
#Elapsed: 21 ms
#Returned:
"Goodbye to you, goodbye to broken hearts"
```

## TODO
This project is still under development but serves well as an example. The most important issue is the implementation of these [LoggingInterceptorTest](src/test/java/org/brutusin/instrumentation/logging/LoggingInterceptorTest.java) methods:
```java
 @Override
    public boolean interceptClass(String className, byte[] byteCode) {
        return className.endsWith("SimpleClass");
    }

    @Override
    public boolean interceptMethod(ClassNode cn, MethodNode mn) {
        return true;
    }
```
making the module non-reusable.
The idea to a future implementation is to load a configuration file from the root logging folder passed, for example a `logging.json`, defining the rules to be used by the interceptor to evaluate if a class or method is being instrumented.

Nevertheless and for the time being, you can create your own implementation based on this example.

## Support, bugs and requests
https://github.com/brutusin/logging-instrumentation/issues

## Authors

- Ignacio del Valle Alles (<https://github.com/idelvall/>)

Contributions are always welcome and greatly appreciated!

## License
Apache License, Version 2.0
http://www.apache.org/licenses/LICENSE-2.0
