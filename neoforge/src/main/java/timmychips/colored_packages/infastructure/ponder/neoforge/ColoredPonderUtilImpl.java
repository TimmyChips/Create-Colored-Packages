package timmychips.colored_packages.infastructure.ponder.neoforge;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;
import timmychips.colored_packages.neoforge.content.logistics.box.ColoredPackageEntityForge;

public class ColoredPonderUtilImpl {
    // Create colored package at given position and color
    public static ElementLink<EntityElement> createPackagePlatform(CreateSceneBuilder scene, BlockPos pos, DyeColor color) {
        return scene.world().createEntity(l -> {
            double offset = 0.5; // Offset so box is in middle of block
            ColoredPackageEntityForge coloredBox = new ColoredPackageEntityForge(l, pos.getX() + offset, pos.getY() + offset, pos.getZ() + offset);
            coloredBox.box = ColoredPackageStyles.getDefaultBoxFromColor(color);

            return coloredBox;
        });
    }
}
