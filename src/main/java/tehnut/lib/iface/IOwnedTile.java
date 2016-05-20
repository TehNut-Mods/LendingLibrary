package tehnut.lib.iface;

import java.util.UUID;

/**
 * Implement on Tile Entities that should save an owner.
 */
public interface IOwnedTile {

    UUID getOwner();

    String getOwnerName();
}
