package com.prography.prography_androidstudy.src.sign_up.interfaces;

import android.view.View;

public interface SignUpActivityView extends View.OnClickListener {
    void validateSuccess(int uniqueId);
    void validateFalire(String message);
}
