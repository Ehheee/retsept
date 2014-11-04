package thething.retsept.utils;

import java.util.Arrays;
import java.util.LinkedList;

public class WordComparator {

	
	public double compareWords(String a, String b){
		int basePoints = a.length() -1;
		double totalPoints = 0;
		System.out.println("basePoints set to: " + basePoints);
		System.out.println("Evaluating a: " + a + " b: " + b);
		for(int i = 0; i < basePoints +1; i++){
			char ai = a.charAt(i);
			int posInB = b.indexOf(ai);
			
			totalPoints += basePoints - Math.abs((i - posInB));
			System.out.println("Evaluated char: " + ai + " index in b: " + posInB +  " posDifference: " + Math.abs((i - posInB)) + " newTotal: " + totalPoints);
		}
		totalPoints = totalPoints/(a.length() *b.length());
		System.out.println("dividedTotal: " + totalPoints);
		return totalPoints;
	}
	
	public double compareWords2(String a, String b){
		a = a.toLowerCase();
		b = b.toLowerCase();
		int basePoints = 3;
		double totalPoints = 0;
		System.out.println("Evaluating a: " + a + " b: " + b);
		char[] bChars = b.toCharArray();
		
		int pushK = 0;
		for(int i = 0; i < a.length() ; i++){
			char ai = a.charAt(i);
			for(int k = Math.max((i - (basePoints - 1)) + pushK, 0); k < (i+basePoints) && k < b.length(); k++){
				if(a.charAt(i) == b.charAt(k)){
					
					totalPoints += basePoints - Math.abs(i - k + pushK);
					
					System.out.println("Index in a: " + i + " Index in b: " + k + " Points given: " + (basePoints - (Math.abs(i-k + pushK))));
					if(i != k){
						pushK = k -i;
					}
				}
			}
		}
		totalPoints = totalPoints/a.length();
		System.out.println("dividedTotal: " + totalPoints);
		return totalPoints;
	}
	
	public double compareWords3(String a, String b){
		LinkedList<Character> aChars = this.wordToLinkedList(a);
		LinkedList<Character> bChars = this.wordToLinkedList(b);
		int aSize = a.length();
		double totalPoints = 0;
		//As array is modified during the loop then the size of original string has to be used in comparison
		for(int ai = 0; ai < aSize; ai++){
			char aChar = aChars.getFirst();
			int diff  = bChars.indexOf(aChar);
			
			System.out.println("aChars: " + aChars + " bChars: " + bChars);
			System.out.println("Character in string a: " + aChar + " Index in string a: " + ai + " Index in string b: " +diff);
			
			//if character in string a exists also in string b give 0.5 points
			if(diff != -1){
				
				System.out.println("Character: " + aChar + " also exists in b + 0.5 points");
				totalPoints += 0.5;
				
			}else{
				aChars.remove();
				totalPoints -= 0.25;
			}
			
			//if character in string a exists in same position in string b give 0.5 points
			if(diff == 0){
				
				System.out.println("Character: " + aChar + " has same position in b + 0.5 points");
				totalPoints += 0.5;
				//remove the first characters as they match
				aChars.remove();
				bChars.remove();
			
			}
			
			//else if character in string a exists in b but on the next position
			else if(diff == 1){
				
				//If the next  char in a is at the current index in b, characters might be switched.
				if(aChars.get(1) == bChars.get(0)){
					//If the next characters after the possibly switched ones match, there is high chance of spelling mistake
					if(aChars.get(2) == bChars.get(2)){
						System.out.println("Possibly switched characters: " + aChars.get(ai) + " , " +bChars.get(ai +1));
						totalPoints += 1;
						
					}
					//remove the first 2 characters as they are both evaluated and possible points given
					aChars.remove();
					aChars.remove();
					bChars.remove();
					bChars.remove();	
				}
				
				//else if the char in a is the next char in b, b might have an extra character
				else if(aChar == bChars.get(1)){
					//next chars after that are the same
					if(aChars.get(1) == bChars.get(2)){
						totalPoints += 0.25;
					}
					
					aChars.remove();
					bChars.remove();
					bChars.remove();
					
				}
				
				//else if the current char in b equals the next one in a, b might miss a character
				else if(bChars.get(0) == aChars.get(1)){
					//next chars after that are the same
					if(bChars.get(1) == aChars.get(2)){
						totalPoints += 0.75;
					}
					aChars.remove();
					aChars.remove();
					bChars.remove();
					
				}
				
			}
			
			System.out.println("New totalpoints: " + totalPoints);
		}
		System.out.println("Totalpoints: " + totalPoints);
		return 0;
	}
	
	private LinkedList<Character> wordToLinkedList(String s){
		LinkedList<Character> chars = new LinkedList<Character>();
		for(char c: s.toCharArray()){
			chars.add(c);
		}
		return chars;
	}
}
