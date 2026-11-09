<div align="center">

#  Mohib Hasan Math Quiz

**A fast, modern math quiz game built with Java 17, Spring Boot, Thymeleaf and JDBC.**
*Think fast. Calculate smart. Climb the Hall of Fame.*

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-6DB33F?logo=springboot&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Templates-005F0F?logo=thymeleaf&logoColor=white)
![H2](https://img.shields.io/badge/Database-H2-1021FF)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white)

<br>



</div>

---

## 📖 Table of Contents

- [About](#-about)
- [Screenshots](#-screenshots)
- [Features](#-features)
- [How the Game Works](#%EF%B8%8F-how-the-game-works)
- [Tech Stack](#%EF%B8%8F-tech-stack)
- [Getting Started](#-getting-started)
- [Database](#%EF%B8%8F-database)
- [Project Structure](#-project-structure)
- [Core Math Methods](#-core-math-methods)
- [Testing](#-testing)
- [Author](#-author)

---

## 🎯 About

**Math Whiz** is a server-rendered web quiz. A player enters a name, answers **four randomly generated math questions** (prime check, modulus, and two trigonometry problems), gets a score out of **10**, and lands on a **Top 10 Hall of Fame** leaderboard stored in a database.

All answer checking is done **on the server** using reusable Java methods, so the correct answers are never exposed to the browser.

---

## 📸 Screenshots

### 1. Welcome Screen
Players enter their name and start a 4-question NORMAL difficulty round.

 <img width="1260" height="600" alt="Screenshot 2026-11-09 182133" src="https://github.com/user-attachments/assets/2f201874-1cf4-45c2-a558-8bbd02626e19" />



### 2. Quiz Screen
Four questions with their marks shown on each card. Maximum score: **10/10**.
<img width="1230" height="617" alt="Screenshot 2026-11-09 182214" src="https://github.com/user-attachments/assets/4c0899af-544a-4ee4-b2ef-1ef9b9228011" />



### 3. (Leaderboard)
Top 10 players: highest scores first, ties broken by the earliest play time.

 <img width="1263" height="612" alt="Screenshot 2026-11-09 182339" src="https://github.com/user-attachments/assets/892bb5eb-4fd2-4bc7-b8ad-986e5e91f723" />


### 4. Saved Scores in the Database
Every finished game is stored with the player name, score, difficulty and timestamp (shown here in MySQL Workbench).

 <img width="1328" height="689" alt="Screenshot 2026-11-09 180521" src="https://github.com/user-attachments/assets/220e2012-f4cd-4984-9167-b8dd990caf73" />


## ✨ Features

- 🎮 **Complete game flow:** welcome → quiz → result → leaderboard
- 🎲 **Random questions** generated fresh for every game
- 🧠 **Server-side validation** using reusable, unit-tested Java methods
- 📐 **±0.01 tolerance** for trigonometry answers (answers to 2 decimal places)
- 💾 **Persistent scores:** name, score, difficulty and timestamp saved via JDBC `PreparedStatement` (through `JdbcTemplate`)
- 🏆 **Top 10 leaderboard** sorted by score (descending), then play time (ascending)
- 🛡️ **Input validation:** blank names, names over 100 characters and expired sessions are handled gracefully
- 🌙 **Sleek dark UI** with a neon-green accent, built with custom CSS

---

## 🕹️ How the Game Works

| # | Topic | Question | Marks |
|---|-------|----------|:-----:|
| Q1 | Prime Number | Is *n* a prime? Enter `1` for Prime or `0` for Not Prime (n between 10 and 50) | 2 |
| Q2 | Modulus | Calculate `a % b` | 2 |
| Q3 | Trigonometry | Calculate SIN / COS / TAN of an angle, up to 2 decimal places | 3 |
| Q4 | Trigonometry | Calculate SIN / COS / TAN of another angle, up to 2 decimal places | 3 |
| | | **Maximum score** | **10** |

```mermaid
flowchart LR
    A[Enter name] --> B["POST /start<br/>generate 4 questions"]
    B --> C[Answer on game page]
    C --> D["POST /submit<br/>validate on server"]
    D --> E[Save score via JDBC]
    E --> F[Result page]
    F --> G[Hall of Fame]
```

**Answer rules**

- **Prime:** only `0` or `1` is accepted.
- **Modulus:** must be a whole number.
- **Trigonometry:** correct if within **±0.01** of the exact value.

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.5.6 (Web, Thymeleaf, JDBC) |
| View | Thymeleaf + custom CSS |
| Database | H2 (file mode) by default |
| Data access | `JdbcTemplate` with `PreparedStatement` |
| Build tool | Maven |
| Testing | JUnit 5 (`spring-boot-starter-test`) |

---

## 🚀 Getting Started

### Prerequisites

- **JDK 17** or newer
- **Maven 3.6+**
- Internet access on the first run (to download dependencies)

### Option A: Run with IntelliJ IDEA

1. Extract the project ZIP.
2. Open the `math-whiz-spring-boot` folder in IntelliJ IDEA.
3. Set the **Project SDK** to Java 17+ and let Maven load the dependencies.
4. Open `MathWhizApplication.java` and click **Run**.

### Option B: Run from the terminal

```bash
git clone <your-repo-url>
cd math-whiz-spring-boot
mvn spring-boot:run
```

Then open **http://localhost:8080** in your browser.

### Application routes

| Method | Route | Description |
|--------|-------|-------------|
| `GET`  | `/` | Welcome page |
| `POST` | `/start` | Validates the name and generates the questions |
| `POST` | `/submit` | Checks answers, saves the score, shows the result |
| `GET`  | `/leaderboard` | Top 10 Hall of Fame |
| `GET`  | `/h2-console` | H2 database console |

---

## 🗄️ Database

By default the app uses an **H2 file database**, created automatically at `./data/math_whiz_db`. Scores persist across restarts and no setup is needed.

**H2 console:** http://localhost:8080/h2-console

| Setting | Value |
|---------|-------|
| JDBC URL | `jdbc:h2:file:./data/math_whiz_db` |
| Username | `sa` |
| Password | *(blank)* |

The `game_scores` table is created automatically from [`schema.sql`](src/main/resources/schema.sql):

```sql
CREATE TABLE IF NOT EXISTS game_scores (
  id          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  player_name VARCHAR(100) NOT NULL,
  score       INT NOT NULL CHECK (score BETWEEN 0 AND 10),
  difficulty  VARCHAR(20) NOT NULL DEFAULT 'NORMAL',
  played_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

<details>
 

<br>

The same `game_scores` table can live in MySQL (as in the Workbench screenshot above).

1. Create the database and table:

```sql
CREATE DATABASE math_whiz_db;
USE math_whiz_db;

CREATE TABLE IF NOT EXISTS game_scores (
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  player_name VARCHAR(100) NOT NULL,
  score       INT NOT NULL CHECK (score BETWEEN 0 AND 10),
  difficulty  VARCHAR(20) NOT NULL DEFAULT 'NORMAL',
  played_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

2. Add the MySQL driver to `pom.xml`:

```xml
<dependency>
  <groupId>com.mysql</groupId>
  <artifactId>mysql-connector-j</artifactId>
  <scope>runtime</scope>
</dependency>
```

3. Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/math_whiz_db
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=your_password
spring.sql.init.mode=never
```

`schema.sql` uses H2 syntax, so set `spring.sql.init.mode=never` when using MySQL.

</details>

---

## 📁 Project Structure

```
math-whiz-spring-boot
├── docs/screenshots/                 # README images
├── src
│   ├── main
│   │   ├── java/com/mohibhasan/mathwhiz
│   │   │   ├── MathWhizApplication.java
│   │   │   ├── controller/GameController.java   # routes + game logic
│   │   │   ├── service/MathService.java         # math methods
│   │   │   ├── repository/ScoreRepository.java  # JDBC insert + top 10 query
│   │   │   └── model/                           # GameQuestion, GameScore
│   │   └── resources
│   │       ├── templates/                       # index, game, result, leaderboard
│   │       ├── static/css/style.css             # UI styling
│   │       ├── schema.sql                       # table definition
│   │       └── application.properties
│   └── test/java/.../MathServiceTest.java
└── pom.xml
```

---

## 🔢 Core Math Methods

All in `MathService`:

| Method | Description |
|--------|-------------|
| `isPrime(int n)` | Returns `1` if prime, otherwise `0` |
| `calculateMod(int a, int b)` | Returns `a % b`; throws if `b == 0` |
| `calculateSin(double degrees)` | Sine of an angle in degrees |
| `calculateCos(double degrees)` | Cosine of an angle in degrees |
| `calculateTan(double degrees)` | Tangent of an angle in degrees; throws where undefined |

---

## ✅ Testing

```bash
mvn test
```

`MathServiceTest` covers prime detection, modulus (including division by zero) and the trigonometry functions.

---

## 👨‍💻 Author

**Mohib Hasan**

If you like this project, give it a ⭐ on GitHub!
