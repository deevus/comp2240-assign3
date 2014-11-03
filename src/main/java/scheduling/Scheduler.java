package scheduling;

import java.util.List;

public abstract class Scheduler {
  private boolean isRunning;
  private Process currentProcess;
  private int currentTick;

  public Scheduler() {
    isRunning = false;
  }

  public void start(List<system.Process> newProcesses) {
    isRunning = true;
    currentTick = -1;
    this.processIncoming(newProcesses);
    this.onStart();
  }

  public void tick() {
    ++currentTick;
    this.onTick();
  }

  public void stop() {
    isRunning = false;

    this.onStop();
  }

  abstract void processIncoming(List<system.Process> newProcesses);
  abstract void onTick();
  abstract void onStart();
  abstract void onStop();

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
}