package timmychips.colored_packages.infastructure.ponder;

import com.simibubi.create.foundation.ponder.PonderWorldBlockEntityFix;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderScenes;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import net.createmod.ponder.api.level.PonderLevel;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import timmychips.colored_packages.ColoredPackages;

public class ColoredPonderPlugin implements PonderPlugin {
    public String getModId() {
        return ColoredPackages.MOD_ID;
    }

    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        AllColoredPonderScenes.register(helper);
    }

    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        AllCreatePonderTags.register(helper);
    }
    public void onPonderLevelRestore(PonderLevel ponderLevel) {
        PonderWorldBlockEntityFix.fixControllerBlockEntities(ponderLevel);
    }

}
