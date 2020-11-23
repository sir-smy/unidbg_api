package run;

import com.github.unidbg.*;
import com.github.unidbg.linux.android.AndroidARMEmulator;
import com.github.unidbg.linux.android.AndroidResolver;
import com.github.unidbg.linux.android.dvm.*;
import com.github.unidbg.memory.Memory;
import com.github.unidbg.linux.android.dvm.array.ByteArray;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;

@Component
public class NativeUtils extends AbstractJni {

    private static LibraryResolver createLibraryResolver() {
        return new AndroidResolver(23);
    }

    private static AndroidEmulator createARMEmulator() {
        // 进程名 这里可以随意定义
        return new AndroidARMEmulator("com.sun.jna1");
    }

    private final AndroidEmulator emulator;
    private final Module module;
    private final VM vm;
    private final DvmClass Native;

    public NativeUtils(String class_name, String so_name) throws IOException {
        emulator = createARMEmulator();
        final Memory memory = emulator.getMemory();
        memory.setLibraryResolver(createLibraryResolver());

        // 动态获取so的路径
        ClassPathResource classPathResource = new ClassPathResource("so_path/" + so_name);
        try {
            InputStream inputStream = classPathResource.getInputStream();
            Files.copy(inputStream, Paths.get(so_name), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建 vm虚拟机
        vm = emulator.createDalvikVM(null);
        DalvikModule dm = vm.loadLibrary(new File(so_name), false);


        // 可以直接导入apk  写apk路径即可 根据需求传apk或者so名字  如果有签名效验建议apk
        //  vm = emulator.createDalvikVM(new File(so_name));
        //  DalvikModule dm = vm.loadLibrary("protect", false);

        vm.setJni(this);
        vm.setVerbose(true);

        // 手动执行jni_load
        dm.callJNI_OnLoad(emulator);
        module = dm.getModule();

        // 注册类
        Native = vm.resolveClass(class_name);
    }

    private void destroy() throws IOException {
        emulator.close();
    }

    public static void main(String[] args) throws IOException {
        String class_name = "com/xx/xx"; //类路径smali写法
        String so_name = "libprotect.so"; //so名字smali写法    文件复制到resources\so_path目录下即可
        NativeUtils instance = new NativeUtils(class_name, so_name);
        instance.getContentJNI();
        instance.destroy();

    }


    public String getQdscJNI(String a1) {
        String methodSign = "getQdscJNI(Ljava/lang/Object;[B)Ljava/lang/String;";
        byte[] data = a1.getBytes();
        // 加载安卓的context 上下文对象
        DvmObject context = vm.resolveClass("android/content/Context").newObject(null);
        vm.addLocalObject(context);
        Object ret = Native.callStaticJniMethodObject(emulator, methodSign,
                context,
                new ByteArray(vm, data)
        );
        String res = (String) ((DvmObject) ret).getValue();
        System.out.println("结果 " + res);
        return res;
    }

    public String getQdsfJNI(String a1) {
        String methodSign = "getQdsfJNI(Landroid/content/Context;J[BJ)Ljava/lang/String;";
        byte[] data = a1.getBytes();

        DvmObject context = vm.resolveClass("android/content/Context").newObject(null);
        vm.addLocalObject(context);
        long time_long = System.currentTimeMillis();
        long time1 = 0;
        Object ret = Native.callStaticJniMethodObject(emulator, methodSign,
                context,
                time1,
                new ByteArray(vm, data),
                time_long
        );
        String res = (String) ((DvmObject) ret).getValue();
        System.out.println("结果 " + res);
        return res;
    }

    public String getQdsfJNI2(String a1) {
        String methodSign = "getQdsfJNI(Landroid/content/Context;J[B)Ljava/lang/String;";
        byte[] data = a1.getBytes();
        DvmObject context = vm.resolveClass("android/content/Context").newObject(null);
        vm.addLocalObject(context);
        long time1 = 20;
        Object ret = Native.callStaticJniMethodObject(emulator, methodSign,
                context,
                time1,
                new ByteArray(vm, data)
        );
        String res = (String) ((DvmObject) ret).getValue();
        System.out.println("结果 " + res);
        return res;
    }

    public String getCorePropsInfo() {
        String methodSign = "getCorePropsInfo(Landroid/content/Context;)Ljava/lang/String;";
        DvmObject context = vm.resolveClass("android/content/Context").newObject(null);
        vm.addLocalObject(context);
        Object ret = Native.callStaticJniMethodObject(emulator, methodSign,
                context
        );
        String res = (String) ((DvmObject) ret).getValue();
        System.out.println("结果 " + res);
        return res;
    }

    public String getContentJNI() {
        String methodSign = "getContentJNI(Ljava/lang/Object;ILjava/lang/String;Ljava/lang/String;)Ljava/lang/String;";
        DvmObject context = vm.resolveClass("android/content/Context").newObject(null);
        vm.addLocalObject(context);
        int a2 = 0;
        String a3 = "207200067a55e4e923e712b7f3d85c76";
        String a4 = "9.21.5";
        Object ret = Native.callStaticJniMethodObject(emulator, methodSign,
                context,
                a2, a3, a4
        );
        String res = (String) ((DvmObject) ret).getValue();
        System.out.println("结果 " + res);
        return res;
    }


}
