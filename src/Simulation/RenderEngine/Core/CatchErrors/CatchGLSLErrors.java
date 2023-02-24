package Simulation.RenderEngine.Core.CatchErrors;

import static com.jogamp.opengl.GL2ES2.GL_INFO_LOG_LENGTH;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

/** 
 * 
 * Modules to catch GLSL code errors and printing out information logs about errors
 * @author Simon Weck
 */
public class CatchGLSLErrors {

	/**
	 * Print the error log from a shader after an error occurred
	 * 
	 * @param shader
	 * 			-reference to the shader that is responsible for the error
	 */
	public static void printShaderLog(int shader){	
		GL4 gl = (GL4) GLContext.getCurrentGL();
		int[] infoLogLength = new int[1]; //holding the code that has information about the length of the information log of the shader 		
		byte[] infoLog = null; //holds the information log 
		int[] stringLengthOfInfoLog = new int[1]; //holding the length of the String that will be saved into the log array
		
		gl.glGetShaderiv(shader, GL_INFO_LOG_LENGTH, infoLogLength, 0); //determine the length of the shader compilation log (code to the length will be saved into the arrayLogLength array)
		if (infoLogLength[0] > 0){	//If shader has no information log, a value of 0 is returned.
			infoLog = new byte[infoLogLength[0]];//declaring length of byte array that will hold the information log
			gl.glGetShaderInfoLog(shader, infoLogLength[0], stringLengthOfInfoLog, 0, infoLog, 0); //saves the information log into an array (reference to the shader//length of the array to store the info log//saves length of the string returned into the log array//??//array to save the log//??)
			System.out.println("Shader Info Log: ");
			for (int i = 0; i < infoLog.length; i++){	
				System.out.print((char) infoLog[i]); //print information log
			}
		}
	}
	
	/**
	 * Print the error log from a program after an error occurred
	 * 
	 * @param program
	 * 			-reference to the program that is responsible for the error
	 */
	public static void printProgramLog(int program){	
		GL4 gl = (GL4) GLContext.getCurrentGL();
		int[] infoLogLength = new int[1];//holding the code that has information about the length of the information log of the shader 
		byte[] infoLog = null; //holds the information log 
		int[] stringLengthOfInfoLog = new int[1]; //holding the length of the String that will be saved into the log array
		
		gl.glGetProgramiv(program, GL_INFO_LOG_LENGTH, infoLogLength, 0); //determine the length of the shader compilation log (code to the length will be saved into the arrayLogLength array)
		if (infoLogLength[0] > 0){	//If shader has no information log, a value of 0 is returned.
			infoLog = new byte[infoLogLength[0]]; //declaring length of byte array that will hold the information log
			gl.glGetProgramInfoLog(program, infoLogLength[0], stringLengthOfInfoLog, 0, infoLog, 0); //saves the information log into an array (reference to the shader//length of the array to store the info log//saves length of the string returned into the log array//??//array to save the log//??)
			System.out.println("Program Info Log: ");
			for (int i = 0; i < infoLog.length; i++){	
				System.out.print((char) infoLog[i]); //print information log
			}
		}
	}
	
}
