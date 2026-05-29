# Snake and Ladder Java Mini Project

This is a beginner-friendly console-based Snake and Ladder game written in Java using Object-Oriented Programming.

## Project Structure

```text
src/
|-- Main.java
|-- Game.java
|-- Board.java
|-- Player.java
|-- Dice.java
|-- Snake.java
|-- Ladder.java
```

## How to Run

```bash
javac src/*.java
java -cp src Main
```

## Class Explanation

### Main

Handles the menu, user input, input validation, and player creation. It keeps user input/output separate from the core game objects.

Important methods:

- `main(String[] args)`: Starts the program and displays the menu until the user exits.
- `startGame(Scanner scanner)`: Reads player details and starts a new game.
- `readPlayerCount(Scanner scanner)`: Ensures the number of players is between 2 and 6.
- `readName(Scanner scanner, String message)`: Ensures player names are not empty.
- `showRules()`: Displays the game rules.

### Game

Controls the game loop and turn-by-turn flow.

Important methods:

- `play()`: Runs the game until a player wins.
- `playTurn(Player player)`: Rolls the dice, moves the player, checks snakes/ladders, and checks for a winner.
- `showAllPlayerPositions()`: Displays all player positions after each turn.

### Board

Represents the 10x10 board with positions from 1 to 100. It stores snakes and ladders using `HashMap`.

Important methods:

- `getFinalPosition(int position)`: Checks whether a position contains a snake or ladder and returns the updated position.
- `initializeSnakes()`: Adds all snakes to the board.
- `initializeLadders()`: Adds all ladders to the board.

### Player

Stores player details.

Important methods:

- `getName()`: Returns the player's name.
- `getPosition()`: Returns the player's current position.
- `setPosition(int position)`: Updates the player's position after validation.

### Dice

Generates random dice values from 1 to 6.

Important method:

- `roll()`: Returns a random integer between 1 and 6.

### Snake

Represents one snake on the board.

Important methods:

- `getHead()`: Returns the snake's starting position.
- `getTail()`: Returns the snake's ending position.

### Ladder

Represents one ladder on the board.

Important methods:

- `getStart()`: Returns the ladder's starting position.
- `getEnd()`: Returns the ladder's ending position.

## Sample Input/Output

```text
========== Snake and Ladder ==========
1. Start Game
2. Show Rules
3. Exit
======================================
Enter your choice: 1
Enter number of players (2 to 6): 2
Enter name for Player 1: Alice
Enter name for Player 2: Bob

Game started!

Current Positions:
Alice -> 0
Bob -> 0

Alice's turn
Alice rolled a 6.
Alice moved from 0 to 6.
Great! Ladder from 6 to 25.
Alice is now at 25.

Current Positions:
Alice -> 25
Bob -> 0

Bob's turn
Bob rolled a 4.
Bob moved from 0 to 4.

Current Positions:
Alice -> 25
Bob -> 4
```

The game continues until a player reaches exactly 100.

## Time Complexity

Let `P` be the number of players and `T` be the number of turns played.

- Each turn takes `O(P)` time because all player positions are displayed after every turn.
- Snake and ladder lookup takes `O(1)` average time using `HashMap`.
- Total game time complexity is `O(T * P)`.

## Space Complexity

- Players require `O(P)` space.
- Snakes and ladders require `O(S + L)` space, where `S` is the number of snakes and `L` is the number of ladders.
- Since the board uses a fixed number of snakes and ladders, practical space complexity is `O(P)`.
