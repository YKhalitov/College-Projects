// Name: Yaroslav Khalitov
// File: Queen.java
// Class: CSC 103
// Description: This program is the driving force for checking if a queen location conflicts with another
// queen location by using linked stacks.


public class Queen{
   private int row;
   private int column;
   
   
    /**
   * Initialize a Queen object with row and columns. 
   * @param - row
   *   the row position of the Queen
   * @param - column
   *   the column position of the Queen
   * @precondition
   *  row & column are non-negative.
   * @postcondition
   *   A Queen object created with users input
   * @exception OutOfMemoryError
   *   Indicates insufficient memory. 
   **/ 
   public Queen(int row, int column){
      this.row = row;
      this.column = column;
   }
   
   /**
   * Return the row of the current Queen object
   * @param - none
   * @return
   *   the row of this queen
   **/ 
   public int getRow( )
   {                        
      return row; 
   }
   
   /**
   * Return the column of the current Queen object
   * @param - none
   * @return
   *   the column of this queen
   **/ 
   public int getColumn( )
   {                         
      return column; 
   }
   
   /**
   * Create a string of the Queen position seperated by spaces.
   * @param - none
   * @return
   *   a string of all the elements in Queen.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory.
   **/ 
   public String toString( )
   {  
      //variables   
      String answer = "Row = " + row + " Column = " + column;
                         
      return answer; 
   }
   
  
   
   
   /**
   * Tests a sent in Queen to see if it is in conflict with any other Queens.
   * @param - Queen
   *  the row and column of a sent in Queen
   * @return
   *   a boolean representing whether the Queen is/is not in conflict with the other Queens.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory.
   **/ 
   public boolean conflict(Queen testQueen)
   {  
      //variables   
      boolean answer = false;
      int rowDif;
      int columnDif;
      
      //test horizontal
      if (this.row == testQueen.getRow()){
         answer = true;
      }
      
      //test vertical
      if (this.column == testQueen.getColumn()){
         answer = true;
      }
      
      //test diagonal
      rowDif = Math.abs(this.row - testQueen.getRow());
      columnDif = Math.abs(this.column - testQueen.getColumn());
      if ((rowDif - columnDif) == 0){
         answer = true;
      }
                         
      return answer; 
   }
   
   

}