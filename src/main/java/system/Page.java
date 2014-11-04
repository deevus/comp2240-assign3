package system;

public class Page {
  public static final int PAGE_LOADED = 0;
  public static final int PAGE_NEW = -1;

  private Process process;
  private int instruction;
  private int ticksTillLoaded;
  private int tickLastUsed;
  private boolean useBit;

  public Page(Process process, int instruction) {
    this.process = process;
    this.instruction = instruction;
    this.ticksTillLoaded = PAGE_NEW;
    this.tickLastUsed = 0;
    this.useBit = false;
  }

  public boolean isLoaded() {
    return ticksTillLoaded == PAGE_LOADED;
  }
  
  public int getTickLastUsed() {
    return tickLastUsed;
  }
  
  public void setTickLastUsed(int tickLastUsed){
    this.tickLastUsed = tickLastUsed;
  }

  public boolean getUseBit() {
    return useBit;
  }
  
  public void setUseBit(boolean useBit){
    this.useBit = useBit;
  }

  public Process getProcess() {
    return process;
  }
  
  public void setProcess(Process process){
    this.process = process;
  }

  public int getInstruction() {
    return instruction;
  }
  
  public void setInstruction(int instruction){
    this.instruction = instruction;
  }

  public int getTicksTillLoaded() {
    return ticksTillLoaded;
  }
  
  public void setTicksTillLoaded(int ticksTillLoaded){
    this.ticksTillLoaded = ticksTillLoaded;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof ProcessFrame) {
      return other.equals(this);
    }

    if (other instanceof Page) {
      Page that = (Page)other;
      return 
        this.process == that.process && 
        this.instruction == that.instruction &&
        this.ticksTillLoaded == that.ticksTillLoaded;
    }
    return false;
  }
}