package dev.mark.system.data;

import java.util.LinkedHashMap;
import java.util.List;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Vec3d;

public class ItemCluster {
   public final Vec3d ajP;
   public final List<ItemEntity> OJ;
   public final LinkedHashMap<String, Integer> auT;

   public ItemCluster(Vec3d vec3d, List<ItemEntity> list, LinkedHashMap<String, Integer> linkedhashmap) {
      this.ajP = vec3d;
      this.OJ = list;
      this.auT = linkedhashmap;
   }
}
