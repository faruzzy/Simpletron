package com.deitel;
import java.util.InputMismatchException;
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
				println();
				return;
			}
		}
	}

	private static void determineOperationCode(int instructionRegister) {
		int operationCode = instructionRegister / 100;
		int operand = instructionRegister % 100;

		switch( operationCode ) {
			case SimpletronConstants.READ :
				int number = -1;
				boolean s;
				do {
					s = false;
					input = new Scanner(System.in);
					print("Enter an Integer: ");

					try {
						number = input.nextInt();
					} catch(InputMismatchException e) {
						s = true;
						println();
						System.err.println("Unexpected input, please try again!");
					}
				} while(s || number > 9999 || number < -999);
				println();

				memory[operand] = "" + number;
				instructionCounter++;
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
				memory[operand] = "" + accumulator;
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
				int divider;
				try {
					divider = Integer.parseInt(memory[operand]);
					accumulator /= divider;
				} catch(Exception e) {
					System.err.println("Unexpected Operation - " + e.getMessage());
					dump(operationCode, operand);
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
				println("*** Simpletron execution terminated ***");
				println();
				dump(operationCode, operand);
				break;
		}
	}

	private static void dump(int operationCode, int operand) {
		String s = " ";
		println("REGISTERS:");
		System.out.printf("accumulator%13s%d\n", " ", accumulator);
		System.out.printf("instructionCounter%6s%d\n", " ", instructionCounter);
		System.out.printf("instructionRegister%5s%d\n", " ", instructionRegister);
		System.out.printf("operationCode%11s%d\n", " ", operationCode);
		System.out.printf("operand%17s%d\n", " ", operand);

		println();

		println("MEMORY:");
		int max = 0;
		for (int i = 0; i < memory.length; i++) {
            if (memory[i].equals("4300")) {
				max = i;
				break;
			} else {
				System.out.printf("%7s%d", s, i);
			}
		}

		println();

		for (int i = 0; i < max; i++) {
			System.out.printf("%d0 ", i);
			int t = 0;
			for (int j = 1; j <= i + 1; j++) {
                if (j == 1) {
					System.out.printf("%5s", memory[j]);
				} else {
					System.out.printf("%8s", memory[j]);
				}
				t = j;
			}

			for (int k = t; k < max; k++) {
				if (t == 0) {
					System.out.printf("%6s", "0000");
				}
				System.out.printf("%8s", "0000");
			}

			println();
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
