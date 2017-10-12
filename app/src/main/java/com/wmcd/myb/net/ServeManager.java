package com.wmcd.myb.net;

import android.content.Context;

import com.wmcd.myb.http.BaseManager;
import com.wmcd.myb.model.AccountModel;
import com.wmcd.myb.model.AddLogo;
import com.wmcd.myb.model.CategoryModel;
import com.wmcd.myb.model.ChargeModel;
import com.wmcd.myb.model.ClassifyModel;
import com.wmcd.myb.model.DeleteLogo;
import com.wmcd.myb.model.DesignerModel;
import com.wmcd.myb.model.DocModel;
import com.wmcd.myb.model.FeedbackModel;
import com.wmcd.myb.model.FontsModel;
import com.wmcd.myb.model.GenerateImageModel;
import com.wmcd.myb.model.GenerateLogoModel;
import com.wmcd.myb.model.HomeModel;
import com.wmcd.myb.model.HomeModel01;
import com.wmcd.myb.model.IndustryModel;
import com.wmcd.myb.model.InformationModel;
import com.wmcd.myb.model.LoginCodeModel;
import com.wmcd.myb.model.LoginModel;
import com.wmcd.myb.model.LogoModel;
import com.wmcd.myb.model.MemberModel;
import com.wmcd.myb.model.PreviewModel;
import com.wmcd.myb.model.PropagandaModel;
import com.wmcd.myb.model.QueryAdModel;
import com.wmcd.myb.model.RecommendModel;
import com.wmcd.myb.model.ResultModel;
import com.wmcd.myb.model.SearchBean;
import com.wmcd.myb.model.SeriesModel;
import com.wmcd.myb.model.SystemMsgBean;
import com.wmcd.myb.model.TemplateByUserModel;
import com.wmcd.myb.model.VersionBean;
import com.wmcd.myb.net.service.ServeService;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by 邓志宏 on 2017/2/11.
 */
public class ServeManager extends BaseManager {
    /**
     * Gets phone code.
     *
     * @param context the context
     * @param phone   the phone
     * @return the phone code
     */
    public static Call<LoginCodeModel> getPhoneCode(Context context, String phone) {
        return createService(context).getPhoneCode(phone);
    }

    /**
     * Gets home 01 service.
     *
     * @param context the context
     * @param uid     the uid
     * @return the home 01 service
     */
    public static Call<HomeModel01> getHome01Service(Context context,String uid) {
        return createService(context).getHome01Service(uid);
    }

    /**
     * Gets logining.
     *
     * @param context   the context
     * @param phone     the phone
     * @param name      the name
     * @param loginType the login type
     * @param loginId   the login id
     * @param icon      the icon
     * @return the logining
     */
    public static Call<LoginModel> getLogining(Context context, String phone, String name, String loginType, String loginId, String icon) {
        return createService(context).getLogining(phone, name, loginType, loginId, icon);
    }

    public static Call<LoginCodeModel> getIsPhone(Context context,String loginId){
        return createService(context).getIsPhone(loginId);
    }

    /**
     * Gets home service.
     *
     * @param context the context
     * @return the home service
     */
    public static Call<HomeModel> getHomeService(Context context) {
        return createService(context).getHomeService();
    }

    /**
     * Gets query ad.
     *
     * @param context the context
     * @return the query ad
     */
    public static Call<QueryAdModel> getQueryAd(Context context) {
        return createService(context).getQueryAd();
    }

    /**
     * Gets user.
     *
     * @param context the context
     * @param id      the id
     * @return the user
     */
    public static Call<LoginModel> getUser(Context context, String id) {
        return createService(context).getUser(id);
    }

    /**
     * Gets template by user.
     *
     * @param context the context
     * @param uid     the uid
     * @return the template by user
     */
    public static Call<TemplateByUserModel> getTemplateByUser(Context context, String uid) {
        return createService(context).getTemplateByUser(uid);
    }

