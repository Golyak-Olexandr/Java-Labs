package main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public final class LocalizationManager {

    private static final Logger LOGGER = Logger.getLogger(LocalizationManager.class.getName());
    private static final String BUNDLE_BASE_NAME = "location.messages";
    private static final LocalizationManager INSTANCE = new LocalizationManager();

    private Locale currentLocale;
    private ResourceBundle bundle;

    private LocalizationManager() {
        setLocale(Locale.forLanguageTag("uk"));
    }

    public static LocalizationManager getInstance() {
        return INSTANCE;
    }

    public synchronized void setLocale(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("Locale must not be null");
        }
        this.currentLocale = locale;
        this.bundle = loadBundle(locale);
        LOGGER.info("Встановлено локаль інтерфейсу: " + locale.toLanguageTag());
    }

    public synchronized Locale getCurrentLocale() {
        return currentLocale;
    }

    public synchronized String getString(String key) {
        return bundle.getString(key);
    }

    public synchronized String format(String key, Object... args) {
        return String.format(currentLocale, bundle.getString(key), args);
    }

    private ResourceBundle loadBundle(Locale locale) {
        try {
            return ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
        } catch (MissingResourceException primaryException) {
            ClassLoader fallbackLoader = buildFallbackClassLoader();
            if (fallbackLoader != null) {
                return ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale, fallbackLoader);
            }
            throw primaryException;
        }
    }

    private ClassLoader buildFallbackClassLoader() {
        List<Path> candidateRoots = List.of(
                Path.of("task3", "src", "resources"),
                Path.of("src", "resources"),
                Path.of("resources")
        );
        List<URL> urls = new ArrayList<>();
        for (Path root : candidateRoots) {
            if (Files.isDirectory(root)) {
                try {
                    File dir = root.toAbsolutePath().toFile();
                    urls.add(dir.toURI().toURL());
                } catch (MalformedURLException ignored) {
                }
            }
        }
        if (urls.isEmpty()) {
            return null;
        }
        return new URLClassLoader(urls.toArray(new URL[0]));
    }
}