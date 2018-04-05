package com.fintechplatform.android.qrtransfer;

import com.fintechplatform.android.qrtransfer.creditui.QrCreditTransferUI;
import com.fintechplatform.android.qrtransfer.creditui.QrCreditTransferUIModule;

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
