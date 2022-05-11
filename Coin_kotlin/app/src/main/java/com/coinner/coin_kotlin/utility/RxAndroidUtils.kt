package com.coinner.coin_kotlin.utility

import android.widget.EditText
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable

object RxAndroidUtils{

    fun getEditTextObservable(editText: EditText): Observable<String>{
        return editText.textChanges()
            .map { obj: CharSequence -> obj.toString() }
    }

}