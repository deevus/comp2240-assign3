/**
 * COMP2240 Assignment 3
 * @author Simon Hartcher
 * @studentno c3185790
 */

package system;

import java.util.List;

public class ClockPageReplacement extends PageReplacementAlgorithm {
  public ClockPageReplacement(int maxFrames) {
    super(maxFrames);
  }

  @Override
  public Page getPageToRemove(List<Page> pages) {
    Page toRemove = null;

    //the clock cycle
    while (toRemove == null) {
      for (Page p : pages) {
        //if usebit is zero
        if (p.getUseBit() == false) {
          toRemove = p;
          break;
        }

        //toggle use bit as we go around
        p.setUseBit(!p.getUseBit());
      }
    }

    return toRemove;
  }
}
