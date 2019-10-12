package befaster.solutions.HLO;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelloSolutionTest {

    private HelloSolution solutionUnderTest = new HelloSolution();

    @Test
    public void testHello() {
        assertEquals("Hello, John!", solutionUnderTest.hello("John"));
    }

}
