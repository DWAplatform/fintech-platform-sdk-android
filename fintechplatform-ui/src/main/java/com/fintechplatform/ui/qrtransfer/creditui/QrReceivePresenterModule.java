package com.fintechplatform.ui.qrtransfer.creditui;

import com.fintechplatform.api.log.Log;
import com.fintechplatform.api.transfer.api.TransferAPI;
import com.fintechplatform.ui.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 18/09/17.
 */
@Module
public class QrReceivePresenterModule {

    QrReceiveActivityContract.View view;
    QrReceiveAmountContract.View amountView;
    QrReceiveShowContract.View showView;
    private DataAccount configuration;

    public QrReceivePresenterModule(QrReceiveActivityContract.View view, QrReceiveAmountContract.View amountView, QrReceiveShowContract.View showView, DataAccount configuration) {
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
