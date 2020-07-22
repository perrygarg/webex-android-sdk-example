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

package com.ciscowebex.androidsdk.kitchensink.login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.ciscowebex.androidsdk.kitchensink.KitchenSinkApp;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.ciscowebex.androidsdk.kitchensink.R;
import com.ciscowebex.androidsdk.kitchensink.actions.commands.RequirePermissionAction;
import com.ciscowebex.androidsdk.kitchensink.launcher.PerryActivity;
import com.ciscowebex.androidsdk.kitchensink.login.fragments.JwtFragment;
import com.ciscowebex.androidsdk.kitchensink.login.fragments.OAuth2Fragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

import java.util.Arrays;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new RequirePermissionAction(this).execute();
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, 0);
        }
        setContentView(R.layout.fragment_login);
        ButterKnife.bind(this);

        if(KitchenSinkApp.getApplication().isWebexAuthorized()) {
            Toast.makeText(this, "Already logged in", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, PerryActivity.class));
            finish();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        System.out.println("onRequestPermissionsResult: " + requestCode
                + ", " + Arrays.toString(permissions)
                + ", " + Arrays.toString(grantResults));
    }

    @Override
    protected void onStart() {
        super.onStart();
//        getFragmentManager().popBackStackImmediate();
    }

    @OnClick(R.id.buttonWebex)
    public void WebexLogin() {
        OAuth2Fragment.newInstance().replace(this, R.id.enter_activity_layout);
    }

    @OnClick(R.id.buttonJWT)
    public void JwtLogin() {
        JwtFragment.newInstance().replace(this, R.id.enter_activity_layout);
    }

    @Override
    public void onBackPressed() {
        if (!getFragmentManager().popBackStackImmediate()) {
            super.onBackPressed();
        }
    }
}
