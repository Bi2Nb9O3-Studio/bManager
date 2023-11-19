package xyz.bi2nb9o3.bmanager.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.SectionHeader;

import java.awt.*;
import java.util.Dictionary;

@Modmenu(modId = "bmanager")
@Config(name = "bManager",wrapperName = "BMConfig")
public class BMConfigModel {
    public boolean enableMod=true;

    @SectionHeader("DatabaseConnection")

    public String jdbcURL="/Default/ JDBC URL";

    public String dbUsername="/Default/ Username";

    public String dbPassword="/Default/ Password";

    @SectionHeader("DatabaseSelector")

    public DataUseAsIdChoices DataUseAsId=DataUseAsIdChoices.PlayerName;

    public enum DataUseAsIdChoices {
        ClientUUID, PlayerName;
    }

    public boolean onlyMatchPlayer=true;

    public String indexRowName="id";

    public String showDetailsJson="[{'table':'1','row':'2',show:['Player Money','$']}]";
}
