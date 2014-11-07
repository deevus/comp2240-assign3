/**
 * COMP2240 Assignment 3
 * @author Simon Hartcher
 * @studentno c3185790
 */

package system;

/**
 * Allocates a fixed number of pages to each process
 * that does not change over the course of execution
 * using a local replacement strategy
 */
public class FixedAllocationStrategy extends AllocationStrategy {
  public FixedAllocationStrategy(int maxFrames, PageReplacement pageReplacement) {
    super(maxFrames, pageReplacement);
  }

  @Override
  public void allocatePages(ProcessFrame processFrame) {
    //average number of pages by number of processes
    //only do this once
    if (processFrame.getMaximumPages() == 0) {
      int pagesPerProcess = this.getMaxFrames() / this.getFrames().size();
      processFrame.setMaximumPages(pagesPerProcess);
    }

    //do we need to remove a page?
    if (processFrame.hasMaxPages()) {
      Page page = this.getReplacementAlgorithm().getPageToRemove(processFrame.getPages());
      processFrame.getPages().remove(page);
    }
  }

}