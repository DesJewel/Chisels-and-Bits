package mod.chiselsandbits.logic;

import mod.chiselsandbits.chiseling.ChiselingManager;
import mod.chiselsandbits.chiseling.GlobalToolModeManager;

public class ServerStartHandler
{

    public static void onServerStart() {
        ChiselingManager.getInstance().onServerStarting();
        GlobalToolModeManager.getInstance().onServerStarting();
    }
}
