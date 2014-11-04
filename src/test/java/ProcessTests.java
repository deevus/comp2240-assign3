import org.junit.*;
import system.Process;
import system.*;
import java.net.*;
import java.io.File;

public class ProcessTests {
  private String processFile = getClass().getResource("process1.txt").getFile();

  @Test
  public void testFromFile() throws URISyntaxException {
    Process p = Process.fromFile(processFile);

    Assert.assertTrue(p != null);
  }

  @Test 
  public void testDoWork() {
    Process p = Process.fromFile(processFile);

    while (!p.isDone()) {
      p.executeInstruction();
    }
  }

  @Test
  public void testEqualsProcessFrame() {
    Process p = Process.fromFile(processFile);
    ProcessFrame pf = new ProcessFrame(p);

    Assert.assertEquals(pf, p);
    Assert.assertEquals(p, pf);
  }
}