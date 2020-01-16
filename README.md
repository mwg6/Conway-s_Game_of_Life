Simple visualizer of Conway's Game of Life. 

Rules are cells die if:
  there are more than XX cells sorrounding them
  there are fewer than XX cells sorrounding them
Cells come to life if:
  Three cells around them are alive

For each new game (currently) cells are randomly distributed on the board. Patterns will emerge through generations.

The application should be fairly easy to run and works well as a (memory intensive) screen saver. Just run:

mvn clean install

and once that's completed hit the main method in src/main/java/Application.java
