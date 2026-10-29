package dev.mark.system.module.util;

import java.util.List;
import net.minecraft.entity.ItemEntity;
import dev.mark.system.data.ItemCluster;

public class CombatScanResult {
   public final List<ItemCluster> auf;
   public final List<ItemEntity> yT;

   public CombatScanResult(List<ItemCluster> list, List<ItemEntity> list1) {
      this.auf = list;
      this.yT = list1;
   }
}
