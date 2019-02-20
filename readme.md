###### This library is created on an idea of *Fabio Collini* ( https://proandroiddev.com/unidirectional-data-flow-using-coroutines-f5a792bf34e5 ).

# ViewStateStore

**ViewStateStore** wraps a *LiveData* for deliver **ViewState**s to the *UI*.

Supported **ViewState** types are;

* *Success* holds the real data
* *Error* holds and error ( which could be a custom class ) with its *Throwable* and an optiona *customMessageRes*.
* *Loading*
* *None* ( default initial value )



## Installation

#### ViewStateStore

###### Groovy

`implementation "studio.forface.viewstatestore:viewstatestore:last_version"`

###### Kotlin-DSL

`implementation( "studio.forface.viewstatestore:viewstatestore:last_version" )`

#### Paging extension

###### Groovy

`implementation "studio.forface.viewstatestore:viewstatestore-paging:last_version"`

###### Kotlin-DSL

`implementation( "studio.forface.viewstatestore:viewstatestore-paging:last_version" )`



## Usage

### ViewStateStore



#### Instantiate ViewStateStore

###### no *initialValue*

`ViewStateStore<CarUiModel>()`

###### with *initialValue*

`ViewStateStore( CarUiModel() )`



#### Observe ViewStateStore

###### observe within `Lifecycle` - from `Activity` or `Fragment`

```kotlin
val viewStateStore = myViewModel.car
viewStateStore.observe {
    doOnData( ::updateUi )
    doOnError { viewStateError -> showToast( viewStateError.customMessageRes ) }
    doOnLoadingChange { isLoading -> ... }
    doOnEach { viewState -> ... }
}
```

###### observe with `Lifecycle` - from any class

```kotlin
val viewStateStore = myViewModel.car
viewStateStore.observe( lifecycleOwner ) { ... }
```

###### observe without `Lifecycle` - from any class

```kotlin
val viewStateStore = myViewModel.car
viewStateStore.observeForever { ... }
```



###### observe data ( same pattern as before `LifecycleOwner.observeData( callback )`, `observeData( LifecycleOwer, callback )`, `observeDataForever( callback )` )

```kotlin
val viewStateStore = myViewModel.car
viewStateStore.observeData( ::updateUi )
```



#### Publish to ViewStateStore

Use `set` functions for publish on the same thread, use `post` functions for publish on the main thread.

Some examples:

* `setState( ViewState.Success( carUiModel ) )`

* `postState( ViewState.Error.fromThrowable( someThrowable ) )`

  

Also some *extension functions* are available.

Some examples:

* `setData( carUiModel )`
* `postError( someThrowable )`
* `postLoading()`



#### Error handling

`ViewState.Error` is an *open class* so it can be extended with some custom implementations.

```kotlin
class ClassCastError( t: ClassCastException ): ViewState.Error( t ) {
    override val customMessageRes get() = R.string.error_class_cast
}
class NPError( t: NullPointerExeption ): ViewState.Error( t ) {
    override val customMessageRes get() = R.string.error_npe
}
```

Then set your custom `ErrorStateGenerator`

```kotlin
ViewStateStoreConfig.errorStateGenerator = { throwable -> // this = ErrorStateFactory
	when ( throwable ) {
		is ClassCastException -> ClassCastErrro( throwable ) 
		is NullPointerException -> NPError( throwable )
		else -> /* this. */default // ViewState.Error default constructor is called
	}
}
```



### Paging extension

An extension for *Android's Paging* is also available.

When using it, instad of `setData( T )`, you would call `setDataFactory( DataSource.Factory<Int, T> )`.

Here's an example:

```kotlin
// ViewModel
val car = PagedViewStateStore( pageSize = 40 )
val carDataFactory: DataSource.Factory<Int, Car> = myRepository.getCars()
car.setDataFactory( carDataFactory )

// Fragment
myViewModel.car.observeData( adapter::submitList )
```