    /**
     * Gets classify.
     *
     * @param context the context
     * @param type    the type
     * @param page    the page
     * @param tagid   the tagid
     * @param sid     the sid
     * @return the classify
     */
    public static Call<ClassifyModel> getClassify(Context context,String uid, String type, String page,String tagid,String sid,String hot,String industryid) {
        return createService(context).getClassify(uid,type, page,tagid,sid,hot,industryid);
    }

    /**
     * Gets picture editor.
     *
     * @param context the context
     * @param id      the id
     * @param uid     the uid
     * @return the picture editor
     */
    public static Call<PreviewModel> getPictureEditor(Context context, String id, String uid) {
        return createService(context).getPictureEditor(id, uid);
    }

    /**
     * Gets charge.
     *
     * @param context        the context
     * @param amount         the amount
     * @param channel        the channel
     * @param subject        the subject
     * @param body           the body
     * @param uid            the uid
     * @param mid            the mid
     * @param beginTime      the begin time
     * @param endTime        the end time
     * @param invitationCode the invitation code
     * @return the charge
     */
    public static Call<ChargeModel> getCharge(Context context, String amount, String channel, String subject, String body, String uid, String mid, String beginTime, String endTime, String invitationCode) {
        return createService(context).getCharge(amount, channel, subject, body, uid, mid, beginTime, endTime, invitationCode);
    }

    /**
     * Gets feedback.
     *
     * @param context the context
     * @param code    the code
     * @return the feedback
     */
    public static Call<FeedbackModel> getFeedback(Context context, String code) {
        return createService(context).getFeedback(code);
    }

    /**
     * Gets vi pmessage.
     *
     * @param context the context
     * @return the vi pmessage
     */
    public static Call<MemberModel> getVIPmessage(Context context) {
        return createService(context).getVIPmessage();
    }

    /**
     * Gets user information.
     *
     * @param context  the context
     * @param uid      the uid
     * @param name     the name
     * @param phone    the phone
     * @param address  the address
     * @param city     the city
     * @param shopname the shopname
     * @param industry the industry
     * @return the user information
     */
    public static Call<InformationModel> getUserInformation(Context context, String uid, String name, String phone, String address, String city, String shopname, String industry) {
        return createService(context).getUserInformation(uid, name, phone, address, city, shopname, industry);
    }

    /**
     * Gets generate image.
     *
     * @param context the context
     * @param body    the body
     * @return the generate image
     */
    public static Call<GenerateImageModel> getGenerateImage(Context context, RequestBody body) {
        return createService(context).getGenerateImage(body);
    }

    /**
     * Gets bank withdraw.
     *
     * @param context  the context
     * @param uid      the uid
     * @param name     the name
     * @param bankno   the bankno
     * @param amount   the amount
     * @param bankname the bankname
     * @return the bank withdraw
     */
    public static Call<ResultModel> getBankWithdraw(Context context, int uid, String name, String bankno, String amount, String bankname) {
        return createService(context).getBankWithdraw(uid, name, bankno, amount, bankname);
    }

    /**
     * Sets email.
     *
     * @param context the context
     * @param photoid the photoid
     * @param email   the email
     * @param img     the img
     * @return the email
     */
    public static Call<ResultModel> setEmail(Context context, int photoid, String email,String img) {
        return createService(context).setEmail(photoid, email,img);
    }

    /**
     * Create service serve service.
     *
     * @param context the context
     * @return the serve service
     */
    private static ServeService createService(Context context) {
        return create(context, ServeService.class);
    }

    /**
     * Gets account info.
     *
     * @param context     the context
     * @param uid         the uid
     * @param currentPage the current page
     * @return the account info
     */
    public static Call<AccountModel> getAccountInfo(Context context, String uid, String currentPage) {
        return createService(context).getAccountInfo(uid, currentPage);
    }

    /**
     * Gets doc list.
     *
     * @param context     the context
     * @param currentPage the current page
     * @return the doc list
     */
    public static Call<DocModel> getDocList(Context context, String currentPage) {
        return createService(context).getDocList(currentPage);
    }

