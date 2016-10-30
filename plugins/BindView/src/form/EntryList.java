package form;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import entity.Element;
import listener.ICancelListener;
import listener.IConfirmListener;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class EntryList extends JPanel {

    private Project mProject;
    private Editor mEditor;
    private ArrayList<Element> mElements = new ArrayList<Element>();
    private ArrayList<Entry> mEntries = new ArrayList<Entry>();
    private String mPrefix = null;
    private JPanel contentPanel;
    private JScrollPane mScrollListPane;
    private IConfirmListener mConfirmListener;
    private ICancelListener mCancelListener;
    private JCheckBox mHolderCheck;
    private boolean mCreateHolder = false;
    private boolean mBind = true;
    private JCheckBox mAllCheck;
    private JCheckBox mBindCheck;
    private JButton mConfirm;
    private JButton mCancel;

    public EntryList(Project project, Editor editor, ArrayList<Element> elements, boolean createHolder, IConfirmListener confirmListener, ICancelListener cancelListener) {
        mProject = project;
        mEditor = editor;
        mCreateHolder = createHolder;
        mConfirmListener = confirmListener;
        mCancelListener = cancelListener;
        if (elements != null) {
            mElements.addAll(elements);
        }

        setPreferredSize(new Dimension(740, 360));
        setMaximumSize(new Dimension(800, 800));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        addInjections();
        addButtons();
    }

    private void addInjections() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(new EntryHeader(new EntryHeader.OnTypeSelected() {
            @Override
            public void onTypeSelected(int type) {
                for (Element element : mElements) {
                    element.fieldNameType = type;
                }

                contentPanel.remove(mScrollListPane);
                mScrollListPane = getScrollListPanel();
                contentPanel.add(mScrollListPane);
                refresh();
            }
        }));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        mScrollListPane = getScrollListPanel();
        contentPanel.add(mScrollListPane);

        add(contentPanel, BorderLayout.CENTER);
        refresh();
    }

    private void checkAll(boolean checked) {
        for (Element element : mElements) {
            element.used = checked;
        }

        contentPanel.remove(mScrollListPane);
        mScrollListPane = getScrollListPanel();
        contentPanel.add(mScrollListPane);
        refresh();
    }

    @NotNull
    private JBScrollPane getScrollListPanel() {
        JPanel injectionsPanel = new JPanel();
        injectionsPanel.setLayout(new BoxLayout(injectionsPanel, BoxLayout.PAGE_AXIS));
        injectionsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        for (int i = 0; i < mElements.size(); i++) {
            Element element = mElements.get(i);
            Entry entry = new Entry(this, element);

            if (i > 0) {
                injectionsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
            injectionsPanel.add(entry);

            mEntries.add(entry);
        }
        injectionsPanel.add(Box.createVerticalGlue());
        if (mElements.size() < 10) {
            int count = 10 - mElements.size();
            injectionsPanel.add(Box.createRigidArea(new Dimension(0, count * 20)));
        } else {
            injectionsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }
        return new JBScrollPane(injectionsPanel);
    }

    private void addButtons() {
        // Annotation
        mBindCheck = new JCheckBox();
        mBindCheck.setPreferredSize(new Dimension(32, 26));
        mBindCheck.setSelected(true);
        mBindCheck.addChangeListener(new CheckBindListener());
        JLabel mBindLabel = new JLabel();
        mBindLabel.setText("Use Annotation");
        mBindLabel.addMouseListener(new ClickBindListener());

        // create viewholder
        mHolderCheck = new JCheckBox();
        mHolderCheck.setPreferredSize(new Dimension(32, 26));
        mHolderCheck.setSelected(mCreateHolder);
        mHolderCheck.setSelected(false);
        mHolderCheck.addChangeListener(new CheckHolderListener());
        JLabel mHolderLabel = new JLabel();
        mHolderLabel.setText("Create ViewHolder");
        mHolderLabel.addMouseListener(new ClickHolderListener());

        // check all
        mAllCheck = new JCheckBox();
        mAllCheck.setPreferredSize(new Dimension(32, 26));
        mAllCheck.setSelected(true);
        mAllCheck.addChangeListener(new CheckAllListener());

        JLabel mAllLabel = new JLabel();
        mAllLabel.setText("Check All");
        mAllLabel.addMouseListener(new ClickAllListener());

        JPanel holderPanel = new JPanel();
        holderPanel.setLayout(new BoxLayout(holderPanel, BoxLayout.LINE_AXIS));
        holderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        holderPanel.add(mBindCheck);
        holderPanel.add(mBindLabel);
        holderPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        holderPanel.add(mAllCheck);
        holderPanel.add(mAllLabel);
        holderPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        holderPanel.add(mHolderCheck);
        holderPanel.add(mHolderLabel);
        holderPanel.add(Box.createHorizontalGlue());
        add(holderPanel, BorderLayout.PAGE_END);

        mCancel = new JButton();
        mCancel.setAction(new CancelAction());
        mCancel.setPreferredSize(new Dimension(120, 26));
        mCancel.setText("Cancel");
        mCancel.setVisible(true);

        mConfirm = new JButton();
        mConfirm.setAction(new ConfirmAction());
        mConfirm.setPreferredSize(new Dimension(120, 26));
        mConfirm.setText("Confirm");
        mConfirm.setVisible(true);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(mCancel);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(mConfirm);

        add(buttonPanel, BorderLayout.PAGE_END);
        refresh();
    }

    private void refresh() {
        revalidate();
        if (mConfirm != null) {
            mConfirm.setVisible(mElements.size() > 0);
        }
    }

    private boolean checkValidity() {
        boolean valid = true;

        for (Element element : mElements) {
            if (!element.checkValidity()) {
                valid = false;
            }
        }

        return valid;
    }

    public JButton getConfirmButton() {
        return mConfirm;
    }

    private class CheckHolderListener implements ChangeListener {

        public void stateChanged(ChangeEvent event) {
            mCreateHolder = mHolderCheck.isSelected();
        }
    }

    private class ClickHolderListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (mHolderCheck.isSelected()) {
                mHolderCheck.setSelected(false);
            } else {
                mHolderCheck.setSelected(true);
            }
            mCreateHolder = mHolderCheck.isSelected();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class CheckAllListener implements ChangeListener {

        public void stateChanged(ChangeEvent event) {
            checkAll(mAllCheck.isSelected());
        }
    }

    private class ClickAllListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (mAllCheck.isSelected()) {
                mAllCheck.setSelected(false);
            } else {
                mAllCheck.setSelected(true);
            }
            checkAll(mAllCheck.isSelected());
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class CheckBindListener implements ChangeListener {

        public void stateChanged(ChangeEvent event) {
            mBind = mBindCheck.isSelected();
        }
    }

    private class ClickBindListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (mBindCheck.isSelected()) {
                mBindCheck.setSelected(false);
            } else {
                mBindCheck.setSelected(true);
            }
            mBind = mBindCheck.isSelected();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class ConfirmAction extends AbstractAction {

        public void actionPerformed(ActionEvent event) {
            boolean valid = checkValidity();

            for (Entry entry : mEntries) {
                entry.syncElement();
            }

            if (valid) {
                if (mConfirmListener != null) {
                    mConfirmListener.onConfirm(mProject, mEditor, mElements, mPrefix, mBind, mCreateHolder);
                }
            }
        }
    }

    private class CancelAction extends AbstractAction {

        public void actionPerformed(ActionEvent event) {
            if (mCancelListener != null) {
                mCancelListener.onCancel();
            }
        }
    }
}
