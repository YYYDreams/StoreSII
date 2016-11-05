package com.example.administrator.store3.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/6/18.
 * 自定义圆角空边矩形
 */
public class CustomGraph extends ImageView {
    Paint mPaint; //画笔,包含了画几何图形、文本等的样式和颜色信息
    /** 上下文对象 */
    private Context mContext;
    /** 进度条的值 */
    private int mProcessValue;
    public CustomGraph(Context context) {
        super(context);
    }

    public CustomGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化成员变量 Context
        mContext = context;
        // 创建画笔, 并设置画笔属性
        mPaint = new Paint();
        // 消除绘制时产生的锯齿
        mPaint.setAntiAlias(true);
        // 绘制空心圆形需要设置该样式
        mPaint.setStyle(Paint.Style.STROKE);

    }

    public CustomGraph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomGraph(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置画笔颜色
        mPaint.setColor(Color.WHITE);
        //设置画笔宽度
        mPaint.setStrokeWidth(2);
        //设置圆弧显示范围
        int random = 50;
        //设置直线调整距离
        int rap=5;
        //创建圆弧对象
        RectF rectf = new RectF(0, 0, random, random);
        //绘制圆弧 参数介绍 : 圆弧, 开始度数, 累加度数, 是否闭合圆弧, 画笔
        canvas.drawArc(rectf, 180, 90, false, mPaint);
        //绘制直线
        canvas.drawLine(random/2-rap, 1, random, 1, mPaint);
        canvas.drawLine(1,random/2-rap,1,random,mPaint);
        RectF rects = new RectF(getWidth()-random, 0, getWidth(), random);
        canvas.drawArc(rects, 270, 90, false, mPaint);
        canvas.drawLine(getWidth() - random, 1, getWidth() - random/2+rap, 1, mPaint);
        canvas.drawLine(getWidth()-1,random/2-rap,getWidth()-1,random,mPaint);
        RectF rectd = new RectF(0, getHeight()-random, random, getHeight());
        canvas.drawArc(rectd, 90, 90, false, mPaint);
        canvas.drawLine(1,getHeight()-random,1,getHeight()-random/2+rap,mPaint);
        canvas.drawLine(random/2-rap,getHeight()-1,random,getHeight()-1,mPaint);
        RectF recte = new RectF(getWidth()-random, getHeight()-random, getWidth(), getHeight());
        canvas.drawArc(recte, 0, 90, false, mPaint);
        canvas.drawLine(getWidth()-random, getHeight()-1, getWidth()-random/2+rap, getHeight()-1, mPaint);
        canvas.drawLine(getWidth()-1,getHeight()-random,getWidth()-1,getHeight()-random/2+rap,mPaint);
    }
    /**
     * 将手机的 设备独立像素 转为 像素值
     *
     *      公式 : px / dip = dpi / 160
     *             px = dip * dpi / 160;
     * @param context
     *              上下文对象
     * @param dpValue
     *              设备独立像素值
     * @return
     *              转化后的 像素值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 将手机的 像素值 转为 设备独立像素
     *      公式 : px/dip = dpi/160
     *             dip = px * 160 / dpi
     *             dpi (dot per inch) : 每英寸像素数 归一化的值 120 160 240 320 480;
     *             density : 每英寸的像素数, 精准的像素数, 可以用来计算准确的值
     *             从 DisplayMetics 中获取的
     * @param context
     *              上下文对象
     * @param pxValue
     *              像素值
     * @return
     *              转化后的 设备独立像素值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 获取组件宽度
     *
     * MeasureSpec : 该 int 类型有 32 位, 前两位是状态位, 后面 30 位是大小值;
     *      常用方法 :
     *      -- MeasureSpec.getMode(int) : 获取模式
     *      -- MeasureSpec.getSize(int) : 获取大小
     *      -- MeasureSpec.makeMeasureSpec(int size, int mode) : 创建一个 MeasureSpec;
     *      -- MeasureSpec.toString(int) : 模式 + 大小 字符串
     *
     *      模式介绍 : 注意下面的数字是二进制的
     *      -- 00 : MeasureSpec.UNSPECIFIED, 未指定模式;
     *      -- 01 : MeasureSpec.EXACTLY, 精准模式;
     *      -- 11 : MeasureSpec.AT_MOST, 最大模式;
     *
     *      注意 : 这个 MeasureSpec 模式是在 onMeasure 方法中自动生成的, 一般不用去创建这个对象
     *
     * @param measureSpec
     *              MeasureSpec 数值
     * @return
     *              组件的宽度
     */
    private int measure(int measureSpec) {
        //返回的结果, 即组件宽度
        int result = 0;
        //获取组件的宽度模式
        int mode = MeasureSpec.getMode(measureSpec);
        //获取组件的宽度大小 单位px
        int size = MeasureSpec.getSize(measureSpec);

        if(mode == MeasureSpec.EXACTLY){//精准模式
            result = size;
        }else{//未定义模式 或者 最大模式
            //注意 200 是默认大小, 在 warp_content 时使用这个值, 如果组件中定义了大小, 就不使用该值
            result = dip2px(mContext, 200);
            if(mode == MeasureSpec.AT_MOST){//最大模式
                //最大模式下获取一个稍小的值
                result = Math.min(result, size);
            }
        }

        return result;
    }
/**
 * 在 onMeasure() 方法中调用该方法, 就设置了组件的宽 和 高, 然后在其它位置调用 getWidth() 和 getHeight() 方法时,
 * 获取的就是 该方法设置的值;
* */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
     /*
     * setMeasuredDimension 方法 : 该方法决定当前的 View 的大小
     * 根据 View 在布局中的显示, 动态获取 View 的宽高
     *
     * 当布局组件 warp_content 时 :
     * 从 MeasureSpec 获取的宽度 : 492 高度 836 ,
     * 默认 宽高 都是 120dip转化完毕后 180px
     *
     * 当将布局组件 的宽高设置为 240 dp :
     * 宽度 和 高度 MeasureSpec 获取的都是 360, 此时 MeasureSpec 属于精准模式
     *
     */
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
    }

}
