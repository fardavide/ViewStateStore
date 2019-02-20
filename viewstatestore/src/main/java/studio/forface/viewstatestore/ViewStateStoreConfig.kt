package studio.forface.viewstatestore

/**
 * @author Davide Giuseppe Farella
 * An object that holds configurations for the library.
 */
object ViewStateStoreConfig {

    /**
     * This is a generator for custom [ViewState.Error]
     *
     * Set a new value for override the default behaviour and create a custom [ViewState.Error] for your [Throwable].
     * E.g. >
        ViewStateStoreConfig.errorStateGenerator = { throwable -> // this = ErrorStateFactory
            when( throwable ) {
                is ClassCastException -> MyClassCastViewStateError
                else -> /* this. */default
            }
        }
     */
    var errorStateGenerator: ErrorStateGenerator = { default }
}