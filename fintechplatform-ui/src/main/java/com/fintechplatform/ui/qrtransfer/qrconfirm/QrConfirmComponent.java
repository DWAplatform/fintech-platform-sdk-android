package com.fintechplatform.ui.qrtransfer.qrconfirm;

import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.ui.account.balance.helpers.BalanceHelperModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.ui.images.ImageHelperModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.ui.money.FeeHelperModule;
import com.fintechplatform.ui.money.MoneyHelperModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.payin.ui.PayInUIModule;
import com.fintechplatform.ui.payin.ui.PaymentCardUIModule;
import com.fintechplatform.ui.payin.ui.Secure3DUIHelperModule;
import com.fintechplatform.api.transfer.api.TransferAPIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 19/09/17.
 */
@Singleton
@Component(modules = {
        QrConfirmPresenterModule.class,
        TransferAPIModule.class,
        PayInUIModule.class,
        AlertHelpersModule.class,
        ImageHelperModule.class,
        BalanceHelperModule.class,
        BalanceAPIModule.class,
        NetModule.class,
        LogModule.class,
        MoneyHelperModule.class,
        FeeHelperModule.class,
        Secure3DUIHelperModule.class,
        PaymentCardUIModule.class
})
public interface QrConfirmComponent {
    void inject(QrConfirmActivity activity);
}
