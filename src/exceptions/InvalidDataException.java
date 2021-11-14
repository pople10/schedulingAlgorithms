package exceptions;

import java.util.Arrays;

public class InvalidDataException extends Exception {

	public InvalidDataException(String msg)
	{
		super(msg);
	}

	@Override
	public String toString() {
		return this.getMessage();
	}
	
}
