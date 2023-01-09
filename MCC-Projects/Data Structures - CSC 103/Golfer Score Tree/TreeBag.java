// Name: Yaroslav Khalitov
// File: TreeBag.java
// Class: CSC 103
// Description: This class stores creates and operates on a BinarySearchTree.

// The implementation of most methods in this file is left as a student
// exercise from Section 9.5 of "Data Structures and Other Objects Using Java"

import java.io.*;
import java.text.DecimalFormat;
/******************************************************************************
* This class is a homework assignment;
* An <CODE>TreeBag</CODE> is a collection of int numbers.
*
* <dl><dt><b>Limitations:</b> <dd>
*   Beyond <CODE>Integer.MAX_VALUE</CODE> elements, <CODE>countOccurrences</CODE>,
*   and <CODE>size</CODE> are wrong. 
*
* <dt><b>Note:</b><dd>
*   This file contains only blank implementations ("stubs")
*   because this is a Programming Project for my students.
*
* @version
*   Jan 24, 2016
******************************************************************************/
public class TreeBag<E extends Comparable> implements Cloneable
{
   // The Term E extends Comparable is letting the compiler know that any type
   // used to instantiate E must implement Comparable. i. e. that means that whatever
   // type E is must have a compareTo method so that elements can be compared against one another
   // This is required becuase we are doing comparisons in our methods


   // Invariant of the TreeBag class:
   //   1. The elements in the bag are stored in a binary search tree.
   //   2. The instance variable root is a reference to the root of the
   //      binary search tree (or null for an empty tree).
   private BTNode<E> root;   

   
   /**
   * Return the root of the current treeBag object
   * @param - none
   * @return
   *   the root of this tree
   **/ 
   public BTNode<E> getRoot( )
   {                         
      return root; 
   }
    
   
   /**
   * Insert a new element into this bag.
   * @param <CODE>element</CODE>
   *   the new element that is being inserted
   * <dt><b>Postcondition:</b><dd>
   *   A new copy of the element has been added to this bag.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory a new BTNode.
   **/
   public void add(E newGolfer)
   {      
      //variables
      boolean done = false;    
      BTNode cursor = root;
      
      //add first element
      if (root == null){
         root = new BTNode(newGolfer, null, null);
      }else{
         while (done != true){
            //add to left
            if (newGolfer.compareTo(cursor.getData()) < 0){
               if (cursor.getLeft() == null){
                  cursor.setLeft(new BTNode(newGolfer, null, null));
                  done = true;
               }else{
                  cursor = cursor.getLeft();
               }
            }
            //add to right
            if (newGolfer.compareTo(cursor.getData()) >= 0){
               if (cursor.getRight() == null){
                  cursor.setRight(new BTNode(newGolfer, null, null));
                  done = true;
               }else{
                  cursor = cursor.getRight();
               }
            } 
         }//while loop close
      }//if close
       
   }

