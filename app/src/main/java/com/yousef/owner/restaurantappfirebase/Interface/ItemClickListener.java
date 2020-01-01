package com.yousef.owner.restaurantappfirebase.Interface;

import com.google.firebase.firestore.DocumentSnapshot;

public interface ItemClickListener {

    void OnClick(DocumentSnapshot view, int position, boolean isLongClick);
}
