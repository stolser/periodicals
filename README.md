The repository contains several study projects.
There are 3 major branches:
* **develop** - contains all the tasks and projects _**except for 'Webproject' (Project-IV)**_;
* **webproject** - contains _**only 'Webproject'**_;
* **master** - is container for the whole codebase. Contains all the files from all the projects being developed during the course. Due to some unresolved conflicts code in this branch can be uncompilable and not working.
_**This branch should be used only for the purpose of running the total statistics on all the projects!**_

## The list of all projects and tasks:
1. [Block2. More-Less Game](https://github.com/stolser/JavaTraining2016Aut/tree/develop/src/main/java/com/stolser/javatraining/block02/morelessgame)

2. [Block04. RecordBook app](https://github.com/stolser/JavaTraining2016Aut/tree/develop/src/main/java/com/stolser/javatraining/block04/recordbook)
Validation of the user’s input using regular expressions.

3. [Block05. Reflection API](https://github.com/stolser/JavaTraining2016Aut/tree/develop/src/main/java/com/stolser/javatraining/block05/reflection)
    * creating custom annotations and proxy classes that process them;
    * creating a proxy for Immutability;
    * writing unit test;
    * creating a proxy for mocking.

4. [Block07. Array and Collection](https://github.com/stolser/JavaTraining2016Aut/tree/develop/src/main/java/com/stolser/javatraining/block07/collection)
    * Task1: print the elements of the first array which are not equal to any element from the second one. Implement methods using 1) a for loop; 2) a while loop; 3) collections.
    * Use generics.
    * Task2: sort an array by the number of repetitions of its elements.
    * Task3: write unit tests.

5. [Project 01](https://github.com/stolser/JavaTraining2016Aut/tree/develop/src/main/java/com/stolser/javatraining/project01)

6. Custom collection implementations
    * [CustomArrayList, CustomLinkedList, CustomTreeSet, CustomTreeMap](https://github.com/stolser/JavaTraining2016Aut/tree/develop/src/main/java/com/stolser/javatraining/collections/);
    * [Custom implementation of java.util.Queue<E>](https://github.com/stolser/MostRecentlyInsertedQueue):
_**MostRecentlyInsertedQueue**_ - is an implementation of java.util.Queue. The purpose of this queue is to store the N most recently inserted elements.
The queue has the following properties:
    * implements the java.util.Queue interface;
    * the queue is bounded in size. The total capacity of the queue is passed into the constructor and cannot be changed;
    * new elements are added to the tail and removed from the head of the queue;
    * the queue is traversed from head to tail;
    * the queue is always accepting new elements. If the queue is already full, the oldest element that was inserted is deleted, and the new element is added from the tail;
    * this queue does not allow null elements.
_**ConcurrentMostRecentlyInsertedQueue**_ - a thread-safe non-blocking variant of MostRecentlyInsertedQueue.
_**MostRecentlyInsertedBlockingQueue**_ - a thread-safe variant of MostRecentlyInsertedQueue that implements java.util.concurrent.BlockingQueue.

7. [Design Patterns](https://github.com/stolser/JavaTraining2016Aut/tree/develop/src/main/java/com/stolser/javatraining/designpatterns)

8. ['Webproject' (Project-IV) ](https://github.com/stolser/JavaTraining2016Aut/tree/webproject/src/main/java/com/stolser/javatraining/webproject)




