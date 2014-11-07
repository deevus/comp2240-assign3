package system;

import scheduling.*;
import java.util.*;

public class OperatingSystem {

  public static final int MAX_FRAMES = 20;
  public static final int PAGE_LOAD_TIME = 7;

  private List<ProcessFrame> frames = new ArrayList<ProcessFrame>();
  private final Scheduler scheduler;
  private final AllocationStrategy allocationStrategy;

  public OperatingSystem(Scheduler scheduler, AllocationStrategy strategy) {
    this.scheduler = scheduler;
    this.scheduler.setOperatingSystem(this);
    this.allocationStrategy = strategy;
    this.allocationStrategy.setFrames(frames);
  }

  public void run(List<system.Process> processes) {
    this.initFrames(processes);
    //this.allocationStrategy.allocatePages(MAX_FRAMES);
    this.scheduler.start(processes);
    while (this.scheduler.isRunning()) {
      this.tick();
    }
  }

  public void initFrames(List<Process> processes) {
    for (Process p: processes) {
      ProcessFrame pf = new ProcessFrame(p);
      p.setProcessFrame(pf);
      frames.add(pf);
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
      allocationStrategy.allocatePages(processFrame);

      Page page = new Page(p, i);
      page.setTicksTillLoaded(PAGE_LOAD_TIME);

      processFrame.addPage(page);
    }
  }

  public void tick() {
    this.updatePages(this.scheduler.getCurrentTick());

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
  private void updatePages(int currentTick) {
    for (ProcessFrame pf: this.frames) {
      for (Page p: pf.getPages()) {
        if (!p.isLoaded()) {
          int ticks = p.getTicksTillLoaded() - 1;
          p.setTicksTillLoaded(ticks);
          p.setTickLastUsed(currentTick);

          //check if now loaded
          if (ticks == 0) {
            //update process state
            p.getProcess().setState(Process.ProcessState.Ready);
          }
        }
      }
    }
  }

  public String report() {
    String result = "PID | Turnaround Time | # Faults | Fault Times\r\n";
    for (ProcessFrame pf: this.frames) {
      Process p = pf.getProcess();
      //result += String.format("P%02d | %s | %d |", p.getId(), p.getFinishTime(), p.getPageFaults().size());
      result += String.format("P%02d |", p.getId());
      result += center(Integer.toString(p.getFinishTime()), 17, " ") + "|";
      result += center(Integer.toString(p.getPageFaults().size()), 10, " ") + "| ";
      result += "{";
      for (int i = 0; i < p.getPageFaults().size(); i++) {
        result += String.format("%d%s", p.getPageFaults().get(i).getTick(), (i < p.getPageFaults().size() - 1 ? ", " : "}\r\n"));
      }
    }
    return result;
  }

  private String center(String text, int width, String separator) {
    int mainCenterIndex = width / 2;
    int textCenterIndex = (text.length() - 1) / 2;
    int textStartIndex = mainCenterIndex - textCenterIndex - 1;

    String result = "";
    int i = -1;
    while (i < (width - 1)) {
      if (i == textStartIndex) {
        result += text;
        i += text.length();
      }
      else {
        result += separator;
        i++;
      }
    }

    return result;
  }

  /**
   * Event handler to run when a process is finished
   * @param process The process that has finished
   */
  public void onProcessFinished(Process process) {
    //todo
  }
}