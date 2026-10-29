package dev.mark.system.gui.component;

import dev.mark.system.gui.animation.PanelTransitionAnimator;
import dev.mark.system.gui.enums.VisibilityMode;
import dev.mark.system.font.MSDFFont;
import dev.mark.system.gui.Component;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import dev.mark.system.render.BuiltRectangle;
import dev.mark.system.render.BuiltText;
import dev.mark.system.render.RectangleCache;
import dev.mark.system.setting.BlockEntry;
import dev.mark.system.setting.BlockListSetting;

import dev.mark.system.gui.Component;
import dev.mark.system.render.builder.RectangleBuilder;
public class BlockListEditor extends Component {
   private static final Map<Character, Character> aij = Map.<Character, Character>ofEntries(
      Map.<Character, Character>entry('А', 'A'),
      Map.<Character, Character>entry('Б', 'B'),
      Map.<Character, Character>entry('С', 'C'),
      Map.<Character, Character>entry('Д', 'D'),
      Map.<Character, Character>entry('Е', 'E'),
      Map.<Character, Character>entry('Ф', 'F'),
      Map.<Character, Character>entry('Г', 'G'),
      Map.<Character, Character>entry('Х', 'H'),
      Map.<Character, Character>entry('И', 'I'),
      Map.<Character, Character>entry('Ж', 'J'),
      Map.<Character, Character>entry('К', 'K'),
      Map.<Character, Character>entry('Л', 'L'),
      Map.<Character, Character>entry('М', 'M'),
      Map.<Character, Character>entry('Н', 'N'),
      Map.<Character, Character>entry('О', 'O'),
      Map.<Character, Character>entry('П', 'P'),
      Map.<Character, Character>entry('Р', 'R'),
      Map.<Character, Character>entry('З', 'S'),
      Map.<Character, Character>entry('Т', 'T'),
      Map.<Character, Character>entry('У', 'U'),
      Map.<Character, Character>entry('В', 'V'),
      Map.<Character, Character>entry('Ш', 'W'),
      Map.<Character, Character>entry('Й', 'Y'),
      Map.<Character, Character>entry('а', 'a'),
      Map.<Character, Character>entry('б', 'b'),
      Map.<Character, Character>entry('с', 'c'),
      Map.<Character, Character>entry('д', 'd'),
      Map.<Character, Character>entry('е', 'e'),
      Map.<Character, Character>entry('ф', 'f'),
      Map.<Character, Character>entry('г', 'g'),
      Map.<Character, Character>entry('х', 'h'),
      Map.<Character, Character>entry('и', 'i'),
      Map.<Character, Character>entry('ж', 'j'),
      Map.<Character, Character>entry('к', 'k'),
      Map.<Character, Character>entry('л', 'l'),
      Map.<Character, Character>entry('м', 'm'),
      Map.<Character, Character>entry('н', 'n'),
      Map.<Character, Character>entry('о', 'o'),
      Map.<Character, Character>entry('п', 'p'),
      Map.<Character, Character>entry('р', 'r'),
      Map.<Character, Character>entry('з', 's'),
      Map.<Character, Character>entry('т', 't'),
      Map.<Character, Character>entry('у', 'u'),
      Map.<Character, Character>entry('в', 'v'),
      Map.<Character, Character>entry('ш', 'w'),
      Map.<Character, Character>entry('й', 'y')
   );
   private static final float awY = 286.0F;
   private static final float zp = 201.0F;
   private static final float UP = 12.0F;
   private static final float AM = 40.0F;
   private static final float azs = 0.01F;
   private static final float r = 26.0F;
   private static final float Ft = 20.0F;
   private static final float CM = 26.0F;
   private static final float ja = 22.0F;
   public final PanelTransitionAnimator pi;
   private final Runnable ll;
   private final CloseButton ig;
   private final BuiltRectangle wy;
   private final BuiltRectangle Ls;
   private final BuiltRectangle yd;
   private final BuiltRectangle XK;
   private final String uu;
   public final BlockListSetting jH;
   private boolean apP = false;
   private VisibilityMode Jx = VisibilityMode.VI;
   private final List<BlockListEntryRenderer> on = new ArrayList<BlockListEntryRenderer>();
   private float cF = 0.0F;
   private float ake = 0.0F;
   private final List<Block> asc = new ArrayList<Block>();
   private final List<Block> avo = new ArrayList<Block>();
   private String xv = "";
   private boolean cs = false;
   private float aum = 0.0F;
   private float anH = 0.0F;
   public ColorPickerComponent Bl = null;
   private final Matrix4f agJ = new Matrix4f();

