package system;

import scheduling.*;
import java.util.*;

public class OperatingSystem {
  public static final int MAX_FRAMES = 20;
  public static final int PAGE_LOAD_TIME = 7;

  private List<ProcessFrame> frames = new ArrayList<ProcessFrame>();
  private final Scheduler scheduler;
  private final AllocationStrategy strategy;
  private final PageReplacementAlgorithm algorithm;

  public OperatingSystem(Scheduler scheduler, AllocationStrategy strategy, PageReplacementAlgorithm algorithm) {
    this.scheduler = scheduler;
    this.scheduler.setOperatingSystem(this);
    this.strategy = strategy;
    this.strategy.setFrames(frames);
    this.algorithm = algorithm;
  }

  public void run(List<system.Process> processes) {
    this.initFrames(processes);
    this.strategy.allocateFrames(MAX_FRAMES);
    this.scheduler.start(processes);
    while (this.scheduler.isRunning()) {
      this.tick();
    }
  }

  public void initFrames(List<Process> processes) {
    for (Process p: processes) {
      frames.add(new ProcessFrame(p));
    }
//    System.out.println(String.format("Init %d process frames.", frames.size()));
  }

  public boolean isPaged(Process p, int i) {
    ProcessFrame pf = frames.get(frames.indexOf(p));
    return pf.instructionLoaded(i);
  }

  public void loadInstruction(Process p, int i) {
    //get the process frame for the given process
    ProcessFrame processFrame = frames.get(frames.indexOf(p));

    //check number of pages allocation for process
    if (!processFrame.hasPageFor(i)) {
      if (processFrame.hasMaxPages())
        algorithm.removePage(processFrame);

      Page page = new Page(p, i);
      page.setTicksTillLoaded(PAGE_LOAD_TIME);

      processFrame.addPage(page);
    }
  }

  public void tick() {
    this.updatePages();

    this.scheduler.tick();
  }

  /**
   * Event handler that is thrown when an instruction is executed
   * on a process
   * @param process The process that executed the instruction
   * @param instruction The instruction that was executed
   * @param currentTick The current clock tick number
   */
  public void onExecuteInstruction(Process process, int instruction, int currentTick) {
    //get the process frame
    ProcessFrame pf = frames.get(frames.indexOf(process));

    //get the page from the given instruction
    Page page = pf.getPageFor(instruction);

    //update tick last used
    page.setTickLastUsed(currentTick);
  }

  /**
   * Update any pages that are loading by decrementing
   * their ticksTillLoaded value
   */
  private void updatePages() {
    for (ProcessFrame pf: this.frames) {
      for (Page p: pf.getPages()) {
        if (!p.isLoaded()) {
          int ticks = p.getTicksTillLoaded() - 1;
          p.setTicksTillLoaded(ticks);

          //check if now loaded
          if (p.isLoaded()) {
            //update process state
            p.getProcess().setState(Process.ProcessState.Ready);
          }
        }
      }
    }
  }

  public String report() {
    String result = "";
    for (ProcessFrame pf: this.frames) {
      Process p = pf.getProcess();
      String heading = String.format("Process %s: %d page faults, completed in %d ticks\r\n", p, p.getPageFaults().size(), p.getFinishTime());
      result += heading;
      for (int i = 1; i < heading.length() - 1; i++) {
        result += "=";
      }
      result += "\r\n";
      for (PageFault pageFault: p.getPageFaults()) {
        result += String.format("\t\tPage Fault @ Tick #%d\r\n", pageFault.getTick());
      }
    }

    return result;
  }

}