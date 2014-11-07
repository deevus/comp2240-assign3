/**
 * COMP2240 Assignment 3
 * @author Simon Hartcher
 * @studentno c3185790
 */

package system;

import java.util.List;

public abstract class AllocationStrategy {
  public enum PageReplacement {
    Clock,
    LRU
  }

  protected List<ProcessFrame> frames;

  private final int maxFrames;
  private PageReplacementAlgorithm replacementAlgorithm;

  public AllocationStrategy(int maxFrames, PageReplacement pageReplacement) {
    this.maxFrames = maxFrames;
    this.initPageReplacement(pageReplacement);
  }

  private void initPageReplacement(PageReplacement pageReplacement) {
    switch (pageReplacement) {
      case Clock:
        replacementAlgorithm = new ClockPageReplacement(maxFrames);
        break;
      case LRU:
        replacementAlgorithm = new LRUPageReplacement(maxFrames);
        break;
      default:
        replacementAlgorithm = new LRUPageReplacement(maxFrames);
        break;
    }
  }

  public int getMaxFrames() {
    return this.maxFrames;
  }

  public PageReplacementAlgorithm getReplacementAlgorithm() {
    return this.replacementAlgorithm;
  }

  /**
   * Allocate pages for a process frame
   * @param processFrame The process frame to allocate pages for
   */
  public abstract void allocatePages(ProcessFrame processFrame);

  public List<ProcessFrame> getFrames() {
    return frames;
  }
  
  public void setFrames(List<ProcessFrame> frames){
    this.frames = frames;
  }
}