package tehnut.lib.mc.tile.sync.adapters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NBT {

    /**
     * Whether this value is synced to the client or not.
     *
     * @return if this value should be synced to the client.
     */
    boolean sync() default true;
}
