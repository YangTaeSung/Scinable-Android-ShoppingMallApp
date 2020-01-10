package org.techtown.push.ui.outer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OuterViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OuterViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("201Garage Introduction");
    }

    public LiveData<String> getText() {
        return mText;
    }
}