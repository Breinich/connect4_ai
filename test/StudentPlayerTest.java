import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StudentPlayerTest {

    final int[] boardSize = new int[] {6, 7};
    final int nToConnect = 4;
    StudentPlayer sp;

    @Before
    public void init(){
        sp = new StudentPlayer(1, boardSize, nToConnect);
    }

    int[][] b1 = {
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0}
    };
    int[][] b2 = {
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 0, 0, 0}
    };

    @Test
    public void TestCalcValue(){
        Assert.assertTrue(sp.calcValue(b1, 1) < sp.calcValue(b2, 1));
    }


}
