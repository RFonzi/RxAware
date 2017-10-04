package io.github.rfonzi.rxaware

import android.content.Intent
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import io.github.rfonzi.rxaware.bus.UIBus

/**
 * Created by ryan on 10/4/17.
 */
interface RxAwareControls {
    fun toast(message: String)
    fun navigateUp()
    fun fragmentTransaction(operations: FragmentTransaction.() -> Unit)
    fun startActivity(target: Class<out AppCompatActivity>)
    fun startActivityAndStore(target: Class<out AppCompatActivity>, data: Any)
    fun store(data: Any)
    fun receive(): Any
}