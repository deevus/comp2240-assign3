/**
 * COMP2240 Assignment 3
 * @author Simon Hartcher
 * @studentno c3185790
 */

package system;

import java.util.*;
import java.lang.IllegalStateException;
import java.io.File;
import java.util.Scanner;

public class Process implements Cloneable {
  public enum ProcessState {
    Ready,
    Blocked,
    Finished
  }

  private ProcessState state;
  private Queue<Integer> instructions;
  private List<PageFault> pageFaults;
  private static int _id = 0;
  private int id;
  private Integer finishTime = null;
  private ProcessFrame processFrame;

  public Process(Queue<Integer> instructions, int id) { 
    this.instructions = instructions;
    this.id = id;
    this.pageFaults = new ArrayList<>();
    this.state = ProcessState.Ready;
  }

  /**
   * Executes the next instruction
   */
  public void executeInstruction() {
    this.instructions.poll();
  }

  public boolean isDone() {
    return this.instructions.isEmpty(); 
  }

  public int nextInstruction() {
    return this.instructions.peek();
  }

  public ProcessFrame getProcessFrame() {
    return this.processFrame;
  }

  public void setProcessFrame(ProcessFrame processFrame) {
    this.processFrame = processFrame;
  }

  public int getId() {
    return id;
  }
  
  public void setId(int id){
    this.id = id;
  }

  public static Process fromFile(String filePath) {
    try {
      File f = new File(filePath);
      Scanner s = new Scanner(f);

      List<String> data = new LinkedList<String>();
      while (s.hasNextLine())
        data.add(s.nextLine().toLowerCase().trim());

      //find begin and end
      int beginIndex = -1;
      int endIndex = -1;
      for (int i = 0; i < data.size(); i++) {
        if (data.get(i).equals("begin")) beginIndex = i;
        if (data.get(i).equals("end")) endIndex = i;
        if (beginIndex > 0 && endIndex > 0) break;
      }

      //find end

      //assertions
      if (beginIndex == -1)
        throw new IllegalStateException("Invalid file format: begin not found.");
      if (endIndex == -1)
        throw new IllegalStateException("Invalid file format: end not found.");

      Queue<Integer> instructions = new LinkedList<Integer>();
      for (int i = beginIndex + 1; i < endIndex; i++) {
        instructions.offer(Integer.parseInt(data.get(i)));
      }

      return new Process(instructions, ++_id);
    }
    catch (Exception e) {
      System.out.println(String.format("Error loading from file %s; %s", filePath, e.getMessage()));
    }

    //something went wrong
    return null;
  }

  public ProcessState getState() {
    return state;
  }
  
  public void setState(ProcessState state){
    this.state = state;
  }

  public int getFinishTime() {
    return finishTime;
  }
  
  public void setFinishTime(int finishTime){
    this.finishTime = finishTime;
  }

  public void addPageFault(int tick) {
    PageFault pageFault = new PageFault(this, tick);
    this.pageFaults.add(pageFault);
  }

  public List<PageFault> getPageFaults() {
    return pageFaults;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof ProcessFrame) {
      return other.equals(this);
    }
    return this == other;
  }

  @Override
  public String toString() {
    return String.format("#%d", this.getId());
  }

  @Override
  public Object clone() {
    Queue<Integer> ins = new LinkedList<>();
    for (Integer i: this.instructions) ins.add(i);
    return new Process(ins, this.id);
  }
}