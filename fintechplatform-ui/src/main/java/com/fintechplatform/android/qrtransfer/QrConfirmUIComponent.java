package com.fintechplatform.android.qrtransfer;

import com.fintechplatform.android.card.ui.PaymentCardUIModule;
import com.fintechplatform.android.payin.ui.PayInUIModule;
import com.fintechplatform.android.qrtransfer.qrconfirm.QrConfirmUI;
import com.fintechplatform.android.qrtransfer.qrconfirm.QrConfirmUIModule;
import com.fintechplatform.android.secure3d.ui.Secure3DUIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 28/02/18.
 */
@Singleton
@Component (modules = {
        QrConfirmUIModule.class,
        PayInUIModule.class,
        PaymentCardUIModule.class,
        Secure3DUIModule.class
})
public interface QrConfirmUIComponent {
    QrConfirmUI getPaymentConfirmUI();
}
