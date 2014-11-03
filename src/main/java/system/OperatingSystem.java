package system;

import scheduling.*;
import java.util.List;

public class OperatingSystem {
  public static final int MAX_FRAMES = 20;

  private int[] frames = new int[MAX_FRAMES];
  private final Scheduler scheduler;

  public OperatingSystem(Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  public void run(List<system.Process> processes) {
    this.scheduler.start(processes);
    while (this.scheduler.isRunning()) {
      this.scheduler.tick();
    }
  }
}