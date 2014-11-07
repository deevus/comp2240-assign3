/**
 * COMP2240 Assignment 3
 * @author Simon Hartcher
 * @studentno c3185790
 */

package scheduling;

import java.util.List;
import system.Process;
import system.OperatingSystem;

public abstract class Scheduler {
  private boolean isRunning;
  protected Process currentProcess;
  private int currentTick;
  protected OperatingSystem system;

  public Scheduler() {
    isRunning = false;
  }

  /**
   * Put the scheduler in a starting state
   */
  public void start(List<Process> newProcesses) {
    isRunning = true;
    currentTick = -1;
    this.processIncoming(newProcesses);
    this.onStart();
  }

  /**
   * Tick the scheduler ahead a single clock cycle
   */
  public void tick() {
    ++currentTick;
    this.onTick();
  }

  /**
   * Put the scheduler in a stopped state
   */
  public void stop() {
    isRunning = false;

    this.onStop();
  }

  /**
   * Process any incoming processes
   * eg. put them into a queue
   * @param newProcesses The new processes
   */
  abstract void processIncoming(List<Process> newProcesses);

  /**
   * Event handler for tick event
   */
  abstract void onTick();

  /**
   * Event handler for start event
   */
  abstract void onStart();

  /**
   * Event handler for stop event
   */
  abstract void onStop();

  protected void onExecuteInstruction(Process process, int instruction) {
    system.onExecuteInstruction(process, instruction, this.getCurrentTick());
  }

  /**
   * Returns true if the scheduler is in a running state
   */
  public boolean isRunning() {
    return isRunning;
  }
  
  public void setRunning(boolean isRunning){
    this.isRunning = isRunning;
  }

  public Process getCurrentProcess() {
    return currentProcess;
  }
  
  public void setCurrentProcess(Process currentProcess){
    this.currentProcess = currentProcess;
  }

  public int getCurrentTick() {
    return currentTick;
  }
  
  protected void setCurrentTick(int currentTick){
    this.currentTick = currentTick;
  }

  public OperatingSystem getOperatingSystem() {
    return system;
  }
  
  public void setOperatingSystem(OperatingSystem system){
    this.system = system;
  }
}