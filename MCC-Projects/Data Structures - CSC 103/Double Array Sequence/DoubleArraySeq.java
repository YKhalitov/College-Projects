// File: DoubleArraySeq.java 
// Name: Yaroslav Khalitov
// This is an assignment for students to complete after reading Chapter 3 of
// "Data Structures and Other Objects Using Java" by Michael Main.


/******************************************************************************
* This class is a homework assignment;
* A DoubleArraySeq is a collection of double numbers.
* The sequence can have a special "current element," which is specified and 
* accessed through four methods that are not available in the bag class 
* (start, getCurrent, advance and isCurrent).
*
* @note
*   (1) The capacity of one a sequence can change after it's created, but
*   the maximum capacity is limited by the amount of free memory on the 
*   machine. The constructor, addAfter, 
*   addBefore, clone, 
*   and concatenation will result in an
*   OutOfMemoryError when free memory is exhausted.
*   <p>
*   (2) A sequence's capacity cannot exceed the maximum integer 2,147,483,647
*   (Integer.MAX_VALUE). Any attempt to create a larger capacity
*   results in a failure due to an arithmetic overflow. 
*
* @note
*   This file contains only blank implementations ("stubs")
*   because this is a Programming Project for my students.
*
* @see
*   <A HREF="../../../../edu/colorado/collections/DoubleArraySeq.java">
*   Java Source Code for this class
*   (www.cs.colorado.edu/~main/edu/colorado/collections/DoubleArraySeq.java)
*   </A>
*
* @version
*   March 5, 2002
******************************************************************************/
public class DoubleArraySeq implements Cloneable
{
   // Invariant of the DoubleArraySeq class:
   //   1. The number of elements in the sequences is in the instance variable 
   //      manyItems.
   //   2. For an empty sequence (with no elements), we do not care what is 
   //      stored in any of data; for a non-empty sequence, the elements of the
   //      sequence are stored in data[0] through data[manyItems-1], and we
   //      doncare what in the rest of data.
   //   3. If there is a current element, then it lies in data[currentIndex];
   //      if there is no current element, then currentIndex equals manyItems. 
   private double[ ] data;
   private int manyItems;
   private int currentIndex;

   

   
   /**
   * Initialize an empty sequence with an initial capacity of 10.  Note that
   * the addAfter and addBefore methods work
   * efficiently (without needing more memory) until this capacity is reached.
   * @param - none
   * @postcondition
   *   This sequence is empty and has an initial capacity of 10.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for: 
   *   new double[10].
   **/   
   public DoubleArraySeq( )
   {
      data = new double [0];
      manyItems = 0;
      currentIndex = -1;
   }
     

   /**
   * Initialize an empty sequence with a specified initial capacity. Note that
   * the addAfter and addBefore methods work
   * efficiently (without needing more memory) until this capacity is reached.
   * @param initialCapacity
   *   the initial capacity of this sequence
   * @precondition
   *   initialCapacity is non-negative.
   * @postcondition
   *   This sequence is empty and has the given initial capacity.
   * @exception IllegalArgumentException
   *   Indicates that initialCapacity is negative.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for: 
   *   new double[initialCapacity].
   **/   
   public DoubleArraySeq(int initialCapacity)
   {
      if (initialCapacity < 0){
         throw new IllegalArgumentException();
      }else{
         data = new double [0];
         manyItems = 0;
         currentIndex = -1;
      }   
         
   }  
        
 
   /**
   * Add a new element to this sequence, after the current element. 
   * If the new element would take this sequence beyond its current capacity,
   * then the capacity is increased before adding the new element.
   * @param element
   *   the new element that is being added
   * @postcondition
   *   A new copy of the element has been added to this sequence. If there was
   *   a current element, then the new element is placed after the current
   *   element. If there was no current element, then the new element is placed
   *   at the end of the sequence. In all cases, the new element becomes the
   *   new current element of this sequence. 
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for increasing the sequence's capacity.
   * @note
   *   An attempt to increase the capacity beyond
   *   Integer.MAX_VALUE will cause the sequence to fail with an
   *   arithmetic overflow.
   **/
   public void addAfter(double element)
   {
      double [] newArray;
      int i;
      ensureCapacity(data.length + 1);
      if (currentIndex == -1){
         currentIndex = data.length - 1;  
         data[currentIndex] = element;
         manyItems++;   
      }else{
         newArray = new double[data.length];
         advance();
         System.arraycopy(data, 0, newArray, 0, currentIndex );
         System.arraycopy(data, currentIndex , newArray, (currentIndex + 1), (manyItems - currentIndex));   
         newArray[currentIndex] = element;
         manyItems++;   
         data = newArray;
      }
      
      
      
        
   }


