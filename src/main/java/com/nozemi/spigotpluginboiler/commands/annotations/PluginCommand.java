package com.nozemi.spigotpluginboiler.commands.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PluginCommand {
    String name();
    String description();
    String usage() default "";
    String[] aliases() default {};
}
