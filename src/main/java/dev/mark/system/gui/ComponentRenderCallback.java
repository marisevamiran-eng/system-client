package dev.mark.system.gui;

import dev.mark.system.gui.Component;
import org.joml.Matrix4f;

@FunctionalInterface
public interface ComponentRenderCallback<T extends Component> {
   void render(T t, Matrix4f matrix4f, float f, float f1, int i, int j, float f2, float f3);
}
