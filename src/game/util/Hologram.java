package game.util;

import java.util.List;

import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author batthomas
 */
public class Hologram {

    private final List<String> lines;
    private final Location loc;
    private static final double ABS = 0.23D;

    public Hologram( Location loc, List<String> lines ) {
        this.lines = lines;
        this.loc = loc;
    }

    public boolean display( Player p ) {
        Location displayLoc = loc.clone().add( 0, ( ABS * lines.size() ) - 1.97D, 0 );
        for ( int i = 0; i < lines.size(); i++ ) {
            Packet<?> packet = getPacket( loc.getWorld(), displayLoc.getX(), displayLoc.getY(), displayLoc.getZ(), lines.get( i ) );
            if ( packet == null ) {
                return false;
            }
            sendPacket( packet, p );
            displayLoc.add( 0, -ABS, 0 );
        }

        return true;
    }

    public boolean displayToAll() {
        Location displayLoc = loc.clone().add( 0, ( ABS * lines.size() ) - 1.97D, 0 );
        for ( int i = 0; i < lines.size(); i++ ) {
            Packet<?> packet = getPacket( loc.getWorld(), displayLoc.getX(), displayLoc.getY(), displayLoc.getZ(), lines.get( i ) );
            if ( packet == null ) {
                return false;
            }
            sendPacket( packet );
            displayLoc.add( 0, -ABS, 0 );
        }

        return true;
    }

    public Packet<?> getPacket( World w, double x, double y, double z, String text ) {

        EntityArmorStand armorStand = new EntityArmorStand( ( ( CraftWorld ) w ).getHandle(), x, y, z );
        armorStand.setCustomName(new ChatMessage(text));
        armorStand.setCustomNameVisible( true );
        armorStand.setNoGravity( true );
        armorStand.setLocation( x, y, z, 0.0F, 0.0F );
        armorStand.setInvisible( true );

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving( armorStand );

        return packet;

    }

    private void sendPacket( Packet<?> packet, Player p ) {
        ( ( CraftPlayer ) p ).getHandle().playerConnection.sendPacket( packet );

    }

    private void sendPacket( Packet<?> packet ) {
        for ( Player p : Bukkit.getOnlinePlayers() ) {
            ( ( CraftPlayer ) p ).getHandle().playerConnection.sendPacket( packet );
        }

    }

}
