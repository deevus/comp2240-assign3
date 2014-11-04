package system;

import scheduling.*;
import java.util.*;

public class OperatingSystem {
  public static final int MAX_FRAMES = 20;
  public static final int PAGE_LOAD_TIME = 7;

  private List<ProcessFrame> frames = new ArrayList<ProcessFrame>();
  private final Scheduler scheduler;
  private final AllocationStrategy strategy;

  public OperatingSystem(Scheduler scheduler, AllocationStrategy strategy) {
    this.scheduler = scheduler;
    this.scheduler.setOperatingSystem(this);
    this.strategy = strategy;
    this.strategy.setFrames(frames);
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
    System.out.println(String.format("Init %d process frames.", frames.size()));
  }

  public boolean isPaged(Process p, int i) {
    ProcessFrame pf = frames.get(frames.indexOf(p));
    return pf.instructionLoaded(i);
  }

  public void loadInstruction(Process p, int i) {
    //get the process frame for the given process
    ProcessFrame processFrame = frames.get(frames.indexOf(p));

    //check number of pages allocation for process
    if (processFrame.hasMaxPages()) return;

    if (!processFrame.hasPageFor(i)) {
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
   * Update any pages that are loading by decrementing
   * their ticksTillLoaded value
   */
  private void updatePages() {
    for (ProcessFrame pf: this.frames) {
      for (Page p: pf.getPages()) {
        if (!p.isLoaded()) {
          int ticks = p.getTicksTillLoaded() - 1;
          p.setTicksTillLoaded(ticks);
        }
      }
    }
  }

}