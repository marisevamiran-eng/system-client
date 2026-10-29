package dev.mark.system.gui;



import dev.mark.system.gui.component.CardBase;
@FunctionalInterface
public interface CardFactory<T> {
   CardBase create(T t);
}
