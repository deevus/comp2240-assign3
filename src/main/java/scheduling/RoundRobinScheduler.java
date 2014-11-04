package scheduling;

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import system.*;

public class RoundRobinScheduler extends Scheduler {
  private static final Logger logger = Logger.getLogger("scheduling.scheduler");
  public static final int TIME_QUANTUM = 4;

  private Queue<system.Process> readyQueue;
  private int timeSliceLeft;

  public RoundRobinScheduler() {
    readyQueue = new LinkedList<system.Process>();
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
    logger.info(String.format("Scheduler stopped after %d ticks.", this.getCurrentTick()));
  }

  @Override
  void onTick() {
    logger.fine(String.format("Scheduler Tick #%d.", this.getCurrentTick()));

    if (currentProcess != null) {
      boolean doBlock = false;
      timeSliceLeft--;

      if (system.isPaged(currentProcess, currentProcess.nextInstruction())) {
        currentProcess.executeInstruction();
      }
      else {
        //block the process
        doBlock = true;

        //load instruction
        system.loadInstruction(currentProcess, currentProcess.nextInstruction());
      }

      //process completed
      if (currentProcess.isDone()) {
        currentProcess = null;
      }

      //out of time
      if (timeSliceLeft == 0 || doBlock) {
        if (readyQueue.isEmpty()) {
          timeSliceLeft = TIME_QUANTUM;
        }
        else {
          readyQueue.offer(currentProcess);
          currentProcess = null;
        }
      }
    }

    if (currentProcess == null && readyQueue.isEmpty() == false) {
      currentProcess = readyQueue.poll();
      timeSliceLeft = TIME_QUANTUM;
    }

    //stop if all done
    if (readyQueue.isEmpty() && currentProcess == null) stop();
  }
}