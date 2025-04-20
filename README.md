# Flappy-Bird
Flappy Bird with Java for OEP. A Java implementation of the popular Flappy Bird game using Swing for graphics and event handling.

## Features

- Classic Flappy Bird gameplay mechanics
- Smooth physics with gravity and jumping
- Randomly generated pipes
- Score tracking
- Game over detection (collision with pipes or ground)
- Restart functionality
(Working on integrating Sound & Title Screen)

##Controls

1.SPACE BAR: Make the bird jump/flap

2.R KEY: Restart the game after game over

##Code Structure

1.FlappyBird class extends JPanel and implements game logic

2.Uses Swing Timer for game loop and pipe generation

3.Custom Bird and Pipe classes to manage game objects

4.Collision detection between bird and pipes

5.Score tracking and display

## How to Run

1. Clone this repository or download the source code
2. Ensure all image files are in the correct path (same directory as the Java file)
3. Compile and run the `FlappyBird.java` file:

```bash
javac FlappyBird.java
java FlappyBird
