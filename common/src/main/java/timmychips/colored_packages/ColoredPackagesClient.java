package timmychips.colored_packages;

import com.simibubi.create.AllParticleTypes;
import io.github.fabricators_of_create.porting_lib.event.client.ParticleManagerRegistrationCallback;
import net.createmod.ponder.foundation.PonderIndex;
import timmychips.colored_packages.infastructure.ponder.ColoredPonderPlugin;
import timmychips.colored_packages.infastructure.ponder.scenes.ColoredPackagerScenes;

public class ColoredPackagesClient {
    public static void init() {
        ColoredPackages.LOGGER.info("Registering ponder");
        PonderIndex.addPlugin(new ColoredPonderPlugin());
//        ParticleManagerRegistrationCallback.EVENT.register(AllPackageParticles::registerFactories); // fabric only?
    }
}
