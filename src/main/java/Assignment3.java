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
//    System.out.println("Loaded " + processes.size() + " process(es) from file");

    if (!processes.isEmpty()) {
      //create the scheduler

      //start os
      Scheduler scheduler = new RoundRobinScheduler();
      AllocationStrategy strategy = new FixedAllocationStrategy();
      PageReplacementAlgorithm algorithm = new LRUPageReplacement();

      System.out.println();
      System.out.println("====================================");
      System.out.println("Fixed Allocation / Local Replacement");
      System.out.println("--------LRU Page Replacement--------");
      System.out.println("====================================");
      OperatingSystem os = new OperatingSystem(scheduler, strategy, algorithm);
      os.run(processes);

      System.out.print(os.report());
    }
  }
}