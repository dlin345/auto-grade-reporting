Overview
--------

Java annotation-based automated grade reporting.

It applies the AWS SDK for Java to the downloadable version of Amazon DynamoDB (`http://docs.aws.amazon.com/amazondynamodb/latest/gettingstartedguide/GettingStarted.Java.html`).

Environment Setup
-----------------
1. Install the Eclipse 4+ (`http://www.eclipse.org/`), and make sure Maven plugin is available.
2. (Optional) The Maven plugin is not availble in your Eclipse, install it using the update site (`http://www.eclipse.org/m2e/download/`).
3. Install the latest Maven command-line tool (`http://maven.apache.org/download.cgi`).

Importing the Project into Eclipse
----------------------------------------
1. File -> Import -> Maven -> Existing Maven Projects.
2. Select the directory containing the pom.xml file.
3. Finish.

Building the Project for the First Time
----------------------------------------
1. Right-click on the project root folder -> Maven -> Update Project.

Configure AWS SDK Credential
----------------------------------------
Setup the AWS SDK for Java by performing the following:
  1. Install a the AWS Toolkit for Eclipse.  Please follow the instructions at: `https://aws.amazon.com/eclipse/`.
  2. Install the AWS SDK.
  3. Setup AWS security credentials for use with the SDK for Java.

For instructions, see 'Getting Started' in the *AWS SDK for Java Developer Guide* at: `http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/`.

Configure Downloadable DynamoDB
----------------------------------------
1. Please follow the instructions to download DynamoDB locally: `http://docs.aws.amazon.com/amazondynamodb/latest/gettingstartedguide/GettingStarted.JsShell.html#GettingStarted.JsShell.Prereqs.Download`.

Running DynamoDB Locally
----------------------------------------
> To start DynamoDB, open a command prompt window, navigate to the directory where you extracted DynamoDBLocal.jar, and enter the following command:

`java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb`

> **Note**

> DynamoDB uses port 8000 by default. If port 8000 is unavailable, this command throws an exception. You can use the -port option to specify a different port number. For a complete list of DynamoDB runtime options, including -port , type this command:

`java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -help`

> If you need to stop DynamoDB, you can do so by pressing Ctrl-C.

Reference: `http://docs.aws.amazon.com/amazondynamodb/latest/gettingstartedguide/GettingStarted.JsShell.html#GettingStarted.JsShell.Prereqs.Download`

Note
----------------------------------------
1. The project has been configured to use JDK 7. If you use other versions installed, please modify the pom.xml file. However, JDK 7+ (or 8) is recommended.

Resources
----------------------------------------
To learn the core techniques used in this program, please read:

1. Getting Started with Amazon DynamoDB at: `http://docs.aws.amazon.com/amazondynamodb/latest/gettingstartedguide/Welcome.html#quick-intro`.
2. Java and DynamoDB at: `http://docs.aws.amazon.com/amazondynamodb/latest/gettingstartedguide/GettingStarted.Java.html`.
3. The Javadocs for the AWS SDK are available at: `http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/`.
