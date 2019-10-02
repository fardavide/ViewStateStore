package studio.forface.viewstatestore

/**
 * @author Davide Giuseppe Farella
 * A typealias that has [ErrorStateFactory] as receiver, [Throwable] as argument and returns a [ViewState.Error].
 * This is the type of the `errorStateGenerator`
 */
typealias ErrorStateGenerator = ErrorStateFactory.(Throwable) -> ViewState.Error

/** @return a [ErrorStateGenerator] created by merging 2 [ErrorStateGenerator] lambdas */
private fun mergeErrorStateGenerators(
    first: ErrorStateGenerator,
    second: ErrorStateGenerator
): ErrorStateGenerator = lambda@ { throwable ->

    val firstError = first( throwable )
    if ( firstError !is ViewState.DefaultError ) return@lambda firstError

    val secondError = second( throwable )
    if ( secondError !is ViewState.DefaultError ) return@lambda secondError

    default
}

/** @return [ErrorStateGenerator] from 2 [ErrorStateGenerator] */
operator fun ErrorStateGenerator.plus( other: ErrorStateGenerator ) = mergeErrorStateGenerators( this, other )

/** A class that only hold a [Throwable] for generate a "default" [ViewState.Error] from [ErrorStateGenerator] */
class ErrorStateFactory internal constructor( private val throwable: Throwable ) {
    /** @return a new instance of the "default" [ViewState.Error] */
    val default get() = ViewState.Error.createDefault( throwable )
}