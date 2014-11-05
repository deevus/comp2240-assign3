package system;

import java.util.List;

public class ClockPageReplacement extends PageReplacementAlgorithm {
  public ClockPageReplacement(int maxFrames) {
    super(maxFrames);
  }

  @Override
  public Page getPageToRemove(List<Page> pages) {
    Page toRemove = null;
    while (toRemove == null) {
      for (Page p : pages) {
        if (p.getUseBit() == false) {
          toRemove = p;
          break;
        }
        p.setUseBit(!p.getUseBit());
      }
    }

    return toRemove;
  }
}
