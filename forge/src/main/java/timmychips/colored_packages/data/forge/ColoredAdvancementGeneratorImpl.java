package timmychips.colored_packages.data.forge;

import com.simibubi.create.foundation.advancement.AllAdvancements;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import timmychips.colored_packages.ColoredPackages;

import java.util.function.Consumer;

public class ColoredAdvancementGeneratorImpl implements ForgeAdvancementProvider.AdvancementGenerator {
    public static void platformGenerate() {
    }

    @Override
    public void generate(HolderLookup.Provider arg, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
//        Advancement COLORED_PACKAGER = getAdvancement(AllAdvancements.PACKAGER)
    }

    protected static Advancement.Builder getAdvancement(Advancement parent, ItemLike display, String name, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(display,
                getTranslation("advancement." + name),
                getTranslation("advancement." + name + ".desc"),
                null, frame, showToast, announceToChat, hidden);
    }

    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable(ColoredPackages.MOD_ID + "." + key, args);
    }
}
