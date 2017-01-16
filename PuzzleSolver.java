/*
* Author: Derek Goodwin
* Date: 01/11/2017
* Description: This program counts the total number of occurrences of words in a given dictionary
* (DICTIONARY in this case) by checking horizontally, vertically, diagonally, as well as those three in reverse
*  and outputs the integer value.
 */

import java.util.*;
import java.lang.*;

public class PuzzleSolver {

    // I used ArrayList in order to use .contains method
    public static ArrayList<String> DICTIONARY =
            new ArrayList<>(Arrays.asList("OX", "CAT", "TOY", "AT", "DOG", "CATAPULT", "T"));


    static boolean IsWord(String testWord) {
        if (DICTIONARY.contains(testWord))
            return true;
        return false;
    }

    // My function starts here
    public static int FindWords(char[][] puzzle){


        // Convert argument from char[][] to String[]
        String[] PUZZLE = new String[puzzle.length];


        for (int i = 0; i < puzzle.length; i++) {
            String temp = "";
            for (int j = 0; j < puzzle[i].length; j++) {
                temp+= puzzle[i][j];
            }

            PUZZLE[i] = temp;
        }

        int returnVal = 0;

        // HORIZONTAL CHECKER

        // Outermost loop to iterate by each word within ArrayList
        for (int i = 0; i < PUZZLE.length;i++) {

            int tempLength = PUZZLE[i].length();

            String tempString = PUZZLE[i];

            // Get Reverse to Horizontally Check Reverse
            String reverseTempString = "";
            for (int rev = (tempLength - 1); rev > -1; rev--) {
                reverseTempString += tempString.substring(rev,rev+1);
            }

            // This loop gives the starting index of substring based off current word
            for (int j = 0; j < tempLength; j++) {

                // This loop gives the ending index of substring based off current word
                for (int k = j+1; k < tempLength + 1; k++) {
                    String temp = tempString.substring(j,k);

                    // Horizontal Check
                    if (IsWord(temp)) {
                        returnVal++;
                    }

                    // Reverse Horizontal Check. length() > 1 used to avoid duplicates
                    String tempR = reverseTempString.substring(j,k);
                    if (tempR.length() > 1 && IsWord(tempR)) {
                        returnVal++;
                    }
                }
            }
        }

        // VERTICAL CHECKER

        // Array lists used to store all vertical rows and combinations.
        ArrayList<String> tempList = new ArrayList<>();
        ArrayList<String> tempListReverse = new ArrayList<>();

        for (int row = 0; row < PUZZLE[0].length(); row++) {
            String tempVertical = "";
            String tempVerticalReverse = "";
            for (int column = 0; column < PUZZLE.length; column++) {

                tempVertical += PUZZLE[column].charAt(row);
                tempVerticalReverse += PUZZLE[PUZZLE.length - 1 - column].charAt(row);

                tempList.add(tempVertical);
                tempListReverse.add(tempVerticalReverse);

                // Now, add all variations of the max-length string going in the opposite direction
                if (column == PUZZLE.length-1) {

                    for (int a = 1; a < tempVertical.length(); a++) {
                        for (int b = a+1; b < tempVertical.length()+1;b++) {

                            if (tempVertical.substring(a, b).length() > 1) {
                                tempList.add(tempVertical.substring(a, b));
                                tempListReverse.add(tempVerticalReverse.substring(a, b));
                            }
                        }
                    }
                }
            }
        }

        // Increment count for vertical array
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).length() > 1 && IsWord(tempList.get(i))) {
                returnVal++;
            }
        }

        for (int i = 0; i < tempListReverse.size(); i++) {
            if (tempListReverse.get(i).length() > 1 && IsWord(tempListReverse.get(i))) {
                returnVal++;
            }
        }

        // DIAGONAL CHECKER

        // Reverse the elements in PUZZLE. That way, this same method can be applied to essentially traverse
        // the matrix from left to right in the southeast direction at the same time as left to right northeast
        String[] PUZZLEReverse = new String[PUZZLE.length];

        for (int reverse = 0; reverse < PUZZLE.length; reverse++) {
            PUZZLEReverse[reverse] = PUZZLE[PUZZLE.length - 1 - reverse];
        }

        // In any (n x m) (or (n x n)) matrix, the number of diagonals is equal to n + m - 1
        int outerLoopTotal = PUZZLE.length + PUZZLE[0].length() - 1;


        int max_bounds = PUZZLE.length - 1;

        // TOP HALF OF MATRIX

        // Very Important ArrayList. This stores every possible diagonal value. We increment the count at the end
        ArrayList<String> diagOne = new ArrayList<>();

        // Traverse top-half of matrix from left to right (excluding corners) in northeast direction
        // Also, calculate the same with inverse x and y. Thus calculating the reverse!
        for (int i = 0; i < outerLoopTotal; i++) {

            int x_coord = i+1;

            if (x_coord > max_bounds) {
                x_coord = max_bounds;
            }

            int y_coord = 0;

            String tempDiag = "";
            String tempDiagRev = "";

            // Reverse
            String REV_tempDiag = "";
            String REV_tempDiagRev = "";

            while (x_coord >= 0) {

                tempDiag += PUZZLE[x_coord].charAt(y_coord);
                tempDiagRev += PUZZLE[y_coord].charAt(x_coord);
                REV_tempDiag += PUZZLEReverse[x_coord].charAt(y_coord);
                REV_tempDiagRev += PUZZLEReverse[y_coord].charAt(x_coord);

                // Add to diag array
                if (tempDiag.length() > 1) {
                    diagOne.add(tempDiag);
                }

                // Add to diag array
                if (tempDiagRev.length() > 1) {
                    diagOne.add(tempDiagRev);
                }

                // Add to diag array
                if (REV_tempDiag.length() > 1) {
                    diagOne.add(REV_tempDiag);
                }

                // Add to diag array
                if (REV_tempDiagRev.length() > 1) {
                    diagOne.add(REV_tempDiagRev);
                }

                x_coord--;
                y_coord++;

                // Last possible option in matrix (we don't need to check singular corner due
                // to already being checked in horizontal. Check last then exit from for loop
                // in if check directly below
                if (x_coord == 0 && y_coord == max_bounds) {

                    tempDiag += PUZZLE[x_coord].charAt(y_coord);
                    tempDiagRev += PUZZLE[y_coord].charAt(x_coord);
                    REV_tempDiag += PUZZLEReverse[x_coord].charAt(y_coord);
                    REV_tempDiagRev += PUZZLEReverse[y_coord].charAt(x_coord);

                    // Add to diag array
                    if (tempDiag.length() > 1) {
                        diagOne.add(tempDiag);
                    }

                    // Add to diag array
                    if (tempDiagRev.length() > 1) {
                        diagOne.add(tempDiagRev);
                    }

                    // Add to diag array
                    if (REV_tempDiag.length() > 1) {
                        diagOne.add(REV_tempDiag);
                    }

                    // Add to diag array
                    if (REV_tempDiagRev.length() > 1) {
                        diagOne.add(REV_tempDiagRev);
                    }

                    break;
                }
            }

            // Add to diagOne array the all possible substrings for each diagonal
            for (int a = 1; a < tempDiag.length(); a++) {
                for (int b = a+1; b < tempDiag.length()+1;b++) {

                    if (tempDiag.substring(a, b).length() > 1 && tempDiag.substring(a, b).length() < tempDiag.length()) {
                        diagOne.add(tempDiag.substring(a, b));
                    }

                    if (REV_tempDiag.substring(a, b).length() > 1 && REV_tempDiag.substring(a, b).length() < REV_tempDiag.length()) {
                        diagOne.add(REV_tempDiag.substring(a, b));
                    }
                }
            }

            // Add to diagOne array the all possible substrings for each diagonal
            for (int a = 1; a < tempDiagRev.length(); a++) {
                for (int b = a+1; b < tempDiagRev.length()+1;b++) {

                    if (tempDiagRev.substring(a, b).length() > 1 && tempDiagRev.substring(a, b).length() < tempDiagRev.length()) {
                        diagOne.add(tempDiagRev.substring(a, b));
                    }

                    if (REV_tempDiagRev.substring(a, b).length() > 1 && REV_tempDiagRev.substring(a, b).length() < REV_tempDiagRev.length()) {
                        diagOne.add(REV_tempDiagRev.substring(a, b));
                    }

                }
            }

            // We have checked last possible option
            if (x_coord == 0 && y_coord == max_bounds) {
                break;
            }
        }

        // BOTTOM HALF OF MATRIX

        // -1 (in the -2 from remaining_diags) is due to not needing bottom-right corner
        // -1 is due to the top half already calculating bottom-left corner to northeast of matrix
        // = -2 in remaining_diags
        int remaining_diags = PUZZLE[0].length() - 2;

        for (int start = 0; start < remaining_diags; start++) {

            int bottom_half_x_coord = PUZZLE.length - 1; //--
            int bottom_half_y_coord = start + 1; //++

            String temp = "";
            String tempR = "";

            while (bottom_half_x_coord > -1) {

                // prevent OutOfBoundsException
                if (bottom_half_y_coord == PUZZLE[0].length()) {
                    break;
                }

                temp += PUZZLE[bottom_half_x_coord].charAt(bottom_half_y_coord);
                tempR += PUZZLEReverse[bottom_half_x_coord].charAt(bottom_half_y_coord);

                // Add to diag array
                if (temp.length() > 1) {
                    diagOne.add(temp);
                }

                // Add to diag array
                if (tempR.length() > 1) {
                    diagOne.add(tempR);
                }

                bottom_half_x_coord--;
                bottom_half_y_coord++;
            }

            // Add to diagOne array the all possible substrings for each diagonal
            for (int a = 1; a < temp.length(); a++) {
                for (int b = a+1; b < temp.length()+1;b++) {

                    if (temp.substring(a, b).length() > 1 && temp.substring(a, b).length() < temp.length()) {
                        diagOne.add(temp.substring(a, b));
                    }

                    if (tempR.substring(a, b).length() > 1 && tempR.substring(a, b).length() < tempR.length()) {
                        diagOne.add(tempR.substring(a, b));
                    }
                }
            }

            // Last check. Look at Reverse Strings of theses 2 and iterate through.
            String REV_temp = temp;
            String REV_tempR = tempR;


            String temp_of_REV_temp = "";
            String temp_of_REV_tempR = "";

            // Reverse string of diagonal reverse. Now, iterate from [0,0] to [end,0]
            for (int rev = 0; rev < temp.length(); rev++) {

                temp_of_REV_temp += REV_temp.charAt(REV_temp.length() - 1 - rev);
                temp_of_REV_tempR += REV_tempR.charAt(REV_tempR.length() - 1 - rev);

                // Add to diag array
                if (temp_of_REV_temp.length() > 1) {
                    diagOne.add(temp_of_REV_temp);
                }

                // Add to diag array
                if (temp_of_REV_tempR.length() > 1) {
                    diagOne.add(temp_of_REV_tempR);
                }
            }

            // Add to diagOne array the all possible substrings for each diagonal
            for (int a = 1; a < temp_of_REV_temp.length(); a++) {
                for (int b = a+1; b < temp_of_REV_temp.length()+1;b++) {

                    if (temp_of_REV_temp.substring(a, b).length() > 1 && temp_of_REV_temp.substring(a, b).length() < temp_of_REV_temp.length()) {
                        diagOne.add(temp_of_REV_temp.substring(a, b));
                    }

                    if (temp_of_REV_tempR.substring(a, b).length() > 1 && temp_of_REV_tempR.substring(a, b).length() < temp_of_REV_tempR.length()) {
                        diagOne.add(temp_of_REV_tempR.substring(a, b));
                    }
                }
            }
        }

        // diagOne now contains every possible diagonal entry. Loop through every element and increment count
        // if element is in dictionary
        for (int word = 0; word < diagOne.size(); word++) {

            if (diagOne.get(word).length() > 1 && IsWord(diagOne.get(word))) {
                // System.out.println("hit");
                returnVal++;
            }
        }

        return returnVal;
    }

    public static void main(String[] args) {
        System.out.print("Your current dictionary is: ");
        System.out.println(DICTIONARY);
        System.out.println();

        //Example 1
        char[][] Test1 = {{'C','A', 'T'}, {'X','Z','T'},{'Y','O','T'}};

        // Example 2
        char[][] Test2 = {{'C','A','T','A','P','U','L','T'}, {'X','Z','T','T','O','Y','O','O'},
                {'Y','O','T','O','X','T','X','X'}};

        // 4x4 Example
        char[][] Test3 = {{'F','I','S','H'}, {'D','O','G','S'}, {'C','A','T','S'}};

        // 6x9 Example
        char[][] Test4 = {{'A','B','C','D','E','F','G','H','I'}, {'C','A','T','S','C','A','T','S','C'},
                {'O','X','O','X','O','X','O','X','O'}, {'C','A','T','A','P','U','L','T','S'},
                {'R','A','I','N','R','A','I','N','R'}, {'M','O','U','N','T','A','I','N','S'}};

        // 9x9 Example
        char[][] Test5 = {{'A','B','C','D','E','F','G','H','I'}, {'C','A','T','S','C','A','T','S','C'},
                {'O','X','O','X','O','X','O','X','O'}, {'C','A','T','A','P','U','L','T','S'},
                {'R','A','I','N','R','A','I','N','R'}, {'M','O','U','N','T','A','I','N','S'},
                {'T','E','S','T','T','E','S','T','T'}, {'Y','A','Y','Y','A','Y','Y','A','Y'},
                {'L','A','K','E','L','A','K','E','L'}};

        System.out.print("Test 1: ");
        System.out.println(FindWords(Test1));

        System.out.println();

        System.out.print("Test 2: ");
        System.out.println(FindWords(Test2));

        System.out.println();

        System.out.print("Test 3: ");
        System.out.println(FindWords(Test3));

        System.out.println();

        System.out.print("Test 4: ");
        System.out.println(FindWords(Test4));

        System.out.println();

        System.out.print("Test 5: ");
        System.out.println(FindWords(Test5));
    }
}