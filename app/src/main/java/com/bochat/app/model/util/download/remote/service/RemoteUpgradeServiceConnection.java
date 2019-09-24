package com.bochat.app.model.util.download.remote.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.bochat.app.IRemoteUpgradeManager;
import com.bochat.app.common.util.ULog;

public class RemoteUpgradeServiceConnection implements ServiceConnection {

    private IRemoteUpgradeManager mRemoteUpgradeManager;

    public IRemoteUpgradeManager getRemoteUpgradeManager() {
        return mRemoteUpgradeManager;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        RemoteUpgradeService.isConnection = true;
        mRemoteUpgradeManager = IRemoteUpgradeManager.Stub.asInterface(iBinder);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        RemoteUpgradeService.isConnection = false;
        mRemoteUpgradeManager = null;
    }

    @Override
    public void onBindingDied(ComponentName name) {
    }

    @Override
    public void onNullBinding(ComponentName name) {
    }
}