   /**
   * Retrieve location of a specified element from this bag.
   * @param <CODE>target</CODE>
   *   the element to locate in the bag
   * @return 
   *  the return value is a reference to the found element in the tree
   * <dt><b>Postcondition:</b><dd>
   *   If <CODE>target</CODE> was found in the bag, then method returns
   *   a reference to a comparable element. If the target was not found then
   *   the method returns null.
   *   The bag remains unchanged.
   **/
   public E retrieve(E target)
   {
      //variables
      boolean done = false;
      BTNode<E> cursor = root;
      
      //main while loop
      while (done != true){
         //search left
         if (target.compareTo(cursor.getData()) < 0){
            if (cursor.getLeft() == null){
               return null;
            }else{
               cursor = cursor.getLeft();
            }
         }
         //search right
         if (target.compareTo(cursor.getData()) > 0){
            if (cursor.getRight() == null){
               return null;
            }else{
               cursor = cursor.getRight();
            }
         }
         //found
         if (target.compareTo(cursor.getData()) == 0){
            return cursor.getData();
         } 
      }//while loop close
      return null;
   }

   
   /**
   * Remove one copy of a specified element from this bag.
   * @param <CODE>target</CODE>
   *   the element to remove from the bag
   * <dt><b>Postcondition:</b><dd>
   *   If <CODE>target</CODE> was found in the bag, then one copy of
   *   <CODE>target</CODE> has been removed and the method returns true. 
   *   Otherwise the bag remains unchanged and the method returns false. 
   **/
   public boolean remove(E target)
   {
      //variables
      BTNode cursor = root;
      BTNode parentOfCursor = null;
      boolean done = false;

      
      //find if in tree
      target = this.retrieve(target);
      if (target == null){
         return false;
      }
           
      //Case 1: Node is leaf
      //Case 2: Node is root without left or without right
      //Case 3: Node is without left or without right
      //Case 4: Node has left and right node
      
      //CASE 2
      if (target.compareTo(cursor.getData()) == 0 && cursor.getLeft() == null){
         root = root.getRight();
         return true;      
      }
      if (target.compareTo(cursor.getData()) == 0 && cursor.getRight() == null){
         root = root.getLeft();
         return true;      
      }
      
      //CASE 4
      if (target.compareTo(cursor.getData()) == 0){
         cursor.setData(cursor.getLeft().getRightmostData());
         cursor.setLeft(cursor.getLeft().removeRightmost());
         return true;      
      }
      
      
      //main while loop
      while (done != true){
      
         //search left
         if (target.compareTo(cursor.getData()) <= 0){
            parentOfCursor = cursor;
            cursor = cursor.getLeft();
            //found
            if (target.compareTo(cursor.getData()) == 0){
               //CASE 1
               if (cursor.isLeaf()){
                  parentOfCursor.setLeft(null);
                  return true;    
               }
               
               //CASE 3
               if (cursor.getLeft() == null){
                  parentOfCursor.setLeft(cursor.getRight());
                  return true;      
               }
               if (cursor.getRight() == null){
                  parentOfCursor.setLeft(cursor.getLeft());
                  return true;      
               }
               //CASE 4
               if (cursor.getLeft() != null && cursor.getRight() != null){
                  cursor.setData(cursor.getLeft().getRightmostData());
                  cursor.setLeft(cursor.getLeft().removeRightmost());
                  return true;      
               }           
            }    
         }//search left close
         
         //search right
         if (target.compareTo(cursor.getData()) >= 0){
            parentOfCursor = cursor;
            cursor = cursor.getRight();
            //found
            if (target.compareTo(cursor.getData()) == 0){
               //CASE 1
               if (cursor.isLeaf()){
                  parentOfCursor.setRight(null);
                  return true;    
               }
               //CASE 3
               if (cursor.getLeft() == null){
                  parentOfCursor.setRight(cursor.getRight());
                  return true;      
               }
               if (cursor.getRight() == null){
                  parentOfCursor.setRight(cursor.getLeft());
                  return true;      
               }
               //CASE 4
               if (cursor.getLeft() != null && cursor.getRight() != null){
                  cursor.setData(cursor.getLeft().getRightmostData());
                  cursor.setLeft(cursor.getLeft().removeRightmost());
                  return true;      
               }
    
            }      
         }//search right close
      }//while loop close
      return false;
      
      
   }
   
   /**
   * Opens write to file and calls to traverse and write to that file.
   * <dt><b>Postcondition:</b><dd>
   * The treebag is traversed and its contents are written
   * out to the file "golferinfo.txt" in the following format
   * lastname numberOfRounds averageScore
   **/
   public void saveToFile(){

      //open writing file
      try (BufferedWriter bw = new BufferedWriter(new FileWriter("golferinfo.txt"))){  
      
         //call recursive method
         BTNode<E> cursor = root;   
         traverseAndWrite(cursor, bw);
         
         //close the file
         bw.close(); 
           
      }catch (IOException e){
         System.out.println("Error While Opening Writing File");
      }
   }
   
   /**
   * Traverse through bag and write to file.
   * <dt><b>Postcondition:</b><dd>
   * The treebag is traversed and its contents are written
   * out to the file "golferinfo.txt" in the following format
   * lastname numberOfRounds averageScore
   **/
   public void traverseAndWrite(BTNode<E> cursor, BufferedWriter bw){
         
         //write to file
         try{
            bw.write(cursor.getData().toString() + "\n");
         }catch (IOException e){
            System.out.println("Error While Writing To File");
         }
         
         //traverse            
         if (cursor.getLeft() != null)       
            traverseAndWrite(cursor.getLeft(), bw);

         
         if (cursor.getRight() != null)
            traverseAndWrite(cursor.getRight(), bw);     
   }
   
   
   /**
   * Traverse through bag and print to console
   * <dt><b>Postcondition:</b><dd>
   * The treebag is traversed and its contents are printed
   * out to the console in the following format
   * lastname numberOfRounds averageScore
   **/
   public void traverseAndDisplay(BTNode<E> cursor){
                         
                     
         if (cursor.getLeft() != null)       
            traverseAndDisplay(cursor.getLeft());

         //print data formatted
         String golferInfo = cursor.getData().toString();
         String answer = formatData(golferInfo);
         System.out.println(answer);
                 
         if (cursor.getRight() != null)
            traverseAndDisplay(cursor.getRight());     
   }
   
   
   
