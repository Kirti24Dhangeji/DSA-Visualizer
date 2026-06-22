# DSA-Visualizer

## Roadmap
-----------------------------------------------------------------------------------------
Module              | Phases    | Role
-----------------------------------------------------------------------------------------
custom-collections/ | 1 + 2     | The foundation — every data structure from scratch
algorithms/         | 4 + 5     | Event system + all algorithm implementations
spring-backend/     | 6         | REST API wrapping the algorithm engine
frontend/           | 7–12      | React visualizer consuming the event steps
documentation/      | 13        | Design decisions, complexity tables, architecture notes
-----------------------------------------------------------------------------------------

## Directory structure
custom-collections/
├── pom.xml                                                         // Maven config: Java 17, JUnit 5, artifact ID
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── dsa/
    │               └── collections/
    │                   ├── interfaces/                             // Core contracts every collection must fulfil
    │                   │   ├── MyCollection.java                   // Base interface — add, remove, contains, size, isEmpty, clear, iterator
    │                   │   ├── MyList.java                         // Extends MyCollection — add(index), get, set, remove(index), indexOf
    │                   │   ├── MyStack.java                        // LIFO contract — push, pop, peek, isEmpty, size
    │                   │   ├── MyQueue.java                        // FIFO contract — enqueue, dequeue, front, isEmpty, size
    │                   │   ├── MyMap.java                          // Key-value contract — put, get, remove, containsKey, size, clear
    │                   │   └── MyTree.java                         // Hierarchical contract — insert, delete, search, height, isEmpty
    │                   ├── iterator/                               // Custom iteration without relying on java.lang.Iterable
    │                   │   └── MyIterator.java                     // Generic iterator — hasNext() and next() only
    │                   ├── exception/                              // Domain-specific runtime exceptions
    │                   │   ├── EmptyCollectionException.java       // Thrown on pop/dequeue/peek when collection is empty
    │                   │   ├── MyIndexOutOfBoundsException.java    // Thrown on invalid index — includes index and size in message
    │                   │   └── KeyNotFoundException.java           // Thrown when a requested map key does not exist
    │                   ├── linear/                                 // Sequential, order-preserving data structures
    │                   │   ├── MyArrayList.java                    // Dynamic array — doubles capacity on resize, O(1) get, O(n) insert
    │                   │   ├── MyLinkedList.java                   // Doubly-linked list — O(1) head/tail ops, bi-directional nodeAt
    │                   │   ├── MyStackImpl.java                    // Stack backed by MyLinkedList — push/pop at tail for O(1)
    │                   │   └── MyQueueImpl.java                    // Queue backed by MyLinkedList — enqueue at tail, dequeue at head
    │                   ├── associative/                            // Key-value mapping structures
    │                   │   └── MyHashMap.java                      // Separate chaining — default capacity 16, load factor 0.75, auto-resize
    │                   ├── tree/                                   // Hierarchical node-based structures
    │                   │   ├── MyBST.java                          // Binary Search Tree — insert/delete/search + 4 traversal methods
    │                   │   └── MyAVLTree.java                      // Self-balancing AVL — extends MyBST, overrides insert/delete, 4 rotation cases
    │                   ├── priority/                               // Priority-ordered access structures
    │                   │   ├── MyHeap.java                         // Array-backed heap — configurable min/max mode, siftUp and siftDown
    │                   │   └── MyPriorityQueue.java                // Implements MyQueue using MyHeap — highest-priority element exits first
    │                   └── graph/                                  // Graph representation
    │                       └── MyGraph.java                        // Adjacency-list graph — directed/undirected, weighted, inner Edge class
    └── test/
        └── java/
            └── com/
                └── dsa/
                    └── collections/
                        ├── linear/                                 // Tests for sequential structures
                        │   ├── MyArrayListTest.java                // add, resize trigger, remove by index/value, indexOf, iterator
                        │   ├── MyLinkedListTest.java               // add to head/tail/mid, unlink, doubly-linked integrity
                        │   ├── MyStackImplTest.java                // push/pop/peek order, EmptyCollectionException on empty stack
                        │   └── MyQueueImplTest.java                // FIFO order, enqueue/dequeue, front accuracy
                        ├── associative/                            // Tests for hashing behaviour
                        │   └── MyHashMapTest.java                  // put/get, collision chaining, resize at 0.75, remove, null key
                        ├── tree/                                   // Tests for tree correctness
                        │   ├── MyBSTTest.java                      // Ordered insertion, traversal output, delete leaf/one/two-child nodes
                        │   └── MyAVLTreeTest.java                  // LL/RR/LR/RL rotations, balance factor stays in [-1, 0, 1]
                        ├── priority/                               // Tests for heap property invariants
                        │   ├── MyHeapTest.java                     // Min-heap and max-heap property after each insert and poll
                        │   └── MyPriorityQueueTest.java            // Dequeue always returns the highest-priority element
                        └── graph/                                  // Tests for graph construction and traversal
                            └── MyGraphTest.java                    // addVertex, addEdge, undirected symmetry, weighted neighbour retrieval