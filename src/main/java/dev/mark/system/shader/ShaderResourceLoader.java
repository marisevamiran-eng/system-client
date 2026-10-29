package dev.mark.system.shader;

import java.util.ArrayList;
import dev.mark.system.shader.BlurShaderResource;
import dev.mark.system.shader.BorderShaderResource;
import dev.mark.system.shader.ColorPickerShaderResource;
import dev.mark.system.shader.CornerBracketShaderResource;
import dev.mark.system.shader.EntityOutlineResources;
import dev.mark.system.shader.Line3dShaderResource;
import dev.mark.system.shader.LineShaderResource;
import dev.mark.system.shader.LiquidGlassShaderResource;
import dev.mark.system.shader.MSDFFontShaderResource;
import dev.mark.system.shader.OutlineShaderResource;
import dev.mark.system.shader.RectangleShaderResource;
import dev.mark.system.shader.ShaderCache;
import dev.mark.system.shader.TextureShaderResource;
import dev.mark.system.shader.WhiteRectangleShaderResource;
import dev.mark.system.resource.CustomResourceManager;

public class ShaderResourceLoader {
   public ShaderResourceLoader(CustomResourceManager CustomResourceManager) {
      ShaderCache.a();
      ArrayList arraylist = new ArrayList();
      arraylist.addAll(new BorderShaderResource().getResources());
      arraylist.addAll(new RectangleShaderResource().getResources());
      arraylist.addAll(new WhiteRectangleShaderResource().getResources());
      arraylist.addAll(new OutlineShaderResource().getResources());
      arraylist.addAll(new LineShaderResource().getResources());
      arraylist.addAll(new Line3dShaderResource().getResources());
      arraylist.addAll(new TextureShaderResource().getResources());
      arraylist.addAll(new BlurShaderResource().getResources());
      arraylist.addAll(new MSDFFontShaderResource().getResources());
      arraylist.addAll(new ColorPickerShaderResource().getResources());
      arraylist.addAll(new EntityOutlineResources().getResources());
      arraylist.addAll(new CornerBracketShaderResource().getResources());
      arraylist.addAll(new LiquidGlassShaderResource().getResources());
      arraylist.addAll(new HandShaderBundle().b());
      arraylist.forEach(r -> CustomResourceManager.addResource((dev.mark.system.shader.ShaderResource)r));
   }
}
