package form;


import entity.Element;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Entry extends JPanel {

    protected EntryList mParent;
    protected Element mElement;
    // ui
    protected JCheckBox mCheck;
    protected JLabel mType;
    protected JLabel mID;
    protected JTextField mName;
    protected Color mNameDefaultColor;
    protected Color mNameErrorColor = new Color(0x880000);

    public Entry(EntryList parent, Element element) {
        mElement = element;
        mParent = parent;

        mCheck = new JCheckBox();
        mCheck.setPreferredSize(new Dimension(40, 26));
        if (!element.id.contains(element.getFullID())) {
            mCheck.setSelected(mElement.used);
        } else {
            mCheck.setSelected(false);
        }
        mCheck.addChangeListener(new CheckListener());

        mType = new JLabel(mElement.name);
        mType.setPreferredSize(new Dimension(100, 26));

        mID = new JLabel(mElement.id);
        mID.setPreferredSize(new Dimension(160, 26));

        mName = new JTextField(mElement.getFieldName(), 10);
        mNameDefaultColor = mName.getBackground();
        mName.setPreferredSize(new Dimension(360, 26));
        mName.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                // empty
            }

            public void focusLost(FocusEvent e) {
                syncElement();
            }
        });

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setMaximumSize(new Dimension(Short.MAX_VALUE, 54));
        add(mCheck);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(mType);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(mID);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(mName);
        add(Box.createHorizontalGlue());

        checkState();
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }

    public Element syncElement() {
        mElement.used = mCheck.isSelected();

        if (mElement.checkValidity()) {
            mName.setBackground(mNameDefaultColor);
        } else {
            mName.setBackground(mNameErrorColor);
        }

        return mElement;
    }

    private void checkState() {
        if (mCheck.isSelected()) {
            mType.setEnabled(true);
            mID.setEnabled(true);
            mName.setEnabled(true);
        } else {
            mType.setEnabled(false);
            mID.setEnabled(false);
            mName.setEnabled(false);
        }
    }

    // classes

    public class CheckListener implements ChangeListener {

        public void stateChanged(ChangeEvent event) {
            checkState();
        }
    }
}
