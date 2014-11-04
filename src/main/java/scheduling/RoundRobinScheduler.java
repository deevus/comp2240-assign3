package scheduling;

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;
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
    System.out.println("Scheduler Started.");
  }

  @Override
  void onStop() {
    System.out.println(String.format("Scheduler stopped after %d ticks.", this.getCurrentTick()));
  }

  @Override
  void onTick() {
    System.out.println(String.format("Scheduler Tick #%d.", this.getCurrentTick()));

    if (currentProcess != null) {
      boolean doBlock = false;
      timeSliceLeft--;

      if (system.isPaged(currentProcess, currentProcess.nextInstruction())) {
        System.out.println(String.format("Process %s executed instruction %d", currentProcess, currentProcess.nextInstruction()));
        currentProcess.executeInstruction();
      }
      else {
        System.out.println(String.format("Process %s instruction %d not loaded. Blocking.", currentProcess, currentProcess.nextInstruction()));
        //block the process
        doBlock = true;

        //load instruction
        system.loadInstruction(currentProcess, currentProcess.nextInstruction());
      }

      //process completed
      if (currentProcess.isDone()) {
        System.out.println(String.format("Process %s completed", currentProcess));
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

    if (currentProcess == null && !readyQueue.isEmpty()) {
      currentProcess = readyQueue.poll();
      timeSliceLeft = TIME_QUANTUM;
    }

    //stop if all done
    if (readyQueue.isEmpty() && currentProcess == null) stop();
  }
}