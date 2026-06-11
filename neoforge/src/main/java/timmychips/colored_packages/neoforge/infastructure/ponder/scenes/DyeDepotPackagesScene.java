package timmychips.colored_packages.neoforge.infastructure.ponder.scenes;

import com.ninni.dye_depot.registry.DDDyes;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import org.joml.Vector3d;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.neoforge.infastructure.ponder.AddBoxArrayUtil;

public class DyeDepotPackagesScene extends ColoredPackagesScene {
    public static void allDyeDepotPackages(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("colored_packages", "All dye depot package styles");
        scene.configureBasePlate(0, 0, 7);
        scene.showBasePlate();

        BlockPos boxStartPos = new BlockPos(1, 1, 1); // initial package/box position
//        arrayCurrentPos = new Vector3d(boxStartPos.getX(), boxStartPos.getY(), boxStartPos.getZ());
        Vector3d startPos = new Vector3d(1);

        AddBoxArrayUtil addBoxUtil = new AddBoxArrayUtil(startPos);

        ColoredPackages.LOGGER.info("dye depot package scene!");

        // Add box to grid with incremental position offset + color
        addBoxUtil.add(scene, 0, 0, DDDyes.AMBER.get());
        addBoxUtil.add(scene, 1, 0, DDDyes.AQUA.get());
        addBoxUtil.add(scene, 1, 0, DDDyes.BEIGE.get());
        addBoxUtil.add(scene, 1, 0, DDDyes.CORAL.get());
        addBoxUtil.add(scene, 1, 0, DDDyes.FOREST.get());
        // Next row
        addBoxUtil.add(scene, -4.5, 2, DDDyes.GINGER.get()); // lonely, pattern outlier boi
        addBoxUtil.add(scene, 1, 0, DDDyes.INDIGO.get());
        addBoxUtil.add(scene, 1, 0, DDDyes.MAROON.get());
        addBoxUtil.add(scene, 1, 0, DDDyes.MINT.get());
        addBoxUtil.add(scene, 1, 0, DDDyes.NAVY.get());
        addBoxUtil.add(scene, 1, 0, DDDyes.OLIVE.get());
        // Next row
        addBoxUtil.add(scene, -4.5, 2, DDDyes.ROSE.get());
        addBoxUtil.add(scene, 1, 0, DDDyes.SLATE.get());
        addBoxUtil.add(scene, 1, 0, DDDyes.TAN.get());
        addBoxUtil.add(scene, 1, 0, DDDyes.TEAL.get());
        addBoxUtil.add(scene, 1, 0, DDDyes.VERDANT.get());
    }
}
