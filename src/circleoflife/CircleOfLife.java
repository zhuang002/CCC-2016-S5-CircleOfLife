/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package circleoflife;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 *
 * @author huang
 */
public class CircleOfLife {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int t=sc.nextInt();
        sc.nextLine();
        BigInteger circle = sc.nextBigInteger(2);
        
        
        circle=getGeneration(n,t,circle);
        printBinary(n,circle);
        System.out.println();
    }

    

    private static BigInteger getGeneration(int n, int t, BigInteger circle) {

        HashMap<BigInteger,Integer> map=new HashMap();
        HashMap<Integer,BigInteger> reverseMap=new HashMap();

        BigInteger next=circle;
        for (int i=1;i<=t;i++) {
            next=getNextGeneration(n,next);
            if (map.containsKey(next)) {
                int cycleStart=map.get(next);
                int cycle=i-cycleStart;
                int left=(t+1-cycleStart)%cycle;
                return reverseMap.get((left+cycle-1)%cycle);
            } else {
                map.put(next, i);
                reverseMap.put(i, next);
            }
        }
        return next;
    }

    private static BigInteger getNextGeneration(int n, BigInteger circle) {
        BigInteger bigInt1=rotateLeft(n,circle);
        BigInteger bigInt2=rotateRight(n,circle);
        return bigInt1.xor(bigInt2);
    }


    private static void printBinary(int n, BigInteger circle) {
        for (int i=n-1;i>=0;i--) {
            System.out.print(circle.testBit(i)?1:0);
        }
    }

    private static BigInteger rotateLeft(int n, BigInteger circle) {
        BigInteger mask=BigInteger.ONE.shiftLeft(n).subtract(BigInteger.ONE);
        BigInteger topBit=circle.testBit(n-1)?BigInteger.ONE:BigInteger.ZERO;
        return circle.shiftLeft(1).add(topBit).and(mask);
    }
    
    private static BigInteger rotateRight(int n, BigInteger circle) {
        BigInteger mask=BigInteger.ONE.shiftLeft(n).subtract(BigInteger.ONE);
        BigInteger bottomBit=circle.testBit(0)?BigInteger.ONE:BigInteger.ZERO;
        return circle.shiftRight(1).add(bottomBit.shiftLeft(n-1)).and(mask);
    }
}

