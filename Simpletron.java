import java.util.Scanner;
import javax.swing.JOptionPane;

public class Simpletron {
	
	private String[] memory = new String[100];
	private int accumulator = 0;
	private int instructionCounter = 0;
	private int instructionRegister = 0;
	private String ans = "";
	
	public void startMessage() {
		System.out.println( "*** Welcome to Simpletron! ***" );
		System.out.println( "*** Please enter your program one instruction ***" );
		System.out.println( "*** (or data word) at a time. I will display ***" );
		System.out.println( "*** the location number and a question mark ***" );
		System.out.println( "*** You then type the word for that location. ***" );
		System.out.println( "*** Type -99999 to stop entering your program. ***" );
	}

	public void prompt() {
		Scanner input = new Scanner( System.in );

		while( !(memory[ instructionCounter ].equals("-99999")) ) {
			System.out.printf( "%d%d ? ", instructionCounter, instructionCounter );
			memory[ instructionCounter ] = input.nextLine();

			instructionRegister = Integer.parseInt( memory[ instructionCounter ] );
			determineOperationCode( instructionRegister );

			instructionCounter ++;
			System.out.println();
		}

		System.out.println( "*** Program loading Completed ***" );	
		System.out.println( "*** Program execution begins ***" );
	}

	public void determineOperationCode( int instructionRegister  ) {
		int operationCode = instructionRegister / 100;
		int operand = instructionRegister % 100;

		switch( operationCode ) {
			case SimpletronConstants.READ :
				int number = 
					Integer.parseInt( JOptionPane.showInputDialog("Please Enter the number") );
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
		}
	}
}
