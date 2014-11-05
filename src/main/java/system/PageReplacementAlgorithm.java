package system;

import java.util.List;

public abstract class PageReplacementAlgorithm {
  private final int maxFrames;

  public PageReplacementAlgorithm(int maxFrames) {
    this.maxFrames = maxFrames;
  }

  public int getMaxFrames() {
    return this.maxFrames;
  }

  /**
   * Removes a page from the given list
   * based on the implemented algorithm
   * @param pages A list of pages to consider
   */
  public abstract void removePage(List<Page> pages);
}