    /**
     * Send emial call.
     *
     * @param context the context
     * @param did     the did
     * @param email   the email
     * @return the call
     */
    public static Call<ResultModel> sendEmial(Context context, String did, String email) {
        return createService(context).sendEmial(did, email);
    }

    /**
     * Validate vesion call.
     *
     * @param context the context
     * @return the call
     */
    public static Call<VersionBean> validateVesion(Context context) {
        return createService(context).validateVesion();
    }

    /**
     * Query user by invitation code call.
     *
     * @param context        the context
     * @param invitationCode the invitation code
     * @return the call
     */
    public static Call<LoginModel> queryUserByInvitationCode(Context context, String invitationCode) {
        return createService(context).queryUserByInvitationCode(invitationCode);
    }

    /**
     * Query template type call.
     *
     * @param context the context
     * @return the call
     */
    public static Call<CategoryModel> queryTemplateType(Context context) {
        return createService(context).queryTemplateType();
    }

    /**
     * Before search call.
     *
     * @param context the context
     * @return the call
     */
    public static Call<SearchBean> beforeSearch(Context context) {
        return createService(context).beforeSearch();
    }

    /**
     * Search call.
     *
     * @param context the context
     * @param key     the key
     * @param page    the page
     * @return the call
     */
    public static Call<ClassifyModel> search(Context context, String key, String page) {
        return createService(context).search(key, page);
    }

    /**
     * Query message call.
     *
     * @param context     the context
     * @param uid         the uid
     * @param type        the type
     * @param currentPage the current page
     * @return the call
     */
    public static Call<SystemMsgBean> queryMessage(Context context, String uid, String type, String currentPage) {
        return createService(context).queryMessage(uid, type, currentPage);
    }

    /**
     * Update message call.
     *
     * @param context the context
     * @param msgid   the msgid
     * @return the call
     */
    public static Call<ResultModel> updateMessage(Context context, String msgid) {
        return createService(context).updateMessage(msgid);
    }

    /**
     * My logo call.
     *
     * @param context the context
     * @param uid     the uid
     * @return the call
     */
    public static Call<LogoModel> myLogo(Context context, String uid) {
        return createService(context).myLogo(uid);
    }

    /**
     * Generate logo call.
     *
     * @param context the context
     * @param text    the text
     * @return the call
     */
    public static Call<GenerateLogoModel> generateLogo(Context context, String text) {
        return createService(context).generateLogo(text);
    }

    /**
     * Add logo call.
     *
     * @param context the context
     * @param uid     the uid
     * @param logoid  the logoid
     * @param text    the text
     * @param img     the img
     * @return the call
     */
    public static Call<AddLogo> addLogo(Context context,String uid,String logoid, String text,String img) {
        return createService(context).addLogo(uid,logoid,text,img);
    }
    public static Call<DeleteLogo> deleteLogo(Context context, String luid) {
        return createService(context).deleteLogo(luid);
    }


    /**
     * Gets fonts.
     *
     * @param context the context
     * @return the fonts
     */
    public static Call<FontsModel> getFonts(Context context) {
        return createService(context).getFonts();
    }

    /**
     * Gets series.
     *
     * @param context the context
     * @param sid     the sid
     * @return the series
     */
    public static Call<SeriesModel> getSeries(Context context,String sid) {
        return createService(context).getSeries(sid);
    }

    /**
     * Get propaganda call.
     *
     * @param context the context
     * @return the call
     */
    public static Call<PropagandaModel> getPropaganda(Context context){
        return createService(context).getPropaganda();
    }

    /**
     * Get tag call.
     *
     * @param context the context
     * @param type    the type
     * @return the call
     */
    public static Call<SeriesModel> getTag(Context context,String type){
        return createService(context).getTag(type);
    }
    public static Call<RecommendModel> getRecommend(Context context){
        return createService(context).getRecommend();
    }
    public static Call<IndustryModel> getIndustry(Context context, String type){
        return createService(context).getIndustry(type);
    }
    public static Call<DesignerModel> getDesigner(Context context,String uid, String page){
        return createService(context).getDesigner(uid,page);
    }

}