/**
 * COMP2240 Assignment 3
 * @author Simon Hartcher
 * @studentno c3185790
 */

package system;

import java.util.*;
import java.util.logging.*;

public class ProcessFrame {
  private static final Logger logger = Logger.getLogger("ProcessFrame");

  private Process process;
  private List<Page> pages;
  private int maximumFrames;

  public ProcessFrame(Process process) {
    this.process = process;
    this.pages = new ArrayList<Page>();
  }

  /**
   * Determines if a page exists for the given instruction
   * The page does not have to be loaded completely
   * @param  instruction The instruction
   */
  public boolean hasPageFor(int instruction) {
    return getPageFor(instruction) != null;
  }

  public Process getProcess() {
    return process;
  }
  
  public void setProcess(Process process){
    this.process = process;
  }

  public void addPage(Page p) {
    this.pages.add(p);
  }

  public List<Page> getPages() {
    return pages;
  }

  public void setPages(List<Page> pages){
    this.pages = pages;
  }

  public int getMaximumPages() {
    return maximumFrames;
  }
  
  public void setMaximumPages(int maximumPages){
    this.maximumFrames = maximumPages;
  }

  public boolean hasMaxPages() {
    return this.pages.size() >= this.maximumFrames;
  }

  /**
   * Determines if an instruction is loaded for the process frame
   * @param  instruction The instruction
   */
  public boolean instructionLoaded(int instruction) {
    for (Page p: this.pages) {
      if (p.getInstruction() == instruction && p.isLoaded()) return true;
    }

    return false;
  }

  /**
   * Allows checking of Page or Process objects that are
   * contained within this Process Frame
   * @param  other The object to compare
   */
  @Override
  public boolean equals(Object other) {
    //by process
    if (other instanceof Process) {
      Process that = (Process)other;
      return this.getProcess() == that;
    }

    //by page
    if (other instanceof Page) {
      Page that = (Page)other;
      return this.getProcess() == that.getProcess() && this.pages.contains(that);
    }

    //by reference
    return this == other;
  }

  public Page getPageFor(int instruction) {
    for (Page p: pages) {
      if (p.getInstruction() == instruction) return p;
    }
    return null;
  }
}