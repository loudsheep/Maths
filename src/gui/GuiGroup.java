package gui;

import java.util.ArrayList;
import java.util.List;

public final class GuiGroup {
    private List<Element> elements = new ArrayList<>();

    public GuiGroup() {
    }

    public void show() {
        for (Element e : elements) {
            e.show();
        }
    }

    public void move(float dx, float dy) {
        for (Element e : elements) {
            e.getPosition(this).x += dx;
            e.getPosition(this).y += dy;
        }
    }

    public Element getElement(int index) {
        return elements.get(index);
    }

    public void addElement(Element element) {
        this.elements.add(element);
    }

    public Element remove(int index) {
        return elements.remove(index);
    }

    public boolean remove(Element element) {
        return elements.remove(element);
    }

    public void mousePressed(int mouseX, int mouseY) {
        for (Element e : elements) {
            e.clicked(mouseX, mouseY);
        }
    }

    public void mouseReleased(int mouseX, int mouseY) {
        for (Element e : elements) {
            e.released(mouseX, mouseY);
        }
    }
}
