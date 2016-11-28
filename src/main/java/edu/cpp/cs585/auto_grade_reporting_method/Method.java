package edu.cpp.cs585.auto_grade_reporting_method;

/**
 * Method abstract class contains information regarding a
 * Java method that has been annotated with @Grade.
 * This abstract class includes the name of the class that
 * declared the method and the name of the method.
 *
 * @author delin
 *
 */

public abstract class Method {

    protected String methodName;

    public Method(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

}
