import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        String javaFilePath = "TestModule.java";
        File file = new File(javaFilePath);

        System.out.println("Запуск відстеження файлу " + file.getAbsolutePath());
        System.out.println("Змініть вміст TestModule.java у VS Code та збережіть (Ctrl + S)...");

        long lastModified = 0;

        while (true) {
            if (file.exists() && file.lastModified() != lastModified) {
                lastModified = file.lastModified();

                // 1. Автоматично компілюємо TestModule.java у TestModule.class
                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                if (compiler == null) {
                    System.err.println("Помилка: JDK Compiler недоступний.");
                    return;
                }

                int compileResult = compiler.run(null, null, null, javaFilePath);
                if (compileResult == 0) {
                    try {
                        // 2. Створюємо НОВИЙ екземпляр ClassLoader для скидання кешу JVM
                        DynamicClassLoader loader = new DynamicClassLoader(".");
                        Class<?> clazz = loader.loadClass("TestModule");

                        // 3. Створюємо об'єкт та викликаємо toString()
                        Object instance = clazz.getDeclaredConstructor().newInstance();
                        System.out.println("\n[Оновлено] System.out.println(t): " + instance);
                    } catch (Exception e) {
                        System.err.println("Помилка завантаження класу: " + e.getMessage());
                    }
                } else {
                    System.err.println("Помилка компіляції TestModule.java");
                }
            }

            Thread.sleep(1000); // Перевірка кожну секунду
        }
    }
}