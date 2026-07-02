package mod.chiselsandbits.api.item.chisel;

import com.google.common.collect.Lists;
import mod.chiselsandbits.api.chiseling.mode.IChiselMode;
import mod.chiselsandbits.api.item.change.IChangeTrackingItem;
import mod.chiselsandbits.api.item.click.ILeftClickControllingItem;
import mod.chiselsandbits.api.item.withhighlight.IWithHighlightItem;
import mod.chiselsandbits.api.item.withmode.IWithModeItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IChiselingItem extends ILeftClickControllingItem, IWithModeItem<IChiselMode>, IWithHighlightItem, IChangeTrackingItem
{

    boolean isDamageableDuringChiseling();

    /**
     * Returns the mode currently selected by the given player, shared across every stack of this item that
     * player carries for the duration of the current session.
     *
     * @param player The player whose selected mode is queried.
     * @param stack The stack in question.
     * @return The mode the given player currently has selected.
     */
    @NotNull
    IChiselMode getMode(final Player player, final ItemStack stack);

    /**
     * Sets the mode currently selected by the given player, shared across every stack of this item that
     * player carries for the duration of the current session.
     *
     * @param player The player whose selected mode is being set.
     * @param stack The stack that triggered the change.
     * @param mode The mode to select.
     */
    void setMode(final Player player, final ItemStack stack, final IChiselMode mode);

    /**
     * Sets the mode with the given index as currently selected by the given player.
     *
     * @param player The player whose selected mode is being set.
     * @param stack The stack that triggered the change.
     * @param modeIndex The modes index to select.
     */
    default void setMode(final Player player, final ItemStack stack, final int modeIndex) {
        if (modeIndex == -1)
            return;

        final List<IChiselMode> modes = Lists.newArrayList(getPossibleModes());
        setMode(player, stack, modes.get(modeIndex));
    }
}
