/**
 * COMP2240 Assignment 3
 * @author Simon Hartcher
 * @studentno c3185790
 */

import scheduling.*;
import system.*;
import system.Process;

import java.util.ArrayList;
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
      List<Process> processesClone = new ArrayList<>();
      for (Process p: processes) processesClone.add((Process)p.clone());

      //create the scheduler
      Scheduler scheduler = new RoundRobinScheduler();
      AllocationStrategy strategy = new FixedAllocationStrategy(OperatingSystem.MAX_FRAMES, AllocationStrategy.PageReplacement.LRU);
      System.out.println();
      System.out.println("LRU - Fixed:");
      OperatingSystem os = new OperatingSystem(scheduler, strategy);
      //start os
      os.run(processesClone);
      System.out.print(os.report());

      processesClone = new ArrayList<>();
      for (Process p: processes) processesClone.add((Process)p.clone());

      scheduler = new RoundRobinScheduler();
      strategy = new VariableAllocationStrategy(OperatingSystem.MAX_FRAMES, AllocationStrategy.PageReplacement.LRU);
      System.out.println();
      System.out.println("LRU - Variable:");
      os = new OperatingSystem(scheduler, strategy);
      os.run(processesClone);
      System.out.print(os.report());

      processesClone = new ArrayList<>();
      for (Process p: processes) processesClone.add((Process)p.clone());

      scheduler = new RoundRobinScheduler();
      strategy = new FixedAllocationStrategy(OperatingSystem.MAX_FRAMES, AllocationStrategy.PageReplacement.Clock);
      System.out.println();
      System.out.println("Clock - Fixed:");
      os = new OperatingSystem(scheduler, strategy);
      os.run(processesClone);
      System.out.print(os.report());

      processesClone = new ArrayList<>();
      for (Process p: processes) processesClone.add((Process)p.clone());

      scheduler = new RoundRobinScheduler();
      strategy = new VariableAllocationStrategy(OperatingSystem.MAX_FRAMES, AllocationStrategy.PageReplacement.Clock);
      System.out.println();
      System.out.println("Clock - Variable:");
      os = new OperatingSystem(scheduler, strategy);
      os.run(processesClone);
      System.out.print(os.report());
      System.out.println();
    }
  }
}