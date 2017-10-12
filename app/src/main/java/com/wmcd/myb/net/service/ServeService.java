package com.wmcd.myb.net.service;

import com.wmcd.myb.http.UrlConfig;
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

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 邓志宏 on 2017/2/11.
 */

public interface ServeService {
    /**
     * 获取验证码
     *
     * @return
     */
    @POST(UrlConfig.getPhoneCode)
    Call<LoginCodeModel> getPhoneCode(@Query("phone") String phone);

    /**
     * 登录
     *
     * @return
     */
    @POST(UrlConfig.getLogining)
    Call<LoginModel> getLogining(@Query("phone") String phone, @Query("name") String name, @Query("loginType") String loginType, @Query("loginId") String loginId, @Query("icon") String icon);
    /**
     * 判断是否已录入电话号码
     */
    @POST(UrlConfig.getIsPhone)
    Call<LoginCodeModel> getIsPhone(@Query("loginId") String loginId);

    /**
     * 首页
     *
     * @return
     */
    @POST(UrlConfig.getHomeService)
    Call<HomeModel> getHomeService();

    /**
     * 首页01
     *
     * @return
     */
    @POST(UrlConfig.getHomeService01)
    Call<HomeModel01> getHome01Service(@Query("uid") String uid);

    /**
     * 首页.轮播
     *
     * @return
     */
    @POST(UrlConfig.getQueryAd)
    Call<QueryAdModel> getQueryAd();

    /**
     * 个人中心
     *
     * @param id
     * @return
     */
    @POST(UrlConfig.getUser)
    Call<LoginModel> getUser(@Query("uid") String id);

    /**
     * 用户拥有的模板
     *
     * @param uid
     * @return
     */
    @POST(UrlConfig.getTemplateByUser)
    Call<TemplateByUserModel> getTemplateByUser(@Query("uid") String uid);

    /**
     * 分类
     * @param type
     * @return
     */
    @POST(UrlConfig.getClassify)
    Call<ClassifyModel> getClassify(@Query("uid") String uid,@Query("type") String type, @Query("currentPage") String page,@Query("tagid") String tagid,@Query("sid") String sid,@Query("hot") String hot,@Query("industryid") String industryid);

    /**
     * 预览
     *
     * @param pid
     * @return http://192.168.0.12/meiyibao/appTemplateController/queryTemplateDetail?tid=1&uid=1
     */
    @POST(UrlConfig.getPictureEditor)
    Call<PreviewModel> getPictureEditor(@Query("tid") String pid, @Query("uid") String uid);

    /**
     * ping++支付
     *
     * @param channel
     * @param subject
     * @param body
     * @param uid
     * @param mid
     * @param beginTime
     * @param endTime
     * @return
     */
    @POST(UrlConfig.getCharge)
    Call<ChargeModel> getCharge(@Query("amount") String amount, @Query("channel") String channel, @Query("subject") String subject, @Query("body") String body, @Query("uid") String uid, @Query("mid") String mid, @Query("beginTime") String beginTime, @Query("endTime") String endTime, @Query("invitationCode") String invitationCode);

    /**
     * 支付反馈
     *
     * @param code
     * @return
     */
    @POST(UrlConfig.getFeedback)
    Call<FeedbackModel> getFeedback(@Query("code") String code);

    /**
     * 会员信息.未购买
     *
     * @return
     */
    @POST(UrlConfig.getVIPmessage)
    Call<MemberModel> getVIPmessage();

    /**
     * 客户信息录入
     */
    @POST(UrlConfig.getUserInformation)
    Call<InformationModel> getUserInformation(@Query("uid") String uid, @Query("name") String name, @Query("phone") String phone, @Query("address") String address, @Query("city") String city, @Query("shopname") String shopname, @Query("industry") String industry);

    /**
     * 图片信息上传
     *
     * @return
     */
    @POST(UrlConfig.getGenerateImage)
    Call<GenerateImageModel> getGenerateImage(@Body RequestBody body);

    /**
     * 提现
     *
     * @param uid
     * @param name
     * @param bankno
     * @param amount
     * @return uid=1&name=张三&bankno=60010201252145521&amount=100
     */
    @POST(UrlConfig.getBankWithdraw)
    Call<ResultModel> getBankWithdraw(@Query("uid") int uid, @Query("name") String name, @Query("bankno") String bankno, @Query("amount") String amount, @Query("bankname") String bankname);

