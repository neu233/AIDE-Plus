package abcd;


/*
 author : 罪慾
 date : 2024/10/2 22:46
 description : QQ3115093767
 */

import static io.github.zeroaicy.aide.completion.XmlCompletionUtilsKt.completionXmlAttribute;
import static io.github.zeroaicy.aide.completion.XmlCompletionUtilsKt.completionXmlTag;
import static io.github.zeroaicy.aide.completion.XmlCompletionUtilsKt.completionXmlValue;
import static io.github.zeroaicy.aide.completion.XmlCompletionUtilsKt.initAndroidSDK;

import androidx.annotation.Keep;

import com.aide.codemodel.api.FileEntry;
import com.aide.codemodel.api.FileSpace;
import com.aide.codemodel.api.Model;
import com.aide.codemodel.api.SyntaxTree;
import com.aide.codemodel.language.xml.XmlCodeModel;
import com.aide.common.AppLog;
import com.aide.ui.project.internal.GradleTools;
import com.android.aaptcompiler.ResourceTable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import io.github.zeroaicy.aide.aaptcompiler.ResourceUtils;
import io.github.zeroaicy.aide.completion.XmlCompletionUtilsKt;
import io.github.zeroaicy.aide.ui.services.ThreadPoolService;
import io.github.zeroaicy.readclass.classInfo.JavaViewUtils;
import io.github.zeroaicy.util.ContextUtil;
import io.github.zeroaicy.util.reflect.ReflectPie;


/**
 * @author: 罪慾
 * @github: https://github.com/neu233/
 * @mail: 3115093767@qq.com
 * @createTime: 2024/11/1
 * @apiNote: 必需Keep下面的方法，因为这些方法都被外部调用了
 * </br>
 * j6(SyntaxTree syntaxTree, int i)
 * </br>
 * Hw(FileEntry fileEntry)
 * </br>
 * FH(SyntaxTree syntaxTree, int i, int i2)
 * </br>
 * DW(SyntaxTree syntaxTree, int i, int i2)
 */

@Keep
public class v7 {
    private static final String TAG = "v7";
    public static boolean isInitAndroidSDK;
    private static AtomicBoolean inited = new AtomicBoolean(false);
    @Keep
    private final Model model;
    private FileSpace fileSpace;
    private ReflectPie fileSpaceReflect;

    @Keep
    public v7(Model model) {
        this.model = model;
        if (model != null) {
            this.fileSpace = model.fileSpace;
            this.fileSpaceReflect = ReflectPie.on(this.fileSpace);
            if (isInitAndroidSDK) {
                return;
            }
            isInitAndroidSDK = true;
            ThreadPoolService defaultThreadPoolService = ThreadPoolService.getDefaultThreadPoolService();
            defaultThreadPoolService.submit(() -> initAndroidSDK(ContextUtil.getContext()));
        }
    }

    @Keep
    public void DW(SyntaxTree syntaxTree, int tag, int element) {
        init();
        completionXmlAttribute(model, syntaxTree, tag, element);
    }

    @Keep
    public void FH(SyntaxTree syntaxTree, int line, int column) {
        // AppLog.println_d("FH: %s ", syntaxTree.getFile());
        init();
        //XmlCompletionUtils.completionTag(model, syntaxTree, line, column);
        completionXmlTag(model, syntaxTree, line, column);
    }

    @Keep
    public void j6(SyntaxTree syntaxTree, int index) {
        init();
        int property = syntaxTree.getIdentifier(syntaxTree.getChildNode(syntaxTree.getChildNode(index, 0), 2));
        completionXmlValue(model, syntaxTree, property, index);
    }

    @Keep
    public boolean Hw(FileEntry fileEntry) {
        init();
        return fileEntry.getCodeModel() instanceof XmlCodeModel;
    }

    private void init() {
        if (inited.get()) {
            return;
        }
        inited.set(true);

        initAsync();
    }

    public void initAsync() {
        AppLog.println_e("initAsync");

        HashMap<Integer, FileSpace.Assembly> assemblyMap = new HashMap<>(getAssemblyMap());

        // synchronized (assemblyMap) {

        // 遍历创建项目
        ResourceUtils resourceUtil = XmlCompletionUtilsKt.getResourceUtil();
        JavaViewUtils javaViewUtils = XmlCompletionUtilsKt.getJavaViewUtils();

        Set<String> loadJarPaths = new HashSet<>();
        Set<File> resDirs = new HashSet<>();

        for (Map.Entry<Integer, FileSpace.Assembly> entry : assemblyMap.entrySet()) {

            FileSpace.Assembly assembly = entry.getValue();

            String projectPath = FileSpace.Assembly.Zo(assembly);

            String assemblyName = FileSpace.Assembly.VH(assembly);
            if ("rt.jar".equals(assemblyName)
                    || "android.jar".equals(assemblyName)) {
                continue;
            }

            File projectFile = new File(projectPath);
            if (projectFile.isFile()) {
                // jar没有
                continue;
            }

            // gradle项目必然是没有classes.jar的
            File resDir;
            if (GradleTools.isGradleProject(projectPath)) {
                resDir = new File(projectFile, "src/main/res");
            } else {
                resDir = new File(projectFile, "res");
            }

            if (resDir.exists()) {
                resDirs.add(resDir);
            }

            File classesJarFile = new File(projectFile, "classes.jar");
            if (classesJarFile.exists()) {
                loadJarPaths.add(classesJarFile.getAbsolutePath());
            }
        }
        // 置空 appResourceTable
        if (resourceUtil != null) {
            resourceUtil.removeTable("app");
        }

        ResourceTable appResourceTable = resourceUtil.forPackage("app", resDirs.toArray(new File[0]));


        try {
            javaViewUtils.loadJar(loadJarPaths);
        } catch (IOException e) {
            AppLog.e(e);
        }
    }

    /**
     * AssemblyId -> Assembly[assemblyName，assembly路径，]
     */
    public HashMap<Integer, FileSpace.Assembly> getAssemblyMap() {
        return this.fileSpaceReflect.get("assemblyMap");
    }
}



