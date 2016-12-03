package edu.cpp.cs585.auto_grade_reporting_annotation;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cpp.cs585.auto_grade_reporting_dbdemo.DB;
import edu.cpp.cs585.auto_grade_reporting_method.FailedMethod;
import edu.cpp.cs585.auto_grade_reporting_method.IgnoredMethod;
import edu.cpp.cs585.auto_grade_reporting_method.Method;
import edu.cpp.cs585.auto_grade_reporting_method.PassedMethod;

/**
 * This abstract class stores the results of a Java class whose methods
 * have been marked with a custom Java annotation.
 *
 * @author delin
 *
 */

public abstract class Results {

    protected final static String PASSED_METHODS = "passedMethods";
    protected final static String FAILED_METHODS = "failedMethods";
    protected final static String IGNORED_METHODS = "ignoredMethods";
    protected final static String STUDENT_NAME = "studentName";
    protected final static String TIME_STAMP = "timeStamp";
    protected final static String DECLARING_CLASS = "declaringClass";

    protected Class declaringClass;

    protected String studentName;
    protected String timeStamp;

    protected List<Method> passedMethods;
    protected List<Method> ignoredMethods;
    protected List<Method> failedMethods;

    protected String jsonFileName;
    protected String dbTableName;
    protected DB db;

    public Results(String studentName, Class classType, String dbTableName, DB db) {
        this.studentName = studentName;
        this.timeStamp = new Timestamp(System.currentTimeMillis()).toString(); // new Date().toString();
        this.declaringClass = classType;
        this.dbTableName = dbTableName;
        this.db = db;

        passedMethods = new ArrayList<Method>();
        ignoredMethods = new ArrayList<Method>();
        failedMethods = new ArrayList<Method>();
    }

    protected abstract void sendToDatabase();

    /**
     * Adds specified method to the list of methods that have
     * been successfully invoked.
     */
    public void addPassedMethod(java.lang.reflect.Method method) {
        Method m = new PassedMethod(method.getName());
        passedMethods.add(m);
    }

    /**
     * Returns the list of methods that have been successfully
     * invoked.
     */
    public List<Method> getPassedMethods() {
        return passedMethods;
    }

    /**
     * Adds specified method to the list of methods that
     * have been ignored.
     */
    public void addIgnoredMethod(java.lang.reflect.Method method) {
        Method m = new IgnoredMethod(method.getName());
        ignoredMethods.add(m);
    }

    /**
     * Returns the list of methods that have been ignored.
     */
    public List<Method> getIgnoredMethods() {
        return ignoredMethods;
    }

    /**
     * Adds specified method to the list of methods that have
     * failed.  The Java exception that caused the failure is
     * stored as a field in FailedMethod.
     */
    public void addFailedMethod(java.lang.reflect.Method method, Throwable ex) {
        Method m = new FailedMethod(method.getName(), ex);
        failedMethods.add(m);
    }

    /**
     * Returns the list of methods that have not been
     * successfully invoked.
     */
    public List<Method> getFailedMethods() {
        return failedMethods;
    }

    /**
     * Returns the name of the student whose results
     * are being stored.
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Returns the time stamp of when the results were
     * created.
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Returns the name of the Java class whose methods
     * are being recorded.
     */
    public Class getDeclaringClass() {
        return declaringClass;
    }

    /**
     * Saves this {@link Results2} to a JSON file locally.
     */
    protected void saveToJson() {

        ObjectMapper mapper = new ObjectMapper();

        //Object to JSON in file
        try {
            jsonFileName = this.getClass().getSimpleName() + "-" + studentName + ".json";
            File file = new File(jsonFileName);
            mapper.writeValue(file, this);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the contents of the specified local JSON file.
     */
    protected void readFromJson(String fileName) {
        ObjectMapper mapper = new ObjectMapper();

        //JSON from file to Object
        try {
            mapper.readValue(new File(fileName), Result.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the {@link Results2} to the console.
     */
    public String toString() {
        String output = "\n";

        output += "****************\n";
        output += "Results\n";
        output += "****************\n";
        output += "Declaring class: " + declaringClass.getName() + "\n";
        output += "Passed methods: \n";
        for (Method method : passedMethods) {
            output += "\t" + method.getMethodName() + "\n";
        }

        output += "\nFailed methods:\n";
        for (Method method : failedMethods) {
            output += "\t" + method.getMethodName()
                    + " - " + ((FailedMethod) method).getCause() + "\n";
        }

        output += "\nIgnored methods:\n";
        for (Method method : ignoredMethods) {
            output += "\t" + method.getMethodName() + "\n";
        }

        output += "\nResult : Total: " + (passedMethods.size() + failedMethods.size() + ignoredMethods.size())
                    + ", Passed: " + passedMethods.size()
                    + ", Failed: " + failedMethods.size()
                    + ", Ignored: " + ignoredMethods.size();

        return output;
    }
}
