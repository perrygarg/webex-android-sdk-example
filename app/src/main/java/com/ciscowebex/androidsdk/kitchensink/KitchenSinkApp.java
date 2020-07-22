/*
 * Copyright 2016-2017 Cisco Systems Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.ciscowebex.androidsdk.kitchensink;

import android.app.Application;

import com.ciscowebex.androidsdk.auth.OAuthWebViewAuthenticator;
import com.ciscowebex.androidsdk.kitchensink.actions.WebexAgent;
import com.ciscowebex.androidsdk.kitchensink.actions.commands.WebexIdLoginAction;
import com.ciscowebex.androidsdk.kitchensink.models.DaoMaster;
import com.ciscowebex.androidsdk.kitchensink.models.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created on 18/09/2017.
 */

public class KitchenSinkApp extends Application {

    private static KitchenSinkApp application;
    private static DaoSession daoSession;
    private boolean isWebexAuthorizedd = false;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "call-history-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        OAuthWebViewAuthenticator oAuth2 = WebexIdLoginAction.initWebexAuthenticator();
        if(oAuth2.isAuthorized()) {
            setWebexAuthorized(true);
            WebexAgent.getInstance().register();
        } else {
            setWebexAuthorized(false);
        }
    }

    public static KitchenSinkApp getApplication() {
        return application;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public boolean isWebexAuthorized() {
        return isWebexAuthorizedd;
    }

    public void setWebexAuthorized(boolean webexAuthorized) {
        isWebexAuthorizedd = webexAuthorized;
    }

}
