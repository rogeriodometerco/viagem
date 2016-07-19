package exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class AppPersistenceException extends AppException {

	private static final long serialVersionUID = 1840487173798113232L;

	public AppPersistenceException(String arg0) {
		super(arg0);
	}

	public AppPersistenceException(Throwable arg0) {
		super(arg0);
	}

	public AppPersistenceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
