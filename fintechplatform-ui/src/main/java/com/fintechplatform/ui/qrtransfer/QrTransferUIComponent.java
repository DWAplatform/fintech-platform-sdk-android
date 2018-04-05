package com.fintechplatform.ui.qrtransfer;

import com.fintechplatform.ui.qrtransfer.creditui.QrCreditTransferUI;
import com.fintechplatform.ui.qrtransfer.creditui.QrCreditTransferUIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 28/02/18.
 */
@Singleton
@Component(modules = {
        QrCreditTransferUIModule.class
})
public interface QrTransferUIComponent {
    QrCreditTransferUI getQrCreditTransgerUI();
}
