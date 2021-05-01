package cn.cookiestudio.aweadyffa;

import cn.nukkit.form.element.ElementButton;

public class FElementButton extends ElementButton {
    private transient FFAArea ffaArea;
    public FElementButton(String text,FFAArea ffaArea) {
        super(text);
        this.ffaArea = ffaArea;
    }

    public FFAArea getFfaArea() {
        return ffaArea;
    }
}
