package edu.cpp.cs585.auto_grade_reporting_dbdemo;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

/**
 * Scans local DynamoDB table at port 8000, if the table
 * exists.  A scan is made on table 'Grade-Results' for
 * items that pass the filter expression.
 *
 * The scan returns all items that meet the restrictions
 * of the filter expression.  The attributes to retrieve
 * for items in the scan results are specified by the
 * projection expression.
 *
 * @author delin
 *
 */

public class ScanGSITable {

    public static void main(String[] args) {

        AmazonDynamoDBClient client = new AmazonDynamoDBClient()
                .withEndpoint("http://localhost:8000");

        String tableName = "Grade-Results";

        // filter returns all items with time stamp beginning from 2016 and any student name
        String deadline = "2016";
        String name = " ";
        String filterExpression = "#ts >= :deadline and studentName >= :studentName";

        // attributes to retrieve for items in the scan results
        String attributesToView = "studentName, #ts, passedMethods, declaringClass";

        Map<String, AttributeValue> expressionAttributeValues =
                new HashMap<String, AttributeValue>();
        expressionAttributeValues.put(":studentName", new AttributeValue().withS(name));
        expressionAttributeValues.put(":deadline", new AttributeValue().withS(deadline));

        // placeholder for DynamoDB reserved word
        Map<String, String> expressionAttributeNames =
                new HashMap<String, String>();
        expressionAttributeNames.put("#ts", "timeStamp");

        ScanRequest scanRequest = new ScanRequest()
                .withTableName(tableName)
                .withExpressionAttributeValues(expressionAttributeValues)
                .withExpressionAttributeNames(expressionAttributeNames) // DynamoDB reserved word
                .withProjectionExpression(attributesToView)
                .withFilterExpression(filterExpression);

        ScanResult result = client.scan(scanRequest);

        // print scan results to console
        for (Map<String, AttributeValue> item : result.getItems()){
            for (String it : item.keySet()) {
                System.out.println(it + " : " + item.get(it));
            }
            System.out.println();
        }
    }

}
