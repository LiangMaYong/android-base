package utils;

import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.search.EverythingGlobalScope;
import entity.Element;
import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BindView extends WriteCommandAction.Simple {

    protected PsiFile mFile;
    protected Project mProject;
    protected PsiClass mClass;
    protected ArrayList<Element> mElements;
    protected PsiElementFactory mFactory;
    protected String mLayoutFileName;
    protected String mFieldNamePrefix;
    protected boolean mCreateHolder;
    protected boolean mBind;

    public BindView(PsiFile file, PsiClass clazz, String command, ArrayList<Element> elements, String layoutFileName, String fieldNamePrefix, boolean bind, boolean createHolder) {
        super(clazz.getProject(), command);
        mFile = file;
        mProject = clazz.getProject();
        mClass = clazz;
        mElements = elements;
        mFactory = JavaPsiFacade.getElementFactory(mProject);
        mLayoutFileName = layoutFileName;
        mFieldNamePrefix = fieldNamePrefix;
        mCreateHolder = createHolder;
        mBind = bind;
    }

    @Override
    public void run() throws Throwable {
        if (mCreateHolder) {
            generateAdapter();
        } else {
            if (mBind) {
                generatorBindView();
                generatorBindOnClick();
                generatorBindLongOnClick();
            } else {
                generateFields();
                generateInitViews();
            }
        }

        // reformat class
        JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(mProject);
        styleManager.optimizeImports(mFile);
        styleManager.shortenClassReferences(mClass);
        new ReformatCodeProcessor(mProject, mClass.getContainingFile(), null, false).runWithoutProgress();
    }

    /**
     * Create ViewHolder for adapters with injections
     */
    protected void generateAdapter() {
        // view holder class
        String holderClassName = Utils.getViewHolderClassName();
        StringBuilder holderBuilder = new StringBuilder();

        // generator of view holder class
        StringBuilder generator = new StringBuilder();
        generator.append("public " + holderClassName + "(android.view.View rootView) {\n");

        // rootView
        String rootViewName = "rootView";
        holderBuilder.append("public " + "android.view.View " + rootViewName + ";\n");
        generator.append("this." + rootViewName + " = " + rootViewName + ";\n");

        for (Element element : mElements) {
            if (!element.used) {
                continue;
            }

            // field
            holderBuilder.append("public " + element.name + " " + element.getFieldName() + ";\n");

            // findViewById in generator
            generator.append("this." + element.getFieldName() + " = (" + element.name + ") "
                    + rootViewName + ".findViewById(" + element.getFullID() + ");\n");
        }
        generator.append("}\n");

        holderBuilder.append(generator.toString());

        PsiClass viewHolder = mFactory.createClassFromText(holderBuilder.toString(), mClass);
        viewHolder.setName(holderClassName);
        mClass.add(viewHolder);
        mClass.addBefore(mFactory.createKeyword("public", mClass), mClass.findInnerClassByName(holderClassName, true));
        mClass.addBefore(mFactory.createKeyword("static", mClass), mClass.findInnerClassByName(holderClassName, true));
    }

    /**
     * Create fields for injections inside main class
     */
    protected void generateFields() {
        for (Iterator<Element> iterator = mElements.iterator(); iterator.hasNext(); ) {
            Element element = iterator.next();

            if (!element.used) {
                iterator.remove();
                continue;
            }

            // remove duplicate field
            PsiField[] fields = mClass.getFields();
            boolean duplicateField = false;
            for (PsiField field : fields) {
                String name = field.getName();
                if (name != null && name.equals(element.getFieldName())) {
                    duplicateField = true;
                    break;
                }
            }
            if (duplicateField) {
                iterator.remove();
                continue;
            }
            mClass.add(mFactory.createFieldFromText("private " + element.name + " " + element.getFieldName() + ";", mClass));
        }
    }

    protected void generateInitViews() {
        PsiClass activityClass = JavaPsiFacade.getInstance(mProject).findClass(
                "android.app.Activity", new EverythingGlobalScope(mProject));
        PsiClass compatActivityClass = JavaPsiFacade.getInstance(mProject).findClass(
                "android.support.v7.app.AppCompatActivity", new EverythingGlobalScope(mProject));
        PsiClass fragmentClass = JavaPsiFacade.getInstance(mProject).findClass(
                "android.app.Fragment", new EverythingGlobalScope(mProject));
        PsiClass supportFragmentClass = JavaPsiFacade.getInstance(mProject).findClass(
                "android.support.v4.app.Fragment", new EverythingGlobalScope(mProject));

        // Check for Activity class
        if ((activityClass != null && mClass.isInheritor(activityClass, true))
                || (compatActivityClass != null && mClass.isInheritor(compatActivityClass, true))
                || mClass.getName().contains("Activity")) {
            generatorLayoutCode("this", null);
            // Check for Fragment class
        } else if ((fragmentClass != null && mClass.isInheritor(fragmentClass, true)) || (supportFragmentClass != null && mClass.isInheritor(supportFragmentClass, true))) {
            generatorLayoutCode("getContext()", "rootView");
        }
    }

    /**
     * generatorLayoutCode
     *
     * @param contextName
     * @param findPre
     */
    protected void generatorLayoutCode(String contextName, String findPre) {
        StringBuilder initView = new StringBuilder();
        if (TextUtils.isEmpty(findPre)) {
            initView.append("private void _initView() {\n");
        } else {
            initView.append("private void _initView(View " + findPre + ") {\n");
        }
        for (Element element : mElements) {
            String pre = TextUtils.isEmpty(findPre) ? "" : findPre + ".";
            initView.append(element.getFieldName() + " = (" + element.name + ") " + pre + "findViewById(" + element.getFullID() + ");\n");
        }
        initView.append("}\n");
        PsiMethod[] initViewMethods = mClass.findMethodsByName("initView", false);
        if (initViewMethods.length > 0 && initViewMethods[0].getBody() != null) {
            PsiCodeBlock initViewMethodBody = initViewMethods[0].getBody();
            for (Element element : mElements) {
                String pre = TextUtils.isEmpty(findPre) ? "" : findPre + ".";
                String s2 = element.getFieldName() + " = (" + element.name + ") " + pre + "findViewById(" + element.getFullID() + ");";
                initViewMethodBody.add(mFactory.createStatementFromText(s2, initViewMethods[0]));
            }
        } else {
            mClass.add(mFactory.createMethodFromText(initView.toString(), mClass));
        }
    }

    /**
     * generatorBindView
     */
    protected void generatorBindView() {
        for (Iterator<Element> iterator = mElements.iterator(); iterator.hasNext(); ) {
            Element element = iterator.next();

            if (!element.used) {
                iterator.remove();
                continue;
            }

            // remove duplicate field
            PsiField[] fields = mClass.getFields();
            boolean duplicateField = false;
            for (PsiField field : fields) {
                String name = field.getName();
                if (name != null && name.equals(element.getFieldName())) {
                    duplicateField = true;
                    break;
                }
            }
            if (duplicateField) {
                iterator.remove();
                continue;
            }
            mClass.add(mFactory.createFieldFromText(BindViewConfig.BindView + "(" + element.getFullID() + ")\nprivate " + element.name + " " + element.getFieldName() + ";", mClass));
        }
    }

    /**
     * generatorBindOnClick
     */
    protected void generatorBindOnClick() {
        List<Element> clickableElements = new ArrayList<Element>();
        for (Element element : mElements) {
            if (element.isClickable) {
                clickableElements.add(element);
            }
        }
        if (clickableElements.size() > 0) {
            StringBuilder buider = new StringBuilder();
            StringBuilder caseBuider = new StringBuilder();
            buider.append(BindViewConfig.BindOnClick + "({");
            for (int i = 0; i < clickableElements.size(); i++) {
                buider.append(clickableElements.get(i).getFullID());
                if (i < clickableElements.size() - 1) {
                    buider.append(",");
                }
                caseBuider.append("case " + clickableElements.get(i).getFullID() + " :\n\nbreak;\n");
            }
            buider.append("})\n");
            mClass.add(mFactory.createMethodFromText(buider.toString() + "private void bindOnClick(View v){\nswitch (v.getId()) {\n" +
                    caseBuider.toString() +
                    "}\n" +
                    "}", mClass));
        }
    }

    /**
     * generatorBindLongOnClick
     */
    protected void generatorBindLongOnClick() {
        List<Element> clickableElements = new ArrayList<Element>();
        for (Element element : mElements) {
            if (element.isLongClickable) {
                clickableElements.add(element);
            }
        }
        if (clickableElements.size() > 0) {
            StringBuilder buider = new StringBuilder();
            StringBuilder caseBuider = new StringBuilder();
            buider.append(BindViewConfig.BindOnLongClick + "({");
            for (int i = 0; i < clickableElements.size(); i++) {
                buider.append(clickableElements.get(i).getFullID());
                if (i < clickableElements.size() - 1) {
                    buider.append(",");
                }
                caseBuider.append("case " + clickableElements.get(i).getFullID() + " :\n\nbreak;\n");
            }
            buider.append("})\n");
            mClass.add(mFactory.createMethodFromText(buider.toString() + "private boolean bindLongOnClick(View v){\nswitch (v.getId()) {\n" +
                    caseBuider.toString() +
                    "}\n" +
                    "return false;\n" +
                    "}", mClass));
        }
    }

}