package timmychips.colored_packages.neoforge.infastructure.ponder;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.minecraft.world.item.DyeColor;
import org.joml.Vector3d;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;
import timmychips.colored_packages.neoforge.content.logistics.box.ColoredPackageEntityForge;

public class PonderCreatePackage {
    public static ElementLink<EntityElement> create(CreateSceneBuilder scene, Vector3d pos, DyeColor color) {
        return scene.world().createEntity(l -> {
            ColoredPackages.LOGGER.info("position: {}", new Vector3d(pos.x(), pos.y(), pos.z()));
            ColoredPackageEntityForge coloredBox = new ColoredPackageEntityForge(l, pos.x(), pos.y(), pos.z());
            coloredBox.box = ColoredPackageStyles.getDefaultBoxFromColor(color);

            return coloredBox;
        });
    }
}
