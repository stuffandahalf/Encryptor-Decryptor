import java.util.*;
import java.io.*;

public class Encrypt {
	public static void main (String args[]) throws IOException
    {
		//String key = args[0];
        //File in = new File(args[1]);
        //String out = args[2];

        String key = "ABCDEFGH";
        File in = new File("test.txt");
        String outFile = "testout.txt";
        
        int hash = key.hashCode();
        Random random = new Random(hash);
        Scanner scanner = new Scanner(in);
        String fileString ="";
        while(scanner.hasNext())
        {
            fileString += scanner.next() + " ";
        }
        fileString = fileString.toUpperCase();
        //System.out.println(fileString);
        
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
        String origAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
        int[] pattern = {0, 1, 2, 3, 4, 5, 6, 7};
        for(int i = 0; i < 100; i++)
        {
            int indLetter1 = random.nextInt(27);
            int indLetter2 = random.nextInt(27);
            char letter1 = alphabet.charAt(indLetter1);
            char letter2 = alphabet.charAt(indLetter2);
            alphabet = alphabet.replace(letter1, '$');
            alphabet = alphabet.replace(letter2, letter1);
            alphabet = alphabet.replace('$', letter2);
            
            //System.out.println(alphabet);
        }
        String strippedFileString = "";
        for(int i = 0; i < fileString.length(); i++)
        {
            if(origAlphabet.indexOf(fileString.charAt(i)) != -1)
            {
                strippedFileString += fileString.charAt(i);
            }
            else if(origAlphabet.indexOf(fileString.charAt(i)) == -1)
            {
                strippedFileString += " ";
            }
        }
        fileString = strippedFileString;
        random = new Random(hash);
        for(int i = 0; i < 100; i++)
        {
            int indPattern1 = random.nextInt(8);
            int indPattern2 = random.nextInt(8);
            int tempPattern = pattern[indPattern1];
            pattern[indPattern1] = pattern[indPattern2];
            pattern[indPattern2] = tempPattern;
        }
        
        
        String encrypted = "";
        for(int i = 0; i < 64; i++)
        {
            encrypted += alphabet.charAt(origAlphabet.indexOf(fileString.charAt(0)));
            if(fileString.length() == 0)
            {
                break;
            }
            fileString = fileString.substring(1, fileString.length());
        }
        
        char[][] encryptedMatrix = new char[8][8];
        
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                encryptedMatrix[i][j] = encrypted.charAt(0);
                if(encrypted.length() == 0)
                {
                    break;
                }
                encrypted = encrypted.substring(1, encrypted.length());
            }
        }
        
        System.out.println(Arrays.deepToString(encryptedMatrix));
        String output = "";
        for(int j : pattern)
        {
            for(int i = 0; i < 8; i++)
            {
                output += encryptedMatrix[i][j];
            }
        }
        System.out.println(output);
        PrintWriter out = new PrintWriter(outFile);
        out.println(output);
        out.close();
	}
}
