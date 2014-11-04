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

  public void start(List<Process> newProcesses) {
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

  public OperatingSystem getOperatingSystem() {
    return system;
  }
  
  public void setOperatingSystem(OperatingSystem system){
    this.system = system;
  }
}