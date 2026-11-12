// Utility quản lý EntityManager - giúp kết nối với CSDL qua Hibernate
// Thông tin kết nối thật (url/user/password) đọc từ db.properties (gitignored)
// để không lỡ commit mật khẩu vào persistence.xml
package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JpaUtils {
    private static final EntityManagerFactory factory =
        Persistence.createEntityManagerFactory("DevClimb", loadDbOverrides());

    // Đọc db.properties (nếu có) và ghi đè thông tin kết nối trong persistence.xml
    private static Map<String, Object> loadDbOverrides() {
        Map<String, Object> overrides = new HashMap<>();
        try (InputStream is = JpaUtils.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) {
                System.err.println("db.properties not found - using values in persistence.xml");
                return overrides;
            }
            Properties props = new Properties();
            props.load(is);
            putIfSet(overrides, "jakarta.persistence.jdbc.url", props.getProperty("db.url"));
            putIfSet(overrides, "jakarta.persistence.jdbc.user", props.getProperty("db.user"));
            putIfSet(overrides, "jakarta.persistence.jdbc.password", props.getProperty("db.password"));
        } catch (Exception e) {
            System.err.println("Failed to read db.properties: " + e.getMessage());
        }
        return overrides;
    }

    private static void putIfSet(Map<String, Object> map, String key, String value) {
        if (value != null && !value.isBlank()) map.put(key, value.trim());
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static void shutdown() {
        if (factory.isOpen()) factory.close();
    }
}
