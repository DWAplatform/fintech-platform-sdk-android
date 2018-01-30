package com.fintechplatform.android.images;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageHelperModule {
    @Provides
    @Singleton
    ImageHelper provideImageHelper() {
        return new ImageHelper();
    }
}
