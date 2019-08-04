package com.android.app.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.android.app.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


abstract class BaseActivity : AppCompatActivity(), IView {
    open var layoutId: Int = R.layout.fragment_holder
    abstract var initFragment: BaseFragment
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(this).inflate(layoutId, null, false)
        setContentView(view)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHolder, initFragment)
            .commitAllowingStateLoss()
    }

    override fun goNext(target: BaseFragment) {
        initFragment = target
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.right_in, R.anim.left_out)
            .replace(R.id.fragmentHolder, initFragment)
            .commitAllowingStateLoss()
    }

    override fun <S: BaseActivity> goNext(target: Class<S>, bundle: Bundle) {
        val intent = Intent(this, target).apply {
            extras?.putAll(bundle)
        }
        startActivity(intent)
        this.overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    override fun goBack(target: BaseFragment) {
        initFragment = target
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.left_in, R.anim.right_out)
            .replace(R.id.fragmentHolder, initFragment)
            .commitAllowingStateLoss()
    }

    override fun goBack() {
        this.finish()
        this.overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }

    override fun onBackPressed() {
        initFragment.onBackPressed()
    }

    override fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    override fun onErrorHandle(throwable: Throwable) {

    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}