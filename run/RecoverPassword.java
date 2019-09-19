//package run;

/*
 * University of Central Florida
 * CIS3360 - Fall 2017
 * Author: Neal Savant 
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;



public class RecoverPassword {
	
	public static void main(String[] args) {
		long[][] inputPWList= new long[999][11];	//ascii values 
		char[][] charPWList= new char[999][11];
		int numLines=0;			//number of rows in file
		System.out.println("-------------------------------------------------");
		System.out.println("Password Recovery by Neal Savant");
		System.out.println("");
		System.out.println("Dictionary file name        : "+ args[0]);
		System.out.println("Salted password hash value  : "+ args[1]);
		
		File pwFile = new File(args[0]); // fix to account for .txt
		
		//file read method
		try {
			
			System.out.println("File opened.");
			System.out.println("------------------------------------------------");
			System.out.println("| Index  |  Word  |  Unsalted ASCII equivalent |");
			System.out.print("------------------------------------------------");
			
			//counts number of lines(passwords) in the file
			FileReader input = new FileReader(pwFile);
			LineNumberReader count = new LineNumberReader(input);
			while(count.skip(Long.MAX_VALUE) > 0)
			{
				
			}
			//numLines is number of rows in the file
			numLines = count.getLineNumber()+1;
			input.close();
			count.close();
			
			//scan file and allocate ASCII values to inputPWList Array
			Scanner fileChars = new Scanner(pwFile);
			
			while(fileChars.hasNextLine()) {
			for(int x=0; x<numLines; x++) {
				for(int z=0; z<6; z++) {
					inputPWList[x][z] = Long.valueOf(fileChars.findInLine(".").charAt(0));
				}
				//scanner moves to next line
				if((x+1)==numLines)
					break;
				else if(x < numLines)
					fileChars.nextLine();
			}
			}
			//finished reading file
			fileChars.close();
		}
		catch(Exception e) {
			System.out.println("Failed to open file.");
			e.printStackTrace();
		}
		//get values for characters from ASCII long array
		for(int x=0; x<numLines; x++) {
			System.out.print("\n"+"| "+  (x+1) + ".   |   ");
			
			for(int z=0; z<6; z++) {
				charPWList[x][z] = (char)inputPWList[x][z];
				System.out.print(charPWList[x][z]);
			}
			//space between char output and int output
			System.out.print("  |   ");
			for(int y=0; y<6; y++) {
				System.out.print(inputPWList[x][y]);
			}
			System.out.print(" |");
			//scanner moves to next line
			//1 more password has been added to array			
		}
		System.out.println("\n---------------------------------------");
	//3) Preprocessing completed
	int saltCombinationsTested = 0;
	long inputHashValue = Long.valueOf(args[1]);
	String salt = "000";
	boolean finish = false;
	
	while(saltCombinationsTested < (1000*numLines) & finish == false) {
	//create prepended array based on current salt value
		String[] tempSaltPW = new String[numLines];
		for(int a=0; a<numLines; a++){
			tempSaltPW[a] = salt;
			for(int b=0; b<6; b++){
				tempSaltPW[a] += String.valueOf(inputPWList[a][b]);
			}
			//split into left and right part
			String left = (tempSaltPW[a].substring(0,7));
			String right = (tempSaltPW[a].substring(7,15));
			//System.out.println(tempSaltPW[a]);
			//compute hash value
			long tempHashValue = ( (243 * Long.parseLong(left)) + (Long.parseLong(right))) % 85767489;
			//System.out.println(tempHashValue);
			
			if(tempHashValue == inputHashValue) {
				System.out.println(" Password recovered:");
				//prints original character array
				System.out.print("  Password             : ");
				for(int d=0; d<6; d++)
					System.out.print(charPWList[a][d]);
				//prints original ASCII character array
				System.out.print("\n  ASCII Value          : ");
				for(int e=0; e<6; e++)
					System.out.print(inputPWList[a][e]);
				//prints Salt Value
				System.out.print("\n  Salt Value           : " + salt);
				System.out.print("\n  Combinations tested  : " + saltCombinationsTested);
				finish =true;
				break;
			}
			
			else if(tempHashValue != inputHashValue) {
				//increment salt combinations
				saltCombinationsTested++;
			}
			if(saltCombinationsTested == (1000*numLines)) {
				System.out.println("\nPassword not found in dictionary");
				System.out.println("Combinations tested: " + saltCombinationsTested );
			}
		}
		//concatenate string salt to integer then increment
		int tempSalt = Integer.valueOf(salt);
		tempSalt++;
		//return to string format
		salt = String.format("%03d", tempSalt);
	}
	
	}
}