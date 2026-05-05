package timmychips.colored_packages.neoforge.mixin;

import com.google.common.collect.ImmutableMap;
import dev.architectury.platform.Platform;
import net.neoforged.fml.ModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

// Optionally applies Mixins for compat mods, only applies Mixin if that mod is installed
public class ColoredPackagesMixinPlugin implements IMixinConfigPlugin {

    public static final String FACTORY_LOGISTIC_ID = "fluidlogistics";
    private static final String REFMAP_CONFIG = "colored_packages.mixins.json";

    private static final List<String> LOGISTICS_MIXINS = List.of(
            "timmychips.colored_packages.neoforge.mixin.compat.fluidlogistic.HandPointerPackagerTogglePacketMixin",
            "timmychips.colored_packages.neoforge.mixin.compat.fluidlogistic.HandPointerInteractionHandler"
    );

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (ModList.get() != null) {
            boolean fluidLogisticsLoaded = ModList.get().isLoaded(FACTORY_LOGISTIC_ID);
            if (LOGISTICS_MIXINS.contains(mixinClassName)) {
                return fluidLogisticsLoaded;
            }
        }
        return true;
    }

    @Override
    public String getRefMapperConfig() {
        return REFMAP_CONFIG;
    }

    // Unused but here for interface impl

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }


    @Override
    public void onLoad(String mixinPackage) {

    }
}
