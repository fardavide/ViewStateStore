@file:Suppress("unused")

package studio.forface.viewstatestore

import androidx.annotation.StringRes
import studio.forface.viewstatestore.ViewState.*
import studio.forface.viewstatestore.ViewState.Error.Companion.createDefault
import studio.forface.viewstatestore.ViewState.Error.Companion.fromThrowable

/**
 * @author Davide Giuseppe Farella.
 * This class hold _data_ for [ViewStateStore].
 * The [data] could be a [Success], an [Error], a [Loading] or [None]
 *
 * @param T the type of the [Success] data.
 */
sealed class ViewState<out T> {

    /** An instance of [T] that will be available in case of [Success], else it will be null */
    open val data: T? = null

    /** A function for map the current [data] */
    abstract fun <R> map( mapper: (T) -> R ): ViewState<R>

    /** Execute an [action] in case of [Success] */
    inline fun doOnData( action: (T) -> Unit ) {
        if ( this is Success ) action( data )
    }

    /** Execute an [action] in case of [Error] */
    inline fun doOnError( action: (Error) -> Unit ) {
        if ( this is Error ) action( this )
    }

    /** Execute an [action] whether is [Loading] or not */
    inline fun doOnLoadingChange( action: (isLoading: Boolean) -> Unit ) {
        action( this is Loading )
    }

    /**
     * A class that represents the success and will contains the [data] [T]
     * Inherit from [ViewState]
     */
    data class Success<out T>( override val data: T ) : ViewState<T>() {
        override fun <R> map( mapper: (T) -> R ): ViewState<R> =
            Success( mapper( data ) )
    }

    /**
     * A class that represents a failure and will contains the relative [throwable].
     * Inherit from [ViewState]
     *
     *
     * @constructor is protected so [Error] cannot be instantiated outside this class or a child class.
     * Use [fromThrowable] instead.
     *
     * This object can also be instantiated inside the library through [createDefault] function on the companion object.
     */
    open class Error protected constructor (
        @Suppress("MemberVisibilityCanBePrivate")
        val throwable: Throwable
    ) : ViewState<Nothing>() {

        companion object {
            /**
             * @return an instance of [ViewState.Error] or a custom extension for the the given [throwable].
             * @see ErrorStateGenerator
             */
            fun fromThrowable( throwable: Throwable ): ViewState.Error =
                ViewStateStoreConfig.errorStateGenerator( ErrorStateFactory( throwable ), throwable )

            /**
             * @return a new instance of [ViewState.Error]
             * This function is used for instantiate a [ViewState.Error] inside the library.
             */
            internal fun createDefault( throwable: Throwable ) = ViewState.Error( throwable )
        }

        /** @return a [String] message from [throwable] */
        val baseMessage: String get() = with( throwable ) { localizedMessage ?: message ?: "error" }

        /** The [StringRes] for a custom message to show to the user */
        @get:StringRes
        open val customMessageRes: Int? = null

        /** [Throwable.printStackTrace] from [throwable] */
        fun printStackTrace() = throwable.printStackTrace()

        override fun <R> map( mapper: (Nothing) -> R ): ViewState<R> = this
    }

    /**
     * A class that represents the loading state and will contains [Nothing]
     * Inherit from [ViewState]
     */
    object Loading : ViewState<Nothing>() {
        override fun <R> map( mapper: (Nothing) -> R ): ViewState<R> = this
    }

    /**
     * A class that represents the unknown state and will contains [Nothing]
     * Inherit from [ViewState]
     */
    object None : ViewState<Nothing>() {
        override fun <R> map( mapper: (Nothing) -> R ): ViewState<R> = this
    }
}

/** @constructor for [ViewState.Success] */
@Suppress("FunctionName")
fun <T> ViewState(data: T ) = ViewState.Success( data )