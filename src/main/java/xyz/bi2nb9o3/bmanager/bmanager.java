package xyz.bi2nb9o3.bmanager;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class bmanager implements ClientModInitializer{
    public static final Logger LOGGER = LoggerFactory.getLogger("bManager");
    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello world!");

    }
}