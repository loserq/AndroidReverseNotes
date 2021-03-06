package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.p057v4.app.Fragment;
import android.view.View;
import com.google.android.gms.dynamic.IFragmentWrapper.Stub;
import com.tencent.matrix.trace.core.AppMethodBeat;

public final class SupportFragmentWrapper extends Stub {
    private Fragment zzabq;

    private SupportFragmentWrapper(Fragment fragment) {
        this.zzabq = fragment;
    }

    public static SupportFragmentWrapper wrap(Fragment fragment) {
        AppMethodBeat.m2504i(90398);
        if (fragment != null) {
            SupportFragmentWrapper supportFragmentWrapper = new SupportFragmentWrapper(fragment);
            AppMethodBeat.m2505o(90398);
            return supportFragmentWrapper;
        }
        AppMethodBeat.m2505o(90398);
        return null;
    }

    public final IObjectWrapper getActivity() {
        AppMethodBeat.m2504i(90399);
        IObjectWrapper wrap = ObjectWrapper.wrap(this.zzabq.getActivity());
        AppMethodBeat.m2505o(90399);
        return wrap;
    }

    public final Bundle getArguments() {
        AppMethodBeat.m2504i(90400);
        Bundle arguments = this.zzabq.getArguments();
        AppMethodBeat.m2505o(90400);
        return arguments;
    }

    public final int getId() {
        AppMethodBeat.m2504i(90401);
        int id = this.zzabq.getId();
        AppMethodBeat.m2505o(90401);
        return id;
    }

    public final IFragmentWrapper getParentFragment() {
        AppMethodBeat.m2504i(90402);
        SupportFragmentWrapper wrap = wrap(this.zzabq.getParentFragment());
        AppMethodBeat.m2505o(90402);
        return wrap;
    }

    public final IObjectWrapper getResources() {
        AppMethodBeat.m2504i(90403);
        IObjectWrapper wrap = ObjectWrapper.wrap(this.zzabq.getResources());
        AppMethodBeat.m2505o(90403);
        return wrap;
    }

    public final boolean getRetainInstance() {
        AppMethodBeat.m2504i(90404);
        boolean retainInstance = this.zzabq.getRetainInstance();
        AppMethodBeat.m2505o(90404);
        return retainInstance;
    }

    public final String getTag() {
        AppMethodBeat.m2504i(90405);
        String tag = this.zzabq.getTag();
        AppMethodBeat.m2505o(90405);
        return tag;
    }

    public final IFragmentWrapper getTargetFragment() {
        AppMethodBeat.m2504i(90406);
        SupportFragmentWrapper wrap = wrap(this.zzabq.getTargetFragment());
        AppMethodBeat.m2505o(90406);
        return wrap;
    }

    public final int getTargetRequestCode() {
        AppMethodBeat.m2504i(90407);
        int targetRequestCode = this.zzabq.getTargetRequestCode();
        AppMethodBeat.m2505o(90407);
        return targetRequestCode;
    }

    public final boolean getUserVisibleHint() {
        AppMethodBeat.m2504i(90408);
        boolean userVisibleHint = this.zzabq.getUserVisibleHint();
        AppMethodBeat.m2505o(90408);
        return userVisibleHint;
    }

    public final IObjectWrapper getView() {
        AppMethodBeat.m2504i(90409);
        IObjectWrapper wrap = ObjectWrapper.wrap(this.zzabq.getView());
        AppMethodBeat.m2505o(90409);
        return wrap;
    }

    public final boolean isAdded() {
        AppMethodBeat.m2504i(90410);
        boolean isAdded = this.zzabq.isAdded();
        AppMethodBeat.m2505o(90410);
        return isAdded;
    }

    public final boolean isDetached() {
        AppMethodBeat.m2504i(90411);
        boolean isDetached = this.zzabq.isDetached();
        AppMethodBeat.m2505o(90411);
        return isDetached;
    }

    public final boolean isHidden() {
        AppMethodBeat.m2504i(90412);
        boolean isHidden = this.zzabq.isHidden();
        AppMethodBeat.m2505o(90412);
        return isHidden;
    }

    public final boolean isInLayout() {
        AppMethodBeat.m2504i(90413);
        boolean isInLayout = this.zzabq.isInLayout();
        AppMethodBeat.m2505o(90413);
        return isInLayout;
    }

    public final boolean isRemoving() {
        AppMethodBeat.m2504i(90414);
        boolean isRemoving = this.zzabq.isRemoving();
        AppMethodBeat.m2505o(90414);
        return isRemoving;
    }

    public final boolean isResumed() {
        AppMethodBeat.m2504i(90415);
        boolean isResumed = this.zzabq.isResumed();
        AppMethodBeat.m2505o(90415);
        return isResumed;
    }

    public final boolean isVisible() {
        AppMethodBeat.m2504i(90416);
        boolean isVisible = this.zzabq.isVisible();
        AppMethodBeat.m2505o(90416);
        return isVisible;
    }

    public final void registerForContextMenu(IObjectWrapper iObjectWrapper) {
        AppMethodBeat.m2504i(90417);
        this.zzabq.registerForContextMenu((View) ObjectWrapper.unwrap(iObjectWrapper));
        AppMethodBeat.m2505o(90417);
    }

    public final void setHasOptionsMenu(boolean z) {
        AppMethodBeat.m2504i(90418);
        this.zzabq.setHasOptionsMenu(z);
        AppMethodBeat.m2505o(90418);
    }

    public final void setMenuVisibility(boolean z) {
        AppMethodBeat.m2504i(90419);
        this.zzabq.setMenuVisibility(z);
        AppMethodBeat.m2505o(90419);
    }

    public final void setRetainInstance(boolean z) {
        AppMethodBeat.m2504i(90420);
        this.zzabq.setRetainInstance(z);
        AppMethodBeat.m2505o(90420);
    }

    public final void setUserVisibleHint(boolean z) {
        AppMethodBeat.m2504i(90421);
        this.zzabq.setUserVisibleHint(z);
        AppMethodBeat.m2505o(90421);
    }

    public final void startActivity(Intent intent) {
        AppMethodBeat.m2504i(90422);
        this.zzabq.startActivity(intent);
        AppMethodBeat.m2505o(90422);
    }

    public final void startActivityForResult(Intent intent, int i) {
        AppMethodBeat.m2504i(90423);
        this.zzabq.startActivityForResult(intent, i);
        AppMethodBeat.m2505o(90423);
    }

    public final void unregisterForContextMenu(IObjectWrapper iObjectWrapper) {
        AppMethodBeat.m2504i(90424);
        this.zzabq.unregisterForContextMenu((View) ObjectWrapper.unwrap(iObjectWrapper));
        AppMethodBeat.m2505o(90424);
    }
}
