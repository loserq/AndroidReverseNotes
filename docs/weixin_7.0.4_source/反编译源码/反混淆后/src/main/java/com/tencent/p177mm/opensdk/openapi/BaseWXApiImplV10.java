package com.tencent.p177mm.opensdk.openapi;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.Scopes;
import com.tencent.matrix.trace.core.AppMethodBeat;
import com.tencent.p177mm.loader.BuildConfig;
import com.tencent.p177mm.opensdk.channel.MMessageActV2;
import com.tencent.p177mm.opensdk.channel.MMessageActV2.Args;
import com.tencent.p177mm.opensdk.channel.p1213a.C18744b;
import com.tencent.p177mm.opensdk.channel.p1213a.C37979a;
import com.tencent.p177mm.opensdk.channel.p1213a.C37979a.C32882a;
import com.tencent.p177mm.opensdk.constants.Build;
import com.tencent.p177mm.opensdk.constants.ConstantsAPI;
import com.tencent.p177mm.opensdk.constants.ConstantsAPI.Token;
import com.tencent.p177mm.opensdk.modelbase.BaseReq;
import com.tencent.p177mm.opensdk.modelbase.BaseResp;
import com.tencent.p177mm.opensdk.modelbiz.AddCardToWXCardPackage;
import com.tencent.p177mm.opensdk.modelbiz.ChooseCardFromWXCardPackage;
import com.tencent.p177mm.opensdk.modelbiz.CreateChatroom;
import com.tencent.p177mm.opensdk.modelbiz.HandleScanResult;
import com.tencent.p177mm.opensdk.modelbiz.JoinChatroom;
import com.tencent.p177mm.opensdk.modelbiz.OpenWebview;
import com.tencent.p177mm.opensdk.modelbiz.SubscribeMessage;
import com.tencent.p177mm.opensdk.modelbiz.SubscribeMessage.Resp;
import com.tencent.p177mm.opensdk.modelbiz.SubscribeMiniProgramMsg;
import com.tencent.p177mm.opensdk.modelbiz.WXInvoiceAuthInsert;
import com.tencent.p177mm.opensdk.modelbiz.WXInvoiceAuthInsert.Req;
import com.tencent.p177mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.p177mm.opensdk.modelbiz.WXNontaxPay;
import com.tencent.p177mm.opensdk.modelbiz.WXOpenBusinessView;
import com.tencent.p177mm.opensdk.modelbiz.WXOpenBusinessWebview;
import com.tencent.p177mm.opensdk.modelbiz.WXPayInsurance;
import com.tencent.p177mm.opensdk.modelmsg.GetMessageFromWX;
import com.tencent.p177mm.opensdk.modelmsg.LaunchFromWX;
import com.tencent.p177mm.opensdk.modelmsg.SendAuth;
import com.tencent.p177mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.p177mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.p177mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.p177mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.p177mm.opensdk.modelpay.JumpToOfflinePay;
import com.tencent.p177mm.opensdk.modelpay.PayResp;
import com.tencent.p177mm.opensdk.utils.C18754d;
import com.tencent.p177mm.opensdk.utils.ILog;
import com.tencent.p177mm.opensdk.utils.Log;
import com.tencent.tmassistantsdk.openSDK.TMQQDownloaderOpenSDKConst;
import java.net.URLEncoder;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* renamed from: com.tencent.mm.opensdk.openapi.BaseWXApiImplV10 */
class BaseWXApiImplV10 implements IWXAPI {
    protected static final String TAG = "MicroMsg.SDK.WXApiImplV10";
    private static String wxappPayEntryClassname = null;
    protected String appId;
    protected boolean checkSignature = false;
    protected Context context;
    protected boolean detached = false;
    private int wxSdkVersion;

    BaseWXApiImplV10(Context context, String str, boolean z) {
        AppMethodBeat.m2504i(128102);
        Log.m4139d(TAG, "<init>, appId = " + str + ", checkSignature = " + z);
        this.context = context;
        this.appId = str;
        this.checkSignature = z;
        AppMethodBeat.m2505o(128102);
    }

    private boolean checkSumConsistent(byte[] bArr, byte[] bArr2) {
        AppMethodBeat.m2504i(128137);
        if (bArr == null || bArr.length == 0 || bArr2 == null || bArr2.length == 0) {
            Log.m4140e(TAG, "checkSumConsistent fail, invalid arguments");
            AppMethodBeat.m2505o(128137);
            return false;
        } else if (bArr.length != bArr2.length) {
            Log.m4140e(TAG, "checkSumConsistent fail, length is different");
            AppMethodBeat.m2505o(128137);
            return false;
        } else {
            for (int i = 0; i < bArr.length; i++) {
                if (bArr[i] != bArr2[i]) {
                    AppMethodBeat.m2505o(128137);
                    return false;
                }
            }
            AppMethodBeat.m2505o(128137);
            return true;
        }
    }

