package com.fintechplatform.android.qrtransfer.qrconfirm;

import com.fintechplatform.android.account.balance.api.BalanceAPIModule;
import com.fintechplatform.android.account.balance.helpers.BalanceHelperModule;
import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.images.ImageHelperModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.money.FeeHelperModule;
import com.fintechplatform.android.money.MoneyHelperModule;
import com.fintechplatform.android.payin.ui.PayInUIModule;
import com.fintechplatform.android.payin.ui.PaymentCardUIModule;
import com.fintechplatform.android.payin.ui.Secure3DUIHelperModule;
import com.fintechplatform.android.transfer.api.TransferAPIModule;

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
