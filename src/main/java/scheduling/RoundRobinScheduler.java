/**
 * COMP2240 Assignment 3
 * @author Simon Hartcher
 * @studentno c3185790
 */

package scheduling;

import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
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
    if (!readyQueue.isEmpty()) {
      this.currentProcess = readyQueue.poll();
    }
  }

  @Override
  void onStop() {
    // System.out.println(String.format("Scheduler stopped after %d ticks.", this.getCurrentTick()));
  }

  @Override
  void onTick() {
    if (currentProcess != null) {
      //while there are processes in the ready state
      while (currentProcess.getState() == Process.ProcessState.Ready) {
        //get next instruction
        int nextInstruction = currentProcess.nextInstruction();

        //check if it is paged
        if (system.isPaged(currentProcess, nextInstruction)) {
          //execute it
          currentProcess.executeInstruction();
          timeSliceLeft--;

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
          timeSliceLeft = TIME_QUANTUM;
          currentProcess = this.getNextProcess();
        }
      }

      //process completed
      if (currentProcess.isDone()) {
//        System.out.println(String.format("T%d Process %s completed", this.getCurrentTick(), currentProcess));
        currentProcess.setFinishTime(this.getCurrentTick() + 1);
        currentProcess.setState(Process.ProcessState.Finished);
        this.onProcessFinished(currentProcess);
        currentProcess = null;
      }

      //out of time
      else if (timeSliceLeft == 0) {
        if (readyQueue.isEmpty() || this.allBlocked(this.readyQueue)) {
          timeSliceLeft = TIME_QUANTUM;
        }
        else {
          readyQueue.offer(currentProcess);
          currentProcess = null;
        }
      }
    }

    if (currentProcess == null && !readyQueue.isEmpty()) {
      currentProcess = this.getNextProcess();
      timeSliceLeft = TIME_QUANTUM;
    }

    //stop if all done
    if (readyQueue.isEmpty() && currentProcess == null) stop();
  }

  public boolean allBlocked(Collection<Process> list) {
    for (Process p: list) {
      if (p.getState() != Process.ProcessState.Blocked) return false;
    }
    return true;
  }

  public Process getNextProcess() {
    Process process = readyQueue.peek();
    for (Process p: readyQueue) {
      if (p.getState() == Process.ProcessState.Ready) {
        process = p;
        break;
      }
    }

    readyQueue.remove(process);
    return process;
  }

  /**
   * Event handler that is called when a process
   * finishes execution
   * @param process The process that has finished
   */
  public void onProcessFinished(Process process) {
    system.onProcessFinished(process);
  }
}

