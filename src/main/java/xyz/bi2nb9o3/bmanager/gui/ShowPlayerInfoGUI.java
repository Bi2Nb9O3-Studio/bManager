package xyz.bi2nb9o3.bmanager.gui;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import io.wispforest.owo.ui.container.ScrollContainer;

import java.util.Objects;

public class ShowPlayerInfoGUI extends BaseOwoScreen<FlowLayout> {
    private final Entity target;
    private final String[] detailsConf;

    public ShowPlayerInfoGUI(Entity target, String[] detailsConf) {
        this.target = target;
        this.detailsConf = detailsConf;
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
//      Set Background
        rootComponent.surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        rootComponent.child(
                Containers.horizontalFlow(Sizing.fill(80), Sizing.fill(80))
                        .child(
                                Components.entity(Sizing.fixed(150), this.target).allowMouseRotation(true).showNametag(true).scaleToFit(true).lookAtCursor(true)
                        )
                        .child(
                                Containers.verticalFlow(Sizing.fill(40), Sizing.fill(80))
                                        .child(Components.label(Text.literal("Player info")).lineHeight(35))
                                        .id("details")
                        )
                        .horizontalAlignment(HorizontalAlignment.CENTER)

        );
        int i = 1;
        for (String s : detailsConf) {
            rootComponent.childById(FlowLayout.class, "details").child(
                    Components.label(Text.literal(s))
            );
            i++;

        }

    }

}

