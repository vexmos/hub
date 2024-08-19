package net.vexmos.hub.database;

import net.vexmos.hub.api.SpigotConfig;

public class SetupConfig {

    SpigotConfig database = new SpigotConfig("database.yml");

    public SetupConfig(){
        database.saveDefault();
    }

}
