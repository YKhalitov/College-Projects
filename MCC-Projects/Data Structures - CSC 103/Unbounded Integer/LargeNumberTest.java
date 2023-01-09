// Name: Yaroslav Khalitov
// Class: CSC 103
// This program uses UnboundedInt.java and tests its methods

import java.util.*;
import java.text.DecimalFormat;

class LargeNumberTest{
   public static void main(String[] args){
      //set up objects
      Scanner keyboard = new Scanner(System.in);
      DecimalFormat df = new DecimalFormat("0.00");
      
      //set up variables
      boolean running = true;
      int userChoice;
      int userInputInt;
      String [] numbers = new String[2];
      String number1;
      String number2;
      
           
      //introduction
      System.out.println("**Unbounded Integer Test Program Initialized**");
      
      //get two numbers
      numbers = getTwo(numbers);
      number1 = numbers[0];
      number2 = numbers[1];
        
      //constructor
      UnboundedInt unbounded1Num = new UnboundedInt(number1);
      UnboundedInt unbounded2Num = new UnboundedInt(number2);
      
  
      //main run loop
      while (running == true){
         userChoice = getChoice();
         switch (userChoice){
            //-----DISPLAY BOTH NUMBERS W/ COMMAS-----
            case 1:
               try{
                  System.out.println("First Number: " + unbounded1Num.toString());
                  System.out.println("Second Number: " + unbounded2Num.toString());
               }
               catch (IllegalStateException e){
                  System.out.println("**ERROR: One of the Unbounded Integers are empty.**");
                  continue;
               } 
                  
               break;
               
               
            //-----INPUT TWO NEW NUMBERS W/O COMMAS-----
            case 2:
               System.out.println();
               numbers = getTwo(numbers);
               number1 = numbers[0];
               number2 = numbers[1];
               
               UnboundedInt unboundedTemp1Num = new UnboundedInt(number1);
               unbounded1Num = unboundedTemp1Num.clone();
               
               UnboundedInt unboundedTemp2Num = new UnboundedInt(number2);
               unbounded2Num = unboundedTemp2Num.clone(); 
               break;
               
                       
            //-----CHECK IF NUMBERS ARE EQUAL-----
            case 3:
               try{
                  if (unbounded1Num.equals(unbounded2Num)){
                     System.out.println("**The two numbers ARE equal**");
                  }else{
                     System.out.println("**The two numbers ARE NOT equal**");
                  }  
               }
               catch(IllegalStateException e){
                  System.out.println("**ERROR: Object is empty**");
                  continue;   
               }
               break;
               
               
            //-----REPORT ADDITION SUM-----   
            case 4:
               UnboundedInt unboundedSum = unbounded1Num.add(unbounded2Num);
               System.out.println("Sum of the two numbers is: " + unboundedSum.toString()); 
               break;
               
               
            //-----REPORT MULTIPLICATION SUM-----   
           case 5:
               UnboundedInt unboundedProduct = unbounded1Num.multiply(unbounded2Num);
               System.out.println("Product of the two numbers is: " + unboundedProduct.toString()); 
               break;
            
            
            //-----CREATE & OUTPUT CLONE OF FIRST NUMBER-----   
           case 6:
               UnboundedInt unbounded1NumClone = unbounded1Num.clone();
               System.out.println("Clone of the first number is: " + unbounded1NumClone.toString());
               break;
               
                           
            //-----EXIT-----   
            case 7:
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
         System.out.println("\n(1) Display Both Numbers(w/commas)" + "\n(2) Input two new numbers(w/o commas)" + "\n(3) Check if numbers are equal" + "\n(4) Report Addition Sum" + "\n(5) Report Multiplication Product" + "\n(6) Create/Output Clone of First Number" + "\n(7) Exit");
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

   public static String[] getTwo(String[] numbers){
      //set up object
      Scanner keyboard = new Scanner(System.in);
      
      //initialize variables
      boolean choiceLoop = true;
      
      while (choiceLoop == true){
      
         //get numbers
         System.out.print("Input first number: ");
         numbers[0] = keyboard.nextLine();
         System.out.print("Input second number: ");
         numbers[1] = keyboard.nextLine();
                 
         //check for valid numbers
         if (numbers[0].equals("") || numbers[1].equals("")){
            System.out.println("\n**ERROR: Please re-enter an integer thats positive**");
         }else{
            choiceLoop = false;
         } 
      }//while loop close
          
      return numbers; 
   
   }
   
   
   
   
   
}//class close