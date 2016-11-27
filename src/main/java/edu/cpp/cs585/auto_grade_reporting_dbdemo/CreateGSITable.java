package edu.cpp.cs585.auto_grade_reporting_dbdemo;

import java.util.ArrayList;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;

/**
 * Creates a DynamoDB table locally at port 8000, if table does not
 * already exist.  Table has name 'Grade-Results' with a student's
 * name as the partition key and the time stamp as the sort key.
 *
 * A global secondary index is defined to allow queries using
 * a student's name.  A query using this index returns all past
 * results of graded methods which are sorted by time stamp.
 *
 * @author delin
 *
 */

public class CreateGSITable {

    public static void main(String[] args) throws Exception {

        AmazonDynamoDBClient client = new AmazonDynamoDBClient()
            .withEndpoint("http://localhost:8000");

        DynamoDB dynamoDB = new DynamoDB(client);

        String tableName = "Grade-Results";

        // Attribute definitions
        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition()
            .withAttributeName("studentName")
            .withAttributeType("S"));
        attributeDefinitions.add(new AttributeDefinition()
            .withAttributeName("timeStamp")
            .withAttributeType("S"));

        // table key schema
        ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<KeySchemaElement>();
        tableKeySchema.add(new KeySchemaElement()
            .withAttributeName("studentName")
            .withKeyType(KeyType.HASH));  // partition key
        tableKeySchema.add(new KeySchemaElement()
            .withAttributeName("timeStamp")
            .withKeyType(KeyType.RANGE));  // sort key

        // global secondary index
        GlobalSecondaryIndex studentIndex = new GlobalSecondaryIndex()
            .withIndexName("studentIndex")
            .withProvisionedThroughput(new ProvisionedThroughput()
                .withReadCapacityUnits((long) 10)
                .withWriteCapacityUnits((long) 1))
                .withProjection(new Projection().withProjectionType(ProjectionType.ALL));

        ArrayList<KeySchemaElement> indexKeySchema = new ArrayList<KeySchemaElement>();
        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName("studentName")
                .withKeyType(KeyType.HASH));  // partition key
        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName("timeStamp")
                .withKeyType(KeyType.RANGE));  // sort key

        studentIndex.setKeySchema(indexKeySchema);

        CreateTableRequest createTableRequest = new CreateTableRequest()
            .withTableName(tableName)
            .withProvisionedThroughput(new ProvisionedThroughput()
                .withReadCapacityUnits((long) 5)
                .withWriteCapacityUnits((long) 1))
            .withAttributeDefinitions(attributeDefinitions)
            .withKeySchema(tableKeySchema)
            .withGlobalSecondaryIndexes(studentIndex);

        try {
            System.out.println("Attempting to create table.  Please wait...");
            dynamoDB.createTable(createTableRequest);
            System.out.println("Successfully created table: " + tableName);

        } catch (ResourceInUseException e) {
            System.err.println("Unable to create table: " + tableName);
            System.err.println(e.getMessage());
        }
    }

}
