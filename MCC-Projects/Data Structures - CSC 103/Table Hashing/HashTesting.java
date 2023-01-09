// Name: Yaroslav Khalitov
// File: HashTesting.java
// Class: CSC 103
// Description: This program uses Table.java & TableDoubleHashing.java & TableChainHash.java
// & Node.java to store data from a txt file in a table using three different methods.
// For each method the number of collisions are counted.

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

class HashTesting{
   public static void main(String[] args){
      //set up objects
      Scanner keyboard = new Scanner(System.in);
      DecimalFormat df = new DecimalFormat("0.00");      
      
      //constants
      final int SAMPLE_TABLE_SIZE = 31;
      final int TABLE_SIZE = 241;
          
      //operation variables
      int attemptCounter = 0;
      int previousSingleCollisions = 0;
      int previousDoubleCollisions = 0;
      int previousChainCollisions = 0;
          
      
      //information variables
      String lineS;   
      String fileName;
      String tempFileNumber;
      int fileNumber;
      

          
      //introduction
      System.out.println("**Hash Testing Program Initialized**");
      System.out.println("Collisions Per Attempted Placement in Tables");
      System.out.println("Attempt   Linear   Double   Chain");
      
      //constructor
      Table mySingleTable = new Table(TABLE_SIZE);
      TableDoubleHash myDoubleTable = new TableDoubleHash(TABLE_SIZE);
      TableChainHash myChainTable = new TableChainHash(TABLE_SIZE);
      
      
      //read in data from txt file 
      try (BufferedReader br = new BufferedReader(new FileReader("names.txt"))) {
         while ((lineS = br.readLine()) != null){
            attemptCounter++;
            for (int i=0; i < lineS.length(); i++){
               if (lineS.charAt(i) == 32){
                  
                  //get data from txt file   
                  fileName = lineS.substring(0, i);
                  tempFileNumber = lineS.substring(i+1, i+10);
                  fileNumber = Integer.parseInt(tempFileNumber);
                  
                  //add to each table
                  mySingleTable.put(fileNumber, fileName);
                  myDoubleTable.put(fileNumber, fileName);
                  myChainTable.put(fileNumber, fileName);
               
                  //print attempt/collisions
                  String answer = String.format("%-1s%-9s%-9s%-9s%-1s" , "   ", attemptCounter, (mySingleTable.getCollisions() - previousSingleCollisions), (myDoubleTable.getCollisions() - previousDoubleCollisions), (myChainTable.getCollisions() - previousChainCollisions));
                  System.out.println(answer);
                  
                  //keep track of previous collisions
                  previousSingleCollisions = mySingleTable.getCollisions();
                  previousDoubleCollisions = myDoubleTable.getCollisions();
                  previousChainCollisions = myChainTable.getCollisions();
                  
               }//if close 
            }//for loop close
         }//while loop close   
      }catch(FileNotFoundException e){
         System.out.println("Can't Find File");
      }catch (IOException e){
         System.out.println("Error While Reading File");
      }
      
  
      //conclusion 
      System.out.println("Linear Average = " + df.format(mySingleTable.getCollisions() / 200.0));
      System.out.println("Double Average = " + df.format(myDoubleTable.getCollisions() / 200.0));
      System.out.println("Chain Average = " + df.format(myChainTable.getCollisions() / 200.0));
      System.out.println("**Hash Testing Program Terminated**");
      
  
      
   }//main method close
}//class close
   