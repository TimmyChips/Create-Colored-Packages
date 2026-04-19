package timmychips.colored_packages;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;

import java.util.function.UnaryOperator;

public class ColoredDataComponent {
    private static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPES = DeferredRegister.create(ColoredPackages.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final DataComponentType<DyeColor> PACKAGE_COLOR = register("package_color",
            builder -> builder.persistent(DyeColor.CODEC).networkSynchronized(DyeColor.STREAM_CODEC));

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        DataComponentType<T> type = builder.apply(DataComponentType.builder()).build();
        COMPONENT_TYPES.register(name, () -> {
//            ColoredPackages.LOGGER.info("name: {}", name);
            return type;
        });
        return type;
    }

    public static void init() {
        COMPONENT_TYPES.register();
    }
}
