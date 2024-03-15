package jt.flights.di

import javax.inject.Scope
import kotlin.reflect.KClass

@Scope
@Retention(AnnotationRetention.RUNTIME)
public annotation class SingleIn(val clazz: KClass<*>)