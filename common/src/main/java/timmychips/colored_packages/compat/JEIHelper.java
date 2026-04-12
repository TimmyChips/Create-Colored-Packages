package timmychips.colored_packages.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import timmychips.colored_packages.ColoredPackages;
import timmychips.colored_packages.ModifyCreativeMenu;
import timmychips.colored_packages.content.logistics.box.ColoredPackageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Annotation and implementation means JEI will register the calls automatically without calling/referencing it
 */
@JeiPlugin
public class JEIHelper implements IModPlugin {
    private static final ResourceLocation ID = ColoredPackages.asResource("jei_plugin");

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ID;
    }

    /**
     * Adds colored package items with PackageColor tag at runtime since the colored packages aren't in a Creative Mode tab, nor do we use the default item registration that JEI expects
     * {@link IModPlugin#registerItemSubtypes(ISubtypeRegistration)} didn't work since that needs an already existing item to add more "subtypes", like tags to.
     * @param jeiRuntime The runtime to add ingredients to
     */
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        List<Item> packageItems = ModifyCreativeMenu.collectPackageItems(); // Get list of package items
        List<ItemStack> packageStacks = new ArrayList<>();

        // Create item stacks from colored package items and set PackageColor tag
        for (Item item : packageItems) {
            ItemStack stack = new ItemStack(item);
            ColoredPackageItem.setColor(stack, DyeColor.RED); // Set PackageColor to color we want

            packageStacks.add(stack);
        }

        // Adds package items at runtime
        jeiRuntime.getIngredientManager().addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, packageStacks);
    }
}
