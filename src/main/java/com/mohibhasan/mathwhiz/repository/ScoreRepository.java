package com.mohibhasan.mathwhiz.repository;
import com.mohibhasan.mathwhiz.model.GameScore;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class ScoreRepository {
 private final JdbcTemplate jdbc;
 public ScoreRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}
 public void save(String name,int score,String difficulty){
  jdbc.update("INSERT INTO game_scores(player_name,score,difficulty,played_at) VALUES(?,?,?,CURRENT_TIMESTAMP)",name,score,difficulty);
 }
 public List<GameScore> findTop10(){
  return jdbc.query("SELECT id,player_name,score,difficulty,played_at FROM game_scores ORDER BY score DESC, played_at ASC LIMIT 10",
   (rs,n)->new GameScore(rs.getLong("id"),rs.getString("player_name"),rs.getInt("score"),rs.getString("difficulty"),rs.getTimestamp("played_at").toLocalDateTime()));
 }
}