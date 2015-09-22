package com.deitel;

import java.util.Scanner;

public class Simpletron {
	
	private static String[] memory = new String[100];
	private static int accumulator = 0;
	private static int instructionCounter = 0;
	private static int instructionRegister = 0;

	private static Scanner input;

	private static void startMessage() {
		println("*** Welcome to Simpletron! 					   ***");
		println("*** Please enter your program one instruction  ***");
		println("*** (or data word) at a time. I will display   ***");
		println("*** the location number and a question mark    ***");
		println("*** You then type the word for that location.  ***");
		println("*** Type -99999 to stop entering your program. ***");
	}

	private static void prompt() {
		startMessage();
		input = new Scanner(System.in);
		int counter = 0;
		boolean s = false;

		do {
			System.out.printf("%02d ? ", counter);
			memory[counter] = input.nextLine();

			counter++;
			println();

		} while(!memory[counter - 1].equals("-99999"));

		println("*** Program loading completed ***");
		println("*** Program execution begins  ***");
	}

	public static void execute() {
		prompt();
		while (true) {
			instructionRegister = Integer.parseInt(memory[instructionCounter]);
			determineOperationCode(instructionRegister);

			if (instructionRegister == 4300) {
				return;
			}
		}
	}

	private static void determineOperationCode(int instructionRegister) {
		int operationCode = instructionRegister / 100;
		int operand = instructionRegister % 100;

		switch( operationCode ) {
			case SimpletronConstants.READ :
				input = new Scanner(System.in);
				print("Enter an Integer: ");

				int number = input.nextInt();
				println();

				memory[ operand ] = "" + number;
				instructionCounter++;
				//dump(operationCode, operand);
				break;

			case SimpletronConstants.WRITE :
				System.out.println( memory[ operand ] );
				instructionCounter++;
				break;

			case SimpletronConstants.LOAD :
				accumulator = Integer.parseInt( memory[ operand ] );
				instructionCounter++;
				break;

			case SimpletronConstants.STORE :
				memory[ operand ] = "" + accumulator;
				instructionCounter++;
				break;

			case SimpletronConstants.ADD :
				int input = Integer.parseInt(memory[operand]);
				if (input >= -9999 && input <= 9999) {
					accumulator += input;
				}
				instructionCounter++;
				break;

			case SimpletronConstants.SUBSTRACT :
				accumulator -= Integer.parseInt( memory[ operand ] );
				instructionCounter++;
				break;

			case SimpletronConstants.DIVIDE :
				int divider = Integer.parseInt(memory[operand]);
				try {
					accumulator /= divider;
				} catch(Exception e) {
					System.err.println("Unexpected Operation - " + e.getMessage());
				}
				instructionCounter++;
				break;
			
			case SimpletronConstants.MULTIPLY :
				accumulator *= Integer.parseInt( memory[ operand ] );
				instructionCounter++;
				break;

			case SimpletronConstants.BRANCH:
				instructionCounter = operand;
				break;

			case SimpletronConstants.BRANCHZERO:
				if (accumulator == 0)
					instructionCounter = operand;
				break;

			case SimpletronConstants.BRANCHNEG :
				if (accumulator < 0)
					instructionCounter = operand;
				break;

			case SimpletronConstants.HALT:
				System.out.println("*** Simpletron execution terminated ***");
				break;
		}
	}

	public static void dump(int operationCode, int operand) {
		String s = " ";
		println("REGISTERS:");
		System.out.printf("accumulator%13s%d\n", " ", accumulator);
		System.out.printf("instructionCounter%6s%d\n", " ", instructionCounter);
		System.out.printf("instructionRegisger%5s%d\n", " ", instructionRegister);
		System.out.printf("operationCode%11s%d\n", " ", operationCode);
		System.out.printf("operand%17s%d\n", " ", operand);

		println();

		println("MEMORY:");

		for (int i = 0; i < 10; i++) {
			System.out.printf("%7s%d", s, i);
		}
		println();
	}

	private static void print(String s) {
		System.out.print(s);
	}

	private static void println() {
		System.out.println();
	}

	private static void println(String s) {
		System.out.println(s);
	}
}
