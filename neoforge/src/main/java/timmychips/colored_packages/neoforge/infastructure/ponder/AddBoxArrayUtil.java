package timmychips.colored_packages.neoforge.infastructure.ponder;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.minecraft.world.item.DyeColor;
import org.joml.Vector3d;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;
import timmychips.colored_packages.neoforge.content.logistics.box.ColoredPackageEntityForge;

public class AddBoxArrayUtil {

    Vector3d currentPos;

    public AddBoxArrayUtil(Vector3d startPos) {
        this.currentPos = startPos.add(0.5, 0, 0.5); // start pos with offset so in middle of block
    }

    // Add box to ponder world
    public ElementLink<EntityElement> add(CreateSceneBuilder scene, double x, double z, DyeColor color) {
        return scene.world().createEntity(l -> {
            ColoredPackageEntityForge coloredBox = new ColoredPackageEntityForge(l, this.currentPos.x() + x, this.currentPos.y(), this.currentPos.z() + z);
            coloredBox.box = ColoredPackageStyles.getDefaultBoxFromColor(color);

            return coloredBox;
        });
    }
}
