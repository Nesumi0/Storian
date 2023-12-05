package com.example.storian.ui.LugaresHistoricos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LugaresViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LugaresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}