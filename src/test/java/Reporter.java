import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

import java.util.List;
import java.util.Map;

public class Reporter implements IReporter {
    @Override
    public void generateReport(List<XmlSuite> xmlSuiteList, List<ISuite> suiteList, String outputDirectory) {
        for (ISuite suite : suiteList) {
            var suiteName = suite.getName();
            Map<String, ISuiteResult> suiteResultMap = suite.getResults();
            for (ISuiteResult suiteResult : suiteResultMap.values()) {
                ITestContext context = suiteResult.getTestContext();
                System.out.println("Passed tests for suite '" + suiteName +
                        "' is:" + context.getPassedTests().getAllResults().size());
                System.out.println("Failed tests for suite '" + suiteName +
                        "' is:" + context.getFailedTests().getAllResults().size());
                System.out.println("Skipped tests for suite '" + suiteName +
                        "' is:" + context.getSkippedTests().getAllResults().size());
            }
        }
    }
}
