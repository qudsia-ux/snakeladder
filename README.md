# Snake and Ladder Game

## Project Overview

Snake and Ladder is a multiplayer board game developed using Java and Java Swing. The game allows multiple players to compete on a 10x10 board while following the traditional Snake and Ladder rules. The project demonstrates Object-Oriented Programming (OOP) concepts and graphical user interface development using Swing.

---

## Authors

* Qudsia Fatima
  
---

## Technologies Used

* Java
* Java Swing
* Object-Oriented Programming (OOP)
* Git & GitHub

---

## Features

* Multiplayer support (2–6 players)
* Interactive graphical user interface using Swing
* Random dice rolling
* Automatic turn management
* Snakes and ladders implementation
* Real-time player position updates
* Winner announcement
* User-friendly game interface

---

## OOP Concepts Implemented

### Encapsulation

Player, Board, Dice, Snake, and Ladder classes encapsulate their respective data and behaviors.

### Abstraction

Game logic is separated from the user interface.

### Modularity

The project is divided into multiple classes, each responsible for a specific functionality.

### Association

The Game class interacts with Player, Board, Dice, Snake, and Ladder objects.

---

## Project Structure

```text
src/
│
├── Main.java
├── Game.java
├── Board.java
├── Player.java
├── Dice.java
├── Snake.java
├── Ladder.java
├── GameFrame.java
└── BoardPanel.java
```

---

## Class Description

### Main.java

Entry point of the application. Launches the game interface.

### Game.java

Controls game flow, player turns, movement logic, and winner determination.

### Board.java

Stores board information including snakes and ladders.

### Player.java

Maintains player details such as name and current position.

### Dice.java

Generates random dice values between 1 and 6.

### Snake.java

Represents snakes with head and tail positions.

### Ladder.java

Represents ladders with start and end positions.

### GameFrame.java

Creates the main Swing window and manages user interaction.

### BoardPanel.java

Displays the game board, player tokens, snakes, and ladders.

---

## Game Rules

1. Each player rolls the dice in turn.
2. Players move according to the dice value.
3. Landing on a ladder moves the player upward.
4. Landing on a snake moves the player downward.
5. A player must reach exactly position 100 to win.
6. The first player to reach 100 wins the game.

---

## How to Run

### Compile

```bash
javac src/*.java
```

### Run

```bash
java -cp src Main
```

### Run Using VS Code

1. Open the project folder in VS Code.
2. Ensure Java Extension Pack is installed.
3. Open `Main.java`.
4. Click the **Run** button.

---

## Sample Gameplay

```text
Player 1 rolled a 6
Player 1 moved from 4 to 10
Ladder! Move from 10 to 28

Player 2 rolled a 4
Player 2 moved from 8 to 12

Player 1 reached 100
Congratulations! Player 1 wins!
```

---

## Future Enhancements

* Save and load game functionality
* Leaderboard system
* Sound effects
* Dice animation
* Custom board configuration
* Online multiplayer support

---

## Educational Purpose

This project was developed as a mini-project to demonstrate Java programming, Swing GUI development, and Object-Oriented Programming principles.

## 📸 Screenshots

### 🚀 Start Screen

<img width="280" height="138" alt="start" src="https://github.com/user-attachments/assets/5644b7fd-d680-4673-b04e-b085c744ce0c" />
*The main game window where players can start the Snake and Ladder game.*

---

### 👥 Player Registration

<img width="221" height="103" alt="players" src="https://github.com/user-attachments/assets/f55263ca-5004-475d-9ffc-a3fb32c917e7" />

*Players enter their names before the game begins.*

---

### 🎮 Gameplay

<img width="960" height="504" alt="gameplay" src="https://github.com/user-attachments/assets/36ac07f5-c34c-4b32-8e8a-a8ae3c0f2e26" />

*Players take turns rolling the dice and moving across the board.*

---

### 🎲 Game Board

<img width="960" height="502" alt="board" src="https://github.com/user-attachments/assets/07f8a2b7-4683-460a-87b0-ce93f43e7948" />

*Graphical representation of the Snake and Ladder board with player positions.*

---

### 🏆 Winner Announcement

<img width="960" height="503" alt="winner" src="https://github.com/user-attachments/assets/4f9f6360-953c-4559-8545-442f2eb711d7" />

*The game announces the winner once a player reaches position 100.*