   public BlockListEditor(float f, float f1, String s, BlockListSetting blocklistsetting, Runnable runnable) {
      super(f, f1, 286.0F, 201.0F);
      this.uu = s;
      this.jH = blocklistsetting;
      this.ll = runnable;
      this.pi = new PanelTransitionAnimator();
      this.pi.a();
      this.pi.b();
      this.ig = new CloseButton(246.0F, 5.0F, this::g);
      this.wy = new RectangleBuilder().a(286.0F, 201.0F).b(8.0F).a();
      this.Ls = new RectangleBuilder().a(286.0F, 0.8F).a();
      this.yd = new RectangleBuilder().a(266.0F, 22.0F).b(4.0F).a();
      this.XK = new RectangleBuilder().a(266.0F, 26.0F).b(4.0F).a();
      this.a();
      this.b();
   }

   private void a() {
      for (Block block : Registries.BLOCK) {
         if (block != Blocks.AIR && block != Blocks.CAVE_AIR && block != Blocks.VOID_AIR) {
            this.asc.add(block);
         }
      }
   }

   public void b() {
      this.on.clear();
      float f = 0.0F;

      for (Entry entry : this.jH.getBlocks().entrySet()) {
         Block block = (Block)entry.getKey();
         BlockEntry blockentry = (BlockEntry)entry.getValue();
         BlockListEntryRenderer entryRenderer = new BlockListEntryRenderer(this, block, blockentry, f);
         this.on.add(entryRenderer);
         f += 26.0F;
      }

      this.c();
   }

   private void c() {
      float f = this.on.size() * 26.0F;
      float f1 = 126.0F;
      this.ake = Math.max(0.0F, f - f1);
   }

   private void d() {
      this.avo.clear();
      if (this.xv.isEmpty()) {
         this.avo.addAll(this.asc);
      } else {
         String s = this.f(this.xv).toLowerCase();
         String s1 = this.xv.toLowerCase();

         for (Block block : this.asc) {
            Identifier identifier = Registries.BLOCK.getId(block);
            String s2 = identifier.getPath().replace("_", " ");
            String s3 = s2.toLowerCase();
            if (s3.contains(s1) || s3.contains(s)) {
               this.avo.add(block);
            }
         }
      }

      this.e();
      this.aum = 0.0F;
   }

   private void e() {
      float f = this.avo.size() * 22.0F;
      float f1 = 115.0F;
      this.anH = Math.max(0.0F, f - f1);
   }

   private String f(String s) {
      StringBuilder stringbuilder = new StringBuilder();

      for (char c0 : s.toCharArray()) {
         char c1 = aij.getOrDefault(c0, c0);
         stringbuilder.append(c1);
      }

      return stringbuilder.toString();
   }

   private void g() {
      if (!this.apP && this.ll != null) {
         this.apP = true;
         this.ll.run();
      }
   }

   public void h(Runnable runnable) {
      this.pi.c(runnable);
   }

