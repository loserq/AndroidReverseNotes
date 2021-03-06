package com.eclipsesource.p097v8.debug.mirror;

import com.eclipsesource.p097v8.Releasable;
import com.eclipsesource.p097v8.V8Object;
import com.eclipsesource.p097v8.V8ResultUndefined;
import com.tencent.matrix.trace.core.AppMethodBeat;

/* renamed from: com.eclipsesource.v8.debug.mirror.Mirror */
public class Mirror implements Releasable {
    private static final String IS_ARRAY = "isArray";
    private static final String IS_BOOLEAN = "isBoolean";
    private static final String IS_FUNCTION = "isFunction";
    private static final String IS_NULL = "isNull";
    private static final String IS_NUMBER = "isNumber";
    private static final String IS_OBJECT = "isObject";
    private static final String IS_STRING = "isString";
    private static final String IS_UNDEFINED = "isUndefined";
    private static final String IS_VALUE = "isValue";
    protected V8Object v8Object;

    Mirror(V8Object v8Object) {
        AppMethodBeat.m2504i(74870);
        this.v8Object = v8Object.twin();
        AppMethodBeat.m2505o(74870);
    }

    public boolean isUndefined() {
        AppMethodBeat.m2504i(74871);
        boolean executeBooleanFunction = this.v8Object.executeBooleanFunction(IS_UNDEFINED, null);
        AppMethodBeat.m2505o(74871);
        return executeBooleanFunction;
    }

    public boolean isValue() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public boolean isBoolean() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    public boolean isObject() {
        return false;
    }

