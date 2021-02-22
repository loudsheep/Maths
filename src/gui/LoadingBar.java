package gui;

import processing.core.PApplet;
import util.Color;
import util.Maths;

public class LoadingBar extends Element {

    private float w, h;
    private float progress; // from 0 to 1
    private boolean finished;

    private Color bgColor;
    private Color borderColor;
    private Color barColor;

    public LoadingBar(PApplet sketch, float x, float y, float w, float h) {
        super(sketch, x, y);
        this.w = w;
        this.h = h;
    }

    @Override
    public void show() {
        sketch.fill(bgColor.r, bgColor.g, bgColor.b);
        sketch.stroke(borderColor.r, borderColor.g, borderColor.b);
        sketch.rect(pos.x, pos.y, w, h);

        sketch.noStroke();
        sketch.fill(barColor.r, barColor.g, barColor.b);
        sketch.rect(pos.x, pos.y, w * progress, h);
    }

    public void addPercentage(float value) {
        progress += value / 100;
        progress = Maths.limit(progress, 0, 1);
        if(progress == 1) finished = true;
        else finished = false;
    }

    @Override
    public void clicked(float v, float v1) {
    }

    @Override
    public void released(float v, float v1) {
    }

    public boolean isFinished() {
        return finished;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setBarColor(Color barColor) {
        this.barColor = barColor;
    }
}
