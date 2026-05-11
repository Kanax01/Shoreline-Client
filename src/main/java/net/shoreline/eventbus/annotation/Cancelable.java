package net.shoreline.eventbus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cancelable {}


/* Location:              C:\Users\kanax\OneDrive\Desktop\Shoreline-1.0_Beta-25.jar!\net\shoreline\eventbus\annotation\Cancelable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */