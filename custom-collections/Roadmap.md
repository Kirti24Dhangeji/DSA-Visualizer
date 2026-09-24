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

## Run commands

custom-collections> mvn clean
custom-collections> mvn test-compile
custom-collections\src> java -cp target\classes;target\test-classes com.dsa.collections.linear.*
custom-collections\src> java -cp target\classes;target\test-classes com.dsa.collections.associative.*