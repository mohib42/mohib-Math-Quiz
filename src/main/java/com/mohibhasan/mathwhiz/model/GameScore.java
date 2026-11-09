package com.mohibhasan.mathwhiz.model;
import java.time.LocalDateTime;
public class GameScore {
 private final long id; private final String playerName,difficulty; private final int score; private final LocalDateTime playedAt;
 public GameScore(long id,String name,int score,String difficulty,LocalDateTime time){this.id=id;this.playerName=name;this.score=score;this.difficulty=difficulty;this.playedAt=time;}
 public long getId(){return id;} public String getPlayerName(){return playerName;} public int getScore(){return score;}
 public String getDifficulty(){return difficulty;} public LocalDateTime getPlayedAt(){return playedAt;}
}