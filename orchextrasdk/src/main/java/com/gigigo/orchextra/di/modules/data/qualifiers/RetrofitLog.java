package com.gigigo.orchextra.di.modules.data.qualifiers;

import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier @Retention(RUNTIME)
public @interface RetrofitLog {
}