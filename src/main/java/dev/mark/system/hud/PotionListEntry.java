package dev.mark.system.hud;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;

public class PotionListEntry extends ListEntry<RegistryEntry<StatusEffect>> {
   public final String TK;
   public String BP;
   public final RegistryEntry<StatusEffect> zk;
   public final StatusEffectInstance ug;

   public PotionListEntry(String s, String s1, RegistryEntry<StatusEffect> registryentry, StatusEffectInstance statuseffectinstance, String s2) {
      super(registryentry, s2);
      this.TK = s;
      this.BP = s1;
      this.zk = registryentry;
      this.ug = statuseffectinstance;
   }

   public void a(String s) {
      this.BP = s;
   }

   @Override
   public void a() {
   }
}
