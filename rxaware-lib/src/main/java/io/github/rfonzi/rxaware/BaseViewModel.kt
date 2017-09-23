package io.github.rfonzi.rxaware

import android.arch.lifecycle.ViewModel
import android.support.v4.app.FragmentTransaction
import io.github.rfonzi.rxaware.bus.UIBus
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by ryan on 9/22/17.
 */
abstract class BaseViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    fun Disposable.lifecycleAware() = disposables.add(this)

    fun toast(message: String) = UIBus.toast(message)

    fun navigateUp() = UIBus.navigateUp()

    fun fragmentTransaction(operations: FragmentTransaction.() -> Unit) = UIBus.fragmentTransaction(operations)

}