# Pasapalabra and Tic-Tac-Toe Desktop App

This is a Java Swing desktop application for a first-year OOP project. Users can register, log in, play Pasapalabra or Tic-Tac-Toe, save unfinished games, continue saved games, view statistics, and use an admin panel.

## Default Admin

- Username: `admin`
- Password: `admin123`

## Run From Terminal

```bash
javac -d out $(find src -name '*.java')
java -cp out Main
```

## Project Structure

```text
src/
  Main.java
  game/
  manager/
  model/
  ui/
data/
  users.csv
  statistics.csv
  saved_games.csv
  pasapalabra_questions.csv
```

The application creates missing data files automatically when it starts.

## Main OOP Concepts

- Encapsulation: model fields are private and accessed through methods.
- Inheritance: `PasapalabraGame` and `TicTacToeGame` extend `Game`.
- Interfaces: `Game` implements `Saveable`.
- Polymorphism: game windows work with shared game behavior.
- Event handling: Swing buttons use action listeners.
- File I/O: users, saved games, questions, and statistics use local files.

## Storage Format

The app uses simple text files with `|` separators:

- `data/users.csv`: username, password, admin flag
- `data/statistics.csv`: username, game, date, score, result
- `data/saved_games.csv`: saved game id, type, players, state, date
- `data/pasapalabra_questions.csv`: letter, question, answer

Passwords are stored in plain text only because this is a small academic project. A real application should hash passwords.
