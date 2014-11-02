package scheduling;

import java.util.List;

public abstract class Scheduler {
  private boolean isRunning;
  private Process currentProcess;
  private int currentTick;

  public Scheduler() {
    isRunning = false;
  }

  public void start() {
    isRunning = true;
    currentTick = -1;

    this.onStart();
  }

  public void tick(List<Process> newProcesses) {
    ++currentTick;

    this.processIncoming(newProcesses);

    this.onTick();
  }

  public void stop() {
    isRunning = false;

    this.onStop();
  }

  abstract void processIncoming(List<Process> newProcesses);
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