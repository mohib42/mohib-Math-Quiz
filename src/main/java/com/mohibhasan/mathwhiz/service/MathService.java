package com.mohibhasan.mathwhiz.service;
import org.springframework.stereotype.Service;
@Service
public class MathService {
 public static int isPrime(int n) {
  if (n < 2) return 0;
  if (n == 2) return 1;
  if (n % 2 == 0) return 0;
  for (int i=3; i <= n/i; i+=2) if (n%i==0) return 0;
  return 1;
 }
 public static int calculateMod(int a,int b) {
  if (b==0) throw new IllegalArgumentException("The divisor cannot be zero.");
  return a%b;
 }
 public static double calculateSin(double d) { return Math.sin(Math.toRadians(d)); }
 public static double calculateCos(double d) { return Math.cos(Math.toRadians(d)); }
 public static double calculateTan(double d) {
  double n=((d%180)+180)%180;
  if (Math.abs(n-90.0)<1.0e-10) throw new IllegalArgumentException("Tangent is undefined at this angle.");
  return Math.tan(Math.toRadians(d));
 }
}