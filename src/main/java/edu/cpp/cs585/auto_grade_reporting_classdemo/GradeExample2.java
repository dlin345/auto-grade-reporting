package edu.cpp.cs585.auto_grade_reporting_classdemo;

import edu.cpp.cs585.auto_grade_reporting_annotation.Grade;

/**
 * Sample Java class to be graded.
 *
 * @author delin
 *
 */

public class GradeExample2 {

    @Grade
    public void failedTest1() throws RuntimeException {
        if (true) {
            throw new RuntimeException("This test always failed");
        }
    }

    @Grade(enabled = false)
    public void ignoredTest1() {
        // do nothing, this test always passes but is ignored
    }

    @Grade(enabled = true)
    public void failedTest2() throws RuntimeException {
        if (true) {
            throw new RuntimeException("This test always failed");
        }
    }

}
