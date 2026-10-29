package dev.mark.system.gui;

import org.joml.Matrix4f;

@FunctionalInterface
public interface AfterCardsRenderCallback {
   void onAfterCardsRender(Matrix4f matrix4f, float f, float f1, int i, int j, float f2, float f3);
}
