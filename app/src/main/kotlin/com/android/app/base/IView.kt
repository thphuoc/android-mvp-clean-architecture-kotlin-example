package com.android.app.base

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.Disposable

interface IView: LifecycleOwner {
    fun addDisposable(disposable: Disposable)
    fun onErrorHandle(throwable: Throwable)
    fun goNext(target: BaseFragment)
    fun <S : BaseActivity> goNext(target: Class<S>, bundle: Bundle = Bundle())
    fun goBack(target: BaseFragment)
    fun goBack()
}