package com.android.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.app.R
import com.android.app.utils.DialogHelper
import com.android.domain.exceptions.EstablishConnectionException
import com.android.domain.exceptions.RemoteException
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseFragment : Fragment(), IView {
    abstract val layoutResId: Int
    private val disposables = CompositeDisposable()
    private val dialogHelper: DialogHelper by inject { parametersOf(context) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context).inflate(layoutResId, container, false)
        bindingView(view)
        return view
    }

    open fun bindingView(view: View) {}

    open fun onBackPressed() {
        goBack()
    }

    override fun addDisposable(disposable: Disposable) {
        this.disposables.add(disposable)
    }

    override fun onErrorHandle(throwable: Throwable) {
        throwable.printStackTrace()
        hideLoading()
        if (throwable is RemoteException) {
            showErrorDialog(throwable.message ?: "")
        } else if (throwable is EstablishConnectionException) {
            showErrorDialog(getString(R.string.error_no_connection))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.disposables.clear()
        dialogHelper.hideDialog()
    }

    open fun showErrorDialog(message: String, onClick: () -> Unit = {}) {
        dialogHelper.showMessageDialog(message, onClick)
    }

    override fun goNext(target: BaseFragment) {
        (activity as BaseActivity).goNext(target)
    }

    override fun <S : BaseActivity> goNext(target: Class<S>, bundle: Bundle) {
        (activity as BaseActivity).goNext(target, bundle)
    }

    override fun goBack(target: BaseFragment) {
        (activity as BaseActivity).goBack(target)
    }

    override fun goBack() {
        (activity as BaseActivity).goBack()
    }

    open fun showLoading() {
        dialogHelper.showLoadingIndicator()
    }

    open fun hideLoading() {
        dialogHelper.hideDialog()
    }
}