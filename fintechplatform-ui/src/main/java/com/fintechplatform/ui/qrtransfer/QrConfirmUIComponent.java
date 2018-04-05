package com.fintechplatform.ui.qrtransfer;

import com.fintechplatform.ui.card.ui.PaymentCardUIModule;
import com.fintechplatform.ui.payin.ui.PayInUIModule;
import com.fintechplatform.ui.qrtransfer.qrconfirm.QrConfirmUI;
import com.fintechplatform.ui.qrtransfer.qrconfirm.QrConfirmUIModule;
import com.fintechplatform.ui.secure3d.ui.Secure3DUIModule;

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
