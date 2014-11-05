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

  public abstract void allocatePages(ProcessFrame processFrame);

  public List<ProcessFrame> getFrames() {
    return frames;
  }
  
  public void setFrames(List<ProcessFrame> frames){
    this.frames = frames;
  }
}