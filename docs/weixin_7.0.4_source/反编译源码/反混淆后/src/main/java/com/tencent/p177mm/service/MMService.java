package com.tencent.p177mm.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.tencent.p177mm.sdk.platformtools.C4990ab;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.tencent.mm.service.MMService */
public abstract class MMService {
    int bWH = 0;
    ConcurrentHashMap<String, MMService> xEV = new ConcurrentHashMap();
    protected Service xFe;
    private Vector<Integer> xFf = new Vector();
    private byte xFg = (byte) 0;

    public void onCreate() {
        C4990ab.m7417i(getTag(), "%s onCreate()", "MicroMsg.MMService");
    }

    @Deprecated
    public void onStart(Intent intent, int i) {
        C4990ab.m7417i(getTag(), "%s onStart()", "MicroMsg.MMService");
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        C4990ab.m7417i(getTag(), "%s onStartCommand()", "MicroMsg.MMService");
        onStart(intent, i2);
        return 0;
    }

    public void onDestroy() {
        C4990ab.m7417i(getTag(), "%s onDestroy()", "MicroMsg.MMService");
    }

    /* renamed from: Iu */
    public IBinder mo10421Iu() {
        C4990ab.m7417i(getTag(), "%s onBind()", "MicroMsg.MMService");
        return null;
    }

    public final void startActivity(Intent intent) {
        this.xFe.startActivity(intent);
    }

    public final void startForeground(int i, Notification notification) {
        this.xFe.startForeground(i, notification);
    }

    public final void dqz() {
        this.xFe.stopForeground(true);
    }

    public final void stopSelf() {
        C4990ab.m7417i(getTag(), "%s stopSelf()", "MicroMsg.MMService");
        mo10429q(new Intent(), "stop");
    }

    public boolean onUnbind(Intent intent) {
        C4990ab.m7417i(getTag(), "%s onUnbind()", "MicroMsg.MMService");
        return false;
    }

    /* JADX WARNING: Missing block: B:11:0x004a, code skipped:
            if (r12.equals("bind") != false) goto L_0x0029;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: q */
    public final IBinder mo10429q(Intent intent, String str) {
        int i = 2;
        String toBinaryString = Integer.toBinaryString(this.xFg);
        C4990ab.m7417i(getTag(), "%s callLifeCycle() callType = %s state = %s", "MicroMsg.MMService", str, toBinaryString);
        switch (str.hashCode()) {
            case -840745386:
                if (str.equals("unbind")) {
                    i = 3;
                    break;
                }
            case 3023933:
                break;
            case 3540994:
                if (str.equals("stop")) {
                    i = 1;
                    break;
                }
            case 109757538:
                if (str.equals("start")) {
                    i = 0;
                    break;
                }
            default:
                i = -1;
                break;
        }
        switch (i) {
            case 0:
                if ((this.xFg & 5) == 0) {
                    onCreate();
                }
                i = this.bWH + 1;
                this.bWH = i;
                onStartCommand(intent, 0, i);
                this.xFg = (byte) (this.xFg | 1);
                return null;
            case 1:
                if (this.xFg == (byte) 1) {
                    onDestroy();
                    this.xEV.remove(getClass().getName());
                    return null;
                } else if (this.xFg == (byte) 4) {
                    return null;
                } else {
                    if (this.xFg == (byte) 5) {
                        this.xFg = (byte) 7;
                        return null;
                    } else if (this.xFg != (byte) 13) {
                        return null;
                    } else {
                        onDestroy();
                        this.xEV.remove(getClass().getName());
                        return null;
                    }
                }
            case 2:
                Integer valueOf = Integer.valueOf(intent.getIntExtra("service_connection", -1));
                if (this.xFf.contains(valueOf)) {
                    return null;
                }
                this.xFf.add(valueOf);
                if ((this.xFg & 5) == 0) {
                    onCreate();
                }
                this.xFg = (byte) (this.xFg | 4);
                return mo10421Iu();
            case 3:
                this.xFf.remove(Integer.valueOf(intent.getIntExtra("service_connection", -1)));
                if (this.xFg == (byte) 1) {
                    return null;
                }
                if (this.xFg == (byte) 4) {
                    onUnbind(intent);
                    onDestroy();
                    this.xEV.remove(getClass().getName());
                    return null;
                } else if (this.xFg == (byte) 5) {
                    this.xFg = (byte) 13;
                    onUnbind(intent);
                    return null;
                } else if (this.xFg != (byte) 7) {
                    return null;
                } else {
                    onUnbind(intent);
                    onDestroy();
                    this.xEV.remove(getClass().getName());
                    return null;
                }
            default:
                return null;
        }
    }

    public String getTag() {
        return "MicroMsg.MMService";
    }
}
