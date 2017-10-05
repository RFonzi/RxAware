package io.github.rfonzi.rxaware

import android.content.Intent
import android.support.v4.app.FragmentTransaction
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.jakewharton.rxrelay2.BehaviorRelay
import io.github.rfonzi.rxaware.bus.UIBus
import io.github.rfonzi.rxaware.bus.events.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by ryan on 9/21/17.
 */
abstract class RxAwareActivity : AppCompatActivity(), RxAwareControls {
    private val disposables = CompositeDisposable()
    private val posts: BehaviorRelay<Any> = BehaviorRelay.create()

    override fun onStart() {
        super.onStart()
        UIBus.toObservable()
                .subscribe {
                    when (it.id) {
                        EventID.TOAST -> toast((it as ToastEvent).message)
                        EventID.NAVIGATE_UP -> navigateUp()
                        EventID.FRAGMENT_TRANSACTION -> fragmentTransaction((it as FragmentTransactionEvent).event)
                        EventID.START_ACTIVITY -> startActivity((it as StartActivityEvent).activity)
                        EventID.POST_TO_CURRENT_ACTIVITY -> posts.accept((it as PostToCurrentActivityEvent).post)
                    }
                }.lifecycleAware()
    }


    fun Disposable.lifecycleAware() = disposables.add(this)

    override fun toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun navigateUp() =
            if (supportFragmentManager.backStackEntryCount > 0)
                supportFragmentManager.popBackStack()
            else
                NavUtils.navigateUpFromSameTask(this)

    override fun fragmentTransaction(operations: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.operations()
        fragmentTransaction.commit()
    }

    override fun startActivity(target: Class<out AppCompatActivity>) = startActivity(Intent(this, target))

    override fun startActivityAndStore(target: Class<out AppCompatActivity>, data: Any) = UIBus.startActivityAndStore(target, data)

    override fun store(data: Any) = UIBus.store(data)

    override fun receive(): Any = UIBus.receive()

    inline fun <reified T : Any> onPost(): Observable<T> = getPostsAsObservable().ofType(T::class.java)

    fun getPostsAsObservable(): Observable<Any> = posts

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }
}