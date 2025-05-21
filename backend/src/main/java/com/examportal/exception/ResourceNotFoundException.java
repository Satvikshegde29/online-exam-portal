<<<<<<< HEAD
// filepath: c:\Users\2407106\project\online-exam-portal\backend\src\main\java\com\examportal\exception\ResourceNotFoundException.java
package com.examportal.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
=======
package com.examportal.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
>>>>>>> cce088a159e2d9090e776ba3c158af7c9d195289