    private boolean createChatroom(Context context, Bundle bundle) {
        AppMethodBeat.m2504i(128124);
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/createChatroom"), null, null, new String[]{this.appId, bundle.getString("_wxapi_basereq_transaction", ""), bundle.getString("_wxapi_create_chatroom_group_id", ""), bundle.getString("_wxapi_create_chatroom_chatroom_name", ""), bundle.getString("_wxapi_create_chatroom_chatroom_nickname", ""), bundle.getString("_wxapi_create_chatroom_ext_msg", ""), bundle.getString("_wxapi_basereq_openid", "")}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128124);
        return true;
    }

    private String getTokenFromWX(Context context) {
        AppMethodBeat.m2504i(128112);
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/genTokenForOpenSdk"), null, null, new String[]{this.appId, BuildConfig.OPEN_SDK_VERSION}, null);
        if (query == null || !query.moveToFirst()) {
            AppMethodBeat.m2505o(128112);
            return null;
        }
        String string = query.getString(0);
        Log.m4141i(TAG, "getTokenFromWX token is ".concat(String.valueOf(string)));
        query.close();
        AppMethodBeat.m2505o(128112);
        return string;
    }

    private boolean handleWxInternalRespType(String str, IWXAPIEventHandler iWXAPIEventHandler) {
        AppMethodBeat.m2504i(128107);
        Log.m4141i(TAG, "handleWxInternalRespType, extInfo = ".concat(String.valueOf(str)));
        try {
            Uri parse = Uri.parse(str);
            String queryParameter = parse.getQueryParameter("wx_internal_resptype");
            Log.m4141i(TAG, "handleWxInternalRespType, respType = ".concat(String.valueOf(queryParameter)));
            String queryParameter2;
            if (C18754d.m29305b(queryParameter)) {
                Log.m4140e(TAG, "handleWxInternalRespType fail, respType is null");
                AppMethodBeat.m2505o(128107);
                return false;
            } else if (queryParameter.equals("subscribemessage")) {
                Resp resp = new Resp();
                queryParameter2 = parse.getQueryParameter("ret");
                if (queryParameter2 != null && queryParameter2.length() > 0) {
                    resp.errCode = C18754d.m29306c(queryParameter2);
                }
                resp.openId = parse.getQueryParameter(Scopes.OPEN_ID);
                resp.templateID = parse.getQueryParameter("template_id");
                resp.scene = C18754d.m29306c(parse.getQueryParameter("scene"));
                resp.action = parse.getQueryParameter(NativeProtocol.WEB_DIALOG_ACTION);
                resp.reserved = parse.getQueryParameter("reserved");
                iWXAPIEventHandler.onResp(resp);
                AppMethodBeat.m2505o(128107);
                return true;
            } else if (queryParameter.contains("invoice_auth_insert")) {
                WXInvoiceAuthInsert.Resp resp2 = new WXInvoiceAuthInsert.Resp();
                queryParameter2 = parse.getQueryParameter("ret");
                if (queryParameter2 != null && queryParameter2.length() > 0) {
                    resp2.errCode = C18754d.m29306c(queryParameter2);
                }
                resp2.wxOrderId = parse.getQueryParameter("wx_order_id");
                iWXAPIEventHandler.onResp(resp2);
                AppMethodBeat.m2505o(128107);
                return true;
            } else if (queryParameter.contains("payinsurance")) {
                WXPayInsurance.Resp resp3 = new WXPayInsurance.Resp();
                queryParameter2 = parse.getQueryParameter("ret");
                if (queryParameter2 != null && queryParameter2.length() > 0) {
                    resp3.errCode = C18754d.m29306c(queryParameter2);
                }
                resp3.wxOrderId = parse.getQueryParameter("wx_order_id");
                iWXAPIEventHandler.onResp(resp3);
                AppMethodBeat.m2505o(128107);
                return true;
            } else if (queryParameter.contains("nontaxpay")) {
                WXNontaxPay.Resp resp4 = new WXNontaxPay.Resp();
                queryParameter2 = parse.getQueryParameter("ret");
                if (queryParameter2 != null && queryParameter2.length() > 0) {
                    resp4.errCode = C18754d.m29306c(queryParameter2);
                }
                resp4.wxOrderId = parse.getQueryParameter("wx_order_id");
                iWXAPIEventHandler.onResp(resp4);
                AppMethodBeat.m2505o(128107);
                return true;
            } else if ("subscribeminiprogrammsg".equals(queryParameter) || "5".equals(queryParameter)) {
                SubscribeMiniProgramMsg.Resp resp5 = new SubscribeMiniProgramMsg.Resp();
                queryParameter2 = parse.getQueryParameter("ret");
                if (queryParameter2 != null && queryParameter2.length() > 0) {
                    resp5.errCode = C18754d.m29306c(queryParameter2);
                }
                resp5.openId = parse.getQueryParameter(Scopes.OPEN_ID);
                resp5.unionId = parse.getQueryParameter("unionid");
                resp5.nickname = parse.getQueryParameter("nickname");
                resp5.errStr = parse.getQueryParameter("errmsg");
                iWXAPIEventHandler.onResp(resp5);
                AppMethodBeat.m2505o(128107);
                return true;
            } else {
                Log.m4140e(TAG, "this open sdk version not support the request type");
                AppMethodBeat.m2505o(128107);
                return false;
            }
        } catch (Exception e) {
            Log.m4140e(TAG, "handleWxInternalRespType fail, ex = " + e.getMessage());
        }
    }

