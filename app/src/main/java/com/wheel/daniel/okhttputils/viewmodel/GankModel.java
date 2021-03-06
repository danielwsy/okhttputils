package com.wheel.daniel.okhttputils.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import com.wheel.daniel.okhttputils.bean.GankContentBean;
import com.wheel.daniel.okhttputils.bean.GankContentMgmt;
import com.wheel.daniel.okhttputils.utils.ThreadMgrUtils;


/**
 * @author danielwang
 * @Description: 获取图片信息，装载到LivaData中
 * @date 2018/9/11 14:07
 * ViewModel 的存在是依赖 Activity 或者 Fragment的，
 * 不管你在什么地方获取ViewModel ，只要你用的是相同的Activity或者 Fragment，
 * 那么获取到的ViewModel将是同一个 (前提是key值是一样的)，所以ViewModel 也具有数据共享的作用！
 */
public class GankModel extends ViewModel {

    //将需要监听的实体类放入
    private MutableLiveData<GankContentBean> liveData = new MutableLiveData<>();


    public void setValue(GankContentBean item) {
        //在UI线程中调用
//        liveData.setValue(item);
        //在子线程中调用
        liveData.postValue(item);
    }

    public LiveData<GankContentBean> getValue() {
        return liveData;
    }


    public void loadData() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final GankContentBean bean = GankContentMgmt.getMovieContents();
                if (bean != null) {
                    setValue(bean);
                }
            }
        };
        ThreadMgrUtils.executeNetworkTask(runnable);
    }
}
