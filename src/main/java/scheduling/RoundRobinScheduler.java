package scheduling;

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;
import system.*;
import system.Process;

public class RoundRobinScheduler extends Scheduler {
  public static final int TIME_QUANTUM = 4;

  private Queue<system.Process> readyQueue;
  private int timeSliceLeft;

  public RoundRobinScheduler() {
    readyQueue = new LinkedList<>();
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
    // System.out.println("Scheduler Started.");
  }

  @Override
  void onStop() {
    // System.out.println(String.format("Scheduler stopped after %d ticks.", this.getCurrentTick()));
  }

  @Override
  void onTick() { if (currentProcess != null) {
      timeSliceLeft--;

      //while there are processes in the ready state
      while (currentProcess.getState() == Process.ProcessState.Ready) {
        //get next instruction
        int nextInstruction = currentProcess.nextInstruction();

        //check if it is paged
        if (system.isPaged(currentProcess, nextInstruction)) {
          //execute it
          currentProcess.executeInstruction();

          //call event handler
          this.onExecuteInstruction(currentProcess, nextInstruction);
          break;
        }
        else {
          //register page fault
          currentProcess.addPageFault(this.getCurrentTick());

          //load instruction
          system.loadInstruction(currentProcess, currentProcess.nextInstruction());
          currentProcess.setState(Process.ProcessState.Blocked);

          //push to queue
          readyQueue.offer(currentProcess);

          //get next process
          currentProcess = readyQueue.poll();
        }
      }

      //process completed
      if (currentProcess.isDone()) {
//        System.out.println(String.format("T%d Process %s completed", this.getCurrentTick(), currentProcess));
        currentProcess.setFinishTime(this.getCurrentTick());
        currentProcess.setState(Process.ProcessState.Finished);
        currentProcess = null;
      }

      //out of time
      else if (timeSliceLeft == 0 || currentProcess.getState() == Process.ProcessState.Blocked) {
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