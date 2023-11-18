package xyz.bi2nb9o3.bmanager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.text.Text;
import xyz.bi2nb9o3.bmanager.config.BMConfig;
import xyz.bi2nb9o3.bmanager.config.BMConfigModel;
import xyz.bi2nb9o3.bmanager.gui.ShowPlayerInfoGUI;
import xyz.bi2nb9o3.bmanager.network.database;

import java.util.Objects;

public class bmanager implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("bManager");
    public static final BMConfig CONFIG;
    static {
        CONFIG = BMConfig.createAndLoad();
    }
    private Object getEntity(){
//        Get the entity in sight
        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;

        switch(hit.getType()) {
            case MISS:
                //nothing near enough
                return 0;
            case BLOCK:
                BlockHitResult blockHit = (BlockHitResult) hit;
                BlockPos blockPos = blockHit.getBlockPos();
                BlockState blockState = client.world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                return 0;
            case ENTITY:
                EntityHitResult entityHit = (EntityHitResult) hit;
                Entity entity = entityHit.getEntity();
                return entity;
            default:
                return 0;
        }
    }
    private static KeyBinding mainKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
    "key.bmanager.main", // The translation key of the keybinding's name
    InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
    GLFW.GLFW_KEY_RIGHT_CONTROL, // The keycode of the key
    "category.bmanager.main" // The translation key of the keybinding's category.
    ));
    private static KeyBinding testKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.bmanager.test", // The translation key of the keybinding's name
            InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
            GLFW.GLFW_KEY_K, // The keycode of the key
            "category.bmanager.main" // The translation key of the keybinding's category.
    ));

    public void sendMsg(Text text){
        MinecraftClient.getInstance().player.sendMessage(Text.literal(("§9§l[bManager]§r "+text.getString())),true);
    }
    @Override
    public void onInitializeClient() {
//      Keyboard Click Event
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (mainKeyBinding.wasPressed()) {
                if(!CONFIG.enableMod()){
                    return;
                }
                Object objRe=getEntity();
                if(Objects.equals(objRe.toString(), "0")){
                    //Missing
                    sendMsg(Text.translatable("text.bmanager.message.miss_target"));
                    return;
                }else{
                    Entity re=(Entity) objRe;
                    sendMsg(Text.translatable("text.bmanager.message.find_entity",re.getDisplayName().getString()));
                    String sid = null;
                    if(CONFIG.DataUseAsId()== BMConfigModel.DataUseAsIdChoices.ClientUUID){
                        sid=re.getUuidAsString();
                    }else if(CONFIG.DataUseAsId()==BMConfigModel.DataUseAsIdChoices.PlayerName){
                        sid=re.getDisplayName().toString();
                    }
                    client.setScreen(new ShowPlayerInfoGUI(re,database.getData(sid,database.analyzeData(CONFIG.showDetailsJson()))));
                    //database.getData(sid,database.analyzeData(CONFIG.showDetailsJson()))
                }
            }
//            while (testKeyBinding.wasPressed()){
//
//            }
        });

    }

}

