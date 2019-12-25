package org.techtown.push.ui.shoes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShoesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShoesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is shoes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}