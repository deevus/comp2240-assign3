import scheduling.*;
import system.*;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Assignment3 {
  private static final Logger logger = Logger.getLogger("Assignment3");

  public static void main(String[] args) {
    //load processes from file
    List<system.Process> processes = new LinkedList<system.Process>();
    for (String path: args) {
      system.Process p = system.Process.fromFile(path);
      if (p != null)
        processes.add(p);
    }
    logger.info("Loaded " + processes.size() + " process(es) from file");

    if (!processes.isEmpty()) {
      //create the scheduler
      Scheduler scheduler = new RoundRobinScheduler();

      //start os
      AllocationStrategy strategy = new FixedAllocationStrategy();
      OperatingSystem os = new OperatingSystem(scheduler, strategy);
      os.run(processes);
    }
  }
}