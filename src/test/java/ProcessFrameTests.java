import org.junit.*;
import system.Process;
import system.*;
import java.net.*;

public class ProcessFrameTests {
  private String processFile = getClass().getResource("process1.txt").getFile();

  @Test
  public void testEqualsProcess() {
    Process p = Process.fromFile(processFile);
    ProcessFrame pf = new ProcessFrame(p);

    Assert.assertTrue(pf.equals(p));
    Assert.assertTrue(p.equals(pf));

    Process p2 = Process.fromFile(processFile);

    Assert.assertFalse(pf.equals(p2));
    Assert.assertFalse(p2.equals(pf));
  }

  @Test
  public void testEqualsPage() {
    Process p = Process.fromFile(processFile);
    ProcessFrame pf = new ProcessFrame(p);
    Page page = new Page(p, p.nextInstruction());

    pf.addPage(page);

    Assert.assertTrue(pf.equals(page));
    Assert.assertTrue(page.equals(pf));

    Page page2 = new Page(p, p.nextInstruction() + 1);
    Assert.assertFalse(pf.equals(page2));
    Assert.assertFalse(page2.equals(pf));
    Assert.assertFalse(page2.equals(page));
  }
}