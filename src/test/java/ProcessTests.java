import org.junit.*;
import system.Process;
import java.net.*;
import java.io.File;

public class ProcessTests {
  @Test
  public void testFromFile() throws URISyntaxException {
    String loc = getClass().getResource("process1.txt").getFile();
    System.out.println(loc);
    Process p = Process.fromFile(loc);

    Assert.assertTrue(p != null);
  }
}