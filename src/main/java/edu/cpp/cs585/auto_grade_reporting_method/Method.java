package edu.cpp.cs585.auto_grade_reporting_method;

/**
 * Method abstract class contains information regarding a
 * Java method that has been annotated with @Grade or @Test.
 * This abstract class includes the name of the class that
 * declared the method and the name of the method.
 *
 * @author delin
 *
 */

public abstract class Method {

    protected String declaringClass;
    protected String methodName;

    public Method(String declaringClass, String methodName) {
        this.declaringClass = declaringClass;
        this.methodName = methodName;
    }

    public String getDeclaringClass() {
        return declaringClass;
    }

    public String getMethodName() {
        return methodName;
    }
}
