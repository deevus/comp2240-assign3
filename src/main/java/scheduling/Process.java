package scheduling;

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.lang.IllegalStateException;
import java.util.logging.*;
import java.io.File;
import java.util.Scanner;

public class Process {
  private Queue<Integer> pageRequests;

  public Process(Queue<Integer> pageRequests) { 
    this.pageRequests = pageRequests;
  }

  public boolean isDone() {
    return this.pageRequests.isEmpty(); 
  }

  public int nextPage() {
    return this.pageRequests.poll();
  }

  public static Process fromFile(String filePath) {
    try {
      File f = new File(filePath);
      Scanner s = new Scanner(f);

      List<String> data = new LinkedList<String>();
      while (s.hasNextLine())
        data.add(s.nextLine());

      //assertions
      if (!data.get(0).equals("begin")) 
        throw new IllegalStateException("Invalid file format: begin not found.");
      if (!data.get(data.size() - 1).equals("end"))
        throw new IllegalStateException("Invalid file format: end not found.");

      Queue<Integer> pageRequests = new LinkedList<Integer>();
      for (int i = 1; i < data.size() - 1; i++) {
        pageRequests.offer(Integer.parseInt(data.get(i)));
      }

      return new Process(pageRequests);
    }
    catch (Exception e) {
      //todo: do something
      Logger.getLogger("scheduling.process").log(Level.SEVERE, "Loading from file", e);
    }

    //something went wrong
    return null;
  }
}