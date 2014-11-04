package system;

import scheduling.*;
import java.util.List;
import java.util.ArrayList;

public class OperatingSystem {
  public static final int MAX_FRAMES = 20;
  public static final int PAGE_LOAD_TIME = 7;

  private ArrayList<Page> frames = new ArrayList<Page>(MAX_FRAMES);
  private final Scheduler scheduler;
  private final AllocationStrategy strategy;

  public OperatingSystem(Scheduler scheduler, AllocationStrategy strategy) {
    this.scheduler = scheduler;
    this.scheduler.setOperatingSystem(this);
    this.strategy = strategy;
  }

  public void run(List<system.Process> processes) {
    this.strategy.allocateFrames(processes, MAX_FRAMES);
    this.scheduler.start(processes);
    while (this.scheduler.isRunning()) {
      this.tick();
    }
  }

  public boolean isPaged(Process p, int i) {
    Page page = new Page(p, i);
    page.setTicksTillLoaded(Page.PAGE_LOADED);
    return frames.contains(page);
  }

  public void loadInstruction(Process p, int i) {
    Page page = new Page(p, i);
    page.setTicksTillLoaded(PAGE_LOAD_TIME);

    frames.add(page);
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
    for (Page p: this.frames) {
      if (p != null && !p.isLoaded()) {
        int ticks = p.getTicksTillLoaded() - 1;
        p.setTicksTillLoaded(ticks);
      }
    }
  }
}