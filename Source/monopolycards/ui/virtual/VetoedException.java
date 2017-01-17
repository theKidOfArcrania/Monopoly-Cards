package monopolycards.ui.virtual;

public class VetoedException extends RuntimeException
{

	private static final long serialVersionUID = -833547115871003661L;

	public VetoedException()
	{
	}

	public VetoedException(String message)
	{
		super(message);
	}

	public VetoedException(Throwable cause)
	{
		super(cause);
	}

	public VetoedException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
