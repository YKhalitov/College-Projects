// Name: Yaroslav Khalitov
// File: GolferScoresTree.java
// Class: CSC 103
// Description: This program uses Golfer.java & TreeBag.java to access/store golfer data 
// in a .txt file & give options to retrieve it and modify it.

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

class GolferScoresTree{
   public static void main(String[] args){
      //set up objects
      Scanner keyboard = new Scanner(System.in);
      DecimalFormat df = new DecimalFormat("0.00");      
          
      //operation variables
      boolean running = true;
      int userChoice;
      String userInput = "";
      double userDouble;
      
      int beggining = 0;
      int counter = 0;
      
      //information variables
      String lineS;
      String tempString;
      String answer;
      
      String golferName = "";
      int golferGame;
      double golferScore;
      
      //constructor
      TreeBag<Golfer> golferTree = new TreeBag<Golfer>();
      Golfer testGolfer = new Golfer("testGolfer");
      Golfer resultReference;
      Golfer newGolfer;
         
      //introduction
      System.out.println("**Golfer Scores Tree Program Initialized**");
      
      //read the file and create tree  
      try (BufferedReader br = new BufferedReader(new FileReader("golferinfo.txt"))) {
         while ((lineS = br.readLine()) != null){
            beggining = 0;
            counter = 0;
            for (int i=0; i < lineS.length(); i++){
               if (lineS.charAt(i) == 32){
                  counter++;
                  switch(counter){
                     //fetch name
                     case 1:
                        tempString = lineS.substring(beggining, i);
                        beggining = i + 1;
                        golferName = tempString;
                        break;
                     //fetch games and score
                     case 2:
                        tempString = lineS.substring(beggining, i);
                        beggining = i + 1;
                        golferGame = Integer.parseInt(tempString); 
                        
                        tempString = lineS.substring(beggining, lineS.length());
                        beggining = i + 1;
                        golferScore = Double.parseDouble(tempString);
                        
                        newGolfer = new Golfer(golferName, golferGame, golferScore);
                        golferTree.add(newGolfer);
                        break;
                  }//switch close
               }//if close 
            }//for loop close
         }//while loop close   
      }catch(FileNotFoundException e){
         System.out.println("Can't Find File");
      }catch (IOException e){
         System.out.println("Error While Reading File");
      }
    
      //main run loop
      while (running == true){
         userChoice = getChoice();
         switch (userChoice){
            //-----DISPLAY ALL STATS-----
            case 1:
               //golferTree.display();
               BTNode myTreesRoot = golferTree.getRoot();
               golferTree.traverseAndDisplay(myTreesRoot);                
               break;
               
               
            //-----DISPLAY ALL STATS TREE FORMAT-----
            case 2:
               golferTree.displayAsTree(); 
               break;
               
                       
            //-----DISPLAY INDIVIDUAL STATS-----
            case 3:
               //get golfers name
               System.out.println("Who's Statistics Would You Like to See?");
               System.out.print(">>> ");
               userInput = keyboard.nextLine();
               
               //look for golfer
               testGolfer.setName(userInput);
               resultReference = golferTree.retrieve(testGolfer);
               if (resultReference == null){
                  System.out.println();
                  System.out.println("****Cannot Find Golfer By The Name Of " + userInput + "****");
               }else{
                  System.out.println();
                  System.out.println("****Displaying " + userInput + "'s Statistics****");
                  answer = resultReference.formatData(resultReference.toString()); 
                  System.out.println(answer);
               }  
               break;
               
               
            //-----UPDATE INDIVIDUAL STATS (ADD SCORE)-----   
            case 4:
               //get golfers name
               System.out.println("Who's Statistics Would You Like to Update?");
               System.out.print(">>> ");
               userInput = keyboard.nextLine();
               
               //look for golfer
               testGolfer.setName(userInput);
               resultReference = golferTree.retrieve(testGolfer);
               if (resultReference == null){
                  System.out.println();
                  System.out.println("****Cannot Find Golfer By The Name Of " + userInput + "****");
               }else{
                  System.out.println("Add Individual Score");
                  System.out.print(">>> ");
                  userDouble = keyboard.nextDouble();
                  
                  //clear buffer
                  keyboard.nextLine();                      
                  
                  //display old & new stats with update     
                  System.out.println();
                  System.out.println("****Displaying " + userInput + "'s Old Statistics****");
                  answer = resultReference.formatData(resultReference.toString()); 
                  System.out.println(answer);
           
                  resultReference.addNewScore(userDouble);
                  
                  System.out.println();
                  System.out.println("****Displaying " + userInput + "'s New Statistics****");
                  answer = resultReference.formatData(resultReference.toString()); 
                  System.out.println(answer);
               } 
               break;
               
               
            //-----ADD NEW GOLFER-----   
           case 5:
               //get new golfers name
               System.out.println("Name of Golfer to Add");
               System.out.print(">>> ");
               golferName = keyboard.nextLine();
               
               //get new golfers rounds played
               System.out.println("Rounds Played");
               System.out.print(">>> ");
               golferGame = keyboard.nextInt();
               
               //get new golfers average score
               System.out.println("Average Score");
               System.out.print(">>> ");
               golferScore = keyboard.nextDouble();
               
               //clear buffer
               keyboard.nextLine();
               
               //add the new golfer to tree
               newGolfer = new Golfer(golferName, golferGame, golferScore);
               golferTree.add(newGolfer);     
               break;
            
            
            //-----REMOVE GOLFER-----   
           case 6:
               //get golfers name
               System.out.println("Name of Golfer to Remove");
               System.out.print(">>> ");
               golferName = keyboard.nextLine();
               
               //look for golfer
               testGolfer = new Golfer(golferName);
               resultReference = golferTree.retrieve(testGolfer);
               if (resultReference == null){
                  System.out.println();
                  System.out.println("****Cannot Find Golfer By The Name Of " + golferName + "****");
               }else{
                  System.out.println();
                  System.out.println("****Removing  " + golferName + " From Data Base****");
                  golferTree.remove(testGolfer);
                  System.out.println("****Removed****");
               } 
               break;
               
                           
            //-----EXIT & UPDATE-----   
            case 7:
               //save the current tree to the filer
               golferTree.saveToFile();
               
               //end the program
               System.out.println("\n*Updating File...*");
               System.out.println("*Exiting...*");
               running = false;
               break;
       
         }//switch case close
      }//main while loop close
   }//main method close
   
   //get good choice
   public static int getChoice(){
      //set up objects & variables
      Scanner keyboard = new Scanner(System.in);
      boolean choiceLoop = true;
      int userChoice = 0;
      
      //get choice loop
      while (choiceLoop == true){
         System.out.print("\nSelect choice by typing in associated integer");
         System.out.println("\n(1) Display listing to screen of all golfers stats(ordered by lastname)" + "\n(2) Display the golfers in current tree format" + "\n(3) Find and display one individual golfers stats" + "\n(4) Update an individual golfers stats" + "\n(5) Add a new golfer to the Database" + "\n(6) Remove a golfer from the Database" + "\n(7) Exit & update datafile");
         System.out.print(">>> "); 
         userChoice = keyboard.nextInt();
         
         if (userChoice < 1 || userChoice > 7){
            System.out.println("\nNot a menu option.");
         }else{
            choiceLoop = false;
         }   
      }//choice loop close
      return userChoice;     
   }
}