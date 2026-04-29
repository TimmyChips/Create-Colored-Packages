package timmychips.colored_packages.infastructure.ponder;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;

public class ColoredPonderUtil {
    // Create colored package at given position and color
    public static ElementLink<EntityElement> createColoredPackage(CreateSceneBuilder scene, BlockPos pos, DyeColor color) {
        return createPackagePlatform(scene, pos, color);
    }

    // Must be performed with Arch implementation since it's unable to get the Colored Package entity as it's per-platform sided
    @ExpectPlatform
    public static ElementLink<EntityElement> createPackagePlatform(CreateSceneBuilder scene, BlockPos pos, DyeColor color) {
        throw new AssertionError(); // dummy
    }
}
