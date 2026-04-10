package timmychips.colored_packages.content.logistics.box.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import timmychips.colored_packages.ColoredPackages;

import java.awt.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Enum with all the dye colors and the corresponding Style color for the colored package tooltip.
 * <p>The  {@link #styleWithColor(Integer)} method works by creating a style with the color integer value.
 * <p>Enum values are defined in two ways:
 * <ul>
 * <li>  Get the {@link ChatFormatting}.COLOR enum value (the Minecraft preset colors like "gold") and then use {@link ChatFormatting#getColor()} to get the integer value
 * <li>  Create a new color with rgb values, then convert it to the integer value with getRGB()
 */
public enum ColorTooltipFormattingHelper {
    BLACK("Black", styleWithColor(new Color(30, 30, 52).getRGB())),
    BLUE("Blue", styleWithColor(ChatFormatting.BLUE.getColor())),
    BROWN("Brown", styleWithColor(new Color(153, 93, 51).getRGB())),
    CYAN("Cyan", styleWithColor(ChatFormatting.DARK_AQUA.getColor())),
    GRAY("Gray", styleWithColor(ChatFormatting.DARK_GRAY.getColor())),
    GREEN("Green", styleWithColor(new Color(101, 147, 32).getRGB())),
    LIGHT_BLUE("Light Blue", styleWithColor(new Color(113, 169, 255).getRGB())),
    LIGHT_GRAY("Light Gray", styleWithColor(ChatFormatting.GRAY.getColor())),
    LIME("Lime", styleWithColor(new Color(131, 212, 28).getRGB())),
    MAGENTA("Magenta", styleWithColor(ChatFormatting.LIGHT_PURPLE.getColor())),
    ORANGE("Orange", styleWithColor(new Color(255, 159, 42).getRGB())),
    PINK("Pink", styleWithColor(new Color(247, 180, 214).getRGB())),
    PURPLE("Purple", styleWithColor(new Color(159, 58, 212).getRGB())),
    RED("Red", styleWithColor(ChatFormatting.RED.getColor())),
    WHITE("White", styleWithColor(new Color(212, 212, 212).getRGB())),
    YELLOW("Yellow", styleWithColor(ChatFormatting.YELLOW.getColor()));

    // Store enumerate values to map for getting (i.e. converts BLUE, and it's enum to blue=enum and places in map)
    private static final Map<String, ColorTooltipFormattingHelper> LOOKUP = Arrays.stream(values())
            .collect(Collectors.toMap(ColorTooltipFormattingHelper::getName, helper -> helper));
    private final String langName;
    private final Style style;

    ColorTooltipFormattingHelper(String langName, Style style) {
        this.langName = langName;
        this.style = style;
    }

    /**
     *
     * @param rgb The rgb integer color value
     * @return The style with the color input
     */
    public static Style styleWithColor(Integer rgb) {
        return Style.EMPTY.withColor(rgb);
    }

    // Gets the name of the Enum (e.g. BLUE) and converts it to lowercase (e.g. blue)
    public String getName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    /**
     * Gets the style from the enum value Map with enum.style
     * @param color The input color string to get the style from
     * @return The Style color associated with input
     */
    public static Style getByName(String color) {
        color = color.toLowerCase();
        try {
            return LOOKUP.get(color).style;
        } catch (Exception e) {
            ColoredPackages.LOGGER.info("Unable to get style object from color: {}", color);
        }
        return Style.EMPTY;
    }

    /**
     * Gets the color/name with proper capitalization and spacing; i.e. "Light Blue" instead of "light_blue"
     * @param color the input color to get the lang for
     * @return the lang name of the color
     */
    public static String getLangName(String color) {
        try {
            return LOOKUP.get(color).langName;
        } catch (Exception e) {
            ColoredPackages.LOGGER.info("Unable to find lang name for color: {}", color);
        }
        return color; // Fallback to color id if it can't find
    }

    public static void init() {};
}
