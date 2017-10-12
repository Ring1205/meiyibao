package com.wmcd.myb.factory;
import com.wmcd.myb.base.BaseFragment;
import com.wmcd.myb.fragment.catrgoryitem.AlbumBelowFragment;
import com.wmcd.myb.fragment.catrgoryitem.CardBelowFragment;
import com.wmcd.myb.fragment.catrgoryitem.CardFragment;
import com.wmcd.myb.fragment.catrgoryitem.ChoiceFragment;
import com.wmcd.myb.fragment.catrgoryitem.CouponFragment;
import com.wmcd.myb.fragment.catrgoryitem.FoldFragment;
import com.wmcd.myb.fragment.catrgoryitem.HeadFragment;
import com.wmcd.myb.fragment.catrgoryitem.PostFragment;
import com.wmcd.myb.fragment.catrgoryitem.PublicityFragment;
import com.wmcd.myb.fragment.catrgoryitem.RollFragment;
import com.wmcd.myb.fragment.catrgoryitem.TableFragment;
import com.wmcd.myb.fragment.catrgoryitem.VIFragment;
import com.wmcd.myb.fragment.catrgoryitem.VipBelowFragment;
import com.wmcd.myb.fragment.catrgoryitem.VipFragment;

import java.util.HashMap;
import java.util.Map;

/**
* 根据ttid创建fragment
* */
public class FragmentFactory {
    public static final int CHOICE = -1;//精选
    public static final int PUBLICITY = -2;//套系宣传
    //public static final int VI = 2;//VI
    public static final int HEAD = 3;//头像
    public static final int POST = 4;//海报
    public static final int CARD = 11;//线上名片

    public static final int COUPON = 12;//优惠券
    //public static final int VIP = 6;//会员卡

    public static final int CARD_BELOW = 7;//线下名片
    public static final int FOLD = 8;//折页
    public static final int ALBUM =5;//画册
    public static final int VIP_BELOW = 13;//线下会员卡
    public static final int ROLL_BANNER = 9;//易拉宝
    public static final int SUPER_TABLE = 10;//超级表格

    public static Map<Integer, BaseFragment> mCacheFragments = new HashMap<>();

    public static BaseFragment createFragment(int position) {
        //定义Fragment对象
        BaseFragment fragment = null;

        //优先缓存集合中取出来
        if (mCacheFragments.containsKey(position)) {
            fragment = mCacheFragments.get(position);
            return fragment;
        }
        switch (position) {
            case CHOICE:
                fragment = new ChoiceFragment();
                break;
            case PUBLICITY:
                fragment = new PublicityFragment();
                break;
           /* case VI:
                fragment = new VIFragment();
                break;*/
            case HEAD:
                fragment = new HeadFragment();
                break;
            case POST:
                fragment = new PostFragment();
                break;
            case CARD:
                fragment = new CardFragment();
                break;
            /*case VIP:
                fragment = new VipFragment();
                break;*/
            case COUPON:
                fragment = new CouponFragment();
                break;
            case CARD_BELOW:
                fragment = new CardBelowFragment();
                break;
            case FOLD:
                fragment = new FoldFragment();
                break;
            case ALBUM:
                fragment = new AlbumBelowFragment();
                break;
            case VIP_BELOW:
                fragment = new VipBelowFragment();
                break;
            case ROLL_BANNER:
                fragment = new RollFragment();
                break;
            case SUPER_TABLE:
                fragment = new TableFragment();
                break;

            default:
                break;
        }
        //保存Fragment到集合中
        mCacheFragments.put(position, fragment);

        return fragment;
    }
}
