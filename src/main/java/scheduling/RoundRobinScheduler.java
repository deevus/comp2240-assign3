package scheduling;

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

public class RoundRobinScheduler extends Scheduler {
  public static final int TIME_QUANTUM = 4;

  private Queue<Process> readyQueue;
  private int timeSliceLeft;

  public RoundRobinScheduler() {
    readyQueue = new LinkedList<Process>();
    timeSliceLeft = TIME_QUANTUM;
  }

  @Override
  void processIncoming(List<Process> newProcesses) {
    for (Process p: newProcesses) {
      readyQueue.add(p);
    }
  }

  @Override
  void onStart() {

  }

  @Override
  void onStop() {

  }

  @Override
  void onTick() {

  }
}