// Name: Yaroslav Khalitov
// File: QueenSimulation.java
// Class: CSC 103
// Description: This program uses Queen.java & LinkedStack.java to calculate a # of possible
// solutions of Queen placements without them threatining each other.

import java.util.*;


class QueenSimulation{
   public static void main(String[] args){
      //set up objects
      Scanner keyboard = new Scanner(System.in);
      
      //set up variables
      int boardSize;
      int newCol = 1;
      int solutionCounter = 1;
      
      //loop variables
      int i;
      int j;
      int k;
      
      //inside loop control bool variables
      boolean restart = true;   //when program went through all possibilities on a certain column in row 1
      boolean conflict = false; //when there is a conflict between queens true
      boolean moveOn = false;   //when a queen was just added, null the rest of the loop
       
      
      
      //get size of the board
      System.out.println("**QUEEN SIMULATION PROGRAM INITIALZIED**");
      System.out.print("\nWhat value of N for an N by N board to solve: ");
      boardSize = keyboard.nextInt();
      
      //constructor
      LinkedStack<Queen> queenStack = new LinkedStack<Queen>();
     
     
     
      //row loop
      for (i=1; i <= boardSize; i++){
         //check if program is sent back to first row on the board & push the next queen
         if (restart == true && newCol <= boardSize){
               queenStack.push(new Queen(1, newCol));  
               i = 2;    
         }else if (newCol >=(boardSize + 1)){
            break;
         }
         
         //reset variables   
         restart = false;
         moveOn = false;
         
         //column loop
         for(j=1; j <= boardSize; j++){   
            //test for conflicts
            for(k=1; k <= queenStack.size(); k++){
               if (moveOn == false){
                  conflict = false;
                  
                  //create two Queen objects 
                  Queen stackTestQueen = new Queen(i, j);   //candidate for placement
                  Queen position = queenStack.itemAt(k);    //the k position on the stack test against candidate
                  
                  
                  //checks to see if there is a conflict (with latest one added)
                  if ((position.conflict(stackTestQueen)) == true){ 
                     k = queenStack.size(); 
                     conflict = true;
                  }
                  
                  
                  //checks to see if for loop went through all queens with no conflicts
                  if (conflict == false && k == queenStack.size()){
                     queenStack.push(new Queen(i, j));
                     moveOn = true;
                  }
                  
                  
                  //pop if no solution for that row
                  if (conflict == true && j == boardSize){
                     Queen popped = queenStack.pop();
                     
                     //keep popping if the new column was the last one available
                     while (popped.getColumn() == boardSize){
                        if (newCol == boardSize){
                           break;
                        }else{
                           popped = queenStack.pop();
                        }
                     }
                     
                     //if that column on the first row has no more available paths do a restart
                     if (queenStack.size() == 0){   
                        newCol++;
                        i = 1;
                        j = 1;
                        k = 1;
                        restart = true;
                        moveOn = true;
                     //if there are more paths, reasign values and keep going   
                     }else{   
                        i = popped.getRow();
                        j = popped.getColumn();
                        k = queenStack.size();
                        moveOn = false;  
                     } 
                  }
                  
                  
                  //checks to see if our queen stack size is the board size (n queens are placed)
                  if (queenStack.size() == boardSize){
                     System.out.println("Solution #" + solutionCounter + ":");
                     solutionCounter++;
                     
                     //print out the location of each queen
                     for (int q=boardSize; q>=1; q--){
                        Queen answer = queenStack.itemAt(q);
                        System.out.println(answer.toString());    
                     }
                     System.out.println();
                     
                     //keep popping if the new column was the last one available
                     Queen popped = queenStack.pop();
                     while (popped.getColumn() == boardSize){
                        if (newCol == boardSize){
                           break;
                        }else{
                           popped = queenStack.pop();
                        }
                     }
                     
                     //if that column on the first row has no more available paths do a restart
                     if (queenStack.size() == 0){
                        newCol++;
                        i = 1;
                        j = 1;
                        k = 1;
                        restart = true;
                        moveOn = true;
                     //if there are more paths, reasign values and keep going   
                     }else{  
                        i = popped.getRow();
                        j = popped.getColumn();
                        k = queenStack.size();
                        moveOn = false;
                       
                     }       
                  }//solution statement close    
               }//moveOn statement close
            }//conflict loop close
         }//vert loop close 
      }//hor loop close
       
      //conclusion
      System.out.print("Total Number of Solutions: " + (solutionCounter - 1));
      System.out.print("\n**QUEEN SIMULATION PROGRAM ENDED**");   

   }//main method close
}//class close