    /**
     * 上传邮箱
     *
     * @param photoid
     * @param email
     * @return
     */
    @POST(UrlConfig.setEmail)
    Call<ResultModel> setEmail(@Query("photoid") int photoid, @Query("email") String email,@Query("img") String img);

    /**
     * 获取账单信息
     */
    @GET(UrlConfig.getAccountInfo)
    Call<AccountModel> getAccountInfo(@Query("uid") String uid, @Query("currentPage") String currentPage);


    /**
     * 获取文档模板
     */
    @GET(UrlConfig.getDocList)
    Call<DocModel> getDocList(@Query("currentPage") String currentPage);

    /**
     * 发送邮件
     */
    @POST(UrlConfig.SEND_MAIL)
    Call<ResultModel> sendEmial(@Query("did") String did, @Query("email") String email);

    /***
     * 升级
     */
    @POST(UrlConfig.queryVersion)
    Call<VersionBean> validateVesion();

    /***
     *通过验证码得到朋友的信息
     */
    @POST(UrlConfig.QUERY_USER_BYCODE)
    Call<LoginModel> queryUserByInvitationCode(@Query("invitationCode") String invitationCode);

    /**
     * 模板分类查询
     */
    @POST(UrlConfig.QUERY_TEMPLATE_TYPE)
    Call<CategoryModel> queryTemplateType();

    /**
     * 热门关键字搜索
     */
    @POST(UrlConfig.BEFORE_SEARCH)
    Call<SearchBean> beforeSearch();

    /**
     * 搜索结果
     */
    @POST(UrlConfig.SEARCH)
    Call<ClassifyModel> search(@Query("word") String word, @Query("currentPage") String currentPage);

    /**
     * 系统消息
     */
    @POST(UrlConfig.QUERY_MESSAGE)
    Call<SystemMsgBean> queryMessage(@Query("uid") String uid, @Query("type") String type, @Query("currentPage") String currentPage);

    /**
     * 修改消息状态
     */
    @POST(UrlConfig.UPDATE_MESSAGE)
    Call<ResultModel> updateMessage(@Query("msgid") String msgid);

    /**
     * 查询用户logo
     */

    @POST(UrlConfig.QUERY_LOGO)
    Call<LogoModel> myLogo(@Query("uid") String uid);

    /**
     * 生成logo
     */
    @POST(UrlConfig.GENERATE_LOGO)
    Call<GenerateLogoModel> generateLogo(@Query("text") String text);

    /**
     * 添加logo
     */
    @POST(UrlConfig.ADD_LOGO)
    Call<AddLogo> addLogo(@Query("uid") String uid, @Query("logoid") String logoid, @Query("text") String text, @Query("img") String img);
    /**
     * 删除logo
     * */
    @POST(UrlConfig.DELETE_LOGO)
    Call<DeleteLogo> deleteLogo(@Query("luid") String luid);

    /**
     * 获取字体列表
     * @return
     */
    @POST(UrlConfig.FONTS_LIBRARY)
    Call<FontsModel> getFonts();

    /**
     * 获取系列推荐
     */
    @POST(UrlConfig.QUERY_SERIES)
    Call<SeriesModel> getSeries(@Query("type") String sid);

    /**
     * 宣传套系
     * @return
     */
    @POST(UrlConfig.PROPAGANDA_SYSTEM)
    Call<PropagandaModel> getPropaganda();

    /**
     * 海报.系列
     * @return
     */
    @POST(UrlConfig.TAG)
    Call<SeriesModel> getTag(@Query("type") String type);
/*REMEN */
    @POST(UrlConfig.GET_RECOMMEND)
    Call<RecommendModel> getRecommend();
    @POST(UrlConfig.QUERY_INDUSTRY)
    Call<IndustryModel> getIndustry(@Query("type") String type);

    /**
     * 设计师模板
     * @param uid
     * @return
     */
    @POST(UrlConfig.DESIGNER_TEMPLATE)
    Call<DesignerModel> getDesigner(@Query("uid") String uid, @Query("currentPage") String currentPage);

}
