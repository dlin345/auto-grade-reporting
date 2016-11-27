package edu.cpp.cs585.auto_grade_reporting_dbdemo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

/**
 * Deletes a DynamoDB table locally at port 8000, if the table
 * exists.  The table to be deleted has name 'Grade-Results'.
 *
 * @author delin
 *
 */

public class DeleteTable {

    public static void main(String[] args) throws Exception {

        AmazonDynamoDBClient client = new AmazonDynamoDBClient()
            .withEndpoint("http://localhost:8000");

        DynamoDB dynamoDB = new DynamoDB(client);

        String tableName = "Grade-Results";

        Table table = dynamoDB.getTable(tableName);

        try {
            System.out.println("Attempting to delete table.  Please wait...");
            table.delete();
            table.waitForDelete();
            System.out.print("Success.");

        } catch (Exception e) {
            System.err.println("Unable to delete table: " + tableName);
            System.err.println(e.getMessage());
        }
    }

}
