package net.shoreline.eventbus;

import net.shoreline.eventbus.event.Event;

public interface EventHandler {
  void subscribe(Object paramObject);
  
  void unsubscribe(Object paramObject);
  
  boolean dispatch(Event paramEvent);
}


/* Location:              C:\Users\kanax\OneDrive\Desktop\Shoreline-1.0_Beta-25.jar!\net\shoreline\eventbus\EventHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */