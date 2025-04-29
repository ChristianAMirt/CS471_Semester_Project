## This repository is for the CS471 Semester Project - Problem 2: Process Synchronization.

- To compile this project on Windows, use the command `./gradlew build` in the ProcessSynchronization directory.

- To run the program, use the command `./gradlew run --args="5000 4 2"` in the ProcessSynchronization directory. The arguments above are example arguments, where the first argument "5000" is the time in miliseconds that the program runs for. The second argument "4" is the number of producers writing to the buffer and the third argument "2" is the number of consumers. These arguments can be changed to any integer value and be sure to not put any other delimiters other than spaces between arguments.

- Alternatively, use the command `./.gradlew run --args="testInput.txt"` in the ProcessSynchronization directory to run multiple test cases from an input file. The first argument "testInput.txt" is the name of the input file with each row formatted as `testCase#    sleepTime    #Producers    #Consumers`.

- The program will write to the output file that has the same name as the input file, but has "\_output" tagged at the end of it. It will be saved in the main folder of the project.
