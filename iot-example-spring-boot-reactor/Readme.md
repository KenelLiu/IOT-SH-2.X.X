
Type 1: Reference to a static method
    If a Lambda expression is like:
    // If a lambda expression just call a static method of a class 
(args) -> Class.staticMethod(args)  
    Then method reference is like:
    // Shorthand if a lambda expression just call a static method of a class 
Class::staticMethod

Type 2: Reference to an instance method of a particular object
    If a Lambda expression is like:
    // If a lambda expression just call a default method of an object 
(args) -> obj.instanceMethod(args) 
    Then method reference is like:
    // Shorthand if a lambda expression just call a default method of an object 
obj::instanceMethod   


Type 3: Reference to an instance method of an arbitrary object of a particular type
    If a Lambda expression is like:
    // If a lambda expression just call an instance method of a  ObjectType 
(obj, args) -> obj.instanceMethod(args) 
    Then method reference is like:
    // Shorthand if a lambda expression just call an instance method of a ObjectType 
ObjectType::instanceMethod 

Type 4: Constructor method reference
    If a Lambda expression is like: 
    // If a lambda expression just create an object 
(args) -> new ClassName(args) 
    Then method reference is like: 
    // Shorthand if a lambda expression just create an object 
ClassName::new 