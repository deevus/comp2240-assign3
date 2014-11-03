package scheduling;

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class RoundRobinScheduler extends Scheduler {
  private static final Logger logger = Logger.getLogger("scheduling.scheduler");
  public static final int TIME_QUANTUM = 4;

  private Queue<system.Process> readyQueue;
  private Queue<system.Process> blockedQueue;
  private int timeSliceLeft;

  public RoundRobinScheduler() {
    readyQueue = new LinkedList<system.Process>();
    blockedQueue = new LinkedList<system.Process>();
    timeSliceLeft = TIME_QUANTUM;
  }

  @Override
  void processIncoming(List<system.Process> newProcesses) {
    for (system.Process p: newProcesses) {
      readyQueue.add(p);
    }
  }

  @Override
  void onStart() {
    logger.info("Scheduler Started.");
  }

  @Override
  void onStop() {
    logger.info("Scheduler Stopped.");
  }

  @Override
  void onTick() {
    logger.fine(String.format("Scheduler Tick #%d.", this.getCurrentTick()));

    if (readyQueue.isEmpty() && blockedQueue.isEmpty()) stop();
  }
}