   /**
   * Add a new element to this sequence, before the current element. 
   * If the new element would take this sequence beyond its current capacity,
   * then the capacity is increased before adding the new element.
   * @param element
   *   the new element that is being added
   * @postcondition
   *   A new copy of the element has been added to this sequence. If there was
   *   a current element, then the new element is placed before the current
   *   element. If there was no current element, then the new element is placed
   *   at the start of the sequence. In all cases, the new element becomes the
   *   new current element of this sequence. 
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for increasing the sequence's capacity.
   * @note
   *   An attempt to increase the capacity beyond
   *   Integer.MAX_VALUE will cause the sequence to fail with an
   *   arithmetic overflow.
   **/
   public void addBefore(double element)
   {
      double [] newArray;
      int i;
      if (currentIndex == -1){
         addFront(element); 
      }else{
         ensureCapacity(data.length + 1);
         newArray = new double[data.length];
         System.arraycopy(data, 0, newArray, 0, currentIndex);
         System.arraycopy(data, currentIndex, newArray, (currentIndex + 1), (manyItems - currentIndex));
         newArray[currentIndex] = element;
         manyItems++;   
         data = newArray;
      }
   }
   
   
   /**
   * Place the contents of another sequence at the end of this sequence.
   * @param addend
   *   a sequence whose contents will be placed at the end of this sequence
   * @precondition
   *   The parameter, addend, is not null. 
   * @postcondition
   *   The elements from addend have been placed at the end of 
   *   this sequence. The current element of this sequence remains where it 
   *   was, and the addend is also unchanged.
   * @exception NullPointerException
   *   Indicates that addend is null. 
   * @exception OutOfMemoryError
   *   Indicates insufficient memory to increase the size of this sequence.
   * @note
   *   An attempt to increase the capacity beyond
   *   Integer.MAX_VALUE will cause an arithmetic overflow
   *   that will cause the sequence to fail.
   **/
   public void addAll(DoubleArraySeq addend)
   {
      double [] newArray;
      ensureCapacity(data.length + addend.manyItems);
      newArray = new double[data.length];
      System.arraycopy(data, 0, newArray, 0, manyItems);
      System.arraycopy(addend, 0, newArray, manyItems, addend.manyItems);
      manyItems += addend.manyItems;
      data = newArray; 
   }   
   
   
   /**
   * Move forward, so that the current element is now the next element in
   * this sequence.
   * @param - none
   * @precondition
   *   isCurrent() returns true. 
   * @postcondition
   *   If the current element was already the end element of this sequence 
   *   (with nothing after it), then there is no longer any current element. 
   *   Otherwise, the new element is the element immediately after the 
   *   original current element.
   * @exception IllegalStateException
   *   Indicates that there is no current element, so 
   *   advance may not be called.
   **/
   public void advance( )
   {
      currentIndex += 1;
   }
   
   
   /**
   * Generate a copy of this sequence.
   * @param - none
   * @return
   *   The return value is a copy of this sequence. Subsequent changes to the
   *   copy will not affect the original, nor vice versa.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for creating the clone.
   **/ 
   public DoubleArraySeq clone( )
   {  // Clone a DoubleArraySeq object.
      DoubleArraySeq answer;
      
      try
      {
         answer = (DoubleArraySeq) super.clone( );
      }
      catch (CloneNotSupportedException e)
      {  // This exception should not occur. But if it does, it would probably
         // indicate a programming error that made super.clone unavailable.
         // The most common error would be forgetting the "Implements Cloneable"
         // clause at the start of this class.
         throw new RuntimeException
         ("This class does not implement Cloneable");
      }
      
      answer.data = data.clone( );
      
      return answer;
   }
   


