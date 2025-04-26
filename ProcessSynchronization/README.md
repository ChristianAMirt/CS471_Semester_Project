## This repository is for the CS471 Semester Project - Problem 2: Process Synchronization.

- To compile this project on Windows, use the command `./gradlew build` in the ProcessSynchronization directory.

- To run the program, use the command `./gradlew run --args="5000 4 2"` in the ProcessSynchronization directory. The arguments above are example arguments, where the first argument "5000" is the time in miliseconds that the program runs for. The second argument "4" is the number of producers writing to the buffer and the third argument "2" is the number of consumers. These arguments can be changed to any integer value and be sure to not put any other delimiters other than spaces between arguments.

- The program will write to the output file specified in the main function of the ProcessSynchronizationDriver class, with a default name of `output.txt`.
