package timmychips.colored_packages.infastructure.ponder.scenes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import com.simibubi.create.infrastructure.ponder.scenes.highLogistics.PonderHilo;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.WorldSectionElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import timmychips.colored_packages.AllDyedBlocks;
import timmychips.colored_packages.content.logistics.DyedPackagerBlockEntity;
import timmychips.colored_packages.content.logistics.box.ColoredPackageStyles;

import java.util.Optional;

public class ColoredPackagerScenes {
    public static void coloredPackager(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("dyed_packager", "Creating colored packages");
        scene.configureBasePlate(0, 0, 7);
        scene.showBasePlate();

        Selection chest1 = util.select()
                .position(2, 2, 2);
        BlockPos funnel1 = util.grid()
                .at(1, 2, 1);
        Selection funnel1S = util.select()
                .position(funnel1);
        BlockPos packager1 = util.grid()
                .at(2, 2, 1);
        Selection packager1S = util.select()
                .position(packager1);
        Selection largeCog = util.select()
                .position(4, 0, 2);
        Selection cogNBelt = util.select()
                .fromTo(3, 1, 1, 0, 1, 1)
                .add(util.select()
                        .position(3, 1, 2));
        BlockPos lever = util.grid()
                .at(2, 3, 1);
        Selection scaff1 = util.select()
                .position(2, 1, 2);
        scene.idle(5);

        scene.world()
                .showSection(chest1, Direction.UP);
        scene.world()
                .showSection(funnel1S, Direction.UP);
        ElementLink<WorldSectionElement> leverL = scene.world()
                .showIndependentSection(util.select()
                        .position(lever), Direction.DOWN);
        scene.world()
                .showSection(largeCog, Direction.UP);
        scene.world()
                .showSection(cogNBelt, Direction.SOUTH);
        scene.world()
                .showSection(scaff1, Direction.UP);
        ElementLink<WorldSectionElement> packager1L = scene.world()
                .showIndependentSection(packager1S, Direction.SOUTH);
//        scene.world().setBlocks(packager1S, AllBlocks.PACKAGER.getDefaultState(), false);
        scene.idle(20);

        scene.overlay()
                .showText(80)
                .text("By default, packagers will create standard packages")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector()
                        .of(2, 3, 1.5));
        scene.idle(60);

        // Activate lever + package; create default package
        scene.world()
                .toggleRedstonePower(util.select()
                        .fromTo(lever, packager1));
        scene.effects()
                .indicateRedstone(lever.west(2)
                        .below());
        ItemStack box = PackageStyles.getDefaultBox()
                .copy();
        PackageItem.addAddress(box, "Warehouse");
        PonderHilo.packagerCreate(scene, packager1, box);
        scene.idle(30);

        // Default package on belt
        scene.world()
                .setKineticSpeed(util.select() // NBT has belt at 0 RPM so we use setKineticSpeed(); also needs to be above createItemOnBelt() so item appears
                        .everywhere(), 32f);
        scene.world()
                .createItemOnBelt(util.grid()
                        .at(1, 1, 1), Direction.EAST, box);
        PonderHilo.packagerClear(scene, packager1);
        scene.world()
                .toggleRedstonePower(util.select()
                        .fromTo(2, 2, 1, 2, 3, 1));
        scene.idle(50);

        // Package hops off belt; item still technically present lmao
        PonderHilo.packageHopsOffBelt(scene, util.grid()
                .at(0, 1, 1), Direction.WEST, box);
        scene.idle(20);

        // Dye overlay
        scene.world()
                .setKineticSpeed(util.select() // Stop belt
                        .everywhere(), 0f);
        scene.overlay()
                .showOutlineWithText(util.select()
                        .position(2, 2, 1), 40)
                .text("Right clicking on a Packager or Re-Packager will dye it")
                .colored(PonderPalette.GREEN)
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(util.vector()
                        .blockSurface(util.grid()
                                .at(2, 2, 1), Direction.WEST));
        scene.idle(60);

        // Dyed packager
        ItemStack applied = new ItemStack(Items.RED_DYE);
        scene.world().setBlocks(packager1S, AllDyedBlocks.DYED_PACKAGER.getDefaultState(), false);
        scene.world().modifyBlockEntity(packager1, DyedPackagerBlockEntity.class, be -> {
            be.color = Optional.of(DyeColor.RED);
        });
        scene.idle(20);
        scene.overlay()
                .showText(90)
                .text("The package will now be colored off the dye applied")
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(util.vector()
                        .blockSurface(util.grid()
                                .at(2, 2, 1), Direction.WEST));
        scene.idle(40);

        // Activate lever + package; colored package
        scene.world()
                .toggleRedstonePower(util.select()
                        .fromTo(lever, packager1));
        scene.effects()
                .indicateRedstone(lever.west(2)
                        .below());
        box = ColoredPackageStyles.getDefaultColoredBox()
                .copy();
        PackageItem.addAddress(box, "Warehouse");
        PonderHilo.packagerCreate(scene, packager1, box);
        scene.idle(30);

        // Colored package on belt
        scene.world()
                .setKineticSpeed(util.select() // Stop belt
                        .everywhere(), 32f);
        scene.world()
                .createItemOnBelt(util.grid()
                        .at(1, 1, 1), Direction.EAST, box);
        PonderHilo.packagerClear(scene, packager1);
        scene.world()
                .toggleRedstonePower(util.select()
                        .fromTo(2, 2, 1, 2, 3, 1));
        scene.idle(50);

        // Water bucket / clear dyed packager
        scene.overlay()
                .showText(90)
                .text("The package will now be colored off the dye applied")
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(util.vector()
                        .blockSurface(util.grid()
                                .at(2, 2, 1), Direction.WEST));
    }
}
