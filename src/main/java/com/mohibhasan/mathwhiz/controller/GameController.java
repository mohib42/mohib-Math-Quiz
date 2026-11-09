package com.mohibhasan.mathwhiz.controller;
import com.mohibhasan.mathwhiz.model.*;
import com.mohibhasan.mathwhiz.repository.ScoreRepository;
import com.mohibhasan.mathwhiz.service.MathService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class GameController {
 private static final String QKEY="mathWhizQuestions", PKEY="mathWhizPlayer";
 private final ScoreRepository repository;
 public GameController(ScoreRepository repository){this.repository=repository;}
 @GetMapping("/") public String home(){return "index";}
 @PostMapping("/start") public String start(@RequestParam String playerName,HttpSession session,Model model){
  String name=playerName==null?"":playerName.trim();
  if(name.isBlank()||name.length()>100){model.addAttribute("error","Enter a name between 1 and 100 characters.");return "index";}
  List<GameQuestion> qs=makeQuestions(); session.setAttribute(PKEY,name);session.setAttribute(QKEY,qs);
  model.addAttribute("playerName",name);model.addAttribute("questions",qs);return "game";
 }
 @PostMapping("/submit") public String submit(@RequestParam String answer1,@RequestParam String answer2,@RequestParam String answer3,@RequestParam String answer4,HttpSession session,Model model){
  String name=(String)session.getAttribute(PKEY);
  @SuppressWarnings("unchecked") List<GameQuestion> qs=(List<GameQuestion>)session.getAttribute(QKEY);
  if(name==null||qs==null||qs.size()!=4){model.addAttribute("error","Game session expired. Please start again.");return "index";}
  String[] answers={answer1,answer2,answer3,answer4}; boolean[] correct=new boolean[4];int score=0;
  for(int i=0;i<4;i++){
   try{
    double value=Double.parseDouble(answers[i].trim()); if(!Double.isFinite(value))continue;
    GameQuestion q=qs.get(i);
    if("PRIME".equals(q.getType())) correct[i]=(value==0||value==1)&&Math.abs(value-q.getExpectedAnswer())<1e-9;
    else if("MOD".equals(q.getType())) correct[i]=value==Math.rint(value)&&Math.abs(value-q.getExpectedAnswer())<1e-9;
    else correct[i]=Math.abs(value-q.getExpectedAnswer())<=0.01;
   }catch(Exception ignored){correct[i]=false;}
   if(correct[i])score+=qs.get(i).getMarks();
  }
  repository.save(name,score,"NORMAL");model.addAttribute("playerName",name);model.addAttribute("score",score);
  model.addAttribute("correct",correct);model.addAttribute("questions",qs);
  session.removeAttribute(PKEY);session.removeAttribute(QKEY);return "result";
 }
 @GetMapping("/leaderboard") public String leaderboard(Model model){model.addAttribute("scores",repository.findTop10());return "leaderboard";}
 private List<GameQuestion> makeQuestions(){
  int n=ThreadLocalRandom.current().nextInt(10,51),a=ThreadLocalRandom.current().nextInt(20,100),b=ThreadLocalRandom.current().nextInt(2,12);
  double d1=new double[]{30,45,60,120,150}[ThreadLocalRandom.current().nextInt(5)];
  double d2=new double[]{0,30,45,60,180,270}[ThreadLocalRandom.current().nextInt(6)];
  String[] fs={"SIN","COS","TAN"};String f1=fs[ThreadLocalRandom.current().nextInt(3)],f2=fs[ThreadLocalRandom.current().nextInt(3)];
  return List.of(new GameQuestion("Is "+n+" a prime number? Enter 1 for Prime or 0 for Not Prime.","PRIME",MathService.isPrime(n),2),
   new GameQuestion("Calculate the remainder: "+a+" % "+b,"MOD",MathService.calculateMod(a,b),2),
   new GameQuestion("Calculate "+f1+"("+d1+"°). Give your answer up to 2 decimal places.","TRIG",trig(f1,d1),3),
   new GameQuestion("Calculate "+f2+"("+d2+"°). Give your answer up to 2 decimal places.","TRIG",trig(f2,d2),3));
 }
 private double trig(String f,double d){return switch(f){case "SIN"->MathService.calculateSin(d);case "COS"->MathService.calculateCos(d);case "TAN"->MathService.calculateTan(d);default->throw new IllegalArgumentException();};}
}