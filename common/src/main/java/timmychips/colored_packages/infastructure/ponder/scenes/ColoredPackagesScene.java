package timmychips.colored_packages.infastructure.ponder.scenes;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import timmychips.colored_packages.infastructure.ponder.ColoredPonderUtil;

public class ColoredPackagesScene {

    private static BlockPos arrayCurrentPos;

    public static void allColoredPackages(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("colored_packages", "All colored package styles");
        scene.configureBasePlate(0, 0, 7);
        scene.showBasePlate();

        BlockPos boxStartPos = new BlockPos(1, 1, 1); // initial package/box position
        arrayCurrentPos = boxStartPos;

        // Add box to grid with incremental position offset + color
        addBoxToArray(scene, 0, 0, DyeColor.BLACK);
        addBoxToArray(scene, 1, 0, DyeColor.BLUE);
        addBoxToArray(scene, 1, 0, DyeColor.BROWN);
        addBoxToArray(scene, 1, 0, DyeColor.CYAN);
        addBoxToArray(scene, 1, 0, DyeColor.GRAY);
        addBoxToArray(scene, 1, 0, DyeColor.GREEN); // lonely, pattern outlier boi
        // Next row
        addBoxToArray(scene, -5, 2, DyeColor.LIGHT_BLUE);
        addBoxToArray(scene, 1, 0, DyeColor.LIGHT_GRAY);
        addBoxToArray(scene, 1, 0, DyeColor.LIME);
        addBoxToArray(scene, 1, 0, DyeColor.MAGENTA);
        addBoxToArray(scene, 1, 0, DyeColor.ORANGE);
        // Next row
        addBoxToArray(scene, -4, 2, DyeColor.PINK);
        addBoxToArray(scene, 1, 0, DyeColor.PURPLE);
        addBoxToArray(scene, 1, 0, DyeColor.RED);
        addBoxToArray(scene, 1, 0, DyeColor.WHITE);
        addBoxToArray(scene, 1, 0, DyeColor.YELLOW);
    }

    // Increment current array position first, then create package/box
    private static void addBoxToArray(CreateSceneBuilder scene, int incrementX, int incrementY, DyeColor color) {
        arrayCurrentPos = arrayCurrentPos.offset(incrementX, 0, incrementY);
        ColoredPonderUtil.createColoredPackage(scene, arrayCurrentPos, color);
    }
}