   /**
   * Change the current capacity of this sequence.
   * @param minimumCapacity
   *   the new capacity for this sequence
   * @postcondition
   *   This sequence's capacity has been changed to at least minimumCapacity.
   *   If the capacity was already at or greater than minimumCapacity,
   *   then the capacity is left unchanged.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for: new int[minimumCapacity].
   **/
   public void ensureCapacity(int minimumCapacity)
   {
      double [] newArray;
      int newCap;
      if (data.length < minimumCapacity){
         newCap = data.length + 1;
         newArray = new double[newCap];
         if (data.length == 0){
            data = newArray;      
         }else{
            System.arraycopy(data, 0, newArray, 0, data.length);
            data = newArray;  
         } 
      }
   }

   
   /**
   * Accessor method to get the current capacity of this sequence. 
   * The add method works efficiently (without needing
   * more memory) until this capacity is reached.
   * @param - none
   * @return
   *   the current capacity of this sequence
   **/
   public int getCapacity( )
   {
      return data.length;
   }


   /**
   * Accessor method to get the current element of this sequence. 
   * @param - none
   * @precondition
   *   isCurrent() returns true.
   * @return
   *   the current element of this sequence
   * @exception IllegalStateException
   *   Indicates that there is no current element, so 
   *   getCurrent may not be called.
   **/
   public double getCurrent( )
   {
      return data[currentIndex];
   }


   /**
   * Accessor method to determine whether this sequence has a specified 
   * current element that can be retrieved with the 
   * getCurrent method. 
   * @param - none
   * @return
   *   true (there is a current element) or false (there is no current element at the moment)
   **/
   public boolean isCurrent( )
   {
      if (currentIndex <= data.length && currentIndex >= 0){
         return true;
      }else{
         return false;
      }  
      
   }
              
