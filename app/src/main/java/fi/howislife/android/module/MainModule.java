package fi.howislife.android.module;

import dagger.Module;
import fi.howislife.android.ui.activity.MainActivity;

/**
 * Created by jduran on 19/12/16.
 */

@Module(library = true,
        complete = false,
        injects = {
                MainActivity.class
        })
public class MainModule {
}
