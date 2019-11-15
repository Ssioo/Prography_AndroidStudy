package com.prography.prography_androidstudy.src.sign_in.interfaces;

import android.view.View;
import android.widget.CompoundButton;

public interface SignInActivityView extends View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    void validateSuccess(int uniqueId);
    void validateFalire(String message);
}
