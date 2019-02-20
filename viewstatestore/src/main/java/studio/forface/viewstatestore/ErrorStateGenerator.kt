package studio.forface.viewstatestore

/**
 * @author Davide Giuseppe Farella
 * A typealias that has [ErrorStateFactory] as receiver, [Throwable] as argument and returns a [ViewState.Error].
 * This is the type of the `errorStateGenerator`
 */
typealias ErrorStateGenerator = ErrorStateFactory.(Throwable) -> ViewState.Error

/** A class that only hold a [Throwable] for generate a "default" [ViewState.Error] from [ErrorStateGenerator] */
class ErrorStateFactory( private val throwable: Throwable ) {
    /** @return a new instance of the "default" [ViewState.Error] */
    val default get() = ViewState.Error.createDefault( throwable )
}