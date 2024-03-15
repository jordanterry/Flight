package jt.flights.di

import android.app.Activity
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
public annotation class ActivityKey(val value: KClass<out Activity>)