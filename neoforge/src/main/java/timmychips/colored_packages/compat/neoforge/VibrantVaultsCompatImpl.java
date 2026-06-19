package timmychips.colored_packages.compat.neoforge;

import net.minecraft.world.item.DyeColor;
import net.zlt.create_vibrant_vaults.block.ModBlocks;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.compat.VibrantVaultsCompat;

import java.util.stream.Collectors;

import static timmychips.colored_packages.compat.VibrantVaultsCompat.VIBRANT_PACKAGERS_MAP;

public class VibrantVaultsCompatImpl {

    public static void setVibrantPackagerSet() {
        if (!VibrantVaultsCompat.HAS_VIBRANT_VAULTS) return;

        VIBRANT_PACKAGERS_MAP = ModBlocks.VIBRANT_PACKAGERS
            .stream()
            .filter(block -> block.get().color.ordinal() < 16)
            .collect(Collectors.toMap(
                            block -> block.getId(),
                    block -> DyeColor.values()[block.get().color.ordinal()])
            );
    }
}
