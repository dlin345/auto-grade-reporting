package edu.cpp.cs585.auto_grade_reporting_method;

/**
 * IgnoredMethod extends abstract class Method.  This class
 * represents a Java method that has been annotated with
 * @Grade or @Test, and that has been disabled.
 *
 * @author delin
 *
 */

public class IgnoredMethod extends Method {

    public IgnoredMethod(String declaringClass, String methodName) {
        super(declaringClass, methodName);
    }

}
