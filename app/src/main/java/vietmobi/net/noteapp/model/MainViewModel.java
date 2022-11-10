package vietmobi.net.noteapp.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    // initialize variables
    MutableLiveData<String> mutableLiveData=new MutableLiveData<>();

    // create set text method
    public void setText(String s)
    {
        // set value
        mutableLiveData.setValue(s);
    }

    // create get text method
    public MutableLiveData<String> getText()
    {
        return mutableLiveData;
    }
}
