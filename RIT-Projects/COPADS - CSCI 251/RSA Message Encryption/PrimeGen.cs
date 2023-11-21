using System.Security.Cryptography;
using System.Numerics;
using PrimeExtension;
//Name: Yaroslav Khalitov
//Class: CSCI.251 COPADS
//Program: This program will generate prime number(s) based of a user given bit input.
//         This program utilizes parallel loops to create threads and accelerate the speed of prime number generation.
//         It also uses a hand-crafted extension of the BigInteger library that will allow the checking if the number generated is likely
//         to be prime using the miller-rabin primality test. 


public class PrimeGen
{
    public static BigInteger generatePrimeNumber(int bits){

        //used as a pre-miller-rabin primality test to weed out some numbers
        int[] prePrimeCheck = new int[1000];
        for (int i = 0; i < 1000; i++){
            prePrimeCheck[i] = i + 3;
        }

        //start the process
        BigInteger primeNumber = sendOff(bits, prePrimeCheck);

        return primeNumber;    
    }

    public static BigInteger sendOff(int bits, int[] prePrimeCheck){
        BigInteger answer = new BigInteger();
        bool result = false;

        //used to create at most 10 threads to speed up process of generating a prime number
        int[] create10Threads = new int[10];
        for (int i = 0; i < 10; i++){
            create10Threads[i] = i + 1;
        }

        //start process
        Parallel.ForEach(create10Threads, iterator => {
            RandomNumberGenerator random = RandomNumberGenerator.Create();
            BigInteger bi = new BigInteger();
            var number = new byte[bits/8];

            while(!result){
                //generate random number
                random.GetBytes(number);
                number[number.Length - 1] &= (byte)0x7F;
                bi = new BigInteger(number);
                //ensure number is even
                if (!bi.IsEven){
                    //perform pre-miller-rabin primality test to weed out numbers
                    Boolean prePrimeCheckResult = false;
                    for (int i = 0; i < 1000; i++){
                        if (bi % prePrimeCheck[i] == 0){
                            prePrimeCheckResult = true;
                            break;   
                        }
                    }
                    if (!prePrimeCheckResult){
                        if (bi.isProbablyPrime()){ //record result if miller-rabin test confirms it
                            result = true;
                            answer = bi;
                        }
                    }      
                }   
            } 
        });
        return answer;
    }
}  