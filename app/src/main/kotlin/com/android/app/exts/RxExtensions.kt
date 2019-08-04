package com.android.app.exts

import com.android.app.base.IView
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <S> Single<S>.observe(view: IView, onSuccess: (it: S) -> Unit) {
    view.addDisposable(
        this.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(onSuccess, {
                view.onErrorHandle(it)
            })
    )
}
fun <S> Flowable<S>.observe(view: IView, onNext: (it: S) -> Unit) {
    view.addDisposable(
        this.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, {
                view.onErrorHandle(it)
            })
    )
}
