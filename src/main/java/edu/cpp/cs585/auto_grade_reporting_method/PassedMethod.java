package edu.cpp.cs585.auto_grade_reporting_method;

/**
 * PassedMethod extends abstract class Method.  This class
 * represents a Java method that has been annotated with
 * @Grade, and that has been successfully executed
 * without throwing any Java exceptions.
 *
 * @author delin
 *
 */

public class PassedMethod extends Method {

    public PassedMethod(String methodName) {
        super(methodName);
    }

}
