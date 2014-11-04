package system;

import java.util.*;
import java.lang.IllegalStateException;
import java.io.File;
import java.util.Scanner;

public class Process {
  private Queue<Integer> instructions;
  private static int _id = 0;
  private int id;

  public Process(Queue<Integer> instructions, int id) { 
    this.instructions = instructions;
    this.id = id;
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

      //assertions
      if (!data.get(0).equals("begin")) 
        throw new IllegalStateException("Invalid file format: begin not found.");
      if (!data.get(data.size() - 1).equals("end"))
        throw new IllegalStateException("Invalid file format: end not found.");

      Queue<Integer> instructions = new LinkedList<Integer>();
      for (int i = 1; i < data.size() - 1; i++) {
        instructions.offer(Integer.parseInt(data.get(i)));
      }

      return new Process(instructions, ++_id);
    }
    catch (Exception e) {
      //todo: do something
      System.out.println(String.format("Error loading from file %s; %s", filePath, e.getMessage()));
    }

    //something went wrong
    return null;
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
}