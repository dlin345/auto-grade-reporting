package edu.cpp.cs585.auto_grade_reporting_classdemo;

import edu.cpp.cs585.auto_grade_reporting_annotation.GradeResults;
import edu.cpp.cs585.auto_grade_reporting_annotation.Results;
import edu.cpp.cs585.auto_grade_reporting_dbdemo.DB;
import edu.cpp.cs585.auto_grade_reporting_dbdemo.LocalDB;

/**
 * This driver sets up a connection to DynamoDB locally and
 * grades the methods of the specified Java class with
 * the @Grade annotation.
 *
 * Assumes Grade-Results table has already been created
 * in DynamoDB locally.
 *
 * @author delin
 *
 */

public class Driver {

    public static void main(String[] args) throws Exception {

        /*
         * To run DynamoDB locally, navigate to directory where
         * DynamoDBLocal.jar was extracted and enter the following command
         *
         * java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb
         */

        // Java class to grade
        Class<GradeExample> obj = GradeExample.class;

        DB db = new LocalDB();
        ((LocalDB) db).setupConnection();

        String dbTableName = "Grade-Results";
        String studentName = "student1";

        Results gr = new GradeResults(studentName, obj, dbTableName, db);

        // process methods with annotation @Grade
        System.out.println("Grading...");
        ((GradeResults) gr).grade();

        // print graded results to console
        System.out.println(gr.toString());

    }
}

