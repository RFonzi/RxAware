package io.github.rfonzi.rxaware

import android.arch.lifecycle.ViewModel
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import io.github.rfonzi.rxaware.bus.UIBus
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by ryan on 9/22/17.
 */
abstract class RxAwareViewModel : ViewModel(), RxAwareControls {
    private val disposables = CompositeDisposable()

    fun Disposable.lifecycleAware() = disposables.add(this)

    override fun toast(message: String) = UIBus.toast(message)

    override fun navigateUp() = UIBus.navigateUp()

    override fun fragmentTransaction(operations: FragmentTransaction.() -> Unit) = UIBus.fragmentTransaction(operations)

    override fun startActivity(target: Class<out AppCompatActivity>) = UIBus.startActivity(target)

    override fun startActivityAndStore(target: Class<out AppCompatActivity>, data: Any) = UIBus.startActivityAndStore(target, data)

    override fun store(data: Any) = UIBus.store(data)

    override fun receive(): Any = UIBus.receive()

}