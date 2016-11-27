package edu.cpp.cs585.auto_grade_reporting_dbdemo;

import java.util.Iterator;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

/**
 * Queries local DynamoDB table at port 8000, if the table
 * exists.  A query is made on table 'Grade-Results' for a
 * student with name 'student1'.
 *
 * Query is made on a global secondary index with a student's
 * name as the partition key and the time stamp as the sort key.
 *
 * @author delin
 *
 */

public class QueryGSITable {

    public static void main(String[] args) {

        AmazonDynamoDBClient client = new AmazonDynamoDBClient()
                .withEndpoint("http://localhost:8000");

        DynamoDB dynamoDB = new DynamoDB(client);

        String tableName = "Grade-Results";
        String studentName = "student1";

        Table table = dynamoDB.getTable(tableName);
        Index index = table.getIndex("studentIndex"); // GSI defined in CreateGSITable.java

        QuerySpec querySpec = new QuerySpec()
            .withKeyConditionExpression("studentName = :name")
            .withValueMap(new ValueMap()
                    .withString(":name", studentName));

        ItemCollection<QueryOutcome> items = index.query(querySpec);
        Iterator<Item> iter = items.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next().toJSONPretty());
        }
    }

}
