package com.prography.prography_androidstudy.src.common.utils;

public interface ItemTouchHelperListener {
    boolean onItemMoved(int fromPosition, int toPosition);
    void onItemRemoved(int position);
}
