package edu.cpp.cs585.auto_grade_reporting_dbdemo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

/**
 * LocalDB represents DynamoDB as it is running locally.  DynamoDB
 * uses port 8000.
 *
 * @author delin
 *
 */

public class LocalDB {

    private AmazonDynamoDBClient client;
    private DynamoDB dynamoDB;

    public void setupConnection() {
        client = new AmazonDynamoDBClient()
                .withEndpoint("http://localhost:8000");

        dynamoDB = new DynamoDB(client);
    }

    public DynamoDB getDynamoDB() {
        return dynamoDB;
    }

}
