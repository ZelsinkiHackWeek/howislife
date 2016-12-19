package fi.howislife.android.module;

import dagger.Module;
import fi.howislife.android.HowIsLifeApplication;

/**
 * Created by jduran on 19/12/16.
 */
@Module(library = true,
        complete = false,
        injects = {
                HowIsLifeApplication.class
        })
public class ApplicationModule {
}
