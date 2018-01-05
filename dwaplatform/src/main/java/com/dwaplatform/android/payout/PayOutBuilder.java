package com.dwaplatform.android.payout;


import com.dwaplatform.android.iban.ui.IbanUIModule;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.payout.ui.PayOutUI;
import com.dwaplatform.android.payout.ui.PayOutUIModule;

public class PayOutBuilder {

    public PayOutUIComponent createPayOutUI(DataAccount configuration) {

        return DaggerPayOutUIComponet.builder()
                .payOutUIModule(new PayOutUIModule(configuration))
                .build();
    }
}