   @Override
   protected void a(Matrix4f matrix4f, float f, float f1, int i, int j, float f2, float f3) {
      this.pi.f();
      float f4 = f3 * this.pi.k();
      if (!(f4 < 0.01F)) {
         Matrix4f matrix4f1 = this.pi.g() ? this.m(matrix4f, f, f1, this.pi.j(), this.aem) : matrix4f;
         if (this.pi.g()) {
            this.pi.g(matrix4f, f, f1);
         }

         this.wy.a(matrix4f1, f, f1, f4);
         String s = this.Jx == VisibilityMode.l ? "Выбор блока" : this.uu;
         BuiltText builttext = this.k(this.ayW, s, 15.0F, Bz);
         builttext.a(matrix4f1, f + 10.0F, f1 + 12.0F, f4);
         this.Ls.a(matrix4f1, f, f1 + 40.0F, f4);
         if (this.Jx == VisibilityMode.VI) {
            this.i(matrix4f1, f, f1, i, j, f2, f4);
            this.j(matrix4f1, f, f1, i, j, f4);
         } else {
            this.k(matrix4f1, f, f1, f4);
            this.l(matrix4f1, f, f1, i, j, f4);
         }

         this.n(this.ig, f, f1, i, j, f2, f4, matrix4f1);
         if (this.Bl != null) {
            this.Bl.render(matrix4f, this.Bl.getX(), this.Bl.getY(), i, j, f2, f3);
         }
      }
   }

   private void i(Matrix4f matrix4f, float f, float f1, int i, int j, float f2, float f3) {
      float f4 = f + 10.0F;
      float f5 = f1 + 40.0F + 10.0F;
      float f6 = 266.0F;
      float f7 = 121.0F;

      for (BlockListEntryRenderer bleRenderer : this.on) {
         float f8 = f5 + bleRenderer.azQ - this.cF;
         if (!(f8 + 26.0F < f5) && !(f8 > f5 + f7)) {
            bleRenderer.a(matrix4f, f4, f8, i, j, f2, f3);
         }
      }
   }

   private void j(Matrix4f matrix4f, float f, float f1, int i, int j, float f2) {
      float f3 = f + 10.0F;
      float f4 = f1 + 201.0F - 28.0F;
      float f5 = 266.0F;
      if (i >= f3 && i <= f3 + f5 && j >= f4 && j <= f4 + 22.0F) {
         boolean flag = true;
      } else {
         boolean flag1 = false;
      }

      this.yd.a(matrix4f, f3, f4, f2);
      BuiltText builttext = this.k(this.nO, "Добавить блок", 13.0F, Bz);
      float f6 = this.nO.c("Добавить блок", 13.0F);
      builttext.a(matrix4f, f3 + f5 / 2.0F - f6 / 2.0F, f4 + 5.0F, f2);
   }

   private void k(Matrix4f matrix4f, float f, float f1, float f2) {
      float f3 = f + 10.0F;
      float f4 = f1 + 40.0F + 10.0F;
      this.XK.a(matrix4f, f3, f4, f2);
      String s = this.xv.isEmpty() ? "Поиск..." : this.xv;
      Color color = this.xv.isEmpty() ? EaseLinear : Bz;
      BuiltText builttext = this.k(this.nO, s, 13.0F, color);
      builttext.a(matrix4f, f3 + 8.0F, f4 + 8.0F, f2);
      if (this.cs && System.currentTimeMillis() % 1000L < 500L) {
         float f5 = f3 + 8.0F + this.nO.c(this.xv, 13.0F);
         BuiltText builttext1 = this.k(this.nO, "|", 13.0F, Bz);
         builttext1.a(matrix4f, f5, f4 + 8.0F, f2);
      }
   }

