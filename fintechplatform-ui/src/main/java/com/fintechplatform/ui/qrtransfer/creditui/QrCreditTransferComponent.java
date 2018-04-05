package com.fintechplatform.ui.qrtransfer.creditui;

import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.transfer.api.TransferAPIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 18/09/17.
 */

@Singleton
@Component (modules = {QrReceivePresenterModule.class, TransferAPIModule.class, NetModule.class, AlertHelpersModule.class, LogModule.class})
public interface QrCreditTransferComponent {
    void inject(QrReceiveActivity activity);
    void inject(QrReceiveAmountFragment fragment);
    void inject(QrReceiveShowFragment fragment);
}
