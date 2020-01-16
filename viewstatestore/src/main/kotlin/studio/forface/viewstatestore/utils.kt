package studio.forface.viewstatestore

import androidx.lifecycle.Lifecycle.State.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

/*
 * Set of utils for the library
 * Author: Davide Giuseppe Farella
 */

/**
 * Apply a mapper on the receiver [LiveData]
 * @return [LiveData] of [R]
 */
internal inline fun <T, R> LiveData<T>.map( crossinline mapper: (T) -> R ) =
        Transformations.map( this ) { mapper( it ) }

/** @return `true` if the receiver [LifecycleOwner] is [STARTED] or [RESUMED] */
internal val LifecycleOwner.isActive get() =
        lifecycle.currentState in listOf(STARTED, RESUMED)

/** @return `true` if the receiver [LifecycleOwner] is not [STARTED] or [RESUMED] */
internal val LifecycleOwner.isInactive get() =
        lifecycle.currentState !in listOf(STARTED, RESUMED)

/** @return `true` if the receiver [LifecycleOwner] is not [DESTROYED] */
internal val LifecycleOwner.isAlive get() =
        lifecycle.currentState != DESTROYED

/** @return `true` if the receiver [LifecycleOwner] is [DESTROYED] */
internal val LifecycleOwner.isNotAlive get() =
        lifecycle.currentState == DESTROYED
