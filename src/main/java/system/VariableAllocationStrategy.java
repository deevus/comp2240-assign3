package system;

import java.util.ArrayList;
import java.util.List;

public class VariableAllocationStrategy extends AllocationStrategy {
  public VariableAllocationStrategy(int maxFrames, PageReplacement pageReplacement) {
    super(maxFrames, pageReplacement);
  }

  @Override
  public void allocatePages(ProcessFrame processFrame) {
    //average number of pages by number of processes
    //only do this once
    if (processFrame.getMaximumPages() == 0) {
      int pagesPerProcess = this.getMaxFrames() / this.getFrames().size();
      processFrame.setMaximumPages(pagesPerProcess);
    }

    //do we need to remove a page?
    if (processFrame.hasMaxPages()) {
      //get all pages
      List<Page> allPages = new ArrayList<>();
      for (ProcessFrame pf: this.getFrames()) {
        allPages.addAll(pf.getPages());
      }

      Page page = this.getReplacementAlgorithm().getPageToRemove(allPages);
      ProcessFrame other = page.getProcess().getProcessFrame();

      //is the page from another process?
      if (page.getProcess().getProcessFrame() != processFrame) {
        //decrement max pages of other process
        other.setMaximumPages(other.getMaximumPages() - 1);

        //increment max pages of this process
        processFrame.setMaximumPages(processFrame.getMaximumPages() + 1);
      }

      //remove the page
      other.getPages().remove(page);
    }
  }
}