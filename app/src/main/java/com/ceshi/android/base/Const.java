package com.ceshi.android.base;

/**
 * Created by killer on 16/1/21.
 */
public class Const {
//    public static String AppUrl = "https://www.cqdmoney.com/";
//    public static String AppUrl = "http://192.168.1.115:8080/fensantouziweb/";
    public static String AppUrl = "http://47.93.127.44/fensantouziweb/";
    public static String Login=AppUrl+"app/myLogin";
    //首页产品
    public static String MainPath=AppUrl+"app/product/index";
    //产品详情
    public static String Detailproduct=AppUrl+"app/user/product/detail";
    //注册
    public static String RegisterFinish=AppUrl+"app/regist";
    //注册协议
    public  static String RegistAgreement=AppUrl+"/static/app/registAgreement";
    //图片验证码
    public static String RegisterPicture=AppUrl+"app/imageCode";
    //产品债权列表
    public static String ZqList=AppUrl+"app/credit/credits";
    //债权详情
    public static String ZqDetail=AppUrl+"app/credit/credit/{creditId}";
    //我的推广
    public static String SpreadList=AppUrl+"app/user/myRecommendInfos";
    //我的 账户中心
    public static String MineCenter=AppUrl+"app/user/account/myHome";
    //安全中心
    public static String SafeCenter=AppUrl+"app/user/account/mySecurity";
    //官方动态
//    public static String Announcements=AppUrl+"static/app/announcements";
    public static String Announcements="http://47.93.127.44/fensantouziweb/static/app/announcements";
    //关于我们
    public static String Aboutus=AppUrl+"static/app/aboutus";
    //产品介绍
    public static String ProductIntruduce = AppUrl+"/app/product/introduce";
    //帮助中心
//    public static String HelpCenter=AppUrl+"static/app/help";
    public static String HelpCenter="http://47.93.127.44/fensantouziweb/static/app/help";
    //官方客服
    public static String Customer=AppUrl+"static/app/customer";
    //理财记录
    public static String LiCaiList=AppUrl+"app/user/invest/myInvest";
    //交易流水
    public static String TradeList=AppUrl+"app/user/invest/myTransaction";
    //我要投资
    public static String MyTzList=AppUrl+"app/product/products";
    //投资记录债权分配列表
    public static String TzZqList=AppUrl+"app/user/invest/creditDistribute";
   //发送短信验证码
    public static String SendMsgCode=AppUrl+"app/sendMobileCode";
    //首页公告
    public  static String MainMessage=AppUrl+"app/message/announcement";
    //首页公告详情
    public  static String MainMessageDetail=AppUrl+"/static/app/announcements";
    //充值初始页
    public static String Deposit_main=AppUrl+"app/user/invest/deposit";
    //充值界面
    public static String Deposit=AppUrl+"app/user/invest/deposit";
    //重新获取验证码
    public static String Recall_verify_code = AppUrl + "user/heepay/sendAuthPayMessage";
    //提现初始页
    public static String Withdraw_main=AppUrl+"app/user/account/withdraw";
    //提现
    public static String Withdraw=AppUrl+"app/user/account/withdraw";
    //首页轮播图
    public static String MainPicture=AppUrl+"app/image/indexCarousel";
    //发现界面 图片
    public static String FoundPicture=AppUrl+"app/image/found";
    //设置修改交易密码
    public static String updateTranPassword=AppUrl+"app/user/account/updateTranPassword";
    //发送交易密码验证短信
    public static String sendMobileCode=AppUrl+"app/user/sendMobileCode";
    //确认支付
    public static String Ensure_pay = AppUrl + "user/heepay/confirmAuthPay";
    //实名认证
    public static  String updateIdentification=AppUrl+"app/user/account/updateIdentification";
    //退出登录
//    public static String signoff=AppUrl+"app/signoff";
    public static String signoff=AppUrl+"app/logout";
    //消息中心
    public static String MessageCenter=AppUrl+"app/user/messages";
    //未读消息数量
    public static String UnreadMessageCount=AppUrl+"app/user/unreadMessageCount";
    //阅读消息
    public static String ReadMessage=AppUrl+"app/user/readMessage";
    //修改登录密码
    public static  String UpdataePassword=AppUrl+"app/user/account/updatePassword";
    //银行列表
    public static String BankInos=AppUrl+"app/user/account/bankInos";
    //省份列表
    public static String Provinces=AppUrl+"app/user/account/provinces";
    //城市列表
    public static String Cities=AppUrl+"app/user/account/cities";
    //绑定银行卡
    public static String BindBankCard=AppUrl+"app/user/invest/bindBankCard";
    //投资初始页
    public static String investInit=AppUrl+"app/user/invest/investInit";
    //投资
    public static String invest=AppUrl+"app/user/invest/invest";
    //找回密码
    public static String forgetPassword=AppUrl+"/forgetPassword";
//    转出
    public static String transfer = AppUrl + "/app/user/account/transfer";
    //    转出
    public static String changeTradePassword = AppUrl + "/app/user/account/updateTranPassword";
    //检查版本更新
    public static String checkForUpdate = AppUrl + "static/app/androidCheckUpgrade";
    //产品介绍
    public static String productIntroduce = AppUrl + "app/product/introduce";
}
