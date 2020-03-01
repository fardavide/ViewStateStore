package studio.forface.viewstatestore

/**
 * @author Davide Giuseppe Farella
 * An object that holds configurations for the library.
 */
object ViewStateStoreConfig {

    /**
     * This [Boolean] defines whether a publishing should be dropped if the same [ViewState] is already the last
     * [ViewStateStore.state]
     *
     * E.g:
     * 1) `viewStateStore.setState( viewState1 )`
     * 2) `viewStateStore.postState( viewState2 )`
     * 3) `viewStateStore.setState( viewState1 )`
     * 4) `viewStateStore.setState( viewState1 )`
     * 5) `viewStateStore.postState( viewState1 )`
     * Here [ViewState]s at points *4* and *5* will be dropped if [dropOnSame] is `true`, since `viewState1` it's
     * already the last [ViewState] published.
     */
    var dropOnSame: Boolean = false

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

/**
 * An override on [invoke] operator for set [ViewStateStoreConfig] in a more fluent way, using it as receiver of a
 * lambda.
 *
 * E.g:
 * >
    ViewStateStoreConfig {
        dropOnSame = true
        errorStateGenerator = { throwable -> // this = ErrorStateFactory
            when( throwable ) {
                is ClassCastException -> MyClassCastViewStateError
                else -> /* this. */default
            }
        }
    }
 */
operator fun ViewStateStoreConfig.invoke( block: ViewStateStoreConfig.() -> Unit ) = apply( block )
