package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.WearableStatusCodes;
import com.tencent.matrix.trace.core.AppMethodBeat;

public final class zzgd {
    public static Status zzb(int i) {
        AppMethodBeat.m2504i(71361);
        Status status = new Status(i, WearableStatusCodes.getStatusCodeString(i));
        AppMethodBeat.m2505o(71361);
        return status;
    }
}
