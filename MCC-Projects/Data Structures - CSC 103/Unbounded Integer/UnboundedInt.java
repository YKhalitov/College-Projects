// File: UnboundedInt.java 
// Name: Yaroslav Khalitov
// Class: CSC 103


/******************************************************************************
* This class uses linked lists to be able to store virtually unlimited integer values.
* There is a maximum value an integer can be and this bypass that by using
* groups of three numbers and performs operations on them such as addition or multiplication.
*
*
*
******************************************************************************/
public class UnboundedInt implements Cloneable
{   
   private IntNode front;
   private IntNode back;
   private IntNode cursor;
   private int manyNodes;

   

   
   /**
   * Initialize a linked list with the highest palce value in the back and lowest in the front. 
   * Organized by groups of three digits. From 0 to 999.
   * @param - userStringInput
   *   the int to be converted in string format.
   * @precondition
   *  userStringInput is non-negative.
   * @postcondition
   *   A linked list is created with a users input.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory. 
   **/   
   public UnboundedInt(String userStringInput)
   {    
      //initialize variables
      int numLength = userStringInput.length();
      int begginingIndex;
      int endingIndex;
      int leftover;
      int groupsOf3;
      String tempString;
      
      //get number of values that are not grouped by 3
      leftover = numLength % 3;
      groupsOf3 = numLength / 3; 
      
      //if theres only less than three int
      if (numLength <= 3){
         begginingIndex = 0;
         endingIndex = numLength;   
         tempString = userStringInput.substring(begginingIndex,endingIndex); 
         front = new IntNode(Integer.valueOf(tempString), null);
         manyNodes++;
      }else{
         //initialize index's
         begginingIndex = numLength - 3;
         endingIndex = numLength;
         
         //run a loop for every group of three
         for (int i=0; i<=groupsOf3 - 1; i++){
         
            //set up front (ALWAYS RUNS)
            if (i==0){
               tempString = userStringInput.substring(begginingIndex,endingIndex); 
               front = new IntNode(Integer.valueOf(tempString), null);
               start();
               manyNodes++;
               
               
            //add subsequent nodes in groups of three   
            }else{
               begginingIndex -= 3;
               endingIndex -= 3;
               tempString = userStringInput.substring(begginingIndex,endingIndex);
               cursor.addNodeAfter(Integer.valueOf(tempString));
               advance();
               manyNodes++;   
            }
         }//for loop close
         
         //deal with leftover
         if (leftover > 0){
            begginingIndex = 0;
            endingIndex = leftover;
            tempString = userStringInput.substring(begginingIndex,endingIndex);
            cursor.addNodeAfter(Integer.valueOf(tempString));
            advance();
            manyNodes++;          
         }      
      }
      
   }
     

