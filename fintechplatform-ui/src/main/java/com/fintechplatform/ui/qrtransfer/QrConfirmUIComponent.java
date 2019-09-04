package com.fintechplatform.ui.qrtransfer;

import com.fintechplatform.ui.qrtransfer.qrconfirm.QrConfirmUI;
import com.fintechplatform.ui.qrtransfer.qrconfirm.QrConfirmUIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 28/02/18.
 */
@Singleton
@Component (modules = {
        QrConfirmUIModule.class
})
public interface QrConfirmUIComponent {
    QrConfirmUI getPaymentConfirmUI();
}
