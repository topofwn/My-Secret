package com.example.kos.mysecrect.utils.customui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import com.example.kos.mysecrect.R;


public class LCButton extends AppCompatButton implements View.OnTouchListener {

    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_TOP = 1;
    public static final int ALIGN_RIGHT = 2;
    public static final int ALIGN_BOTTOM = 3;

    private int btnWidth;
    private int btnHeight;

    private boolean isHaveDrawable = false;

    //Custom values
    private boolean isShadowEnabled = false;
    private int mButtonColor;
    private int mButtonDisableColor;
    private int mButtonPressedColor;
    private int mShadowColor;
    private int mBorderColor;
    private int mShadowHeight;
    private int mCornerRadius;
    private int mBorderWidth;
    private int mTextButtonColor;
    private int mTextButtonPressedColor;
    //Native values
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mPaddingBottom;
    //Background drawable
    private Drawable pressedDrawable;
    private Drawable unpressedDrawable;

    private Drawable tempDrawable;
    private int tempAlign = 0;

    boolean isSelected = false;
    boolean isShadowColorDefined = false;


    public LCButton(Context context) {
        super(context);
        init(getTypeface());
        setOnTouchListener(this);
    }

    public LCButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(getTypeface());
        parseAttrs(context, attrs);
        setOnTouchListener(this);
    }

    public LCButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(getTypeface());
        parseAttrs(context, attrs);
        setOnTouchListener(this);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        btnWidth = MeasureSpec.getSize(widthMeasureSpec);
        btnHeight = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isHaveDrawable) {
            setDrawable(tempDrawable, tempAlign);
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //Update background color
        refresh();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                updateBackground(pressedDrawable);
                setTextColor(mTextButtonPressedColor);
                this.setPadding(mPaddingLeft, mPaddingTop + mShadowHeight, mPaddingRight, mPaddingBottom);
                break;
            case MotionEvent.ACTION_MOVE:
                Rect r = new Rect();
                view.getLocalVisibleRect(r);
                if (!r.contains((int) motionEvent.getX(), (int) motionEvent.getY() + 3 * mShadowHeight) &&
                        !r.contains((int) motionEvent.getX(), (int) motionEvent.getY() - 3 * mShadowHeight)) {
                    updateBackground(unpressedDrawable);
                    this.setPadding(mPaddingLeft, mPaddingTop + mShadowHeight, mPaddingRight, mPaddingBottom + mShadowHeight);
                }
                break;
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                updateBackground(unpressedDrawable);
                setTextColor(mTextButtonColor);
                this.setPadding(mPaddingLeft, mPaddingTop + mShadowHeight, mPaddingRight, mPaddingBottom + mShadowHeight);
                break;
        }
        return false;
    }

    private void init(Typeface tf) {
        //Init default values
        isShadowEnabled = false;
        Resources resources = getResources();
        if (resources == null) return;
        mTextButtonColor = resources.getColor(android.R.color.black);
        mTextButtonPressedColor = resources.getColor(android.R.color.black);
        setTextColor(mTextButtonColor);
        mButtonColor = resources.getColor(R.color.colorPrimary);
        mButtonDisableColor = resources.getColor(R.color.colorPrimaryDark);
        mButtonPressedColor = resources.getColor(R.color.colorPrimaryDark);
        mShadowColor = resources.getColor(R.color.colorPrimaryDark);
        mBorderColor = resources.getColor(R.color.colorAccent);
        mShadowHeight = resources.getDimensionPixelSize(R.dimen.default_custom_button_shadow_height);
        mCornerRadius = resources.getDimensionPixelSize(R.dimen.default_custom_button_conner_radius);
        mBorderWidth = resources.getDimensionPixelSize(R.dimen.default_custom_button_border_width);
        isSelected = false;
        setGravity(Gravity.CENTER);
    }

    private void parseAttrs(Context context, AttributeSet attrs) {

        //Get paddingLeft, paddingRight
        int[] attrsArray = new int[]{
                android.R.attr.paddingLeft,  // 0
                android.R.attr.paddingRight, // 1
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
        if (ta == null) return;
        mPaddingLeft = ta.getDimensionPixelSize(0, getResources().getDimensionPixelSize(R.dimen.default_custom_button_padding_start));
        mPaddingRight = ta.getDimensionPixelSize(1, getResources().getDimensionPixelSize(R.dimen.default_custom_button_padding_end));
        ta.recycle();

        //Get paddingTop, paddingBottom
        int[] attrsArray2 = new int[]{
                android.R.attr.paddingTop,   // 0
                android.R.attr.paddingBottom,// 1
        };
        TypedArray ta1 = context.obtainStyledAttributes(attrs, attrsArray2);
        if (ta1 == null) return;
        mPaddingTop = ta1.getDimensionPixelSize(0, getResources().getDimensionPixelSize(R.dimen.default_custom_button_padding_top));
        mPaddingBottom = ta1.getDimensionPixelSize(1, getResources().getDimensionPixelSize(R.dimen.default_custom_button_padding_bottom));

        ta1.recycle();

        //Load from custom attributes
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCoffeeButton);
        if (typedArray == null) return;
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.MyCoffeeButton_shadowEnabled:
                    isShadowEnabled = typedArray.getBoolean(attr, false); //Default is true
                    break;
                case R.styleable.MyCoffeeButton_buttonColor:
                    mButtonColor = typedArray.getColor(attr, getResources().getColor(R.color.colorPrimary));
                    break;
                case R.styleable.MyCoffeeButton_buttonDisableColor:
                    mButtonDisableColor = typedArray.getColor(attr, getResources().getColor(R.color.colorPrimaryDark));
                    break;
                case R.styleable.MyCoffeeButton_buttonPressedColor:
                    mButtonPressedColor = typedArray.getColor(attr, getResources().getColor(R.color.colorPrimaryDark));
                    break;
                case R.styleable.MyCoffeeButton_borderColor:
                    mBorderColor = typedArray.getColor(attr, getResources().getColor(R.color.colorAccent));
                    break;
                case R.styleable.MyCoffeeButton_shadowColor:
                    mShadowColor = typedArray.getColor(attr, getResources().getColor(R.color.colorPrimaryDark));
                    isShadowColorDefined = true;
                    break;
                case R.styleable.MyCoffeeButton_shadowHeight:
                    mShadowHeight = typedArray.getDimensionPixelSize(attr, getResources().getDimensionPixelSize(R.dimen.default_custom_button_shadow_height));
                    break;
                case R.styleable.MyCoffeeButton_cornerRadius:
                    mCornerRadius = typedArray.getDimensionPixelSize(attr, getResources().getDimensionPixelSize(R.dimen.default_custom_button_conner_radius));
                    break;
                case R.styleable.MyCoffeeButton_borderWidth:
                    mBorderWidth = typedArray.getDimensionPixelSize(attr, getResources().getDimensionPixelSize(R.dimen.default_custom_button_border_width));
                    break;
                case R.styleable.MyCoffeeButton_textColor:
                    mTextButtonColor = typedArray.getColor(attr, getResources().getColor(android.R.color.black));
                    textColorTemp = mTextButtonColor;
                    setTextColor(mTextButtonColor);
                    break;
                case R.styleable.MyCoffeeButton_textPressedColor:
                    mTextButtonPressedColor = typedArray.getColor(attr, getResources().getColor(android.R.color.black));
                    break;
                case R.styleable.MyCoffeeButton_buttonDrawable:
                    tempDrawable = typedArray.getDrawable(attr);
                    isHaveDrawable = true;
                    break;
                case R.styleable.MyCoffeeButton_buttonDrawableAlign:
                    tempAlign = typedArray.getInt(attr, ALIGN_LEFT);
                    break;
                case R.styleable.MyCoffeeButton_paddingLeft:
                    mPaddingLeft = typedArray.getDimensionPixelSize(attr, getResources().getDimensionPixelSize(R.dimen.default_custom_button_padding_start));
                    break;
                case R.styleable.MyCoffeeButton_paddingRight:
                    mPaddingRight = typedArray.getDimensionPixelSize(attr, getResources().getDimensionPixelSize(R.dimen.default_custom_button_padding_end));
                    break;
                case R.styleable.MyCoffeeButton_paddingTop:
                    mPaddingTop = typedArray.getDimensionPixelSize(attr, getResources().getDimensionPixelSize(R.dimen.default_custom_button_padding_top));
                    break;
                case R.styleable.MyCoffeeButton_paddingBottom:
                    mPaddingBottom = typedArray.getDimensionPixelSize(attr, getResources().getDimensionPixelSize(R.dimen.default_custom_button_padding_bottom));
                    break;
                case R.styleable.MyCoffeeButton_isSetSelected:
                    isSelected = typedArray.getBoolean(attr, false);
                    break;
                default:
                    break;
            }
        }
        typedArray.recycle();


    }

    private int textColorTemp;

    public void refresh() {
        int alpha = Color.alpha(mButtonColor);
        float[] hsv = new float[3];
        Color.colorToHSV(mButtonColor, hsv);
        hsv[2] *= 0.8f; // value component
        //if shadow color was not defined, generate shadow color = 80% brightness
        if (!isShadowColorDefined) {
            mShadowColor = Color.HSVToColor(alpha, hsv);
        }
        /*if (isSelected) {
            int temp = mTextButtonColor;
            mTextButtonColor = mTextButtonPressedColor;
            mTextButtonPressedColor = temp;
            temp = mButtonColor;
            mButtonColor = mButtonPressedColor;
            mButtonPressedColor = temp;
            setTextColor(mTextButtonColor);
        } else {

        }*/
        //Create pressed background and unpressed background drawables
        if (isSelected) {
            textColorTemp = mTextButtonColor;
            setTextColor(mTextButtonPressedColor);
        } else {
            textColorTemp = mTextButtonColor;
            setTextColor(textColorTemp);
        }
        if (this.isEnabled()) {
            if (isShadowEnabled) {
                pressedDrawable = createDrawable(mCornerRadius, isSelected ? mButtonColor : mButtonPressedColor, isSelected ? mButtonPressedColor : mButtonColor, mBorderColor, mBorderWidth);
                unpressedDrawable = createDrawable(mCornerRadius, isSelected ? mButtonPressedColor : mButtonColor, mShadowColor, mBorderColor, mBorderWidth);
            } else {
                mShadowHeight = 0;
                pressedDrawable = createDrawable(mCornerRadius, isSelected ? mButtonColor : mButtonPressedColor, Color.TRANSPARENT, mBorderColor, mBorderWidth);
                unpressedDrawable = createDrawable(mCornerRadius, isSelected ? mButtonPressedColor : mButtonColor, Color.TRANSPARENT, mBorderColor, mBorderWidth);
            }
        } else {
            if (isShadowEnabled) {
                pressedDrawable = createDrawable(mCornerRadius, mButtonDisableColor, mButtonColor, mBorderColor, mBorderWidth);
                unpressedDrawable = createDrawable(mCornerRadius, mButtonDisableColor, mShadowColor, mBorderColor, mBorderWidth);
            } else {
                mShadowHeight = 0;
                pressedDrawable = createDrawable(mCornerRadius, mButtonDisableColor, Color.TRANSPARENT, mBorderColor, mBorderWidth);
                unpressedDrawable = createDrawable(mCornerRadius, mButtonDisableColor, Color.TRANSPARENT, mBorderColor, mBorderWidth);
            }
        }
        updateBackground(unpressedDrawable);
        //Set padding
        this.setPadding(mPaddingLeft, mPaddingTop + mShadowHeight, mPaddingRight, mPaddingBottom + mShadowHeight);
    }

    private void updateBackground(Drawable background) {
        if (background == null) return;
        //Set button background
        this.setBackground(background);
    }

    private LayerDrawable createDrawable(int radius, int topColor, int bottomColor, int borderColor, int borderWidth) {

        float[] outerRadius = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};

        //Top
        RoundRectShape topRoundRect = new RoundRectShape(outerRadius, null, null);
        ShapeDrawable topShapeDrawable = new ShapeDrawable(topRoundRect);
        topShapeDrawable.getPaint().setColor(topColor);

        //Top Border
        GradientDrawable borderLineDrawable = new GradientDrawable();
        borderLineDrawable.setShape(GradientDrawable.RECTANGLE);
        borderLineDrawable.setStroke(borderWidth, borderColor);
        borderLineDrawable.setCornerRadii(outerRadius);
        borderLineDrawable.setColor(topColor);

        //Bottom
        RoundRectShape roundRectShape = new RoundRectShape(outerRadius, null, null);
        ShapeDrawable bottomShapeDrawable = new ShapeDrawable(roundRectShape);
        bottomShapeDrawable.getPaint().setColor(bottomColor);

        LayerDrawable layerDrawable;
        //Create array
        Drawable[] drawArrayNoBorder = {bottomShapeDrawable, topShapeDrawable};
        Drawable[] drawArrayHasBorder = {bottomShapeDrawable, borderLineDrawable};
        if (borderWidth == 0) {
            layerDrawable = new LayerDrawable(drawArrayNoBorder);
        } else {
            layerDrawable = new LayerDrawable(drawArrayHasBorder);
        }

        layerDrawable.setLayerInset(1, 0, 0, 0, mShadowHeight);  /*index, left, top, right, bottom*/
        //Set shadow height
        if (isShadowEnabled && topColor != Color.TRANSPARENT) {
            //unpressed drawable
            layerDrawable.setLayerInset(0, 0, 0, 0, 0);  /*index, left, top, right, bottom*/
        } else {
            //pressed drawable
            layerDrawable.setLayerInset(0, 0, mShadowHeight, 0, 0);/*index, left, top, right, bottom*/
            layerDrawable.setLayerInset(1, 0, mShadowHeight, 0, 0);  /*index, left, top, right, bottom*/
        }
        return layerDrawable;
    }


    // Have to provide fixed height when using in listview
    public void setDrawable(int res, int align) {
        Drawable drawable = getResources().getDrawable(res);
        setDrawable(drawable, align);
    }

    // Have to provide fixed height when using in listview
    public void setDrawable(Drawable drawable, int align) {
        isHaveDrawable = true;
        tempDrawable = drawable;
        tempAlign = align;
        if (btnHeight == 0 || btnWidth == 0) {
            return;
        }
        double ratioWH = (double) drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight();
        setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.default_custom_button_drawable_padding));
        switch (tempAlign) {
            case ALIGN_LEFT:
                double heightScaleDrawable = btnHeight * 0.7;
                drawable.setBounds(0, 0, (int) (heightScaleDrawable / ratioWH),
                        (int) heightScaleDrawable);
                setCompoundDrawables(drawable, null, null, null);
                break;
            case ALIGN_TOP:
//                 heightScaleDrawable  =  btnHeight * 0.5;
//                drawable.setBounds(0, 0, (int)(heightScaleDrawable / ratioWH) ,
//                        (int) heightScaleDrawable);
//                setCompoundDrawables(null , drawable, null  , null);
                double widthScaleDrawable = btnWidth * 0.3;
                drawable.setBounds(0, 0, (int) widthScaleDrawable,
                        (int) (widthScaleDrawable / ratioWH));
                setCompoundDrawables(null, drawable, null, null);
                setCompoundDrawablePadding(0);
                break;
            case ALIGN_RIGHT:
                heightScaleDrawable = btnHeight * 0.7;
                drawable.setBounds(0, 0, (int) (heightScaleDrawable / ratioWH),
                        (int) heightScaleDrawable);
                setCompoundDrawables(null, null, drawable, null);
                break;
            case ALIGN_BOTTOM:
                widthScaleDrawable = btnWidth * 0.3;
                drawable.setBounds(0, 0, (int) widthScaleDrawable,
                        (int) (widthScaleDrawable / ratioWH));
                setCompoundDrawables(null, null, drawable, null);
                setCompoundDrawablePadding(0);
//                heightScaleDrawable  =  btnHeight * 0.5;
//                drawable.setBounds(0, 0, (int)(heightScaleDrawable / ratioWH) ,
//                        (int) heightScaleDrawable);
//                setCompoundDrawables(null , null  , null, drawable);
                break;
            default:
                break;
        }
        isHaveDrawable = false;
    }

    public void setDrawable(Drawable drawable) {
        setDrawable(drawable, ALIGN_LEFT);
    }

    public void setDrawable(int res) {
        setDrawable(res, ALIGN_LEFT);
    }

    //Setter
    public void setShadowEnabled(boolean isShadowEnabled) {
        this.isShadowEnabled = isShadowEnabled;
        setShadowHeight(0);
        refresh();
    }

    public void setButtonColor(int buttonColor) {
        this.mButtonColor = buttonColor;
        refresh();
    }



    public void setShadowColor(int shadowColor) {
        this.mShadowColor = shadowColor;
        isShadowColorDefined = true;
        refresh();
    }

    public void setShadowHeight(int shadowHeight) {
        this.mShadowHeight = shadowHeight;
        refresh();
    }

    public void setCornerRadius(int cornerRadius) {
        this.mCornerRadius = cornerRadius;
        refresh();
    }


    public void setBorderWidth(int mBorderWidth) {
        this.mBorderWidth = mBorderWidth;
        refresh();
    }

    public void setButtonPadding(int left, int top, int right, int bottom) {
        mPaddingLeft = left;
        mPaddingRight = right;
        mPaddingTop = top;
        mPaddingBottom = bottom;
        refresh();
    }

    public void setTextButtonColor(int mTextButtonColor) {
        this.mTextButtonColor = getResources().getColor(mTextButtonColor);
        this.setTextColor(this.mTextButtonColor);
    }

    public void setTextButtonPressedColor(int mTextButtonPressedColor) {
        this.mTextButtonPressedColor = getResources().getColor(mTextButtonPressedColor);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        refresh();
    }

    //Getter
    public boolean isShadowEnabled() {
        return isShadowEnabled;
    }

    public int getButtonColor() {
        return mButtonColor;
    }

    public int getShadowColor() {
        return mShadowColor;
    }

    public int getShadowHeight() {
        return mShadowHeight;
    }

    public int getCornerRadius() {
        return mCornerRadius;
    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    public int getTextButtonColor() {
        return mTextButtonColor;
    }

    public int getTextButtonPressedColor() {
        return mTextButtonPressedColor;
    }

    public void inverseButtonColor() {
        refresh();
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
        refresh();
    }
}
