package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GuiGroup {
    private List<Element> elements = new ArrayList<>();
    private Map<String, Element> elems = new HashMap<>();

    public GuiGroup() {
    }

    public void show() {
        for (Element e : elems.values()) {
            e.show();
        }
    }

    public void move(float dx, float dy) {
        for (Element e : elems.values()) {
            e.getPosition(this).x += dx;
            e.getPosition(this).y += dy;
        }
    }

    public Element getElement(String id) {
        if (!elems.containsKey(id)) return null;

        return elems.get(id);
    }

    public void addElement(String id, Element element) {
        this.elems.put(id, element);
    }

    public Element remove(String id) {
        return elems.remove(id);
    }

    public boolean remove(Element element) {
        return elements.remove(element);

    }

    public void mousePressed(int mouseX, int mouseY) {
        for (Element e : elems.values()) {
            e.clicked(mouseX, mouseY);
        }
    }

    public void mouseReleased(int mouseX, int mouseY) {
        for (Element e : elems.values()) {
            e.released(mouseX, mouseY);
        }
    }
}
