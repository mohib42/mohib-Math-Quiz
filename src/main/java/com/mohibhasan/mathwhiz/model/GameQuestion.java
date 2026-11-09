package com.mohibhasan.mathwhiz.model;
public class GameQuestion {
 private final String prompt,type; private final double expectedAnswer; private final int marks;
 public GameQuestion(String p,String t,double a,int m){prompt=p;type=t;expectedAnswer=a;marks=m;}
 public String getPrompt(){return prompt;} public String getType(){return type;}
 public double getExpectedAnswer(){return expectedAnswer;} public int getMarks(){return marks;}
}