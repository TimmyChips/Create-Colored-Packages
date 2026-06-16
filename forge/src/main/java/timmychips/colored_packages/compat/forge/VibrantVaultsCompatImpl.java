package timmychips.colored_packages.compat.forge;

import net.minecraft.world.item.DyeColor;
import net.zlt.create_vibrant_vaults.block.ModBlocks;
import timmychips.colored_packages.compat.VibrantVaultsCompat;

import java.util.stream.Collectors;

import static timmychips.colored_packages.compat.VibrantVaultsCompat.VIBRANT_PACKAGERS_MAP;

public class VibrantVaultsCompatImpl {

    public static void setVibrantPackagerSet() {
        if (!VibrantVaultsCompat.HAS_VIBRANT_VAULTS) return;

        // Add map of all vibrant vault packager blocks and their respective DyeColor value
        VIBRANT_PACKAGERS_MAP = ModBlocks.VIBRANT_PACKAGERS
                .stream()
                .filter(block -> block.get().color.ordinal() < 16)
                .collect(Collectors.toMap(
                        block -> block.getId(),
                        block -> DyeColor.values()[block.get().color.ordinal()])
                );
    }
}