package com.mohibhasan.mathwhiz.service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class MathServiceTest {
 @Test void prime(){assertEquals(1,MathService.isPrime(7));assertEquals(0,MathService.isPrime(8));assertEquals(0,MathService.isPrime(1));}
 @Test void modulus(){assertEquals(2,MathService.calculateMod(17,5));assertThrows(IllegalArgumentException.class,()->MathService.calculateMod(4,0));}
 @Test void trig(){assertEquals(.5,MathService.calculateSin(30),1e-6);assertEquals(.5,MathService.calculateCos(60),1e-6);assertEquals(1,MathService.calculateTan(45),1e-6);assertThrows(IllegalArgumentException.class,()->MathService.calculateTan(90));}
}