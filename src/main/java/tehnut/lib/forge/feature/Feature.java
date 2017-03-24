package tehnut.lib.forge.feature;

import net.minecraftforge.fml.common.Loader;
import tehnut.lib.forge.util.helper.ReflectionHelper;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Feature {

    @Nonnull
    String name();

    @Nonnull
    String description() default "";

    boolean enabledByDefault() default true;

    boolean handleConfig() default false;

    int priority() default 100;

    @Nonnull
    String[] modRequirements() default {};

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Config {

        String category() default "";

        String comment() default "";
    }

    ReflectionHelper.IAnnotationPreloader PRELOADER = new ReflectionHelper.IAnnotationPreloader() {
        @Override
        public boolean shouldLoad(Map<String, Object> annotationData) {
            Object requirements = annotationData.get("modRequirements");
            if (requirements == null)
                return true;

            String[] cast = (String[]) requirements;
            for (String mod : cast)
                if (!Loader.isModLoaded(mod))
                    return false;

            return true;
        }
    };

    ReflectionHelper.IAnnotationLoader<IFeature> LOADER = new ReflectionHelper.IAnnotationLoader<IFeature>() {
        @Override
        public IFeature createInstance(Class<?> type, Map<String, Object> annotationData) throws Exception {
            return (IFeature) type.newInstance();
        }
    };
}