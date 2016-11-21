package edu.cpp.cs585.auto_grade_reporting_method;

/**
 * FailedMethod extends abstract class Method.  This class
 * represents a Java method that has been annotated with
 * @Grade or @Test, and that has not been successfully executed.
 * This class includes the Throwable object that caused the
 * Java error or exception that was thrown in the method execution.
 *
 * @author delin
 *
 */

public class FailedMethod extends Method {

    private Throwable thrown;

    public FailedMethod(String declaringClass, String methodName, Throwable thrown) {
        super(declaringClass, methodName);

        this.thrown = thrown;
    }

    public String getCause() {
        return thrown.getCause().toString();
    }
}
