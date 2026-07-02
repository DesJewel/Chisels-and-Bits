package mod.chiselsandbits.chiseling;

import com.google.common.collect.Maps;
import mod.chiselsandbits.api.chiseling.mode.IChiselMode;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;

/**
 * Tracks the currently selected {@link IChiselMode} per player, for the duration of the current session,
 * shared across every chisel (or every bit) stack that player is carrying.
 * <p>
 * Chisel and bit modes are tracked independently, since the set of modes valid for a chisel is a subset
 * of the modes valid for a bit (bits also support placement-only modes).
 */
public final class GlobalToolModeManager
{
    private static final GlobalToolModeManager INSTANCE = new GlobalToolModeManager();

    public static GlobalToolModeManager getInstance()
    {
        return INSTANCE;
    }

    private UUID activeInstanceId = UUID.randomUUID();

    private final ThreadLocal<UUID> activeThreadId = ThreadLocal.withInitial(() -> activeInstanceId);

    private final ThreadLocal<Map<UUID, IChiselMode>> chiselModes = ThreadLocal.withInitial(Maps::newHashMap);
    private final ThreadLocal<Map<UUID, IChiselMode>> bitModes = ThreadLocal.withInitial(Maps::newHashMap);

    private GlobalToolModeManager()
    {
    }

    public IChiselMode getChiselMode(final Player player)
    {
        validateOrSetup();
        return chiselModes.get().getOrDefault(player.getUUID(), IChiselMode.getDefaultMode());
    }

    public void setChiselMode(final Player player, final IChiselMode mode)
    {
        validateOrSetup();
        chiselModes.get().put(player.getUUID(), mode);
    }

    public IChiselMode getBitMode(final Player player)
    {
        validateOrSetup();
        return bitModes.get().getOrDefault(player.getUUID(), IChiselMode.getDefaultMode());
    }

    public void setBitMode(final Player player, final IChiselMode mode)
    {
        validateOrSetup();
        bitModes.get().put(player.getUUID(), mode);
    }

    public void onServerStarting()
    {
        this.activeInstanceId = UUID.randomUUID();
    }

    private void validateOrSetup()
    {
        final UUID threadId = activeThreadId.get();
        if (threadId != activeInstanceId)
        {
            this.chiselModes.get().clear();
            this.bitModes.get().clear();

            activeThreadId.set(activeInstanceId);
        }
    }
}
