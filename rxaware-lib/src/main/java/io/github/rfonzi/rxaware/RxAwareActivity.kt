package io.github.rfonzi.rxaware

import android.content.Intent
import android.support.v4.app.FragmentTransaction
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.github.rfonzi.rxaware.bus.UIBus
import io.github.rfonzi.rxaware.bus.events.EventID
import io.github.rfonzi.rxaware.bus.events.FragmentTransactionEvent
import io.github.rfonzi.rxaware.bus.events.StartActivityEvent
import io.github.rfonzi.rxaware.bus.events.ToastEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by ryan on 9/21/17.
 */
abstract class RxAwareActivity : AppCompatActivity() {
    private val disposables = CompositeDisposable()

    override fun onStart() {
        super.onStart()
        UIBus.toObservable()
                .subscribe {
                    when(it.id){
                        EventID.TOAST -> toast((it as ToastEvent).message)
                        EventID.NAVIGATE_UP -> navigateUp()
                        EventID.FRAGMENT_TRANSACTION -> fragmentTransaction((it as FragmentTransactionEvent).event)
                        EventID.START_ACTIVITY -> startActivity((it as StartActivityEvent).activity)
                    }
                }.lifecycleAware()
    }


    fun Disposable.lifecycleAware() = disposables.add(this)

    fun toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    fun navigateUp() = NavUtils.navigateUpFromSameTask(this)

    fun fragmentTransaction(operations: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.operations()
        fragmentTransaction.commit()
    }

    fun startActivity(target: Class<out AppCompatActivity>) = startActivity(Intent(this, target))

    fun startActivityAndStore(target: Class<out AppCompatActivity>, data: Any) = UIBus.startActivityAndStore(target, data)

    fun store(data: Any) = UIBus.store(data)

    fun receive(): Any = UIBus.receive()

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }
}