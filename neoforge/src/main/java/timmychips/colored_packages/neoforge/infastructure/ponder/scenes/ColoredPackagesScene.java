package timmychips.colored_packages.neoforge.infastructure.ponder.scenes;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.world.item.DyeColor;
import org.joml.Vector3d;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.neoforge.infastructure.ponder.AddBoxArrayUtil;

import java.util.List;

public class ColoredPackagesScene {
    private static final List<DyeColor> COLORS = List.of(
            DyeColor.BLACK,
            DyeColor.BLUE,
            DyeColor.BROWN,
            DyeColor.CYAN,
            DyeColor.GRAY,
            DyeColor.GREEN,
            DyeColor.LIGHT_BLUE,
            DyeColor.LIGHT_GRAY,
            DyeColor.LIME,
            DyeColor.MAGENTA,
            DyeColor.ORANGE,
            DyeColor.PINK,
            DyeColor.PURPLE,
            DyeColor.RED,
            DyeColor.WHITE,
            DyeColor.YELLOW
    );

    public static void allColoredPackages(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("colored_packages", "All colored package styles");
        scene.configureBasePlate(0, 0, 7);
        scene.showBasePlate();

        Vector3d startPos = new Vector3d(1, 1, 1);
        AddBoxArrayUtil addBoxUtil = new AddBoxArrayUtil(startPos);

        // Add box to grid with incremental position offset + color
        createBoxArray(COLORS, addBoxUtil, scene);
    }

    public static void createBoxArray(List<DyeColor> colorsList, AddBoxArrayUtil addBoxUtil, CreateSceneBuilder scene) {
        int colorIndex = 0;
        for (int row = 0; row < 3; row++) {
            int colsInRow = (row == 1) ? 6 : 5;
            double xOffset = (row == 1) ? -0.5 : 0.0;
            double zOffset = (row == 0) ? 0 : (row == 1) ? 2 : 4;

            for (int col = 0; col < colsInRow; col++) {
                ColoredPackages.LOGGER.info("xOffset: {}, zOffset: {}", xOffset + col, zOffset);
                addBoxUtil.addPos(scene, xOffset + col, zOffset, colorsList.get(colorIndex++));
            }
        }
    }
}
