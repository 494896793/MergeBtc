package com.bochat.app.model.util.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;

public class DownloadApkReceiver extends BroadcastReceiver {

    public static final String REQUEST_ID = "RequestId";

    private Handler mHandler;

    public DownloadApkReceiver(Handler handler) {
        mHandler = handler;
    }

    class DownloadReceiverRunnable implements Runnable {

        Context mContext;

        long mRequestId;

        DownloadReceiverRunnable(Context context, Intent intent) {
            mContext = context;
            mRequestId = intent.getLongExtra(REQUEST_ID, 0);
        }

        @Override
        public void run() {

            DownloadManager dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Query dmQuery = new DownloadManager.Query();
            dmQuery.setFilterById(mRequestId);
            boolean downloadStatus = true;
            while (downloadStatus) {

                Cursor cursor = dm.query(dmQuery);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        switch (status) {
                            case DownloadManager.STATUS_PENDING:
                                mHandler.obtainMessage(DownloadManager.STATUS_PENDING).sendToTarget();
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL:

                                downloadStatus = false;
                                mHandler.obtainMessage(DownloadManager.STATUS_SUCCESSFUL).sendToTarget();

                                break;
                            case DownloadManager.STATUS_FAILED:

                                int reason = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON));
                                downloadStatus = false;
                                mHandler.obtainMessage(DownloadManager.STATUS_FAILED, reason).sendToTarget();

                                break;
                            case DownloadManager.STATUS_RUNNING:

                                int totalSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                                int curSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                                mHandler.obtainMessage(DownloadManager.STATUS_RUNNING, totalSize, curSize).sendToTarget();

                                break;
                            default:
                                break;
                        }
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                } finally {
                    if(cursor != null)
                        cursor.close();
                }

            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        new Thread(new DownloadReceiverRunnable(context, intent)).start();
    }
}
