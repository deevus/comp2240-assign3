package system;

public class FixedAllocationStrategy extends AllocationStrategy {
  public FixedAllocationStrategy() {

  }

  @Override
  public void allocateFrames(int availableFrames) {
    int framesPerProcess = availableFrames / this.getFrames().size();
    while (availableFrames > 0) {
      for (ProcessFrame pf: this.getFrames()) {
        pf.setMaximumPages(pf.getMaximumPages() + framesPerProcess);
        availableFrames -= framesPerProcess;
        if (availableFrames == 0) break;
      }
      framesPerProcess = 1;
    }

    for (ProcessFrame pf: this.getFrames()) {
      System.out.println(
        String.format("Process %s has %d max pages", 
          pf.getProcess(), pf.getMaximumPages())
        );
    }
  }

}