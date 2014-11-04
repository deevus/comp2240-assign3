package system;

import java.util.List;

public class FixedAllocationStrategy implements AllocationStrategy {
  public FixedAllocationStrategy() {

  }

  @Override
  public void allocateFrames(List<system.Process> processes, int availableFrames) {
    // int framesPerProcess = availableFrames / processes.size();
    // while (availableFrames > 0) {
    //   for (system.Process p: processes) {
    //     p.setMaximumFrames(framesPerProcess);
    //     availableFrames -= framesPerProcess;
    //     if (availableFrames == 0) return;
    //   }
    //   framesPerProcess = 1;
    // }
  }

}