package com.dwaplatform.android.card.ui;

import android.content.Context;
import android.content.Intent;

/**
 * Created by ingrid on 13/12/17.
 */

public class PaymentCardUI {

    void start(Context context) {
        Intent intent = new Intent(context, PaymentCardActivity.class);
        context.startActivity(intent);
    }

}
