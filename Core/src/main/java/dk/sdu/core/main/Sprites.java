package dk.sdu.core.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.UUID;

/**
 *
 * @author fatihozcelik
 */
public class Sprites extends Sprite {

    private final UUID UUID;

    public Sprites(Texture texture, UUID ID) {
        super(texture);
        this.UUID = ID;
    }

    public UUID getUUID() {
        return UUID;
    }
}
