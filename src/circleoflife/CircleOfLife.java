/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package circleoflife;

import java.math.BigInteger;
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
        long t=sc.nextLong();
        sc.nextLine();
        BigInteger circle = sc.nextBigInteger(2);
        
        
        circle=getGeneration(n,t,circle);
        printBinary(n,circle);
        System.out.println();
    }

    

    private static BigInteger getGeneration(int n, long t, BigInteger circle) {
        if (t==0) {
            return circle;
        } 
        int twoPower=calculateTwoPower(t);
        long shift;
        if (twoPower==0) {
            shift=t;
            t=0;
        } else {
            shift=1L<<twoPower;
            t-=shift;
        }
        
        BigInteger left=rotateLeft(n,circle,(int)(shift%(long)n));
        BigInteger right=rotateRight(n,circle,(int)(shift%(long)n));
        
        BigInteger newCircle=left.xor(right);
        
        return getGeneration(n,t,newCircle);
    }



    private static void printBinary(int n, BigInteger circle) {
        for (int i=n-1;i>=0;i--) {
            System.out.print(circle.testBit(i)?1:0);
        }
    }

    private static BigInteger rotateLeft(int n, BigInteger circle,int rotate) {
        BigInteger mask=BigInteger.ONE.shiftLeft(rotate).subtract(BigInteger.ONE);
        BigInteger right=circle.shiftRight(n-rotate).and(mask);
        mask=BigInteger.ONE.shiftLeft(n-rotate).subtract(BigInteger.ONE).shiftLeft(rotate);
        BigInteger left=circle.shiftLeft(rotate).and(mask);
        
        return left.or(right);
    }
    
    private static BigInteger rotateRight(int n, BigInteger circle,int rotate) {
        return rotateLeft(n,circle,n-rotate);
    }

    private static int calculateTwoPower(long t) {
        if (t<2) return 0;
        int count=0;
        long twoPower=1;
        while (twoPower<t) {
            twoPower<<=1;
            count++;
        }
        return count-1;
    }
}

