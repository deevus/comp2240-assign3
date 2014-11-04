package system;

import java.util.List;

public interface AllocationStrategy {
  void allocateFrames(List<system.Process> processes, int availableFrames);
}