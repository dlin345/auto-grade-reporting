package edu.cpp.cs585.auto_grade_reporting_classdemo;

import edu.cpp.cs585.auto_grade_reporting_annotation.Grade;

/**
 * Sample Java class to be graded.
 *
 * @author delin
 *
 */

public class GradeExample3 {

    @Grade
    public void passedTest1() throws RuntimeException {
        // do nothing, this test always passed
    }

    @Grade(enabled = false)
    public void ignoredTest1() {
        // do nothing, this test always passes but is ignored
    }

    @Grade(enabled = true)
    public void passedTest2() {
        // do nothing, this test always passed
    }

}
