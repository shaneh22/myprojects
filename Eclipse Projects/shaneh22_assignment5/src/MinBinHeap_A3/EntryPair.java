package MinBinHeap_A3;

public class EntryPair implements EntryPair_Interface {
  public String value;
  public Long priority;

  public EntryPair(String aValue, Long aPriority) {
    value = aValue;
    priority = aPriority;
  }

  public String getValue() { return value; }
  public long getPriority() { return priority; }
}