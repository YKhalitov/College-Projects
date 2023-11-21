using System.Numerics;
using System.Security.Cryptography;
//Name: Yaroslav Khalitov
//Class: CSCI.251 COPADS
//Program: This is an extension to the BigInteger library that will check to see if the given
//         BigInteger value in the parameter is likely to me a prime number.


namespace PrimeExtension
{
    public static class BigIntegerExtension
     {
        public static Boolean isProbablyPrime(this BigInteger value, int k = 10)
        {   
            RandomNumberGenerator random = RandomNumberGenerator.Create();
            BigInteger toBeDivided = value * 3;
            byte[] ourBits = toBeDivided.ToByteArray(); 
            BigInteger testNumber;

            //figure out variables for miller test
            BigInteger d = value - 1;
            int r = 0;
            while (d.IsEven){
                d = d/2;
                r = r + 1;
            }

            //miller rabin test for primality
            for(int i = 0; i < k; i++){
                //generate random number between 2 & value-2
                do{
                    random.GetBytes(ourBits);
                    ourBits[ourBits.Length - 1] &= (byte)0x7F;   
                    testNumber = new BigInteger(ourBits);
                    testNumber = testNumber + 2;
                    testNumber = testNumber % (value - 2);
                }while (testNumber <= 2 || testNumber >= (value - 2));   

                BigInteger x = BigInteger.ModPow(testNumber, d ,value);
                if (x == 1 || x == value - 1){
                    continue;
                }
                for (int j = 0; j < r - 1; j++){
                    x = BigInteger.ModPow(x, 2, value);
                    if (x == value - 1){
                        continue;
                    }   
                }
                return false; 
            }
            return true;
        }
    }
}