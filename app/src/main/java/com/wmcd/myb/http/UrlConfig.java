package com.wmcd.myb.http;

/**
 * Created by 邓志宏 on 2017/2/11.
 */
public class UrlConfig {
    /**
     * 判断11位电话号码格式的正则
     */
    public static final String PHONE_MATCH = "^1[34578]\\d{9}$";
    /**
     * 美易宝IP
     */

//    public static String IP = "http://192.168.90.55/meiyibao/";
    //public static String IP = "http://s.dzwapp.com/meiyibao/";
    public static  String IP = "https://www.meiyibaoapp.com/";

    /**
     * 图片加载前缀登录IP
     */
    public static final String getLogining = "appUserController/login";
    //登录
    public static final String getIsPhone = "appUserController/validate";
    //首页
    public static final String getHomeService = "appHomePageController/homePage";
    public static final String getHomeService01 = "appHomePageController/home";

    //首页.轮播
    public static final String getQueryAd = "appHomePageController/queryAd";
    //个人中心
    public static final String getUser = "appUserController/queryUser";
    //个人中心的模板
    public static final String getTemplateByUser = "appTemplateController/queryTemplateByUser";

    public static String IMG = "http://img.meiyibaoapp.com/";
    /**
     * 字体下载
     */
    public static String FONT = "http://font.meiyibaoapp.com/";
    //手机登录获取验证码
    public static final String getPhoneCode = "appUserController/code";
    //分类
    public static final String getClassify = "appTemplateController/queryTemplate";
    //预览
    public static final String getPictureEditor = "appTemplateController/queryTemplateDetail";
    //支付
    public static final String getCharge = "appPayController/createCharge";
    //支付反馈
    public static final String getFeedback = "appPayController/queryCharge";
    //查询会员信息
    public static final String getVIPmessage = "appUserController/queryMember";
    //客户信息录入
    public static final String getUserInformation = "appUserController/updateUser";
    //上传图片信息
    public static final String getGenerateImage = "appGraphController/generateImage";
    //提现
    public static final String getBankWithdraw = "appWithdrawController/withdrawApplication";
    //邮箱
    public static final String setEmail = "appPhotoController/sendMail";
    //账单详情
    public static final String getAccountInfo = "appUserController/queryInvitation";
    //文档模板
    public static final String getDocList = "appDocController/queryDoc";
    //文档下载路径
    public static String DOC_HTTP = "http://doc.meiyibaoapp.com/";
    //发送邮件
    public static final String SEND_MAIL = "appDocController/sendMail";
    //升级
    public static final String queryVersion = "appHomePageController/queryVersion";
    //通过验证码得到客户信息
    public static final String QUERY_USER_BYCODE = "appUserController/queryUserByInvitationCode";
    //模板分类
    public static final String QUERY_TEMPLATE_TYPE = "appTemplateController/queryTemplateType";
    //热门搜索
    public static final String BEFORE_SEARCH = "appHomePageController/beforeSearch";
    //搜索结果
    public static final String SEARCH = "appHomePageController/search";
    public static String endpoint = "http://oss-cn-shanghai.aliyuncs.com/";
    public static String testBucket = "myb-img";
    public static String accessKeyId = "LTAInYBFafmp6jDV";
    public static String accessKeySecret = "xJLjCxni2OtCJP7pzEvFuhh7hQhNve";
    //消息列表
    public static final String QUERY_MESSAGE = "appHomePageController/queryMessage";
    //修改消息状态
    public static final String UPDATE_MESSAGE = "appHomePageController/updateMessageRead";
    //查询用户
    public static final String QUERY_LOGO = "appLogoController/queryUserLogo";
    //制作Logo
    public static final String GENERATE_LOGO = "appLogoController/generateLogo";
    //添加logo
    public static final String ADD_LOGO = "appLogoController/addUserLogo";
    //删除logo
    public static final String DELETE_LOGO = "appLogoController/deleteUserLogo";
    //字体数据库
    public static final String FONTS_LIBRARY = "appFontController/queryFont";
    //得到
    public static final String QUERY_SERIES = "appTemplateController/querySeries";
    //分类.宣传套系
    public static final String PROPAGANDA_SYSTEM = "appTemplateController/querySeriesType";
    //分类.海报.系列
    public static final String TAG = "appTemplateController/queryTagByType";
    //精选推荐
    public static final String GET_RECOMMEND = "appTemplateController/queryRecommendTemplate";
    //query industry
    public static final String QUERY_INDUSTRY = "appTemplateController/queryIndustry";
    //设计师模板预览
    public static final String DESIGNER_TEMPLATE="appTemplateController/queryDesignerTemplate";
}
