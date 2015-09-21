package com.deitel;

import java.util.Scanner;
import javax.swing.JOptionPane;

public class Simpletron {
	
	private static String[] memory;
	private static int accumulator = 0;
	private static int instructionCounter = 0;
	private static int instructionRegister = 0;
	private static int globalOperand;

	public Simpletron() {
		memory = new String[100];
	}

	private static void startMessage() {
		System.out.println("*** Welcome to Simpletron! 					***");
		System.out.println( "*** Please enter your program one instruction  ***" );
		System.out.println( "*** (or data word) at a time. I will display   ***" );
		System.out.println( "*** the location number and a question mark    ***" );
		System.out.println( "*** You then type the word for that location.  ***" );
		System.out.println("*** Type -99999 to stop entering your program. ***");
	}

	private static void prompt() {
		startMessage();
		Scanner input = new Scanner(System.in);
		int counter = 0;

		do {
			System.out.printf("%02d ? ", counter, counter);
			memory[counter] = input.nextLine();
			counter++;
			System.out.println();
		} while(!(memory[counter - 1].equals("-99999")));

		System.out.println("*** Program loading completed ***");
		System.out.println("*** Program execution begins  ***");
	}

	public static void execute() {
		prompt();
		for (int i = 0; i < memory.length; i++) {
			instructionCounter = i;
			instructionRegister = Integer.parseInt(memory[instructionCounter]);
			determineOperationCode(instructionRegister);

			if (instructionRegister / 100 == 43) {
				break;
			}

			if (globalOperand > 0) {
				i += globalOperand - i - 1;
			}
		}
	}

	private static void determineOperationCode(int instructionRegister) {
		int operationCode = instructionRegister / 100;
		int operand = instructionRegister % 100;

		switch( operationCode ) {
			case SimpletronConstants.READ :
				int number = 
					Integer.parseInt( JOptionPane.showInputDialog("Enter an Integer") );
				memory[ operand ] = "" + number;
				break;

			case SimpletronConstants.WRITE :
				System.out.println( memory[ operand ] );
				break;

			case SimpletronConstants.LOAD :
				accumulator = Integer.parseInt( memory[ operand ] );
				break;

			case SimpletronConstants.STORE :
				memory[ operand ] = "" + accumulator;
				break;

			case SimpletronConstants.ADD :
				accumulator += Integer.parseInt( memory[ operand ] );
				break;

			case SimpletronConstants.SUBSTRACT :
				accumulator -= Integer.parseInt( memory[ operand ] );
				break;

			case SimpletronConstants.DIVIDE :
				accumulator /= Integer.parseInt( memory[ operand ] );
				break;
			
			case SimpletronConstants.MULTIPLY :
				accumulator *= Integer.parseInt( memory[ operand ] );
				break;

			case SimpletronConstants.BRANCH:
				globalOperand = operand;
				break;

			case SimpletronConstants.BRANCHZERO:
				if (accumulator == 0)
					globalOperand = operand;
				break;

			case SimpletronConstants.BRANCHNEG :
				if (accumulator < 0)
					globalOperand = operand;
				break;

			case SimpletronConstants.HALT:
				System.out.println("*** Simpletron execution terminated ***");
				break;
		}
	}
}
