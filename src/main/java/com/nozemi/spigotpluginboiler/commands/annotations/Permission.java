package com.nozemi.spigotpluginboiler.commands.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    String name();
    String message();
}
