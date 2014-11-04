package system;

import java.util.List;

public abstract class AllocationStrategy {
  protected List<ProcessFrame> frames;

  public abstract void allocateFrames(int availableFrames);

  public List<ProcessFrame> getFrames() {
    return frames;
  }
  
  public void setFrames(List<ProcessFrame> frames){
    this.frames = frames;
  }
}