    private boolean joinChatroom(Context context, Bundle bundle) {
        AppMethodBeat.m2504i(128125);
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/joinChatroom"), null, null, new String[]{this.appId, bundle.getString("_wxapi_basereq_transaction", ""), bundle.getString("_wxapi_join_chatroom_group_id", ""), bundle.getString("_wxapi_join_chatroom_chatroom_nickname", ""), bundle.getString("_wxapi_join_chatroom_ext_msg", ""), bundle.getString("_wxapi_basereq_openid", "")}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128125);
        return true;
    }

    private boolean sendAddCardToWX(Context context, Bundle bundle) {
        AppMethodBeat.m2504i(128117);
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/addCardToWX"), null, null, new String[]{this.appId, bundle.getString("_wxapi_add_card_to_wx_card_list"), bundle.getString("_wxapi_basereq_transaction")}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128117);
        return true;
    }

    private boolean sendChooseCardFromWX(Context context, Bundle bundle) {
        AppMethodBeat.m2504i(128118);
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/chooseCardFromWX"), null, null, new String[]{bundle.getString("_wxapi_choose_card_from_wx_card_app_id"), bundle.getString("_wxapi_choose_card_from_wx_card_location_id"), bundle.getString("_wxapi_choose_card_from_wx_card_sign_type"), bundle.getString("_wxapi_choose_card_from_wx_card_card_sign"), bundle.getString("_wxapi_choose_card_from_wx_card_time_stamp"), bundle.getString("_wxapi_choose_card_from_wx_card_nonce_str"), bundle.getString("_wxapi_choose_card_from_wx_card_card_id"), bundle.getString("_wxapi_choose_card_from_wx_card_card_type"), bundle.getString("_wxapi_choose_card_from_wx_card_can_multi_select"), bundle.getString("_wxapi_basereq_transaction")}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128118);
        return true;
    }

    private boolean sendHandleScanResult(Context context, Bundle bundle) {
        AppMethodBeat.m2504i(128126);
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/handleScanResult"), null, null, new String[]{this.appId, bundle.getString("_wxapi_scan_qrcode_result")}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128126);
        return true;
    }

    private boolean sendInvoiceAuthInsert(Context context, BaseReq baseReq) {
        AppMethodBeat.m2504i(128129);
        Req req = (Req) baseReq;
        ContentResolver contentResolver = context.getContentResolver();
        Uri parse = Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview");
        String encode = URLEncoder.encode(String.format("url=%s", new Object[]{URLEncoder.encode(req.url)}));
        Cursor query = contentResolver.query(parse, null, null, new String[]{this.appId, "2", encode}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128129);
        return true;
    }

    private boolean sendJumpToBizProfileReq(Context context, Bundle bundle) {
        AppMethodBeat.m2504i(128114);
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/jumpToBizProfile"), null, null, new String[]{this.appId, bundle.getString("_wxapi_jump_to_biz_profile_req_to_user_name"), bundle.getString("_wxapi_jump_to_biz_profile_req_ext_msg"), bundle.getInt("_wxapi_jump_to_biz_profile_req_scene"), bundle.getInt("_wxapi_jump_to_biz_profile_req_profile_type")}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128114);
        return true;
    }

    private boolean sendJumpToBizTempSessionReq(Context context, Bundle bundle) {
        AppMethodBeat.m2504i(128116);
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/jumpToBizTempSession"), null, null, new String[]{this.appId, bundle.getString("_wxapi_jump_to_biz_webview_req_to_user_name"), bundle.getString("_wxapi_jump_to_biz_webview_req_session_from"), bundle.getInt("_wxapi_jump_to_biz_webview_req_show_type")}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128116);
        return true;
    }

    private boolean sendJumpToBizWebviewReq(Context context, Bundle bundle) {
        AppMethodBeat.m2504i(128115);
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/jumpToBizProfile"), null, null, new String[]{this.appId, bundle.getString("_wxapi_jump_to_biz_webview_req_to_user_name"), bundle.getString("_wxapi_jump_to_biz_webview_req_ext_msg"), bundle.getInt("_wxapi_jump_to_biz_webview_req_scene")}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128115);
        return true;
    }

    private boolean sendJumpToOfflinePayReq(Context context, Bundle bundle) {
        AppMethodBeat.m2504i(128122);
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/jumpToOfflinePay"), null, null, new String[]{this.appId}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128122);
        return true;
    }

