package timmychips.colored_packages.compat.neoforge;

import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.zlt.create_vibrant_vaults.block.ModBlocks;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.compat.VibrantVaultsCompat;

import java.util.HashMap;
import java.util.stream.Collectors;

import static timmychips.colored_packages.compat.VibrantVaultsCompat.VIBRANT_PACKAGERS_MAP;

public class VibrantVaultsCompatImpl {

    public static void setVibrantPackagerSet() {
        if (VibrantVaultsCompat.HAS_VIBRANT_VAULTS) {
            ColoredPackages.LOGGER.info("Setting hash map!");
            VIBRANT_PACKAGERS_MAP = new HashMap<>(
                    ModBlocks.VIBRANT_PACKAGERS
                            .stream()
                            .filter(block -> block.get().color.ordinal() < 16)
                            .collect(Collectors.toMap(
                                            block -> block.getId(),
                                    block -> {
                                        ColoredPackages.LOGGER.info("color: {}", DyeColor.values()[block.get().color.ordinal()]);
                                        return DyeColor.values()[block.get().color.ordinal()];
//                                        return DyeColor.RED;
                                    })
                            )
            );
            ColoredPackages.LOGGER.info("MAP: {}", VIBRANT_PACKAGERS_MAP);
        }
    }
}
