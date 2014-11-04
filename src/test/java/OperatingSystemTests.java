import org.junit.*;
import java.net.*;
import system.*;
import system.Process;
import scheduling.*;
import java.util.*;

public class OperatingSystemTests {
  private OperatingSystem os = new OperatingSystem(new RoundRobinScheduler(), new FixedAllocationStrategy(), new LRUPageReplacement());

  @Test 
  public void testRun() {

  }

  @Test
  public void testIsPaged() {
    Queue<Integer> ins = new LinkedList<Integer>();
    ins.offer(1);
    ins.offer(2);
    ins.offer(3);
    ins.offer(1);

    Process process = new Process(ins, 1);

    List<Process> processList = new ArrayList<Process>();
    processList.add(process);

    os.initFrames(processList);
  }

  @Test
  public void testLoadInstruction() {

  }

  @Test
  public void testTick() {

  }
}