    private boolean sendLaunchWXMiniprogram(Context context, BaseReq baseReq) {
        AppMethodBeat.m2504i(128132);
        WXLaunchMiniProgram.Req req = (WXLaunchMiniProgram.Req) baseReq;
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/launchWXMiniprogram"), null, null, new String[]{this.appId, req.userName, req.path, req.miniprogramType, req.extData}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128132);
        return true;
    }

    private boolean sendNonTaxPay(Context context, BaseReq baseReq) {
        AppMethodBeat.m2504i(128130);
        WXNontaxPay.Req req = (WXNontaxPay.Req) baseReq;
        ContentResolver contentResolver = context.getContentResolver();
        Uri parse = Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview");
        String encode = URLEncoder.encode(String.format("url=%s", new Object[]{URLEncoder.encode(req.url)}));
        Cursor query = contentResolver.query(parse, null, null, new String[]{this.appId, TMQQDownloaderOpenSDKConst.VERIFYTYPE_ALL, encode}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128130);
        return true;
    }

    private boolean sendOpenBusiLuckyMoney(Context context, Bundle bundle) {
        AppMethodBeat.m2504i(128123);
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openBusiLuckyMoney"), null, null, new String[]{this.appId, bundle.getString("_wxapi_open_busi_lucky_money_timeStamp"), bundle.getString("_wxapi_open_busi_lucky_money_nonceStr"), bundle.getString("_wxapi_open_busi_lucky_money_signType"), bundle.getString("_wxapi_open_busi_lucky_money_signature"), bundle.getString("_wxapi_open_busi_lucky_money_package")}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128123);
        return true;
    }

    private boolean sendOpenBusinessView(Context context, BaseReq baseReq) {
        AppMethodBeat.m2504i(128133);
        WXOpenBusinessView.Req req = (WXOpenBusinessView.Req) baseReq;
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openBusinessView"), null, null, new String[]{this.appId, req.businessType, req.query, req.extInfo}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128133);
        return true;
    }

    private boolean sendOpenBusinessWebview(Context context, BaseReq baseReq) {
        AppMethodBeat.m2504i(128121);
        WXOpenBusinessWebview.Req req = (WXOpenBusinessWebview.Req) baseReq;
        ContentResolver contentResolver = context.getContentResolver();
        Uri parse = Uri.parse("content://com.tencent.mm.sdk.comm.provider/openBusinessWebview");
        String str = "";
        if (req.queryInfo != null && req.queryInfo.size() > 0) {
            str = new JSONObject(req.queryInfo).toString();
        }
        Cursor query = contentResolver.query(parse, null, null, new String[]{this.appId, req.businessType, str}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128121);
        return true;
    }

    private boolean sendOpenRankListReq(Context context, Bundle bundle) {
        AppMethodBeat.m2504i(128119);
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openRankList"), null, null, new String[0], null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128119);
        return true;
    }

    private boolean sendOpenWebview(Context context, Bundle bundle) {
        AppMethodBeat.m2504i(128120);
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openWebview"), null, null, new String[]{this.appId, bundle.getString("_wxapi_jump_to_webview_url"), bundle.getString("_wxapi_basereq_transaction")}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128120);
        return true;
    }

    private boolean sendPayInSurance(Context context, BaseReq baseReq) {
        AppMethodBeat.m2504i(128131);
        WXPayInsurance.Req req = (WXPayInsurance.Req) baseReq;
        ContentResolver contentResolver = context.getContentResolver();
        Uri parse = Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview");
        String encode = URLEncoder.encode(String.format("url=%s", new Object[]{URLEncoder.encode(req.url)}));
        Cursor query = contentResolver.query(parse, null, null, new String[]{this.appId, "4", encode}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128131);
        return true;
    }

    private boolean sendPayReq(Context context, Bundle bundle) {
        AppMethodBeat.m2504i(128113);
        if (wxappPayEntryClassname == null) {
            wxappPayEntryClassname = new MMSharedPreferences(context).getString("_wxapp_pay_entry_classname_", null);
            Log.m4139d(TAG, "pay, set wxappPayEntryClassname = " + wxappPayEntryClassname);
            if (wxappPayEntryClassname == null) {
                try {
                    wxappPayEntryClassname = context.getPackageManager().getApplicationInfo("com.tencent.mm", 128).metaData.getString("com.tencent.mm.BuildInfo.OPEN_SDK_PAY_ENTRY_CLASSNAME", null);
                } catch (Exception e) {
                    Log.m4140e(TAG, "get from metaData failed : " + e.getMessage());
                }
            }
            if (wxappPayEntryClassname == null) {
                Log.m4140e(TAG, "pay fail, wxappPayEntryClassname is null");
                AppMethodBeat.m2505o(128113);
                return false;
            }
        }
        Args args = new Args();
        args.bundle = bundle;
        args.targetPkgName = "com.tencent.mm";
        args.targetClassName = wxappPayEntryClassname;
        boolean send = MMessageActV2.send(context, args);
        AppMethodBeat.m2505o(128113);
        return send;
    }

