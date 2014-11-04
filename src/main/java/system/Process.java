package system;

import java.util.*;
import java.lang.IllegalStateException;
import java.util.logging.*;
import java.io.File;
import java.util.Scanner;

public class Process {
  private Queue<Integer> instructions;

  public Process(Queue<Integer> instructions) { 
    this.instructions = instructions;
  }

  /**
   * Executes the next instruction
   * @param instruction [description]
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

      return new Process(instructions);
    }
    catch (Exception e) {
      //todo: do something
      Logger.getLogger("scheduling.process").log(Level.WARNING, "Loading from file", e);
    }

    //something went wrong
    return null;
  }
}