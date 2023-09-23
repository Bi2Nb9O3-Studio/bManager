package xyz.bi2nb9o3.bmanager.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "bmanager")
@Config(name = "bManager",wrapperName = "BMConfig")
public class BMConfigModel {
    public boolean enableMod=true;
}
