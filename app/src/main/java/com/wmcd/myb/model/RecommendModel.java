package com.wmcd.myb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class RecommendModel {

    /**
     * result : 01
     * msg : 成功！
     * recommendTemplates : [{"recommendTemplateList":[{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"a6d60af7a60f4a9abe2b18273455a041","description":"HY-TX-04.14.10","createdate":"2017-04-24 13:56:52","viewtype":1,"mname":"合伙人会员","type":3,"tid":337,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.10","width":700,"listtype":2,"useCount":91,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"6d49fc5ba31a4000beb5a89a7679cb12","description":"HY-TX-04.14.09","createdate":"2017-04-24 13:46:27","viewtype":1,"mname":"合伙人会员","type":3,"tid":336,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.09","width":700,"listtype":2,"useCount":44,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"a84667f2319b409881a69de2fed4c1bb","description":"HY-TX-04.14.08","createdate":"2017-04-24 11:38:27","viewtype":1,"mname":"合伙人会员","type":3,"tid":335,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.08","width":700,"listtype":2,"useCount":71,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"941f8f0182d048c8b14328aca590bec5","description":"HY-TX-04.14.07","createdate":"2017-04-24 11:29:37","viewtype":1,"mname":"合伙人会员","type":3,"tid":334,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.07","width":2918,"listtype":2,"useCount":32,"open":0,"height":2918,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"eaa4425dadb64745bdb814195b77c8d6","description":"HY-TX-04.14.06","createdate":"2017-04-24 11:15:04","viewtype":1,"mname":"合伙人会员","type":3,"tid":333,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.06","width":2918,"listtype":2,"useCount":14,"open":0,"height":2918,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"24f768fad7f94983b599fa28449762f9","description":"HY-TX-04.14.04","createdate":"2017-04-24 10:20:13","viewtype":1,"mname":"合伙人会员","type":3,"tid":331,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.04","width":2918,"listtype":2,"useCount":9,"open":0,"height":2917,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"68b9c1173a334d8fa2ad29cb8c1e8fec","description":"HY-TX-04.14.03","createdate":"2017-04-24 09:59:37","viewtype":1,"mname":"合伙人会员","type":3,"tid":329,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.03","width":2918,"listtype":2,"useCount":15,"open":0,"height":2917,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"2bc0f5f04fa0409686632d61f1537144","description":"YY-TX-042107","createdate":"2017-04-21 16:27:54","viewtype":1,"mname":"合伙人会员","type":3,"tid":317,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"YY-TX-042107","width":2917,"listtype":2,"useCount":9,"open":0,"height":2917,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"11b22b7b978b44dc81507b94e2f37c62","description":"曼文","createdate":"2017-04-01 17:15:34","viewtype":1,"mname":"合伙人会员","type":3,"tid":139,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像18","width":700,"listtype":2,"useCount":193,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"a35649c8711b4998a9ea96a1fa918968","description":"熙雯","createdate":"2017-03-31 17:22:41","viewtype":1,"mname":"合伙人会员","type":3,"tid":158,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像27","width":700,"listtype":2,"useCount":288,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"49e91c5f72db4a9c86e6e26f85d3d220","description":"高智旋","createdate":"2017-03-31 11:29:41","viewtype":1,"mname":"合伙人会员","type":3,"tid":159,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像28","width":700,"listtype":2,"useCount":17,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"81babc4e6b3c40cc9f51650a7cf8d232","description":"笑笑","createdate":"2017-03-30 19:34:07","viewtype":1,"mname":"合伙人会员","type":3,"tid":206,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像35","width":1181,"listtype":2,"useCount":54,"open":0,"height":1181,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"1a518d0c81cf494cbaf0cbbd1e5016dc","description":"美琳","createdate":"2017-03-25 11:41:23","viewtype":1,"mname":"合伙人会员","type":3,"tid":161,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像29","width":701,"listtype":2,"useCount":33,"open":0,"height":701,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"08e49973d7e3437d92dd132a97d5d8d9","description":"高菲菲","createdate":"2017-03-24 21:20:47","viewtype":1,"mname":"合伙人会员","type":3,"tid":143,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像21","width":700,"listtype":2,"useCount":38,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"3856c8d68da84423bfd5c89c7b25b44b","description":"凌萱","createdate":"2017-03-23 17:10:11","viewtype":1,"mname":"合伙人会员","type":3,"tid":147,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像22","width":700,"listtype":2,"useCount":108,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"994c4d69b96849318688ab82a40a12a6","description":"依/娜","createdate":"2017-03-23 10:50:55","viewtype":1,"mname":"黄金会员","type":3,"tid":120,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"65b01885041550009983d1c7a75e2a87.png","name":"头像14","width":701,"listtype":2,"useCount":16,"open":0,"height":701,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"60cb0ef7c2da416c86d76c91217f1d4d","description":"段萱","createdate":"2017-03-23 10:15:46","viewtype":1,"mname":"合伙人会员","type":3,"tid":119,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像13","width":700,"listtype":2,"useCount":22,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"fefc13a83e344364babe24b65aa827a9","description":"美梦成真","createdate":"2017-03-23 00:03:46","viewtype":1,"mname":"合伙人会员","type":3,"tid":220,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"美梦成真","width":2953,"listtype":2,"useCount":40,"open":0,"height":2953,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"1cd9967af6415b05938f8987360d2738.png","description":"传奇一生","createdate":"2017-03-21 21:05:39","viewtype":1,"mname":"黄金会员","type":3,"tid":35,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"65b01885041550009983d1c7a75e2a87.png","name":"头像10","width":700,"listtype":2,"useCount":39,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"a9d7ee9b378f4751be4637209d915647","description":"个人头像","createdate":"2017-03-20 13:44:28","viewtype":1,"mname":"合伙人会员","type":3,"tid":22,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像9","width":700,"listtype":2,"useCount":36,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"de7718cf3c4948e6a06543dc1e339831","description":"个人头像","createdate":"2017-03-20 13:44:27","viewtype":1,"mname":"合伙人会员","type":3,"tid":21,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像8","width":700,"listtype":2,"useCount":13,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"de0195296644411ebe22853fe74a1757","description":"个人头像","createdate":"2017-03-20 13:44:24","viewtype":1,"mname":"合伙人会员","type":3,"tid":19,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像6","width":700,"listtype":2,"useCount":20,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"2d262121515b46f68f9066b4824c1a84","description":"个人头像","createdate":"2017-03-20 13:44:23","viewtype":1,"mname":"合伙人会员","type":3,"tid":18,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像5","width":700,"listtype":2,"useCount":20,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"8f36e0aa0d6444af9002b7dce2e9832d","description":"个人头像","createdate":"2017-03-20 13:44:22","viewtype":1,"mname":"合伙人会员","type":3,"tid":17,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像4","width":700,"listtype":2,"useCount":12,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"e667bfe4eac1456cb0bafd217ecedc74","description":"个人头像","createdate":"2017-03-20 13:44:19","viewtype":1,"mname":"黄金会员","type":3,"tid":15,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"65b01885041550009983d1c7a75e2a87.png","name":"头像2","width":700,"listtype":2,"useCount":18,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"f2ec87de1fa14dc98474169bae1cdcc0","description":"个人头像","createdate":"2017-03-20 13:44:18","viewtype":1,"mname":"免费","type":3,"tid":14,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"","name":"头像1","width":700,"listtype":2,"useCount":67,"open":1,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"eddeaefb0d8d4ca6a42668b90eb3b97b","description":"财富论坛2","createdate":"2017-03-10 23:29:48","viewtype":1,"mname":"合伙人会员","type":3,"tid":218,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"财富论坛2","width":1181,"listtype":2,"useCount":62,"open":0,"height":1181,"order":0,"status":0}],"name":"头像","type":3},{"recommendTemplateList":[{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"06eb84968a0b4e74b81def10cfd85184","description":"HY-HB-051102","createdate":"2017-05-11 16:59:28","viewtype":1,"mname":"合伙人会员","type":4,"tid":503,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-HB-051102","width":2481,"listtype":2,"useCount":2,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"508b617f4edd4eb0aede88fda9bb4599","description":"HY-HB-051102","createdate":"2017-05-11 16:51:14","viewtype":1,"mname":"合伙人会员","type":4,"tid":502,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-HB-051102","width":2481,"listtype":2,"useCount":6,"open":0,"height":3367,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"cb8b695d1531406b9b202fedf36f0db3","description":"HY-HB-520-Z","createdate":"2017-05-11 16:39:09","viewtype":1,"mname":"合伙人会员","type":4,"tid":501,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-HB-520-Z","width":2480,"listtype":2,"useCount":6,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"ba846e3c60ac4910aaae677879d5f478","description":"HY-HB-051101","createdate":"2017-05-11 16:23:06","viewtype":1,"mname":"合伙人会员","type":4,"tid":500,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-HB-051101","width":2481,"listtype":2,"useCount":0,"open":0,"height":3367,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"bf3dfecb80fe4d659a15067c4d1e5714","description":"SD-HB-520","createdate":"2017-05-11 16:05:50","viewtype":1,"mname":"合伙人会员","type":4,"tid":499,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"SD-HB-520","width":2480,"listtype":2,"useCount":0,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"10c421f8a8d5499d9c29081f7871f0ca","description":"WYL-HB-520","createdate":"2017-05-11 15:39:19","viewtype":1,"mname":"合伙人会员","type":4,"tid":498,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"WYL-HB-520","width":2480,"listtype":2,"useCount":0,"open":0,"height":3508,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"efed14adb10a4e578a8b668707338a19","description":"HY-HB-051101","createdate":"2017-05-11 15:32:47","viewtype":1,"mname":"合伙人会员","type":4,"tid":497,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-HB-051101","width":2481,"listtype":2,"useCount":0,"open":0,"height":3367,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"d9284565d054498989ce58a889c6fecd","description":"HL-HB-520","createdate":"2017-05-11 14:29:12","viewtype":1,"mname":"合伙人会员","type":4,"tid":496,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HL-HB-520","width":2480,"listtype":2,"useCount":1,"open":0,"height":3508,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"2ad7774399d9470a8cf272f2c47ac8eb","description":"YY-HB-520","createdate":"2017-05-11 14:13:34","viewtype":1,"mname":"合伙人会员","type":4,"tid":495,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"YY-HB-520","width":2480,"listtype":2,"useCount":1,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"83da03af9f654f1c96bdd9c94cfb7be0","description":"MQJ-MJ5.10","createdate":"2017-05-10 14:27:16","viewtype":1,"mname":"合伙人会员","type":4,"tid":494,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"MQJ-MJ5.10","width":2480,"listtype":2,"useCount":9,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"2b5ae3a40d6242809b0b4efcc9369d8b","description":"CN-HB-051001","createdate":"2017-05-10 14:22:38","viewtype":1,"mname":"合伙人会员","type":4,"tid":493,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"CN-HB-051001","width":2480,"listtype":2,"useCount":4,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"d2a22fc65f8e43f3bbd62012f27b46de","description":"HL-HB-510","createdate":"2017-05-10 14:15:09","viewtype":1,"mname":"合伙人会员","type":4,"tid":492,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HL-HB-510","width":2480,"listtype":2,"useCount":2,"open":0,"height":3508,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"daf99d805987499d81ec72e3ff6ea371","description":"SD-MQJHB-051002","createdate":"2017-05-10 14:12:57","viewtype":1,"mname":"合伙人会员","type":4,"tid":491,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"SD-MQJHB-051002","width":2480,"listtype":2,"useCount":0,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"390baeebfbc44bdab9e5fa213e807156","description":"YY-HB-051001","createdate":"2017-05-10 13:53:06","viewtype":1,"mname":"合伙人会员","type":4,"tid":490,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"YY-HB-051001","width":2480,"listtype":2,"useCount":1,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"2a9a96356a0945c4845f47c831b068d5","description":"SD-MQJHB-051001","createdate":"2017-05-10 13:43:36","viewtype":1,"mname":"合伙人会员","type":4,"tid":489,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"SD-MQJHB-051001","width":2480,"listtype":2,"useCount":1,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"91a8a7147d194e1abbf7eb302a833679","description":"HY-HB-10","createdate":"2017-05-10 10:43:12","viewtype":1,"mname":"合伙人会员","type":4,"tid":488,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-HB-10","width":2481,"listtype":2,"useCount":0,"open":0,"height":3367,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"4cc4c026d4e24c2888c8ff6c3d236ebe","description":"WYL-HB-510","createdate":"2017-05-10 10:30:10","viewtype":1,"mname":"合伙人会员","type":4,"tid":487,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"WYL-HB-510","width":2480,"listtype":2,"useCount":3,"open":0,"height":3508,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"4641e55908ef43debe353915bd81236e","description":"HY-HB-051001","createdate":"2017-05-10 09:58:42","viewtype":1,"mname":"合伙人会员","type":4,"tid":486,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-HB-051001","width":2481,"listtype":2,"useCount":2,"open":0,"height":3367,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"872c34e5ce8341d89d643390e2150c41","description":"YY-HB-050301","createdate":"2017-05-03 11:39:16","viewtype":1,"mname":"合伙人会员","type":4,"tid":485,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"YY-HB-050301","width":4724,"listtype":2,"useCount":0,"open":0,"height":7087,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"134a39a940da4f78b3c970de60858da8","description":"SD-HB-05.01.01","createdate":"2017-05-01 15:04:49","viewtype":1,"mname":"合伙人会员","type":4,"tid":481,"sid":5,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"SD-HB-05.01.01","width":2480,"listtype":2,"useCount":3,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"c11e669219274fee9656c935abb01073","description":"HY-HB-5.1","createdate":"2017-05-01 14:50:29","viewtype":1,"mname":"合伙人会员","type":4,"tid":480,"sid":9,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-HB-5.1","width":2481,"listtype":2,"useCount":7,"open":0,"height":3367,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"d6bbebff71da471b8a88080612e8f9e1","description":"LT-HB-050101","createdate":"2017-05-01 10:57:10","viewtype":1,"mname":"合伙人会员","type":4,"tid":471,"sid":7,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"LT-HB-050101","width":2480,"listtype":2,"useCount":0,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"9db07a251542443d93b2730c6d1f1c6c","description":"SD-ZP-043002","createdate":"2017-04-30 11:56:59","viewtype":1,"mname":"合伙人会员","type":4,"tid":462,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"SD-ZP-043002","width":2480,"listtype":2,"useCount":2,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"dacb4df453464ef7a89bdf07b0543ccd","description":"SD-ZMHB-04303","createdate":"2017-04-30 11:31:49","viewtype":1,"mname":"合伙人会员","type":4,"tid":460,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"SD-ZMHB-04303","width":2480,"listtype":2,"useCount":0,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"50a35bc6cb6742fb8594c7134932c5fd","description":"YY-ZP-043001","createdate":"2017-04-30 10:57:04","viewtype":1,"mname":"合伙人会员","type":4,"tid":457,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"YY-ZP-043001","width":2480,"listtype":2,"useCount":0,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"93696bf0356044a7be9aa95326c3f306","description":"HL-ZSHB-042701","createdate":"2017-04-27 18:55:46","viewtype":1,"mname":"合伙人会员","type":4,"tid":445,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HL-ZSHB-042701","width":750,"listtype":2,"useCount":1,"open":0,"height":1334,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"f164a17618f54ac1bbf62b7b87e59c4c","description":"Y-ZP-042601","createdate":"2017-04-26 18:59:27","viewtype":1,"mname":"合伙人会员","type":4,"tid":426,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"Y-ZP-042601","width":2480,"listtype":2,"useCount":4,"open":0,"height":3547,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"bf469825a4d244ea988a6303088582b0","description":"Y-ZP-04.21.01","createdate":"2017-04-26 14:48:14","viewtype":1,"mname":"合伙人会员","type":4,"tid":416,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"Y-ZP-04.21.01","width":2480,"listtype":2,"useCount":2,"open":0,"height":3508,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"ac7137e17eb84f53b21485ccfa69a382","description":"HL-ZPHB-042602","createdate":"2017-04-26 14:42:30","viewtype":1,"mname":"合伙人会员","type":4,"tid":415,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HL-ZPHB-042602","width":2480,"listtype":2,"useCount":2,"open":0,"height":3405,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"a4c72e1b1acd4f21b49eacbc6a5063e8","description":"LT-ZPHB-042601","createdate":"2017-04-26 13:53:49","viewtype":1,"mname":"合伙人会员","type":4,"tid":411,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"LT-ZPHB-042601","width":2480,"listtype":2,"useCount":8,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"79efdcb3219947c0b5001e6bf7f938f9","description":"LT-HB-04.17.01","createdate":"2017-04-21 16:40:38","viewtype":1,"mname":"合伙人会员","type":4,"tid":319,"sid":1,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"LT-HB-04.17.01","width":2480,"listtype":2,"useCount":28,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"469d2fa3c46042318ac756da28623817","description":"H-Y0421、海报","createdate":"2017-04-21 16:30:21","viewtype":1,"mname":"合伙人会员","type":4,"tid":318,"sid":6,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"H-Y0421","width":2481,"listtype":2,"useCount":65,"open":0,"height":3367,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"21c807470cfb42b999ff6834b8def55a","description":"盛夏妆不花","createdate":"2017-04-19 16:34:02","viewtype":1,"mname":"合伙人会员","type":4,"tid":262,"sid":3,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"盛夏妆不花","width":2481,"listtype":2,"useCount":0,"open":0,"height":3508,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"2cdd21c375ec4554a3fbb2da8220b5ec","description":"最美丽的夏季","createdate":"2017-04-18 17:39:48","viewtype":1,"mname":"合伙人会员","type":4,"tid":250,"sid":8,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"最美丽的夏季","width":2480,"listtype":2,"useCount":1,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"4d63f469b4e64a02aab17e4b89fb632e","description":"五四青春","createdate":"2017-04-18 17:33:27","viewtype":1,"mname":"合伙人会员","type":4,"tid":249,"sid":4,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"五四青春","width":2480,"listtype":2,"useCount":3,"open":0,"height":3508,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"462f77a06ff24ea78089971962bc0c01","description":"温情五月","createdate":"2017-04-18 10:34:42","viewtype":1,"mname":"合伙人会员","type":4,"tid":248,"sid":2,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"温情五月","width":2480,"listtype":2,"useCount":5,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"ce700177f9f54edd8005c23e5823f028","description":"美眉","createdate":"2017-03-28 15:01:33","viewtype":1,"mname":"合伙人会员","type":4,"tid":168,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报26","width":596,"listtype":2,"useCount":45,"open":0,"height":888,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"3a1320ce5a6e4724ae235816daae3f95","description":"海报","createdate":"2017-03-27 13:50:44","viewtype":1,"mname":"合伙人会员","type":4,"tid":30,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报6","width":750,"listtype":2,"useCount":37,"open":0,"height":1120,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"2190e4eb5c0c4d68b81bde300d432fe6","description":"感恩季爱自己","createdate":"2017-03-27 11:53:20","viewtype":1,"mname":"合伙人会员","type":4,"tid":106,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报8","width":2268,"listtype":2,"useCount":42,"open":0,"height":3402,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"f350dc6b1b4242998300764faaf99d11","description":"38美丽女神节","createdate":"2017-03-26 22:55:30","viewtype":1,"mname":"合伙人会员","type":4,"tid":217,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"38美丽女神节","width":2480,"listtype":2,"useCount":8,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"ee40af39011e432ebaafb65cd10b3ed5","description":"盛夏","createdate":"2017-03-26 22:05:02","viewtype":1,"mname":"合伙人会员","type":4,"tid":212,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报39","width":4724,"listtype":2,"useCount":39,"open":0,"height":7087,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"544489e3f774460585fbb2a5e8f816db","description":"浪漫七夕","createdate":"2017-03-26 21:47:44","viewtype":1,"mname":"合伙人会员","type":4,"tid":211,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报38","width":4724,"listtype":2,"useCount":8,"open":0,"height":7087,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"6bdec19f16734a34b3e5b856b24cedd5","description":"引爆双11","createdate":"2017-03-26 21:14:47","viewtype":1,"mname":"合伙人会员","type":4,"tid":210,"sid":null,"isup":1,"price":0,"swiperOrder":6,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报37","width":2268,"listtype":2,"useCount":22,"open":0,"height":3402,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"b1cfe1314d874c90b710d353f9dbd7ed","description":"3.8女神节","createdate":"2017-03-26 17:09:01","viewtype":1,"mname":"合伙人会员","type":4,"tid":203,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报36","width":1240,"listtype":2,"useCount":23,"open":0,"height":1754,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"e06da8e6af75462f82b93dd5dec5d24a","description":"半永久全能培训班","createdate":"2017-03-26 13:03:32","viewtype":1,"mname":"合伙人会员","type":4,"tid":197,"sid":null,"isup":1,"price":0,"swiperOrder":5,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报35","width":4724,"listtype":2,"useCount":64,"open":0,"height":7087,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"025e2454abdb46f1a9883ba79d70a4ff","description":"定妆眉","createdate":"2017-03-26 11:52:35","viewtype":1,"mname":"合伙人会员","type":4,"tid":196,"sid":null,"isup":1,"price":0,"swiperOrder":4,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报34","width":4724,"listtype":2,"useCount":48,"open":0,"height":7087,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"199e89707d444d679c5fbe71338e3c2c","description":"半永久会员活动来袭","createdate":"2017-03-26 11:48:25","viewtype":1,"mname":"黄金会员","type":4,"tid":195,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"65b01885041550009983d1c7a75e2a87.png","name":"海报33","width":4724,"listtype":2,"useCount":10,"open":0,"height":7087,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"fae6dc7a1ff1405f9cadd2b329ea674a","description":"美甲店盛大开业","createdate":"2017-03-26 11:31:44","viewtype":1,"mname":"黄金会员","type":4,"tid":193,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"65b01885041550009983d1c7a75e2a87.png","name":"海报32","width":4724,"listtype":2,"useCount":34,"open":0,"height":7087,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"51b9fbdd8fcd44c6b5cce3b1d2736a23","description":"开业海报1","createdate":"2017-03-25 23:30:28","viewtype":1,"mname":"合伙人会员","type":4,"tid":188,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报31","width":2362,"listtype":2,"useCount":11,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"067ac6562590490786108ce3a913ee49","description":"会员日海报2","createdate":"2017-03-25 23:09:27","viewtype":1,"mname":"合伙人会员","type":4,"tid":187,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报30","width":2362,"listtype":2,"useCount":1,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"2989b18584bf4cc4a1b52b5f7faef9e1","description":"超级会员日","createdate":"2017-03-25 22:24:42","viewtype":1,"mname":"合伙人会员","type":4,"tid":186,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报29","width":2362,"listtype":2,"useCount":5,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"3a451b6d38434c8ebe8f384b1637822a","description":"1周年店庆","createdate":"2017-03-25 21:29:46","viewtype":1,"mname":"合伙人会员","type":4,"tid":182,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报28","width":2362,"listtype":2,"useCount":3,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"0d8ebc1b80f24880821602cd8331a4a3","description":"2周年店庆","createdate":"2017-03-25 21:09:44","viewtype":1,"mname":"黄金会员","type":4,"tid":179,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"65b01885041550009983d1c7a75e2a87.png","name":"海报27","width":2362,"listtype":2,"useCount":9,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"e75018b7ab9d4ec0b8d0f2b05029f55e","description":"中式无痕","createdate":"2017-03-25 16:41:19","viewtype":1,"mname":"合伙人会员","type":4,"tid":204,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报40","width":2480,"listtype":2,"useCount":12,"open":0,"height":3543,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"57069de6dbaf4c1fb3608f8809a95c09","description":"周年庆典","createdate":"2017-03-25 14:24:12","viewtype":1,"mname":"合伙人会员","type":4,"tid":166,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报25","width":596,"listtype":2,"useCount":20,"open":0,"height":808,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"42922bbab7f64353ac9e575fc30b74dd","description":"开业盛典","createdate":"2017-03-25 14:10:29","viewtype":1,"mname":"合伙人会员","type":4,"tid":165,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报24","width":596,"listtype":2,"useCount":7,"open":0,"height":808,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"c0facafe75b846c6a287d5ce68d9c292","description":"盛大开业","createdate":"2017-03-25 13:56:59","viewtype":1,"mname":"合伙人会员","type":4,"tid":163,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报22","width":596,"listtype":2,"useCount":5,"open":0,"height":808,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"85e9be9304b04658bcef809977af020c","description":"萌萌哒","createdate":"2017-03-25 12:11:42","viewtype":1,"mname":"合伙人会员","type":4,"tid":162,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报21","width":596,"listtype":2,"useCount":2,"open":0,"height":809,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"ed6b90dd68c4450e9884f96509eb2413","description":"甲妆有您","createdate":"2017-03-25 11:37:52","viewtype":1,"mname":"合伙人会员","type":4,"tid":160,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报20","width":597,"listtype":2,"useCount":14,"open":0,"height":808,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"0253415baf25479bb8a9fa3912359681","description":"全城大放\"价\"","createdate":"2017-03-25 10:32:14","viewtype":1,"mname":"合伙人会员","type":4,"tid":155,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报19","width":597,"listtype":2,"useCount":7,"open":0,"height":809,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"67c59d3a98e34663a2e6cb4cad2311a0","description":"双十一","createdate":"2017-03-24 23:29:54","viewtype":1,"mname":"合伙人会员","type":4,"tid":151,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报18","width":1240,"listtype":2,"useCount":4,"open":0,"height":1713,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"652abac3a49d4818bcfa32b385e97824","description":"中秋","createdate":"2017-03-24 23:10:42","viewtype":1,"mname":"黄金会员","type":4,"tid":150,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"65b01885041550009983d1c7a75e2a87.png","name":"海报17","width":1240,"listtype":2,"useCount":1,"open":0,"height":1713,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"ab1bc556ce9545f087c013d65565c3e0","description":"周年庆","createdate":"2017-03-24 22:55:14","viewtype":1,"mname":"合伙人会员","type":4,"tid":149,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报16","width":1240,"listtype":2,"useCount":5,"open":0,"height":1713,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"a91d3957782a49e7a565d03200a05340","description":"国庆海报","createdate":"2017-03-24 22:35:32","viewtype":1,"mname":"合伙人会员","type":4,"tid":148,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报15","width":1488,"listtype":2,"useCount":7,"open":0,"height":1984,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"e6bd4d8425a34853868dc62e58c0442e","description":"五一\"巨划算\"","createdate":"2017-03-24 21:23:06","viewtype":1,"mname":"黄金会员","type":4,"tid":144,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"65b01885041550009983d1c7a75e2a87.png","name":"海报13","width":2480,"listtype":2,"useCount":57,"open":0,"height":3366,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"328cd301e4014c2bb0f2718852b546cb","description":"开业大酬宾","createdate":"2017-03-24 20:20:27","viewtype":1,"mname":"黄金会员","type":4,"tid":141,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"65b01885041550009983d1c7a75e2a87.png","name":"海报12","width":1240,"listtype":2,"useCount":3,"open":0,"height":1713,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"251ad33c0d3f4bc9bea361ae42ebbed7","description":"VIP会员招募","createdate":"2017-03-24 17:06:05","viewtype":1,"mname":"合伙人会员","type":4,"tid":137,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报11","width":1240,"listtype":2,"useCount":4,"open":0,"height":1713,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"9a64394b9c124287b7b1ac219497dbb2","description":"月缘中秋","createdate":"2017-03-22 14:58:17","viewtype":1,"mname":"合伙人会员","type":4,"tid":109,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报10","width":2268,"listtype":2,"useCount":1,"open":0,"height":3402,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"83629c1577b8453891907cff643f13d0","description":"萌系小鹿妆圣诞欢乐送","createdate":"2017-03-22 14:03:07","viewtype":1,"mname":"合伙人会员","type":4,"tid":108,"sid":null,"isup":1,"price":0,"swiperOrder":3,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报9","width":2268,"listtype":2,"useCount":2,"open":0,"height":3402,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"e44f90c2c4684523b1ea98ee51fedff6","description":"海报","createdate":"2017-03-20 13:50:47","viewtype":1,"mname":"合伙人会员","type":4,"tid":31,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报7","width":750,"listtype":2,"useCount":0,"open":0,"height":1120,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"dfc849fbabb24176824e7093480b6ebd","description":"活动单页","createdate":"2017-03-20 13:50:42","viewtype":1,"mname":"合伙人会员","type":4,"tid":29,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报-4","width":596,"listtype":2,"useCount":9,"open":0,"height":808,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"e67d890bbba046c1b90f4441b9e7dd82","description":"活动单页","createdate":"2017-03-20 13:49:12","viewtype":1,"mname":"合伙人会员","type":4,"tid":28,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"折页7","width":596,"listtype":2,"useCount":4,"open":0,"height":808,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"e1512e76b8ea4ae9883a6b3078b6f611","description":"价格单","createdate":"2017-03-20 13:49:07","viewtype":1,"mname":"合伙人会员","type":4,"tid":27,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报-3","width":596,"listtype":2,"useCount":1,"open":0,"height":808,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"7898e8e6f0b6475ba589a5509abac2ab","description":"价格单","createdate":"2017-03-20 13:49:05","viewtype":1,"mname":"合伙人会员","type":4,"tid":26,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报-2","width":596,"listtype":2,"useCount":2,"open":0,"height":808,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"e4a9be2093e94a94a29302bb0517e4c0","description":"价格单","createdate":"2017-03-20 13:49:03","viewtype":1,"mname":"合伙人会员","type":4,"tid":24,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报-1","width":596,"listtype":2,"useCount":6,"open":0,"height":808,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"37a7d067f9b74a038ad31c58c750ef84","description":"活动海报","createdate":"2017-03-20 11:40:13","viewtype":1,"mname":"合伙人会员","type":4,"tid":8,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报5","width":750,"listtype":2,"useCount":13,"open":0,"height":1120,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"964a11ef9148484a9f80cdf34affbcbd","description":"活动海报","createdate":"2017-03-20 11:40:10","viewtype":1,"mname":"合伙人会员","type":4,"tid":6,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报3","width":750,"listtype":2,"useCount":2,"open":0,"height":1120,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"ede9d97bb32a45d5b909ea31e58f9107","description":"活动海报","createdate":"2017-03-20 11:40:09","viewtype":1,"mname":"合伙人会员","type":4,"tid":5,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报2","width":750,"listtype":2,"useCount":14,"open":0,"height":1120,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"59890bfe9a244609b33d18d3c48c65b2","description":"感恩回馈","createdate":"2017-03-14 21:58:48","viewtype":1,"mname":"合伙人会员","type":4,"tid":146,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报14","width":595,"listtype":2,"useCount":14,"open":0,"height":822,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"f6b368208658416e9646320f804c1753","description":"HAPPY NEW YEAR","createdate":"2017-03-13 13:57:29","viewtype":1,"mname":"合伙人会员","type":4,"tid":164,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"海报23","width":596,"listtype":2,"useCount":2,"open":0,"height":809,"order":0,"status":0}],"name":"海报","type":4},{"recommendTemplateList":[{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"26662f6059dc465cb00c3c63a3f8792d","description":"我是时尚，我是艺术","createdate":"2017-03-25 19:37:05","viewtype":2,"mname":"合伙人会员","type":5,"tid":175,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"画册2","width":1145,"listtype":1,"useCount":832,"open":0,"height":840,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"20a1c02800f44e89a9dfb523b92e1de6","description":"画册","createdate":"2017-03-23 17:00:03","viewtype":2,"mname":"合伙人会员","type":5,"tid":129,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"画册1","width":1146,"listtype":1,"useCount":624,"open":0,"height":840,"order":0,"status":0}],"name":"画册","type":5}]
     */

    private String result;
    private String msg;
    private List<RecommendTemplatesBean> recommendTemplates;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RecommendTemplatesBean> getRecommendTemplates() {
        return recommendTemplates;
    }

    public void setRecommendTemplates(List<RecommendTemplatesBean> recommendTemplates) {
        this.recommendTemplates = recommendTemplates;
    }

    public static class RecommendTemplatesBean {
        /**
         * recommendTemplateList : [{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"a6d60af7a60f4a9abe2b18273455a041","description":"HY-TX-04.14.10","createdate":"2017-04-24 13:56:52","viewtype":1,"mname":"合伙人会员","type":3,"tid":337,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.10","width":700,"listtype":2,"useCount":91,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"6d49fc5ba31a4000beb5a89a7679cb12","description":"HY-TX-04.14.09","createdate":"2017-04-24 13:46:27","viewtype":1,"mname":"合伙人会员","type":3,"tid":336,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.09","width":700,"listtype":2,"useCount":44,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"a84667f2319b409881a69de2fed4c1bb","description":"HY-TX-04.14.08","createdate":"2017-04-24 11:38:27","viewtype":1,"mname":"合伙人会员","type":3,"tid":335,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.08","width":700,"listtype":2,"useCount":71,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"941f8f0182d048c8b14328aca590bec5","description":"HY-TX-04.14.07","createdate":"2017-04-24 11:29:37","viewtype":1,"mname":"合伙人会员","type":3,"tid":334,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.07","width":2918,"listtype":2,"useCount":32,"open":0,"height":2918,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"eaa4425dadb64745bdb814195b77c8d6","description":"HY-TX-04.14.06","createdate":"2017-04-24 11:15:04","viewtype":1,"mname":"合伙人会员","type":3,"tid":333,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.06","width":2918,"listtype":2,"useCount":14,"open":0,"height":2918,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"24f768fad7f94983b599fa28449762f9","description":"HY-TX-04.14.04","createdate":"2017-04-24 10:20:13","viewtype":1,"mname":"合伙人会员","type":3,"tid":331,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.04","width":2918,"listtype":2,"useCount":9,"open":0,"height":2917,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"68b9c1173a334d8fa2ad29cb8c1e8fec","description":"HY-TX-04.14.03","createdate":"2017-04-24 09:59:37","viewtype":1,"mname":"合伙人会员","type":3,"tid":329,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"HY-TX-04.14.03","width":2918,"listtype":2,"useCount":15,"open":0,"height":2917,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"2bc0f5f04fa0409686632d61f1537144","description":"YY-TX-042107","createdate":"2017-04-21 16:27:54","viewtype":1,"mname":"合伙人会员","type":3,"tid":317,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"YY-TX-042107","width":2917,"listtype":2,"useCount":9,"open":0,"height":2917,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"11b22b7b978b44dc81507b94e2f37c62","description":"曼文","createdate":"2017-04-01 17:15:34","viewtype":1,"mname":"合伙人会员","type":3,"tid":139,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像18","width":700,"listtype":2,"useCount":193,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"a35649c8711b4998a9ea96a1fa918968","description":"熙雯","createdate":"2017-03-31 17:22:41","viewtype":1,"mname":"合伙人会员","type":3,"tid":158,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像27","width":700,"listtype":2,"useCount":288,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"49e91c5f72db4a9c86e6e26f85d3d220","description":"高智旋","createdate":"2017-03-31 11:29:41","viewtype":1,"mname":"合伙人会员","type":3,"tid":159,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像28","width":700,"listtype":2,"useCount":17,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"81babc4e6b3c40cc9f51650a7cf8d232","description":"笑笑","createdate":"2017-03-30 19:34:07","viewtype":1,"mname":"合伙人会员","type":3,"tid":206,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像35","width":1181,"listtype":2,"useCount":54,"open":0,"height":1181,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"1a518d0c81cf494cbaf0cbbd1e5016dc","description":"美琳","createdate":"2017-03-25 11:41:23","viewtype":1,"mname":"合伙人会员","type":3,"tid":161,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像29","width":701,"listtype":2,"useCount":33,"open":0,"height":701,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"08e49973d7e3437d92dd132a97d5d8d9","description":"高菲菲","createdate":"2017-03-24 21:20:47","viewtype":1,"mname":"合伙人会员","type":3,"tid":143,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像21","width":700,"listtype":2,"useCount":38,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"3856c8d68da84423bfd5c89c7b25b44b","description":"凌萱","createdate":"2017-03-23 17:10:11","viewtype":1,"mname":"合伙人会员","type":3,"tid":147,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像22","width":700,"listtype":2,"useCount":108,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"994c4d69b96849318688ab82a40a12a6","description":"依/娜","createdate":"2017-03-23 10:50:55","viewtype":1,"mname":"黄金会员","type":3,"tid":120,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"65b01885041550009983d1c7a75e2a87.png","name":"头像14","width":701,"listtype":2,"useCount":16,"open":0,"height":701,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"60cb0ef7c2da416c86d76c91217f1d4d","description":"段萱","createdate":"2017-03-23 10:15:46","viewtype":1,"mname":"合伙人会员","type":3,"tid":119,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像13","width":700,"listtype":2,"useCount":22,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"fefc13a83e344364babe24b65aa827a9","description":"美梦成真","createdate":"2017-03-23 00:03:46","viewtype":1,"mname":"合伙人会员","type":3,"tid":220,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"美梦成真","width":2953,"listtype":2,"useCount":40,"open":0,"height":2953,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"1cd9967af6415b05938f8987360d2738.png","description":"传奇一生","createdate":"2017-03-21 21:05:39","viewtype":1,"mname":"黄金会员","type":3,"tid":35,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"65b01885041550009983d1c7a75e2a87.png","name":"头像10","width":700,"listtype":2,"useCount":39,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"a9d7ee9b378f4751be4637209d915647","description":"个人头像","createdate":"2017-03-20 13:44:28","viewtype":1,"mname":"合伙人会员","type":3,"tid":22,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像9","width":700,"listtype":2,"useCount":36,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"de7718cf3c4948e6a06543dc1e339831","description":"个人头像","createdate":"2017-03-20 13:44:27","viewtype":1,"mname":"合伙人会员","type":3,"tid":21,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像8","width":700,"listtype":2,"useCount":13,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"de0195296644411ebe22853fe74a1757","description":"个人头像","createdate":"2017-03-20 13:44:24","viewtype":1,"mname":"合伙人会员","type":3,"tid":19,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像6","width":700,"listtype":2,"useCount":20,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"2d262121515b46f68f9066b4824c1a84","description":"个人头像","createdate":"2017-03-20 13:44:23","viewtype":1,"mname":"合伙人会员","type":3,"tid":18,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像5","width":700,"listtype":2,"useCount":20,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"8f36e0aa0d6444af9002b7dce2e9832d","description":"个人头像","createdate":"2017-03-20 13:44:22","viewtype":1,"mname":"合伙人会员","type":3,"tid":17,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"头像4","width":700,"listtype":2,"useCount":12,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"e667bfe4eac1456cb0bafd217ecedc74","description":"个人头像","createdate":"2017-03-20 13:44:19","viewtype":1,"mname":"黄金会员","type":3,"tid":15,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"65b01885041550009983d1c7a75e2a87.png","name":"头像2","width":700,"listtype":2,"useCount":18,"open":0,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"f2ec87de1fa14dc98474169bae1cdcc0","description":"个人头像","createdate":"2017-03-20 13:44:18","viewtype":1,"mname":"免费","type":3,"tid":14,"sid":null,"isup":1,"price":null,"swiperOrder":0,"micon":"","name":"头像1","width":700,"listtype":2,"useCount":67,"open":1,"height":700,"order":0,"status":0},{"owner":0,"isbuy":0,"buyCount":0,"browseCount":0,"icon":"eddeaefb0d8d4ca6a42668b90eb3b97b","description":"财富论坛2","createdate":"2017-03-10 23:29:48","viewtype":1,"mname":"合伙人会员","type":3,"tid":218,"sid":null,"isup":1,"price":0,"swiperOrder":0,"micon":"a72485dce2df5a87af4d1ec9edeab110.png","name":"财富论坛2","width":1181,"listtype":2,"useCount":62,"open":0,"height":1181,"order":0,"status":0}]
         * name : 头像
         * type : 3
         */

        private String name;
        private int type;
        private List<RecommendTemplateListBean> recommendTemplateList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<RecommendTemplateListBean> getRecommendTemplateList() {
            return recommendTemplateList;
        }

        public void setRecommendTemplateList(List<RecommendTemplateListBean> recommendTemplateList) {
            this.recommendTemplateList = recommendTemplateList;
        }

        public static class RecommendTemplateListBean {
            /**
             * owner : 0
             * isbuy : 0
             * buyCount : 0
             * browseCount : 0
             * icon : a6d60af7a60f4a9abe2b18273455a041
             * description : HY-TX-04.14.10
             * createdate : 2017-04-24 13:56:52
             * viewtype : 1
             * mname : 合伙人会员
             * type : 3
             * tid : 337
             * sid : null
             * isup : 1
             * price : 0
             * swiperOrder : 0
             * micon : a72485dce2df5a87af4d1ec9edeab110.png
             * name : HY-TX-04.14.10
             * width : 700
             * listtype : 2
             * useCount : 91
             * open : 0
             * height : 700
             * order : 0
             * status : 0
             */

            private int owner;
            private int isbuy;
            private int buyCount;
            private int browseCount;
            private String icon;
            private String description;
            private String createdate;
            private int viewtype;
            private String mname;
            private int type;
            private int tid;
            private Object sid;
            private int isup;
            private int price;
            private int swiperOrder;
            private String micon;
            private String name;
            private int width;
            private int listtype;
            private int useCount;
            private int open;
            private int height;
            private int order;
            private int status;

            public int getOwner() {
                return owner;
            }

            public void setOwner(int owner) {
                this.owner = owner;
            }

            public int getIsbuy() {
                return isbuy;
            }

            public void setIsbuy(int isbuy) {
                this.isbuy = isbuy;
            }

            public int getBuyCount() {
                return buyCount;
            }

            public void setBuyCount(int buyCount) {
                this.buyCount = buyCount;
            }

            public int getBrowseCount() {
                return browseCount;
            }

            public void setBrowseCount(int browseCount) {
                this.browseCount = browseCount;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public int getViewtype() {
                return viewtype;
            }

            public void setViewtype(int viewtype) {
                this.viewtype = viewtype;
            }

            public String getMname() {
                return mname;
            }

            public void setMname(String mname) {
                this.mname = mname;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getTid() {
                return tid;
            }

            public void setTid(int tid) {
                this.tid = tid;
            }

            public Object getSid() {
                return sid;
            }

            public void setSid(Object sid) {
                this.sid = sid;
            }

            public int getIsup() {
                return isup;
            }

            public void setIsup(int isup) {
                this.isup = isup;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getSwiperOrder() {
                return swiperOrder;
            }

            public void setSwiperOrder(int swiperOrder) {
                this.swiperOrder = swiperOrder;
            }

            public String getMicon() {
                return micon;
            }

            public void setMicon(String micon) {
                this.micon = micon;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getListtype() {
                return listtype;
            }

            public void setListtype(int listtype) {
                this.listtype = listtype;
            }

            public int getUseCount() {
                return useCount;
            }

            public void setUseCount(int useCount) {
                this.useCount = useCount;
            }

            public int getOpen() {
                return open;
            }

            public void setOpen(int open) {
                this.open = open;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