   private void l(Matrix4f matrix4f, float f, float f1, int i, int j, float f2) {
      float f3 = f + 10.0F;
      float f4 = f1 + 40.0F + 26.0F + 15.0F;
      float f5 = 266.0F;
      float f6 = 115.0F;

      for (int k = 0; k < this.avo.size(); k++) {
         Block block = this.avo.get(k);
         float f7 = f4 + k * 22.0F - this.aum;
         if (!(f7 + 22.0F < f4) && !(f7 > f4 + f6)) {
            boolean flag = i >= f3 && i <= f3 + f5 && j >= f7 && j <= f7 + 22.0F;
            if (flag) {
               RectangleCache.b(f5, 22.0F, 4.0F).a(matrix4f, f3, f7, f2 * 0.3F);
            }

            Identifier identifier = Registries.BLOCK.getId(block);
            String s = identifier.getPath().replace("_", " ");
            BuiltText builttext = this.k(this.nO, s, 13.0F, Bz);
            builttext.a(matrix4f, f3 + 8.0F, f7 + 5.0F, f2);
         }
      }
   }

   private Matrix4f m(Matrix4f matrix4f, float f, float f1, float f2, float f3) {
      if (f2 == 1.0F) {
         return matrix4f;
      } else {
         float f4 = f + 143.0F;
         float f5 = f1 + f3 * 0.5F;
         this.agJ.set(matrix4f);
         this.agJ.translate(f4, f5, 0.0F);
         this.agJ.scale(f2, f2, 1.0F);
         this.agJ.translate(-f4, -f5, 0.0F);
         return this.agJ;
      }
   }

   private void n(CloseButton CloseButton, float f, float f1, int i, int j, float f2, float f3, Matrix4f matrix4f) {
      if (CloseButton != null) {
         float f4 = f + CloseButton.getX();
         float f5 = f1 + CloseButton.getY();
         CloseButton.it = f4;
         CloseButton.atW = f5;
         CloseButton.render(matrix4f, f4, f5, i, j, f2, f3);
      }
   }

   @Override
   protected boolean b(double d0, double d1, int i) {
      if ((!this.pi.g() || !(this.pi.k() < 0.5F)) && !this.apP) {
         if (this.Bl != null && this.Bl.mouseClicked(d0, d1, i)) {
            return true;
         } else if (this.q(d0, d1, i)) {
            return true;
         } else {
            return this.Jx == VisibilityMode.VI ? this.o(d0, d1, i) : this.p(d0, d1, i);
         }
      } else {
         return false;
      }
   }

