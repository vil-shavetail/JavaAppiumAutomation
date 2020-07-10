import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass {

    @Test
    public void testGetLocalNumber() {
        Assert.assertEquals("The return value is not equal to 14",
                14, getLocalNumber());
    }

    @Test
    public void testGetClassNumber() {
        Assert.assertTrue("The return value is less or equal than 45",
                this.getClassNumber() > 45);
    }

    @Test
    public void testGetClassString() {
        Assert.assertTrue("The returned string doesn,t contain a \"Hello\" or \"hello\" substring.",
                this.getClassString().contains("Hello") || this.getClassString().contains("hello"));
    }
}