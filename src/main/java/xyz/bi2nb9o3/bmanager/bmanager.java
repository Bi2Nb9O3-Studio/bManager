package xyz.bi2nb9o3.bmanager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
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
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.text.Text;

import java.util.Objects;

public class bmanager implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("bManager");
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
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (mainKeyBinding.wasPressed()) {
                Object re=getEntity();
                String str;
                if(Objects.equals(re.toString(), "0")){
                    str="No entity got!";
                }else{
                    Entity re1=(Entity) re;
                    if(re1.getType()==client.player.getType()){
                        str=re1.getDisplayName().getString();
                    }else {
                        str="No player got!";
                    }
                }
                client.player.sendMessage(Text.literal(str), false);
                LOGGER.info("Get the entity");
            }
        });

    }
}

