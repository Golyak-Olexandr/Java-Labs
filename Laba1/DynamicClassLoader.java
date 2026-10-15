import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DynamicClassLoader extends ClassLoader {
    private final String classDir;

    public DynamicClassLoader(String classDir) {
        // null в якості parent запобігає завантаженню через системний ClassLoader
        super(null);
        this.classDir = classDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // Дозволяємо стандартним системним класам (String, Object тощо) завантажуватися базовим кастомним лоадером
        if (name.startsWith("java.")) {
            return ClassLoader.getSystemClassLoader().loadClass(name);
        }

        File file = new File(classDir, name.replace('.', '/') + ".class");
        if (!file.exists()) {
            throw new ClassNotFoundException("Класс не знайдено: " + name);
        }

        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Не вдалося зчитати файл класу", e);
        }
    }
}