   /**
   * Formats and returns golfers data formatted
   * @param - golferInfo
   *   the data of the golfer
   * @precondition
   *   the string sent in is in "lastname numberOfRounds averageScore" format
   * @postcondition
   *   The string sent in is formatted and returned with markers.
   * @return - golferInfo
   *   the formatted string with "Name: Rounds: and Average Score:" markers.
   **/
   private String formatData(String golferInfo)
   {
      //objects
      DecimalFormat df = new DecimalFormat("0.00");
               
      //variables
      String tempString = "";
      String golferName = "";
      int golferGame;
      double golferScore;
      int beggining = 0;
      int counter = 0;
      
      for (int i=0; i < golferInfo.length(); i++){
         if (golferInfo.charAt(i) == 32){
            counter++;
            switch(counter){
               //fetch name
               case 1:
                  tempString = golferInfo.substring(beggining, i);
                  beggining = i + 1;
                  golferName = tempString;
                  break;
               //fetch games and score
               case 2:
                  tempString = golferInfo.substring(beggining, i);
                  beggining = i + 1;
                  golferGame = Integer.parseInt(tempString); 
                  
                  tempString = golferInfo.substring(beggining, golferInfo.length());
                  beggining = i + 1;
                  golferScore = Double.parseDouble(tempString);
                  
                  
                  String lastnamePrint = "Name: " + golferName;
                  String numberOfRoundsPrint = "Rounds: " + golferGame;
                  String averageScorePrint = "Average Score: " + df.format(golferScore); 
                  String answer = String.format("%-20s%-15s%-20s" , lastnamePrint, numberOfRoundsPrint, averageScorePrint);
                  return answer;
            }//switch close
         }//if close 
      }//for loop close
      return "";   
   }
   
   
   /**
   * Displays the entire tree of Node elements in a order specified
   * by the elements compareTo method
   * 
   * @param 
   *   none
   * <dt><b>Postcondition:</b><dd>
   *   Outputs all elements in the tree to Screen.
   *   Does not change the structure 
   **/
   public void display()
   {
      root.inorderPrint();
   } 
     
   /**
   * Displays the entire tree of Node elements using the
   * built in print method of BTNode
   * which displays the entire tree in tree format
   * 
   * @param 
   *   none
   * <dt><b>Postcondition:</b><dd>
   *   Outputs all elements in the tree to Screen.
   *   Does not change the structure 
   **/   
   public void displayAsTree() {
      if (root == null)
         throw new IllegalArgumentException("The tree is empty");
      root.print(0);
   }
      
   
   
   /**
   * Generate a copy of this bag.
   * @param - none
   * @return
   *   The return value is a copy of this bag. Subsequent changes to the
   *   copy will not affect the original, nor vice versa. Note that the return
   *   value must be type cast to an <CODE>TreeBag</CODE> before it can be used.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for creating the clone.
   **/ 
   public TreeBag<E> clone( )
   {  // Clone an IntTreeBag object.
      // Student will replace this return statement with their own code:
      return null; 
   } 

   /**
   * Accessor method to count the number of occurrences of a particular element
   * in this bag.
   * @param <CODE>target</CODE>
   *   the element that needs to be counted
   * @return
   *   the number of times that <CODE>target</CODE> occurs in this bag
   **/
   public int countOccurrences(E target)
   {
      // Student will replace this return statement with their own code:
      return 0;
   }
   
       
   /**
   * Determine the number of elements in this bag.
   * @param - none
   * @return
   *   the number of elements in this bag
   **/                           
   public int size( )
   {
      return BTNode.treeSize(root);
   }



   /**
   * Add the contents of another bag to this bag.
   * @param <CODE>addend</CODE>
   *   a bag whose contents will be added to this bag
   * <dt><b>Precondition:</b><dd>
   *   The parameter, <CODE>addend</CODE>, is not null.
   * <dt><b>Postcondition:</b><dd>
   *   The elements from <CODE>addend</CODE> have been added to this bag.
   * @exception IllegalArgumentException
   *   Indicates that <CODE>addend</CODE> is null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory to increase the size of the bag.
   **/
   public void addAll(TreeBag<E> addend)
   {
      // Implemented by student.
   }
   
   /**
   * Create a new bag that contains all the elements from two other bags.
   * @param <CODE>b1</CODE>
   *   the first of two bags
   * @param <CODE>b2</CODE>
   *   the second of two bags
   * <dt><b>Precondition:</b><dd>
   *   Neither b1 nor b2 is null.
   * @return
   *   the union of b1 and b2
   * @exception IllegalArgumentException
   *   Indicates that one of the arguments is null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for the new bag.
   **/   
   public static TreeBag union(TreeBag b1, TreeBag b2)
   {
      // Student will replace this return statement with their own code:
      return null;
   }
      
}