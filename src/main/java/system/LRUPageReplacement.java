package system;

import java.util.List;

public class LRUPageReplacement extends PageReplacementAlgorithm {
  @Override
  public void removePage(ProcessFrame processFrame) {
    List<Page> pages = processFrame.getPages();
    Page lru = null;
    for (Page p: pages) {
      if (lru == null || p.getTickLastUsed() < lru.getTickLastUsed()) {
        lru = p;
      }
    }

    pages.remove(lru);
  }
}
