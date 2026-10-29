package dev.mark.system.gui.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record ScoreboardEntryRecord(Text name,
   int score,
   @Nullable Text formattedScore,
   int scoreWidth) {

}