    private boolean sendSubscribeMessage(Context context, BaseReq baseReq) {
        AppMethodBeat.m2504i(128127);
        SubscribeMessage.Req req = (SubscribeMessage.Req) baseReq;
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview"), null, null, new String[]{this.appId, "1", String.valueOf(req.scene), req.templateID, req.reserved}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128127);
        return true;
    }

    private boolean sendSubscribeMiniProgramMsg(Context context, BaseReq baseReq) {
        AppMethodBeat.m2504i(128128);
        SubscribeMiniProgramMsg.Req req = (SubscribeMiniProgramMsg.Req) baseReq;
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.tencent.mm.sdk.comm.provider/openTypeWebview"), null, null, new String[]{this.appId, "5", req.miniProgramAppId}, null);
        if (query != null) {
            query.close();
        }
        AppMethodBeat.m2505o(128128);
        return true;
    }

    public void detach() {
        AppMethodBeat.m2504i(128135);
        Log.m4139d(TAG, "detach");
        this.detached = true;
        this.context = null;
        AppMethodBeat.m2505o(128135);
    }

    public int getWXAppSupportAPI() {
        AppMethodBeat.m2504i(128109);
        if (this.detached) {
            IllegalStateException illegalStateException = new IllegalStateException("getWXAppSupportAPI fail, WXMsgImpl has been detached");
            AppMethodBeat.m2505o(128109);
            throw illegalStateException;
        } else if (isWXAppInstalled()) {
            this.wxSdkVersion = 0;
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            new Thread(new Runnable() {
                public void run() {
                    AppMethodBeat.m2504i(128076);
                    try {
                        BaseWXApiImplV10.this.wxSdkVersion = new MMSharedPreferences(BaseWXApiImplV10.this.context).getInt("_build_info_sdk_int_", 0);
                    } catch (Exception e) {
                        Log.m4143w(BaseWXApiImplV10.TAG, e.getMessage());
                    }
                    countDownLatch.countDown();
                    AppMethodBeat.m2505o(128076);
                }
            }).run();
            try {
                countDownLatch.await(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Log.m4143w(TAG, e.getMessage());
            }
            Log.m4139d(TAG, "_build_info_sdk_int_ = " + this.wxSdkVersion);
            if (this.wxSdkVersion == 0) {
                try {
                    this.wxSdkVersion = this.context.getPackageManager().getApplicationInfo("com.tencent.mm", 128).metaData.getInt("com.tencent.mm.BuildInfo.OPEN_SDK_VERSION", 0);
                    Log.m4139d(TAG, "OPEN_SDK_VERSION = " + this.wxSdkVersion);
                } catch (Exception e2) {
                    Log.m4140e(TAG, "get from metaData failed : " + e2.getMessage());
                }
            }
            int i = this.wxSdkVersion;
            AppMethodBeat.m2505o(128109);
            return i;
        } else {
            Log.m4140e(TAG, "open wx app failed, not installed or signature check failed");
            AppMethodBeat.m2505o(128109);
            return 0;
        }
    }

    public boolean handleIntent(Intent intent, IWXAPIEventHandler iWXAPIEventHandler) {
        AppMethodBeat.m2504i(128106);
        try {
            if (!WXApiImplComm.isIntentFromWx(intent, Token.WX_TOKEN_VALUE_MSG)) {
                Log.m4141i(TAG, "handleIntent fail, intent not from weixin msg");
                AppMethodBeat.m2505o(128106);
                return false;
            } else if (this.detached) {
                IllegalStateException illegalStateException = new IllegalStateException("handleIntent fail, WXMsgImpl has been detached");
                AppMethodBeat.m2505o(128106);
                throw illegalStateException;
            } else {
                String stringExtra = intent.getStringExtra(ConstantsAPI.CONTENT);
                int intExtra = intent.getIntExtra(ConstantsAPI.SDK_VERSION, 0);
                String stringExtra2 = intent.getStringExtra(ConstantsAPI.APP_PACKAGE);
                if (stringExtra2 == null || stringExtra2.length() == 0) {
                    Log.m4140e(TAG, "invalid argument");
                    AppMethodBeat.m2505o(128106);
                    return false;
                } else if (checkSumConsistent(intent.getByteArrayExtra(ConstantsAPI.CHECK_SUM), C18744b.m29297a(stringExtra, intExtra, stringExtra2))) {
                    int intExtra2 = intent.getIntExtra("_wxapi_command_type", 0);
                    Log.m4141i(TAG, "handleIntent, cmd = ".concat(String.valueOf(intExtra2)));
                    switch (intExtra2) {
                        case 1:
                            iWXAPIEventHandler.onResp(new SendAuth.Resp(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        case 2:
                            iWXAPIEventHandler.onResp(new SendMessageToWX.Resp(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        case 3:
                            iWXAPIEventHandler.onReq(new GetMessageFromWX.Req(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        case 4:
                            ShowMessageFromWX.Req req = new ShowMessageFromWX.Req(intent.getExtras());
                            stringExtra = req.message.messageExt;
                            if (stringExtra == null || !stringExtra.contains("wx_internal_resptype")) {
                                if (stringExtra != null) {
                                    if (stringExtra.contains("openbusinesswebview")) {
                                        try {
                                            Uri parse = Uri.parse(stringExtra);
                                            if (parse == null || !"openbusinesswebview".equals(parse.getHost())) {
                                                Log.m4139d(TAG, "not openbusinesswebview %".concat(String.valueOf(stringExtra)));
                                            } else {
                                                WXOpenBusinessWebview.Resp resp = new WXOpenBusinessWebview.Resp();
                                                String queryParameter = parse.getQueryParameter("ret");
                                                if (queryParameter != null && queryParameter.length() > 0) {
                                                    resp.errCode = C18754d.m29306c(queryParameter);
                                                }
                                                resp.resultInfo = parse.getQueryParameter("resultInfo");
                                                resp.errStr = parse.getQueryParameter("errmsg");
                                                stringExtra2 = parse.getQueryParameter("type");
                                                if (stringExtra2 != null && stringExtra2.length() > 0) {
                                                    resp.businessType = C18754d.m29306c(stringExtra2);
                                                }
                                                iWXAPIEventHandler.onResp(resp);
                                                AppMethodBeat.m2505o(128106);
                                                return true;
                                            }
                                        } catch (Exception e) {
                                            Log.m4140e(TAG, "parse fail, ex = " + e.getMessage());
                                        }
                                    }
                                }
                                iWXAPIEventHandler.onReq(req);
                                AppMethodBeat.m2505o(128106);
                                return true;
                            }
                            boolean handleWxInternalRespType = handleWxInternalRespType(stringExtra, iWXAPIEventHandler);
                            Log.m4141i(TAG, "handleIntent, extInfo contains wx_internal_resptype, ret = ".concat(String.valueOf(handleWxInternalRespType)));
                            AppMethodBeat.m2505o(128106);
                            return handleWxInternalRespType;
                        case 5:
                            iWXAPIEventHandler.onResp(new PayResp(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        case 6:
                            iWXAPIEventHandler.onReq(new LaunchFromWX.Req(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        case 9:
                            iWXAPIEventHandler.onResp(new AddCardToWXCardPackage.Resp(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        case 12:
                            iWXAPIEventHandler.onResp(new OpenWebview.Resp(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        case 14:
                            iWXAPIEventHandler.onResp(new CreateChatroom.Resp(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        case 15:
                            iWXAPIEventHandler.onResp(new JoinChatroom.Resp(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        case 16:
                            iWXAPIEventHandler.onResp(new ChooseCardFromWXCardPackage.Resp(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        case 17:
                            iWXAPIEventHandler.onResp(new HandleScanResult.Resp(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        case 19:
                            iWXAPIEventHandler.onResp(new WXLaunchMiniProgram.Resp(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        case 24:
                            iWXAPIEventHandler.onResp(new JumpToOfflinePay.Resp(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        case 25:
                            iWXAPIEventHandler.onResp(new WXOpenBusinessWebview.Resp(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        case 26:
                            iWXAPIEventHandler.onResp(new WXOpenBusinessView.Resp(intent.getExtras()));
                            AppMethodBeat.m2505o(128106);
                            return true;
                        default:
                            Log.m4140e(TAG, "unknown cmd = ".concat(String.valueOf(intExtra2)));
                            break;
                    }
                    AppMethodBeat.m2505o(128106);
                    return false;
                } else {
                    Log.m4140e(TAG, "checksum fail");
                    AppMethodBeat.m2505o(128106);
                    return false;
                }
            }
        } catch (Exception e2) {
            Log.m4140e(TAG, "handleIntent fail, ex = " + e2.getMessage());
        }
    }

    public boolean isWXAppInstalled() {
        boolean z = false;
        AppMethodBeat.m2504i(128108);
        if (this.detached) {
            IllegalStateException illegalStateException = new IllegalStateException("isWXAppInstalled fail, WXMsgImpl has been detached");
            AppMethodBeat.m2505o(128108);
            throw illegalStateException;
        }
        try {
            PackageInfo packageInfo = this.context.getPackageManager().getPackageInfo("com.tencent.mm", 64);
            if (packageInfo == null) {
                AppMethodBeat.m2505o(128108);
            } else {
                z = WXApiImplComm.validateAppSignature(this.context, packageInfo.signatures, this.checkSignature);
                AppMethodBeat.m2505o(128108);
            }
        } catch (NameNotFoundException e) {
            AppMethodBeat.m2505o(128108);
        }
        return z;
    }

    public boolean openWXApp() {
        AppMethodBeat.m2504i(128110);
        if (this.detached) {
            IllegalStateException illegalStateException = new IllegalStateException("openWXApp fail, WXMsgImpl has been detached");
            AppMethodBeat.m2505o(128110);
            throw illegalStateException;
        } else if (isWXAppInstalled()) {
            try {
                this.context.startActivity(this.context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm"));
                AppMethodBeat.m2505o(128110);
                return true;
            } catch (Exception e) {
                Log.m4140e(TAG, "startActivity fail, exception = " + e.getMessage());
                AppMethodBeat.m2505o(128110);
                return false;
            }
        } else {
            Log.m4140e(TAG, "open wx app failed, not installed or signature check failed");
            AppMethodBeat.m2505o(128110);
            return false;
        }
    }

    public boolean registerApp(String str) {
        AppMethodBeat.m2504i(128103);
        boolean registerApp = registerApp(str, 0);
        AppMethodBeat.m2505o(128103);
        return registerApp;
    }

    public boolean registerApp(String str, long j) {
        AppMethodBeat.m2504i(128104);
        if (this.detached) {
            IllegalStateException illegalStateException = new IllegalStateException("registerApp fail, WXMsgImpl has been detached");
            AppMethodBeat.m2505o(128104);
            throw illegalStateException;
        } else if (WXApiImplComm.validateAppSignatureForPackage(this.context, "com.tencent.mm", this.checkSignature)) {
            Log.m4139d(TAG, "registerApp, appId = ".concat(String.valueOf(str)));
            if (str != null) {
                this.appId = str;
            }
            Log.m4139d(TAG, "registerApp, appId = ".concat(String.valueOf(str)));
            if (str != null) {
                this.appId = str;
            }
            Log.m4139d(TAG, "register app " + this.context.getPackageName());
            C32882a c32882a = new C32882a();
            c32882a.f15023a = "com.tencent.mm";
            c32882a.action = ConstantsAPI.ACTION_HANDLE_APP_REGISTER;
            c32882a.content = "weixin://registerapp?appid=" + this.appId;
            c32882a.f15024b = j;
            boolean a = C37979a.m64269a(this.context, c32882a);
            AppMethodBeat.m2505o(128104);
            return a;
        } else {
            Log.m4140e(TAG, "register app failed for wechat app signature check failed");
            AppMethodBeat.m2505o(128104);
            return false;
        }
    }

    public boolean sendReq(BaseReq baseReq) {
        AppMethodBeat.m2504i(128111);
        if (this.detached) {
            IllegalStateException illegalStateException = new IllegalStateException("sendReq fail, WXMsgImpl has been detached");
            AppMethodBeat.m2505o(128111);
            throw illegalStateException;
        } else if (!WXApiImplComm.validateAppSignatureForPackage(this.context, "com.tencent.mm", this.checkSignature)) {
            Log.m4140e(TAG, "sendReq failed for wechat app signature check failed");
            AppMethodBeat.m2505o(128111);
            return false;
        } else if (baseReq.checkArgs()) {
            Log.m4141i(TAG, "sendReq, req type = " + baseReq.getType());
            Bundle bundle = new Bundle();
            baseReq.toBundle(bundle);
            boolean sendPayReq;
            if (baseReq.getType() == 5) {
                sendPayReq = sendPayReq(this.context, bundle);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 7) {
                sendPayReq = sendJumpToBizProfileReq(this.context, bundle);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 8) {
                sendPayReq = sendJumpToBizWebviewReq(this.context, bundle);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 10) {
                sendPayReq = sendJumpToBizTempSessionReq(this.context, bundle);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 9) {
                sendPayReq = sendAddCardToWX(this.context, bundle);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 16) {
                sendPayReq = sendChooseCardFromWX(this.context, bundle);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 11) {
                sendPayReq = sendOpenRankListReq(this.context, bundle);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 12) {
                sendPayReq = sendOpenWebview(this.context, bundle);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 25) {
                sendPayReq = sendOpenBusinessWebview(this.context, baseReq);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 13) {
                sendPayReq = sendOpenBusiLuckyMoney(this.context, bundle);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 14) {
                sendPayReq = createChatroom(this.context, bundle);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 15) {
                sendPayReq = joinChatroom(this.context, bundle);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 17) {
                sendPayReq = sendHandleScanResult(this.context, bundle);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 18) {
                sendPayReq = sendSubscribeMessage(this.context, baseReq);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 23) {
                sendPayReq = sendSubscribeMiniProgramMsg(this.context, baseReq);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 19) {
                sendPayReq = sendLaunchWXMiniprogram(this.context, baseReq);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 26) {
                sendPayReq = sendOpenBusinessView(this.context, baseReq);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 20) {
                sendPayReq = sendInvoiceAuthInsert(this.context, baseReq);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 21) {
                sendPayReq = sendNonTaxPay(this.context, baseReq);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 22) {
                sendPayReq = sendPayInSurance(this.context, baseReq);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else if (baseReq.getType() == 24) {
                sendPayReq = sendJumpToOfflinePayReq(this.context, bundle);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            } else {
                if (baseReq.getType() == 2) {
                    int i = bundle.getInt("_wxapi_sendmessagetowx_req_media_type");
                    if (C18754d.m29304a(i)) {
                        SendMessageToWX.Req req = (SendMessageToWX.Req) baseReq;
                        WXWebpageObject wXWebpageObject;
                        if (getWXAppSupportAPI() < Build.MINIPROGRAM_SUPPORTED_SDK_INT) {
                            wXWebpageObject = new WXWebpageObject();
                            wXWebpageObject.webpageUrl = bundle.getString("_wxminiprogram_webpageurl");
                            req.message.mediaObject = wXWebpageObject;
                        } else if (i != 46 || getWXAppSupportAPI() >= Build.WEISHI_MINIPROGRAM_SUPPORTED_SDK_INT) {
                            WXMiniProgramObject wXMiniProgramObject = (WXMiniProgramObject) req.message.mediaObject;
                            wXMiniProgramObject.userName += "@app";
                            String str = wXMiniProgramObject.path;
                            if (!C18754d.m29305b(str)) {
                                String[] split = str.split("\\?");
                                wXMiniProgramObject.path = split.length > 1 ? split[0] + ".html?" + split[1] : split[0] + ".html";
                            }
                        } else {
                            wXWebpageObject = new WXWebpageObject();
                            wXWebpageObject.webpageUrl = bundle.getString("_wxminiprogram_webpageurl");
                            req.message.mediaObject = wXWebpageObject;
                        }
                        if (req.scene != 3) {
                            req.scene = 0;
                        }
                        baseReq.toBundle(bundle);
                    }
                }
                String tokenFromWX = getTokenFromWX(this.context);
                Args args = new Args();
                args.bundle = bundle;
                args.content = "weixin://sendreq?appid=" + this.appId;
                args.targetPkgName = "com.tencent.mm";
                args.targetClassName = "com.tencent.mm.plugin.base.stub.WXEntryActivity";
                args.token = tokenFromWX;
                sendPayReq = MMessageActV2.send(this.context, args);
                AppMethodBeat.m2505o(128111);
                return sendPayReq;
            }
        } else {
            Log.m4140e(TAG, "sendReq checkArgs fail");
            AppMethodBeat.m2505o(128111);
            return false;
        }
    }

    public boolean sendResp(BaseResp baseResp) {
        AppMethodBeat.m2504i(128134);
        if (this.detached) {
            IllegalStateException illegalStateException = new IllegalStateException("sendResp fail, WXMsgImpl has been detached");
            AppMethodBeat.m2505o(128134);
            throw illegalStateException;
        } else if (!WXApiImplComm.validateAppSignatureForPackage(this.context, "com.tencent.mm", this.checkSignature)) {
            Log.m4140e(TAG, "sendResp failed for wechat app signature check failed");
            AppMethodBeat.m2505o(128134);
            return false;
        } else if (baseResp.checkArgs()) {
            Bundle bundle = new Bundle();
            baseResp.toBundle(bundle);
            Args args = new Args();
            args.bundle = bundle;
            args.content = "weixin://sendresp?appid=" + this.appId;
            args.targetPkgName = "com.tencent.mm";
            args.targetClassName = "com.tencent.mm.plugin.base.stub.WXEntryActivity";
            boolean send = MMessageActV2.send(this.context, args);
            AppMethodBeat.m2505o(128134);
            return send;
        } else {
            Log.m4140e(TAG, "sendResp checkArgs fail");
            AppMethodBeat.m2505o(128134);
            return false;
        }
    }

    public void setLogImpl(ILog iLog) {
        AppMethodBeat.m2504i(128136);
        Log.setLogImpl(iLog);
        AppMethodBeat.m2505o(128136);
    }

    public void unregisterApp() {
        AppMethodBeat.m2504i(128105);
        if (this.detached) {
            IllegalStateException illegalStateException = new IllegalStateException("unregisterApp fail, WXMsgImpl has been detached");
            AppMethodBeat.m2505o(128105);
            throw illegalStateException;
        } else if (WXApiImplComm.validateAppSignatureForPackage(this.context, "com.tencent.mm", this.checkSignature)) {
            Log.m4139d(TAG, "unregisterApp, appId = " + this.appId);
            if (this.appId == null || this.appId.length() == 0) {
                Log.m4140e(TAG, "unregisterApp fail, appId is empty");
                AppMethodBeat.m2505o(128105);
                return;
            }
            Log.m4139d(TAG, "unregister app " + this.context.getPackageName());
            C32882a c32882a = new C32882a();
            c32882a.f15023a = "com.tencent.mm";
            c32882a.action = ConstantsAPI.ACTION_HANDLE_APP_UNREGISTER;
            c32882a.content = "weixin://unregisterapp?appid=" + this.appId;
            C37979a.m64269a(this.context, c32882a);
            AppMethodBeat.m2505o(128105);
        } else {
            Log.m4140e(TAG, "unregister app failed for wechat app signature check failed");
            AppMethodBeat.m2505o(128105);
        }
    }
}
