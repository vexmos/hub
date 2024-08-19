package net.vexmos.lobby.database;

import net.vexmos.lobby.api.SpigotConfig;

public class SetupConfig {

    SpigotConfig database = new SpigotConfig("database.yml");

    public SetupConfig(){
        database.saveDefault();
    }

}
