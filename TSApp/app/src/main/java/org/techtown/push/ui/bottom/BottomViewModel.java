package org.techtown.push.ui.bottom;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BottomViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BottomViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is bottom fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}