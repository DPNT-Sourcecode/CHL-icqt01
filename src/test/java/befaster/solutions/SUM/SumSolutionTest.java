package befaster.solutions.SUM;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class SumSolutionTest {
    private SumSolution sum;

    @Before
    public void setUp() {

        sum = new SumSolution();
    }

    @Test
    public void compute_sum() {
        assertThat(sum.compute(1, 1), equalTo(2));
    }

    @Test
    public void computeSumOfNegatives() {
        assertEquals(-5, sum.compute(-2, -3));
    }

    @Test
    public void computerSumOfPositiveAndNegative() {
        assertEquals(-1, sum.compute(99, -100));
    }
}


