package timmychips.colored_packages.neoforge.infastructure.ponder.scenes;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import org.joml.Vector3d;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.neoforge.infastructure.ponder.AddBoxArrayUtil;

import java.util.ArrayList;
import java.util.List;

public class ColoredPackagesScene {

    public static Vector3d arrayCurrentPos;

    private static ArrayList<DyeColor> COLORS = List.of()

    public static void allColoredPackages(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("colored_packages", "All colored package styles");
        scene.configureBasePlate(0, 0, 7);
        scene.showBasePlate();

        BlockPos boxStartPos = new BlockPos(1, 1, 1); // initial package/box position
//        arrayCurrentPos = new Vector3d(boxStartPos.getX(), boxStartPos.getY(), boxStartPos.getZ());
        Vector3d startPos = new Vector3d(1, 1, 1);

        AddBoxArrayUtil addBoxUtil = new AddBoxArrayUtil(startPos);

        ColoredPackages.LOGGER.info("colored package scene!");

        // Add box to grid with incremental position offset + color
        addBoxUtil.add(scene, 0, 0, DyeColor.BLACK);
        addBoxUtil.add(scene, 1, 0, DyeColor.BLUE);
        addBoxUtil.add(scene, 1, 0, DyeColor.BROWN);
        addBoxUtil.add(scene, 1, 0, DyeColor.CYAN);
        addBoxUtil.add(scene, 1, 0, DyeColor.GRAY);
        // Next row
        addBoxUtil.add(scene, -4.5, 2, DyeColor.GREEN); // lonely, pattern outlier boi
        addBoxUtil.add(scene, 1, 0, DyeColor.LIGHT_BLUE);
        addBoxUtil.add(scene, 1, 0, DyeColor.LIGHT_GRAY);
        addBoxUtil.add(scene, 1, 0, DyeColor.LIME);
        addBoxUtil.add(scene, 1, 0, DyeColor.MAGENTA);
        addBoxUtil.add(scene, 1, 0, DyeColor.ORANGE);
        // Next row
        addBoxUtil.add(scene, -4.5, 2, DyeColor.PINK);
        addBoxUtil.add(scene, 1, 0, DyeColor.PURPLE);
        addBoxUtil.add(scene, 1, 0, DyeColor.RED);
        addBoxUtil.add(scene, 1, 0, DyeColor.WHITE);
        addBoxUtil.add(scene, 1, 0, DyeColor.YELLOW);
    }

    /*
    // Increment current array position first, then create package/box
    public static void addBoxToArray(CreateSceneBuilder scene, Vector3d currentPos, double incrementX, double incrementY, DyeColor color) {
        arrayCurrentPos = arrayCurrentPos.add(incrementX, 0, incrementY);
        ColoredPonderUtil.createColoredPackage(scene, arrayCurrentPos, color);
    }

     */
}
