package system;

import java.util.List;

public class LRUPageReplacement extends PageReplacementAlgorithm {
  public LRUPageReplacement(int maxFrames) {
    super(maxFrames);
  }

  @Override
  public void removePage(List<Page> pages) {
    Page lru = null;
    for (Page p: pages) {
      if (lru == null || p.getTickLastUsed() < lru.getTickLastUsed()) {
        lru = p;
      }
    }
    if (lru == null) return;

    //remove from process frame
    ProcessFrame pf = lru.getProcess().getProcessFrame();
    pf.getPages().remove(lru);
  }
}
