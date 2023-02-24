package Computergrafik.Engine.Core.CatchErrors;

import static com.jogamp.opengl.GL.GL_NO_ERROR;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.glu.GLU;

/**
 * 
 * Module to catch GL Errors. checkOpenGLError() can be called after every openGL method to check wether it generated an error or not. 
 * 
 * VM run config to enable jogl composable pipeline :
 * 
 * -Djogl.debug.DebugGL
 * -Djogl.debug.TraceGL
 *
 *	@author Simon Weck
 */
public class CatchGLErrors {

	/**
	 * Prints the OpenGL errors that occurred 
	 * 
	 */
	public static void checkOpenGLError(){	
		GL4 gl = (GL4) GLContext.getCurrentGL();
		GLU glu = new GLU();
		int glError = gl.glGetError(); //saves the error flag into an int.
		/*
		The following errors are currently defined:
		
		GL_NO_ERROR
		No error has been recorded. The value of this symbolic constant is guaranteed to be 0.

		GL_INVALID_ENUM
		An unacceptable value is specified for an enumerated argument. The offending command is ignored and has no other side effect than to set the error flag.

		GL_INVALID_VALUE
		A numeric argument is out of range. The offending command is ignored and has no other side effect than to set the error flag.

		GL_INVALID_OPERATION
		The specified operation is not allowed in the current state. The offending command is ignored and has no other side effect than to set the error flag.

		GL_INVALID_FRAMEBUFFER_OPERATION
		The framebuffer object is not complete. The offending command is ignored and has no other side effect than to set the error flag.

		GL_OUT_OF_MEMORY
		There is not enough memory left to execute the command. The state of the GL is undefined, except for the state of the error flags, after this error is recorded.

		GL_STACK_UNDERFLOW
		An attempt has been made to perform an operation that would cause an internal stack to underflow.

		GL_STACK_OVERFLOW
		An attempt has been made to perform an operation that would cause an internal stack to overflow.
		*/
		
		/*
		To allow for distributed implementations, there may be several error flags. 
		If any single error flag has recorded an error, the value of that flag is returned and that flag is reset to GL_NO_ERROR when glGetError is called. 
		If more than one flag has recorded an error, glGetError returns and clears an arbitrary error flag value. 
		Thus, glGetError should always be called in a loop, until it returns GL_NO_ERROR, if all error flags are to be reset.
		*/
		while (glError != GL_NO_ERROR){	//loop all possible errors
			System.err.println("glError: " + glu.gluErrorString(glError)); //print error 
			glError = gl.glGetError(); //gets next error flag
		}
	}
}
