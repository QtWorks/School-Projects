package exceptions;

public class EarlyTerminationException extends Exception {

	public EarlyTerminationException() {
	}

	public EarlyTerminationException(String arg0) {
		super(arg0);
	}

	public EarlyTerminationException(Throwable arg0) {
		super(arg0);

	}

	public EarlyTerminationException(String arg0, Throwable arg1) {
		super(arg0, arg1);

	}

	public EarlyTerminationException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);

	}

}
