// Name: Yaroslav Khalitov
// File: Golfer.java
// Class: CSC 103
// Description: This class stores information on the Golfers Data.
import java.text.DecimalFormat;

public class Golfer implements Comparable<Golfer>{
   private String lastname;
   private int numberOfRounds;
   private double averageScore;
   
   
   /**
   * Initialize a Golfer object with a lastname, rounds, and score.
   * @param - name
   *   the lastname of the golfer
   * @param - rounds
   *   the number of rounds the golfer has played
   * @param - score
   *   the average score of the golfer
   * @postcondition
   *   A Golfer object created with golfers data.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory. 
   **/ 
   public Golfer(String name, int rounds, double score){
      lastname = name;
      numberOfRounds = rounds;
      averageScore = score;
   }
   
   /**
   * Initialize a Golfer object with a lastname.
   * @param - name
   *   the lastname of the golfer
   * @postcondition
   *   A Golfer object created with golfers name.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory. 
   **/ 
   public Golfer(String name){
      lastname = name;
   }
   
   /**
   * Return the name of the current Golfer object
   * @param - none
   * @return
   *   the name of this golfer
   **/ 
   public String getName( )
   {                        
      return lastname; 
   }
   
   /**
   * Return the number of rounds of the current Golfer object
   * @param - none
   * @return
   *   the number of rounds of this golfer
   **/ 
   public int getRounds( )
   {                         
      return numberOfRounds; 
   }
   
   /**
   * Return the average score of the current Golfer object
   * @param - none
   * @return
   *   the average score of this golfer
   **/ 
   public double getAvgScore( )
   {                         
      return averageScore; 
   }
   
   /**
   * Set the lastname of the current Golfer object
   * @param - name
   *   the lastname of the golfer
   * @postcondition
   *   The lastname of Golfer object updated with parameter.
   * @return - none
   **/ 
   public void setName(String name)
   {                        
      lastname = name; 
   }
   
   /**
   * Set the numberOfRounds of the current Golfer object
   * @param - rounds
   *   the number of rounds the golfer has played
   * @postcondition
   *   The numberOfRounds of Golfer object updated with parameter.
   * @return - none
   **/  
   public void setRounds(int rounds)
   {                         
      numberOfRounds = rounds; 
   }
   
   /**
   * Set the averageScore of the current Golfer object
   * @param - score
   *   the average score of the golfer
   * @postcondition
   *   The averageScore of Golfer object updated with parameter.
   * @return - none
   **/  
   public void setAvgScore(double score)
   {                         
      averageScore = score; 
   }
   
   /**
   * Adds a new score to the golfer's data.
   * @param - score
   *   a new score of the golfer
   * @exception OutOfMemoryError
   *   Indicates insufficient memory.
   **/ 
   public void addNewScore(double score)
   {  
      averageScore *= numberOfRounds;
      averageScore += score;
      
      numberOfRounds++;
      averageScore /= numberOfRounds;                          
   }
   
   
   /**
   * Compares the names of golfers.
   * @param - otherGolfer
   *   the golfer being compared to
   * @return
   *   a true or false if names are equal   
   **/ 
   public int compareTo(Golfer otherGolfer)
   {  
      int answer = this.lastname.compareTo(otherGolfer.getName());                       
      return answer;   
   }
   
   /**
   * Create a string of the Golfer data.
   * @param - none
   * @return
   *   a string of all the data in Golfer.
   * @exception OutOfMemoryError
   *   Indicates insufficient memory.
   **/ 
   public String toString( )
   {  
      //variables
      String answer = lastname + " " + numberOfRounds + " " + averageScore;
                       
      return answer; 
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
   public String formatData(String golferInfo)
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
   
      
  
   
   
   
   
   

}