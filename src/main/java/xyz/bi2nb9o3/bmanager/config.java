package xyz.bi2nb9o3.bmanager;
import net.fabricmc.simplelibs.simpleconfig.*;

public class config {
    simpleconfig CONFIG = simpleconfig.of( "config" ).request();
    public final String SOME_STRING = CONFIG.getOrDefault( "default.database.host", "127.0.0.1:3306" );
}
