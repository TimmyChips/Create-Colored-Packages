package timmychips.colored_packages.forge.infastructure.ponder.scenes;

import com.ninni.dye_depot.registry.DDDyes;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import org.joml.Vector3d;
import timmychips.colored_packages.forge.infastructure.ponder.AddBoxArrayUtil;

import java.util.List;

public class DyeDepotPackagesScene extends ColoredPackagesScene {

    private static final List<DDDyes> COLORS = List.of(
            DDDyes.AMBER,
            DDDyes.AQUA,
            DDDyes.BEIGE,
            DDDyes.CORAL,
            DDDyes.FOREST,
            DDDyes.GINGER,
            DDDyes.INDIGO,
            DDDyes.MAROON,
            DDDyes.MINT,
            DDDyes.NAVY,
            DDDyes.OLIVE,
            DDDyes.ROSE,
            DDDyes.SLATE,
            DDDyes.TAN,
            DDDyes.TEAL,
            DDDyes.VERDANT
    );

    public static void allDyeDepotPackages(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("dye_depot_packages", "All dye depot package styles");
        scene.configureBasePlate(0, 0, 7);
        scene.showBasePlate();

        Vector3d startPos = new Vector3d(1);
        AddBoxArrayUtil addBoxUtil = new AddBoxArrayUtil(startPos);

        // Add box to grid with incremental position offset + color
        createDyeDepotArray(COLORS, addBoxUtil, scene);
    }

    // For dye depot colors, streams DDDye values to DyeColor counterpart
    public static void createDyeDepotArray(List<DDDyes> colorsList, AddBoxArrayUtil addBoxUtil, CreateSceneBuilder scene) {
        createBoxArray(colorsList.stream().map(DDDyes::get).toList(), addBoxUtil, scene);
    }
}