// Yaroslav Khalitov
// This program uses DoubleArraySequence.java and tests its methods

import java.util.*;
import java.text.DecimalFormat;

class SequenceTest{
   public static void main(String[] args){
      //set up objects
      Scanner keyboard = new Scanner(System.in);
      DecimalFormat df = new DecimalFormat("0.00");
      
      //set up variables
      boolean running = true;
      int userChoice;
      double userInputDouble;
      int userInputInt;
      
      //constructor
      DoubleArraySeq sequence = new DoubleArraySeq();
      
      //introduction
      System.out.println("**Sequence Test Program Initialized**");
   
      //main run loop
      while (running == true){
         userChoice = getChoice();
         switch (userChoice){
            //-----PRINT OUT TO SCREEN-----
            case 1: 
               System.out.println("The current Sequence Is:");
               System.out.println(sequence.toString());   
               break;
               
               
            //-----REPORT CAPACITY-----
            case 2:
               System.out.println("The current capacity is: " + sequence.getCapacity());
               break;
               
                       
            //-----SET ELEMENT LOCATION-----
            case 3:
               try{
                  int userSetCurrent;
                  System.out.print("What element would you like to make current?: ");
                  userSetCurrent = keyboard.nextInt();
                  sequence.setCurrent(userSetCurrent);
               }
               catch(IllegalStateException | IllegalArgumentException e){
                  System.out.println("Sequence is empty or not a valid location");
                  continue;   
               }

               break;
               
               
            //-----ADD NUMBER TO FRONT-----   
            case 4:
               System.out.print("What element would you like to add to the front?: ");
               userInputDouble = keyboard.nextDouble();
               sequence.addFront(userInputDouble);
               break;
               
               
            //-----ADD NUMBER TO BACK-----   
           case 5:
               System.out.print("What element would you like to add to the back?: ");
               userInputDouble = keyboard.nextDouble();
               sequence.setCurrent(sequence.size());
               sequence.addAfter(userInputDouble);
               break;
            
            
            //-----ADD NUMBER BEFORE CURRENT ELEMENT-----   
           case 6:
               System.out.print("What would you like to add?: ");
               userInputDouble = keyboard.nextDouble();
               sequence.addBefore(userInputDouble);
               break;
               
                           
            //-----ADD NUMBER AFTER CURRENT ELEMENT-----   
            case 7:
               double userAddAfter;
               System.out.print("What would you like to add?: ");
               userAddAfter = keyboard.nextDouble();
               sequence.addAfter(userAddAfter);
               break;


            //-----DELETE FIRST NUMBER IN SEQUENCE-----   
            case 8:
               System.out.print("First Number Deleted");
               sequence.removeFront();
               break;


            //-----DELETE NUMBER AT LOCATION-----   
            case 9:
               try{
                  System.out.print("At which location do you want to delete the element for?: ");
                  userInputInt = keyboard.nextInt();
                  sequence.setCurrent(userInputInt);
                  sequence.removeCurrent();
               }
               catch(IllegalStateException | IllegalArgumentException e){
                  System.out.println("Sequence is empty or not a valid location");
                  continue;   
               }
               break;


            //-----DISPLAY VALUE AT LOCATION-----   
            case 10:
               try{
                  int userGetElement;
                  System.out.print("At which location do you want to display the element for?: ");
                  userGetElement = keyboard.nextInt();
                  System.out.println("The element is: " + sequence.getElement(userGetElement));
               }
               catch(IllegalStateException | IllegalArgumentException e){
                  System.out.println("Sequence is empty or not a valid location");
                  continue;   
               }
               break;


            //-----DISPLAY LAST ELEMENT IN SEQUENCE-----   
            case 11:
               System.out.print("Displaying Last Element in Sequence ");
               sequence.setCurrent(sequence.size());
               System.out.println(sequence.getCurrent()); 
               break;

   
            //-----EXIT-----     
            case 12:
               System.out.println("\n*Exiting...*");
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
         System.out.println("\n(1) Print Out Sequence" + "\n(2) Report Capacity" + "\n(3) Set Current Element Location" + "\n(4) Add Number In Front of Sequence" + "\n(5) Add Number At End of Sequence" + "\n(6) Add Number Before Current Element");
         System.out.println("(7) Add Number After Current Element" + "\n(8) Delete First Number From Sequence" + "\n(9) Delete Number At a Location" + "\n(10) Display Value At a Location" + "\n(11) Display Last Element in Sequence" + "\n(12) Exit");
         System.out.print(">>> "); 
         userChoice = keyboard.nextInt();
         
         if (userChoice < 1 || userChoice > 12){
            System.out.println("\nNot a menu option.");
         }else{
            choiceLoop = false;
         }   
      }//choice loop close
      return userChoice;     
   }
   
   
   
   
   
}//class close