package game.util;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.*;

/**
 * This is a chainable builder for {@link ItemStack}s in {@link Bukkit}
 * <br>
 * Example Usage:<br>
 * {@code ItemStack is = new ItemBuilder(Material.LEATHER_HELMET).amount(2).data(4).durability(4).enchantment(Enchantment.ARROW_INFINITE).enchantment(Enchantment.LUCK, 2).name(ChatColor.RED + "the name").lore(ChatColor.GREEN + "line 1").lore(ChatColor.BLUE + "line 2").color(Color.MAROON).build();
 *
 * @author MiniDigger
 * @version 1.2
 */
public class ItemBuilder {

    private final ItemStack is;

    /**
     * Inits the builder with the given {@link Material}
     *
     * @param mat the {@link Material} to start the builder from
     * @since 1.0
     */
    public ItemBuilder( final Material mat ) {
        checkNotNull( mat );

        is = new ItemStack( mat );
    }
    
    /**
     * Inits the builder with the given {@link ItemStack}
     *
     * @param is the {@link ItemStack} to start the builder from
     * @since 1.0
     */
    public ItemBuilder( final ItemStack is ) {
        checkNotNull( is );

        this.is = is;
    }

    /**
     * Changes the amount of the {@link ItemStack}
     *
     * @param amount the new amount to set
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemBuilder amount( final int amount ) {
        checkArgument( amount >= 1 && amount <= is.getMaxStackSize() );

        is.setAmount( amount );
        return this;
    }

    /**
     * Changes the display name of the {@link ItemStack}
     *
     * @param name the new display name to set
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemBuilder name( final String name ) {
        checkNotNull( name );

        final ItemMeta meta = is.getItemMeta();
        meta.setDisplayName( name );
        is.setItemMeta( meta );
        return this;
    }

    /**
     * Adds a new line to the lore of the {@link ItemStack}
     *
     * @param text the new line to add
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemBuilder lore( final String text ) {
        checkNotNull( text );

        final ItemMeta meta = is.getItemMeta();
        List<String> lore = meta.getLore();
        if ( lore == null ) {
            lore = new ArrayList<String>();
        }
        lore.add( text );
        meta.setLore( lore );
        is.setItemMeta( meta );
        return this;
    }

    /**
     * Changes the durability of the {@link ItemStack}
     *
     * @param durability the new durability to set
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemBuilder durability( final int durability ) {
        checkArgument( durability > 1 && durability <= is.getType().getMaxDurability() );

        is.setDurability( (short) durability );
        return this;
    }

    /**
     * Changes the data of the {@link ItemStack}
     *
     * @param data the new data to set
     * @return this builder for chaining
     * @since 1.0
     */
    @SuppressWarnings( "deprecation" )
    public ItemBuilder data( final int data ) {
        is.setData( new MaterialData( is.getType(), (byte) data ) );
        return this;
    }

    /**
     * Adds an {@link Enchantment} with the given level to the {@link ItemStack}
     *
     * @param enchantment the enchantment to add
     * @param level       the level of the enchantment
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemBuilder enchantment( final Enchantment enchantment, final int level ) {
        checkNotNull( enchantment );
        checkArgument( level >= 1 && level <= enchantment.getMaxLevel() );

        is.addUnsafeEnchantment( enchantment, level );
        return this;
    }

    /**
     * Adds an {@link Enchantment} with the level 1 to the {@link ItemStack}
     *
     * @param enchantment the enchantment to add
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemBuilder enchantment( final Enchantment enchantment ) {
        checkNotNull( enchantment );

        is.addUnsafeEnchantment( enchantment, 1 );
        return this;
    }

    /**
     * Changes the {@link Material} of the {@link ItemStack}
     *
     * @param material the new material to set
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemBuilder type( final Material material ) {
        checkNotNull( material );

        is.setType( material );
        return this;
    }

    /**
     * Clears the lore of the {@link ItemStack}
     *
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemBuilder clearLore() {
        final ItemMeta meta = is.getItemMeta();
        meta.setLore( new ArrayList<String>() );
        is.setItemMeta( meta );
        return this;
    }

    /**
     * Clears the list of {@link Enchantment}s of the {@link ItemStack}
     *
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemBuilder clearEnchantments() {
        for ( final Enchantment e : is.getEnchantments().keySet() ) {
            is.removeEnchantment( e );
        }
        return this;
    }

    /**
     * Sets the {@link Color} of a part of leather armor
     *
     * @param color the {@link Color} to use
     * @return this builder for chaining
     * @since 1.1
     */
    public ItemBuilder color( Color color ) {
        checkState( is.getType() == Material.LEATHER_BOOTS || is.getType() == Material.LEATHER_CHESTPLATE || is.getType() == Material.LEATHER_HELMET
                || is.getType() == Material.LEATHER_LEGGINGS, "color() only applicable for leather armor!" );

        LeatherArmorMeta meta = (LeatherArmorMeta) is.getItemMeta();
        meta.setColor( color );
        is.setItemMeta( meta );
        return this;
    }

    /**
     * Builds the {@link ItemStack}
     *
     * @return the created {@link ItemStack}
     * @since 1.0
     */
    public ItemStack build() {
        return is;
    }
}
