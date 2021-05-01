package pescador.exceptions;

public class HttpStatusCodeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public HttpStatusCodeException(String msg) {
		super(msg);
	}

}
