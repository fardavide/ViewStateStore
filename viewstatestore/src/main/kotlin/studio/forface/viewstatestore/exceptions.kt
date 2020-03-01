package studio.forface.viewstatestore

/**
 * An [Exception] that represents that [ViewState.Error.resolve] has been called, while no [ViewState.Error.resolution]
 * has been set.
 */
class NoResolutionException internal constructor( error: ViewState.Error ) : Exception(
    "No available resolution for '${error.javaClass.canonicalName}', please make sure a resolution is available via" +
            "'${ViewState.Error::hasResolution.name}' or directly call '${ViewState.Error::tryToResolve.name}'"
)
