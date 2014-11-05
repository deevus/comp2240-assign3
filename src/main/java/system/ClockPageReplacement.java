package system;

import java.util.List;

public class ClockPageReplacement extends PageReplacementAlgorithm {
  public ClockPageReplacement(int maxFrames) {
    super(maxFrames);
  }

  @Override
  public void removePage(List<Page> pages) {
    Page toRemove = null;
    while (toRemove == null) {
      for (Page p : pages) {
        if (p.getUseBit() == false) {
          toRemove = p;
        }
        p.setUseBit(!p.getUseBit());
      }
    }

    //get process frame for page
    ProcessFrame pf = toRemove.getProcess().getProcessFrame();

    //remove it
    pf.getPages().remove(toRemove);
  }
}
