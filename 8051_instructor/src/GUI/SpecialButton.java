package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class SpecialButton extends JButton {
    private int width = 0;
    private int height = 0;
    private int str_width, str_height, x, y, arc_width, arc_height;
    private String text;
    private Color backgroundColor, textColor;
    private Font font;
    private FontRenderContext frc;

    public SpecialButton(int width, int height, int arc_width, int arc_height, Color backgroundColor, Color textColor, String text){
        this.width = width;
        this.height = height;
        this.arc_height = arc_height;
        this.arc_width = arc_width;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.text = text;

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);

        font = new Font("標楷體", Font.PLAIN, 20);
        frc = new FontRenderContext(new AffineTransform(), true, true);
        Rectangle rec = font.getStringBounds(text, frc).getBounds();

        str_width = rec.width;
        str_height = rec.height;
        x = (width - str_width) / 2;
        y = (int)((height - str_height) / 2 + str_height * 0.7);

        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
    }

    @Override
    public void paintBorder(Graphics g){
        super.paintBorder(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        if(backgroundColor != null){
            g2.setColor(backgroundColor);
            g2.fillRoundRect(3, 3, width-6, height-6, arc_width, arc_height);
        }

        g2.setColor(textColor);
        g2.setFont(font);
        g2.drawString(text, x, y);
    }
}
