/**
 * COMP2240 Assignment 3
 * @author Simon Hartcher
 * @studentno c3185790
 */

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
  public abstract Page getPageToRemove(List<Page> pages);
}

