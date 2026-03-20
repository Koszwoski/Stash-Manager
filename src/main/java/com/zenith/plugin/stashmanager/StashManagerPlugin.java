package com.zenith.plugin.stashmanager;

import com.zenith.plugin.api.Plugin;
import com.zenith.plugin.api.PluginAPI;
import com.zenith.plugin.api.ZenithProxyPlugin;
import com.zenith.plugin.stashmanager.command.StashCommand;
import com.zenith.plugin.stashmanager.command.StashSearchCommand;
import com.zenith.plugin.stashmanager.command.StashSupplyCommand;
import com.zenith.plugin.stashmanager.index.ContainerIndex;

@Plugin(
    id = BuildConstants.PLUGIN_ID,
    version = BuildConstants.VERSION,
    description = "Container scanning, indexing, and multi-platform friendly inventory management",
    authors = {"MOAR"},
    mcVersions = {BuildConstants.MC_VERSION}
)
public class StashManagerPlugin implements ZenithProxyPlugin {

    private static ContainerIndex sharedIndex;
    private static StashManagerModule sharedModule;

    @Override
    public void onLoad(PluginAPI pluginAPI) {
        var config = pluginAPI.registerConfig(BuildConstants.PLUGIN_ID, StashManagerConfig.class);
        sharedIndex = new ContainerIndex();
        sharedModule = new StashManagerModule(config, sharedIndex);

        pluginAPI.registerModule(sharedModule);
        pluginAPI.registerCommand(new StashCommand(config, sharedModule, sharedIndex));
        pluginAPI.registerCommand(new StashSearchCommand(sharedIndex));
        pluginAPI.registerCommand(new StashSupplyCommand(config));
    }

    public static ContainerIndex getIndex() {
        return sharedIndex;
    }

    public static StashManagerModule getModule() {
        return sharedModule;
    }
}
