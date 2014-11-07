/**
 * COMP2240 Assignment 3
 * @author Simon Hartcher
 * @studentno c3185790
 */

package system;

import java.util.List;

public class LRUPageReplacement extends PageReplacementAlgorithm {
  public LRUPageReplacement(int maxFrames) {
    super(maxFrames);
  }


  @Override
  public Page getPageToRemove(List<Page> pages) {
    Page lru = null;
    for (Page p: pages) {
      if (lru == null || (p.getTickLastUsed() < lru.getTickLastUsed() && p.isLoaded())) {
        lru = p;
      }
    }
    return lru;
  }
}
