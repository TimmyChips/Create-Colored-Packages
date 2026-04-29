package timmychips.colored_packages;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

// Registers Dyed Packager/Re-Packager as a valid target for Mechanical Arm
public class AllDyedArmInteractionPointTypes {

    static {
        register("dyed_packager", new DyedPackagerType());
    }

    private static <T extends ArmInteractionPointType> void register(String name, T type) {
        Registry.register(CreateBuiltInRegistries.ARM_INTERACTION_POINT_TYPE, ColoredPackages.asResource(name), type);
    }

    public static class DyedPackagerType extends AllArmInteractionPointTypes.PackagerType {
        // Override to check for dyed packager blocks
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return AllDyedBlocks.DYED_PACKAGER.has(state) || AllDyedBlocks.DYED_REPACKAGER.has(state);
        }
    }

    public static void init() {}
}