   /**
   * Add the current UnboundedInt with the passed in one. The
   * return becomes the new UnboundedInt
   * @param addend
   *   the passed in UnboundedInt
   * @postcondition
   *   The returned object is the sum of the current and passed in UnboundedInt
   * @exception OutOfMemoryError
   *   Indicates insufficient memory. 
   **/   
   public UnboundedInt add(UnboundedInt addend)
   {  
      //initialze variables
      final int TOP = 999;
      
      //operation variables
      int carryOver = 0;
      
      //node variables
      int thisNodeValue;
      int addendNodeValue;
      int blockSum = 0;
      
      //answer variables
      String stringAnswer = "";
      int newBlock = 0;
      
      //go to beggining in each list
      start();
      addend.start();
       
      //iterate as long as lists aren't empty
      while (cursor != null || addend.cursor != null){
      
         //get cursor data if its not null for both UnboundedInt's
         if (cursor != null){
            thisNodeValue = cursor.getData();
         }else{
            thisNodeValue = 0;
         }
         
         if (addend.cursor != null){
            addendNodeValue =  (addend.cursor).getData();
         }else{
            addendNodeValue = 0;
         }
         
         //determine the sum
         blockSum = thisNodeValue + addendNodeValue + carryOver;
         carryOver = 0;
         
         //determine if there is a carryover and adjust
         if (blockSum > TOP){
            carryOver++;
            newBlock = blockSum - 1000; 
         }else{
            newBlock = blockSum;
         }
         
         //add to string the new block of integers
         stringAnswer = String.valueOf(newBlock) + stringAnswer;
         
         //if there is a 2/1 place value 3 digit number block, add 0's where needed
         if (String.valueOf(newBlock).length() == 1){
            stringAnswer = "00" + stringAnswer;
         }else if (String.valueOf(newBlock).length() == 2){
            stringAnswer = "0" + stringAnswer;
         }
         
         //advance in each list if not null
         if (cursor != null){
            advance();   
         }
         
         if (addend.cursor != null){
            addend.advance();
         }
            
      }//while loop close
      
      //last carry over check
      if (carryOver == 1){
         stringAnswer = "1" + stringAnswer;   
      }
       
      //return the new answer
      UnboundedInt answer = new UnboundedInt(stringAnswer);
      return answer;      
   }  
        
 
   /**
   * Multiply the current UnboundedInt with the passed in one. The
   * return becomes the new UnboundedInt
   * @param addend
   *   the passed in UnboundedInt
   * @postcondition
   *   The returned object is the product of the current and passed in UnboundedInt
   * @exception OutOfMemoryError
   *   Indicates insufficient memory. 
   **/   
   public UnboundedInt multiply(UnboundedInt multiplier)
   {   
      //initialze variables
      final int TOP = 999;
      
      //operation variables
      int carryOver = 0;
      int i;
      int j;
      int k;
      int z;
      int zeroCounter = 0;
      
      //node variables
      int thisNodeIntValue;
      String thisNodeStringValue;
      int currentSingularThisNodeIntValue;
      
      int multiplierNodeIntValue;
      String multiplierNodeStringValue;
      
      int blockProduct = 0;
      String blockProductString = "";
      
      //return variables
      UnboundedInt answer = new UnboundedInt("0");
      
      
      //go to beggining in each list
      start();
      multiplier.start();
        
      //for every node in the bottom number 
      for (i=0; i < front.listLength(front); i++){
         thisNodeIntValue = getNodeValue();
         thisNodeStringValue = String.valueOf(thisNodeIntValue);
         
         if (thisNodeStringValue.length() == 1 && i != front.listLength(front)- 1){
            thisNodeStringValue = "00" + thisNodeStringValue;
         }else if (thisNodeStringValue.length() == 2 && i != front.listLength(front)- 1){
            thisNodeStringValue = "0" + thisNodeStringValue;
         }
         
         //take that node and take the last integer in the 3 digit sequence
         for (j = thisNodeStringValue.length(); j > 0; j--){
                     
            /*take digit of 3 digit sequence and turn it into an int variable
            reads back to front (682 -> 2, 8, 6)*/
            currentSingularThisNodeIntValue = Integer.valueOf(String.valueOf(thisNodeStringValue.charAt(j - 1))); 
            blockProductString = null;
            blockProductString = "";
            
            //multiply it by the 3 digit node from the top number and store it using .add
            for (k=0; k < (multiplier.front).listLength(multiplier.front); k++){
               
               //point to first node (last three values) first time running this loop 
               if (k==0){
                  multiplier.start();
               }
               
               //get product
               multiplierNodeIntValue = (multiplier.cursor).getData();
               blockProduct = multiplierNodeIntValue * currentSingularThisNodeIntValue + carryOver;
               carryOver = 0;
           
               //determine the carry over and left over product
               if (blockProduct > TOP){
                  carryOver = blockProduct / 1000;
                  blockProduct = blockProduct % 1000;
               }
               
               //build the product
               blockProductString = String.valueOf(blockProduct) + blockProductString; 
               
               //if there is a 2/1 place value 3 digit number block, add 0's where needed
               if (String.valueOf(blockProduct).length() == 1 && k != ((multiplier.front).listLength(multiplier.front) - 1)){
                  blockProductString = "00" + blockProductString;
               }else if (String.valueOf(blockProduct).length() == 2 && k != ((multiplier.front).listLength(multiplier.front) - 1)){
                  blockProductString = "0" + blockProductString;
               }
               
               //add zeroes for each addition step
               if (k == ((multiplier.front).listLength(multiplier.front) - 1)){
                  for (z=0; z < zeroCounter; z++){
                     blockProductString = blockProductString + "0";   
                  }
                  zeroCounter++;
                  UnboundedInt mult1 = new UnboundedInt(blockProductString);
                  answer = answer.add(mult1);
               }
                
               multiplier.advance();
          
            }//third for loop close  
         }//secondary for loop close     
         advance(); 
      }//main for loop close 
         
         
       
      //return the new answer
      return answer;      
   }

 
   /**
   * Return a copy of original UnboundedInt
   * @precondition
   *   Original UnboundedInt is not empty.
   * @postcondition
   *   A copy of original UnboundedInt is returned.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory. 
   **/  
   public UnboundedInt clone()
   {  // Clone a UnboundedInt object.
      UnboundedInt answer;
      
      try
      {
         answer = (UnboundedInt) super.clone( );
      }
      catch (CloneNotSupportedException e)
      {  // This exception should not occur. But if it does, it would probably
         // indicate a programming error that made super.clone unavailable.
         // The most common error would be forgetting the "Implements Cloneable"
         // clause at the start of this class.
         throw new RuntimeException
         ("This class does not implement Cloneable");
      }
      
      
      return answer;
   }   
   
   
   /**
   * Check if the current UnboundedInt is the same as the input object.
   * @param - obj
   *   the object being compared to  
   * @postcondition
   *   If the two objects are the same return true. False otherwise
   * @exception IllegalStateException
   *   obj is empty.
   **/
   public boolean equals(Object obj)
   {
      boolean answer = true;
   
      //check if passed in object is actually UnboundedInt object
      if (obj instanceof UnboundedInt){
         UnboundedInt possible = (UnboundedInt) obj;
         
         //check if obj is empty
         if (possible.front.listLength(possible.front) == 0){
            throw new IllegalStateException();
         }
         
         //go to beggining of each list
         start();
         possible.start();
         
         //loop through both lists and check for discrepancy
         while (cursor != null || possible.cursor != null){
         
            if (getNodeValue() != possible.getNodeValue()){
               answer = false;
               break;   
            }
            
            //move forward in both lists
            advance();
            possible.advance();     
         }//while loop close
             
      }
      return answer;
   }
   
   
   /**
   * Create a string of all the elements seperated by commas with trailing zeroes.
   * @param - none
   * @return
   *   a string of all the elements in UnboundedInt.
   * @exception IllegalStateException
   *   Indicates UnboundedInt is empty.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory.
   **/ 
   public String toString( )
   {     
      //initialize varibles
      String answer = "";
      double formatAnswer;
      int length;
      int transfer;
      String stringTransfer;
      length = front.listLength(front) - 1;
      
      //check if obj is empty
      if (front.listLength(front) == 0){
         throw new IllegalStateException();
      }
      
      //go to start of linked list
      start();   
      for (int i=0; i<=length; i++){
      
         //determine the three number int value of the node
         transfer = getNodeValue();
         stringTransfer = String.valueOf(transfer);
         
         //add 0's if necessary
         if (stringTransfer.length() == 1 && i != length){
            stringTransfer = "00" + stringTransfer;
         }else if (stringTransfer.length() == 2 && i != length){
            stringTransfer = "0" + stringTransfer;
         }
         
         //append the node value to variable answer & add commas
         if (i==0){
            answer = stringTransfer + answer;
         }else{
            answer = stringTransfer + "," + answer;
         }
         
         //move forward in the list
         advance();
         
      }//for loop close
                   
      return answer; 
   }
   
      
   /**
   * Sets cursor to the front of the linked list.
   * @param - none
   * @postcondition
   *   Cursor points to the start of the list.  
   **/
   public void start( )
   {
      cursor = front;   
   }


   /**
   * Sets cursor to the next node of the linked list.
   * @param - none
   * @precondition
   *   The linked list has atleast one node
   * @postcondition
   *   The cursor points to the next node
   **/
   public void advance( )
   {
      cursor = cursor.getLink();   
   }


   /**
   * Accessor method to get the integer value of the Node pointed
   * to by the cursor. 
   * @param - none
   * @return
   *   Integer value of the node.
   **/
   public int getNodeValue( )
   { 
      int value = cursor.getData();
      return value;   
   }
              
     
}//class close