import org.junit.*;
import system.Process;
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
}