   private boolean o(double d0, double d1, int i) {
      float f = this.it + 10.0F;
      float f1 = this.atW + 201.0F - 28.0F;
      float f2 = 266.0F;
      if (d0 >= f && d0 <= f + f2 && d1 >= f1 && d1 <= f1 + 22.0F) {
         this.r();
         return true;
      } else {
         float f3 = this.it + 10.0F;
         float f4 = this.atW + 40.0F + 10.0F;
         float f5 = 266.0F;
         float f6 = 121.0F;
         if (d0 >= f3 && d0 <= f3 + f5 && d1 >= f4 && d1 <= f4 + f6) {
            for (BlockListEntryRenderer bleRenderer : this.on) {
               float f7 = f4 + bleRenderer.azQ - this.cF;
               if (bleRenderer.c(d0, d1, i, f3, f7)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean p(double d0, double d1, int j) {
      float f = this.it + 10.0F;
      float f1 = this.atW + 40.0F + 10.0F;
      if (d0 >= f && d0 <= f + 286.0F - 20.0F && d1 >= f1 && d1 <= f1 + 26.0F) {
         this.cs = true;
         return true;
      } else {
         this.cs = false;
         float f2 = this.it + 10.0F;
         float f3 = this.atW + 40.0F + 26.0F + 15.0F;
         float f4 = 266.0F;
         float f5 = 115.0F;
         if (d0 >= f2 && d0 <= f2 + f4 && d1 >= f3 && d1 <= f3 + f5) {
            int i = (int)((d1 - f3 + this.aum) / 22.0);
            if (i >= 0 && i < this.avo.size()) {
               Block block = this.avo.get(i);
               this.s(block);
               return true;
            }
         }

         return false;
      }
   }

   private boolean q(double d0, double d1, int i) {
      float f = this.it + this.ig.getX();
      float f1 = this.atW + this.ig.getY();
      this.ig.it = f;
      this.ig.atW = f1;
      return d0 >= f && d0 <= f + this.ig.getWidth() && d1 >= f1 && d1 <= f1 + this.ig.getHeight() && this.ig.mouseClicked(d0, d1, i);
   }

   @Override
   protected boolean c(double d0, double d1, int i) {
      if (this.Bl != null) {
         this.Bl.mouseReleased(d0, d1, i);
      }

      if (i == 0) {
         if (this.ig != null) {
            this.ig.mouseReleased(d0, d1, i);
         }

         if (this.Jx == VisibilityMode.VI) {
            for (BlockListEntryRenderer bleRenderer : this.on) {
               bleRenderer.d(d0, d1, i);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   protected boolean e(double d0, double d1, double d2, double d3) {
      if (this.Bl != null) {
         return this.Bl.mouseScrolled(d0, d1, d2, d3);
      } else {
         float f = this.it + 10.0F;
         float f1 = 266.0F;
         if (this.Jx == VisibilityMode.VI) {
            float f2 = this.atW + 40.0F + 10.0F;
            float f3 = 121.0F;
            if (d0 >= f && d0 <= f + f1 && d1 >= f2 && d1 <= f2 + f3) {
               this.cF = Math.max(0.0F, Math.min(this.ake, (float)(this.cF - d3 * 20.0)));
               return true;
            }
         } else {
            float f4 = this.atW + 40.0F + 26.0F + 15.0F;
            float f5 = 115.0F;
            if (d0 >= f && d0 <= f + f1 && d1 >= f4 && d1 <= f4 + f5) {
               this.aum = Math.max(0.0F, Math.min(this.anH, (float)(this.aum - d3 * 20.0)));
               return true;
            }
         }

         return false;
      }
   }

   @Override
   protected boolean f(int i, int j, int k) {
      if (this.Jx != VisibilityMode.l || !this.cs) {
         return false;
      } else if (i == 259) {
         if (!this.xv.isEmpty()) {
            this.xv = this.xv.substring(0, this.xv.length() - 1);
            this.d();
         }

         return true;
      } else if (i == 257 || i == 335) {
         this.cs = false;
         return true;
      } else if (i == 256) {
         this.t();
         return true;
      } else {
         return false;
      }
   }

   @Override
   protected boolean h(char c0, int i) {
      if (this.Jx != VisibilityMode.l || !this.cs) {
         return false;
      } else if (!Character.isLetterOrDigit(c0) && c0 != ' ' && c0 != '_') {
         return false;
      } else {
         String s = this.xv;
         this.xv = s + c0;
         this.d();
         return true;
      }
   }

   private void r() {
      this.Jx = VisibilityMode.l;
      this.xv = "";
      this.cs = true;
      this.d();
   }

   private void s(Block block) {
      this.jH.setBlock(block, true, Color.WHITE);
      this.b();
      this.t();
   }

   private void t() {
      this.Jx = VisibilityMode.VI;
      this.xv = "";
      this.cs = false;
   }

   public void u() {
      if (this.Bl != null) {
         this.Bl.b(() -> {
            this.Bl = null;
         });
      }
   }

   static MSDFFont w(BlockListEditor BlockListEditor) {
      return BlockListEditor.nO;
   }

   static BuiltText x(BlockListEditor BlockListEditor, MSDFFont msdffont, String s, float f, Color color) {
      return BlockListEditor.k(msdffont, s, f, color);
   }

   static MSDFFont y(BlockListEditor BlockListEditor) {
      return BlockListEditor.aiL;
   }

   static BuiltText z(BlockListEditor BlockListEditor, MSDFFont msdffont, String s, float f, Color color) {
      return BlockListEditor.k(msdffont, s, f, color);
   }
}
