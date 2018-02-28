package com.fintechplatform.android.qrtransfer.ui;

import com.fintechplatform.android.log.Log;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.transfer.api.TransferAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 18/09/17.
 */
@Module
public class QrReceiveActivityPresenterModule {

    QrReceiveActivityContract.View view;
    QrReceiveAmountContract.View amountView;
    QrReceiveShowContract.View showView;
    private DataAccount configuration;

    public QrReceiveActivityPresenterModule(QrReceiveActivityContract.View view, QrReceiveAmountContract.View amountView, QrReceiveShowContract.View showView, DataAccount configuration) {
        this.view = view;
        this.amountView = amountView;
        this.showView = showView;
        this.configuration = configuration;
    }

    @Singleton
    @Provides
    QrReceiveActivityContract.Presenter providesQrReceivePresenter(){
        return new QrReceiveActivityPresenter(view);
    }

    @Singleton
    @Provides
    QrReceiveAmountContract.Presenter providesQrReceiveAmountPresenter(TransferAPI api){
        return new QrReceiveAmountFragmentPresenter(amountView, api, configuration);
    }

    @Singleton
    @Provides
    QrReceiveShowContract.Presenter providesQrReceiveShowPresenter(Log log){
        return new QrReceiveShowFragmentPresenter(showView, log);
    }
}
