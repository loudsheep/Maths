package gui;

import processing.core.PApplet;
import util.Color;

public class Slider extends Element {
    public interface Change {
        void change(float value);
    }

    float width, height;
    float minRange, maxRange;
    float step;
    float stepDistance;
    float value;
    float sliderPos;
    float sliderWidth, sliderHeight;

    Change action;

    Color sliderColor = new Color(150, 150, 150);
    Color lineColor = new Color(100, 100, 100);

    public Slider(PApplet sketch, float x, float y, float w, float min, float max, float value) {
        this(sketch, x, y, w, min, max, 0.01f, value);
    }

    public Slider(PApplet sketch, float x, float y, float w, float min, float max, float step, float value) {
        super(sketch, x, y);
        this.width = w;
        this.height = w / 5f;
        this.sliderWidth = width / 10;
        this.sliderHeight = height;
        this.minRange = min;
        this.maxRange = max;
        this.step = step;
        this.stepDistance = w / ((max - min) / step);

        this.value = PApplet.constrain(value, min, max); // constrain value to not exceed given range
        this.sliderPos = PApplet.map(this.value, min, max, 0, w);
//        this.sliderPos = Math.max(Math.min(value, max), min); // constrain value to not exceed given range
    }

    @Override
    public void show() {
        if (!active) return;

        sketch.strokeWeight(3);
        sketch.stroke(lineColor.r, lineColor.g, lineColor.b);
        sketch.line(pos.x, pos.y + height / 2, pos.x + width, pos.y + height / 2);

        sketch.noStroke();
        sketch.fill(sliderColor.r, sliderColor.g, sliderColor.b);
        sketch.rect(pos.x + sliderPos - sliderWidth / 2, pos.y, sliderWidth, sliderHeight);

        if (clicked) {
            float x = sketch.mouseX;
            float nearestStep = PApplet.map(x, pos.x, pos.x + width, 0, width);
            nearestStep = PApplet.constrain(nearestStep, 0, width);

            nearestStep = Math.round(nearestStep / stepDistance) * stepDistance;

            float value = PApplet.map(nearestStep + pos.x, pos.x, pos.x + width, minRange, maxRange);
            value = PApplet.constrain(value, minRange, maxRange);
            if (this.value != value) {
                this.value = value;
                if (action != null) {
                    action.change(value);
                }
            }
            sliderPos = PApplet.map(value, minRange, maxRange, 0, width);
        }
    }

    @Override
    public void clicked(float mouseX, float mouseY) {
        if (mouseX > pos.x && mouseX < pos.x + width) {
            if (mouseY > pos.y && mouseY < pos.y + height) {
                clicked = true;
            }
        }
    }

    @Override
    public void released(float mouseX, float mouseY) {
        clicked = false;
    }

    public void setAction(Change action) {
        this.action = action;
    }
}