   /**
   * Remove the current element from this sequence.
   * @param - none
   * @precondition
   *   isCurrent() returns true.
   * @postcondition
   *   The current element has been removed from this sequence, and the 
   *   following element (if there is one) is now the new current element. 
   *   If there was no following element, then there is now no current 
   *   element.
   * @exception IllegalStateException
   *   Indicates that there is no current element, so 
   *   removeCurrent may not be called. 
   **/
   public void removeCurrent( )
   {
      boolean currentFlag;
      double [] newArray;
      currentFlag = isCurrent();
      if (currentFlag == false){
         throw new IllegalStateException();
      }else{
         newArray = new double[data.length - 1];
         System.arraycopy(data, 0, newArray, 0, currentIndex);
         System.arraycopy(data, (currentIndex + 1) , newArray, currentIndex, (data.length - currentIndex - 1));  
         data = newArray;
         manyItems--;
         
         
            
           
      }
           
      
   }
                 
   
   /**
   * Determine the number of elements in this sequence.
   * @param - none
   * @return
   *   the number of elements in this sequence
   **/ 
   public int size( )
   {
      return manyItems;
   }
   
   
   /**
   * Set the current element at the front of this sequence.
   * @param - none
   * @postcondition
   *   The front element of this sequence is now the current element (but 
   *   if this sequence has no elements at all, then there is no current 
   *   element).
   **/ 
   public void start( )
   {
      currentIndex = 0;
   }
   
   
   /**
   * Reduce the current capacity of this sequence to its actual size (i.e., the
   * number of elements it contains).
   * @param - none
   * @postcondition
   *   This sequence's capacity has been changed to its current size.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory for altering the capacity. 
   **/
   public void trimToSize( )
   {
      double[ ] trimmedArray;
      
      if (data.length != manyItems)
      {
         trimmedArray = new double[manyItems];
         System.arraycopy(data, 0, trimmedArray, 0, manyItems);
         data = trimmedArray;
      }
   }
   
   
   /**
   * Add a new element to the front of the sequence and make it the current element
   * @param value
   *   Element which is added
   * @postcondition
   *   The double element is added to the front of the sequence.
   *   The current element now points to this element.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory to increase the size of this sequence.
   **/
   public void addFront(double value)
   {
      double [] newArray;
      ensureCapacity(data.length + 1);
      newArray = new double[data.length];
      System.arraycopy(data, 0, newArray, 1, manyItems); 
      newArray[0] = value;
      data = newArray;
      manyItems++;         
   }
   
   
   /**
   * Remove element in the front of the sequence
   * @param - none
   * @postcondition
   *   Remove front element and make the current element the new front element.
   *   If Only one element, remove it and make the current element null.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory to increase the size of this sequence.
   * @exception IllegalStateException
   *   Indicates sequence is empty when method is called.
   **/
   public void removeFront()
   {
      setCurrent(1);
      removeCurrent();
      manyItems--;  
   }
   
   
   /**
   * Makes nth element the current element
   * @param place
   *   The place/term that becomes the current element
   * @postcondition
   *   The current element becomes whatever value is.   
   * @exception OutOfMemoryError
   *   Indicates insufficient memory to increase the size of this sequence.
   * @exception IllegalStateException
   *   Indicates sequence is empty when method is called.
   * @exception IllegalArgumentException
   *   Indicates parameter is not a valid location.
   **/
   public void setCurrent(int place)
   {
      if (manyItems == 0){
         throw new IllegalStateException();
      }else if (place > manyItems){
         throw new IllegalArgumentException();
      }else{
         currentIndex = place - 1;   
      }
      
   }
   
   
   /**
   * Return a specified locations element in the sequence.
   * @param place
   *   The place/term of the sequence that gets returned.
   * @postcondition
   *   The current element becomes whatever the value of place is.   
   * @return
   *   The specified places element in the sequence.
   * @exception IllegalStateException
   *   Indicates sequence is empty when method is called.
   * @exception IllegalArgumentException
   *   Indicates parameter is not a valid location.
   **/
   public double getElement(int place)
   {  
      if (manyItems == 0){
         throw new IllegalStateException();
      }else if (place > manyItems){
         throw new IllegalArgumentException();
      }else{
         setCurrent(place);
         return data[currentIndex];
      }        
   }
   
   
   /**
   * Returns true if sequence is the same length and order and data.
   * @param addend
   *   The object that is being compared
   * @postcondition
   *   True/False is returned if both sequences are the same. 
   * @return
   *   Either true if the sequences are the same or false if they are not.
   **/
   public boolean equals(DoubleArraySeq addend)
   {  
      boolean lengthCheck = false;
      boolean dataCheck = false;
      if (data.length == addend.manyItems){
         lengthCheck = true;
      }
      for (int i = 0; i <= data.length; i++){
         if (data[i] != addend.getCurrent()){
            dataCheck = false;
         }else{
            dataCheck = true;
         }
      }
      if (dataCheck == true && lengthCheck == true){
         return true;
      }else{
         return false;
      }
   }
   
   
   /**
   * Create a string of all elements in sequence seperated by spaces.
   * @postcondition
   *   Resets original value of current element.
   * @return
   *   String of a all elements in the sequence.
   * @exception IllegalStateException
   *   Indicates sequence is empty when method is called.
   **/
   public String toString()
   {
      String elements = "";
      int i;
      int tempIndex;
      tempIndex = currentIndex;
      
      start();
      for(i=0; i < data.length; i++){
         elements = elements + " " + getCurrent();
         advance();   
      }
      currentIndex = tempIndex;
      return elements;
   }

      
}