import java.util.*;
import java.io.*;

public class Decrypt {
	
	public static void main (String args[]) throws IOException
    {
		//String key = args[0];
        //File in = new File(args[1]);
        //String out = args[2];
        
        final String key = "ABCDEFGH";                                // variable to store encryption key
        final File in = new File("input.txt");                         // variable to store input file
        final String outFile = "output.txt";                         // name of output file
        
        final int SQUARE = 8;                                   // square root of number of chars to work with at a time
        
        int hash = key.hashCode();                              // hashed key
        Random random = new Random(hash);                       // created random generator using hashed value as seed
        Scanner scanner = new Scanner(in);                      // created a scanner from the input file
        String fileString ="";                                  // created a variable to hold the contents of the input file
        while(scanner.hasNext())                                // appended every value from the scanner to the variable
        {
            fileString += scanner.next() + " ";
        }
        fileString = fileString.toUpperCase();                  // converted the input text to uppercase
        
        String encryptedAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";        // created a variable to store the alphabet to be encryoted
        final String origAlphabet = encryptedAlphabet;            // made another variable to store the original alphabet before encrytion
        
        encryptedAlphabet = alphabetEncryptor(hash, encryptedAlphabet);
        System.out.println(origAlphabet);
        
        fileString = stringStripper(fileString, origAlphabet);
        
        System.out.println(fileString);
        
        int[] pattern = pattern(hash, SQUARE);
        
        System.out.println(Arrays.toString(pattern));
        
        String decryptedString = "";                            // location to store the decrypted string
        
        char[][] encryptedArray = encryptedToArray(fileString, SQUARE, pattern);
        
        String encryptedString = "";                            // location to store the re-sorted string
        for(int i = 0; i < SQUARE; i++)
        {
            for(int j = 0; j < SQUARE; j++)
            {
                encryptedString += encryptedArray[i][j];        // add all of the sorted characters to the string
            }
        }
        System.out.println(encryptedString);
        while(encryptedString.length() > 0)                     // while there are values to decrypt
        {
            decryptedString += origAlphabet.charAt(encryptedAlphabet.indexOf(encryptedString.charAt(0)));    // match the letter to the original and add it to the new string
            encryptedString = encryptedString.substring(1, encryptedString.length());       // remove the first character of the encrypted string
        }
        
        System.out.println(decryptedString);
        
        PrintWriter out = new PrintWriter(outFile);
        out.println(decryptedString);
        out.close();
        //System.out.println(Arrays.deepToString(encryptedArray));
        
	}
    
    public static String alphabetEncryptor(int hash, String alphabet)
    {
        Random random = new Random(hash);
        for(int i = 0; i < 100; i++)                            // replace two random letters in the alphabet 100 times
        {
            int indLetter1 = random.nextInt(27);
            int indLetter2 = random.nextInt(27);
            char letter1 = alphabet.charAt(indLetter1);
            char letter2 = alphabet.charAt(indLetter2);
            alphabet = alphabet.replace(letter1, '$');
            alphabet = alphabet.replace(letter2, letter1);
            alphabet = alphabet.replace('$', letter2);
        }
        return alphabet;
    }
    
    public static int[] pattern(int hash, int size)
    {
        int[] pattern = new int[size];
        for(int i = 0; i < size; i++)
        {
            pattern[i] = i;
        }
        Random random = new Random(hash);                              // reset the random generator
        for(int i = 0; i < 100; i++)                            // swap two random numbers in the order for reading
        {
            int indPattern1 = random.nextInt(8);
            int indPattern2 = random.nextInt(8);
            int tempPattern = pattern[indPattern1];
            pattern[indPattern1] = pattern[indPattern2];
            pattern[indPattern2] = tempPattern;
        }
        return pattern;
    }
    
    public static String stringStripper(String fileString, String origAlphabet)
    {
        String strippedFileString = "";
        for(int i = 0; i < fileString.length(); i++)            // for every character in the text
        {
            if(origAlphabet.indexOf(fileString.charAt(i)) != -1)    // check if it is in the original alphabet
            {
                strippedFileString += fileString.charAt(i);         // concatenate it if it is
            }
            else if(origAlphabet.indexOf(fileString.charAt(i)) == -1)
            {
                strippedFileString += " ";                          // add a space if it is not
            }
        }
        return strippedFileString;
    }
    
    public static char[][] encryptedToArray(String fileString, int SQUARE, int[] pattern)
    {
        char[][] encryptedArray = new char[SQUARE][SQUARE];      // created a square 2d array to store the encrypted characters
        for(int j = SQUARE-1; j >= 0; j--)
        {
            for(int i = SQUARE-1; i >= 0; i--)
            {
                //System.out.println(i);
                if(fileString.length() == 0)            // fill the array while there are values left in the string
                {
                    break;
                }
                encryptedArray[i][pattern[j]] = fileString.charAt(fileString.length()-2);       // filled the array with the string from the file
                fileString = fileString.substring(0, fileString.length()-1);
            }
        }
        return encryptedArray;
    }
    
    
            
}

