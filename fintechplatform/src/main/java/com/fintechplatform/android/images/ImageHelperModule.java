package com.fintechplatform.android.images;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 15/09/17.
 */
@Module
public class ImageHelperModule {
    @Provides
    @Singleton
    ImageHelper provideImageHelper() {
        return new ImageHelper();
    }
}
