package io.github.rfonzi.rxaware

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import io.github.rfonzi.rxaware.bus.UIBus
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by ryan on 9/20/17.
 */
abstract class BaseFragment : Fragment() {

    private val disposables = CompositeDisposable()

    fun Disposable.lifecycleAware() = disposables.add(this)

    fun toast(message: String) = UIBus.toast(message)

    fun navigateUp() = UIBus.navigateUp()

    fun fragmentTransaction(operations: FragmentTransaction.() -> Unit) = UIBus.fragmentTransaction(operations)

    fun startActivity(target: Class<out AppCompatActivity>) = UIBus.startActivity(target)

    fun startActivityAndStore(target: Class<out AppCompatActivity>, data: Any) = UIBus.startActivityAndStore(target, data)

    fun store(data: Any) = UIBus.store(data)

    fun receive(): Any = UIBus.receive()

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }
}