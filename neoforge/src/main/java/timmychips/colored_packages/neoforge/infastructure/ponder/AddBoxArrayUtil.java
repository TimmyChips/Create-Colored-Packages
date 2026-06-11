package timmychips.colored_packages.neoforge.infastructure.ponder;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.minecraft.world.item.DyeColor;
import org.joml.Vector3d;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;
import timmychips.colored_packages.infastructure.ponder.ColoredPonderUtil;
import timmychips.colored_packages.neoforge.content.logistics.box.ColoredPackageEntityForge;

public class AddBoxArrayUtil {

    Vector3d currentPos;

    public AddBoxArrayUtil(Vector3d startPos) {
        this.currentPos = startPos.add(0.5, 0, 0.5); // start pos with offset so in middle of block
    }

    public void resetCurrentPos() {
        this.currentPos = new Vector3d(1);
    }

    // Increment current array position first, then create package/box
    public ElementLink<EntityElement> add(CreateSceneBuilder scene, double incrementX, double incrementZ, DyeColor color) {
        return scene.world().createEntity(l -> {

//            this.currentPos = this.currentPos.add(incrementX, 0, incrementZ);
            ColoredPackages.LOGGER.info("POS: {}", this.currentPos);

            ColoredPackages.LOGGER.info("position: {}", new Vector3d(this.currentPos.x(), this.currentPos.y(), this.currentPos.z()));
            ColoredPackageEntityForge coloredBox = new ColoredPackageEntityForge(l, this.currentPos.x(), this.currentPos.y(), this.currentPos.z());
            coloredBox.box = ColoredPackageStyles.getDefaultBoxFromColor(color);

            return coloredBox;
        });
    }

    public ElementLink<EntityElement> addPos(CreateSceneBuilder scene, double x, double z, DyeColor color) {
        return scene.world().createEntity(l -> {
            ColoredPackageEntityForge coloredBox = new ColoredPackageEntityForge(l, this.currentPos.x() + x, this.currentPos.y(), this.currentPos.z() + z);
            coloredBox.box = ColoredPackageStyles.getDefaultBoxFromColor(color);

            return coloredBox;
        });
    }

    public static ElementLink<EntityElement> createBox(CreateSceneBuilder scene, Vector3d pos, DyeColor color) {
        return scene.world().createEntity(l -> {
            ColoredPackages.LOGGER.info("position: {}", new Vector3d(pos.x(), pos.y(), pos.z()));
            ColoredPackageEntityForge coloredBox = new ColoredPackageEntityForge(l, pos.x(), pos.y(), pos.z());
            coloredBox.box = ColoredPackageStyles.getDefaultBoxFromColor(color);

            return coloredBox;
        });
    }
}
