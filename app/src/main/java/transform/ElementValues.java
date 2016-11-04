package transform;

import java.io.Serializable;

/**
 * Created by admin on 2016/11/3.
 */
public class ElementValues implements Serializable{
    private float bgPivotY;
    private float bgHeight;
    private float iconPivotX;
    private float iconWidth;
    private float iconPivotY;

    public ElementValues() {

    }

    public ElementValues(float bgPivotY, float bgHeight, float iconPivotX, float iconWidth, float iconPivotY) {
        this.bgPivotY = bgPivotY;
        this.bgHeight = bgHeight;
        this.iconPivotX = iconPivotX;
        this.iconWidth = iconWidth;
        this.iconPivotY = iconPivotY;
    }

    public float getBgPivotY() {
        return bgPivotY;
    }

    public void setBgPivotY(float bgPivotY) {
        this.bgPivotY = bgPivotY;
    }

    public float getBgHeight() {
        return bgHeight;
    }

    public void setBgHeight(float bgHeight) {
        this.bgHeight = bgHeight;
    }

    public float getIconPivotX() {
        return iconPivotX;
    }

    public void setIconPivotX(float iconPivotX) {
        this.iconPivotX = iconPivotX;
    }

    public float getIconWidth() {
        return iconWidth;
    }

    public void setIconWidth(float iconWidth) {
        this.iconWidth = iconWidth;
    }

    public float getIconPivotY() {
        return iconPivotY;
    }

    public void setIconPivotY(float iconPivotY) {
        this.iconPivotY = iconPivotY;
    }
}
