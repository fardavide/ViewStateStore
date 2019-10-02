package studio.forface.viewstatestore.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

/*
 * Set of utils for the library
 * Author: Davide Giuseppe Farella
 */

/**
 * Apply a mapper on the given [LiveData]
 * @return [LiveData] of [R]
 */
internal inline fun <T, R> LiveData<T>.map( crossinline mapper: (T) -> R ) =
        Transformations.map( this ) { mapper( it ) }