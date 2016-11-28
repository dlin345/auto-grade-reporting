package edu.cpp.cs585.auto_grade_reporting_annotation;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cpp.cs585.auto_grade_reporting_dbdemo.DB;
import edu.cpp.cs585.auto_grade_reporting_dbdemo.LocalDB;

/**
 * This class extends abstract class Results and grades a
 * Java class whose methods have been marked with the custom
 * Java annotation @Grade.  The results of the annotated
 * methods are automatically sent to a database.
 *
 *  - methods that are annotated with @Grade and that have not thrown
 *  a Java exception when invoked are classified as passedMethods
 *  - methods that are annotated with @Grade and that have thrown
 *  a Java exception when invoked are classified as failedMethods
 *  - methods that are annotated with @Grade and that have not
 *  been enabled are classified as ignoredMethods
 *
 * @author delin
 *
 */

public class GradeResults extends Results {

    public GradeResults(String studentName, Class classType, String dbTablename, DB db) {
        super(studentName, classType, dbTablename, db);
    }

    /**
     * Invokes each method marked with @Grade annotation.  Each annotated
     * method is classified as either a PassedMethod, FailedMethod, or
     * IgnoredMethod.  The results are saved locally to a JSON file and then
     * sent to the database specified at the instantiation of this GradeResults
     * object.
     */
    public void grade() {
        for (java.lang.reflect.Method method : declaringClass.getDeclaredMethods()) {

            // if method is annotated with @Grade
            if (method.isAnnotationPresent(Grade.class)) {
                Annotation annotation = method.getAnnotation(Grade.class);
                Grade grade = (Grade) annotation;

                // if enabled = true (default)
                if (grade.enabled()) {

                    try {
                        method.invoke(declaringClass.newInstance());
                        this.addPassedMethod(method);
                    } catch (Throwable ex) {
                        this.addFailedMethod(method, ex);
                    }
                } else {
                    this.addIgnoredMethod(method);
                }
            }
        }

        saveToJson();
        System.out.println("Saved results to JSON file: " + jsonFileName);

        sendToDatabase();
    }

    /**
     * Sends the results that were saved to the JSON file to the database.
     */
    protected void sendToDatabase() {
        Table table = ((LocalDB) db).getDynamoDB().getTable(dbTableName);

        JsonParser parser;
        try {
            parser = new JsonFactory()
                    .createParser(new File(jsonFileName));

            JsonNode currentNode = new ObjectMapper().readTree(parser);
            String studentName = currentNode.path(STUDENT_NAME).asText();
            String timeStamp = currentNode.path(TIME_STAMP).asText();

            table.putItem(new Item()
                    .withPrimaryKey(STUDENT_NAME, studentName, TIME_STAMP, timeStamp)
                    .withJSON(DECLARING_CLASS, currentNode.path(DECLARING_CLASS).toString())
                    .withJSON(PASSED_METHODS, currentNode.path(PASSED_METHODS).toString())
                    .withJSON(FAILED_METHODS, currentNode.path(FAILED_METHODS).toString())
                    .withJSON(IGNORED_METHODS, currentNode.path(IGNORED_METHODS).toString()));

            System.out.println("\nSuccessfully added item to table: " + dbTableName);
            System.out.println("\tStudent name: " + studentName);
            System.out.println("\tTime stamp: " + timeStamp);

            parser.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unable to add : ");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
