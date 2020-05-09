package cc.com.aoptest;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author: xuehao@duia.com create time: 2018/4/20 10:56
 * tag: class//
 * description:
 */

//创建一个操作类：注意，这个类必须要加上@Aspect注解。
@Aspect
public class BehaviorAscept {
    public static String TAG="TAG";
    //    再声明一个方法作为切点，这个方法需要加上注解：
//    @pointcut(execution(@全类名 * *(. .)))
//    后面的两个表示*匹配所有的方法名，两个.表示匹配所有的方法参数.
    @Pointcut("execution(@cc.com.myaoptest0.CheckNet * *(. .))")
    public void checkNetBehavior() {
        //构造方法可以为空
    }

    //    申明一个表示切面的方法：checkBehavior
    @Around("checkNetBehavior()")
    public Object dealPoint(ProceedingJoinPoint point) throws Throwable {
        Log.e(TAG, "dealPoint: " );
        //获取checkNet注解
        MethodSignature mSignature = (MethodSignature) point.getSignature();
        CheckNet mCheckNet = mSignature.getMethod().getAnnotation(CheckNet.class);
        if (mCheckNet != null) {
            String mValue = mCheckNet.value();
            Log.e(TAG,"获取到注解的值 dealPoint: "+mValue);
            //获取context
            Object mThis = point.getThis();
            Context mContext = getContext(mThis);
            //判断有没有网络
            if (!hasNetWorkConection(mContext)) {
                //没有网络
                Toast.makeText(mContext, "没有网络", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        return point.proceed();
    }

    private Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof View) {
            View mView = (View) object;
            return mView.getContext();
        }
        return null;
    }

    /**
     * 判断是否具有网络连接
     */
    private static final boolean hasNetWorkConection(Context mContext) {
        //获取连接活动管理器
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) mContext
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取连接的网络信息
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isAvailable());
    }

}