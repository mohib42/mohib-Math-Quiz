# MOHIB HASAN – MATH WHIZ
Java 17 + Spring Boot + Thymeleaf + JDBC + H2 mathematical game.

## Features
- Player name, welcome, game, result and leaderboard screens.
- One prime question (2 marks), one modulus question (2 marks), two trigonometry questions (3 marks each).
- Random questions and server-side calculations using reusable Java methods.
- ±0.01 tolerance for trigonometry answers.
- Saves name, score, NORMAL difficulty and timestamp in H2 using JDBC PreparedStatement through JdbcTemplate.
- Top 10 leaderboard sorted by score descending and played time ascending.

## Requirements
Install JDK 17+ and Maven 3.6+. Internet is needed on the first Maven run to download dependencies.

## Run using IntelliJ IDEA
1. Extract this ZIP.
2. Open the extracted `math-whiz-spring-boot` folder in IntelliJ IDEA.
3. Set Project SDK to Java 17 or newer and allow Maven to load dependencies.
4. Open `MathWhizApplication.java` and click Run.

## Run using terminal
From the project folder:
```bash
mvn spring-boot:run
```
Open http://localhost:8080

## Database
H2 file database is automatically created in `./data/math_whiz_db`. Scores persist across restarts.
H2 console: http://localhost:8080/h2-console
JDBC URL: `jdbc:h2:file:./data/math_whiz_db`
Username: `sa` | Password: (blank)

The `game_scores` table is created automatically from `src/main/resources/schema.sql`.
To run tests: `mvn test`.

## Required methods
`MathService.isPrime(int)`, `calculateMod(int,int)`, `calculateSin(double)`, `calculateCos(double)`, `calculateTan(double)`.

## Structure
- controller/GameController.java — routes and game logic
- service/MathService.java — mathematical methods
- repository/ScoreRepository.java — JDBC insert and top 10 query
- model/ — question and score models
- resources/templates/ — Thymeleaf pages
- resources/static/css/style.css — UI styling
- resources/schema.sql — database table