    public boolean isFunction() {
        return false;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isFrame() {
        return false;
    }

    public boolean isProperty() {
        return false;
    }

    public void release() {
        AppMethodBeat.m2504i(74872);
        if (!(this.v8Object == null || this.v8Object.isReleased())) {
            this.v8Object.release();
            this.v8Object = null;
        }
        AppMethodBeat.m2505o(74872);
    }

    protected static boolean isValue(V8Object v8Object) {
        AppMethodBeat.m2504i(74873);
        try {
            boolean executeBooleanFunction = v8Object.executeBooleanFunction(IS_VALUE, null);
            AppMethodBeat.m2505o(74873);
            return executeBooleanFunction;
        } catch (V8ResultUndefined e) {
            AppMethodBeat.m2505o(74873);
            return false;
        }
    }

    private static boolean isObject(V8Object v8Object) {
        AppMethodBeat.m2504i(74874);
        try {
            boolean executeBooleanFunction = v8Object.executeBooleanFunction(IS_OBJECT, null);
            AppMethodBeat.m2505o(74874);
            return executeBooleanFunction;
        } catch (V8ResultUndefined e) {
            AppMethodBeat.m2505o(74874);
            return false;
        }
    }

    private static boolean isNumber(V8Object v8Object) {
        AppMethodBeat.m2504i(74875);
        try {
            boolean executeBooleanFunction = v8Object.executeBooleanFunction(IS_NUMBER, null);
            AppMethodBeat.m2505o(74875);
            return executeBooleanFunction;
        } catch (V8ResultUndefined e) {
            AppMethodBeat.m2505o(74875);
            return false;
        }
    }

    private static boolean isBoolean(V8Object v8Object) {
        AppMethodBeat.m2504i(74876);
        try {
            boolean executeBooleanFunction = v8Object.executeBooleanFunction(IS_BOOLEAN, null);
            AppMethodBeat.m2505o(74876);
            return executeBooleanFunction;
        } catch (V8ResultUndefined e) {
            AppMethodBeat.m2505o(74876);
            return false;
        }
    }

    private static boolean isFunction(V8Object v8Object) {
        AppMethodBeat.m2504i(74877);
        try {
            boolean executeBooleanFunction = v8Object.executeBooleanFunction(IS_FUNCTION, null);
            AppMethodBeat.m2505o(74877);
            return executeBooleanFunction;
        } catch (V8ResultUndefined e) {
            AppMethodBeat.m2505o(74877);
            return false;
        }
    }

    private static boolean isArray(V8Object v8Object) {
        AppMethodBeat.m2504i(74878);
        try {
            boolean executeBooleanFunction = v8Object.executeBooleanFunction(IS_ARRAY, null);
            AppMethodBeat.m2505o(74878);
            return executeBooleanFunction;
        } catch (V8ResultUndefined e) {
            AppMethodBeat.m2505o(74878);
            return false;
        }
    }

    private static boolean isString(V8Object v8Object) {
        AppMethodBeat.m2504i(74879);
        try {
            boolean executeBooleanFunction = v8Object.executeBooleanFunction(IS_STRING, null);
            AppMethodBeat.m2505o(74879);
            return executeBooleanFunction;
        } catch (V8ResultUndefined e) {
            AppMethodBeat.m2505o(74879);
            return false;
        }
    }

    private static boolean isUndefined(V8Object v8Object) {
        AppMethodBeat.m2504i(74880);
        try {
            boolean executeBooleanFunction = v8Object.executeBooleanFunction(IS_UNDEFINED, null);
            AppMethodBeat.m2505o(74880);
            return executeBooleanFunction;
        } catch (V8ResultUndefined e) {
            AppMethodBeat.m2505o(74880);
            return false;
        }
    }

    private static boolean isNull(V8Object v8Object) {
        AppMethodBeat.m2504i(74881);
        try {
            boolean executeBooleanFunction = v8Object.executeBooleanFunction(IS_NULL, null);
            AppMethodBeat.m2505o(74881);
            return executeBooleanFunction;
        } catch (V8ResultUndefined e) {
            AppMethodBeat.m2505o(74881);
            return false;
        }
    }

    protected static ValueMirror createMirror(V8Object v8Object) {
        AppMethodBeat.m2504i(74882);
        ValueMirror undefinedMirror;
        if (Mirror.isNull(v8Object)) {
            NullMirror nullMirror = new NullMirror(v8Object);
            AppMethodBeat.m2505o(74882);
            return nullMirror;
        } else if (Mirror.isUndefined(v8Object)) {
            undefinedMirror = new UndefinedMirror(v8Object);
            AppMethodBeat.m2505o(74882);
            return undefinedMirror;
        } else if (Mirror.isFunction(v8Object)) {
            undefinedMirror = new FunctionMirror(v8Object);
            AppMethodBeat.m2505o(74882);
            return undefinedMirror;
        } else if (Mirror.isArray(v8Object)) {
            undefinedMirror = new ArrayMirror(v8Object);
            AppMethodBeat.m2505o(74882);
            return undefinedMirror;
        } else if (Mirror.isObject(v8Object)) {
            undefinedMirror = new ObjectMirror(v8Object);
            AppMethodBeat.m2505o(74882);
            return undefinedMirror;
        } else if (Mirror.isString(v8Object)) {
            undefinedMirror = new StringMirror(v8Object);
            AppMethodBeat.m2505o(74882);
            return undefinedMirror;
        } else if (Mirror.isNumber(v8Object)) {
            undefinedMirror = new NumberMirror(v8Object);
            AppMethodBeat.m2505o(74882);
            return undefinedMirror;
        } else if (Mirror.isBoolean(v8Object)) {
            undefinedMirror = new BooleanMirror(v8Object);
            AppMethodBeat.m2505o(74882);
            return undefinedMirror;
        } else {
            undefinedMirror = new ValueMirror(v8Object);
            AppMethodBeat.m2505o(74882);
            return undefinedMirror;
        }
    }

    public String toString() {
        AppMethodBeat.m2504i(74883);
        String v8Object = this.v8Object.toString();
        AppMethodBeat.m2505o(74883);
        return v8Object;
    }

    public boolean equals(Object obj) {
        AppMethodBeat.m2504i(74884);
        if (obj == null) {
            AppMethodBeat.m2505o(74884);
            return false;
        } else if (obj instanceof Mirror) {
            boolean equals = this.v8Object.equals(((Mirror) obj).v8Object);
            AppMethodBeat.m2505o(74884);
            return equals;
        } else {
            AppMethodBeat.m2505o(74884);
            return false;
        }
    }

    public int hashCode() {
        AppMethodBeat.m2504i(74885);
        int hashCode = this.v8Object.hashCode();
        AppMethodBeat.m2505o(74885);
        return hashCode;
    }
}
